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
import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:40 PM
 */
public class DeleteBuddyResponseEvent extends ResponseEvent {

    private Status status;
    private BuddyDetails details;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_PERSISTENCE,      // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private DeleteBuddyResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static DeleteBuddyResponseEvent success(final BuddyDetails buddyDetails) {
        DeleteBuddyResponseEvent event = new DeleteBuddyResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.details = buddyDetails;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static DeleteBuddyResponseEvent failure(final Status status) {
        DeleteBuddyResponseEvent event = new DeleteBuddyResponseEvent();

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
    public static DeleteBuddyResponseEvent failure(final Status status,
                                                   final Throwable exception) {

        DeleteBuddyResponseEvent event = new DeleteBuddyResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public BuddyDetails getDetails() {
        return details;
    }
}
