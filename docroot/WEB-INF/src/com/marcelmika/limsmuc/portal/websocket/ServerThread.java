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

import java.net.UnknownHostException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.InterruptibleChannel;
import java.nio.channels.Selector;

/**
 * Thread which holds web socket server
 */
public class ServerThread extends Thread {

    // Properties
    private Integer port;
    private String hostname;
    private Server server;

    // Log
    private static final Log log = LogFactoryUtil.getLog(ServerThread.class);

    /**
     * Constructor
     *
     * @param hostname Server host name
     * @param port     Server port
     */
    public ServerThread(String hostname, Integer port) {
        // Call super to create a thread
        super();
        // Pass parameters
        this.hostname = hostname;
        this.port = port;
    }

    /**
     * Runs this thread
     *
     * @see #start()
     * @see #stop()
     */
    @Override
    public void run() {
        initializeWebSocket();
    }

    /**
     * Interrupts this thread.
     */
    @Override
    public void interrupt() {
        shutdownWebSocket();
        super.interrupt();
    }

    /**
     * Initializes web socket server
     */
    private void initializeWebSocket() {
        // Log
        log.info(String.format("Initializing web socket server %s:%s ", hostname, port));

        try {
            // Create echo server
            server = new Server(hostname, port);
            // Start the server
            server.start();

        } catch (UnknownHostException e) {
            log.error("Websocket server cannot be initialized", e);
        }
    }

    /**
     * Shuts down the web socket server
     */
    private void shutdownWebSocket() {
        try {
            // Stop the server
            server.stop();

            // Log
            log.info(String.format("Web socket server %s:%s stopped", hostname, port));
        } catch (Exception e) {
            log.error("Websocket server cannot be stopped", e);
        }
    }
}
