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

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 8:29 AM
 */
public class ReadBuddyPresenceRequestEvent extends RequestEvent {

    private final BuddyDetails buddyDetails;

    public ReadBuddyPresenceRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }
}
