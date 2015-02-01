/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.buddy;

import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.events.ResponseEvent;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/14
 * Time: 23:01
 */
public class SearchBuddiesResponseEvent extends ResponseEvent {

    private List<BuddyDetails> searchResults;
    private Status status;

    public enum Status {
        SUCCESS,                // Event was successful
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_PERSISTENCE,      // Error with persistence occurred
        ERROR_NO_SESSION,       // User doesn't have a jabber sessions
        ERROR_JABBER,           // Error with jabber occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private SearchBuddiesResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static SearchBuddiesResponseEvent success(final List<BuddyDetails> searchResults) {
        SearchBuddiesResponseEvent event = new SearchBuddiesResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.searchResults = searchResults;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static SearchBuddiesResponseEvent failure(final Status status) {
        SearchBuddiesResponseEvent event = new SearchBuddiesResponseEvent();

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
    public static SearchBuddiesResponseEvent failure(final Status status,
                                                     final Throwable exception) {

        SearchBuddiesResponseEvent event = new SearchBuddiesResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public List<BuddyDetails> getSearchResults() {
        return searchResults;
    }

    public Status getStatus() {
        return status;
    }
}
