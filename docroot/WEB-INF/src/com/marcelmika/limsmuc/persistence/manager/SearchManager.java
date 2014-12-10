/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.manager;

import com.marcelmika.limsmuc.persistence.domain.Buddy;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/09/14
 * Time: 10:03
 */
public interface SearchManager {

    /**
     * Returns a list of buddies based on the search query. The search will be performed
     * in first name, middle name, last name, screen name and email.
     *
     * @param userId      Long
     * @param searchQuery String
     * @param start       of the list
     * @param end         of the list
     * @return List of buddies
     * @throws Exception
     */
    public List<Buddy> searchBuddies(Long userId, String searchQuery, int start, int end) throws Exception;

}
