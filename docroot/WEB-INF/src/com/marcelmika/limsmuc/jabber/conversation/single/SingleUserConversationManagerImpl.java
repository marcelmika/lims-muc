/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.conversation.single;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.jabber.conversation.ConversationListener;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.Message;
import com.marcelmika.limsmuc.jabber.domain.SingleUserConversation;
import com.marcelmika.limsmuc.jabber.exception.JabberException;
import com.marcelmika.limsmuc.jabber.utils.Jid;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:20 PM
 */
public class SingleUserConversationManagerImpl
        implements SingleUserConversationManager, ChatManagerListener, MessageListener {

    // Log
    private static Log log = LogFactoryUtil.getLog(SingleUserConversationManagerImpl.class);

    // Smack Chat Manager
    private ChatManager chatManager;
    // We need the company id otherwise we cannot find users in liferay
    private Long companyId;

    // Conversation Listeners
    private List<ConversationListener> conversationListeners = Collections.synchronizedList(
            new ArrayList<ConversationListener>()
    );

    /**
     * Register conversation listener
     *
     * @param listener ConversationListener
     */
    @Override
    public void addConversationListener(ConversationListener listener) {
        conversationListeners.add(listener);
    }

    // -------------------------------------------------------------------------------------------
    // Single User Conversation Manager
    // -------------------------------------------------------------------------------------------

    /**
     * Manage conversations from chat manager
     *
     * @param chatManager ChatManager
     */
    @Override
    public void setChatManager(ChatManager chatManager) {
        this.chatManager = chatManager;

        // Add listener
        this.chatManager.addChatListener(this);
    }

    /**
     * Sets company id to the group manager
     *
     * @param companyId Long
     */
    @Override
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * Sends message to conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     */
    @Override
    public void sendMessage(SingleUserConversation conversation,
                            Message message) throws JabberException {

        // Log
        if (log.isDebugEnabled()) {
            log.debug(String.format("Conversation %s, to: %s, body %s",
                            conversation.getConversationId(), conversation.getParticipant(), message.getBody())
            );
        }

        // Send a message to the conversation which was already in the system
        try {
            // Receiver
            Buddy receiver = conversation.getParticipant();
            // Receiver's Jid
            String receiverJid = Jid.getJid(receiver.getScreenName());
            // Create a new chat
            Chat chat = chatManager.createChat(receiverJid, this);
            // Send message via new conversation
            chat.sendMessage(message.getBody());
        }
        // Failure
        catch (Exception exception) {
            throw new JabberException(exception);
        }
    }

    /**
     * Destroys manager
     */
    @Override
    public void destroy() {
        chatManager.removeChatListener(this);
    }


    // -------------------------------------------------------------------------------------------
    // Chat Manager Listener
    // -------------------------------------------------------------------------------------------

    /**
     * Called when the chat was created
     *
     * @param chat           the chat that was created
     * @param createdLocally true if the chat was created by the local user
     */
    @Override
    public void chatCreated(Chat chat, boolean createdLocally) {
        // Only if the remote user created the conversation
        if (!createdLocally) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug(String.format("Chat created remotely with participant: %s", chat.getParticipant()));
            }
            // Add listener
            chat.addMessageListener(this);
        }
        // Local
        else {
            // We don't need to care about it here because the chat was already created
            if (log.isDebugEnabled()) {
                log.debug(String.format("Chat created locally with participant: %s", chat.getParticipant()));
            }
        }
    }


    // -------------------------------------------------------------------------------------------
    // Message Listener
    // -------------------------------------------------------------------------------------------

    /**
     * This is called whenever we receive a message
     *
     * @param chat         Chat
     * @param smackMessage Message
     */
    @Override
    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message smackMessage) {

        // Don't notify about the message if the user sent message from LIMS to avoid
        // unnecessary duplicates
        String resource = Jid.getResource(smackMessage.getFrom());

        if (Environment.getJabberResource().toLowerCase().equals(resource)) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug("Received message from the same resource: " + resource + ". Skipping...");
            }
            // End here
            return;
        }

        // Parse message
        Message message = Message.fromSmackMessage(smackMessage);

        // Important: This is a leaking of the business logic. However there is no better place where
        // such call can be added. Thus we simply ignore this fact.
        try {
            User fromUser = UserLocalServiceUtil.getUserByScreenName(companyId, message.getFrom().getScreenName());
            User toUser = UserLocalServiceUtil.getUserByScreenName(companyId, message.getTo().getScreenName());
            // Map the user
            message.setFrom(Buddy.fromPortalUser(fromUser));
            message.setTo(Buddy.fromPortalUser(toUser));

        } catch (Exception e) {
            // No such user was found, thus simply ignore this fact and don't add it to the list
            if (log.isDebugEnabled()) {
                log.debug(e);
            }
            return;
        }

        // Don't pass a message that is empty
        if (message.getBody() != null) {
            // Notify about incoming message
            notifyListeners(message);

            // Log
            if (log.isDebugEnabled()) {
                log.debug(String.format("Participant: %s, Message received: %s", chat.getParticipant(), message));
            }
        }
    }


    /**
     * Notifies listeners about incoming message
     *
     * @param message Message
     */
    private void notifyListeners(Message message) {
        // Notify all
        for (ConversationListener listener : conversationListeners) {
            listener.messageReceived(message);
        }
    }
}
