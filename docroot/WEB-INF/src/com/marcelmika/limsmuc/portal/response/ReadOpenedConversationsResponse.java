/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.response;

import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.Conversation;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 01/04/15
 * Time: 10:05
 */
public class ReadOpenedConversationsResponse {

    private List<Conversation> conversations;
    private List<Buddy> changedPresences;

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
    }

    public List<Buddy> getChangedPresences() {
        return changedPresences;
    }

    public void setChangedPresences(List<Buddy> changedPresences) {
        this.changedPresences = changedPresences;
    }
}
