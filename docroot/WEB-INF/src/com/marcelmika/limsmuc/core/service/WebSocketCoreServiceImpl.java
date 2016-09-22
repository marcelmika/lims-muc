/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.marcelmika.limsmuc.api.events.websocket.StartServerRequestEvent;
import com.marcelmika.limsmuc.api.events.websocket.StartServerResponseEvent;
import com.marcelmika.limsmuc.api.events.websocket.StopServerRequestEvent;
import com.marcelmika.limsmuc.api.events.websocket.StopServerResponseEvent;
import com.marcelmika.limsmuc.portal.service.WebSocketPortalService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/16
 * Time: 13:30
 */
public class WebSocketCoreServiceImpl implements WebSocketCoreService {

    // Dependencies
    private WebSocketPortalService webSocketPortalService;

    /**
     * Constructor
     *
     * @param webSocketPortalService WebSocketPortalService
     */
    public WebSocketCoreServiceImpl(WebSocketPortalService webSocketPortalService) {
        this.webSocketPortalService = webSocketPortalService;
    }

    /**
     * Starts Web Socket Server
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    @Override
    public StartServerResponseEvent startServer(StartServerRequestEvent event) {
        return webSocketPortalService.startServer(event);
    }

    /**
     * Stops Web Socket Server
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    @Override
    public StopServerResponseEvent stopServer(StopServerRequestEvent event) {
        return webSocketPortalService.stopServer(event);
    }
}
