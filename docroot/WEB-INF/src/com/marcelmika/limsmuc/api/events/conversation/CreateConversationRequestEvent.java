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
 * Date: 2/19/14
 * Time: 11:06 PM
 */
public class CreateConversationRequestEvent extends RequestEvent {

    private final BuddyDetails creator;
    private final ConversationDetails conversation;
    private final MessageDetails initialMessage;

    public CreateConversationRequestEvent(final BuddyDetails creator,
                                          final ConversationDetails conversation,
                                          final MessageDetails initialMessage) {

        this.creator = creator;
        this.conversation = conversation;
        this.initialMessage = initialMessage;
    }

    public BuddyDetails getCreator() {
        return creator;
    }

    public ConversationDetails getConversation() {
        return conversation;
    }

    public MessageDetails getInitialMessage() {
        return initialMessage;
    }
}
