/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.domain;

import com.marcelmika.limsmuc.api.entity.GroupCollectionDetails;
import com.marcelmika.limsmuc.api.entity.GroupDetails;
import com.marcelmika.limsmuc.api.environment.Environment;

import java.util.*;


/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 5:29 PM
 */
public class GroupCollection {

    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());
    private Date lastModified = Calendar.getInstance().getTime();
    private Environment.BuddyListStrategy listStrategy;

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

        // Modification date
        details.setLastModified(lastModified);
        // List strategy
        details.setListStrategy(listStrategy);

        return details;
    }

    /**
     * Adds group to groups list
     *
     * @param group Group
     */
    public void addGroup(Group group) {
        groups.add(group);
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public Environment.BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(Environment.BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }
}
