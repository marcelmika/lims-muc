/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.buddy;

import com.marcelmika.limsmuc.api.events.RequestEvent;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 01/04/15
 * Time: 09:08
 */
public class ReadPresenceChangeRequestEvent extends RequestEvent {

    private Long userId;
    private Date since;

    public ReadPresenceChangeRequestEvent(Long userId, Date since) {
        this.userId = userId;
        this.since = since;
    }

    public Long getUserId() {
        return userId;
    }

    public Date getSince() {
        return since;
    }
}
