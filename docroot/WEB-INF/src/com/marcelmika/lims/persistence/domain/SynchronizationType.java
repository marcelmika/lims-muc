/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.domain;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 14:13
 */
public enum SynchronizationType {

    /**
     * Unrecognized type of synchronization
     */
    UNKNOWN(0, "Unknown"),

    /**
     * Single user conversation
     */
    SUC(1, "Single User Chat Sync Type");

    // Current value
    private int code;
    private String description;


    /**
     * Private constructor
     *
     * @param code        int
     * @param description String
     */
    private SynchronizationType(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method that create conversation type enum from the code
     *
     * @param code that uniquely represents conversation type
     * @return ConversationType
     */
    public static SynchronizationType fromCode(int code) {

        // Single user chat
        if (code == SUC.getCode()) {
            return SUC;
        }
        // Unrecognized
        else {
            return UNKNOWN;
        }
    }

    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------


    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

}
