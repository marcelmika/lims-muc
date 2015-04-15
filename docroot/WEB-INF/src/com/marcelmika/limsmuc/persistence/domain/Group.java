/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.domain;

import com.liferay.portal.model.UserGroup;
import com.marcelmika.limsmuc.api.entity.GroupDetails;
import com.marcelmika.limsmuc.api.environment.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/14/14
 * Time: 3:12 PM
 */
public class Group {

    // -------------------------------------------------------------------------------------------
    // Properties
    // -------------------------------------------------------------------------------------------

    // Group id
    private Long groupId;
    // Name of the group
    private String name;
    // Buddies related to the group
    private List<Buddy> buddies = new ArrayList<Buddy>();
    // Pagination related to the buddies
    private Page page;
    // List strategy
    private Environment.BuddyListStrategy listStrategy;
    private Environment.BuddyListGroup listGroup;
    // Social relation type
    private Environment.BuddyListSocialRelation socialRelation;


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method
     *
     * @param details GroupDetails
     * @return Group
     */
    public static Group fromGroupDetails(GroupDetails details) {
        Group group = new Group();

        // Properties
        group.groupId = details.getGroupId();
        group.name = details.getName();
        group.listStrategy = details.getListStrategy();
        group.listGroup = details.getListGroup();
        group.socialRelation = details.getSocialRelation();

        // Relations
        if (details.getBuddies() != null) {
            group.buddies = Buddy.fromBuddyDetailsList(details.getBuddies());
        }

        if (details.getPage() != null) {
            group.page = Page.fromPageDetails(details.getPage());
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
        Group group = new Group();

        // Properties
        group.groupId = (Long) object[firstElement++];
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
        Group group = new Group();

        // Properties
        group.name = userGroup.getName();

        return group;
    }

    /**
     * Mapping method
     *
     * @return GroupDetails
     */
    public GroupDetails toGroupDetails() {
        GroupDetails details = new GroupDetails();

        // Properties
        details.setGroupId(groupId);
        details.setName(name);
        details.setListStrategy(listStrategy);
        details.setListGroup(listGroup);
        details.setSocialRelation(socialRelation);

        // Relations
        if (buddies != null) {
            for (Buddy buddy : buddies) {
                details.addBuddyDetails(buddy.toBuddyDetails());
            }
        }

        if (page != null) {
            details.setPage(page.toPageDetails());
        }

        return details;
    }

    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<Buddy> getBuddies() {
        return buddies;
    }

    public void addBuddy(Buddy buddy) {
        this.buddies.add(buddy);
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Environment.BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(Environment.BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }

    public Environment.BuddyListGroup getListGroup() {
        return listGroup;
    }

    public void setListGroup(Environment.BuddyListGroup listGroup) {
        this.listGroup = listGroup;
    }

    public Environment.BuddyListSocialRelation getSocialRelation() {
        return socialRelation;
    }

    public void setSocialRelation(Environment.BuddyListSocialRelation socialRelation) {
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
