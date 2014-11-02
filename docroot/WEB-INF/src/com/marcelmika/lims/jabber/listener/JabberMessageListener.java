
/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.jabber.listener;

import com.marcelmika.lims.jabber.domain.Conversation;
import com.marcelmika.lims.jabber.domain.MessageDeprecated;
import com.marcelmika.lims.jabber.JabberUtil;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 * @deprecated
 */
public class JabberMessageListener implements MessageListener {

    private Conversation conversation;

    public JabberMessageListener() {

    }

    public JabberMessageListener(Conversation conversation) {
        this.conversation = conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }

    public void processMessage(Chat chat, org.jivesoftware.smack.packet.Message message) {
        // Message adapter
        MessageDeprecated msg = new MessageDeprecated(message);
        // Extract  jabber name
        msg.setFrom(JabberUtil.getScreenName(msg.getFrom()));
//        msg.setCompanyId(conversation.getOwner().getCompanyId());

        // Add only messages with sender
        if (msg.getFrom() != null) {
            conversation.getMessages().add(msg);
//            System.out.println("[CONVERSATION][" + conversation.getOwner().getScreenName() + "] " + msg.getFrom() + ": " + msg.getBody());

//            System.out.println("[Current Messages stack:] " + conversation.getMessages());
        } else {
//            System.out.println("[CONVERSATION][" + conversation.getOwner().getScreenName() + "] Message with no sender: " + msg.getBody());
        }
    }

}