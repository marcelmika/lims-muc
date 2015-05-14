/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.marcelmika.limsmuc.api.events.conversation.AddParticipantsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.AddParticipantsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.CloseConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.CloseConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.LeaveConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.LeaveConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.OpenConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.OpenConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.ReadSingleUserConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.ReadSingleUserConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.ResetUnreadMessagesCounterRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.ResetUnreadMessagesCounterResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.SendMessageResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.SwitchConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.SwitchConversationsResponseEvent;

/**
 * Serves as a port to the business logic related to conversation.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:01 PM
 */
public interface ConversationCoreService {

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event);

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event);

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event);

    /**
     * Reads messages from conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    ReadSingleUserConversationResponseEvent readConversation(ReadSingleUserConversationRequestEvent event);

    /**
     * Opens existing conversation
     *
     * @param event request event for method
     * @return response event for method
     * @deprecated
     */
    OpenConversationResponseEvent openConversation(OpenConversationRequestEvent event);

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event request event for method
     * @return response event for method
     */
    CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event);

    /**
     * Reset counter of unread messages (usually displayed in badge) for the particular user and conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    ResetUnreadMessagesCounterResponseEvent resetUnreadMessagesCounter(ResetUnreadMessagesCounterRequestEvent event);

    /**
     * Removes buddy from the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event);

    /**
     * Switch conversations positions
     *
     * @param event request event for method
     * @return response event for method
     */
    SwitchConversationsResponseEvent switchConversations(SwitchConversationsRequestEvent event);

    /**
     * Adds buddies to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    AddParticipantsResponseEvent addParticipants(AddParticipantsRequestEvent event);

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    SendMessageResponseEvent sendMessage(SendMessageRequestEvent event);


}
