/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization.chp.version.v2_0_5;

import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.limsmuc.persistence.generated.service.SynchronizationLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.synchronization.Synchronizer;
import com.marcelmika.limsmuc.persistence.synchronization.Version;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 21/11/14
 * Time: 18:21
 */
public class SynchronizerImpl implements Synchronizer {

    // Number of rows taken at once
    private static final int READ_TABLE_STEP_SIZE = 100;

    // Version type
    private static final Version VERSION = Version.CHAT_PORTLET_2_0_5;

    /**
     * Runs the synchronization process
     *
     * @throws SystemException
     */
    @Override
    public void run() throws SystemException {

        // Important note: The steps are idempotent. In other words they need to
        // be ran in the specific order

        synchronizeSettings();
    }

    private void synchronizeSettings() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        try {

            do {
                // Find start and end
                int start = index * step;
                int end = (index + 1) * step;

                // Get from db
                objects = SynchronizationLocalServiceUtil.findSettings(VERSION.getDescription(), start, end);




            } while (objects.size() != 0); // Continue until there are no more objects
        }
        // System exception has to be thrown otherwise the transaction management wouldn't work
        catch (Exception exception) {
            throw new SystemException(exception);
        }

    }
}
