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
public class ReadBuddyPresenceResponseEvent extends ResponseEvent {

    private PresenceDetails presence;
    private Status status;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_PERSISTENCE,      // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private ReadBuddyPresenceResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static ReadBuddyPresenceResponseEvent success(final PresenceDetails presence) {
        ReadBuddyPresenceResponseEvent event = new ReadBuddyPresenceResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.presence = presence;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static ReadBuddyPresenceResponseEvent failure(final Status status) {
        ReadBuddyPresenceResponseEvent event = new ReadBuddyPresenceResponseEvent();

        event.success = false;
        event.status = status;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status    Status
     * @param exception Exception
     * @return ResponseEvent
     */
    public static ReadBuddyPresenceResponseEvent failure(final Status status,
                                                         final Throwable exception) {

        ReadBuddyPresenceResponseEvent event = new ReadBuddyPresenceResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public PresenceDetails getPresence() {
        return presence;
    }
}
