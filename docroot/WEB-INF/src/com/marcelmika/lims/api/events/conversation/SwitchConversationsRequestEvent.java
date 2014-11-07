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
 * Date: 07/11/14
 * Time: 13:11
 */
public class SwitchConversationsRequestEvent extends RequestEvent {

    private BuddyDetails buddy;
    private String firstConversationId;
    private String secondConversationId;


    public SwitchConversationsRequestEvent(BuddyDetails buddy,
                                           String firstConversationId,
                                           String secondConversationId) {
        this.buddy = buddy;
        this.firstConversationId = firstConversationId;
        this.secondConversationId = secondConversationId;
    }

    public BuddyDetails getBuddy() {
        return buddy;
    }

    public String getFirstConversationId() {
        return firstConversationId;
    }

    public String getSecondConversationId() {
        return secondConversationId;
    }
}
