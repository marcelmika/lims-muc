/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.synchronization;

import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 11:38
 */
public class SynchronizeSUCRequestEvent extends RequestEvent {

    private boolean forceSynchronization;

    public SynchronizeSUCRequestEvent() {
        this.forceSynchronization = false;
    }

    public SynchronizeSUCRequestEvent(boolean forceSynchronization) {
        this.forceSynchronization = forceSynchronization;
    }

    public boolean forceSynchronization() {
        return forceSynchronization;
    }
}
