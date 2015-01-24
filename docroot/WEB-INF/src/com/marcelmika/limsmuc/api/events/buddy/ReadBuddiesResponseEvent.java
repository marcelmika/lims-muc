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

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/01/15
 * Time: 20:00
 */
public class ReadBuddiesResponseEvent extends ResponseEvent {

    private Status status;
    private List<BuddyDetails> buddies;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_FORBIDDEN,        // User is not allowed to perform this action
        ERROR_PERSISTENCE,      // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private ReadBuddiesResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static ReadBuddiesResponseEvent success(final List<BuddyDetails> buddies) {
        ReadBuddiesResponseEvent event = new ReadBuddiesResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.buddies = buddies;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static ReadBuddiesResponseEvent failure(final Status status) {
        ReadBuddiesResponseEvent event = new ReadBuddiesResponseEvent();

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
    public static ReadBuddiesResponseEvent failure(final Status status,
                                                   final Throwable exception) {

        ReadBuddiesResponseEvent event = new ReadBuddiesResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public List<BuddyDetails> getBuddies() {
        return buddies;
    }
}
