/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.entity;

import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;

import java.util.Date;
import java.util.List;

/**
 * Simple container that holds groups
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 5:26 PM
 */
public class GroupCollectionDetails {

    private List<GroupDetails> groups;
    private Date lastModified;
    private BuddyListStrategy listStrategy;

    public List<GroupDetails> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDetails> groups) {
        this.groups = groups;
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

    @Override
    public String toString() {
        return "GroupCollectionDetails{" +
                "groups=" + groups +
                ", lastModified=" + lastModified +
                ", listStrategy=" + listStrategy +
                '}';
    }
}
