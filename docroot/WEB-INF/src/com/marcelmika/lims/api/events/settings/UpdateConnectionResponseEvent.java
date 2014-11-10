/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.entity.SettingsDetails;
import com.marcelmika.lims.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/11/14
 * Time: 18:59
 */
public class UpdateConnectionResponseEvent extends ResponseEvent {

    private SettingsDetails settings;
    private Status status;

    public enum Status {
        SUCCESS, // Event was successful
        ERROR_PERSISTENCE, // Error with persistence occurred
    }

    /**
     * Constructor is private. Use factory methods to create new success or failure instances
     */
    private UpdateConnectionResponseEvent() {
        // No params
    }

    /**
     * Factory method for success status
     *
     * @return ResponseEvent
     */
    public static UpdateConnectionResponseEvent success(SettingsDetails settings) {
        UpdateConnectionResponseEvent event = new UpdateConnectionResponseEvent();

        event.success = true;
        event.status = Status.SUCCESS;
        event.settings = settings;

        return event;
    }

    /**
     * Factory method for failure status
     *
     * @param status Status
     * @return ResponseEvent
     */
    public static UpdateConnectionResponseEvent failure(final Status status) {
        UpdateConnectionResponseEvent event = new UpdateConnectionResponseEvent();

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
    public static UpdateConnectionResponseEvent failure(final Status status,
                                                            final Throwable exception) {

        UpdateConnectionResponseEvent event = new UpdateConnectionResponseEvent();

        event.success = false;
        event.status = status;
        event.exception = exception;

        return event;
    }

    public Status getStatus() {
        return status;
    }

    public SettingsDetails getSettings() {
        return settings;
    }
}
