/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:08 PM
 */
public class CreateConversationResponseEvent extends ResponseEvent {

    private ConversationDetails conversation;
    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_NO_SESSION, // User does not have a session
        ERROR_UNKNOWN_CONVERSATION_TYPE, // Unknown conversation type
        ERROR_NOT_IMPLEMENTED, // Functionality is not implemented yet
        ERROR_JABBER, // Error with jabber occurred
        ERROR_MUC_COLLISION, // Such multi user conversation already exists
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private CreateConversationResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static CreateConversationResponseEvent success(ConversationDetails conversation) {
        CreateConversationResponseEvent event = new CreateConversationResponseEvent();

        event.conversation = conversation;
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
    public static CreateConversationResponseEvent failure(final Status status) {
        CreateConversationResponseEvent event = new CreateConversationResponseEvent();

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
    public static CreateConversationResponseEvent failure(final Status status,
                                                          final Throwable exception) {
        CreateConversationResponseEvent event = new CreateConversationResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public ConversationDetails getConversation() {
        return conversation;
    }
}
