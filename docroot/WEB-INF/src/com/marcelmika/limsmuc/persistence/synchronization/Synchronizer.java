/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization;

import com.liferay.portal.kernel.exception.SystemException;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 13/11/14
 * Time: 19:56
 */
public interface Synchronizer {

    /**
     * Runs the synchronization process
     *
     * @throws SystemException
     */
    public void run() throws SystemException;
}
