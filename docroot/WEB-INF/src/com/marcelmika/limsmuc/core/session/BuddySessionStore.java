/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.session;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 17/01/15
 * Time: 20:47
 */
public interface BuddySessionStore {

    /**
     * Adds a list of buddy ids to the store
     *
     * @param buddies list of buddy ids
     */
    public void addBuddies(List<Long> buddies);

    /**
     * Returns true if the buddy is shouldn't be allowed to get the session
     *
     * @param buddyId of the buddy
     * @return boolean
     */
    public boolean isOverSessionLimit(Long buddyId);

}
