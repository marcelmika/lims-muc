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
 * Time: 15:14
 */
public class PersistenceException extends Exception {

    public PersistenceException() {
        super();
    }

    public PersistenceException(String s) {
        super(s);
    }

    public PersistenceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public PersistenceException(Throwable throwable) {
        super(throwable);
    }
}
