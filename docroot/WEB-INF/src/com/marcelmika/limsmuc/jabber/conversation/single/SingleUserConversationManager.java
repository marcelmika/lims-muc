/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.conversation.single;

import com.marcelmika.limsmuc.jabber.conversation.ConversationListener;
import com.marcelmika.limsmuc.jabber.domain.Message;
import com.marcelmika.limsmuc.jabber.domain.SingleUserConversation;
import com.marcelmika.limsmuc.jabber.exception.JabberException;
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
    void addConversationListener(ConversationListener listener);

    /**
     * Manage conversations from chat manager
     *
     * @param chatManager ChatManager
     */
    void setChatManager(ChatManager chatManager);

    /**
     * Sets company id
     *
     * @param companyId Long
     */
    void setCompanyId(Long companyId);

    /**
     * Sends message to conversation
     *
     * @param conversation SingleUserConversation
     * @param message      Message
     */
    void sendMessage(SingleUserConversation conversation, Message message) throws JabberException;

    /**
     * Destroys manager
     */
    void destroy();

}
