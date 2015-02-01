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


    private static ConversationEventBus conversationEventBus;

    /**
     * Returns ConversationCoreService implementation
     *
     * @return ConversationCoreService
     */
    public static ConversationEventBus getConversationEventBus() {
        return conversationEventBus;
    }

    /**
     * Injects proper ConversationCoreService via Dependency Injection
     *
     * @param conversationEventBus ConversationCoreService
     */
    public void setConversationCoreService(ConversationEventBus conversationEventBus) {
        ConversationEventBusUtil.conversationEventBus = conversationEventBus;
    }

}
