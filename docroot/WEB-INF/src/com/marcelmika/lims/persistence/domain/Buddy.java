/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.domain;

import com.liferay.portal.model.User;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.persistence.generated.model.Participant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:55 PM
 */
public class Buddy {

    private Long buddyId;
    private String fullName;
    private String screenName;
    private String password;
    private Presence presence;
    private Date presenceUpdatedAt;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

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
        buddy.fullName = buddyDetails.getFullName();
        buddy.screenName = buddyDetails.getScreenName();
        buddy.password = buddyDetails.getPassword();

        // Relations
        if (buddyDetails.getPresenceDetails() != null) {
            buddy.presence = Presence.fromPresenceDetails(buddyDetails.getPresenceDetails());
        }

        return buddy;
    }


    /**
     * Factory method which creates new list of Buddies from the list of BuddyDetails
     *
     * @param detailsList list of buddy details
     * @return List<Buddy> of buddies
     */
    public static List<Buddy> fromBuddyDetailsList(List<BuddyDetails> detailsList) {
        // Create new list of buddies
        List<Buddy> buddies = new ArrayList<Buddy>();

        // Iterate through details and create buddy based on that
        for (BuddyDetails details : detailsList) {
            buddies.add(Buddy.fromBuddyDetails(details));
        }

        return buddies;
    }

    /**
     * Factory method which creates buddy from participant model
     *
     * @param participant Participant
     * @return Buddy
     */
    public static Buddy fromParticipantModel(Participant participant) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data from model
        buddy.setBuddyId(participant.getParticipantId());

        return buddy;
    }

    /**
     * Factory method which creates buddy from plain java object usually retrieved from database
     *
     * @param object       Object[] array which contains buddy data
     * @param firstElement determines first element in Object[] where the buddy serialization should start
     * @return Buddy
     */
    public static Buddy fromPlainObject(Object[] object, int firstElement) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data from object
        buddy.buddyId = (Long) object[firstElement++];
        buddy.screenName = (String) object[firstElement++];
        buddy.fullName = String.format("%s %s %s",
                object[firstElement++],
                object[firstElement++],
                object[firstElement++]);

        String presence = String.format("%s", object[firstElement++]);
        if (presence != null) {
            buddy.presence = Presence.fromDescription(presence);
        }

        Calendar presenceUpdatedAt = (Calendar) object[firstElement];
        if (presenceUpdatedAt != null) {
            buddy.presenceUpdatedAt = presenceUpdatedAt.getTime();
        }

        return buddy;
    }

    /**
     * Factory method which create buddy form Liferay's user
     *
     * @param user User
     * @return Buddy
     */
    public static Buddy fromUser(User user) {
        // Create new buddy
        Buddy buddy = new Buddy();
        // Map data from user
        buddy.buddyId = user.getUserId();
        buddy.screenName = user.getScreenName();
        buddy.fullName = user.getFullName();

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
        details.setFullName(fullName);
        details.setScreenName(screenName);
        details.setPassword(password);

        // Relations
        if (presence != null) {
            details.setPresenceDetails(presence.toPresenceDetails());
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

    public Date getPresenceUpdatedAt() {
        return presenceUpdatedAt;
    }

    public void setPresenceUpdatedAt(Date presenceUpdatedAt) {
        this.presenceUpdatedAt = presenceUpdatedAt;
    }

    @Override
    public int hashCode() {
        return buddyId != null ? buddyId.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Buddy buddy = (Buddy) o;

        return !(buddyId != null ? !buddyId.equals(buddy.buddyId) : buddy.buddyId != null);
    }

    @Override
    public String toString() {
        return "Buddy{" +
                "buddyId=" + buddyId +
                ", fullName='" + fullName + '\'' +
                ", screenName='" + screenName + '\'' +
                ", password='" + password + '\'' +
                ", presence=" + presence +
                '}';
    }
}
