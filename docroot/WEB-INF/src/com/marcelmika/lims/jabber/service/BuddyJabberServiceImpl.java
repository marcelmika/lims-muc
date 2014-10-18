/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.api.events.buddy.*;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManager;
import com.marcelmika.lims.jabber.connection.manager.ConnectionManagerFactory;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.Presence;
import com.marcelmika.lims.jabber.session.UserSession;
import com.marcelmika.lims.jabber.session.store.UserSessionStore;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 7:25 PM
 */
public class BuddyJabberServiceImpl implements BuddyJabberService {

    // Dependencies
    private UserSessionStore userSessionStore;

    /**
     * BuddyJabberServiceImpl
     *
     * @param userSessionStore UserSessionStore
     */
    public BuddyJabberServiceImpl(final UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
    }

    /**
     * Connect buddy to the Jabber server
     *
     * @param event Request event for method
     * @return Response event for method
     */
    @Override
    public ConnectBuddyResponseEvent connectBuddy(ConnectBuddyRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        // We use buddy ID as a user identification
        Long buddyId = buddy.getBuddyId();
        Long companyId = buddy.getCompanyId();

        // buddyId and companyId cannot be null
        if (buddyId == null || companyId == null) {
            return ConnectBuddyResponseEvent.connectFailure(
                    "Cannot connect buddy without buddy id or company id", event.getDetails()
            );
        }

        // Create new connection manager (screen name is the ID)
        ConnectionManager connectionManager = ConnectionManagerFactory.buildManager();

        try {
            // Connect
            connectionManager.createConnection();
        } catch (JabberException e) {
            // Failure
            return ConnectBuddyResponseEvent.connectFailure(e.getMessage(), buddy.toBuddyDetails());
        }

        // Connection with jabber server was successfully created. Consequently, we should
        // create a session in memory
        UserSession userSession = UserSession.fromConnectionManager(buddyId, companyId, connectionManager);
        // Add user session to store so it can be queried later
        userSessionStore.addUserSession(userSession);


        // Success
        return ConnectBuddyResponseEvent.connectSuccess(
                "User " + buddy.getBuddyId() + " successfully created connection to jabber server.",
                buddy.toBuddyDetails()
        );
    }

    /**
     * Login buddy to Jabber
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return LoginBuddyResponseEvent.loginFailure(
                    LoginBuddyResponseEvent.Status.ERROR_JABBER,
                    new JabberException(String.format("Cannot find session for buddy %s",
                            event.getDetails().getScreenName()))
            );
        }
        // We need connection manager to login
        ConnectionManager connectionManager = userSession.getConnectionManager();

        try {
            // Login
            connectionManager.login(buddy);
        } catch (JabberException exception) {
            // Failure
            return LoginBuddyResponseEvent.loginFailure(
                    LoginBuddyResponseEvent.Status.ERROR_JABBER, exception);
        }

        // Success
        return LoginBuddyResponseEvent.loginSuccess(buddy.toBuddyDetails());
    }

    /**
     * Logout buddy from Jabber
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {
        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return LogoutBuddyResponseEvent.logoutFailure(
                    "Cannot find session for buddy.", event.getDetails()
            );
        }
        // We need connection manager to login
        ConnectionManager connectionManager = userSession.getConnectionManager();
        // Logout
        connectionManager.logout();
        // Destroy user session
        userSessionStore.removeUserSession(buddyId);

        // Success
        return LogoutBuddyResponseEvent.logoutSuccess(
                "User " + buddy.getBuddyId() + " successfully signed out",
                buddy.toBuddyDetails()
        );
    }

    /**
     * Change buddy's status
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event) {
        // We use buddy ID as an identification
        Long buddyId = event.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return UpdatePresenceBuddyResponseEvent.updatePresenceFailure(
                    UpdatePresenceBuddyResponseEvent.Status.ERROR_NO_SESSION
            );
        }
        // We need connection manager to login
        ConnectionManager connectionManager = userSession.getConnectionManager();
        // Map presence
        Presence presence = Presence.fromPresenceDetails(event.getPresenceDetails());

        try {
            // Set presence on server
            connectionManager.setPresence(presence.toSmackPresence());
            // Success
            return UpdatePresenceBuddyResponseEvent.updatePresenceSuccess();
        } catch (Exception exception) {
            // Failure
            return UpdatePresenceBuddyResponseEvent.updatePresenceFailure(
                    UpdatePresenceBuddyResponseEvent.Status.ERROR_JABBER, exception
            );
        }
    }

}
