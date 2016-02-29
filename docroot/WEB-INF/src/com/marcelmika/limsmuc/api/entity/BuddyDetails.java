/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.entity;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:28 PM
 */
public class BuddyDetails {

    private Long buddyId;
    private Long companyId;
    private String fullName;
    private String screenName;
    private String password;
    private Boolean connected;
    private Boolean connectedJabber;
    private Date connectedAt;
    private PresenceDetails presenceDetails;
    private SettingsDetails settingsDetails;

    public Long getBuddyId() {
        return buddyId;
    }

    public void setBuddyId(Long buddyId) {
        this.buddyId = buddyId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PresenceDetails getPresenceDetails() {
        return presenceDetails;
    }

    public void setPresenceDetails(PresenceDetails presenceDetails) {
        this.presenceDetails = presenceDetails;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Boolean getConnectedJabber() {
        return connectedJabber;
    }

    public void setConnectedJabber(Boolean connectedJabber) {
        this.connectedJabber = connectedJabber;
    }

    public Date getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(Date connectedAt) {
        this.connectedAt = connectedAt;
    }

    public SettingsDetails getSettingsDetails() {
        return settingsDetails;
    }

    public void setSettingsDetails(SettingsDetails settingsDetails) {
        this.settingsDetails = settingsDetails;
    }

    @Override
    public String toString() {
        return "BuddyDetails{" +
                "buddyId=" + buddyId +
                ", companyId=" + companyId +
                ", fullName='" + fullName + '\'' +
                ", screenName='" + screenName + '\'' +
                ", password='" + password + '\'' +
                ", connected=" + connected +
                ", connectedJabber=" + connectedJabber +
                ", connectedAt=" + connectedAt +
                ", presenceDetails=" + presenceDetails +
                ", settingsDetails=" + settingsDetails +
                '}';
    }
}
