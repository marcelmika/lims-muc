/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.buddy;

import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.events.RequestEvent;

import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/01/15
 * Time: 19:59
 */
public class ReadBuddiesPresenceRequestEvent extends RequestEvent {

    private final BuddyDetails buddy;
    private final Set<Long> buddies;

    public ReadBuddiesPresenceRequestEvent(BuddyDetails buddy, Set<Long> buddies) {
        this.buddy = buddy;
        this.buddies = buddies;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public Set<Long> getBuddies() {
        return buddies;
    }
}
