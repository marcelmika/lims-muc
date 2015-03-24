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
import com.marcelmika.limsmuc.api.events.group.GetGroupRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupResponseEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.limsmuc.core.service.GroupCoreService;
import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.Group;
import com.marcelmika.limsmuc.portal.domain.GroupCollection;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.request.parameters.GetGroupListParameters;
import com.marcelmika.limsmuc.portal.request.parameters.GetGroupParameters;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:33 PM
 */
public class GroupController {

    // Log
    private static Log log = LogFactoryUtil.getLog(GroupController.class);

    // Dependencies
    GroupCoreService groupCoreService;

    /**
     * Constructor
     *
     * @param groupCoreService GroupCoreService
     */
    public GroupController(final GroupCoreService groupCoreService) {
        this.groupCoreService = groupCoreService;
    }

    /**
     * Fetches all groups related to the buddy.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void getGroupList(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                        // Authorized user
        GetGroupListParameters parameters;  // Parameters for the request

        // Deserialize
        try {
            // Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), GetGroupListParameters.class
            );

            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);
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

        // Get groups
        GetGroupsResponseEvent responseEvent = groupCoreService.getGroups(
                new GetGroupsRequestEvent(buddy.toBuddyDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map group collection
            GroupCollection groupCollection = GroupCollection.fromGroupCollectionDetails(
                    responseEvent.getGroupCollection()
            );

            // ... and compare it with group collection etag
            // Cached
            if (parameters.getEtag().equals(groupCollection.getEtag())) {
                // Etags equal which means that nothing has changed
                ResponseUtil.writeResponse(HttpStatus.NOT_MODIFIED, response);
            }
            // Not cached
            else {
                // Serialize
                String serialized = JSONFactoryUtil.looseSerialize(groupCollection, "groups", "groups.buddies");
                // Etags are different which means that groups were modified
                // Send the whole package to the client
                ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
            }
        }
        // Failure
        else {
            GetGroupsResponseEvent.Status status = responseEvent.getStatus();
            // Bad request
            if (status == GetGroupsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // No session
            if (status == GetGroupsResponseEvent.Status.ERROR_NO_SESSION) {
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
     * Fetches given group
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void getGroup(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                    // Authorized user
        GetGroupParameters parameters;  // Parameters for the request

        // Deserialize
        try {
            // Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), GetGroupParameters.class
            );

            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);
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

        // Get group
        GetGroupResponseEvent responseEvent = groupCoreService.getGroup(
                new GetGroupRequestEvent(
                        buddy.toBuddyDetails(),
                        parameters.getGroupId(),
                        parameters.getListStrategy(),
                        parameters.getNumber()
                )
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map group
            Group group = Group.fromGroupDetails(responseEvent.getGroup());
            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(group, "buddies");
            // Write to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            GetGroupResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == GetGroupResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Bad request
            else if (status == GetGroupResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // No session
            else if (status == GetGroupResponseEvent.Status.ERROR_NO_SESSION) {
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
}
