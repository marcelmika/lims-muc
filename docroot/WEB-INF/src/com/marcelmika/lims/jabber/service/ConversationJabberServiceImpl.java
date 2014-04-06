package com.marcelmika.lims.jabber.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.events.conversation.GetConversationsRequestEvent;
import com.marcelmika.lims.api.events.conversation.GetConversationsResponseEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.lims.api.events.conversation.SendMessageResponseEvent;
import com.marcelmika.lims.jabber.JabberException;
import com.marcelmika.lims.jabber.conversation.manager.single.SingleUserConversationManager;
import com.marcelmika.lims.jabber.domain.Buddy;
import com.marcelmika.lims.jabber.domain.ConversationType;
import com.marcelmika.lims.jabber.domain.Message;
import com.marcelmika.lims.jabber.domain.SingleUserConversation;
import com.marcelmika.lims.jabber.session.UserSession;
import com.marcelmika.lims.jabber.session.store.UserSessionStore;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 8:08 PM
 */
public class ConversationJabberServiceImpl implements ConversationJabberService {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConversationJabberServiceImpl.class);

    // Dependencies
    private UserSessionStore userSessionStore;

    /**
     * Constructor
     *
     * @param userSessionStore UserSessionStore
     */
    public ConversationJabberServiceImpl(UserSessionStore userSessionStore) {
        this.userSessionStore = userSessionStore;
    }


    // -------------------------------------------------------------------------------------------
    // Conversation Jabber Service
    // -------------------------------------------------------------------------------------------

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event) {
        // Check preconditions
        if (event.getBuddyDetails() == null) {
            return GetConversationsResponseEvent.getConversationsFailure(
                    new JabberException("Some of required params is missing")
            );
        }

        // Get buddy form details
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        // We use buddy ID as an identification
        Long buddyId = buddy.getBuddyId();
        // Get the session from store
        UserSession userSession = userSessionStore.getUserSession(buddyId);
        // No session
        if (userSession == null) {
            return GetConversationsResponseEvent.getConversationsFailure(
                    new JabberException("There is no user session. Message cannot be sent.")
            );
        }

        // Create new list
        List<ConversationDetails> conversations = new ArrayList<ConversationDetails>();

        // Add single user conversations
        SingleUserConversationManager singleManager = userSession.getSingleUserConversationManager();
        List<SingleUserConversation> singleConversations = singleManager.getConversations();
        conversations.addAll(SingleUserConversation.toConversationDetailsList(singleConversations));

        // todo: Add multi user conversations

        // Return list
        return GetConversationsResponseEvent.getConversationsSuccess(conversations);
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
                event.getConversationDetails() == null) {
            return SendMessageResponseEvent.sendMessageFailure(
                    new JabberException("Some of required params is missing")
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
            return SendMessageResponseEvent.sendMessageFailure(
                    new JabberException("There is no user session. Message cannot be sent.")
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
            return SendMessageResponseEvent.sendMessageFailure(
                    new RuntimeException("Multi user not implemented yet")
            );
        }
        // Unrecognized type
        else {
            return SendMessageResponseEvent.sendMessageFailure(
                    new JabberException("Unrecognized type of conversation: " + conversationType)
            );
        }
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
            conversation = manager.sendMessage(conversation, message);
        } catch (JabberException e) {
            return SendMessageResponseEvent.sendMessageFailure(e);
        }

        return SendMessageResponseEvent.sendMessageSuccess(
                "Message successfully sent", conversation.toConversationDetails()
        );
    }
}
