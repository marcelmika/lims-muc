/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.controller;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCRequestEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCResponseEvent;
import com.marcelmika.limsmuc.core.service.SynchronizationCoreService;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.portlet.PermissionDetector;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 16/11/14
 * Time: 17:45
 */
public class SynchronizationController {

    // Log
    private static Log log = LogFactoryUtil.getLog(SynchronizationController.class);

    // Dependencies
    SynchronizationCoreService synchronizationCoreService;

    /**
     * Constructor
     *
     * @param synchronizationCoreService SynchronizationCoreService
     */
    public SynchronizationController(SynchronizationCoreService synchronizationCoreService) {
        this.synchronizationCoreService = synchronizationCoreService;
    }


    /**
     * Synchronizes with SUC
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void synchronizeSUC(ResourceRequest request, ResourceResponse response) {

        // Check if the user is an admin
        if (!PermissionDetector.isAdmin(request)) {
            ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            return;
        }

        // Start the synchronization
        SynchronizeSUCResponseEvent responseEvent = synchronizationCoreService.synchronizeSUC(
                new SynchronizeSUCRequestEvent(true) // force synchronization
        );

        // Get the status
        SynchronizeSUCResponseEvent.Status status = responseEvent.getStatus();

        // Success
        if (responseEvent.isSuccess()) {
            // Success
            if (status == SynchronizeSUCResponseEvent.Status.SUCCESS) {
                ResponseUtil.writeResponse(HttpStatus.OK, response);
            }
            // In progress
            else {
                ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
            }
        }
        // Failure
        else {
            // Error during sync
            if (status == SynchronizeSUCResponseEvent.Status.ERROR_PERSISTENCE) {
                ResponseUtil.writeResponse(HttpStatus.NOT_MODIFIED, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
        }
    }
}
