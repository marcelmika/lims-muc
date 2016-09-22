/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.websocket;

import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/16
 * Time: 13:32
 */
public class StartServerResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        SUCCESS,        // Event was successfull
        ERROR,          // Error occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private StartServerResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static StartServerResponseEvent success() {
        StartServerResponseEvent event = new StartServerResponseEvent();

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
    public static StartServerResponseEvent failure(final Status status) {
        StartServerResponseEvent event = new StartServerResponseEvent();

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
    public static StartServerResponseEvent failure(final Status status,
                                                   final Throwable exception) {
        StartServerResponseEvent event = new StartServerResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }
}
