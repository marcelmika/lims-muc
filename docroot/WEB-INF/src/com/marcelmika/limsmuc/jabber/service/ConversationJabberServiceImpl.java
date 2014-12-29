/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.conversation.*;
import com.marcelmika.limsmuc.core.bus.ConversationEventBus;
import com.marcelmika.limsmuc.jabber.JabberException;
import com.marcelmika.limsmuc.jabber.conversation.manager.ConversationListener;
import com.marcelmika.limsmuc.jabber.conversation.manager.single.SingleUserConversationManager;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.ConversationType;
import com.marcelmika.limsmuc.jabber.domain.Message;
import com.marcelmika.limsmuc.jabber.domain.SingleUserConversation;
import com.marcelmika.limsmuc.jabber.session.UserSession;
import com.marcelmika.limsmuc.jabber.session.store.UserSessionStore;
import com.marcelmika.limsmuc.jabber.session.store.UserSessionStoreListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 8:08 PM
 */
public class ConversationJabberServiceImpl
        implements ConversationJabberService, UserSessionStoreListener, ConversationListener {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ConversationJabberServiceImpl.class);

    // Dependencies
    private UserSessionStore userSessionStore;
    private ConversationEventBus conversationEventBus;

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public ConversationJabberServiceImpl(final UserSessionStore userSessionStore,
                                         final ConversationEventBus conversationEventBus) {
        // Set
        this.userSessionStore = userSessionStore;
        this.conversationEventBus = conversationEventBus;
        // Add listeners
        userSessionStore.addUserSessionStoreListener(this);
    }

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {
        // Check preconditions
        if (event.getConversation() == null) {
            return CreateConversationResponseEvent.failure(
                    CreateConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        // Get buddy from details
        Buddy creator = Buddy.fromBuddyDetails(event.getCreator());

        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(creator.getBuddyId());
        // No session
        if (userSession == null) {
            return CreateConversationResponseEvent.failure(
                    CreateConversationResponseEvent.Status.ERROR_NO_SESSION
            );
        }

        // Decide where to go based on the conversation type
        ConversationType conversationType = ConversationType.fromConversationTypeDetails(
                event.getConversation().getConversationType()
        );

        // Single user conversation
        if (conversationType == ConversationType.SINGLE_USER) {

            // There is no need to create a conversation if we are dealing with the single user chat.
            // Each time the message is set a new chat object is created. Thus we don't need to
            // explicitly trigger any create conversation action. Consequently, return success.
            return CreateConversationResponseEvent.success(event.getConversation());
        }
        // Multi user conversation
        else if (conversationType == ConversationType.MULTI_USER) {
            return CreateConversationResponseEvent.failure(
                    CreateConversationResponseEvent.Status.ERROR_NOT_IMPLEMENTED
            );
        }
        // Unrecognized type
        else {
            return CreateConversationResponseEvent.failure(
                    CreateConversationResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE
            );
        }
    }

    /**
     * Sends message to conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event) {
        // Check preconditions
        if (event.getBuddyDetails() == null ||
                event.getMessageDetails() == null ||
                event.getConversationDetails() == null ||
                event.getConversationDetails().getBuddy() == null) {
            return SendMessageResponseEvent.failure(
                    SendMessageResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        Message message = Message.fromMessageDetails(event.getMessageDetails());

        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return SendMessageResponseEvent.failure(
                    SendMessageResponseEvent.Status.ERROR_NO_SESSION
            );
        }

        // Decide where to go based on the conversation type
        ConversationType conversationType = ConversationType.fromConversationTypeDetails(
                event.getConversationDetails().getConversationType()
        );

        // Single user conversation
        if (conversationType == ConversationType.SINGLE_USER) {
            // Map from conversation details
            SingleUserConversation conversation = SingleUserConversation.fromConversationDetails(
                    event.getConversationDetails()
            );

            // Send
            return sendSingleUserMessage(conversation, message, userSession);
        }
        // Multi user conversation
        else if (conversationType == ConversationType.MULTI_USER) {
            return SendMessageResponseEvent.failure(
                    SendMessageResponseEvent.Status.ERROR_NOT_IMPLEMENTED
            );
        }
        // Unrecognized type
        else {
            return SendMessageResponseEvent.failure(
                    SendMessageResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE
            );
        }
    }


    // -------------------------------------------------------------------------------------------
    // User Session Store Listener
    // -------------------------------------------------------------------------------------------

    @Override
    public void userSessionAdded(UserSession userSession) {
        userSession.getSingleUserConversationManager().addConversationListener(this);
    }

    @Override
    public void userSessionRemoved(Long id) {
        // Do nothing
    }


    // -------------------------------------------------------------------------------------------
    // Conversation Listener
    // -------------------------------------------------------------------------------------------

    /**
     * Called whenever the user receives a message within any
     * of the conversations
     *
     * @param message received message
     */
    @Override
    public void messageReceived(Message message) {
        // Create conversation from message
        SingleUserConversation conversation = SingleUserConversation.fromMessage(message);

        // Publish the event to the conversation bus
        conversationEventBus.publish(
                new MessageReceivedBusEvent(conversation.toConversationDetails(), message.toMessageDetails())
        );
    }

    // -------------------------------------------------------------------------------------------
    // Private Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Sends message to a single user conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     * @param session      UserSession
     * @return SendMessageResponseEvent
     */
    private SendMessageResponseEvent sendSingleUserMessage(SingleUserConversation conversation,
                                                           Message message,
                                                           UserSession session) {
        // Send message via single user conversation manager taken from user session
        SingleUserConversationManager manager = session.getSingleUserConversationManager();

        try {
            // Send message
            manager.sendMessage(conversation, message);
        }
        // Failure
        catch (JabberException exception) {
            return SendMessageResponseEvent.failure(
                    SendMessageResponseEvent.Status.ERROR_JABBER, exception);
        }

        // Success
        return SendMessageResponseEvent.success(message.toMessageDetails());
    }

}
