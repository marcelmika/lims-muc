/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.synchronization;

import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 11:38
 */
public class SynchronizeSUCResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private SynchronizeSUCResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static SynchronizeSUCResponseEvent success() {
        SynchronizeSUCResponseEvent event = new SynchronizeSUCResponseEvent();

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
    public static SynchronizeSUCResponseEvent failure(final Status status) {
        SynchronizeSUCResponseEvent event = new SynchronizeSUCResponseEvent();

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
    public static SynchronizeSUCResponseEvent failure(final Status status,
                                                      final Throwable exception) {

        SynchronizeSUCResponseEvent event = new SynchronizeSUCResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

}
