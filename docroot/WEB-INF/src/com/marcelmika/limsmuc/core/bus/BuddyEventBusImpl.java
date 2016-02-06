/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.bus;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.buddy.PresenceChangeBusEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 06/02/16
 * Time: 11:07
 */
public class BuddyEventBusImpl implements BuddyEventBus {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(BuddyEventBusImpl.class);

    // Subscribers
    private List<BuddyEventBusListener> subscribers = Collections.synchronizedList(
            new LinkedList<BuddyEventBusListener>()
    );

    /**
     * Register group event bus listener
     *
     * @param listener BuddyEventBusListener
     */
    @Override
    public void register(BuddyEventBusListener listener) {
        subscribers.add(listener);
    }

    /**
     * Publish presence change bus event
     *
     * @param event PresenceChangeBusEvent
     */
    @Override
    public void publish(PresenceChangeBusEvent event) {
        // Publish to all subscribers
        for (BuddyEventBusListener subscriber : subscribers) {
            subscriber.presenceChange(event);
        }
    }
}
