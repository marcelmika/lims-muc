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
 * Date: 01/04/15
 * Time: 09:09
 */
public class ReadPresenceChangeResponseEvent extends ResponseEvent {

    private Status status;
    private List<BuddyDetails> buddies;

    public enum Status {
        SUCCESS,                // Event was successful
        SUCCESS_LOADING,        // Event was successful but the presences are still loading
        ERROR_WRONG_PARAMETERS, // Wrong input parameters
        ERROR_FORBIDDEN,        // User is not allowed to perform this action
        ERROR_NO_SESSION,       // User doesn't have a jabber session
        ERROR_PERSISTENCE,      // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private ReadPresenceChangeResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static ReadPresenceChangeResponseEvent success(final Status status) {
        ReadPresenceChangeResponseEvent event = new ReadPresenceChangeResponseEvent();

        event.success = true;
        event.status = status;
        event.buddies = null;

        return event;
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static ReadPresenceChangeResponseEvent success(final List<BuddyDetails> buddies) {
        ReadPresenceChangeResponseEvent event = new ReadPresenceChangeResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.buddies = buddies;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static ReadPresenceChangeResponseEvent failure(final Status status) {
        ReadPresenceChangeResponseEvent event = new ReadPresenceChangeResponseEvent();

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
    public static ReadPresenceChangeResponseEvent failure(final Status status,
                                                          final Throwable exception) {

        ReadPresenceChangeResponseEvent event = new ReadPresenceChangeResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public List<BuddyDetails> getBuddies() {
        return buddies;
    }

}
