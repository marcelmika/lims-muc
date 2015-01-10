/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.marcelmika.limsmuc.api.events.settings.TestConnectionRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.TestConnectionResponseEvent;
import com.marcelmika.limsmuc.jabber.connection.ConnectionManager;
import com.marcelmika.limsmuc.jabber.connection.ConnectionManagerFactory;
import com.marcelmika.limsmuc.jabber.exception.JabberException;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/01/15
 * Time: 21:53
 */
public class SettingsJabberServiceImpl implements SettingsJabberService {

    /**
     * Tests connection with the jabber server
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public TestConnectionResponseEvent testConnection(TestConnectionRequestEvent event) {
        // Create new connection manager (screen name is the ID)
        ConnectionManager connectionManager = ConnectionManagerFactory.buildManager();

        try {
            // Connect
            connectionManager.createConnection();
        }
        // Failure
        catch (JabberException exception) {
            return TestConnectionResponseEvent.failure(
                    TestConnectionResponseEvent.Status.ERROR_JABBER, exception
            );
        }

        // After the test logout the connection
        connectionManager.logout();

        // Success
        return TestConnectionResponseEvent.success();
    }
}
