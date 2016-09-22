/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.websocket;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 18/09/16
 * Time: 17:53
 */
public class Server extends WebSocketServer {

    // Log
    private static final Log log = LogFactoryUtil.getLog(Server.class);

    /**
     * Constructor
     *
     * @param port Port on which the server should start
     */

    public Server(String hostname, int port) throws UnknownHostException {
        super(new InetSocketAddress(hostname,  port));

        // Log
        log.info("Websocket server started at: " + getAddress());
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        log.info("OPEN");
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        log.info("CLOSE");
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        log.info("MESSAGE");
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        log.info("ERROR", e);
    }
}
