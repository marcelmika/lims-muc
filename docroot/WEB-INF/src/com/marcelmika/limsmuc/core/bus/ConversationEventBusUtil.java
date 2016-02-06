/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.bus;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 25/12/14
 * Time: 13:10
 */
public class ConversationEventBusUtil {

    // Injected event bus
    private static ConversationEventBus conversationEventBus;

    /**
     * Returns ConversationEventBus implementation
     *
     * @return ConversationEventBus
     */
    public static ConversationEventBus getConversationEventBus() {
        return conversationEventBus;
    }

    /**
     * Injects proper ConversationEventBus via Dependency Injection
     *
     * @param conversationEventBus ConversationEventBus
     */
    public void setConversationCoreService(ConversationEventBus conversationEventBus) {
        ConversationEventBusUtil.conversationEventBus = conversationEventBus;
    }

}
