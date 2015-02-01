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
 * Date: 03/01/15
 * Time: 10:09
 */
public class UpdatePasswordRequestEvent extends RequestEvent {

    private BuddyDetails buddy;

    public UpdatePasswordRequestEvent(BuddyDetails buddy) {
        this.buddy = buddy;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }
}
