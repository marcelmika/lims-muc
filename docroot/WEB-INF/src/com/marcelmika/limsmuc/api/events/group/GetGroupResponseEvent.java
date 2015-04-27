/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.group;

import com.marcelmika.limsmuc.api.entity.GroupDetails;
import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/03/15
 * Time: 16:37
 */
public class GetGroupResponseEvent extends ResponseEvent {

    private Status status;
    private GroupDetails group;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_NOT_FOUND,        // No such group
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_PERSISTENCE,      // Error with persistence occurred
        ERROR_FORBIDDEN,        // Such group cannot be read
        ERROR_NO_SESSION,       // User doesn't have the jabber session
        ERROR_JABBER,           // Error with jabber occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetGroupResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetGroupResponseEvent success(final GroupDetails group) {
        GetGroupResponseEvent event = new GetGroupResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.group = group;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static GetGroupResponseEvent failure(final Status status) {
        GetGroupResponseEvent event = new GetGroupResponseEvent();

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
    public static GetGroupResponseEvent failure(final Status status,
                                                final Throwable exception) {

        GetGroupResponseEvent event = new GetGroupResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public GroupDetails getGroup() {
        return group;
    }
}
