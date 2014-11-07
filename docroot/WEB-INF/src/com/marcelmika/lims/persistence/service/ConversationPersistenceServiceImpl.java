/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.events.conversation.*;
import com.marcelmika.lims.persistence.domain.*;
import com.marcelmika.lims.persistence.generated.NoSuchConversationException;
import com.marcelmika.lims.persistence.generated.NoSuchParticipantException;
import com.marcelmika.lims.persistence.generated.model.Participant;
import com.marcelmika.lims.persistence.generated.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.MessageLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.ParticipantLocalServiceUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 6/29/14
 * Time: 11:48 AM
 */
public class ConversationPersistenceServiceImpl implements ConversationPersistenceService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ConversationPersistenceServiceImpl.class);

    /**
     * Creates new conversation
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public CreateConversationResponseEvent createConversation(CreateConversationRequestEvent event) {

        Buddy creator = Buddy.fromBuddyDetails(event.getCreator());
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());

        // Save to persistence
        try {
            // User cannot create a multi user conversation that already exists
            if (conversation.getConversationType() == ConversationType.MULTI_USER) {
                // Try to find a conversation with the same id
                com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                        ConversationLocalServiceUtil.getConversation(conversation.getConversationId());

                // Conversation already exits -> call collision
                if (conversationModel != null) {
                    return CreateConversationResponseEvent.failure(
                            CreateConversationResponseEvent.Status.ERROR_MUC_COLLISION
                    );
                }
            }

            // Create conversation
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.addConversation(
                            conversation.getConversationId(), conversation.getConversationType().getCode()
                    );

            // Add participants to response
            List<Buddy> participants = conversation.getParticipants();

            // Save Participants
            for (Buddy buddy : participants) {
                ParticipantLocalServiceUtil.addParticipant(conversationModel.getCid(), buddy.getBuddyId());
            }

            // Creator is also participant
            Participant participantModel = ParticipantLocalServiceUtil.addParticipant(
                    conversationModel.getCid(), creator.getBuddyId(), true
            );

            participants.add(creator);

            // Create updated conversation
            conversation = Conversation.fromConversationModel(conversationModel);
            conversation.setUnreadMessagesCount(participantModel.getUnreadMessagesCount());
            conversation.setBuddy(creator);
            conversation.setParticipants(participants);

            // Call Success
            return CreateConversationResponseEvent.success(conversation.toConversationDetails());
        }
        // Failure
        catch (Exception exception) {
            return CreateConversationResponseEvent.failure(
                    CreateConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Reads messages from conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ReadSingleUserConversationResponseEvent readConversation(ReadSingleUserConversationRequestEvent event) {

        // Map to persistence objects
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());
        MessagePagination pagination = MessagePagination.fromMessagePaginationDetails(event.getPagination());
        Buddy buddy = Buddy.fromBuddyDetails(event.getParticipant());

        // Read data from persistence
        try {
            // Find conversation
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(
                            conversation.getConversationId()
                    );

            // No such conversation was found
            if (conversationModel == null) {
                return ReadSingleUserConversationResponseEvent.failure(
                        ReadSingleUserConversationResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Get user's participant model for the given conversation
            Participant participant = ParticipantLocalServiceUtil.getParticipant(
                    conversationModel.getCid(), buddy.getBuddyId()
            );

            // User is not in the conversation thus he can't read it
            if (participant == null) {
                return ReadSingleUserConversationResponseEvent.failure(
                        ReadSingleUserConversationResponseEvent.Status.ERROR_FORBIDDEN
                );
            }

            // Map conversation from the persistence conversation model
            conversation = Conversation.fromConversationModel(conversationModel);

            // Add messages
            conversation.setMessages(readMessages(conversationModel.getCid(), pagination));
            // Add first message
            conversation.setFirstMessage(getFirstMessage(conversationModel.getCid()));
            // Add last message
            conversation.setLastMessage(getLastMessage(conversationModel.getCid()));
            // Add unread messages count
            conversation.setUnreadMessagesCount(participant.getUnreadMessagesCount());
            // Add participants
            conversation.setParticipants(readParticipants(conversationModel.getCid()));
            // Add buddy
            conversation.setBuddy(buddy);
            // Add opened at info
            conversation.setOpenedAt(participant.getOpenedAt());

            // Call Success
            return ReadSingleUserConversationResponseEvent.success(
                    conversation.toConversationDetails()
            );

        } catch (Exception exception) {
            // Call Failure
            return ReadSingleUserConversationResponseEvent.failure(
                    ReadSingleUserConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Returns a list of participants related to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public GetConversationParticipantsResponseEvent getParticipants(GetConversationParticipantsRequestEvent event) {
        // Get conversation from event
        Conversation conversation = Conversation.fromConversationDetails(event.getConversation());

        try {
            // Find conversation based on conversation ID
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(conversation.getConversationId());

            // Find participant related to the conversation
            List<Participant> participants = ParticipantLocalServiceUtil.getConversationParticipants(
                    conversationModel.getCid()
            );

            // Map participants to buddies
            List<Buddy> buddies = new LinkedList<Buddy>();
            for (Participant participant : participants) {

                // Do not include participants that have left
                if (participant.getHasLeft()) {
                    continue;
                }

                // Find user in Liferay
                User user = UserLocalServiceUtil.getUser(participant.getParticipantId());
                // Map Liferay user to buddy
                Buddy buddy = Buddy.fromUser(user);
                // Add to list
                buddies.add(buddy);
            }

            // Add buddies to conversation
            conversation.setParticipants(buddies);

            // Success
            return GetConversationParticipantsResponseEvent.success(
                    conversation.toConversationDetails()
            );
        }
        // Failure
        catch (Exception exception) {
            return GetConversationParticipantsResponseEvent.failure(
                    GetConversationParticipantsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Closes existing conversation. User remains in the conversation though.
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public CloseConversationResponseEvent closeConversation(CloseConversationRequestEvent event) {
        // Get parameters from event
        String conversationId = event.getConversationId();
        Long participantId = event.getBuddyId();

        try {
            // Close conversation for participant
            ParticipantLocalServiceUtil.closeConversation(conversationId, participantId);

            // Call success
            return CloseConversationResponseEvent.success();
        }
        // Persistence error
        catch (SystemException exception) {
            return CloseConversationResponseEvent.failure(
                    CloseConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
        // Conversation not found
        catch (NoSuchConversationException exception) {
            return CloseConversationResponseEvent.failure(
                    CloseConversationResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND, exception
            );
        }
        // Participant not found
        catch (NoSuchParticipantException exception) {
            return CloseConversationResponseEvent.failure(
                    CloseConversationResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND, exception
            );
        }
    }

    /**
     * Reset counter of unread messages (usually displayed in badge) for the particular user and conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public ResetUnreadMessagesCounterResponseEvent resetUnreadMessagesCounter(ResetUnreadMessagesCounterRequestEvent event) {
        // Get parameters from event
        String conversationId = event.getConversationId();
        Long participantId = event.getBuddyId();

        try {
            // Reset the counter
            ParticipantLocalServiceUtil.resetUnreadMessagesCounter(conversationId, participantId);

            // Call success
            return ResetUnreadMessagesCounterResponseEvent.success();

        }
        // Persistence error
        catch (SystemException exception) {
            return ResetUnreadMessagesCounterResponseEvent.failure(
                    ResetUnreadMessagesCounterResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
        // Conversation not found
        catch (NoSuchConversationException exception) {
            return ResetUnreadMessagesCounterResponseEvent.failure(
                    ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND, exception
            );
        }
        // Participant not found
        catch (NoSuchParticipantException exception) {
            return ResetUnreadMessagesCounterResponseEvent.failure(
                    ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND, exception
            );
        }
    }

    /**
     * Adds buddies to the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public AddParticipantsResponseEvent addParticipants(AddParticipantsRequestEvent event) {

        // Map to persistence objects
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddy());
        String conversationId = event.getConversationId();
        BuddyCollection buddyCollection = BuddyCollection.fromBuddyCollectionDetails(event.getBuddyCollectionDetails());

        // Save to persistence
        try {

            // Find conversation. Since each message is related to the conversation we need to find it first
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(conversationId);

            // No such conversation was found
            if (conversationModel == null) {
                // Failure
                return AddParticipantsResponseEvent.failure(AddParticipantsResponseEvent.Status.ERROR_NOT_FOUND);
            }

            // Check if the conversation is of the multi user type
            if (ConversationType.fromCode(conversationModel.getConversationType()) != ConversationType.MULTI_USER) {
                // Failure
                return AddParticipantsResponseEvent.failure(AddParticipantsResponseEvent.Status.ERROR_NOT_MUC);
            }

            // Check if the user is in the conversation
            Participant participant = ParticipantLocalServiceUtil.getParticipant(
                    conversationModel.getCid(), buddy.getBuddyId()
            );

            // User is not in the conversation thus he can't add participants
            if (participant == null) {
                // Failure
                return AddParticipantsResponseEvent.failure(AddParticipantsResponseEvent.Status.ERROR_FORBIDDEN);
            }

            // Add participants to conversation
            for (Buddy addedParticipant : buddyCollection.getBuddies()) {

                // Save to persistence
                ParticipantLocalServiceUtil.addParticipant(conversationModel.getCid(), addedParticipant.getBuddyId());

                // Create new user added message
                MessageLocalServiceUtil.addMessage(
                        conversationModel.getCid(),         // Message is related to the conversation
                        addedParticipant.getBuddyId(),      // Save the added buddy's id
                        MessageType.ADDED.getCode(),        // Message type
                        null,                               // Body of message is empty
                        Calendar.getInstance().getTime()    // Date of creation is now
                );
            }

            // Update conversation timestamp
            ConversationLocalServiceUtil.updateConversationTimestamp(conversationModel.getCid());

            // Return success
            return AddParticipantsResponseEvent.success();
        }
        // Failure
        catch (Exception exception) {
            // Return failure
            return AddParticipantsResponseEvent.failure(
                    AddParticipantsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }

    }

    /**
     * Removes buddy from the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public LeaveConversationResponseEvent leaveConversation(LeaveConversationRequestEvent event) {
        // Map to persistence objects
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddy());
        String conversationId = event.getConversationId();

        // Save to persistence
        try {
            // Find conversation. Since each message is related to the conversation we need to find it first
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(conversationId);

            // No such conversation was found
            if (conversationModel == null) {
                return LeaveConversationResponseEvent.failure(
                        LeaveConversationResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Check if the conversation is of the multi user type
            if (ConversationType.fromCode(conversationModel.getConversationType()) != ConversationType.MULTI_USER) {
                return LeaveConversationResponseEvent.failure(
                        LeaveConversationResponseEvent.Status.ERROR_NOT_MUC
                );
            }

            // Check if the user is in the conversation
            Participant participant = ParticipantLocalServiceUtil.getParticipant(
                    conversationModel.getCid(), buddy.getBuddyId()
            );

            // User is not in the conversation thus he can't leave it
            if (participant == null) {
                // Failure
                return LeaveConversationResponseEvent.failure(LeaveConversationResponseEvent.Status.ERROR_FORBIDDEN);
            }

            // Leave the conversation
            ParticipantLocalServiceUtil.leaveConversation(conversationModel.getCid(), buddy.getBuddyId());

            // Create new user has left message
            MessageLocalServiceUtil.addMessage(
                    conversationModel.getCid(),             // Message is related to the conversation
                    buddy.getBuddyId(),                     // Message is created by buddy
                    MessageType.LEFT.getCode(),             // Message type
                    null,                                   // Body of message
                    Calendar.getInstance().getTime()        // Date of creation
            );

            // Update conversation timestamp
            ConversationLocalServiceUtil.updateConversationTimestamp(conversationModel.getCid());

            // Return success
            return LeaveConversationResponseEvent.success();
        }
        // Failure
        catch (Exception exception) {
            // Return failure
            return LeaveConversationResponseEvent.failure(
                    LeaveConversationResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Switch conversations positions
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SwitchConversationsResponseEvent switchConversations(SwitchConversationsRequestEvent event) {

        // Map to persistence objects
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddy());
        String firstConversationId = event.getFirstConversationId();
        String secondConversationId = event.getSecondConversationId();

        // Save to persistence
        try {

            // Find conversations
            com.marcelmika.lims.persistence.generated.model.Conversation firstConversationModel =
                    ConversationLocalServiceUtil.getConversation(firstConversationId);
            com.marcelmika.lims.persistence.generated.model.Conversation secondConversationModel =
                    ConversationLocalServiceUtil.getConversation(secondConversationId);

            // Both conversations must exist
            if (firstConversationModel == null || secondConversationModel == null) {
                // Call failure
                return SwitchConversationsResponseEvent.failure(
                        SwitchConversationsResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Find participant objects
            Participant firstParticipant = ParticipantLocalServiceUtil.getParticipant(
                    firstConversationModel.getCid(), buddy.getBuddyId()
            );
            Participant secondParticipant = ParticipantLocalServiceUtil.getParticipant(
                    secondConversationModel.getCid(), buddy.getBuddyId()
            );

            // User must participate in both conversations
            if (firstParticipant == null || secondParticipant == null) {
                // Call failure
                return SwitchConversationsResponseEvent.failure(
                        SwitchConversationsResponseEvent.Status.ERROR_FORBIDDEN
                );
            }

            // Switch conversations order
            ParticipantLocalServiceUtil.switchConversations(firstParticipant, secondParticipant);

            // Call success
            return SwitchConversationsResponseEvent.success();
        }
        // Failure
        catch (Exception exception) {
            // Call Failure
            return SwitchConversationsResponseEvent.failure(
                    SwitchConversationsResponseEvent.Status.ERROR_PERSISTENCE
            );
        }
    }

    /**
     * Creates message within the conversation
     *
     * @param event request event for method
     * @return response event for method
     */
    @Override
    public SendMessageResponseEvent sendMessage(SendMessageRequestEvent event) {
        // Map to persistence objects
        Conversation conversation = Conversation.fromConversationDetails(event.getConversationDetails());
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        Message message = Message.fromMessageDetails(event.getMessageDetails());

        // Save to persistence
        try {
            // Find conversation. Since each message is related to the conversation we need to find it first
            com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                    ConversationLocalServiceUtil.getConversation(conversation.getConversationId());

            // No such conversation was found
            if (conversationModel == null) {
                return SendMessageResponseEvent.failure(
                        SendMessageResponseEvent.Status.ERROR_NOT_FOUND
                );
            }

            // Check if the user is in the conversation
            Participant participant = ParticipantLocalServiceUtil.getParticipant(
                    conversationModel.getCid(), buddy.getBuddyId()
            );

            // User is not in the conversation thus he can't leave it
            if (participant == null) {
                // Failure
                return SendMessageResponseEvent.failure(SendMessageResponseEvent.Status.ERROR_FORBIDDEN);
            }

            // Create new message
            com.marcelmika.lims.persistence.generated.model.Message messageModel = MessageLocalServiceUtil.addMessage(
                    conversationModel.getCid(),             // Message is related to the conversation
                    buddy.getBuddyId(),                     // Message is created by buddy
                    message.getMessageType().getCode(),     // Message type
                    message.getBody(),                      // Body of message
                    message.getCreatedAt()                  // Date of creation
            );

            // Notify participants about newly created messages. This will basically update message counters,
            // open conversation to users, etc.
            ParticipantLocalServiceUtil.updateParticipants(conversationModel.getCid(), buddy.getBuddyId());

            // Map message from message model
            Message successMessage = Message.fromMessageModel(messageModel);
            // Don't forget to add the buddy as the creator
            successMessage.setFrom(buddy);

            // Update conversation timestamp
            ConversationLocalServiceUtil.updateConversationTimestamp(conversationModel.getCid());

            // Call Success
            return SendMessageResponseEvent.success(successMessage.toMessageDetails());

        }
        // Failure
        catch (Exception exception) {
            // Call Failure
            return SendMessageResponseEvent.failure(
                    SendMessageResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Get all opened conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetOpenedConversationsResponseEvent getOpenedConversations(GetOpenedConversationsRequestEvent event) {
        // Map to persistence objects
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());

        try {
            // Get a list of conversations where the user participates and are opened
            List<Participant> buddyParticipates = ParticipantLocalServiceUtil.getOpenedConversations(
                    buddy.getBuddyId()
            );

            // Prepare the conversation list that will be returned
            List<Conversation> conversations = new LinkedList<Conversation>();

            // Go over all the participant objects and map the conversation data related to them
            for (Participant participates : buddyParticipates) {

                // Do not count participants that left the conversation
                if (participates.getHasLeft()) {
                    continue;
                }

                // Count the number of messages in the conversation
                Integer numberOfMessages = MessageLocalServiceUtil.countMessages(participates.getCid());

                // If the user is not the creator of the conversation and there is no message yet go on. It means
                // that the user who is creating the conversation hasn't sent any message yet. He may change
                // his mind and don't send a message at all. However, if there is no check like that the other
                // users will see this conversation.
                if (!participates.getIsCreator() && numberOfMessages == 0) {
                    continue;
                }

                // Read the conversation data
                Conversation conversation = readConversation(participates.getCid());

                // If such conversation was found
                if (conversation != null) {
                    // Add properties to conversation
                    conversation.setParticipants(readParticipants(participates.getCid()));
                    conversation.setUnreadMessagesCount(participates.getUnreadMessagesCount());
                    conversation.setBuddy(buddy);

                    // Add to the conversation list
                    conversations.add(conversation);
                }
            }

            // Create details from persistence object
            List<ConversationDetails> conversationDetails = new LinkedList<ConversationDetails>();
            for (Conversation conversation : conversations) {
                conversationDetails.add(conversation.toConversationDetails());
            }

            // Call Success
            return GetOpenedConversationsResponseEvent.success(conversationDetails);

        } catch (Exception exception) {
            // Call Failure
            return GetOpenedConversationsResponseEvent.failure(
                    GetOpenedConversationsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Get all conversations related to the particular buddy
     *
     * @param event request event for method
     * @return response event for  method
     */
    @Override
    public GetConversationsResponseEvent getConversations(GetConversationsRequestEvent event) {

        // Map to persistence objects
        Buddy buddy = Buddy.fromBuddyDetails(event.getBuddyDetails());
        ConversationPagination pagination = ConversationPagination.fromConversationPaginationDetails(
                event.getPagination()
        );

        try {

            // Get a count of all conversations where the user participates
            Integer maxPageSize = ParticipantLocalServiceUtil.getConversationsCount(buddy.getBuddyId());
            Integer pageSize = Environment.getConversationFeedMaxConversations();
            Integer currentPageSize = pagination.getPageSize();
            Boolean readMore = pagination.getReadMore();

            // Get a list of conversations where the user participates
            List<Participant> buddyParticipates = ParticipantLocalServiceUtil.getConversations(
                    buddy.getBuddyId(), pageSize, currentPageSize, maxPageSize, readMore
            );

            // Prepare conversations container
            List<Conversation> conversations = new LinkedList<Conversation>();

            // Find conversations where the user participates
            for (Participant participates : buddyParticipates) {

                // Read conversation from persistence
                Conversation conversation = readConversation(participates.getCid());

                if (conversation != null) {
                    // Get the last message
                    Message lastMessage = getLastMessage(participates.getCid());

                    // Don't include conversation that have no messages
                    if (lastMessage != null) {

                        // Add properties to the conversation
                        conversation.setParticipants(readParticipants(participates.getCid()));
                        conversation.setUnreadMessagesCount(participates.getUnreadMessagesCount());
                        conversation.setBuddy(buddy);
                        conversation.setLastMessage(lastMessage);

                        // Add to container
                        conversations.add(conversation);
                    }
                }
            }

            // Create conversation collection
            ConversationCollection conversationCollection = new ConversationCollection();

            // Check the last modification
            Date lastModification = null;
            if (conversations.size() > 0) {
                // Last modification is whenever the top most conversation is updated
                // by e.g. sending a new message to it
                lastModification = conversations.get(0).getUpdatedAt();
            }

            // Set data to conversation collection
            conversationCollection.setConversations(conversations);
            conversationCollection.setLastModified(lastModification);
            conversationCollection.setPageSize(conversations.size());
            conversationCollection.setMaxSize(maxPageSize);

            // Call success
            return GetConversationsResponseEvent.success(conversationCollection.toConversationCollectionDetails());

        } catch (Exception exception) {
            // Call Failure
            return GetConversationsResponseEvent.failure(
                    GetConversationsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Returns a mapped conversation from the model cid
     *
     * @param cid id of the conversation
     * @return mapped conversation
     * @throws Exception
     */
    private Conversation readConversation(Long cid) throws Exception {

        // Find by cid
        com.marcelmika.lims.persistence.generated.model.Conversation conversationModel =
                ConversationLocalServiceUtil.getConversation(cid);

        // No need to map anything else
        if (conversationModel == null) {
            return null;
        }

        // Finally, we have everything we needed
        return Conversation.fromConversationModel(conversationModel);
    }

    /**
     * Reads participants from the model
     *
     * @param cid id of the conversation
     * @return list of participants
     * @throws Exception
     */
    private List<Buddy> readParticipants(Long cid) throws Exception {
        // Get participants
        List<Participant> participantModels = ParticipantLocalServiceUtil.getConversationParticipants(
                cid
        );

        // Map to persistence
        List<Buddy> participants = new LinkedList<Buddy>();
        for (Participant participantModel : participantModels) {

            // Do not include participants that have left
            if (participantModel.getHasLeft()) {
                continue;
            }

            Buddy buddy = Buddy.fromParticipantModel(participantModel);
            participants.add(buddy);
        }

        return participants;
    }


    /**
     * Reads the message related to the conversation. The size of list is determined
     * by an instance of pagination received as a parameter
     *
     * @param cid        id of the conversation
     * @param pagination determines size of the returned list
     * @return a list of messages
     * @throws Exception
     */
    private List<Message> readMessages(Long cid, MessagePagination pagination) throws Exception {

        Integer pageSize = Environment.getConversationListMaxMessages();
        Long stopperId = pagination.getStopperId();
        Boolean readMore = pagination.getReadMore();

        // Get messages from persistence
        List<Object[]> messageObjects = MessageLocalServiceUtil.readMessages(
                cid, pageSize, stopperId, readMore
        );

        // Map to persistence domain
        return Message.toMessageList(messageObjects, 0);
    }

    /**
     * Returns first message in the conversation
     *
     * @param cid id of the conversation
     * @return first message, null if no message was found
     * @throws Exception
     */
    private Message getFirstMessage(Long cid) throws Exception {

        // Read the first message from persistence
        Object[] messageObject = MessageLocalServiceUtil.firstMessage(cid);

        // No message found
        if (messageObject == null) {
            return null;
        }

        return Message.fromPlainObject(messageObject, 0);
    }

    /**
     * Returns last message in the conversation
     *
     * @param cid id of the conversation
     * @return last message, null if no message was found
     * @throws Exception
     */
    private Message getLastMessage(Long cid) throws Exception {

        // Read the first message from persistence
        Object[] messageObject = MessageLocalServiceUtil.lastMessage(cid);

        // No message found
        if (messageObject == null) {
            return null;
        }

        return Message.fromPlainObject(messageObject, 0);
    }
}
