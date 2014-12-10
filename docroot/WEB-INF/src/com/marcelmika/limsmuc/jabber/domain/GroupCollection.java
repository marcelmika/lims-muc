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

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 5/3/14
 * Time: 5:29 PM
 */
public class GroupCollection {

    private Date lastModified = Calendar.getInstance().getTime();
    private List<Group> groups = Collections.synchronizedList(new ArrayList<Group>());

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
        for(Group group : this.groups) {
            groups.add(group.toGroupDetails());
        }
        details.setGroups(groups);

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

    public Date getLastModified() {
        return lastModified;
    }
}
