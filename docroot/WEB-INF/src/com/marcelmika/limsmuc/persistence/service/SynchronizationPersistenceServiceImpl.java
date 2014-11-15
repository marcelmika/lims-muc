/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCRequestEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCResponseEvent;
import com.marcelmika.limsmuc.persistence.generated.model.Synchronization;
import com.marcelmika.limsmuc.persistence.generated.service.SynchronizationLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.synchronization.Version;

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

        // Get the synchronization
        try {
            Synchronization synchronization = SynchronizationLocalServiceUtil.getSynchronization();

            // Synchronization was already done
            if (synchronization.getSucSync()) {
                return SynchronizeSUCResponseEvent.failure(
                        SynchronizeSUCResponseEvent.Status.ERROR_ALREADY_SYNCED
                );
            }


            // It doesn't matter if the conversation was successful or not, set the flag to true
            SynchronizationLocalServiceUtil.setSucSynced(true);
        }
        // Failure
        catch (SystemException e) {
            return SynchronizeSUCResponseEvent.failure(SynchronizeSUCResponseEvent.Status.ERROR_PERSISTENCE);
        }


        // Do the synchronization
        boolean success = false;

        // Try to synchronize with SUC v1.2.0
        if (synchronizeSUC(Version.SUC_1_2_0)) {
            success = true;
        }
        // Try to synchronize with SUC v1.1.0
        else if (synchronizeSUC(Version.SUC_1_1_0)) {
            success = true;
        }
        // Try to synchronize with SUC v1.0.1
        else if (synchronizeSUC(Version.SUC_1_0_1)) {
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
    private boolean synchronizeSUC(Version version) {
        // Let the persistence handle it
        try {
            // Synchronize
            SynchronizationLocalServiceUtil.synchronizeSUC(version.getDescription());

            // Success
            return true;
        }
        // Failure
        catch (SystemException e) {

            // Log for debug
            if (log.isDebugEnabled()) {
                log.debug("Version: " + version.getDescription() + " sync failed");
            }

            return false;
        }
    }
}
