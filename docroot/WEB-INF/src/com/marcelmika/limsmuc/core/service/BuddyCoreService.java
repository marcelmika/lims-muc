/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.marcelmika.limsmuc.api.events.buddy.DeleteBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.DeleteBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.LogoutBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.LogoutBuddyResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddiesPresenceRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadBuddiesPresenceResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadPresenceChangeRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadPresenceChangeResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.SearchBuddiesRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.SearchBuddiesResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePasswordRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePasswordResponseEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePresenceBuddyResponseEvent;

/**
 * Serves as a port to the business logic related to buddy.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public interface BuddyCoreService {

    /**
     * Login buddy to System
     *
     * @param event Request event
     * @return Response event
     */
    LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event);

    /**
     * Logout buddy from System
     *
     * @param event Request event
     * @return Response event
     */
    LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event);

    /**
     * Completely removes buddy from the System
     *
     * @param event Request event
     * @return Response event
     */
    DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event);

    /**
     * Update buddy's presence
     *
     * @param event Request event
     * @return Response event
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

    /**
     * Reads buddies that have changed their presence since the particular time
     *
     * @param event Request event
     * @return Response event
     */
    ReadPresenceChangeResponseEvent readPresenceChange(ReadPresenceChangeRequestEvent event);

}
