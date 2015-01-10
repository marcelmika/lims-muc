/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.settings;

import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.entity.SettingsDetails;
import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 4:03 PM
 */
public class ReadSettingsRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;
    private SettingsDetails settingsDetails;

    public ReadSettingsRequestEvent(BuddyDetails buddyDetails) {
        this.buddyDetails = buddyDetails;
    }

    public ReadSettingsRequestEvent(SettingsDetails settingsDetails, BuddyDetails buddyDetails) {
        this.settingsDetails = settingsDetails;
        this.buddyDetails = buddyDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }
}
