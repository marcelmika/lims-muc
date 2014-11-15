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
import com.marcelmika.limsmuc.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsResponseEvent;
import com.marcelmika.limsmuc.persistence.domain.Buddy;
import com.marcelmika.limsmuc.persistence.domain.GroupCollection;
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
        // Map buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        // Check params
        if (buddy.getBuddyId() == null) {
            return GetGroupsResponseEvent.failure(GetGroupsResponseEvent.Status.ERROR_WRONG_PARAMETERS);
        }

        try {
            // TODO: Implement pagination
            int start = 0;
            int end = Environment.getBuddyListMaxBuddies();

            // Get groups from manager
            GroupCollection groupCollection = groupManager.getGroups(buddy.getBuddyId(), start, end);

            // Call success
            return GetGroupsResponseEvent.success(groupCollection.toGroupCollectionDetails());
        }
        // Something went wrong
        catch (Exception exception) {
            // Failure
            return GetGroupsResponseEvent.failure(
                    GetGroupsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }
}