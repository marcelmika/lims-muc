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
import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.entity.MessagePaginationDetails;
import com.marcelmika.lims.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:58 PM
 */
public class ReadSingleUserConversationRequestEvent extends RequestEvent {

    private final BuddyDetails participant;
    private final ConversationDetails conversation;
    private final MessagePaginationDetails pagination;

    public ReadSingleUserConversationRequestEvent(final BuddyDetails participant,
                                                  final ConversationDetails conversation,
                                                  final MessagePaginationDetails pagination) {
        this.participant = participant;
        this.conversation = conversation;
        this.pagination = pagination;
    }

    public BuddyDetails getParticipant() {
        return participant;
    }

    public ConversationDetails getConversation() {
        return conversation;
    }

    public MessagePaginationDetails getPagination() {
        return pagination;
    }
}
