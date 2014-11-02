/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.domain;

import com.liferay.portal.model.UserGroup;
import com.marcelmika.lims.api.entity.GroupDetails;
import com.marcelmika.lims.api.environment.Environment.BuddyListSocialRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:12 PM
 */
public class Group {

    private String name;
    private List<Buddy> buddies = new ArrayList<Buddy>();
    private BuddyListSocialRelation socialRelation;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Creates new group and maps data from group details
     *
     * @param groupDetails GroupDetails
     * @return Group
     */
    public static Group fromGroupDetails(GroupDetails groupDetails) {
        // Create new group
        Group group = new Group();
        // Map data to group details
        group.name = groupDetails.getName();
        group.socialRelation = groupDetails.getSocialRelation();

        // Relations
        if (groupDetails.getBuddies() != null) {
            group.buddies = Buddy.fromBuddyDetailsList(groupDetails.getBuddies());
        }

        return group;
    }

    /**
     * Factory method which creates group from plain java object usually retrieved from database
     *
     * @param object       Object[] array which contains group data
     * @param firstElement determines first element in Object[] where the group serialization should start
     * @return Group
     */
    public static Group fromPlainObject(Object[] object, int firstElement) {
        // Create new group
        Group group = new Group();
        // Map data from object
        group.name = (String) object[firstElement];

        return group;
    }

    /**
     * Factory method which create group from the user group
     *
     * @param userGroup UserGroup
     * @return Group
     */
    public static Group fromUserGroup(UserGroup userGroup) {
        // Crate new group
        Group group = new Group();
        // Map data from the user group
        group.name = userGroup.getName();

        return group;
    }

    /**
     * Maps group to group details
     *
     * @return GroupDetails
     */
    public GroupDetails toGroupDetails() {
        // Create new group details
        GroupDetails details = new GroupDetails();
        // Map data from group
        details.setName(name);
        details.setSocialRelation(socialRelation);

        // Relations
        if (buddies != null) {
            for (Buddy buddy : buddies) {
                details.addBuddyDetails(buddy.toBuddyDetails());
            }
        }

        return details;
    }

    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public List<Buddy> getBuddies() {
        return buddies;
    }

    public void addBuddy(Buddy buddy) {
        this.buddies.add(buddy);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BuddyListSocialRelation getSocialRelation() {
        return socialRelation;
    }

    public void setSocialRelation(BuddyListSocialRelation socialRelation) {
        this.socialRelation = socialRelation;
    }

    /**
     * String representation of the Group.
     *
     * @return A String representation of the Group.
     */
    @Override
    public String toString() {
        return String.format("Group: %s:", name);
    }


}
