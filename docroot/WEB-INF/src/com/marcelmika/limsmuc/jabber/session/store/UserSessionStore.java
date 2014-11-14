/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.session.store;

import com.marcelmika.limsmuc.jabber.session.UserSession;

/**
 * User Session Store holds all user sessions
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:41 PM
 */
public interface UserSessionStore {

    /**
     * Registers user session lister
     *
     * @param listener UserSessionStoreListener
     */
    public void addUserSessionStoreListener(UserSessionStoreListener listener);

    /**
     * Returns stored user session
     *
     * @param id of the user session
     * @return UserSession
     */
    public UserSession getUserSession(Long id);

    /**
     * Removes user session from the store
     *
     * @param id of the user session
     */
    public void removeUserSession(Long id);

    /**
     * Adds user session to the store
     *
     * @param userSession UserSession
     */
    public void addUserSession(UserSession userSession);

    /**
     * Returns true if the store contains user session
     *
     * @param id of the user session
     * @return true if the store contains user session
     */
    public boolean containsUserSession(Long id);
}
