/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.domain;

import com.liferay.portal.kernel.json.JSON;
import com.marcelmika.limsmuc.api.entity.SettingsDetails;

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
    private boolean notificationsEnabled;
    private boolean isChatEnabled;
    @JSON(include = false)
    @Deprecated
    private boolean isAdminAreaOpened;
    @JSON(include = false)
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
        settings.notificationsEnabled = details.isNotificationsEnabled();

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
        // Map data from user
        details.setActivePanelId(activePanelId);
        details.setMute(isMute);
        details.setChatEnabled(isChatEnabled);
        details.setAdminAreaOpened(isAdminAreaOpened);
        details.setJabberDisconnected(isJabberDisconnected);
        details.setNotificationsEnabled(notificationsEnabled);


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

    public void setIsMute(boolean isMute) {
        this.isMute = isMute;
    }

    public boolean isMute() {
        return isMute;
    }

    public boolean getIsChatEnabled() {
        return isChatEnabled;
    }

    public boolean isNotificationsEnabled() {
        return notificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        this.notificationsEnabled = notificationsEnabled;
    }

    public void setIsChatEnabled(boolean isChatEnabled) {
        this.isChatEnabled = isChatEnabled;
    }

    public boolean isChatEnabled() {
        return isChatEnabled;
    }

    public boolean getIsAdminAreaOpened() {
        return isAdminAreaOpened;
    }

    @Deprecated
    public void setIsAdminAreaOpened(boolean isAdminAreaOpened) {
        this.isAdminAreaOpened = isAdminAreaOpened;
    }

    @Deprecated
    public boolean isAdminAreaOpened() {
        return isAdminAreaOpened;
    }

    public boolean getIsJabberDisconnected() {
        return isJabberDisconnected;
    }

    public void setIsJabberDisconnected(boolean isJabberDisconnected) {
        this.isJabberDisconnected = isJabberDisconnected;
    }

    public boolean isJabberDisconnected() {
        return isJabberDisconnected;
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
                ", isJabberDisconnected=" + isJabberDisconnected +
                '}';
    }
}
