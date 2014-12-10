/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.conversation;

import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.entity.ConversationPaginationDetails;
import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 5:50 PM
 */
public class GetConversationsRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;
    private ConversationPaginationDetails pagination;

    public GetConversationsRequestEvent(BuddyDetails buddyDetails, ConversationPaginationDetails pagination) {
        this.buddyDetails = buddyDetails;
        this.pagination = pagination;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }

    public ConversationPaginationDetails getPagination() {
        return pagination;
    }
}
