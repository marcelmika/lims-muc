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
import com.marcelmika.limsmuc.api.entity.ConversationDetails;
import com.marcelmika.limsmuc.api.entity.MessageDetails;
import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 3:50 PM
 */
public class SendMessageRequestEvent extends RequestEvent {

    private BuddyDetails buddyDetails;
    private ConversationDetails conversationDetails;
    private MessageDetails messageDetails;

    public SendMessageRequestEvent(BuddyDetails buddyDetails,
                                   ConversationDetails conversationDetails,
                                   MessageDetails messageDetails) {
        this.buddyDetails = buddyDetails;
        this.conversationDetails = conversationDetails;
        this.messageDetails = messageDetails;
    }

    public BuddyDetails getBuddyDetails() {
        return buddyDetails;
    }

    public ConversationDetails getConversationDetails() {
        return conversationDetails;
    }

    public MessageDetails getMessageDetails() {
        return messageDetails;
    }
}
