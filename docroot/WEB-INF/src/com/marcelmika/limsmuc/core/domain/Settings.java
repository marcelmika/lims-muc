/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.domain;

import com.marcelmika.limsmuc.api.entity.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/5/14
 * Time: 8:33 PM
 */
public class Settings {

    private Presence presence;
    private String activePanelId;
    private boolean isMute;
    private boolean isChatEnabled;
    private boolean isAdminAreaOpened;
    private boolean isJabberDisconnected;


    /**
     * Create new settings and maps data from settings details
     *
     * @param details SettingsDetails
     * @return Settings
     */
    public static Settings fromSettingsDetails(SettingsDetails details) {
        // Create new Settings
        Settings settings = new Settings();
        // Map data to settings details
        settings.activePanelId = details.getActivePanelId();
        settings.isMute = details.isMute();
        settings.isChatEnabled = details.isChatEnabled();
        settings.isAdminAreaOpened = details.isAdminAreaOpened();
        settings.isJabberDisconnected = details.isJabberDisconnected();

        // Relations
        if (details.getPresenceDetails() != null) {
            settings.presence = Presence.fromPresenceDetails(details.getPresenceDetails());
        }

        return settings;
    }

    /**
     * Maps settings to settings details
     *
     * @return SettingsDetails
     */
    public SettingsDetails toSettingsDetails() {
        // Create new user details
        SettingsDetails details = new SettingsDetails();
        // Map data from users
        details.setActivePanelId(activePanelId);
        details.setMute(isMute);
        details.setChatEnabled(isChatEnabled);
        details.setAdminAreaOpened(isAdminAreaOpened);
        details.setJabberDisconnected(isJabberDisconnected);

        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        return details;
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

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    public boolean isAdminAreaOpened() {
        return isAdminAreaOpened;
    }

    public void setAdminAreaOpened(boolean isAdminAreaOpened) {
        this.isAdminAreaOpened = isAdminAreaOpened;
    }

    public boolean isJabberDisconnected() {
        return isJabberDisconnected;
    }

    public void setJabberDisconnected(boolean isJabberDisconnected) {
        this.isJabberDisconnected = isJabberDisconnected;
    }
}
