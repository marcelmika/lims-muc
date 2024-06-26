/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.domain;

import com.marcelmika.limsmuc.api.entity.GroupCollectionDetails;
import com.marcelmika.limsmuc.api.entity.GroupDetails;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 5:29 PM
 */
public class GroupCollection {

    private Date lastModified = Calendar.getInstance().getTime();
    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());
    private BuddyListStrategy listStrategy;
    private boolean loading;

    /**
     * Maps a list of groups to a list of group details
     *
     * @return list of group details
     */
    public GroupCollectionDetails toGroupCollectionDetails() {
        // Create new collection
        GroupCollectionDetails details = new GroupCollectionDetails();
        // Groups
        List<GroupDetails> groups = new ArrayList<GroupDetails>();
        for (Group group : this.groups) {
            groups.add(group.toGroupDetails());
        }
        details.setGroups(groups);
        details.setListStrategy(listStrategy);
        details.setLoading(loading);

        // Modification date
        details.setLastModified(lastModified);

        return details;
    }


    public void addGroups(List<Group> groups) {
        // Overwrite current groups
        this.groups.clear();
        this.groups.addAll(groups);
        // Update modification date
        Calendar calendar = Calendar.getInstance();
        this.lastModified = calendar.getTime();
    }

    /**
     * Creates a duplicate of the group collection
     *
     * @param page Page
     * @return GroupCollection
     */
    public GroupCollection duplicate(Page page) {
        GroupCollection groupCollection = new GroupCollection();

        // Map properties
        groupCollection.listStrategy = listStrategy;
        groupCollection.lastModified = lastModified;
        groupCollection.loading = loading;

        // Duplicate groups
        List<Group> duplicatedGroups = new LinkedList<Group>();
        for (Group group : groups) {
            duplicatedGroups.add(group.duplicate(page));
        }

        // Set the groups to collection
        groupCollection.groups = duplicatedGroups;

        return groupCollection;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }

    @Override
    public String toString() {
        return "GroupCollection{" +
                "lastModified=" + lastModified +
                ", groups=" + groups +
                ", listStrategy=" + listStrategy +
                ", loading=" + loading +
                '}';
    }
}
