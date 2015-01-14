/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.domain;

import com.marcelmika.limsmuc.api.entity.GroupCollectionDetails;
import com.marcelmika.limsmuc.api.entity.GroupDetails;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 6:10 PM
 */
public class GroupCollection {

    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());
    private Date lastModified;
    private BuddyListStrategy listStrategy;
    private boolean loading;
    private int etag;

    /**
     * Factory method that creates group collection from group collection details
     *
     * @param details GroupCollectionDetails
     * @return GroupCollection
     */
    public static GroupCollection fromGroupCollectionDetails(GroupCollectionDetails details) {
        // Create new group
        GroupCollection groupCollection = new GroupCollection();
        // Map data to group details
        groupCollection.lastModified = details.getLastModified();
        groupCollection.listStrategy = details.getListStrategy();
        groupCollection.loading = details.isLoading();

        if (details.getLastModified() != null) {
            // Etag is made of the last modification date and the list strategy type. Thus if the group collection
            // changes its modification date or its type the client will be forced to download updated version
            groupCollection.etag = details.getLastModified().hashCode() + groupCollection.listStrategy.hashCode();
        }

        // Relations
        if (details.getGroups() != null) {
            for (GroupDetails groupDetail : details.getGroups()) {
                groupCollection.groups.add(Group.fromGroupDetails(groupDetail));
            }
        }

        return groupCollection;
    }


    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    public int getEtag() {
        return etag;
    }

    public void setEtag(int etag) {
        this.etag = etag;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }
}
