/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.domain;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.entity.ConversationDetails;
import org.jivesoftware.smack.Chat;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 1:07 AM
 */
public class SingleUserConversation {

    // Log
    private static Log log = LogFactoryUtil.getLog(SingleUserConversation.class);

    private String conversationId;
    private ConversationType conversationType;
    private Buddy buddy;
    private Buddy participant;
    private List<Message> messages = new ArrayList<Message>();


    // -------------------------------------------------------------------------------------------
    // Factory Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Factory method creates single user conversation from smack chat
     *
     * @param chat Conversation is created from the Chat.
     * @return SingleUserConversation
     */
    public static SingleUserConversation fromChat(Chat chat) {
        // Create new instance of conversation
        SingleUserConversation conversation = new SingleUserConversation();
        // Map properties
        conversation.conversationId = chat.getParticipant();
        // Map relationships
        conversation.participant = Buddy.fromChat(chat);
        conversation.conversationType = ConversationType.SINGLE_USER;

        return conversation;
    }

    public static SingleUserConversation fromConversationDetails(ConversationDetails details) {
        // Create new instance of conversation
        SingleUserConversation conversation = new SingleUserConversation();
        // Map properties
        conversation.conversationId = details.getConversationId();

        // Relations
        if (details.getConversationType() != null) {
            conversation.conversationType = ConversationType.fromConversationTypeDetails(details.getConversationType());
        }

        if (details.getParticipants() != null) {
            // The size of the participant list for a single user chat cannot be bigger than 2.
            // However, we should fail gracefully here. So we just take the first participant in the list
            // which isn't the owner of conversation and show a log message that should warn us.
            if (details.getParticipants().size() > 2) {
                if (log.isErrorEnabled()) {
                    log.error("The size of participants list for a single user conversation is bigger than 2." +
                            "This shouldn't normally happen.");
                }
            }

            // Get from by listing the participants
            for (BuddyDetails participant : details.getParticipants()) {
                // The creator of the conversation is not the participant
                if (participant.getBuddyId().equals(details.getBuddy().getBuddyId())) {
                    continue;
                }
                // Take first participant
                conversation.participant = Buddy.fromBuddyDetails(participant);
                break;
            }
        }

        if (details.getMessages() != null) {
            conversation.messages = Message.fromMessageDetails(details.getMessages());
        }

        return conversation;
    }

    public static SingleUserConversation fromMessage(Message message) {

        Buddy from = message.getFrom();
        Buddy to = message.getTo();

        // Create new conversation
        SingleUserConversation conversation = new SingleUserConversation();

        // Properties
        conversation.conversationType = ConversationType.SINGLE_USER;
        conversation.conversationId = createConversationId(from.getScreenName(), to.getScreenName());

        // Relations
        conversation.buddy = from;
        conversation.participant = to;

        return conversation;
    }

    public static List<ConversationDetails> toConversationDetailsList(List<SingleUserConversation> conversations) {
        // Create new list
        List<ConversationDetails> details = new ArrayList<ConversationDetails>();
        // Map
        for (SingleUserConversation conversation : conversations) {
            details.add(conversation.toConversationDetails());
        }

        return details;
    }

    public ConversationDetails toConversationDetails() {
        // Create new details
        ConversationDetails details = new ConversationDetails();
        // Properties
        details.setConversationId(conversationId);

        // Relations
        if (buddy != null) {
            details.setBuddy(buddy.toBuddyDetails());
        }

        if (conversationType != null) {
            details.setConversationType(conversationType.toConversationTypeDetails());
        }

        if (messages != null) {
            details.setMessages(Message.toMessageDetailsList(messages));
        }

        if (participant != null) {
            List<BuddyDetails> participants = new ArrayList<BuddyDetails>();
            participants.add(participant.toBuddyDetails());
            details.setParticipants(participants);
        }

        return details;
    }

    // -------------------------------------------------------------------------------------------
    // Private Methods
    // -------------------------------------------------------------------------------------------

    /**
     * Creates conversation id based on the users ids. Returns null if no conversation id can be created
     *
     * @param fromUser from user
     * @param toUser   to user
     * @return conversation id or null if no conversation id can be created
     */
    private static String createConversationId(String fromUser, String toUser) {

        if (fromUser == null || toUser == null) {
            return null; // No users were found
        }

        // Create the conversation id from the users
        int comparison = fromUser.compareToIgnoreCase(toUser);
        String conversationId;

        // Cannot have conversation with yourself
        if (comparison == 0) {
            return null;
        }
        // From user is lexicographically after To user
        else if (comparison < 0) {
            conversationId = String.format("%s_%s", fromUser, toUser);
        }
        // To user is lexicographically after To user
        else {
            conversationId = String.format("%s_%s", toUser, fromUser);
        }

        return conversationId;
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

    public Buddy getParticipant() {
        return participant;
    }

    public void setParticipant(Buddy participant) {
        this.participant = participant;
    }

    public Buddy getBuddy() {
        return buddy;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
