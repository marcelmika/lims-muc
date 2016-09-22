/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/16
 * Time: 13:30
 */
public class WebSocketCoreServiceUtil {

    private static WebSocketCoreService webSocketCoreService;

    /**
     * Returns WebSocketCoreService implementation
     *
     * @return WebSocketCoreService
     */
    public static WebSocketCoreService getWebSocketCoreService() {
        return webSocketCoreService;
    }

    /**
     * Injects implementation of WebSocketCoreService
     *
     * @param webSocketCoreService WebSocketCoreService
     */
    public void setWebSocketCoreService(WebSocketCoreService webSocketCoreService) {
        WebSocketCoreServiceUtil.webSocketCoreService = webSocketCoreService;
    }
}
