/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.entity;

/**
 * Enum for conversation message type details
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 31/10/14
 * Time: 22:30
 */
public enum MessageTypeDetails {

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
    private MessageTypeDetails(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}