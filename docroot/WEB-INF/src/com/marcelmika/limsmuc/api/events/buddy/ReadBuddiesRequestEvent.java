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

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/01/15
 * Time: 19:59
 */
public class ReadBuddiesRequestEvent extends RequestEvent {

    private final BuddyDetails buddy;
    private final List<BuddyDetails> buddies;

    public ReadBuddiesRequestEvent(BuddyDetails buddy, List<BuddyDetails> buddies) {
        this.buddy = buddy;
        this.buddies = buddies;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public List<BuddyDetails> getBuddies() {
        return buddies;
    }
}
