/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.conversation;

import com.marcelmika.limsmuc.api.entity.MessageDetails;
import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 3:51 PM
 */
public class SendMessageResponseEvent extends ResponseEvent {

    private Status status;
    private MessageDetails message;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_NO_SESSION, // User does not have a session
        ERROR_FORBIDDEN, // User is not allowed to send the message
        ERROR_NOT_FOUND, // No Conversation was found
        ERROR_UNKNOWN_CONVERSATION_TYPE, // Unknown conversation type
        ERROR_NOT_IMPLEMENTED, // Functionality is not implemented yet
        ERROR_JABBER, // Error with jabber occurred
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private SendMessageResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static SendMessageResponseEvent success(MessageDetails message) {
        SendMessageResponseEvent event = new SendMessageResponseEvent();

        event.status = Status.SUCCESS;
        event.success = true;
        event.message = message;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static SendMessageResponseEvent failure(final Status status) {
        SendMessageResponseEvent event = new SendMessageResponseEvent();

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
    public static SendMessageResponseEvent failure(final Status status,
                                                   final Throwable exception) {

        SendMessageResponseEvent event = new SendMessageResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public MessageDetails getMessage() {
        return message;
    }
}
