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

import java.util.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:42 PM
 */
public class UserSessionStoreImpl implements UserSessionStore {

    // Map of Connection Managers
    private Map<Long, UserSession> connectionManagerMap = new HashMap<Long, UserSession>();

    // User session listeners
    private List<UserSessionStoreListener> userSessionStoreListeners = Collections.synchronizedList(
            new ArrayList<UserSessionStoreListener>()
    );


    /**
     * Registers user session lister
     *
     * @param listener UserSessionStoreListener
     */
    @Override
    public void addUserSessionStoreListener(UserSessionStoreListener listener) {
        userSessionStoreListeners.add(listener);
    }

    /**
     * Returns stored user session
     *
     * @param id of the user session
     * @return UserSession
     */
    @Override
    public UserSession getUserSession(Long id) {
        return connectionManagerMap.get(id);
    }

    /**
     * Removes user session from the store
     *
     * @param id of the user session
     */
    @Override
    public void removeUserSession(Long id) {
        // Remove session from the store
        connectionManagerMap.remove(id);
        // Callback
        for (UserSessionStoreListener listener : userSessionStoreListeners) {
            listener.userSessionRemoved(id);
        }
    }

    /**
     * Adds user session to the store
     *
     * @param userSession UserSession
     */
    @Override
    public void addUserSession(UserSession userSession) {
        // Add user session to map
        connectionManagerMap.put(userSession.getSessionId(), userSession);
        // Callback
        for (UserSessionStoreListener listener : userSessionStoreListeners) {
            listener.userSessionAdded(userSession);
        }
    }

    /**
     * Returns true if the store contains user session
     *
     * @param id of the user session
     * @return true if the store contains user session
     */
    @Override
    public boolean containsUserSession(Long id) {
        return connectionManagerMap.containsKey(id);
    }
}
