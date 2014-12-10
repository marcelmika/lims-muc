/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.conversation.manager.single;

import com.marcelmika.limsmuc.jabber.JabberException;
import com.marcelmika.limsmuc.jabber.conversation.manager.ConversationListener;
import com.marcelmika.limsmuc.jabber.domain.Message;
import com.marcelmika.limsmuc.jabber.domain.SingleUserConversation;
import org.jivesoftware.smack.ChatManager;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:40 PM
 */
public interface SingleUserConversationManager {

    /**
     * Register conversation listener
     *
     * @param listener ConversationListener
     */
    public void addConversationListener(ConversationListener listener);

    /**
     * Manage conversations from chat manager
     *
     * @param chatManager ChatManager
     */
    public void setChatManager(ChatManager chatManager);

    /**
     * Sends message to conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     */
    public void sendMessage(SingleUserConversation conversation,
                            Message message) throws JabberException;

}
