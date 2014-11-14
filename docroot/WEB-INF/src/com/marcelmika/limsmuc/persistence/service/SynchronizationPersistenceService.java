/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCRequestEvent;
import com.marcelmika.limsmuc.api.events.synchronization.SynchronizeSUCResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 11:46
 */
public interface SynchronizationPersistenceService {

    /**
     * Synchronizes system with the data from existing LIMS SUC edition
     *
     * @param event Request event
     * @return Response event
     */
    public SynchronizeSUCResponseEvent synchronizeSUC(SynchronizeSUCRequestEvent event);

}
