/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.settings;

import com.marcelmika.limsmuc.api.events.ResponseEvent;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 17/01/15
 * Time: 21:03
 */
public class GetConnectedBuddiesResponseEvent extends ResponseEvent {

    private Status status;
    private List<Long> buddies;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetConnectedBuddiesResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetConnectedBuddiesResponseEvent success(List<Long> buddies) {
        GetConnectedBuddiesResponseEvent event = new GetConnectedBuddiesResponseEvent();

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
    public static GetConnectedBuddiesResponseEvent failure(final Status status) {
        GetConnectedBuddiesResponseEvent event = new GetConnectedBuddiesResponseEvent();

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
    public static GetConnectedBuddiesResponseEvent failure(final Status status,
                                                           final Throwable exception) {
        GetConnectedBuddiesResponseEvent event = new GetConnectedBuddiesResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public List<Long> getBuddies() {
        return buddies;
    }
}
