/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.request.parameters;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 07/11/14
 * Time: 13:07
 */
public class SwitchConversationsParameters {

    public String firstConversationId;
    public String secondConversationId;

    public String getFirstConversationId() {
        return firstConversationId;
    }

    public void setFirstConversationId(String firstConversationId) {
        this.firstConversationId = firstConversationId;
    }

    public String getSecondConversationId() {
        return secondConversationId;
    }

    public void setSecondConversationId(String secondConversationId) {
        this.secondConversationId = secondConversationId;
    }

    @Override
    public String toString() {
        return "SwitchConversationsParameters{" +
                "firstConversationId='" + firstConversationId + '\'' +
                ", secondConversationId='" + secondConversationId + '\'' +
                '}';
    }
}
