/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.conversation.manager;

import com.marcelmika.limsmuc.jabber.domain.Message;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 07/09/14
 * Time: 21:01
 */
public interface ConversationListener {

    /**
     * Called whenever the user receives a message within any
     * of the conversations
     *
     * @param message received message
     */
    public void messageReceived(Message message);
}
