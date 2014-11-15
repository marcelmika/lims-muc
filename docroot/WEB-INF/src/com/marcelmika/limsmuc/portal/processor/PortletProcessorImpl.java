/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.processor;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.request.RequestParameterKeys;
import com.marcelmika.limsmuc.portal.response.ResponseUtil;
import com.marcelmika.limsmuc.portal.controller.*;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import java.util.Random;

/**
 * Portlet processor contains business logic which decides what controller
 * should be called based on the query received in the request parameter.
 * This is an important part of the system. Every time you introduce new controller
 * or add a method to the existing one you need to add mapping to the processRequest() method.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/12/14
 * Time: 9:44 AM
 */
public class PortletProcessorImpl implements PortletProcessor {

    // Log
    private static Log log = LogFactoryUtil.getLog(PortletProcessorImpl.class);

    // Controllers
    BuddyController buddyController;
    ConversationController conversationController;
    GroupController groupController;
    SettingsController settingsController;
    ServerController serverController;
    PropertiesController propertiesController;

    // Private properties
    private Random random = new Random();

    // Client returns query parameter. Thanks to this we can decide which method should be called.
    // If we were RESTFul we would be using resources at different urls. However, since liferay gives us
    // only one url for AJAX communication we need such query parameter to decide which method and resource
    // should be used.
    private static final String QUERY_CREATE_SINGLE_USER_CONVERSATION = "CreateSingleUserConversation";
    private static final String QUERY_READ_SINGLE_USER_CONVERSATION = "ReadSingleUserConversation";
    private static final String QUERY_CLOSE_SINGLE_USER_CONVERSATION = "CloseSingleUserConversation";
    private static final String QUERY_RESET_UNREAD_MESSAGES_COUNTER = "ResetUnreadMessagesCounter";
    private static final String QUERY_READ_OPENED_CONVERSATIONS = "ReadOpenedConversations";
    private static final String QUERY_READ_CONVERSATIONS = "ReadConversations";
    private static final String QUERY_ADD_PARTICIPANTS = "AddParticipants";
    private static final String QUERY_LEAVE_CONVERSATION = "LeaveConversation";
    private static final String QUERY_SWITCH_CONVERSATIONS = "SwitchConversations";
    private static final String QUERY_GET_GROUP_LIST = "GetGroupList";
    private static final String QUERY_CREATE_MESSAGE = "CreateMessage";
    private static final String QUERY_UPDATE_BUDDY_PRESENCE = "UpdateBuddyPresence";
    private static final String QUERY_READ_SETTINGS = "ReadSettings";
    private static final String QUERY_UPDATE_ACTIVE_PANEL = "UpdateActivePanel";
    private static final String QUERY_UPDATE_SETTINGS = "UpdateSettings";
    private static final String QUERY_GET_SERVER_TIME = "GetServerTime";
    private static final String QUERY_SEARCH_BUDDIES = "SearchBuddies";
    private static final String QUERY_PATCH_PROPERTIES = "PatchProperties";

    /**
     * Constructor
     *
     * @param buddyController        BuddyController
     * @param conversationController ConversationController
     * @param groupController        GroupController
     * @param settingsController     SettingsController
     * @param serverController       ServerController
     * @param propertiesController   PropertiesController
     */
    public PortletProcessorImpl(final BuddyController buddyController,
                                final ConversationController conversationController,
                                final GroupController groupController,
                                final SettingsController settingsController,
                                final ServerController serverController,
                                final PropertiesController propertiesController) {

        this.buddyController = buddyController;
        this.conversationController = conversationController;
        this.groupController = groupController;
        this.settingsController = settingsController;
        this.serverController = serverController;
        this.propertiesController = propertiesController;
    }

