/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.entity;

import java.util.Date;

/**
 * Enum for conversation message type
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 31/10/14
 * Time: 22:30
 */
public class MessageDetails {

    private Long messageId;
    private BuddyDetails to;
    private BuddyDetails from;
    private String body;
    private Date createdAt;
    private MessageTypeDetails messageType;

    public MessageTypeDetails getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageTypeDetails messageType) {
        this.messageType = messageType;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public BuddyDetails getTo() {
        return to;
    }

    public void setTo(BuddyDetails to) {
        this.to = to;
    }

    public BuddyDetails getFrom() {
        return from;
    }

    public void setFrom(BuddyDetails from) {
        this.from = from;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "MessageDetails{" +
                "messageId=" + messageId +
                ", to=" + to +
                ", from=" + from +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", messageType=" + messageType +
                '}';
    }
}
