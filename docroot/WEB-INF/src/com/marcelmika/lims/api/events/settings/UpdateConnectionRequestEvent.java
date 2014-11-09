/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.settings;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/11/14
 * Time: 18:57
 */
public class UpdateConnectionRequestEvent extends RequestEvent {

    private BuddyDetails buddy;

    public UpdateConnectionRequestEvent(BuddyDetails buddy) {
        this.buddy = buddy;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }
}
