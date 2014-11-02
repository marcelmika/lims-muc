/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.buddy;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/09/14
 * Time: 23:01
 */
public class SearchBuddiesRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;
    private String searchQuery;

    public SearchBuddiesRequestEvent(BuddyDetails buddyDetails, String searchQuery) {
        this.buddyDetails = buddyDetails;
        this.searchQuery = searchQuery;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }

    public String getSearchQuery() {
        return searchQuery;
    }
}
