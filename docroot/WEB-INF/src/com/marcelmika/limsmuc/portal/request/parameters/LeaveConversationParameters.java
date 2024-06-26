/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.request.parameters;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 31/10/14
 * Time: 09:51
 */
public class LeaveConversationParameters {

    private String conversationId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "LeaveConversationParameters{" +
                "conversationId='" + conversationId + '\'' +
                '}';
    }
}
