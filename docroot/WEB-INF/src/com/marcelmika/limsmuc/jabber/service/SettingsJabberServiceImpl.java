/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.marcelmika.limsmuc.api.entity.SettingsDetails;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.TestConnectionRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.TestConnectionResponseEvent;
import com.marcelmika.limsmuc.jabber.connection.ConnectionManager;
import com.marcelmika.limsmuc.jabber.connection.ConnectionManagerFactory;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.exception.JabberException;
import com.marcelmika.limsmuc.jabber.session.UserSession;
import com.marcelmika.limsmuc.jabber.session.store.UserSessionStore;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/01/15
 * Time: 21:53
 */
public class SettingsJabberServiceImpl implements SettingsJabberService {

    // Dependencies
    UserSessionStore userSessionStore;

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public SettingsJabberServiceImpl(final UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
    }

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

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event) {
        // Map buddy
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        SettingsDetails settings = event.getSettingsDetails();
        // We user buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);

        // No session
        if (userSession == null) {
            // Set jabber settings values
            settings.setJabberDisconnected(true);
            // Success
            return ReadSettingsResponseEvent.success(settings);
        }

        // Get the connection manager related to the user session
        ConnectionManager connectionManager = userSession.getConnectionManager();

        // Set jabber settings
        settings.setJabberDisconnected(!connectionManager.isAuthenticated());

        // Success
        return ReadSettingsResponseEvent.success(settings);
    }
}
