/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.conversation.AddParticipantsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.AddParticipantsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.CloseConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.CloseConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.ExistsConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.ExistsConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationParticipantsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationParticipantsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.LeaveConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.LeaveConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.MessageReceivedBusEvent;
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
import com.marcelmika.limsmuc.core.bus.ConversationEventBus;
import com.marcelmika.limsmuc.core.bus.ConversationEventBusListener;
import com.marcelmika.limsmuc.core.domain.Buddy;
import com.marcelmika.limsmuc.core.domain.Conversation;
import com.marcelmika.limsmuc.core.domain.Message;
import com.marcelmika.limsmuc.jabber.service.ConversationJabberService;
import com.marcelmika.limsmuc.persistence.service.ConversationPersistenceService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Implementation of ConversationCoreService
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/19/14
 * Time: 11:03 PM
 */
public class ConversationCoreServiceImpl implements ConversationCoreService, ConversationEventBusListener {

    // Dependencies
    ConversationJabberService conversationJabberService;
    ConversationPersistenceService conversationPersistenceService;
    ConversationEventBus conversationEventBus;

    // Log
    private static Log log = LogFactoryUtil.getLog(ConversationCoreServiceImpl.class);

    /**
     * Constructor
     *
     * @param conversationJabberService jabber service
     */
    public ConversationCoreServiceImpl(final ConversationJabberService conversationJabberService,
                                       final ConversationPersistenceService conversationPersistenceService,
                                       final ConversationEventBus conversationEventBus) {
        this.conversationJabberService = conversationJabberService;
        this.conversationPersistenceService = conversationPersistenceService;
        this.conversationEventBus = conversationEventBus;

        // Listeners
        conversationEventBus.register(this);
    }

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event) {
        // Read from persistence
        return conversationPersistenceService.getConversations(event);
    }

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event) {
        // Read from persistence
        return conversationPersistenceService.getOpenedConversations(event);
    }

    /**
     * Creates new conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {

        // Create conversation locally
        CreateConversationResponseEvent persistenceResponseEvent = conversationPersistenceService.createConversation(
                event
        );

        // Check for error
        if (!persistenceResponseEvent.isSuccess()) {
            return persistenceResponseEvent;
        }

        // If enabled create in jabber too
        if (Environment.isJabberEnabled()) {
            conversationJabberService.createConversation(event);
        }

        return persistenceResponseEvent;
    }

    /**
     * Reads messages from conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ReadSingleUserConversationResponseEvent readConversation(ReadSingleUserConversationRequestEvent event) {
        // Read from persistence
        return conversationPersistenceService.readConversation(event);
    }

    /**
     * Opens existing conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public OpenConversationResponseEvent openConversation(OpenConversationRequestEvent event) {
        throw new NotImplementedException();
    }

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event) {
        // Save to persistence
        return conversationPersistenceService.closeConversation(event);
    }

    /**
     * Reset counter of unread messages (usually displayed in badge) for the particular user and conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ResetUnreadMessagesCounterResponseEvent resetUnreadMessagesCounter(ResetUnreadMessagesCounterRequestEvent event) {
        // Save to persistence
        return conversationPersistenceService.resetUnreadMessagesCounter(event);
    }

    /**
     * Removes buddy from the conversation
     *
     * @param event Request event for login method
     * @return Response event for login method
     */
    @Override
    public LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event) {
        // Save to persistence
        return conversationPersistenceService.leaveConversation(event);
    }

    /**
     * Switch conversations positions
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SwitchConversationsResponseEvent switchConversations(SwitchConversationsRequestEvent event) {
        // Send to persistence
        return conversationPersistenceService.switchConversations(event);
    }

    /**
     * Adds buddies to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public AddParticipantsResponseEvent addParticipants(AddParticipantsRequestEvent event) {
        // Save to persistence
        return conversationPersistenceService.addParticipants(event);
    }

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event) {

        // Add participants to the conversation. We are not receiving a list of participants from
        // the request thus we need to retrieve a list from persistence first
        GetConversationParticipantsResponseEvent participantListEvent = conversationPersistenceService.getParticipants(
                new GetConversationParticipantsRequestEvent(event.getConversationDetails())
        );
        // Failure
        if (!participantListEvent.isSuccess()) {
            return SendMessageResponseEvent.failure(
                    SendMessageResponseEvent.Status.ERROR_PERSISTENCE, participantListEvent.getException()
            );
        }

        // Send message to Jabber if enabled
        if (Environment.isJabberEnabled()) {

            // Send message via jabber service
            SendMessageResponseEvent jabberResponseEvent = conversationJabberService.sendMessage(
                    new SendMessageRequestEvent(
                            event.getBuddyDetails(),
                            participantListEvent.getConversation(),
                            event.getMessageDetails())
            );

            // Failure
            if (!jabberResponseEvent.isSuccess()) {
                // TODO: Remove after MUC in jabber is implemented
                if (jabberResponseEvent.getStatus() != SendMessageResponseEvent.Status.ERROR_NOT_IMPLEMENTED) {
                    return jabberResponseEvent;
                }
            }
        }

        // Send message locally
        SendMessageResponseEvent persistenceResponseEvent = conversationPersistenceService.sendMessage(
                new SendMessageRequestEvent(
                        event.getBuddyDetails(),
                        participantListEvent.getConversation(),
                        event.getMessageDetails())
        );

        // Failure
        if (!persistenceResponseEvent.isSuccess()) {
            return persistenceResponseEvent;
        }

        // Return persistence event
        return SendMessageResponseEvent.success(
                persistenceResponseEvent.getMessage()
        );
    }


    // -------------------------------------------------------------------------------------------
    // Conversation Bus
    // -------------------------------------------------------------------------------------------

    @Override
    public void messageReceived(MessageReceivedBusEvent event) {

        // Log
        if (log.isDebugEnabled()) {
            log.debug("Message received" + event);
        }

        // Map from api objects
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());
        Message message = Message.fromMessageDetails(event.getMessage());

        // Check if the conversation exists
        ExistsConversationResponseEvent existsConversationRequest = conversationPersistenceService.existsConversation(
                new ExistsConversationRequestEvent(conversation.getConversationId())
        );

        // Failure
        if (!existsConversationRequest.isSuccess()) {
            // Log error
            if (log.isDebugEnabled()) {
                log.debug(existsConversationRequest.getException());
            }
        }

        // Conversation holds the creator
        Buddy creator = conversation.getBuddy();

        // If no such conversation exists create a new one
        if (!existsConversationRequest.isExists()) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug("Creating new conversation" + conversation);
            }

            // Create new conversation
            CreateConversationResponseEvent responseEvent = conversationPersistenceService.createConversation(
                    new CreateConversationRequestEvent(
                            creator.toBuddyDetails(), conversation.toConversationDetails(), message.toMessageDetails()
                    ));

            // Failure
            if (!responseEvent.isSuccess()) {
                // Log error
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getStatus());
                    log.debug(responseEvent.getException());
                }
                // End here
                return;
            }
        }

        // Send a message
        SendMessageResponseEvent sendMessageResponse = conversationPersistenceService.sendMessage(
                new SendMessageRequestEvent(
                        creator.toBuddyDetails(), conversation.toConversationDetails(), message.toMessageDetails()
                ));

        // Send message
        if (!sendMessageResponse.isSuccess()) {
            // Log error
            if (log.isDebugEnabled()) {
                log.debug(sendMessageResponse.getStatus());
                log.debug(sendMessageResponse.getException());
            }
        }
    }
}
