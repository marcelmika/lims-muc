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

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class UpdatePresenceBuddyResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_NO_SESSION,       // User does not have a session
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_JABBER,           // Error with jabber occurred
        ERROR_PERSISTENCE,      // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private UpdatePresenceBuddyResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static UpdatePresenceBuddyResponseEvent success() {
        UpdatePresenceBuddyResponseEvent event = new UpdatePresenceBuddyResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static UpdatePresenceBuddyResponseEvent failure(final Status status) {
        UpdatePresenceBuddyResponseEvent event = new UpdatePresenceBuddyResponseEvent();

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
    public static UpdatePresenceBuddyResponseEvent failure(final Status status,
                                                           final Throwable exception) {

        UpdatePresenceBuddyResponseEvent event = new UpdatePresenceBuddyResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }
}
