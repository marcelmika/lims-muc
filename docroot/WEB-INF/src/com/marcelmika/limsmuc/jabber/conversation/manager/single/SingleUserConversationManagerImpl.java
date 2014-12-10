/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.conversation.manager.single;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.jabber.JabberException;
import com.marcelmika.limsmuc.jabber.conversation.manager.ConversationListener;
import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.Message;
import com.marcelmika.limsmuc.jabber.domain.SingleUserConversation;
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
            Chat chat = chatManager.createChat(receiverJid, null);
            // Send message via new conversation
            chat.sendMessage(message.getBody());
        }
        // Failure
        catch (Exception exception) {
            throw new JabberException(exception);
        }
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

        // Parse message
        Message message = Message.fromSmackMessage(smackMessage);

        // Don't no pass a message that is empty
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
