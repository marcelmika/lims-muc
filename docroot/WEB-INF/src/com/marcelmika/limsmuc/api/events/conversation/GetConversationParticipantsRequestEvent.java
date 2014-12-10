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
import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 28/08/14
 * Time: 10:35
 */
public class GetConversationParticipantsRequestEvent extends RequestEvent {

    private final ConversationDetails conversation;

    public GetConversationParticipantsRequestEvent(final ConversationDetails conversation) {
        this.conversation = conversation;
    }

    public ConversationDetails getConversation() {
        return conversation;
    }
}
