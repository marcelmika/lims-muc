/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.bus;

import com.marcelmika.limsmuc.api.events.buddy.PresenceChangeBusEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 06/02/16
 * Time: 10:32
 */
public interface BuddyEventBus {

    /**
     * Register group event bus listener
     *
     * @param listener BuddyEventBusListener
     */
    void register(BuddyEventBusListener listener);

    /**
     * Publish presence change bus event
     *
     * @param event PresenceChangeBusEvent
     */
    void publish(PresenceChangeBusEvent event);
}
