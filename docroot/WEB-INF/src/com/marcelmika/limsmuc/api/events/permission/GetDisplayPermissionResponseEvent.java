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
 * Date: 04/05/16
 * Time: 15:41
 */
public class GetDisplayPermissionResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        GRANTED,        // Permission was granted
        NOT_GRANTED,    // Permission was not granted
        ERROR,          // Error occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetDisplayPermissionResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetDisplayPermissionResponseEvent success() {
        GetDisplayPermissionResponseEvent event = new GetDisplayPermissionResponseEvent();

        event.success = true;
        event.status = Status.GRANTED;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static GetDisplayPermissionResponseEvent failure(final Status status) {
        GetDisplayPermissionResponseEvent event = new GetDisplayPermissionResponseEvent();

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
    public static GetDisplayPermissionResponseEvent failure(final Status status,
                                                            final Throwable exception) {
        GetDisplayPermissionResponseEvent event = new GetDisplayPermissionResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }
}
