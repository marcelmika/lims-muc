/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.buddy;

import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.entity.BuddyDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:40 PM
 */
public class DeleteBuddyResponseEvent extends ResponseEvent {

    private BuddyDetails details;

    public static DeleteBuddyResponseEvent failure(String result, BuddyDetails details) {
        DeleteBuddyResponseEvent event = new DeleteBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = false;

        return event;
    }

    public static DeleteBuddyResponseEvent success(String result, BuddyDetails details) {
        DeleteBuddyResponseEvent event = new DeleteBuddyResponseEvent();
        event.result = result;
        event.details = details;
        event.success = true;

        return event;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
