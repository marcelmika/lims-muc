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
import com.marcelmika.limsmuc.portal.domain.ServerTime;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 15/09/14
 * Time: 15:35
 */
public class ServerController {

    // Log
    private static Log log = LogFactoryUtil.getLog(ServerController.class);


    /**
     * Returns current server time
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void getServerTime(ResourceRequest request, ResourceResponse response) {

        // Get current server time
        Date time = Calendar.getInstance().getTime();

        // Store in object
        ServerTime serverTime = new ServerTime();
        serverTime.setTime(time);

        // Serialize
        String serializedTime = JSONFactoryUtil.looseSerialize(serverTime);

        // Write to response
        ResponseUtil.writeResponse(serializedTime, HttpStatus.OK, response);
    }
}
