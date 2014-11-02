/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:16 AM
 */
public class LeaveConversationRequestEvent extends RequestEvent {

    private BuddyDetails buddy;
    private String conversationId;

    public LeaveConversationRequestEvent(BuddyDetails buddy, String conversationId) {
        this.buddy = buddy;
        this.conversationId = conversationId;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public String getConversationId() {
        return conversationId;
    }
}
