/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.lims.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageResponseEvent;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.conversation.manager.ConversationListener;
import com.marcelmika.lims.jabber.conversation.manager.single.SingleUserConversationManager;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.ConversationType;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import com.marcelmika.lims.jabber.session.UserSession;
import com.marcelmika.lims.jabber.session.store.UserSessionStore;
import com.marcelmika.lims.jabber.session.store.UserSessionStoreListener;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 8:08 PM
 */
public class ConversationJabberServiceImpl
        implements ConversationJabberService, UserSessionStoreListener, ConversationListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConversationJabberServiceImpl.class);

    // Dependencies
    private UserSessionStore userSessionStore;

    // Listener
    private List<ConversationJabberServiceListener> listeners = Collections.synchronizedList(
            new LinkedList<ConversationJabberServiceListener>()
    );

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public ConversationJabberServiceImpl(final UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
        // Add listeners
        userSessionStore.addUserSessionStoreListener(this);
    }


    // -------------------------------------------------------------------------------------------
    // Conversation Jabber Service
    // -------------------------------------------------------------------------------------------

    @Override
    public void addConversationJabberServiceListener(ConversationJabberServiceListener listener) {
        listeners.add(listener);
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


    // -------------------------------------------------------------------------------------------
    // Conversation Listener
    // -------------------------------------------------------------------------------------------

    @Override
    public void messageReceived(Message message) {

        // Create conversation from message
        SingleUserConversation conversation = SingleUserConversation.fromMessage(message);

        // Notify listeners
        for (ConversationJabberServiceListener listener : listeners) {
            listener.messageReceived(conversation.toConversationDetails(), message.toMessageDetails());
        }
    }
}
