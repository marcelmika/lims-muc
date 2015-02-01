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
 * Date: 17/01/15
 * Time: 22:16
 */
public class ReadSessionLimitResponseEvent extends ResponseEvent {

    private Status status;
    private boolean overLimit;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR,   // Error occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private ReadSessionLimitResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static ReadSessionLimitResponseEvent success(boolean overLimit) {
        ReadSessionLimitResponseEvent event = new ReadSessionLimitResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.overLimit = overLimit;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static ReadSessionLimitResponseEvent failure(final Status status) {
        ReadSessionLimitResponseEvent event = new ReadSessionLimitResponseEvent();

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
    public static ReadSessionLimitResponseEvent failure(final Status status,
                                                        final Throwable exception) {
        ReadSessionLimitResponseEvent event = new ReadSessionLimitResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public boolean isOverLimit() {
        return overLimit;
    }
}
