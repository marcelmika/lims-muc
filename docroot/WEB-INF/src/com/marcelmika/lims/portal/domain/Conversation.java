/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.domain;

import com.liferay.portal.kernel.json.JSON;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.ConversationDetails;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 6:39 AM
 */
public class Conversation {

    // Properties
    private String conversationId;
    private ConversationType conversationType;
    private String title;
    private Integer unreadMessagesCount;
    private Buddy buddy;
    private List<Buddy> participants;
    private List<Message> messages;
    private Date updatedAt;
    private Date openedAt;
    private Message firstMessage;
    private Message lastMessage;


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method which creates new Conversation from the ConversationDetails
     *
     * @param details ConversationDetails
     * @return Conversation
     */
    public static Conversation fromConversationDetails(ConversationDetails details) {
        // Create new conversation
        Conversation conversation = new Conversation();

        // Parameters
        conversation.conversationId = details.getConversationId();
        conversation.unreadMessagesCount = details.getUnreadMessagesCount();
        conversation.updatedAt = details.getUpdatedAt();
        conversation.openedAt = details.getOpenedAt();

        // Relations
        if (details.getParticipants() != null) {
            conversation.participants = Buddy.fromBuddyDetailsList(details.getParticipants());
        }

        if (details.getMessages() != null) {
            conversation.messages = Message.fromMessageDetailsList(details.getMessages());
        }

        if (details.getConversationType() != null) {
            conversation.conversationType = ConversationType.fromConversationTypeDetails(details.getConversationType());
        }

        if (details.getBuddy() != null) {
            conversation.buddy = Buddy.fromBuddyDetails(details.getBuddy());
        }

        if (details.getFirstMessage() != null) {
            conversation.firstMessage = Message.fromMessageDetails(details.getFirstMessage());
        }

        if (details.getLastMessage() != null) {
            conversation.lastMessage = Message.fromMessageDetails(details.getLastMessage());
        }

        return conversation;
    }

    /**
     * Factory method which creates new list of Conversations from the list of ConversationDetails
     *
     * @param details list of conversation details
     * @return List<Conversation> of conversations
     */
    public static List<Conversation> fromConversationDetailsList(List<ConversationDetails> details) {
        // Create new list of conversations
        List<Conversation> conversations = new LinkedList<Conversation>();

        // Iterate through details and create conversation based on that
        for (ConversationDetails conversationDetails : details) {
            conversations.add(fromConversationDetails(conversationDetails));
        }

        return conversations;
    }

    /**
     * Maps conversation to conversation details
     *
     * @return ConversationDetails
     */
    public ConversationDetails toConversationDetails() {
        // Create conversation details
        ConversationDetails details = new ConversationDetails();
        // Map data from conversation
        details.setConversationId(conversationId);
        details.setUpdatedAt(updatedAt);
        details.setOpenedAt(openedAt);

        // Relations
        if (conversationType != null) {
            details.setConversationType(conversationType.toConversationTypeDetails());
        }

        if (participants != null) {
            List<BuddyDetails> participantDetails = new LinkedList<BuddyDetails>();
            for (Buddy participant : participants) {
                participantDetails.add(participant.toBuddyDetails());
            }
            details.setParticipants(participantDetails);
        }

        if (buddy != null) {
            details.setBuddy(buddy.toBuddyDetails());
        }

        if (firstMessage != null) {
            details.setFirstMessage(firstMessage.toMessageDetails());
        }

        if (lastMessage != null) {
            details.setLastMessage(lastMessage.toMessageDetails());
        }

        return details;
    }


    // -------------------------------------------------------------------------------------------
    // Computed values
    // -------------------------------------------------------------------------------------------

    @JSON
    public Integer getEtag() {
        if (updatedAt != null) {
            return updatedAt.hashCode();
        }
        return 0;
    }


    // -------------------------------------------------------------------------------------------
    // Getters/Setters
    // -------------------------------------------------------------------------------------------

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public ConversationType getConversationType() {
        return conversationType;
    }

    public void setConversationType(ConversationType conversationType) {
        this.conversationType = conversationType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Buddy> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Buddy> participants) {
        this.participants = participants;
    }

    public Buddy getBuddy() {
        return buddy;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public Integer getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Integer unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(Date openedAt) {
        this.openedAt = openedAt;
    }

    public Message getFirstMessage() {
        return firstMessage;
    }

    public void setFirstMessage(Message firstMessage) {
        this.firstMessage = firstMessage;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationId='" + conversationId + '\'' +
                ", conversationType=" + conversationType +
                ", title='" + title + '\'' +
                ", unreadMessagesCount=" + unreadMessagesCount +
                ", buddy=" + buddy +
                ", participants=" + participants +
                ", messages=" + messages +
                ", updatedAt=" + updatedAt +
                ", openedAt=" + openedAt +
                ", firstMessage=" + firstMessage +
                ", lastMessage=" + lastMessage +
                '}';
    }
}
