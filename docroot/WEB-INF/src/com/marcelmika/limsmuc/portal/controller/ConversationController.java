/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.controller;

import com.liferay.portal.kernel.json.JSONDeserializer;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.events.buddy.ReadPresenceChangeRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.ReadPresenceChangeResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.AddParticipantsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.AddParticipantsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.CloseConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.CloseConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.CreateConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.CreateConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.LeaveConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.LeaveConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.ReadSingleUserConversationRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.ReadSingleUserConversationResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.ResetUnreadMessagesCounterRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.ResetUnreadMessagesCounterResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.SendMessageRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.SendMessageResponseEvent;
import com.marcelmika.limsmuc.api.events.conversation.SwitchConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.SwitchConversationsResponseEvent;
import com.marcelmika.limsmuc.core.service.BuddyCoreService;
import com.marcelmika.limsmuc.core.service.ConversationCoreService;
import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.BuddyCollection;
import com.marcelmika.limsmuc.portal.domain.Conversation;
import com.marcelmika.limsmuc.portal.domain.ConversationCollection;
import com.marcelmika.limsmuc.portal.domain.ConversationType;
import com.marcelmika.limsmuc.portal.domain.ErrorMessage;
import com.marcelmika.limsmuc.portal.domain.Message;
import com.marcelmika.limsmuc.portal.domain.MessagePagination;
import com.marcelmika.limsmuc.portal.domain.MessageType;
import com.marcelmika.limsmuc.portal.domain.Presence;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.localization.ConversationLocalizationUtil;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.request.parameters.AddParticipantsParameters;
import com.marcelmika.limsmuc.portal.request.parameters.CloseConversationParameters;
import com.marcelmika.limsmuc.portal.request.parameters.CreateMessageParameters;
import com.marcelmika.limsmuc.portal.request.parameters.LeaveConversationParameters;
import com.marcelmika.limsmuc.portal.request.parameters.ReadConversationParameters;
import com.marcelmika.limsmuc.portal.request.parameters.ReadConversationsParameters;
import com.marcelmika.limsmuc.portal.request.parameters.ReadOpenedConversationsParameters;
import com.marcelmika.limsmuc.portal.request.parameters.ResetUnreadMessagesCounterParameters;
import com.marcelmika.limsmuc.portal.request.parameters.SwitchConversationsParameters;
import com.marcelmika.limsmuc.portal.response.ReadOpenedConversationsResponse;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 5:24 PM
 */
public class ConversationController {

    // Log
    private static Log log = LogFactoryUtil.getLog(ConversationController.class);

    // Dependencies
    ConversationCoreService conversationCoreService;
    BuddyCoreService buddyCoreService;

    /**
     * Constructor
     *
     * @param conversationCoreService ConversationCoreService
     */
    public ConversationController(final ConversationCoreService conversationCoreService,
                                  final BuddyCoreService buddyCoreService) {
        this.conversationCoreService = conversationCoreService;
        this.buddyCoreService = buddyCoreService;
    }

