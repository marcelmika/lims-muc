/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.domain;

import com.marcelmika.lims.api.entity.GroupDetails;
import com.marcelmika.lims.api.environment.Environment.BuddyListSocialRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:37 PM
 */
public class Group {

    private String name;
    private List<Buddy> buddies = new ArrayList<Buddy>();
    private BuddyListSocialRelation socialRelation;

    /**
     * Create new group and maps data from group details
     *
     * @param details GroupDetails
     * @return Group
     */
    public static Group fromGroupDetails(GroupDetails details) {
        // Create new group
        Group group = new Group();
        // Map data to group details
        group.name = details.getName();
        group.socialRelation = details.getSocialRelation();

        // Relations
        if (details.getBuddies() != null) {
            group.buddies = Buddy.fromBuddyDetailsList(details.getBuddies());
        }

        return group;
    }

    /**
     * Create a list of groups from a list of group details
     *
     * @param groupDetails list of group details
     * @return list of groups
     */
    public static List<Group> fromGroupDetails(List<GroupDetails> groupDetails) {
        List<Group> groups = new ArrayList<Group>();

        // Map all group details to group
        for (GroupDetails details : groupDetails) {
            groups.add(Group.fromGroupDetails(details));
        }

        return groups;
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
        return name;
    }


}
