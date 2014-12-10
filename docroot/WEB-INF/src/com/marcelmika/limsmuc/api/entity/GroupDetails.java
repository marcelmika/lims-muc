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

    private String name;
    private List<BuddyDetails> buddies = new ArrayList<BuddyDetails>();
    private Date lastModified;
    private Environment.BuddyListSocialRelation socialRelation;

    public List<BuddyDetails> getBuddies() {
        return buddies;
    }

    public void addBuddyDetails(BuddyDetails buddy) {
        buddies.add(buddy);
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
}
