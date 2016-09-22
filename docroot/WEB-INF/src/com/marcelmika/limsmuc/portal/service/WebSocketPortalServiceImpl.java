/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.websocket.StartServerRequestEvent;
import com.marcelmika.limsmuc.api.events.websocket.StartServerResponseEvent;
import com.marcelmika.limsmuc.api.events.websocket.StopServerRequestEvent;
import com.marcelmika.limsmuc.api.events.websocket.StopServerResponseEvent;
import com.marcelmika.limsmuc.portal.websocket.ServerThread;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/16
 * Time: 13:42
 */
public class WebSocketPortalServiceImpl implements WebSocketPortalService {

    // Log
    private static final Log log = LogFactoryUtil.getLog(WebSocketPortalServiceImpl.class);

    // List of all web socket threads
    private List<ServerThread> threads = new LinkedList<ServerThread>();

    /**
     * Starts Web Socket Server
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    @Override
    public StartServerResponseEvent startServer(StartServerRequestEvent event) {
        try {
            // Start the server
            start(event.getHostname(), event.getPort());

            // Success
            return StartServerResponseEvent.success();
        }
        // Failure
        catch (Exception e) {
            return StartServerResponseEvent.failure(StartServerResponseEvent.Status.ERROR, e);
        }
    }

    /**
     * Stops Web Socket Server
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    @Override
    public StopServerResponseEvent stopServer(StopServerRequestEvent event) {
        try {
            // Stop the server
            stop();

            // Success
            return StopServerResponseEvent.success();
        }
        // Failure
        catch (Exception e) {
            return StopServerResponseEvent.failure(StopServerResponseEvent.Status.ERROR, e);
        }
    }

    /**
     * Starts the web socket server
     */
    private void start(String hostname, Integer port) {
        // Stop the service first
        stop();

        // Create new websocket thread
        ServerThread thread = new ServerThread(hostname, port);

        // Add it to list so we can interrupt it in the future
        threads.add(thread);

        // Start the server thread
        thread.start();
    }

    /**
     * Stops the web socket server
     */
    private void stop() {
        for (ServerThread thread : threads) {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }
    }
}
