/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.request.parameters;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/10/14
 * Time: 16:26
 */
public class ParameterException extends Exception {

    public ParameterException() {
        super();
    }

    public ParameterException(String s) {
        super(s);
    }

    public ParameterException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ParameterException(Throwable throwable) {
        super(throwable);
    }
}
