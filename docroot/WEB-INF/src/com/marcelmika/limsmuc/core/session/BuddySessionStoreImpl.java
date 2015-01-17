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
    }

    /**
     * Returns true if the buddy is shouldn't be allowed to get the session
     *
     * @param buddyId of the buddy
     * @return boolean
     */
    @Override
    public boolean isOverSessionLimit(Long buddyId) {
        // If the buddy has already the session
        if (buddySessions.contains(buddyId)) {
            // He is not over the session limit
            return false;
        }
        
        // Buddy is not allowed to obtain the session
        if (buddySessions.size() >= 2) {
            // TODO: Set to false to turn of the session mechanism
            return true;
        }

        // User is allowed to obtain the session
        return false;
    }

    @Override
    public String toString() {
        return "Buddy session list{" +
                "buddies=" + buddySessions +
                '}';
    }
}
