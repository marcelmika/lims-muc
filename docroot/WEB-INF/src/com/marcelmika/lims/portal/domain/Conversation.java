/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
    private Integer unreadMessagesCount;
    private Buddy buddy;
    private List<Buddy> participants;
    private List<Message> messages;
    private Date updatedAt;
    private Message firstMessage;
    private Message lastMessage;

    // Maximal size of the buddy's name the title
    private static final int BUDDY_TITLE_MAX_SIZE = 10;


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

    /**
     * Returns conversation title
     *
     * @return String title
     */
    @JSON
    public String getTitle() {

        String title = "-"; // Default

        // Current user needs to be filtered of the list of participants
        List<Buddy> filteredParticipants = filterParticipants(participants, buddy);

        // Single user conversation title
        if (conversationType == ConversationType.SINGLE_USER) {
            title = generateSUCTitle(filteredParticipants);
        }
        // Multi user conversation title
        else if (conversationType == ConversationType.MULTI_USER) {
            title = generateMUCTitle(filteredParticipants, buddy);
        }

        return title;
    }

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
                "lastMessage=" + lastMessage +
                ", firstMessage=" + firstMessage +
                ", updatedAt=" + updatedAt +
                ", messages=" + messages +
                ", participants=" + participants +
                ", buddy=" + buddy +
                ", unreadMessagesCount=" + unreadMessagesCount +
                ", conversationType=" + conversationType +
                ", conversationId='" + conversationId + '\'' +
                '}';
    }


    /**
     * Generates single user chat title from the list of participants
     *
     * @param participants a list of participants
     * @return SUC title or "-" if an empty participants list was passed
     */
    private String generateSUCTitle(List<Buddy> participants) {
        if (participants.size() == 0) {
            return "-";
        }

        return participants.get(0).getFullName();
    }

    /**
     * Generates multi user chat title from the list of participants
     *
     * @param participants a list of participants
     * @param buddy used when no participants are in the conversation
     * @return MUC title of "-" if an empty list of participants was passed
     */
    private String generateMUCTitle(List<Buddy> participants, Buddy buddy) {

        String title;

        // No participants means that the user is left alone in the conversation
        if (participants.size() == 0) {
            title = buddy.getFullName();
        }

        // One participants in the MUC conversation means that
        // the other participants have left
        else if (participants.size() == 1) {
            title = participants.get(0).getFullName();
        }

        // We have exactly two participants
        else if (participants.size() == 2) {
            // TODO: i18n
            title = String.format("%s and %s",
                    generateBuddyTitleName(participants.get(0)),
                    generateBuddyTitleName(participants.get(1))
            );
        }
        // We have have more than two participants
        else {
            // TODO: i18n
            title = String.format("%s and %d others",
                    generateBuddyTitleName(participants.get(0)),
                    participants.size() - 1 // Minus you
            );
        }

        return title;
    }


    /**
     * Filter buddy given in parameter
     *
     * @param participants a list of participants
     * @param buddy        that will be filtered out
     * @return list of participants
     */
    private List<Buddy> filterParticipants(List<Buddy> participants, Buddy buddy) {

        List<Buddy> filteredParticipants = new LinkedList<Buddy>();

        for (Buddy participant : participants) {
            // Single user conversation has two participants. Title is "the other" participant than myself.
            if (!participant.getBuddyId().equals(buddy.getBuddyId())) {
                filteredParticipants.add(participant);
            }
        }

        return filteredParticipants;
    }

    /**
     * Generates title from buddy
     *
     * @param buddy Buddy
     * @return title
     */
    private String generateBuddyTitleName(Buddy buddy) {
        String name = "";

        // If the user has first name, use that
        if (buddy.getFirstName() != null) {
            name = buddy.getFirstName();
        }
        // If not use the last name
        else if (buddy.getLastName() != null) {
            name = buddy.getLastName();
        }
        // If none of those were set, use the full name
        else {
            name = buddy.getFullName();
        }

        // Limit the maximal size of the message to 10
        if (name.length() > BUDDY_TITLE_MAX_SIZE) {
            name = name.substring(BUDDY_TITLE_MAX_SIZE);
        }

        return name;
    }
}
