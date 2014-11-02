/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.api.events.conversation;

import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/25/14
 * Time: 8:30 PM
 */
public class ResetUnreadMessagesCounterRequestEvent extends RequestEvent {

    private Long buddyId;
    private String conversationId;

    public ResetUnreadMessagesCounterRequestEvent(Long buddyId, String conversationId) {
        this.buddyId = buddyId;
        this.conversationId = conversationId;
    }

    public Long getBuddyId() {
        return buddyId;
    }

    public String getConversationId() {
        return conversationId;
    }

}
