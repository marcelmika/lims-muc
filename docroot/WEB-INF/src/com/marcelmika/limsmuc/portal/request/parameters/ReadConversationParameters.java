/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.request.parameters;

import com.marcelmika.limsmuc.portal.domain.MessagePagination;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 7/13/14
 * Time: 7:25 PM
 */
public class ReadConversationParameters {

    private String conversationId;
    private MessagePagination pagination;
    private Integer etag;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public MessagePagination getPagination() {
        return pagination;
    }

    public void setPagination(MessagePagination pagination) {
        this.pagination = pagination;
    }

    public Integer getEtag() {
        return etag;
    }

    public void setEtag(Integer etag) {
        this.etag = etag;
    }

    @Override
    public String toString() {
        return "ReadConversationParameters{" +
                "conversationId='" + conversationId + '\'' +
                ", pagination=" + pagination +
                ", etag=" + etag +
                '}';
    }
}
