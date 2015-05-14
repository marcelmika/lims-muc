/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.marcelmika.limsmuc.api.events.group.GetGroupRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupResponseEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/1/14
 * Time: 5:05 PM
 */
public interface GroupPersistenceService {

    /**
     * Get all groups related to the particular user
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    GetGroupsResponseEvent getGroups(GetGroupsRequestEvent event);

    /**
     * Returns a particular group
     *
     * @param event RequestEvent
     * @return ResponseEvent
     */
    GetGroupResponseEvent getGroup(GetGroupRequestEvent event);
}
