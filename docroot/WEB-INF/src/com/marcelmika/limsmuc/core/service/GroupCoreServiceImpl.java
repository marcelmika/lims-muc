/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.group.GetGroupRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupResponseEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.limsmuc.jabber.service.GroupJabberService;
import com.marcelmika.limsmuc.persistence.service.GroupPersistenceService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:38 PM
 */
public class GroupCoreServiceImpl implements GroupCoreService {

    // Dependencies
    GroupJabberService groupJabberService;
    GroupPersistenceService groupPersistenceService;

    /**
     * Constructor
     *
     * @param groupJabberService jabber service
     */
    public GroupCoreServiceImpl(final GroupJabberService groupJabberService,
                                final GroupPersistenceService groupPersistenceService) {
        this.groupJabberService = groupJabberService;
        this.groupPersistenceService = groupPersistenceService;
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {

        boolean isJabber = Environment.getBuddyListStrategy() == Environment.BuddyListStrategy.JABBER
                && Environment.isJabberEnabled();

        // Take the groups from jabber only if the jabber is enabled
        if (isJabber) {
            return groupJabberService.getGroups(event);
        }
        // Otherwise, take them from Liferay
        else {
            return groupPersistenceService.getGroups(event);
        }
    }

    /**
     * Returns a particular group
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    @Override
    public GetGroupResponseEvent getGroup(GetGroupRequestEvent event) {
        return groupPersistenceService.getGroup(event);
    }

}
