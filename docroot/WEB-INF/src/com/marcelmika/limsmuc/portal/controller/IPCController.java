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
