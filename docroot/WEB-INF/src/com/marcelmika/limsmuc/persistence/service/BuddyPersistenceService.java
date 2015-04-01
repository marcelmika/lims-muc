/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.marcelmika.limsmuc.api.events.buddy.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:55 PM
 */
public interface BuddyPersistenceService {

    /**
     * Login buddy to System
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event);

    /**
     * Logout buddy from System
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event);

    /**
     * Completely removes buddy from Persistence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event);

    /**
     * Reads presence of a single buddy
     *
     * @param event Request event
     * @return Response event
     */
    ReadBuddyPresenceResponseEvent readBuddyPresence(ReadBuddyPresenceRequestEvent event);

    /**
     * Reads presence of buddies
     *
     * @param event Request event
     * @return Response event
     */
    ReadBuddiesPresenceResponseEvent readBuddiesPresence(ReadBuddiesPresenceRequestEvent event);

    /**
     * Change buddy's presence
     *
     * @param event Request event
     * @return Response event
     */
    UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event);

    /**
     * Search buddies in the system
     *
     * @param event Request event
     * @return Response event
     */
    SearchBuddiesResponseEvent searchBuddies(SearchBuddiesRequestEvent event);

    /**
     * Reads buddies that have changed their presence since the particular time
     *
     * @param event Request event
     * @return Response event
     */
    ReadPresenceChangeResponseEvent readPresenceChange(ReadPresenceChangeRequestEvent event);

}
