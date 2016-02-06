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
 * Time: 11:07
 */
public interface BuddyEventBusListener {

    /**
     * Called when the presence is changed
     *
     * @param event PresenceChangeBusEvent
     */
    void presenceChange(PresenceChangeBusEvent event);
}
