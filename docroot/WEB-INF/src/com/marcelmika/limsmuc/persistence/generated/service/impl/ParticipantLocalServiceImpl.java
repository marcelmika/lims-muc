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

package com.marcelmika.limsmuc.persistence.generated.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.persistence.generated.model.Participant;
import com.marcelmika.limsmuc.persistence.generated.model.impl.ParticipantImpl;
import com.marcelmika.limsmuc.persistence.generated.service.PanelLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.generated.service.base.ParticipantLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the participant local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.limsmuc.persistence.generated.service.ParticipantLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.ParticipantLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.ParticipantLocalServiceUtil
 */
public class ParticipantLocalServiceImpl extends ParticipantLocalServiceBaseImpl {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ParticipantLocalServiceImpl.class);

	/*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.limsmuc.persistence.generated.service.ParticipantLocalServiceUtil} to access the participant local service.
	 */

    /**
     * Adds new participant to the system
     *
     * @param cid           Id of the conversation to which the participant belongs to
     * @param participantId User Id of the participant
     * @return Participant Model
     * @throws SystemException
     */
    @Override
    public Participant addParticipant(Long cid, Long participantId) throws SystemException {
        return addParticipant(cid, participantId, false);
    }

    /**
     * Given method updates all participants related to the conversation. By updated we mean incrementing of the
     * unread message count if needed and opening the conversation if needed
     *
     * @param cid Id of the conversation related to the participants
     * @throws SystemException
     * @throws PortalException
     */
    @Override
    public void updateParticipants(Long cid, Long senderId) throws SystemException, PortalException {
        // Fetch all participants by the conversation id
        List<Participant> participantList = participantPersistence.findByCid(cid);

        for (Participant participant : participantList) {

            // We don't want to increase a count of unread messages of the user who
            // actually sent the message
            if (participant.getParticipantId() == senderId) {
                continue;
            }

            // Update message count only if user's currently opened panel is different then the one with conversation.
            // We don't want to increment unread message count for the conversation which is currently presented to
            // the user
            int unreadMessageCount = participant.getUnreadMessagesCount();
            participant.setUnreadMessagesCount(++unreadMessageCount);

            // Only if the conversation isn't opened for the participant already
            if (!participant.getIsOpened()) {
                // Open the conversation. In other words if the user closed the conversation open it again for him.
                // By opening we doesn't mean opening the panel. The panel may be minimized but the conversation is still
                // opened.
                participant.setIsOpened(true);
                // Set the time when the conversation was opened
                participant.setOpenedAt(Calendar.getInstance().getTime());
            }

            // Save the participant
            participantPersistence.update(participant, false);
        }
    }

    /**
     * Closes conversation for the particular participant id by setting isOpened flag to false.
     *
     * @param conversationId Conversation which should be closed
     * @param participantId  Participant whose conversation should be closed
     * @throws com.marcelmika.limsmuc.persistence.generated.NoSuchConversationException
     * @throws SystemException
     * @throws com.marcelmika.limsmuc.persistence.generated.NoSuchParticipantException
     */
    @Override
    public void closeConversation(String conversationId, Long participantId)
            throws com.marcelmika.limsmuc.persistence.generated.NoSuchConversationException, SystemException, com.marcelmika.limsmuc.persistence.generated.NoSuchParticipantException {

        // Find conversation
        com.marcelmika.limsmuc.persistence.generated.model.Conversation conversation = conversationPersistence.findByConversationId(conversationId);

        // Find participant
        Participant participant = participantPersistence.findByCidParticipantId(conversation.getCid(), participantId);

        // Close conversation
        participant.setIsOpened(false);

        // Since the panel was closed no active panel is currently there
        com.marcelmika.limsmuc.persistence.generated.model.Panel panel = PanelLocalServiceUtil.getPanelByUser(participant.getParticipantId());
        panel.setActivePanelId("");
        panelPersistence.update(panel, false);

        // Save
        participantPersistence.update(participant, false);
    }

    /**
     * Resets counter of unread messages for the user who participates in the given conversation
     *
     * @param conversationId Conversation where the counter should be reset
     * @param participantId  Participant whose counter should be reset
     * @throws com.marcelmika.limsmuc.persistence.generated.NoSuchParticipantException
     * @throws SystemException
     * @throws com.marcelmika.limsmuc.persistence.generated.NoSuchConversationException
     */
    @Override
    public void resetUnreadMessagesCounter(String conversationId, Long participantId)
            throws com.marcelmika.limsmuc.persistence.generated.NoSuchParticipantException, SystemException, com.marcelmika.limsmuc.persistence.generated.NoSuchConversationException {

        // Find conversation
        com.marcelmika.limsmuc.persistence.generated.model.Conversation conversation = conversationPersistence.findByConversationId(conversationId);

        // Find participant
        Participant participant = participantPersistence.findByCidParticipantId(conversation.getCid(), participantId);

        // Set counter to zero
        participant.setUnreadMessagesCount(0);

        // Save
        participantPersistence.update(participant, false);
    }

    /**
     * Returns a list of opened conversations where the the user participates
     *
     * @param participantId User Id of the participant
     * @return List of opened conversations
     * @throws SystemException
     */
    @Override
    public List<Participant> getOpenedConversations(Long participantId) throws SystemException {
        return participantPersistence.findByParticipantIdIsOpened(participantId, true);
    }

    /**
     * Switches an order of two conversations by switching their openedAt timestamps
     *
     * @param firstConversationParticipant  Participant
     * @param secondConversationParticipant Participant
     * @throws SystemException
     */
    @Override
    public void switchConversations(Participant firstConversationParticipant,
                                    Participant secondConversationParticipant) throws SystemException {

        // Take dates
        Date firstOpenedAt = firstConversationParticipant.getOpenedAt();
        Date secondOpenedAt = secondConversationParticipant.getOpenedAt();

        // Switch them
        firstConversationParticipant.setOpenedAt(secondOpenedAt);
        secondConversationParticipant.setOpenedAt(firstOpenedAt);

        // Save it
        participantPersistence.update(firstConversationParticipant, false);
        participantPersistence.update(secondConversationParticipant, false);
    }

    /**
     * Returns a list of users who participates in conversation
     *
     * @param cid Id of the conversation related to the participants
     * @return list of participants
     * @throws com.marcelmika.limsmuc.persistence.generated.NoSuchParticipantException
     * @throws SystemException
     */
    @Override
    public List<Participant> getConversationParticipants(Long cid) throws com.marcelmika.limsmuc.persistence.generated.NoSuchParticipantException, SystemException {
        return participantPersistence.findByCid(cid);
    }

    /**
     * Returns particular participant based on the id
     *
     * @param participantId Id of the participant
     * @return participant or null if no participant was found
     * @throws SystemException
     */
    @Override
    public Participant getParticipant(Long cid, Long participantId) throws SystemException {
        return participantPersistence.fetchByCidParticipantId(cid, participantId);
    }

    /**
     * Returns particular participant based on the id
     *
     * @param participantId Id of the participant
     * @param useCache true if the cache should be used
     * @return participant or null if no participant was found
     * @throws SystemException
     */
    @Override
    public Participant getParticipant(Long cid, Long participantId, boolean useCache) throws SystemException {
        return participantPersistence.fetchByCidParticipantId(cid, participantId, useCache);
    }

    /**
     * Returns a list of conversations where the user participates.
     * Conversations that contain no messages are not included in the list.
     *
     * @param participantId User Id of the participant
     * @return List of conversation
     * @throws SystemException
     */
    @Override
    public List<Participant> getConversations(Long participantId,
                                              Integer defaultPageSize,
                                              Integer currentPageSize,
                                              Integer maxPageSize,
                                              Boolean readMore) throws SystemException {

        // This is a first request, no current page size is set
        if (currentPageSize == null || currentPageSize < defaultPageSize) {
            // Set it to default page size
            currentPageSize = defaultPageSize;
        }

        // User wants to read history
        if (readMore != null && readMore) {
            // Increase the size of page by a default page size
            currentPageSize += defaultPageSize;

            // We can be over the actual maximal size of the page, so just stop here
            if (currentPageSize > maxPageSize) {
                currentPageSize = maxPageSize;
            }
        }

        // Get the participant objects from database
        List<Object[]> participantObjects = participantFinder.findParticipatedConversations(
                participantId, 0, currentPageSize
        );

        // Map to participants and return
        return ParticipantImpl.fromPlainObjectList(participantObjects, 0);
    }

    /**
     * Returns a number of all conversations where the user participates
     *
     * @param participantId Long
     * @return summed number of conversations where the user participates
     * @throws com.liferay.portal.kernel.exception.SystemException
     */
    @Override
    public Integer getConversationsCount(Long participantId) throws SystemException {
        return participantFinder.countParticipatedConversations(participantId);
    }

    /**
     * Leaves the conversation for the given participant
     *
     * @param cid           id of the conversation
     * @param participantId id of the participant
     * @throws SystemException
     */
    @Override
    public void leaveConversation(Long cid, Long participantId) throws SystemException {
        // Try to find participant
        Participant participantModel = participantPersistence.fetchByCidParticipantId(cid, participantId);

        if (participantModel != null) {

            // Set the has left flag to true
            participantModel.setHasLeft(true);

            // Save
            participantPersistence.update(participantModel, false);
        }
    }

    /**
     * Adds new participant to the system
     *
     * @param cid           Id of the conversation to which the participant belongs to
     * @param participantId User Id of the participant
     * @param isCreator     true if the user is a creator of hte conversation
     * @return Participant Model
     * @throws com.liferay.portal.kernel.exception.SystemException
     */
    @Override
    public Participant addParticipant(Long cid, Long participantId, boolean isCreator) throws SystemException {

        // Fetch possible existing conversation
        Participant participantModel = participantPersistence.fetchByCidParticipantId(cid, participantId);

        // Create a new one
        if (participantModel == null) {
            // No participant was found, so create a new one
            participantModel = participantPersistence.create(counterLocalService.increment());
            participantModel.setCid(cid);
            participantModel.setParticipantId(participantId);
        }

        // Only if the conversation isn't opened for the participant already
        if (!participantModel.getIsOpened()) {
            // Set the time when the conversation was opened
            participantModel.setOpenedAt(Calendar.getInstance().getTime());
            // Open conversation for participant
            participantModel.setIsOpened(true);
        }

        // Since we are adding the user again, even though he might left we will ignore that
        participantModel.setHasLeft(false);
        // The counter needs to be reset otherwise the user might get unread message count
        // during the time he wasn't in the conversation and that's something we don't want.
        participantModel.setUnreadMessagesCount(0);
        // If the participant was yet set as a creator set the creator flag. However, if the
        // user was once set as a creator the fact cannot be changed.
        if (!participantModel.getIsCreator()) {
            participantModel.setIsCreator(isCreator);
        }

        // Save
        participantPersistence.update(participantModel, false);

        return participantModel;
    }

    /**
     * Create new plain participant object
     *
     * @return created participant
     * @throws SystemException
     */
    @Override
    public Participant createParticipant() throws SystemException {
        return participantPersistence.create(counterLocalService.increment());
    }

    /**
     * Saves participant
     *
     * @param participant Participant
     * @throws SystemException
     */
    @Override
    public void saveParticipant(Participant participant) throws SystemException {
        participantPersistence.update(participant, false);
    }
}