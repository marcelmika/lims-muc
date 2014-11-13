/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.marcelmika.lims.persistence.generated.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.persistence.domain.ConversationType;
import com.marcelmika.lims.persistence.domain.MessageType;
import com.marcelmika.lims.persistence.generated.model.*;
import com.marcelmika.lims.persistence.generated.service.base.SynchronizationLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the synchronization local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.SynchronizationLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.SynchronizationLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.SynchronizationLocalServiceUtil
 */
public class SynchronizationLocalServiceImpl
        extends SynchronizationLocalServiceBaseImpl {

	/*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.persistence.generated.service.SynchronizationLocalServiceUtil} to access the synchronization local service.
	 */

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SynchronizationLocalServiceImpl.class);

    // Number of rows taken at once
    private static final int READ_TABLE_STEP_SIZE = 100;

    /**
     * Synchronizes LIMS SUC v1.2.0
     *
     * @throws SystemException
     */
    @Override
    public void synchronizeSUC_1_2_0() throws SystemException {

        // Important note: The operations are not idempotent.
        // In other words, don't change the order of the operations below

        // Settings
        synchronizeSUCSettings_1_2_0();
        // Panel
        synchronizeSUCPanel_1_2_0();
        // Conversation
        synchronizeSUCConversation_1_2_0();
        // Participant
        synchronizeSUCParticipant_1_2_0();
        // Message
        synchronizeSUCMessage_1_2_0();
    }

    /**
     * Sync SUC Settings table for SUC 1.2.0
     *
     * @throws SystemException
     */
    private void synchronizeSUCSettings_1_2_0() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        do {
            // Find start and end
            int start = index * step;
            int end = (index + 1) * step;

            // Get from db
            objects = synchronizationFinder.findSUCSettings_1_2_0(start, end);

            for (Object[] object : objects) {

                // Get the user ID
                Long userId = (Long) object[1];

                // If the settings for the particular user are already there do nothing
                Settings settings = settingsPersistence.fetchByUserId(userId);
                if (settings != null) {
                    continue;
                }

                // Map suc settings object to muc settings object
                settings = settingsPersistence.create(counterLocalService.increment());
                settings.setUserId((Long) object[1]);
                settings.setPresence((String) object[2]);
                settings.setPresenceUpdatedAt(new Date((Long) object[3]));
                settings.setMute((Boolean) object[4]);
                settings.setChatEnabled((Boolean) object[5]);
                settings.setAdminAreaOpened((Boolean) object[6]);
                // Presence needs to be set to online because of the different presence system in muc
                if (settings.getPresence().equals("presence.off")) {
                    settings.setPresence("presence.online");
                }
                settings.setConnected(false);
                settings.setConnectedAt(new Date(0));
                // Save
                settingsPersistence.update(settings, false);
            }

            // Increase index
            index++;

        } while (objects.size() != 0); // Continue until there are no more objects
    }

    /**
     * Sync SUC Panel table for SUC 1.2.0
     *
     * @throws SystemException
     */
    private void synchronizeSUCPanel_1_2_0() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        do {
            // Find start and end
            int start = index * step;
            int end = (index + 1) * step;

            // Get from db
            objects = synchronizationFinder.findSUCPanel_1_2_0(start, end);

            for (Object[] object : objects) {
                // Get the user ID
                Long userId = (Long) object[1];

                // If the panel row for the particular user is already there do nothing
                Panel panel = panelPersistence.fetchByUserId(userId);
                if (panel != null) {
                    continue;
                }

                // Map suc panel object to muc object
                panel = panelPersistence.create(counterLocalService.increment());
                panel.setUserId((Long) object[1]);
                panel.setActivePanelId((String) object[2]);
                // Save
                panelPersistence.update(panel, false);
            }

            // Increase index
            index++;

        } while (objects.size() != 0); // Continue until there are no more objects
    }

    /**
     * Sync SUC Conversation table for SUC 1.2.0
     *
     * @throws SystemException
     */
    private void synchronizeSUCConversation_1_2_0() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        do {

            // Find start and end
            int start = index * step;
            int end = (index + 1) * step;

            // Get from db
            objects = synchronizationFinder.findSUCConversation_1_2_0(start, end);

            for (Object[] object : objects) {

                // Get the conversation ID
                String conversationId = (String) object[1];

                // Check if there is already such conversation
                Conversation conversation = conversationPersistence.fetchByConversationId(conversationId);

                // If the particular conversation is not in db yet
                if (conversation == null) {
                    // Create a new one
                    conversation = conversationPersistence.create(counterLocalService.increment());
                    conversation.setConversationId((String) object[1]);
                    conversation.setConversationType(ConversationType.SINGLE_USER.getCode()); // There are no MUC in SUC

                    Calendar updatedAt = (Calendar) object[3];
                    if (updatedAt != null) {
                        conversation.setUpdatedAt(updatedAt.getTime());
                    }
                }

                conversation.setSyncIdSUC((Long) object[0]);

                // Save the conversation
                conversationPersistence.update(conversation, false);
            }

            // Increase index
            index++;

        } while (objects.size() != 0); // Continue until there are no more objects
    }

    /**
     * Sync SUC Participant table for SUC 1.2.0
     *
     * @throws SystemException
     */
    private void synchronizeSUCParticipant_1_2_0() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        do {

            // Find start and end
            int start = index * step;
            int end = (index + 1) * step;

            // Get from db
            objects = synchronizationFinder.findSUCParticipant_1_2_0(start, end);

            for (Object[] object : objects) {

                // First, find the conversation
                Long cid = (Long) object[1];
                Long participantId = (Long) object[2];

                // This will find the conversation that belongs to the participant
                Conversation conversation = conversationPersistence.fetchBySyncIdSUC(cid);

                // We cannot add a participant to a non-existing conversation
                if (conversation == null) {
                    continue;
                }

                // Check if there is such participant already
                Participant participant = participantPersistence.fetchByCidParticipantId(
                        conversation.getCid(), participantId
                );

                // The participant is not in the db yet
                if (participant == null) {
                    // Create a new one
                    participant = participantPersistence.create(counterLocalService.increment());
                    participant.setCid(conversation.getCid());
                    participant.setParticipantId(participantId);
                    participant.setUnreadMessagesCount((Integer) object[3]);
                    participant.setIsOpened((Boolean) object[4]);
                    participant.setIsCreator(false); // default
                    participant.setHasLeft(false); // will never happen in SUC
                    participant.setOpenedAt(new Date((Long) object[5]));

                    participantPersistence.update(participant, false);
                }
            }

            // Increase index
            index++;

        } while (objects.size() != 0); // Continue until there are no more objects
    }

    /**
     * Sync SUC Message table for SUC 1.2.0
     *
     * @throws SystemException
     */
    private void synchronizeSUCMessage_1_2_0() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = READ_TABLE_STEP_SIZE;

        do {

            // Find start and end
            int start = index * step;
            int end = (index + 1) * step;

            // Get from db
            objects = synchronizationFinder.findSUCMessage_1_2_0(start, end);

            for (Object[] object : objects) {

                // First, find the conversation
                Long mid = (Long) object[0];
                Long cid = (Long) object[1];

                // Find the message
                Message message = messagePersistence.fetchBySyncIdSUC(mid);

                // Message already exists there is no need to synchronize it again
                if (message != null) {
                    continue;
                }

                // Find the conversation related to the message
                Conversation conversation = conversationPersistence.fetchBySyncIdSUC(cid);

                // We cannot add a message to a non-existing conversation
                if (conversation == null) {
                    continue;
                }

                // Create new message an map values
                message = messagePersistence.create(counterLocalService.increment());
                message.setCid(conversation.getCid());
                message.setMessageType(MessageType.REGULAR.getCode());
                message.setCreatorId((Long) object[2]);

                Calendar createdAt = (Calendar) object[3];
                if (createdAt != null) {
                    message.setCreatedAt(createdAt.getTime());
                }
                message.setBody((String) object[4]);
                message.setSyncIdSUC(mid);

                messagePersistence.update(message, false);
            }

            // Increase index
            index++;

        } while (objects.size() != 0); // Continue until there are no more objects
    }
}