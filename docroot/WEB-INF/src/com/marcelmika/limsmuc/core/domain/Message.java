/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.domain;

import com.marcelmika.limsmuc.api.entity.MessageDetails;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private MessageType messageType;

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
        message.body = details.getBody();

        // Relations
        if (details.getMessageType() != null) {
            message.messageType = MessageType.fromMessageTypeDetails(details.getMessageType());
        }

        return message;
    }

    /**
     * Creates a list of messages from the list of message details
     *
     * @param details list of message details
     * @return list of messages
     */
    public static List<Message> fromMessageDetails(List<MessageDetails> details) {
        // Create new message list
        List<Message> messages = new ArrayList<Message>();
        // Map
        for (MessageDetails messageDetails : details) {
            messages.add(Message.fromMessageDetails(messageDetails));
        }
        return messages;
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

        // Relations
        if (messageType != null) {
            details.setMessageType(messageType.toMessageTypeDetails());
        }

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
