/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.hooks;

import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.SimpleAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCRequestEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCResponseEvent;
import com.marcelmika.limsmuc.core.service.SynchronizationCoreService;
import com.marcelmika.limsmuc.core.service.SynchronizationCoreServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 12:16
 */
public class ApplicationStartupAction extends SimpleAction {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ApplicationStartupAction.class);

    // Dependencies
    SynchronizationCoreService synchronizationService = SynchronizationCoreServiceUtil.getSynchronizationCoreService();


    /**
     * Application startup event that runs once for every web site instance of the portal that initializes
     *
     * @param strings String[]
     * @throws ActionException
     */
    @Override
    public void run(String[] strings) throws ActionException {
        // Run the sync event
        runSUCSynchronization();
    }

    /**
     * Runs the synchronization event
     */
    private void runSUCSynchronization() {

        // Sync
        SynchronizeSUCResponseEvent responseEvent = synchronizationService.synchronizeSUC(
                new SynchronizeSUCRequestEvent()
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Log
            if (log.isInfoEnabled()) {
                log.info("LIMS MUC was successfully synchronized with LIMS SUC");
            }
        }
        // Failure
        else {
            // Log
            if (log.isInfoEnabled()) {
                log.info("LIMS SUC synchronization failed. Either no SUC edition was installed or an error occurred");
            }
        }
    }
}
