/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.entity.ConversationCollectionDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 5:52 PM
 */
public class GetConversationsResponseEvent extends ResponseEvent {


    private Status status;
    private ConversationCollectionDetails conversationCollection;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_NO_SESSION, // User does not have a session
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private GetConversationsResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static GetConversationsResponseEvent success(final ConversationCollectionDetails conversationCollection) {
        GetConversationsResponseEvent event = new GetConversationsResponseEvent();

        event.status = Status.SUCCESS;
        event.success = true;
        event.conversationCollection = conversationCollection;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static GetConversationsResponseEvent failure(final Status status) {
        GetConversationsResponseEvent event = new GetConversationsResponseEvent();

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
    public static GetConversationsResponseEvent failure(final Status status,
                                                        final Throwable exception) {

        GetConversationsResponseEvent event = new GetConversationsResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }


    public Status getStatus() {
        return status;
    }

    public ConversationCollectionDetails getConversationCollection() {
        return conversationCollection;
    }
}
