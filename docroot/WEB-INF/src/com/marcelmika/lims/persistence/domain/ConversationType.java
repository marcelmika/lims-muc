/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.ConversationTypeDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:57 AM
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
     * Factory method returns conversation type based on the passed string
     *
     * @param string conversation type string
     * @return ConversationType
     * @deprecated
     */
    public static ConversationType fromString(String string) {

        // Single user chat
        if (string.equals(SINGLE_USER.getDescription())) {
            return SINGLE_USER;
        }
        // Multi user chat
        else if (string.equals(MULTI_USER.getDescription())) {
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
