/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.request.parameters;

import com.marcelmika.lims.portal.properties.InputLimits;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/14
 * Time: 20:02
 */
public class SearchBuddiesParameters {

    private String searchQuery;

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        // Limit the maximal search query size
        if (searchQuery.length() > InputLimits.SEARCH_QUERY_MAX_SIZE) {
            searchQuery = searchQuery.substring(0, InputLimits.SEARCH_QUERY_MAX_SIZE);
        }

        this.searchQuery = searchQuery;
    }

    @Override
    public String toString() {
        return "SearchBuddiesParameters{" +
                "searchQuery='" + searchQuery + '\'' +
                '}';
    }
}
