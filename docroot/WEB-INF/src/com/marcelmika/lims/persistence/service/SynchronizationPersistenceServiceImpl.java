/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.synchronization.SynchronizeSUCRequestEvent;
import com.marcelmika.lims.api.events.synchronization.SynchronizeSUCResponseEvent;
import com.marcelmika.lims.persistence.generated.service.SynchronizationLocalServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 11:46
 */
public class SynchronizationPersistenceServiceImpl implements SynchronizationPersistenceService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SynchronizationPersistenceServiceImpl.class);

    /**
     * Synchronizes system with the data from existing LIMS SUC edition
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SynchronizeSUCResponseEvent synchronizeSUC(SynchronizeSUCRequestEvent event) {

        boolean success = false;

        // Try to synchronize with SUC v1.2.0
        if (synchronizeSUC_1_2_0()) {
            success = true;
        }


        // Success
        if (success) {
            return SynchronizeSUCResponseEvent.success();
        }
        // Failure
        else {
            return SynchronizeSUCResponseEvent.failure(SynchronizeSUCResponseEvent.Status.ERROR_PERSISTENCE);
        }
    }

    /**
     * Synchronizes with SUC v1.2.0
     *
     * @return true if the sync was successful, false otherwise
     */
    private boolean synchronizeSUC_1_2_0() {
        // Let the persistence handle it
        try {
            // Synchronize
            SynchronizationLocalServiceUtil.synchronizeSUC_1_2_0();

            // Success
            return true;
        }
        // Failure
        catch (SystemException e) {

            if (log.isDebugEnabled()) {
                log.debug("Sync SUC v1.2.0 FAILED");
            }

            return false;
        }
    }
}
