/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.service;

import com.marcelmika.lims.api.events.conversation.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:47 AM
 */
public interface ConversationPersistenceService {

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event);

    /**
     * Reads messages from conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public ReadSingleUserConversationResponseEvent readConversation(ReadSingleUserConversationRequestEvent event);

    /**
     * Returns a list of participants related to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public GetConversationParticipantsResponseEvent getParticipants(GetConversationParticipantsRequestEvent event);

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event request event for method
     * @return response event for method
     */
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event);

    /**
     * Reset counter of unread messages (usually displayed in badge) for the particular user and conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public ResetUnreadMessagesCounterResponseEvent resetUnreadMessagesCounter(ResetUnreadMessagesCounterRequestEvent
                                                                                      event);

    /**
     * Adds buddies to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public AddParticipantsResponseEvent addParticipants(AddParticipantsRequestEvent event);

    /**
     * Removes buddy from the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event);

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event);

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event);

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event);

}
