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
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeChatPortletRequestEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeChatPortletResponseEvent;
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

    private static boolean sucInProgress = false;
    private static boolean chatPortletInProgress = false;

    /**
     * Synchronizes system with the data from LIMS SUC edition
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SynchronizeSUCResponseEvent synchronizeSUC(SynchronizeSUCRequestEvent event) {

        // If the synchronization is already in progress do nothing
        if (sucInProgress) {
            return SynchronizeSUCResponseEvent.success(
                    SynchronizeSUCResponseEvent.Status.SUCCESS_IN_PROGRESS
            );
        }

        // If the synchronization is not forced to happen check if suc was already synced
        if (!event.forceSynchronization()) {
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
        }


        // Do the synchronization
        boolean success = false;

        // Sync is in progress
        sucInProgress = true;

        // Try to synchronize with SUC v1.2.0
        if (synchronize(Version.SUC_1_2_0)) {
            success = true;
        }
        // Try to synchronize with SUC v1.1.0
        else if (synchronize(Version.SUC_1_1_0)) {
            success = true;
        }
        // Try to synchronize with SUC v1.0.1
        else if (synchronize(Version.SUC_1_0_1)) {
            success = true;
        }

        // Sync finished
        sucInProgress = false;

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
     * Synchronizes system with the data from Chat Portlet
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SynchronizeChatPortletResponseEvent synchronizeChatPortlet(SynchronizeChatPortletRequestEvent event) {

        // If the synchronization is already in progress do nothing
        if (chatPortletInProgress) {
            return SynchronizeChatPortletResponseEvent.success(
                    SynchronizeChatPortletResponseEvent.Status.SUCCESS_IN_PROGRESS
            );
        }

        // Synchronize
        boolean success = false;

        // Sync is in progress
        chatPortletInProgress = true;

        // Try to synchronize with Chat Portlet v2.0.5
        if (synchronize(Version.CHAT_PORTLET_2_0_5)) {
            success = true;
        }

        // Sync finished
        chatPortletInProgress = false;

        // Success
        if (success) {
            return SynchronizeChatPortletResponseEvent.success();
        }
        // Failure
        else {
            return SynchronizeChatPortletResponseEvent.failure(
                    SynchronizeChatPortletResponseEvent.Status.ERROR_PERSISTENCE
            );
        }
    }

    /**
     * Synchronize with the given version of SUC
     *
     * @param version Version
     * @return true if the sync was successful, false otherwise
     */
    private boolean synchronize(Version version) {
        // Let the persistence handle it
        try {
            // Synchronize
            SynchronizationLocalServiceUtil.synchronize(version.getDescription());

            // Success
            return true;
        }
        // Failure
        catch (SystemException e) {

            // Log for debug
            if (log.isDebugEnabled()) {
                log.debug("Version: " + version.getDescription() + " sync failed");
                log.debug(e);
            }

            return false;
        }
    }
}
