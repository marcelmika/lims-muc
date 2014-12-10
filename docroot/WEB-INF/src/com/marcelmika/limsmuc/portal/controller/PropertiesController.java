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
import com.marcelmika.limsmuc.portal.domain.Properties;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.portlet.PermissionDetector;
import com.marcelmika.limsmuc.portal.properties.PropertiesManager;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/10/14
 * Time: 12:16
 */
public class PropertiesController {

    // Log
    private static Log log = LogFactoryUtil.getLog(PropertiesController.class);

    // Dependencies
    PropertiesManager propertiesManager;

    /**
     * Constructor
     *
     * @param propertiesManager Preferences
     */
    public PropertiesController(final PropertiesManager propertiesManager) {
        this.propertiesManager = propertiesManager;
    }

    /**
     * Patches properties
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void patchProperties(ResourceRequest request, ResourceResponse response) {

        // Check if the user is an admin
        if (!PermissionDetector.isAdmin(request)) {
            ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            return;
        }

        // Check preferences
        if (request.getPreferences() == null) {
            ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            if (log.isErrorEnabled()) {
                log.error("Cannot find preferences in request. This shouldn't normally happen.");
            }
            return;
        }


        Properties properties;

        // Deserialize
        try {
            properties = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_CONTENT), Properties.class
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

        // Update preferences
        try {
            propertiesManager.updatePortletPreferences(request.getPreferences(), properties);
        }
        // Failure
        catch (Exception exception) {
            // This is a server fault
            ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
            // Log
            if (log.isErrorEnabled()) {
                log.error(exception);
            }
        }
    }
}

