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
import com.marcelmika.limsmuc.api.entity.PresenceDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 06/02/16
 * Time: 11:10
 */
public class PresenceChangeBusEvent {

    private BuddyDetails buddy;
    private PresenceDetails presence;

    public PresenceChangeBusEvent(final BuddyDetails buddy, final PresenceDetails presence) {
        this.buddy = buddy;
        this.presence = presence;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public PresenceDetails getPresence() {
        return presence;
    }

    @Override
    public String toString() {
        return "PresenceChangeBusEvent{" +
                "buddy=" + buddy +
                ", presence=" + presence +
                '}';
    }
}
