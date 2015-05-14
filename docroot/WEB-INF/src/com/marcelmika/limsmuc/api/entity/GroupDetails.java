/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.entity;

import com.marcelmika.limsmuc.api.environment.Environment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:11 PM
 */
public class GroupDetails {

    // -------------------------------------------------------------------------------------------
    // Properties
    // -------------------------------------------------------------------------------------------

    private Long groupId;
    private String name;
    private List<BuddyDetails> buddies = new ArrayList<BuddyDetails>();
    private PageDetails page;
    private Date lastModified;
    private Environment.BuddyListStrategy listStrategy;
    private Environment.BuddyListGroup listGroup;
    private Environment.BuddyListSocialRelation socialRelation;


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public List<BuddyDetails> getBuddies() {
        return buddies;
    }

    public void addBuddyDetails(BuddyDetails buddy) {
        buddies.add(buddy);
    }

    public PageDetails getPage() {
        return page;
    }

    public void setPage(PageDetails page) {
        this.page = page;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Environment.BuddyListSocialRelation getSocialRelation() {
        return socialRelation;
    }

    public void setSocialRelation(Environment.BuddyListSocialRelation socialRelation) {
        this.socialRelation = socialRelation;
    }

    public Environment.BuddyListGroup getListGroup() {
        return listGroup;
    }

    public void setListGroup(Environment.BuddyListGroup listGroup) {
        this.listGroup = listGroup;
    }

    public Environment.BuddyListStrategy getListStrategy() {
        return listStrategy;
    }

    public void setListStrategy(Environment.BuddyListStrategy listStrategy) {
        this.listStrategy = listStrategy;
    }

    // -------------------------------------------------------------------------------------------
    // Override
    // -------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "GroupDetails{" +
                "socialRelation=" + socialRelation +
                ", listGroup=" + listGroup +
                ", listStrategy=" + listStrategy +
                ", lastModified=" + lastModified +
                ", page=" + page +
                ", buddies=" + buddies +
                ", name='" + name + '\'' +
                ", groupId=" + groupId +
                '}';
    }
}
