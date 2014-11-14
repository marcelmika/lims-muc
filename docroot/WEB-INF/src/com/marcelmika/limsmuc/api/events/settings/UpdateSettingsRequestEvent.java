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
import com.marcelmika.limsmuc.api.entity.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 10:17 PM
 */
public class UpdateSettingsRequestEvent extends RequestEvent {

    private final Long buddyId;
    private final SettingsDetails settingsDetails;

    public UpdateSettingsRequestEvent(Long buddyId, SettingsDetails settingsDetails) {
        this.buddyId = buddyId;
        this.settingsDetails = settingsDetails;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }
}
