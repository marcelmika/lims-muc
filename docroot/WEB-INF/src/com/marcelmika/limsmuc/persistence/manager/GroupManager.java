/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.manager;

import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.limsmuc.persistence.domain.Group;
import com.marcelmika.limsmuc.persistence.domain.GroupCollection;
import com.marcelmika.limsmuc.persistence.domain.Page;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/4/14
 * Time: 9:44 AM
 */
public interface GroupManager {

    /**
     * Returns Group Collection of all groups related to the user
     *
     * @param userId Long id of the user
     * @param page   Page pagination object
     * @return GroupCollection of groups related to the user
     * @throws Exception
     */
    public GroupCollection getGroups(Long userId, Page page) throws Exception;

    /**
     * Returns Group
     *
     * @param userId       Long id of the user
     * @param groupId      Long id of the group
     * @param listStrategy String group list strategy
     * @param page         Page pagination object
     * @return Group
     * @throws Exception
     */
    public Group getGroup(Long userId, Long groupId, BuddyListStrategy listStrategy, Page page) throws Exception;

}
