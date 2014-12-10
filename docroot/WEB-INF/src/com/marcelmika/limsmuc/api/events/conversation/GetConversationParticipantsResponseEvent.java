/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.conversation;

import com.marcelmika.limsmuc.api.entity.ConversationDetails;
import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 28/08/14
 * Time: 10:35
 */
public class GetConversationParticipantsResponseEvent extends ResponseEvent {

    private Status status;
    private ConversationDetails conversation;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetConversationParticipantsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetConversationParticipantsResponseEvent success(ConversationDetails conversation) {
        GetConversationParticipantsResponseEvent event = new GetConversationParticipantsResponseEvent();

        event.status = Status.SUCCESS;
        event.success = true;
        event.conversation = conversation;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static GetConversationParticipantsResponseEvent failure(final Status status) {
        GetConversationParticipantsResponseEvent event = new GetConversationParticipantsResponseEvent();

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
    public static GetConversationParticipantsResponseEvent failure(final Status status,
                                                                   final Throwable exception) {

        GetConversationParticipantsResponseEvent event = new GetConversationParticipantsResponseEvent();

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
