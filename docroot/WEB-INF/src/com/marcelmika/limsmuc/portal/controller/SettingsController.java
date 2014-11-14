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
import com.marcelmika.limsmuc.api.events.settings.*;
import com.marcelmika.limsmuc.core.service.SettingsCoreService;
import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.Settings;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:32 PM
 */
public class SettingsController {

    // Log
    private static Log log = LogFactoryUtil.getLog(SettingsController.class);

    // Dependencies
    SettingsCoreService settingsCoreService;

    /**
     * Constructor
     *
     * @param settingsCoreService SettingsCoreService
     */
    public SettingsController(final SettingsCoreService settingsCoreService) {
        this.settingsCoreService = settingsCoreService;
    }

    /**
     * Reads users settings
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readSettings(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;        // Authorized user

        // Deserialize
        try {
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

        // Update connection
        ReadSettingsResponseEvent responseEvent = settingsCoreService.readSettings(
                new ReadSettingsRequestEvent(buddy.toBuddyDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map settings
            Settings settings = Settings.fromSettingsDetails(responseEvent.getSettingsDetails());
            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(settings);
            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            ReadSettingsResponseEvent.Status status = responseEvent.getStatus();
            // Everything else is a server fault
            if (status == ReadSettingsResponseEvent.Status.ERROR_PERSISTENCE) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            }
            // Log
            if (log.isErrorEnabled()) {
                log.error(responseEvent.getException());
            }
        }
    }

    /**
     * Update buddy's settings
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void updateSettings(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;        // Authorized user
        Settings settings;  // Settings that should be updated

        // Deserialize
        try {
            // Request content
            String content = request.getParameter(RequestParameterKeys.KEY_CONTENT);
            // Settings
            settings = JSONFactoryUtil.looseDeserialize(content, Settings.class);
            // Buddy
            buddy = Buddy.fromResourceRequest(request);
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            log.error(exception);
            // End here
            return;
        }

        // Send request to core service
        UpdateSettingsResponseEvent responseEvent = settingsCoreService.updateSettings(
                new UpdateSettingsRequestEvent(buddy.getBuddyId(), settings.toSettingsDetails())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            UpdateSettingsResponseEvent.Status status = responseEvent.getStatus();
            // Bad parameters
            if (status == UpdateSettingsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                log.error(responseEvent.getException());
            }
        }
    }

    /**
     * Updates buddy's active panel
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void updateActivePanel(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;        // Authorized user
        Settings settings;  // Active panel is a part of settings

        // Deserialize
        try {
            // Request content
            String content = request.getParameter(RequestParameterKeys.KEY_CONTENT);
            // Settings
            settings = JSONFactoryUtil.looseDeserialize(content, Settings.class);
            // Buddy
            buddy = Buddy.fromResourceRequest(request);
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            log.debug(exception);
            // End here
            return;
        }

        // Send request to core service
        UpdateActivePanelResponseEvent responseEvent = settingsCoreService.updateActivePanel(
                new UpdateActivePanelRequestEvent(buddy.getBuddyId(), settings.getActivePanelId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            UpdateActivePanelResponseEvent.Status status = responseEvent.getStatus();
            // Bad Request
            if (status == UpdateActivePanelResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                log.error(responseEvent.getException());
            }
        }
    }
}