    /**
     * Adds request further to the system and writes data to response if needed.
     * Contains logic that decides which resource should be accessed.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void processRequest(ResourceRequest request, ResourceResponse response) {

        // This is for time profiling purposes
        long start = 0;

        // Log request
        if (log.isDebugEnabled()) {
            logRequest(request);
            start = System.currentTimeMillis();
        }

        // If the error mode is on and a random error was added to the response
        // don't continue in the request processing
        if (processErrorMode(request, response)) {
            return;
        }

        // Get query type from parameter
        String query = request.getParameter(RequestParameterKeys.KEY_QUERY);

        // Return error response if no query was set
        if (query == null) {
            ResponseUtil.writeResponse(null, HttpStatus.BAD_REQUEST, response);
            return;
        }

        // Decide which method should be called
        dispatchRequest(request, response, query);

        // Debug time
        if (log.isDebugEnabled()) {
            long end = System.currentTimeMillis();
            log.debug(String.format("[%s] TIME: %dms",
                    request.getParameter(RequestParameterKeys.KEY_QUERY),
                    (end - start)
            ));
        }
    }

    /**
     * Calls all appropriate methods on PollerProcessor that are scheduled for the receive request event.
     *
     * @param response request from browser
     * @param request  response sent to browser
     */
    private void dispatchRequest(ResourceRequest request,
                                 ResourceResponse response,
                                 String query) {

        // Create Single User Conversation
        if (query.equals(QUERY_CREATE_SINGLE_USER_CONVERSATION)) {
            conversationController.createSingleUserConversation(request, response);
        }
        // Read Single User Conversation
        else if (query.equals(QUERY_READ_SINGLE_USER_CONVERSATION)) {
            conversationController.readSingleUserConversation(request, response);
        }
        // Close Single User Conversation
        else if (query.equals(QUERY_CLOSE_SINGLE_USER_CONVERSATION)) {
            conversationController.closeSingleUserConversation(request, response);
        }
        // Reset Unread Messages Counter
        else if (query.equals(QUERY_RESET_UNREAD_MESSAGES_COUNTER)) {
            conversationController.resetUnreadMessagesCounter(request, response);
        }
        // Read Opened Conversations
        else if (query.equals(QUERY_READ_OPENED_CONVERSATIONS)) {
            conversationController.readOpenedConversations(request, response);
        }
        // Read Conversations
        else if (query.equals(QUERY_READ_CONVERSATIONS)) {
            conversationController.readConversations(request, response);
        }
        // Add Participants
        else if (query.equals(QUERY_ADD_PARTICIPANTS)) {
            conversationController.addParticipants(request, response);
        }
        // Leave Conversation
        else if (query.equals(QUERY_LEAVE_CONVERSATION)) {
            conversationController.leaveConversation(request, response);
        }
        // Switch Conversations
        else if (query.equals(QUERY_SWITCH_CONVERSATIONS)) {
            conversationController.switchConversations(request, response);
        }
        // Get Group List
        else if (query.equals(QUERY_GET_GROUP_LIST)) {
            groupController.getGroupList(request, response);
        }
        // Send message
        else if (query.equals(QUERY_CREATE_MESSAGE)) {
            conversationController.createMessage(request, response);
        }
        // Update buddy presence
        else if (query.equals(QUERY_UPDATE_BUDDY_PRESENCE)) {
            buddyController.updateBuddyPresence(request, response);
        }
        // Update active panel
        else if (query.equals(QUERY_UPDATE_ACTIVE_PANEL)) {
            settingsController.updateActivePanel(request, response);
        }
        // Read settings connection
        else if (query.equals(QUERY_READ_SETTINGS)) {
            settingsController.readSettings(request, response);
        }
        // Update settings
        else if (query.equals(QUERY_UPDATE_SETTINGS)) {
            settingsController.updateSettings(request, response);
        }
        // Get Server Time
        else if (query.equals(QUERY_GET_SERVER_TIME)) {
            serverController.getServerTime(request, response);
        }
        // Search buddies
        else if (query.equals(QUERY_SEARCH_BUDDIES)) {
            buddyController.searchBuddies(request, response);
        }
        // Patch properties
        else if (query.equals(QUERY_PATCH_PROPERTIES)) {
            propertiesController.patchProperties(request, response);
        }
        // No such query was found
        else {
            // Write 404 to response
            ResponseUtil.writeResponse(HttpStatus.NOT_FOUND, response);
        }
    }

    /**
     * If the error mode is on randomly adds an error to the response.
     * Returns true if the error was added to the response
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     * @return true if an error was added to the response
     */
    private boolean processErrorMode(ResourceRequest request, ResourceResponse response) {

        // Process error only if the error mode is enabled
        if (Environment.isErrorModeEnabled() != null && Environment.isErrorModeEnabled()) {

            // Generates random number between 0 and 10
            int number = random.nextInt(10) + 1;

            // Get query type from parameter
            String query = request.getParameter(RequestParameterKeys.KEY_QUERY);

            if (query.equals(QUERY_CREATE_SINGLE_USER_CONVERSATION) && number > 5) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }

            if (query.equals(QUERY_READ_SINGLE_USER_CONVERSATION) && number > 5) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }

            if (query.equals(QUERY_READ_OPENED_CONVERSATIONS) && number > 9) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }

            if (query.equals(QUERY_READ_CONVERSATIONS) && number > 5) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }

            if (query.equals(QUERY_LEAVE_CONVERSATION) && number > 3) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }

            if (query.equals(QUERY_GET_GROUP_LIST) && number > 8) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }

            if (query.equals(QUERY_CREATE_MESSAGE) && number > 6) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }

            if (query.equals(QUERY_SEARCH_BUDDIES) && number > 5) {
                ResponseUtil.writeResponse(HttpStatus.INTERNAL_SERVER_ERROR, response);
                return true;
            }
        }

        return false;
    }

    /**
     * Logs request data
     *
     * @param request ResourceRequest
     */
    private void logRequest(ResourceRequest request) {

        // Log request params
        if (request.getParameter(RequestParameterKeys.KEY_PARAMETERS) != null) {
            log.debug(String.format("[%s] PARAMS: %s",
                            request.getParameter(RequestParameterKeys.KEY_QUERY),
                            request.getParameter(RequestParameterKeys.KEY_PARAMETERS)
                    )
            );
        }
        // Log request content
        if (request.getParameter(RequestParameterKeys.KEY_CONTENT) != null) {
            log.debug(String.format("[%s] CONTENT: %s",
                    request.getParameter(RequestParameterKeys.KEY_QUERY),
                    request.getParameter(RequestParameterKeys.KEY_CONTENT)
            ));
        }
    }
}