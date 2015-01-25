/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.controller;

import com.liferay.portal.kernel.exception.PortalException;
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
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.LinkedList;
import java.util.List;

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

        List<Long> buddies;    // List of deserialized buddies

        // Deserialize
        try {
            // Create deserializer for a list of buddies
            JSONDeserializer<List<Long>> deserializer = JSONFactoryUtil.createJSONDeserializer();
            // Values must be numbers
            deserializer.use("values", Long.class);
            // Deserialize buddies
            buddies = deserializer.deserialize(request.getParameter(RequestParameterKeys.KEY_CONTENT));
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
            List<Buddy> readBuddies = readBuddies(buddies);

            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(readBuddies);

            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        catch (Exception e) {
            // Log
            if (log.isErrorEnabled()) {
                log.error(e);
            }
            // Return error
            ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
        }
    }

    /**
     * Read buddies presences
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readPresences(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;            // Authorized user
        List<Long> buddyIds;    // List of deserialized buddies

        // Deserialize
        try {
            // Get buddy from request
            buddy = Buddy.fromResourceRequest(request);
            // Create deserializer for a list of buddies
            JSONDeserializer<List<Long>> deserializer = JSONFactoryUtil.createJSONDeserializer();
            // Values must be numbers
            deserializer.use("values", Long.class);
            // Deserialize buddies
            buddyIds = deserializer.deserialize(request.getParameter(RequestParameterKeys.KEY_CONTENT));
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
        if (buddyIds.size() > MAX_BUDDIES_COUNT) {
            // Tell client that the request entity is to large
            ResponseUtil.writeResponse(HttpStatus.REQUEST_ENTITY_TOO_LARGE, response);
            // End here
            return;
        }

        // Read presences
        ReadBuddiesPresenceResponseEvent responseEvent = buddyCoreService.readBuddiesPresence(
                new ReadBuddiesPresenceRequestEvent(buddy.toBuddyDetails(), buddyIds)
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Get buddies from response event
            List<Buddy> buddies = Buddy.fromBuddyDetailsList(responseEvent.getBuddies());

            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(buddies);

            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
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
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }


    /**
     * Appends extra data from portal to the list of buddies
     *
     * @param buddies list of buddies
     * @return list of buddies
     * @throws SystemException
     * @throws PortalException
     */
    private List<Buddy> readBuddies(List<Long> buddies) throws SystemException, PortalException {
        List<Buddy> readBuddies = new LinkedList<Buddy>();

        for (Long buddyId : buddies) {
            // Add extra info about user
            User user = UserLocalServiceUtil.getUser(buddyId);

            // Read user from portal
            readBuddies.add(Buddy.fromPortalUser(user));
        }

        return readBuddies;
    }
}
