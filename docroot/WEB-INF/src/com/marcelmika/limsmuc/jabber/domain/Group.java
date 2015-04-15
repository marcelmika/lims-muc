/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.domain;

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
    private String name;
    private List<Buddy> buddies = new ArrayList<Buddy>();
    // List strategy
    private Environment.BuddyListStrategy listStrategy = Environment.BuddyListStrategy.JABBER;
    // Pagination related to the buddies
    private Page page;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

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
        group.groupId = details.getGroupId();
        group.name = details.getName();
        group.listStrategy = details.getListStrategy();

        // Relations
        if (details.getBuddies() != null) {
            group.buddies = Buddy.fromBuddyDetails(details.getBuddies());
        }

        if (details.getPage() != null) {
            group.page = Page.fromPageDetails(details.getPage());
        }

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
        details.setGroupId(groupId);
        details.setName(name);
        details.setListStrategy(listStrategy);

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

    /**
     * Creates a duplicated of the group with a size of buddies based on the page
     *
     * @param page Page
     * @return Group
     */
    public Group duplicate(Page page) {
        Group group = new Group();

        // Map properties
        group.groupId = groupId;
        group.name = name;
        group.listStrategy = listStrategy;

        // Get number and size
        int number = page.getNumber();
        int size = page.getSize();

        // Calculated start and end
        int start = number * size;
        int end = start + size;

        int buddiesSize = buddies.size();

        if (end > buddiesSize) {
            end = buddiesSize;
        }

        if (start > buddiesSize) {
            start = buddiesSize;
        }

        if (start > end) {
            start = end;
        }

        // Add buddies
        group.buddies = buddies.subList(start, end);

        // Compose page
        Page groupPage = new Page();
        groupPage.setNumber(number);
        groupPage.setSize(size);
        groupPage.setTotalElements(buddiesSize);
        groupPage.setTotalPages((int) Math.ceil(buddiesSize / (double) size));

        // Add page
        group.page = groupPage;

        return group;
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

    public void setBuddies(List<Buddy> buddies) {
        this.buddies = buddies;
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Environment.BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(Environment.BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", name='" + name + '\'' +
                ", buddies=" + buddies +
                ", page=" + page +
                '}';
    }
}
