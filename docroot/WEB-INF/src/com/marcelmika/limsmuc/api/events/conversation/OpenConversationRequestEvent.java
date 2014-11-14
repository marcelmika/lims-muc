/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.conversation;

import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/20/14
 * Time: 8:14 AM
 */
public class OpenConversationRequestEvent extends RequestEvent {

    private Long buddyId;
    private String conversationId;

    public OpenConversationRequestEvent(Long buddyId, String conversationId) {
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
