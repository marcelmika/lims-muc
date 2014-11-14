/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.buddy;

import com.marcelmika.limsmuc.api.events.ResponseEvent;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:46 PM
 */
public class LogoutBuddyResponseEvent extends ResponseEvent {

    private BuddyDetails details;

    public static LogoutBuddyResponseEvent failure(String result, BuddyDetails details) {
        LogoutBuddyResponseEvent event = new LogoutBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    public static LogoutBuddyResponseEvent success(String result, BuddyDetails details) {
        LogoutBuddyResponseEvent event = new LogoutBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
