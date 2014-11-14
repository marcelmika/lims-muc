/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.domain;

import com.marcelmika.limsmuc.api.entity.MessageDetails;
import com.marcelmika.limsmuc.portal.properties.InputLimits;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Message {

    // Properties
    private Long messageId;
    private Buddy from;
    private String conversationId;
    private String body;
    private Date createdAt;
    private MessageType messageType;

    /**
     * Factory method which creates a list of Messages from a list of MessageDetails
     *
     * @param detailsList list of MessageDetails
     * @return Message
     */
    public static List<Message> fromMessageDetailsList(List<MessageDetails> detailsList) {
        // Create new list of messages
        List<Message> messages = new ArrayList<Message>();
        // Iterate through the list and add messages
        for (MessageDetails details : detailsList) {
            messages.add(Message.fromMessageDetails(details));
        }

        return messages;
    }

    /**
     * Factory method which creates new Message from the MessageDetails
     *
     * @param details message details
     * @return Message
     */
    public static Message fromMessageDetails(MessageDetails details) {
        // Crate new message
        Message message = new Message();
        // Map values
        message.messageId = details.getMessageId();
        message.createdAt = details.getCreatedAt();
        message.body = details.getBody();

        // Relations
        if (details.getFrom() != null) {
            message.from = Buddy.fromBuddyDetails(details.getFrom());
        }

        if (details.getMessageType() != null) {
            message.messageType = MessageType.fromMessageTypeDetails(details.getMessageType());
        }

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
        details.setCreatedAt(createdAt);
        details.setBody(body);

        // Relations
        if (from != null) {
            details.setFrom(from.toBuddyDetails());
        }

        if (messageType != null) {
            details.setMessageType(messageType.toMessageTypeDetails());
        }

        return details;
    }

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
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
        // Limit the body maximal size
        if (body.length() > InputLimits.MESSAGE_MAX_SIZE) {
            body = body.substring(0, InputLimits.MESSAGE_MAX_SIZE);
        }

        this.body = body;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", from=" + from +
                ", conversationId='" + conversationId + '\'' +
                ", body='" + body + '\'' +
                ", createdAt=" + createdAt +
                ", messageType=" + messageType.getDescription() +
                '}';
    }
}




