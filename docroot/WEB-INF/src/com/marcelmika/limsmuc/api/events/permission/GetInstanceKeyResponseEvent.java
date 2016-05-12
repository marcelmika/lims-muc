/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.permission;

import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 06/05/16
 * Time: 15:15
 */
public class GetInstanceKeyResponseEvent extends ResponseEvent {

    private Status status;
    private String instanceKey;

    public enum Status {
        SUCCESS,        // Event was successfull
        ERROR,          // Error occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetInstanceKeyResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetInstanceKeyResponseEvent success(final String instanceKey) {
        GetInstanceKeyResponseEvent event = new GetInstanceKeyResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.instanceKey = instanceKey;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static GetInstanceKeyResponseEvent failure(final Status status) {
        GetInstanceKeyResponseEvent event = new GetInstanceKeyResponseEvent();

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
    public static GetInstanceKeyResponseEvent failure(final Status status,
                                                            final Throwable exception) {
        GetInstanceKeyResponseEvent event = new GetInstanceKeyResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public String getInstanceKey() {
        return instanceKey;
    }
}
