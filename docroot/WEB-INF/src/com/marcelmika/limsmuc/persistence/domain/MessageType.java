/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.domain;

import com.marcelmika.limsmuc.api.entity.MessageTypeDetails;

/**
 * Enum for the conversation message type
 */
public enum MessageType {

    /**
     * Unknown type of message
     */
    UNKNOWN(0, "unknown"),

    /**
     * Regular type of message
     */
    REGULAR(1, "regular"),

    /**
     * User has left the conversation type of message
     */
    LEFT(2, "left"),

    /**
     * User has been added to the conversation type of message
     */
    ADDED(3, "added");

    // Unique integer code
    private int code;
    // Unique string description
    private String description;

    /**
     * Private constructor
     *
     * @param code        integer code of the message
     * @param description string description of the message
     */
    private MessageType(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Factory method that creates message type enum from message type details enum
     *
     * @param details MessageTypeDetails
     * @return MessageType
     */
    public static MessageType fromMessageTypeDetails(MessageTypeDetails details) {
        // Regular
        if (details == MessageTypeDetails.REGULAR) {
            return MessageType.REGULAR;
        }
        // Left
        else if (details == MessageTypeDetails.LEFT) {
            return MessageType.LEFT;
        }
        // Added
        else if (details == MessageTypeDetails.ADDED) {
            return MessageType.ADDED;
        }
        // Unknown
        else {
            return MessageType.UNKNOWN;
        }
    }

    /**
     * Factory method that creates message type enum from the code
     *
     * @param code that uniquely represent message type
     * @return MessageType
     */
    public static MessageType fromCode(int code) {
        // Regular
        if (code == REGULAR.getCode()) {
            return MessageType.REGULAR;
        }
        // Left
        else if (code == LEFT.getCode()) {
            return MessageType.LEFT;
        }
        // Added
        else if (code == ADDED.getCode()) {
            return MessageType.ADDED;
        }
        // Unknown
        else {
            return MessageType.UNKNOWN;
        }
    }

    /**
     * Factory method that create message type details enum from the message type enum
     *
     * @return MessageTypeDetails
     */
    public MessageTypeDetails toMessageTypeDetails() {
        // Regular
        if (this == REGULAR) {
            return MessageTypeDetails.REGULAR;
        }
        // Left
        else if (this == LEFT) {
            return MessageTypeDetails.LEFT;
        }
        // Added
        else if (this == ADDED) {
            return MessageTypeDetails.ADDED;
        }
        // Unknown
        else {
            return MessageTypeDetails.UNKNOWN;
        }
    }

    /**
     * Returns message type integer code
     *
     * @return int
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns message type string description
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }
}
