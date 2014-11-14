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
import com.marcelmika.limsmuc.api.entity.PresenceDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 9:08 PM
 */
public class UpdatePresenceBuddyRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final PresenceDetails presenceDetails;

    public UpdatePresenceBuddyRequestEvent(Long buddyId, PresenceDetails presenceDetails) {
        this.buddyId = buddyId;
        this.presenceDetails = presenceDetails;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }
}
