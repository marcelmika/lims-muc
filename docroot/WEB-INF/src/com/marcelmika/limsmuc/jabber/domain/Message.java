/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.domain;

import com.marcelmika.limsmuc.api.entity.MessageDetails;
import org.jivesoftware.smackx.delay.packet.DelayInformation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 1:16 AM
 */
public class Message {

    private Long messageId;
    private Buddy to;
    private Buddy from;
    private Date createdAt;
    private String body;
    private MessageType messageType;

    /**
     * Factory method creates message form Smack message
     *
     * @param smackMessage Message
     * @return Message
     */
    public static Message fromSmackMessage(org.jivesoftware.smack.packet.Message smackMessage) {
        // Create new message
        Message message = new Message();
        // Map properties
        message.body = smackMessage.getBody();
        message.messageType = MessageType.REGULAR;
        message.createdAt = getMessageTimestamp(smackMessage);
        // Map relations
        message.from = Buddy.fromSmackUser(smackMessage.getFrom());
        message.to = Buddy.fromSmackUser(smackMessage.getTo());

        return message;
    }

    /**
     * Factory method creates message from message details
     *
     * @param details MessageDetails
     * @return Message
     */
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

    /**
     * Maps a list of message details to a list of messages
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
     * Maps a list of messages to a list of message details
     *
     * @param messages list of messages
     * @return list fo message details
     */
    public static List<MessageDetails> toMessageDetailsList(List<Message> messages) {
        // Create new list
        List<MessageDetails> details = new ArrayList<MessageDetails>();
        // Map
        for (Message message : messages) {
            details.add(message.toMessageDetails());
        }

        return details;
    }

    /**
     * Creates message details from message
     *
     * @return MessageDetails
     */
    public MessageDetails toMessageDetails() {
        // Create new message details
        MessageDetails details = new MessageDetails();
        // Properties
        details.setMessageId(messageId);
        details.setBody(body);
        details.setCreatedAt(createdAt);

        // Relations
        if (from != null) {
            details.setFrom(from.toBuddyDetails());
        }

        if (to != null) {
            details.setTo(to.toBuddyDetails());
        }

        if (messageType != null) {
            details.setMessageType(messageType.toMessageTypeDetails());
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Private Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Method which calculates smack message timestamp.
     * Message creation date can be retrieved from the offline messages only.
     *
     * @param message from smack
     * @return Date
     */
    private static Date getMessageTimestamp(org.jivesoftware.smack.packet.Message message) {

        try {
            // Get the timestamp from message extension
            DelayInformation information = message.getExtension("x", "jabber:x:delay");
            // Return offline message timestamp
            if (information != null) {
                return information.getStamp();
            }

            // Message is not offline -> return current timestamp
            Calendar calendar = Calendar.getInstance();

            return calendar.getTime();

        } catch (Exception e) {
            // Extension isn't provided so return empty date
            return new Date(0);
        }
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------


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

    public Buddy getTo() {
        return to;
    }

    public void setTo(Buddy to) {
        this.to = to;
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

    public MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "messageId=" + messageId +
                ", to=" + to +
                ", from=" + from +
                ", createdAt=" + createdAt +
                ", body='" + body + '\'' +
                ", messageType=" + messageType +
                '}';
    }
}
