/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.entity;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 8:41 PM
 */
public class SettingsDetails {

    private PresenceDetails presenceDetails;
    private String activePanelId;
    private boolean isMute;
    private boolean isChatEnabled;
    private boolean isAdminAreaOpened;

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }

    public void setPresenceDetails(PresenceDetails presenceDetails) {
        this.presenceDetails = presenceDetails;
    }

    public String getActivePanelId() {
        return activePanelId;
    }

    public void setActivePanelId(String activePanelId) {
        this.activePanelId = activePanelId;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean isMute) {
        this.isMute = isMute;
    }

    public boolean isChatEnabled() {
        return isChatEnabled;
    }

    public void setChatEnabled(boolean isChatEnabled) {
        this.isChatEnabled = isChatEnabled;
    }

    public boolean isAdminAreaOpened() {
        return isAdminAreaOpened;
    }

    public void setAdminAreaOpened(boolean isAdminAreaOpened) {
        this.isAdminAreaOpened = isAdminAreaOpened;
    }
}
