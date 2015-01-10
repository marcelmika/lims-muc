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

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/01/15
 * Time: 21:45
 */
public class TestConnectionResponseEvent extends ResponseEvent {

    private String message;
    private Status status;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_JABBER,           // Error with jabber occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private TestConnectionResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static TestConnectionResponseEvent success() {
        TestConnectionResponseEvent event = new TestConnectionResponseEvent();

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
    public static TestConnectionResponseEvent failure(final Status status) {
        TestConnectionResponseEvent event = new TestConnectionResponseEvent();

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
    public static TestConnectionResponseEvent failure(final Status status,
                                                      final Throwable exception) {

        TestConnectionResponseEvent event = new TestConnectionResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        if (exception != null) {
            event.message = exception.getLocalizedMessage();
        }

        return event;
    }

    public String getMessage() {
        return message;
    }

    public Status getStatus() {
        return status;
    }

}
