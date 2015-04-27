/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.marcelmika.limsmuc.api.events.buddy.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:22 PM
 */
public interface BuddyJabberService {

    /**
     * Connect buddy to the Jabber server
     *
     * @param event Request event for method
     * @return Response event for method
     */
    ConnectBuddyResponseEvent connectBuddy(ConnectBuddyRequestEvent event);

    /**
     * Login buddy to Jabber
     *
     * @param event Request event for method
     * @return Response event for method
     */
    LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event);

    /**
     * Logout buddy from Jabber
     *
     * @param event Request event for method
     * @return Response event for method
     */
    LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event);

    /**
     * Change buddy's status
     *
     * @param event Request event for method
     * @return Response event for method
     */
    UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event);

    /**
     * Updates buddy's password
     *
     * @param event Request event
     * @return Response event
     */
    UpdatePasswordResponseEvent updatePassword(UpdatePasswordRequestEvent event);

    /**
     * Search buddies in the system
     *
     * @param event Request event
     * @return Response event
     */
    SearchBuddiesResponseEvent searchBuddies(SearchBuddiesRequestEvent event);

    /**
     * Reads presence of buddies
     *
     * @param event Request event
     * @return Response event
     */
    ReadBuddiesPresenceResponseEvent readBuddiesPresence(ReadBuddiesPresenceRequestEvent event);

}
