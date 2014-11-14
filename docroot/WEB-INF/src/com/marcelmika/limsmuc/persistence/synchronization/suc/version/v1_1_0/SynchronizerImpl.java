/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization.suc.version.v1_1_0;

import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.limsmuc.persistence.synchronization.Synchronizer;
import com.marcelmika.limsmuc.persistence.synchronization.Version;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 13/11/14
 * Time: 20:15
 */
public class SynchronizerImpl implements Synchronizer {

    // Number of rows taken at once
    private static final int READ_TABLE_STEP_SIZE = 100;

    // Version type
    private static final Version VERSION = Version.SUC_1_1_0;

    /**
     * Runs the synchronization process
     *
     * @throws SystemException
     */
    @Override
    public void run() throws SystemException {

    }
}
