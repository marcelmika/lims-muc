/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.controller;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.SearchBuddiesRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.SearchBuddiesResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePresenceBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.limsmuc.core.service.BuddyCoreService;
import com.marcelmika.limsmuc.core.service.SettingsCoreService;
import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.Presence;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.request.parameters.ReloginParameters;
import com.marcelmika.limsmuc.portal.request.parameters.SearchBuddiesParameters;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 4:59 PM
 */
public class BuddyController {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyController.class);

    // Dependencies
    BuddyCoreService buddyCoreService;
    SettingsCoreService settingsCoreService;

    /**
     * Constructor
     *
     * @param buddyCoreService    BuddyCoreService
     * @param settingsCoreService SettingsCoreService
     */
    public BuddyController(final BuddyCoreService buddyCoreService,
                           final SettingsCoreService settingsCoreService) {

        this.buddyCoreService = buddyCoreService;
        this.settingsCoreService = settingsCoreService;
    }

    /**
     * Update buddy's status
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void updateBuddyPresence(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;            // Authorized user
        Presence presence;      // Buddy's presence

        // Deserialize
        try {
            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Presence
            Buddy deserializedBuddy = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_CONTENT), Buddy.class
            );
            presence = deserializedBuddy.getPresence();
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Send request to core service
        UpdatePresenceBuddyResponseEvent responseEvent = buddyCoreService.updatePresence(
                new UpdatePresenceBuddyRequestEvent(buddy.getBuddyId(), presence.toPresenceDetails())
        );

        // Disable chat if presence is offline
        if (presence == Presence.OFFLINE) {
            settingsCoreService.disableChat(new DisableChatRequestEvent(buddy.toBuddyDetails()));
        }
        // Enable otherwise
        else {
            settingsCoreService.enableChat(new EnableChatRequestEvent(buddy.toBuddyDetails()));
        }

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(null, HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            UpdatePresenceBuddyResponseEvent.Status status = responseEvent.getStatus();
            // Bad request
            if (status == UpdatePresenceBuddyResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Unauthorized
            else if (status == UpdatePresenceBuddyResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Returns a list of buddies based on the search query
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void searchBuddies(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                            // Authorized user
        SearchBuddiesParameters parameters;     // Request parameters

        // Deserialize
        try {
            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), SearchBuddiesParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Search event
        SearchBuddiesResponseEvent responseEvent = buddyCoreService.searchBuddies(new SearchBuddiesRequestEvent(
                buddy.toBuddyDetails(), parameters.getSearchQuery()
        ));

        // Success
        if (responseEvent.isSuccess()) {

            // Map buddy details to buddy
            List<Buddy> buddyList = Buddy.fromBuddyDetailsList(responseEvent.getSearchResults());

            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(buddyList);

            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            SearchBuddiesResponseEvent.Status status = responseEvent.getStatus();
            // Bad request
            if (status == SearchBuddiesResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // No session
            else if (status == SearchBuddiesResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Performs relogin
     *
     * @param request ResourceRequest
     * @param response ResourceResponse
     */
    public void relogin(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                    // Authorized user
        ReloginParameters parameters;   // Request parameters

        // Deserialize
        try {
            buddy = Buddy.fromResourceRequest(request);

            // Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), ReloginParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Set password
        buddy.setPassword(parameters.getPassword());

        // Login buddy
        LoginBuddyResponseEvent responseEvent = buddyCoreService.loginBuddy(
                new LoginBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Write success to response
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            LoginBuddyResponseEvent.Status status = responseEvent.getStatus();
            // Bad request
            if (status == LoginBuddyResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Forbidden
            else if (status == LoginBuddyResponseEvent.Status.ERROR_JABBER ||
                    status == LoginBuddyResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }
}
