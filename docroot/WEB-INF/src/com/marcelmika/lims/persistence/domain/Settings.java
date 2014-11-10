/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.domain;

import com.marcelmika.lims.api.entity.SettingsDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/4/14
 * Time: 4:26 PM
 */
public class Settings {

    private Presence presence;
    private String activePanel;
    private boolean isMute;
    private boolean isChatEnabled;
    private boolean isAdminAreaOpened;


    /**
     * Factory method which builds Settings object from Panel and Settings Model taken from database
     *
     * @param panelModel    Panel model
     * @param settingsModel Settings model
     * @return Settings
     */
    public static Settings fromServiceBuilderModel(com.marcelmika.lims.persistence.generated.model.Panel panelModel,
                                                   com.marcelmika.lims.persistence.generated.model.Settings settingsModel) {

        // Create new settings
        Settings settings = new Settings();
        // Map Panel
        if (panelModel != null) {
            settings.activePanel = panelModel.getActivePanelId();
        }
        // Map Settings
        if (settingsModel != null) {
            settings.isMute = settingsModel.getMute();
            settings.isChatEnabled = settingsModel.getChatEnabled();
            settings.isAdminAreaOpened = settingsModel.isAdminAreaOpened();
            // Relations
            settings.presence = Presence.fromDescription(settingsModel.getPresence());
        }

        return settings;
    }

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
        settings.activePanel = settingsDetails.getActivePanelId();
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
        details.setActivePanelId(activePanel);
        details.setMute(isMute);
        details.setChatEnabled(isChatEnabled);
        details.setAdminAreaOpened(isAdminAreaOpened);

        // Relations
        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        return details;
    }

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    public String getActivePanel() {
        return activePanel;
    }

    public void setActivePanel(String activePanel) {
        this.activePanel = activePanel;
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

    @Override
    public String toString() {
        return "Settings{" +
                "presence=" + presence +
                ", activePanel='" + activePanel + '\'' +
                ", isMute=" + isMute +
                ", isChatEnabled=" + isChatEnabled +
                ", isAdminAreaOpened=" + isAdminAreaOpened +
                '}';
    }
}
