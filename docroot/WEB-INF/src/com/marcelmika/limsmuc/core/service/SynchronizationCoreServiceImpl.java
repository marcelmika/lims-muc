/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeChatPortletRequestEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeChatPortletResponseEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCRequestEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCResponseEvent;
import com.marcelmika.limsmuc.persistence.service.SynchronizationPersistenceService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 11:42
 */
public class SynchronizationCoreServiceImpl implements SynchronizationCoreService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SynchronizationCoreServiceImpl.class);

    // Dependencies
    SynchronizationPersistenceService synchronizationPersistenceService;

    /**
     * Constructor
     *
     * @param synchronizationPersistenceService Dependencies
     */
    public SynchronizationCoreServiceImpl(final SynchronizationPersistenceService synchronizationPersistenceService) {
        this.synchronizationPersistenceService = synchronizationPersistenceService;
    }

    /**
     * Synchronizes system with the data from LIMS SUC edition
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SynchronizeSUCResponseEvent synchronizeSUC(SynchronizeSUCRequestEvent event) {
        return synchronizationPersistenceService.synchronizeSUC(event);
    }

    /**
     * Synchronizes system with the data from Chat Portlet
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SynchronizeChatPortletResponseEvent synchronizeChatPortlet(SynchronizeChatPortletRequestEvent event) {
        return synchronizationPersistenceService.synchronizeChatPortlet(event);
    }
}
