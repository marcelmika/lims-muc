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
 * Date: 03/01/15
 * Time: 10:09
 */
public class UpdatePasswordResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_NOT_FOUND, // Such user was not found
        ERROR_NO_SESSION, // User does not have a session
        ERROR_JABBER, // Error with jabber occurred
        ERROR_WRONG_PARAMETERS // Wrong input parameters
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private UpdatePasswordResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static UpdatePasswordResponseEvent success() {
        UpdatePasswordResponseEvent event = new UpdatePasswordResponseEvent();

        event.status = Status.SUCCESS;
        event.success = true;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static UpdatePasswordResponseEvent failure(final Status status) {
        UpdatePasswordResponseEvent event = new UpdatePasswordResponseEvent();

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
    public static UpdatePasswordResponseEvent failure(final Status status,
                                                      final Throwable exception) {

        UpdatePasswordResponseEvent event = new UpdatePasswordResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

}
