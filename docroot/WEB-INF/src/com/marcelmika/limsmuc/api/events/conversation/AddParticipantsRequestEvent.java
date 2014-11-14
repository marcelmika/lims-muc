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
import com.marcelmika.limsmuc.api.events.RequestEvent;
import com.marcelmika.limsmuc.api.entity.BuddyCollectionDetails;


/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:41 PM
 */
public class AddParticipantsRequestEvent extends RequestEvent {

    private BuddyDetails buddy;
    private String conversationId;
    private BuddyCollectionDetails buddyCollectionDetails;

    public AddParticipantsRequestEvent(BuddyDetails buddy,
                                       String conversationId,
                                       BuddyCollectionDetails buddyCollectionDetails) {
        this.buddy = buddy;
        this.conversationId = conversationId;
        this.buddyCollectionDetails = buddyCollectionDetails;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public String getConversationId() {
        return conversationId;
    }

    public BuddyCollectionDetails getBuddyCollectionDetails() {
        return buddyCollectionDetails;
    }
}
