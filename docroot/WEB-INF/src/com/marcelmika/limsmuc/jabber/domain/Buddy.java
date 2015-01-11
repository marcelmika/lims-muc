/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.domain;

import com.liferay.portal.model.User;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.jabber.utils.Jid;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.RosterEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/2/14
 * Time: 6:21 PM
 */
public class Buddy {

    private Long buddyId;
    private Long companyId;
    private String fullName;
    private String screenName;
    private String password;
    private Presence presence;
    private Boolean connected;
    private Date connectedAt;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Create new Buddy from RosterEntry.
     *
     * @param rosterEntry Smack's RosterEntry.
     * @return Buddy
     */
    public static Buddy fromRosterEntry(RosterEntry rosterEntry) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map properties
        buddy.fullName = rosterEntry.getName();
        buddy.screenName = Jid.getName(rosterEntry.getUser());

        return buddy;
    }

    /**
     * Creates buddy from chat and roster
     *
     * @param chat Chat
     * @return Buddy
     */
    public static Buddy fromChat(Chat chat) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map properties
        buddy.fullName = chat.getParticipant();
        buddy.screenName = Jid.getBareAddress(chat.getParticipant());

        return buddy;
    }

    /**
     * Factory method create a buddy from smack user
     *
     * @param smackUser username
     * @return Buddy
     */
    public static Buddy fromSmackUser(String smackUser) {
        Buddy buddy = new Buddy();
        buddy.screenName = Jid.getName(smackUser);

        return buddy;
    }

    /**
     * Factory method which creates new Buddy object from portal User
     *
     * @param user User
     * @return Buddy
     */
    public static Buddy fromPortalUser(User user) {
        // Create new empty buddy
        Buddy buddy = new Buddy();

        buddy.buddyId = user.getUserId();
        buddy.companyId = user.getCompanyId();
        buddy.screenName = user.getScreenName();
        buddy.password = user.getPassword();

        return buddy;
    }

    /**
     * Factory method which creates new list of Buddies from the list of BuddyDetails
     *
     * @param detailsList list of buddy details
     * @return List<Buddy> of buddies
     */
    public static List<Buddy> fromBuddyDetails(List<BuddyDetails> detailsList) {
        // Create new list of buddies
        List<Buddy> buddies = new ArrayList<Buddy>();

        // Iterate through details and create buddy based on that
        for (BuddyDetails details : detailsList) {
            buddies.add(Buddy.fromBuddyDetails(details));
        }

        return buddies;
    }

    /**
     * Create new user and maps data from user details
     *
     * @param buddyDetails BuddyDetails
     * @return User
     */
    public static Buddy fromBuddyDetails(BuddyDetails buddyDetails) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data to user details
        buddy.buddyId = buddyDetails.getBuddyId();
        buddy.companyId = buddyDetails.getCompanyId();
        buddy.fullName = buddyDetails.getFullName();
        buddy.screenName = buddyDetails.getScreenName();
        buddy.password = buddyDetails.getPassword();
        buddy.connected = buddyDetails.getConnected();
        buddy.connectedAt = buddyDetails.getConnectedAt();

        if (buddyDetails.getPresenceDetails() != null) {
            buddy.presence = Presence.fromPresenceDetails(buddyDetails.getPresenceDetails());
        }

        return buddy;
    }

    /**
     * Maps user to user details
     *
     * @return UserDetails
     */
    public BuddyDetails toBuddyDetails() {
        // Create new user details
        BuddyDetails details = new BuddyDetails();
        // Map data from user
        details.setBuddyId(buddyId);
        details.setCompanyId(companyId);
        details.setFullName(fullName);
        details.setScreenName(screenName);
        details.setPassword(password);
        details.setConnected(connected);
        details.setConnectedAt(connectedAt);

        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
        }

        return details;
    }

    /**
     * Mapping method
     *
     * @param buddies list of Buddies
     * @return list of BuddyDetails
     */
    public static List<BuddyDetails> toBuddyDetails(List<Buddy> buddies) {
        List<BuddyDetails> details = new LinkedList<BuddyDetails>();

        for (Buddy buddy : buddies) {
            details.add(buddy.toBuddyDetails());
        }

        return details;
    }

    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

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

    public Presence getPresence() {
        return presence;
    }

    public void setPresence(Presence presence) {
        this.presence = presence;
    }

    public Boolean getConnected() {
        return connected;
    }

    public void setConnected(Boolean connected) {
        this.connected = connected;
    }

    public Date getConnectedAt() {
        return connectedAt;
    }

    public void setConnectedAt(Date connectedAt) {
        this.connectedAt = connectedAt;
    }

    @Override
    public String toString() {
        return "Buddy{" +
                "buddyId=" + buddyId +
                ", companyId=" + companyId +
                ", fullName='" + fullName + '\'' +
                ", screenName='" + screenName + '\'' +
                ", password='" + password + '\'' +
                ", presence=" + presence +
                ", connected=" + connected +
                ", connectedAt=" + connectedAt +
                '}';
    }
}
