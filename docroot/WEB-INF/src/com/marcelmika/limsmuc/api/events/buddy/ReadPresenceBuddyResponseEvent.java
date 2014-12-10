/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.buddy;

import com.marcelmika.limsmuc.api.entity.PresenceDetails;
import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 8:29 AM
 */
public class ReadPresenceBuddyResponseEvent extends ResponseEvent {

    private PresenceDetails presenceDetails;

    public static ReadPresenceBuddyResponseEvent success(String result, PresenceDetails presenceDetails) {
        ReadPresenceBuddyResponseEvent event = new ReadPresenceBuddyResponseEvent();
        event.result = result;
        event.success = true;
        event.presenceDetails = presenceDetails;

        return event;
    }

    public static ReadPresenceBuddyResponseEvent failure(Throwable exception) {
        ReadPresenceBuddyResponseEvent event = new ReadPresenceBuddyResponseEvent();
        event.result = exception.getMessage();
        event.success = false;
        event.exception = exception;

        return event;
    }

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }
}
