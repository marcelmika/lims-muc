/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.settings;

import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 17/01/15
 * Time: 22:08
 */
public class ReadSessionLimitRequestEvent extends RequestEvent {

    Long buddyId;

    public ReadSessionLimitRequestEvent(Long buddyId) {
        this.buddyId = buddyId;
    }

    public Long getBuddyId() {
        return buddyId;
    }
}
