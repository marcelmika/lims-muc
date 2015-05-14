/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.exception;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 27/04/15
 * Time: 15:13
 */
public class ForbiddenException extends Exception {

    public ForbiddenException() {
        super();
    }

    public ForbiddenException(String s) {
        super(s);
    }

    public ForbiddenException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ForbiddenException(Throwable throwable) {
        super(throwable);
    }
}
