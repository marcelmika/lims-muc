/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.domain;

import com.marcelmika.limsmuc.api.entity.ConversationTypeDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/6/14
 * Time: 9:11 AM
 */
public enum ConversationType {

    /**
     * Unrecognized type of conversation
     */
    UNRECOGNIZED(0, "UNRECOGNIZED"),

    /**
     * Single user conversation
     */
    SINGLE_USER(1, "SINGLE_USER"),

    /**
     * Multi user conversation
     */
    MULTI_USER(2, "MULTI_USER");

    // Current value
    private int code;
    private String description;


    /**
     * Private constructor
     *
     * @param code        int
     * @param description String
     */
    private ConversationType(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method which creates new ConversationType from ConversationTypeDetails
     *
     * @param details conversation type details
     * @return ConversationType
     */
    public static ConversationType fromConversationTypeDetails(ConversationTypeDetails details) {

        // Single user chat
        if (details == ConversationTypeDetails.SINGLE_USER) {
            return SINGLE_USER;
        }
        // Multi user chat
        else if (details == ConversationTypeDetails.MULTI_USER) {
            return MULTI_USER;
        }
        // Unrecognized
        else {
            return UNRECOGNIZED;
        }
    }

    /**
     * Factory method that create conversation type enum from the code
     *
     * @param code that uniquely represents conversation type
     * @return ConversationType
     */
    public static ConversationType fromCode(int code) {

        // Single user chat
        if (code == SINGLE_USER.getCode()) {
            return SINGLE_USER;
        }
        // Multi user chat
        else if (code == MULTI_USER.getCode()) {
            return MULTI_USER;
        }
        // Unrecognized
        else {
            return UNRECOGNIZED;
        }
    }

    /**
     * Maps Presence to PresenceDetails
     *
     * @return PresenceDetails
     */
    public ConversationTypeDetails toConversationTypeDetails() {

        // Single user chat
        if (this == SINGLE_USER) {
            return ConversationTypeDetails.SINGLE_USER;
        }
        // Multi user chat
        else if (this == ConversationType.MULTI_USER) {
            return ConversationTypeDetails.MULTI_USER;
        }
        // Unrecognized
        else {
            return ConversationTypeDetails.UNRECOGNIZED;
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
