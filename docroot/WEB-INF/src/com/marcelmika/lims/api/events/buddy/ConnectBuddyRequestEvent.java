/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.buddy;

import com.marcelmika.lims.api.events.RequestEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 12:28 PM
 */
public class ConnectBuddyRequestEvent extends RequestEvent {

    private final BuddyDetails details;

    public ConnectBuddyRequestEvent(BuddyDetails details) {
        this.details = details;
    }

    public BuddyDetails getDetails() {
        return details;
    }

}
