/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.controller;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddiesPresenceRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddiesPresenceResponseEvent;
import com.marcelmika.limsmuc.core.service.BuddyCoreService;
import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.ErrorMessage;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/01/15
 * Time: 19:19
 */
public class IPCController {

    // Log
    private static Log log = LogFactoryUtil.getLog(BuddyController.class);

    // Dependencies
    BuddyCoreService buddyCoreService;

    // Constants
    private final static int MAX_BUDDIES_COUNT = 100; // Max number of buddies for one request

    /**
     * Constructor
     *
     * @param buddyCoreService BuddyCoreService
     */
    public IPCController(final BuddyCoreService buddyCoreService) {
        this.buddyCoreService = buddyCoreService;
    }

    /**
     * Reads buddies data
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readBuddies(ResourceRequest request, ResourceResponse response) {

        Set<Long> buddyIdsSet;      // Set of deserialized buddies (no duplicates)

        // Deserialize
        try {
            // Create deserializer for a list of buddies
            JSONDeserializer<List<Long>> deserializer = JSONFactoryUtil.createJSONDeserializer();
            // Values must be numbers
            deserializer.use("values", Long.class);
            // Deserialize buddies
            List<Long> buddyIdsList = deserializer.deserialize(request.getParameter(RequestParameterKeys.KEY_CONTENT));

            // We need to get rid of the duplicates
            buddyIdsSet = new HashSet<Long>(buddyIdsList);
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

        try {
            // Appends extra data like screen name, full name etc.
            List<Buddy> readBuddies = readBuddies(buddyIdsSet);

            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(readBuddies);

            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // No user found
        catch (NoSuchUserException e) {
            // Bad request
            ResponseUtil.writeResponse(
                    ErrorMessage.badRequest("Such user doesn't exits").serialize(), HttpStatus.BAD_REQUEST, response
            );
        }
        // General error
        catch (SystemException e) {
            // Server fault
            ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(e);
            }
        }
    }

    /**
     * Read buddies presences
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readPresences(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                // Authorized user
        Set<Long> buddyIdsSet;      // Set of deserialized buddies (no duplicates)

        // Deserialize
        try {
            // Get buddy from request
            buddy = Buddy.fromResourceRequest(request);
            // Create deserializer for a list of buddies
            JSONDeserializer<List<Long>> deserializer = JSONFactoryUtil.createJSONDeserializer();
            // Values must be numbers
            deserializer.use("values", Long.class);
            // Deserialize buddies
            List<Long> buddyIdsList = deserializer.deserialize(request.getParameter(RequestParameterKeys.KEY_CONTENT));

            // We need to get rid of the duplicates
            buddyIdsSet = new HashSet<Long>(buddyIdsList);
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

        // Check the length
        if (buddyIdsSet.size() > MAX_BUDDIES_COUNT) {
            // Compose error message
            String message = String.format("You wanted to read presence for %d users. However, only %d can be" +
                    "read at once.", buddyIdsSet.size(), MAX_BUDDIES_COUNT);
            // Tell client that the request entity is to large
            ResponseUtil.writeResponse(
                    ErrorMessage.requestEntityTooLarge(message).serialize(),
                    HttpStatus.REQUEST_ENTITY_TOO_LARGE,
                    response
            );
            // End here
            return;
        }

        // Read presences
        ReadBuddiesPresenceResponseEvent responseEvent = buddyCoreService.readBuddiesPresence(
                new ReadBuddiesPresenceRequestEvent(buddy.toBuddyDetails(), buddyIdsSet)
        );

        // Success
        if (responseEvent.isSuccess()) {
            ReadBuddiesPresenceResponseEvent.Status status = responseEvent.getStatus();
            // Success
            if (status == ReadBuddiesPresenceResponseEvent.Status.SUCCESS) {
                // Get buddies from response event
                List<Buddy> buddies = Buddy.fromBuddyDetailsList(responseEvent.getBuddies());

                // Serialize
                String serialized = JSONFactoryUtil.looseSerialize(buddies);

                // Write success to response
                ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
            }
            // Loading
            else {
                ResponseUtil.writeResponse(HttpStatus.PARTIAL_CONTENT, response);
            }
        }
        // Failure
        else {
            ReadBuddiesPresenceResponseEvent.Status status = responseEvent.getStatus();
            // Wrong params
            if (status == ReadBuddiesPresenceResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Forbidden
            else if (status == ReadBuddiesPresenceResponseEvent.Status.ERROR_FORBIDDEN) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // No session
            else if (status == ReadBuddiesPresenceResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(ErrorMessage.forbidden("User is not connected to Jabber").serialize(),
                        HttpStatus.FORBIDDEN, response);
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
     * Appends extra data from portal to the list of buddies
     *
     * @param buddies list of buddies
     * @return set of buddies
     * @throws SystemException
     * @throws NoSuchUserException
     */
    private List<Buddy> readBuddies(Set<Long> buddies) throws SystemException, NoSuchUserException {
        List<Buddy> readBuddies = new LinkedList<Buddy>();

        for (Long buddyId : buddies) {
            // Add extra info about user
            User user = UserLocalServiceUtil.fetchUser(buddyId);

            if (user == null) {
                throw new NoSuchUserException();
            }

            // Read user from portal
            readBuddies.add(Buddy.fromPortalUser(user));
        }

        return readBuddies;
    }
}
