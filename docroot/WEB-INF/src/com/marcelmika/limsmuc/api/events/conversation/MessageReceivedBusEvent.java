/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.conversation;

import com.marcelmika.limsmuc.api.entity.ConversationDetails;
import com.marcelmika.limsmuc.api.entity.MessageDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/12/14
 * Time: 16:04
 */
public class MessageReceivedBusEvent {

    private ConversationDetails conversation;
    private MessageDetails message;

    public MessageReceivedBusEvent(ConversationDetails conversation, MessageDetails message) {
        this.conversation = conversation;
        this.message = message;
    }

    public ConversationDetails getConversation() {
        return conversation;
    }

    public MessageDetails getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "MessageReceivedBusEvent{" +
                "conversation=" + conversation +
                ", message=" + message +
                '}';
    }
}
