/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.marcelmika.limsmuc.api.events.group.GetGroupRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupResponseEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsRequestEvent;
import com.marcelmika.limsmuc.api.events.group.GetGroupsResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/1/14
 * Time: 8:07 PM
 */
public interface GroupJabberService {

    /**
     * Get all groups related to the particular user
     *
     * @param event request event for method
     * @return response event for  method
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
