/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.marcelmika.limsmuc.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.SendMessageResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 8:08 PM
 */
public interface ConversationJabberService {

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event);

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    SendMessageResponseEvent sendMessage(SendMessageRequestEvent event);

}
