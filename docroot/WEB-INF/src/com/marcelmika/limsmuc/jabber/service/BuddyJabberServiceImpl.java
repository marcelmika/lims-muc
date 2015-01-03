/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.marcelmika.limsmuc.jabber.exception.JabberException;
import com.marcelmika.limsmuc.jabber.connection.manager.ConnectionManager;
import com.marcelmika.limsmuc.jabber.connection.manager.ConnectionManagerFactory;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.Presence;
import com.marcelmika.limsmuc.jabber.session.UserSession;
import com.marcelmika.limsmuc.jabber.session.store.UserSessionStore;
import com.marcelmika.limsmuc.api.events.buddy.*;

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
            return ConnectBuddyResponseEvent.failure(
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
            return ConnectBuddyResponseEvent.failure(e.getMessage(), buddy.toBuddyDetails());
        }

        // Connection with jabber server was successfully created. Consequently, we should
        // create a session in memory
        UserSession userSession = UserSession.fromConnectionManager(buddyId, companyId, connectionManager);
        // Add user session to store so it can be queried later
        userSessionStore.addUserSession(userSession);


        // Success
        return ConnectBuddyResponseEvent.success(
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
            return LoginBuddyResponseEvent.failure(
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
            return LoginBuddyResponseEvent.failure(
                    LoginBuddyResponseEvent.Status.ERROR_JABBER, exception);
        }

        // Success
        return LoginBuddyResponseEvent.success(buddy.toBuddyDetails());
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
            return LogoutBuddyResponseEvent.failure(
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
        return LogoutBuddyResponseEvent.success(
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
            return UpdatePresenceBuddyResponseEvent.failure(
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
            return UpdatePresenceBuddyResponseEvent.success();
        } catch (Exception exception) {
            // Failure
            return UpdatePresenceBuddyResponseEvent.failure(
                    UpdatePresenceBuddyResponseEvent.Status.ERROR_JABBER, exception
            );
        }
    }

    /**
     * Updates buddy's password
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public UpdatePasswordResponseEvent updatePassword(UpdatePasswordRequestEvent event) {

        // Get parameters
        Long buddyId = event.getBuddy().getBuddyId();
        String password = event.getBuddy().getPassword();

        // Check parameters
        if (buddyId == null || password == null) {
            return UpdatePasswordResponseEvent.failure(
                    UpdatePasswordResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return UpdatePasswordResponseEvent.failure(
                    UpdatePasswordResponseEvent.Status.ERROR_NO_SESSION
            );
        }

        // We need the jabber connection manager to update the password
        ConnectionManager connectionManager = userSession.getConnectionManager();

        try {
            // Update password
            connectionManager.updatePassword(password);
            // Success
            return UpdatePasswordResponseEvent.success();
        }
        // Failure
        catch (JabberException e) {
            return UpdatePasswordResponseEvent.failure(
                    UpdatePasswordResponseEvent.Status.ERROR_JABBER
            );
        }
    }

}
