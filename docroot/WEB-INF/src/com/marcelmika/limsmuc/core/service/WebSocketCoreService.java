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

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/16
 * Time: 13:30
 */
public interface WebSocketCoreService {

    /**
     * Starts Web Socket Server
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    StartServerResponseEvent startServer(StartServerRequestEvent event);

    /**
     * Stops Web Socket Server
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    StopServerResponseEvent stopServer(StopServerRequestEvent event);
}
