/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization.chp.version.v2_0_5;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.domain.ConversationType;
import com.marcelmika.limsmuc.persistence.domain.MessageType;
import com.marcelmika.limsmuc.persistence.generated.model.Conversation;
import com.marcelmika.limsmuc.persistence.generated.model.Message;
import com.marcelmika.limsmuc.persistence.generated.model.Panel;
import com.marcelmika.limsmuc.persistence.generated.model.Participant;
import com.marcelmika.limsmuc.persistence.generated.model.Settings;
import com.marcelmika.limsmuc.persistence.generated.service.ConversationLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.generated.service.MessageLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.generated.service.PanelLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.generated.service.ParticipantLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.generated.service.SynchronizationLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.synchronization.Synchronizer;
import com.marcelmika.limsmuc.persistence.synchronization.Version;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 21/11/14
 * Time: 18:21
 */
public class SynchronizerImpl implements Synchronizer {

    // Number of rows taken at once
    private static final int READ_TABLE_STEP_SIZE = 100;

    // Version type
    private static final Version VERSION = Version.CHAT_PORTLET_2_0_5;

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SynchronizerImpl.class);

    /**
     * Runs the synchronization process
     *
     * @throws SystemException
     */
    @Override
    public void run() throws SystemException {

        // Important note: The steps are idempotent. In other words they need to
        // be ran in the specific order

        synchronizeSettings();
        synchronizePanel();
        synchronizeConversation();
        synchronizeParticipant();
        synchronizeMessage();
    }

    /**
     * Sync MUC Settings table with Chat Portlet
     *
     * @throws SystemException
     */
    private void synchronizeSettings() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        try {

            do {
                // Find start and end
                int start = index * step;
                int end = (index + 1) * step;

                // Get from db
                objects = SynchronizationLocalServiceUtil.findSettings(VERSION.getDescription(), start, end);

                for (Object[] object : objects) {

                    // Get the user ID
                    Long userId = (Long) object[0];

                    // If the settings for the particular user are already there do nothing
                    Settings settings = SettingsLocalServiceUtil.fetchByUserId(userId, false);
                    if (settings != null) {
                        continue;
                    }

                    // Map chat portlet settings to muc settings object
                    settings = SettingsLocalServiceUtil.createSettings();
                    settings.setUserId(userId);
                    settings.setPresence("presence.online");
                    settings.setPresenceUpdatedAt(new Date(0));
                    settings.setMute((Boolean) object[2]);
                    settings.setChatEnabled(true);
                    settings.setAdminAreaOpened(false);
                    settings.setConnected(false);
                    settings.setConnectedAt(new Date((Long) object[1]));

                    // Save it
                    SettingsLocalServiceUtil.saveSettings(settings);
                }

                // Increase index
                index++;

            } while (objects.size() != 0); // Continue until there are no more objects
        }
        // System exception has to be thrown otherwise the transaction management wouldn't work
        catch (Exception exception) {
            throw new SystemException(exception);
        }
    }


    /**
     * Sync MUC Panel table with Chat Portlet
     *
     * @throws SystemException
     */
    private void synchronizePanel() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        try {

            do {
                // Find start and end
                int start = index * step;
                int end = (index + 1) * step;

                // Get from db
                objects = SynchronizationLocalServiceUtil.findPanel(VERSION.getDescription(), start, end);

                for (Object object : objects) {
                    // Get the user ID
                    Long userId = (Long) object;

                    // If the panel row for the particular user is already there do nothing
                    Panel panel = PanelLocalServiceUtil.fetchByUserId(userId, false);
                    if (panel != null) {
                        continue;
                    }

                    // Map panel
                    panel = PanelLocalServiceUtil.createPanel();
                    panel.setUserId(userId);
                    panel.setActivePanelId("");

                    // Save
                    PanelLocalServiceUtil.savePanel(panel);
                }

                // Increase index
                index++;

            } while (objects.size() != 0); // Continue until there are no more objects
        }
        // System exception has to be thrown otherwise the transaction management wouldn't work
        catch (Exception exception) {
            throw new SystemException(exception);
        }
    }

    /**
     * Sync MUC Conversation table with Chat Portlet
     *
     * @throws SystemException
     */
    private void synchronizeConversation() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        try {

            do {

                // Find start and end
                int start = index * step;
                int end = (index + 1) * step;

                // Get form db
                objects = SynchronizationLocalServiceUtil.findConversation(VERSION.getDescription(), start, end);

                for (Object[] object : objects) {

                    // First, compose conversation id
                    Long fromUserId = (Long) object[0];
                    Long toUserId = (Long) object[1];

                    // Get the conversation id
                    String conversationId = createConversationId(fromUserId, toUserId);

                    // No conversation can be created
                    if (conversationId == null) {
                        continue;
                    }

                    // Check if there is already such conversation
                    Conversation conversation = ConversationLocalServiceUtil.fetchByConversationId(
                            conversationId, false
                    );

                    // If the particular conversation is not in db yet
                    if (conversation == null) {
                        // Create a new one
                        conversation = ConversationLocalServiceUtil.createConversation();
                        conversation.setConversationId(conversationId);
                        conversation.setConversationType(ConversationType.SINGLE_USER.getCode()); // There is no MUC in chat portlet
                        conversation.setUpdatedAt(Calendar.getInstance().getTime());

                        // Save the conversation
                        ConversationLocalServiceUtil.saveConversation(conversation);
                    }
                }

                // Increment index
                index++;

            } while (objects.size() != 0); // Continue until there are no more objects
        }
        // System exception has to be thrown otherwise the transaction management wouldn't work
        catch (Exception exception) {
            throw new SystemException(exception);
        }
    }


    /**
     * Sync MUC Conversation table with Chat Portlet
     *
     * @throws SystemException
     */
    private void synchronizeParticipant() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        try {

            do {

                // Find start and end
                int start = index * step;
                int end = (index + 1) * step;

                // Get from db
                objects = SynchronizationLocalServiceUtil.findParticipant(VERSION.getDescription(), start, end);

                for (Object[] object : objects) {

                    // First, compose conversation id
                    Long fromUserId = (Long) object[0];
                    Long toUserId = (Long) object[1];

                    // Get the conversation id
                    String conversationId = createConversationId(fromUserId, toUserId);

                    // No conversation can be created
                    if (conversationId == null) {
                        continue;
                    }

                    // Check if there is already such conversation
                    Conversation conversation = ConversationLocalServiceUtil.fetchByConversationId(
                            conversationId, false
                    );

                    // Check if there is such participant already
                    Participant fromParticipant = ParticipantLocalServiceUtil.getParticipant(
                            conversation.getCid(), fromUserId, false
                    );

                    // The participant is not in the db yet
                    if (fromParticipant == null) {
                        // Create a new one
                        fromParticipant = ParticipantLocalServiceUtil.createParticipant();
                        fromParticipant.setCid(conversation.getCid());
                        fromParticipant.setParticipantId(fromUserId);
                        fromParticipant.setUnreadMessagesCount(0); // default
                        fromParticipant.setIsOpened(false); // default
                        fromParticipant.setIsCreator(false); // default
                        fromParticipant.setHasLeft(false); // will never happen in Chat portlet
                        fromParticipant.setOpenedAt(Calendar.getInstance().getTime()); // default

                        // Save
                        ParticipantLocalServiceUtil.saveParticipant(fromParticipant);
                    }

                    // Check if there is such participant already
                    Participant toParticipant = ParticipantLocalServiceUtil.getParticipant(
                            conversation.getCid(), fromUserId, false
                    );

                    // The participant is not in the db yet
                    if (toParticipant == null) {
                        // Create a new one
                        toParticipant = ParticipantLocalServiceUtil.createParticipant();
                        toParticipant.setCid(conversation.getCid());
                        toParticipant.setParticipantId(fromUserId);
                        toParticipant.setUnreadMessagesCount(0); // default
                        toParticipant.setIsOpened(false); // default
                        toParticipant.setIsCreator(false); // default
                        toParticipant.setHasLeft(false); // will never happen in Chat portlet
                        toParticipant.setOpenedAt(Calendar.getInstance().getTime()); // default

                        // Save
                        ParticipantLocalServiceUtil.saveParticipant(fromParticipant);
                    }
                }

                // Increase index
                index++;

            } while (objects.size() != 0); // Continue until there are no more objects
        }
        // System exception has to be thrown otherwise the transaction management wouldn't work
        catch (Exception exception) {
            throw new SystemException(exception);
        }
    }


    /**
     * Sync MUC Message table with Chat Portlet
     *
     * @throws SystemException
     */
    private void synchronizeMessage() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        try {

            do {

                // Find start and end
                int start = index * step;
                int end = (index + 1) * step;

                // Get from db
                objects = SynchronizationLocalServiceUtil.findMessage(VERSION.getDescription(), start, end);

                for (Object[] object : objects) {

                    // Get the message body
                    String body = (String) object[4];
                    // Trim the body
                    body = body.trim();

                    // Don't add empty messages
                    if (body.length() == 0) {
                        continue;
                    }

                    // Get the message id
                    Long messageId = (Long) object[0];

                    // Find message
                    Message message = MessageLocalServiceUtil.fetchBySyncIdChatPortlet(messageId, false);

                    // Message already exists there is no need to synchronize it again
                    if (message != null) {
                        continue;
                    }

                    // Find the conversation
                    Long fromUserId = (Long) object[1];
                    Long toUserId = (Long) object[2];

                    // Get the conversation id
                    String conversationId = createConversationId(fromUserId, toUserId);

                    // No conversation can be created
                    if (conversationId == null) {
                        continue;
                    }

                    // Check if there is already such conversation
                    Conversation conversation = ConversationLocalServiceUtil.fetchByConversationId(
                            conversationId, false
                    );

                    // We cannot add a message to a non-existing conversation
                    if (conversation == null) {
                        continue;
                    }

                    // Create new message and map values
                    message = MessageLocalServiceUtil.createMessage();
                    message.setCid(conversation.getCid());
                    message.setMessageType(MessageType.REGULAR.getCode()); // default
                    message.setCreatorId(fromUserId);
                    message.setCreatedAt(new Date((Long) object[3]));
                    message.setBody(body);
                    message.setSyncIdChatPortlet(messageId);

                    MessageLocalServiceUtil.saveMessage(message);
                }

                // Increase index
                index++;

            } while (objects.size() != 0); // Continue until there are no more objects
        }
        // System exception has to be thrown otherwise the transaction management wouldn't work
        catch (Exception exception) {
            throw new SystemException(exception);
        }
    }

    /**
     * Creates conversation id based on the users ids. Returns null if no conversation id can be created
     *
     * @param fromUserId from user
     * @param toUserId   to user
     * @return conversation id or null if no conversation id can be created
     */
    private String createConversationId(Long fromUserId, Long toUserId) {

        // Find user based on the user ids
        User fromUser;
        User toUser;

        try {
            // Find users based on their user ids
            fromUser = UserLocalServiceUtil.getUser(fromUserId);
            toUser = UserLocalServiceUtil.getUser(toUserId);
        } catch (Exception e) {
            return null; // No conversation id can be created
        }

        if (fromUser == null || toUser == null) {
            return null; // No users were found
        }

        // Get the user names
        String fromUserScreenName = fromUser.getScreenName();
        String toUserScreenName = toUser.getScreenName();

        if (fromUserScreenName == null || toUserScreenName == null) {
            return null; // Users doesn't have screen names
        }

        // Create the conversation id from the users
        int comparison = fromUserScreenName.compareToIgnoreCase(toUserScreenName);
        String conversationId;

        // Cannot have conversation with yourself
        if (comparison == 0) {
            return null;
        }
        // From user is lexicographically after To user
        else if (comparison < 0) {
            conversationId = String.format("%s_%s", fromUserScreenName, toUserScreenName);
        }
        // To user is lexicographically after To user
        else {
            conversationId = String.format("%s_%s", toUserScreenName, fromUserScreenName);
        }

        return conversationId;
    }
}
