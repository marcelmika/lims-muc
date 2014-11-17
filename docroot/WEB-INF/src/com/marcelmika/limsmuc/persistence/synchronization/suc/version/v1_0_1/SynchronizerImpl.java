/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization.suc.version.v1_0_1;

import com.liferay.portal.kernel.exception.SystemException;
import com.marcelmika.limsmuc.persistence.domain.ConversationType;
import com.marcelmika.limsmuc.persistence.domain.MessageType;
import com.marcelmika.limsmuc.persistence.generated.model.*;
import com.marcelmika.limsmuc.persistence.generated.service.*;
import com.marcelmika.limsmuc.persistence.synchronization.Synchronizer;
import com.marcelmika.limsmuc.persistence.synchronization.Version;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 15/11/14
 * Time: 16:57
 */
public class SynchronizerImpl implements Synchronizer {

    // Number of rows taken at once
    private static final int READ_TABLE_STEP_SIZE = 100;

    // Version type
    private static final Version VERSION = Version.SUC_1_0_1;

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
     * Sync SUC Settings table for SUC 1.0.1
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
                    Long userId = (Long) object[1];

                    // If the settings for the particular user are already there do nothing
                    Settings settings = SettingsLocalServiceUtil.fetchByUserId(userId, false);
                    if (settings != null) {
                        continue;
                    }

                    // Map suc settings object to muc settings object
                    settings = SettingsLocalServiceUtil.createSettings();
                    settings.setUserId((Long) object[1]);
                    settings.setPresence((String) object[2]);
                    settings.setPresenceUpdatedAt(new Date(0));
                    settings.setMute((Boolean) object[3]);
                    settings.setChatEnabled((Boolean) object[4]);
                    settings.setAdminAreaOpened(false); // default
                    // Presence needs to be set to online because of the different presence system in muc
                    if (settings.getPresence().equals("presence.off")) {
                        settings.setPresence("presence.online");
                    }
                    settings.setConnected(false);
                    settings.setConnectedAt(new Date(0));

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
     * Sync SUC Panel table for SUC 1.0.1
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

                for (Object[] object : objects) {
                    // Get the user ID
                    Long userId = (Long) object[1];

                    // If the panel row for the particular user is already there do nothing
                    Panel panel = PanelLocalServiceUtil.fetchByUserId(userId, false);
                    if (panel != null) {
                        continue;
                    }

                    // Map suc panel object to muc object
                    panel = PanelLocalServiceUtil.createPanel();
                    panel.setUserId((Long) object[1]);
                    panel.setActivePanelId((String) object[2]);
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
     * Sync SUC Conversation table for SUC 1.0.1
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

                // Get from db
                objects = SynchronizationLocalServiceUtil.findConversation(VERSION.getDescription(), start, end);

                for (Object[] object : objects) {

                    // Get the conversation ID
                    String conversationId = (String) object[1];

                    // Check if there is already such conversation
                    Conversation conversation = ConversationLocalServiceUtil.fetchByConversationId(
                            conversationId, false
                    );

                    // If the particular conversation is not in db yet
                    if (conversation == null) {
                        // Create a new one
                        conversation = ConversationLocalServiceUtil.createConversation();
                        conversation.setConversationId((String) object[1]);
                        conversation.setConversationType(ConversationType.SINGLE_USER.getCode()); // There are no MUC in SUC

                        Calendar updatedAt = (Calendar) object[3];
                        if (updatedAt != null) {
                            conversation.setUpdatedAt(updatedAt.getTime());
                        }
                    }

                    conversation.setSyncIdSUC((Long) object[0]);

                    // Save the conversation
                    ConversationLocalServiceUtil.saveConversation(conversation);
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
     * Sync SUC Participant table for SUC 1.0.1
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

                    // First, find the conversation
                    Long cid = (Long) object[1];
                    Long participantId = (Long) object[2];

                    // This will find the conversation that belongs to the participant
                    Conversation conversation = ConversationLocalServiceUtil.fetchBySyncIdSUC(cid, false);

                    // We cannot add a participant to a non-existing conversation
                    if (conversation == null) {
                        continue;
                    }

                    // Check if there is such participant already
                    Participant participant = ParticipantLocalServiceUtil.getParticipant(
                            conversation.getCid(), participantId, false
                    );

                    // The participant is not in the db yet
                    if (participant == null) {
                        // Create a new one
                        participant = ParticipantLocalServiceUtil.createParticipant();
                        participant.setCid(conversation.getCid());
                        participant.setParticipantId(participantId);
                        participant.setUnreadMessagesCount((Integer) object[3]);
                        participant.setIsOpened((Boolean) object[4]);
                        participant.setIsCreator(false); // default
                        participant.setHasLeft(false); // will never happen in SUC
                        participant.setOpenedAt(new Date(0));

                        // Save
                        ParticipantLocalServiceUtil.saveParticipant(participant);
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
     * Sync SUC Message table for SUC 1.0.1
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

                    // First, find the conversation
                    Long mid = (Long) object[0];
                    Long cid = (Long) object[1];

                    // Find the message
                    Message message = MessageLocalServiceUtil.fetchBySyncIdSUC(mid, false);

                    // Message already exists there is no need to synchronize it again
                    if (message != null) {
                        continue;
                    }

                    // Find the conversation related to the message
                    Conversation conversation = ConversationLocalServiceUtil.fetchBySyncIdSUC(cid, false);

                    // We cannot add a message to a non-existing conversation
                    if (conversation == null) {
                        continue;
                    }

                    // Create new message an map values
                    message = MessageLocalServiceUtil.createMessage();
                    message.setCid(conversation.getCid());
                    message.setMessageType(MessageType.REGULAR.getCode());
                    message.setCreatorId((Long) object[2]);

                    Calendar createdAt = (Calendar) object[3];
                    if (createdAt != null) {
                        message.setCreatedAt(createdAt.getTime());
                    }
                    message.setBody((String) object[4]);
                    message.setSyncIdSUC(mid);

                    // Save
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
}
