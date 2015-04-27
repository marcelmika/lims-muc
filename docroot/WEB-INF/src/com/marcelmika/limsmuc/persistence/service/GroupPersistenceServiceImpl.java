/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.group.GetGroupRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupResponseEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.limsmuc.persistence.domain.Buddy;
import com.marcelmika.limsmuc.persistence.domain.Group;
import com.marcelmika.limsmuc.persistence.domain.GroupCollection;
import com.marcelmika.limsmuc.persistence.domain.Page;
import com.marcelmika.limsmuc.persistence.exception.ForbiddenException;
import com.marcelmika.limsmuc.persistence.exception.PersistenceException;
import com.marcelmika.limsmuc.persistence.manager.GroupManager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/1/14
 * Time: 5:06 PM
 */
public class GroupPersistenceServiceImpl implements GroupPersistenceService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(GroupPersistenceServiceImpl.class);

    // Dependencies
    GroupManager groupManager;

    /**
     * Constructor
     *
     * @param groupManager GroupManager
     */
    public GroupPersistenceServiceImpl(final GroupManager groupManager) {
        this.groupManager = groupManager;
    }

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event) {

        // Map entities
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        // Check params
        if (buddy.getBuddyId() == null) {
            return GetGroupsResponseEvent.failure(GetGroupsResponseEvent.Status.ERROR_WRONG_PARAMETERS);
        }

        // Create page
        Page page = new Page();
        page.setNumber(0); // We always start from the beginning of the list
        page.setSize(Environment.getBuddyListMaxBuddies());

        try {
            // Get groups from manager
            GroupCollection groupCollection = groupManager.getGroups(buddy.getBuddyId(), page);

            // Success
            return GetGroupsResponseEvent.success(groupCollection.toGroupCollectionDetails());
        }
        // Failure
        catch (Exception exception) {
            return GetGroupsResponseEvent.failure(
                    GetGroupsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
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

        // Map entities
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddy());

        // Check params
        if (buddy.getBuddyId() == null || event.getNumber() == null) {
            return GetGroupResponseEvent.failure(GetGroupResponseEvent.Status.ERROR_WRONG_PARAMETERS);
        }

        // Create page
        Page page = new Page();
        page.setNumber(event.getNumber());
        page.setSize(Environment.getBuddyListMaxBuddies());

        try {

            // Get group from manager
            Group group = groupManager.getGroup(
                    buddy.getBuddyId(), event.getGroupId(), event.getListStrategy(), event.getListGroup(), page
            );

            // Not found
            if (group == null) {
                return GetGroupResponseEvent.failure(
                        GetGroupResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Success
            return GetGroupResponseEvent.success(group.toGroupDetails());
        }
        // Failure persistence
        catch (PersistenceException exception) {
            return GetGroupResponseEvent.failure(
                    GetGroupResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
        // Failure forbidden
        catch (ForbiddenException exception) {
            return GetGroupResponseEvent.failure(
                    GetGroupResponseEvent.Status.ERROR_FORBIDDEN, exception
            );
        }
    }
}
