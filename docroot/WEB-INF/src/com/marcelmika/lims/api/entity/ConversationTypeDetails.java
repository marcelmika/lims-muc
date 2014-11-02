/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/6/14
 * Time: 9:22 AM
 */
public enum ConversationTypeDetails {

    // Possible values
    SINGLE_USER(0),
    MULTI_USER(1),
    UNRECOGNIZED(2);

    // Current value
    private int code;


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    private ConversationTypeDetails(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
