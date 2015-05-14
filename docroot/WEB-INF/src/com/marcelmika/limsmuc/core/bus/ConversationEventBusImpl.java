/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.bus;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.conversation.MessageReceivedBusEvent;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/12/14
 * Time: 13:10
 */
public class ConversationEventBusImpl implements ConversationEventBus {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ConversationEventBusImpl.class);


    // Subscribers
    private List<ConversationEventBusListener> subscribers = Collections.synchronizedList(
            new LinkedList<ConversationEventBusListener>()
    );

    /**
     * Register message bus listener
     *
     * @param listener MessageEventBusListener
     */
    @Override
    public void register(ConversationEventBusListener listener) {
        subscribers.add(listener);
    }

    /**
     * Publish message received bus event
     *
     * @param event MessageReceivedBusEvent
     */
    @Override
    public void publish(MessageReceivedBusEvent event) {
        // Publish to all subscribers
        for (ConversationEventBusListener subscriber : subscribers) {
            subscriber.messageReceived(event);
        }
    }
}
