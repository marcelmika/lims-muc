/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.core.domain;

import com.marcelmika.lims.api.entity.MessageDetails;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/7/14
 * Time: 11:26 PM
 */
public class Message {

    private Long messageId;
    private Buddy from;
    private Date createdAt;
    private String body;

    /**
     * Creates new Message and maps data from Message details
     *
     * @param details MessageDetails
     * @return Message
     */
    public static Message fromMessageDetails(MessageDetails details) {
        // Create new Message
        Message message = new Message();
        // Map data to message details
        message.messageId = details.getMessageId();
        message.from = Buddy.fromBuddyDetails(details.getFrom());
        message.createdAt = details.getCreatedAt();
        message.body = message.getBody();

        return message;
    }

    /**
     * Maps message to message details
     *
     * @return MessageDetails
     */
    public MessageDetails toMessageDetails() {
        // Create message details
        MessageDetails details = new MessageDetails();
        // Map data from message
        details.setMessageId(messageId);
        details.setFrom(from.toBuddyDetails());
        details.setCreatedAt(createdAt);
        details.setBody(body);

        return details;
    }

    public Buddy getFrom() {
        return from;
    }

    public void setFrom(Buddy from) {
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
}
