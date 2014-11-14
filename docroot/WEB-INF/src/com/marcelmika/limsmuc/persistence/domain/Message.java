/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.domain;

import com.marcelmika.limsmuc.api.entity.MessageDetails;

import java.util.*;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:59 AM
 */
public class Message {

    private Long messageId;
    private Buddy from;
    private String body;
    private Date createdAt;
    private MessageType messageType;

    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    public static Message fromMessageDetails(MessageDetails details) {
        // Create new message
        Message message = new Message();

        // Map properties
        message.messageId = details.getMessageId();
        message.body = details.getBody();
        message.createdAt = details.getCreatedAt();

        // Relations
        if (details.getFrom() != null) {
            message.from = Buddy.fromBuddyDetails(details.getFrom());
        }

        if (details.getMessageType() != null) {
            message.messageType = MessageType.fromMessageTypeDetails(details.getMessageType());
        }

        return message;
    }

    public static List<Message> fromMessageDetails(List<MessageDetails> details) {
        // Create new message list
        List<Message> messages = new ArrayList<Message>();
        // Map
        for (MessageDetails messageDetails : details) {
            messages.add(Message.fromMessageDetails(messageDetails));
        }
        return messages;
    }

    public static Message fromMessageModel(com.marcelmika.limsmuc.persistence.generated.model.Message messageModel) {
        // Create new message
        Message message = new Message();
        // Properties
        message.messageId = messageModel.getMid();
        message.messageType = MessageType.fromCode(messageModel.getMessageType());
        message.body = messageModel.getBody();
        message.createdAt = messageModel.getCreatedAt();

        Buddy creator = new Buddy();
        creator.setBuddyId(messageModel.getCreatorId());
        message.from = creator;


        return message;
    }

    /**
     * Factory method which creates message from plain java object usually retrieved from database
     *
     * @param object       Object[] array which contains message data
     * @param firstElement determines first element in Object[] where the message serialization should start
     * @return Buddy
     */
    public static Message fromPlainObject(Object[] object, int firstElement) {
        // Create new message
        Message message = new Message();
        // Map data from object
        message.messageId = (Long) object[firstElement++];
        message.messageType = MessageType.fromCode((Integer) object[firstElement++]);

        Buddy creator = new Buddy();
        creator.setBuddyId((Long) object[firstElement++]);
        message.from = creator;

        Calendar createdAt = (Calendar) object[firstElement++];
        message.createdAt = createdAt.getTime();

        message.body = (String) object[firstElement];

        return message;
    }

    public static List<Message> toMessageList(List<Object[]> objects, int firstElement) {
        // Create new list
        List<Message> messages = new LinkedList<Message>();
        // Map
        for (Object[] object : objects) {
            messages.add(Message.fromPlainObject(object, firstElement));
        }

        return messages;
    }

    public static List<MessageDetails> toMessageDetailsList(List<Message> messages) {
        // Create new list
        List<MessageDetails> details = new ArrayList<MessageDetails>();
        // Map
        for (Message message : messages) {
            details.add(message.toMessageDetails());
        }

        return details;
    }

    public MessageDetails toMessageDetails() {
        // Create new message details
        MessageDetails details = new MessageDetails();
        // Properties
        details.setMessageId(messageId);
        details.setBody(body);
        details.setCreatedAt(createdAt);

        if (from != null) {
            details.setFrom(from.toBuddyDetails());
        }

        if (messageType != null) {
            details.setMessageType(messageType.toMessageTypeDetails());
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------


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
        this.body = body;
    }
}
