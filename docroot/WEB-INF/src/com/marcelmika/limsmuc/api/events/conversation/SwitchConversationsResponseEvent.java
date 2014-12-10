/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.conversation;

import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 07/11/14
 * Time: 13:11
 */
public class SwitchConversationsResponseEvent extends ResponseEvent {

    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_NOT_FOUND, // Such conversation was not found
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_FORBIDDEN, // User is not allowed to leave the conversation
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private SwitchConversationsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static SwitchConversationsResponseEvent success() {
        SwitchConversationsResponseEvent event = new SwitchConversationsResponseEvent();

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
    public static SwitchConversationsResponseEvent failure(final Status status) {
        SwitchConversationsResponseEvent event = new SwitchConversationsResponseEvent();

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
    public static SwitchConversationsResponseEvent failure(final Status status,
                                                           final Throwable exception) {

        SwitchConversationsResponseEvent event = new SwitchConversationsResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }


}
