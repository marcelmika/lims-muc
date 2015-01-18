/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.session;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 17/01/15
 * Time: 20:48
 */
public class BuddySessionStoreImpl implements BuddySessionStore {

    private final Set<Long> buddySessions = Collections.synchronizedSet(new HashSet<Long>());

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(BuddySessionStoreImpl.class);

    /**
     * Adds a single buddy id to the store
     *
     * @param buddyId Long
     */
    @Override
    public void addBuddy(Long buddyId) {

        synchronized (buddySessions) {
            // Add a single buddy
            buddySessions.add(buddyId);
        }

        // Log
        if (log.isDebugEnabled()) {
            log.debug(String.format("Buddy [%d] added to store %s", buddyId, toString()));
        }
    }

    /**
     * Adds a list of buddy ids to the store
     *
     * @param buddies list of buddy ids
     */
    @Override
    public void addBuddies(List<Long> buddies) {

        synchronized (buddySessions) {
            // Clear the old values
            buddySessions.clear();
            // Add new ones
            buddySessions.addAll(buddies);
        }

        // Log
        if (log.isDebugEnabled()) {
            log.debug(String.format("Buddies added to store %s", toString()));
        }
    }

    /**
     * Removes buddy id from the session store
     *
     * @param buddyId Long
     */
    @Override
    public void removeBuddy(Long buddyId) {

        synchronized (buddySessions) {
            // Remove specific buddy
            buddySessions.remove(buddyId);
        }

        // Log
        if (log.isDebugEnabled()) {
            log.debug(String.format("Buddy removed [%d] off the store %s", buddyId, toString()));
        }
    }

    /**
     * Returns true if the buddy is shouldn't be allowed to get the session
     *
     * @param buddyId of the buddy
     * @return boolean
     */
    @Override
    public boolean isOverSessionLimit(Long buddyId) {

        synchronized (buddySessions) {

            // Buddy id is already registered in the buddy session store. It means that
            // the user was registered in the store thus he has the access.
            if (buddySessions.contains(buddyId)) {
                // User in not over the session limit
                return false;
            }

            // Store is already full. Deny the access.
            if (buddySessions.size() >= 2) {
                // TODO: Set to false to turn of the session mechanism
                return true;
            }

            // Add the user to the session store because the store is not full yet.
            buddySessions.add(buddyId);

            // User is allowed to obtain the session
            return false;
        }
    }

    @Override
    public String toString() {
        return String.format("{%s}", buddySessions);
    }
}
