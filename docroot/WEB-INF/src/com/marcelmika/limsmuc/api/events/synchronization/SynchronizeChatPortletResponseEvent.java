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
 * Date: 21/11/14
 * Time: 17:53
 */
public class SynchronizeChatPortletResponseEvent extends ResponseEvent {


    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        SUCCESS_IN_PROGRESS, // Event is still in progress
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private SynchronizeChatPortletResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static SynchronizeChatPortletResponseEvent success() {
        SynchronizeChatPortletResponseEvent event = new SynchronizeChatPortletResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;

        return event;
    }

    /**
     * Factory method for success status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static SynchronizeChatPortletResponseEvent success(final Status status) {
        SynchronizeChatPortletResponseEvent event = new SynchronizeChatPortletResponseEvent();

        event.success = true;
        event.status = status;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static SynchronizeChatPortletResponseEvent failure(final Status status) {
        SynchronizeChatPortletResponseEvent event = new SynchronizeChatPortletResponseEvent();

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
    public static SynchronizeChatPortletResponseEvent failure(final Status status,
                                                      final Throwable exception) {

        SynchronizeChatPortletResponseEvent event = new SynchronizeChatPortletResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

}
