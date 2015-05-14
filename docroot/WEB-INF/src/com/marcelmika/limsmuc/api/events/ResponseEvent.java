/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:42 PM
 */
public class ResponseEvent {

    protected boolean success;
    protected Throwable exception;

    /**
     * Returns true if the request was successful
     *
     * @return boolean
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Returns response exception if set. Null otherwise
     *
     * @return Throwable
     */
    public Throwable getException() {
        return exception;
    }

    /**
     * Returns exception message if set. Empty string otherwise
     *
     * @return String
     */
    public String getExceptionMessage() {
        if (exception == null) {
            return "";
        }

        return exception.getLocalizedMessage();
    }
}
