/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.json.JSON;
import com.marcelmika.lims.api.entity.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/6/14
 * Time: 8:21 PM
 */
public class Settings {

    private Buddy buddy;
    private Presence presence;
    private String activePanelId;
    private boolean isMute;
    private boolean isChatEnabled;
    @JSON(include = false)
    private boolean isAdminAreaOpened;


    /**
     * Create new settings and maps data from settings details
     *
     * @param settingsDetails SettingsDetails
     * @return Settings
     */
    public static Settings fromSettingsDetails(SettingsDetails settingsDetails) {
        // Create new Settings
        Settings settings = new Settings();
        // Map data to settings details
        settings.activePanelId = settingsDetails.getActivePanelId();
        settings.isMute = settingsDetails.isMute();
        settings.isChatEnabled = settingsDetails.isChatEnabled();
        settings.isAdminAreaOpened = settingsDetails.isAdminAreaOpened();

        // Relations
        if (settingsDetails.getPresenceDetails() != null) {
            settings.presence = Presence.fromPresenceDetails(settingsDetails.getPresenceDetails());
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
        // Map data from user
        details.setActivePanelId(activePanelId);
        details.setMute(isMute);
        details.setChatEnabled(isChatEnabled);
        details.setAdminAreaOpened(isAdminAreaOpened);

        // Relations
        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        return details;
    }

    public Buddy getBuddy() {
        return buddy;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    public String getActivePanelId() {
        return activePanelId;
    }

    public void setActivePanelId(String activePanelId) {
        this.activePanelId = activePanelId;
    }

    public boolean getIsMute() {
        return isMute;
    }

    public boolean isMute() {
        return isMute;
    }

    public void setMute(boolean isMute) {
        this.isMute = isMute;
    }

    public void setIsMute(boolean isMute) {
        this.isMute = isMute;
    }

    public boolean getIsChatEnabled() {
        return isChatEnabled;
    }

    public boolean isChatEnabled() {
        return isChatEnabled;
    }

    public void setChatEnabled(boolean isChatEnabled) {
        this.isChatEnabled = isChatEnabled;
    }

    public boolean getIsAdminAreaOpened() {
        return isAdminAreaOpened;
    }

    public boolean isAdminAreaOpened() {
        return isAdminAreaOpened;
    }

    public void setIsAdminAreaOpened(boolean isAdminAreaOpened) {
        this.isAdminAreaOpened = isAdminAreaOpened;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "buddy=" + buddy +
                ", presence=" + presence +
                ", activePanelId='" + activePanelId + '\'' +
                ", isMute=" + isMute +
                ", isChatEnabled=" + isChatEnabled +
                ", isAdminAreaOpened=" + isAdminAreaOpened +
                '}';
    }
}
