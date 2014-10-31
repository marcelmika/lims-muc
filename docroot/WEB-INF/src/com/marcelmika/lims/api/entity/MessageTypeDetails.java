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

package com.marcelmika.lims.api.entity;

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