    /**
     * Creates single user conversation with a buddy selected in request
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void createSingleUserConversation(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                // Authorized user
        Conversation conversation;  // Conversation that should be created

        // Deserizalize
        try {
            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize Content
            conversation = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_CONTENT), Conversation.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }


        // Multi user chat conversation
        if (conversation.getParticipants().size() > 1) {
            conversation.setConversationType(ConversationType.MULTI_USER);
        }
        // Single user chat conversation
        else {
            conversation.setConversationType(ConversationType.SINGLE_USER);
        }

        // Add conversation to system
        CreateConversationResponseEvent responseEvent = conversationCoreService.createConversation(
                new CreateConversationRequestEvent(buddy.toBuddyDetails(), conversation.toConversationDetails(), null)
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map conversation from response
            Conversation conversationResponse = Conversation.fromConversationDetails(responseEvent.getConversation());
            // Localize conversation
            conversationResponse = ConversationLocalizationUtil.localizeConversation(conversationResponse, request);
            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(conversationResponse);
            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            CreateConversationResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == CreateConversationResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Unknown conversation type
            else if (status == CreateConversationResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE) {
                ResponseUtil.writeResponse(
                        ErrorMessage.badRequest("You must provide conversation type").serialize(),
                        HttpStatus.BAD_REQUEST,
                        response);
            }
            // Wrong parameters
            else if (status == CreateConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // MUC Collision
            else if (status == CreateConversationResponseEvent.Status.ERROR_MUC_COLLISION) {
                ResponseUtil.writeResponse(
                        ErrorMessage.conflict("MUC Conversation with such ID already exists").serialize(),
                        HttpStatus.CONFLICT,
                        response
                );
            }
            // SUC Collision
            else if (status == CreateConversationResponseEvent.Status.ERROR_SUC_COLLISION) {
                ResponseUtil.writeResponse(
                        ErrorMessage.conflict("You can't create conversation with yourself").serialize(),
                        HttpStatus.CONFLICT,
                        response
                );
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Reads Single User Conversation messages
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readSingleUserConversation(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                            // Authorized user
        ReadConversationParameters parameters;  // Request parameters

        // Deserialize
        try {
            // Buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), ReadConversationParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Create objects from parameters
        Conversation conversation = new Conversation();
        conversation.setConversationId(parameters.getConversationId());
        MessagePagination pagination = parameters.getPagination();

        // Read conversations
        ReadSingleUserConversationResponseEvent responseEvent = conversationCoreService.readConversation(
                new ReadSingleUserConversationRequestEvent(
                        buddy.toBuddyDetails(),                  // Buddy is participant
                        conversation.toConversationDetails(),    // Read proper conversation
                        pagination.toMessagePaginationDetails()) // Pagination request
        );

        // Success
        if (responseEvent.isSuccess()) {
            conversation = Conversation.fromConversationDetails(responseEvent.getConversation());

            // Client has fresh copy so there is no need to send it
            if (conversation.getEtag().equals(parameters.getEtag())) {
                ResponseUtil.writeResponse(HttpStatus.NOT_MODIFIED, response);
                return;
            }

            // Localize conversation
            conversation = ConversationLocalizationUtil.localizeConversation(conversation, request);

            // Serialize
            String serialized = JSONFactoryUtil.looseSerialize(conversation,
                    "messages",
                    "messages.from",
                    "participants"
            );

            // Write success to response
            ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
        }
        // Failure
        else {
            ReadSingleUserConversationResponseEvent.Status status = responseEvent.getStatus();
            // Not found
            if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Forbidden
            else if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_FORBIDDEN) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Unauthorized
            else if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad request
            else if (status == ReadSingleUserConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Closes Single User Conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void closeSingleUserConversation(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                                // Authorized user
        CloseConversationParameters parameters;     // Request parameters


        // Deserizalize
        try {
            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), CloseConversationParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Close conversation
        CloseConversationResponseEvent responseEvent = conversationCoreService.closeConversation(
                new CloseConversationRequestEvent(buddy.getBuddyId(), parameters.getConversationId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            CloseConversationResponseEvent.Status status = responseEvent.getStatus();
            // Conversation not found
            if (status == CloseConversationResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND) {
                ResponseUtil.writeResponse(
                        ErrorMessage.notFound("No such conversation has been found").serialize(),
                        HttpStatus.NOT_FOUND,
                        response
                );
            }
            // Participant not found
            else if (status == CloseConversationResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND) {
                ResponseUtil.writeResponse(
                        ErrorMessage.notFound("No such participant has been found").serialize(),
                        HttpStatus.NOT_FOUND,
                        response
                );
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Resets unread messages counter for the given conversation and participant
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void resetUnreadMessagesCounter(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                                        // Authorized user
        ResetUnreadMessagesCounterParameters parameters;    // Request parameters

        // Deserialize
        try {
            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS),
                    ResetUnreadMessagesCounterParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Reset counter
        ResetUnreadMessagesCounterResponseEvent responseEvent = conversationCoreService.resetUnreadMessagesCounter(
                new ResetUnreadMessagesCounterRequestEvent(buddy.getBuddyId(), parameters.getConversationId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            ResetUnreadMessagesCounterResponseEvent.Status status = responseEvent.getStatus();
            // Conversation not found
            if (status == ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_CONVERSATION_FOUND) {
                ResponseUtil.writeResponse(
                        ErrorMessage.notFound("No such conversation has been found").serialize(),
                        HttpStatus.NOT_FOUND,
                        response
                );
            }
            // Participant not found
            else if (status == ResetUnreadMessagesCounterResponseEvent.Status.ERROR_NO_PARTICIPANT_FOUND) {
                ResponseUtil.writeResponse(
                        ErrorMessage.notFound("No such participant has been found").serialize(),
                        HttpStatus.NOT_FOUND,
                        response
                );
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Reads currently opened conversations
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readOpenedConversations(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                                    // Authorized user
        ReadOpenedConversationsParameters parameters;   // Request parameters

        // Deserialize
        try {
            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS),
                    ReadOpenedConversationsParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Read conversations
        GetOpenedConversationsResponseEvent responseEvent = conversationCoreService.getOpenedConversations(
                new GetOpenedConversationsRequestEvent(buddy.toBuddyDetails())
        );

        // Read statuses
        ReadPresenceChangeResponseEvent presenceChangeResponseEvent = buddyCoreService.readPresenceChange(
                new ReadPresenceChangeRequestEvent(buddy.getBuddyId(), parameters.getSince())
        );

        // Success
        if (responseEvent.isSuccess()) {

            // Response is composed thus we need a special response object
            ReadOpenedConversationsResponse responseObject = new ReadOpenedConversationsResponse();

            // Map conversation from details
            List<Conversation> conversationList = Conversation.fromConversationDetailsList(
                    responseEvent.getConversationDetails()
            );

            // Add list of conversations to the response object
            responseObject.setConversations(conversationList);

            // If presence was changed
            if (presenceChangeResponseEvent.isSuccess()) {
                // Map from service response, don't add user data from portal since we only need the presence
                List<Buddy> buddies = Buddy.fromBuddyDetailsList(presenceChangeResponseEvent.getBuddies(), false);

                // Add to response object
                responseObject.setChangedPresences(buddies);
            }

            // Serialize
            String serializedConversations = JSONFactoryUtil.looseSerialize(
                    responseObject, "conversations", "changedPresences"
            );

            // Write success to response
            ResponseUtil.writeResponse(serializedConversations, HttpStatus.OK, response);
        }
        // Failure
        else {
            GetOpenedConversationsResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == GetOpenedConversationsResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad Request
            else if (status == GetOpenedConversationsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Reads all conversations of the given user
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void readConversations(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                            // Authorized user
        ReadConversationsParameters parameters; // Parameters for the request

        // Deserialize
        try {
            // Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), ReadConversationsParameters.class
            );

            // Create buddy from request
            buddy = Buddy.fromResourceRequest(request);
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Read all conversations
        GetConversationsResponseEvent responseEvent = conversationCoreService.getConversations(
                new GetConversationsRequestEvent(
                        buddy.toBuddyDetails(), parameters.getPagination().toConversationPaginationDetails()
                ));

        // Success
        if (responseEvent.isSuccess()) {
            // Map conversation collection
            ConversationCollection conversationCollection = ConversationCollection.fromConversationCollectionDetails(
                    responseEvent.getConversationCollection()
            );

            // ... and compare it with group collection etag
            // Cached
            if (parameters.getEtag().equals(conversationCollection.getEtag())) {
                // Etags equal which means that nothing has changed
                ResponseUtil.writeResponse(HttpStatus.NOT_MODIFIED, response);
            }
            // Not cached
            else {
                // Localize conversations
                conversationCollection.setConversations(ConversationLocalizationUtil.localizeConversationList(
                        conversationCollection.getConversations(), request
                ));
                // Serialize
                String serialized = JSONFactoryUtil.looseSerialize(conversationCollection,
                        "conversations",
                        "conversations.lastMessage",
                        "conversations.lastMessage.from",
                        "conversations.participants"
                );
                // Write success to response
                ResponseUtil.writeResponse(serialized, HttpStatus.OK, response);
            }
        }
        // Failure
        else {
            GetConversationsResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == GetConversationsResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Bad Request
            else if (status == GetConversationsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Adds participants to the existing MUC conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void addParticipants(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                            // Authorized user
        AddParticipantsParameters parameters;   // Request parameters
        List<Buddy> participants;               // List of participants
        BuddyCollection buddyCollection;        // Collection of participants

        // Deserialize
        try {
            // Buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), AddParticipantsParameters.class
            );


            // Create deserializer for participants
            JSONDeserializer<List<Buddy>> deserializer = JSONFactoryUtil.createJSONDeserializer();

            // Buddies will come in an array
            participants = deserializer.use("values", Buddy.class).deserialize(
                    request.getParameter(RequestParameterKeys.KEY_CONTENT)
            );

            // Create buddy collection from the list
            buddyCollection = BuddyCollection.fromBuddyList(participants);

        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Add to system
        AddParticipantsResponseEvent responseEvent = conversationCoreService.addParticipants(
                new AddParticipantsRequestEvent(
                        buddy.toBuddyDetails(),
                        parameters.getConversationId(),
                        buddyCollection.toBuddyCollectionDetails()
                ));

        // Success
        if (responseEvent.isSuccess()) {
            // Write success to response
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            AddParticipantsResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == AddParticipantsResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Forbidden
            else if (status == AddParticipantsResponseEvent.Status.ERROR_FORBIDDEN) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Not found
            else if (status == AddParticipantsResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Not MUC
            else if (status == AddParticipantsResponseEvent.Status.ERROR_NOT_MUC) {
                ResponseUtil.writeResponse(
                        ErrorMessage.badRequest("Participants can be added to SUC conversation only").serialize(),
                        HttpStatus.BAD_REQUEST,
                        response);
            }
            // Wrong parameters
            else if (status == AddParticipantsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }

    }

    /**
     * Leave conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void leaveConversation(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                                // Authorized user
        LeaveConversationParameters parameters;     // Request parameters

        // Deserialize
        try {
            // Buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), LeaveConversationParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Add to system
        LeaveConversationResponseEvent responseEvent = conversationCoreService.leaveConversation(
                new LeaveConversationRequestEvent(buddy.toBuddyDetails(), parameters.getConversationId())
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Write success to response
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            LeaveConversationResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == LeaveConversationResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.UNAUTHORIZED, response);
            }
            // Forbidden
            else if (status == LeaveConversationResponseEvent.Status.ERROR_FORBIDDEN) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Not found
            else if (status == LeaveConversationResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Not MUC
            else if (status == LeaveConversationResponseEvent.Status.ERROR_NOT_MUC) {
                ResponseUtil.writeResponse(
                        ErrorMessage.badRequest("Participants can be added to SUC conversation only").serialize(),
                        HttpStatus.BAD_REQUEST,
                        response);
            }
            // Wrong parameters
            else if (status == LeaveConversationResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Switches two conversations positions
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void switchConversations(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                                // Authorized user
        SwitchConversationsParameters parameters;   // Request parameters

        // Deserialize
        try {
            // Buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), SwitchConversationsParameters.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Switch conversations
        SwitchConversationsResponseEvent responseEvent = conversationCoreService.switchConversations(
                new SwitchConversationsRequestEvent(
                        buddy.toBuddyDetails(),
                        parameters.getFirstConversationId(),
                        parameters.getSecondConversationId()
                ));

        // Success
        if (responseEvent.isSuccess()) {
            // Write success to response
            ResponseUtil.writeResponse(HttpStatus.NO_CONTENT, response);
        }
        // Failure
        else {
            SwitchConversationsResponseEvent.Status status = responseEvent.getStatus();
            // Forbidden
            if (status == SwitchConversationsResponseEvent.Status.ERROR_FORBIDDEN) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Not found
            else if (status == SwitchConversationsResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Bad request
            else if (status == SwitchConversationsResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }

    /**
     * Create new message in conversation
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void createMessage(ResourceRequest request, ResourceResponse response) {

        Buddy buddy;                        // Authorized user
        CreateMessageParameters parameters; // Request parameters
        Message message;                    // Message that should be sent

        // Deserialize
        try {
            // Buddy from request
            buddy = Buddy.fromResourceRequest(request);

            // Deserialize Parameters
            parameters = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_PARAMETERS), CreateMessageParameters.class
            );
            // Deserialize Content
            message = JSONFactoryUtil.looseDeserialize(
                    request.getParameter(RequestParameterKeys.KEY_CONTENT), Message.class
            );
        }
        // Failure
        catch (Exception exception) {
            // Bad request
            ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // End here
            return;
        }

        // Create new conversation that will be passed to the system
        Conversation conversation = new Conversation();
        conversation.setConversationId(parameters.getConversationId());
        conversation.setBuddy(buddy);

        // This is a regular type of the message
        message.setMessageType(MessageType.REGULAR);

        // Add to system
        SendMessageResponseEvent responseEvent = conversationCoreService.sendMessage(
                new SendMessageRequestEvent(
                        buddy.toBuddyDetails(),                 // Creator
                        conversation.toConversationDetails(),   // Conversation
                        message.toMessageDetails())             // Message
        );

        // Success
        if (responseEvent.isSuccess()) {
            // Map message from response
            Message responseMessage = Message.fromMessageDetails(responseEvent.getMessage());
            // Serialize
            String serializedMessage = JSONFactoryUtil.looseSerialize(responseMessage);
            // Write success to response
            ResponseUtil.writeResponse(serializedMessage, HttpStatus.OK, response);
        }
        // Failure
        else {
            SendMessageResponseEvent.Status status = responseEvent.getStatus();
            // Unauthorized
            if (status == SendMessageResponseEvent.Status.ERROR_NO_SESSION) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Forbidden
            else if (status == SendMessageResponseEvent.Status.ERROR_FORBIDDEN) {
                ResponseUtil.writeResponse(HttpStatus.FORBIDDEN, response);
            }
            // Not found
            else if (status == SendMessageResponseEvent.Status.ERROR_NOT_FOUND) {
                ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
            }
            // Unknown conversation type
            else if (status == SendMessageResponseEvent.Status.ERROR_UNKNOWN_CONVERSATION_TYPE) {
                ResponseUtil.writeResponse(
                        ErrorMessage.badRequest("Unknown conversation type").serialize(),
                        HttpStatus.BAD_REQUEST,
                        response
                );
            }
            // Wrong parameters
            else if (status == SendMessageResponseEvent.Status.ERROR_WRONG_PARAMETERS) {
                ResponseUtil.writeResponse(HttpStatus.BAD_REQUEST, response);
            }
            // Everything else is a server fault
            else {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                // Log
                if (log.isDebugEnabled()) {
                    log.debug(responseEvent.getException());
                }
            }
        }
    }
}
