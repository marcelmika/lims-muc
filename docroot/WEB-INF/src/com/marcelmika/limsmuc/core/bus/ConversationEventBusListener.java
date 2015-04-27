/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.bus;

import com.marcelmika.limsmuc.api.events.conversation.MessageReceivedBusEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/12/14
 * Time: 13:11
 */
public interface ConversationEventBusListener {

    /**
     * Called when the message is received
     *
     * @param event MessageReceivedBusEvent
     */
    void messageReceived(MessageReceivedBusEvent event);

}
