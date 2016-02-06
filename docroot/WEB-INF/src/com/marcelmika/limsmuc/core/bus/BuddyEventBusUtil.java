/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.bus;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 06/02/16
 * Time: 11:07
 */
public class BuddyEventBusUtil {

    // Injected event bus
    private static BuddyEventBus buddyEventBus;

    /**
     * Returns BuddyEventBus implementation
     *
     * @return BuddyEventBus
     */
    public static BuddyEventBus getBuddyEventBus() {
        return buddyEventBus;
    }

    /**
     * Inject proper BuddyEventBus via Dependency Injection
     *
     * @param buddyEventBus BuddyEventBus
     */
    public void setBuddyEventBus(BuddyEventBus buddyEventBus) {
        BuddyEventBusUtil.buddyEventBus = buddyEventBus;
    }
}
