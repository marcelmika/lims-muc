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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.persistence.generated.model.Message;
import com.marcelmika.limsmuc.persistence.generated.service.base.MessageLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the message local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.limsmuc.persistence.generated.service.MessageLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.MessageLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.MessageLocalServiceUtil
 */
public class MessageLocalServiceImpl extends MessageLocalServiceBaseImpl {


	/*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.limsmuc.persistence.generated.service.MessageLocalServiceUtil} to access the message local service.
	 */

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(MessageLocalServiceImpl.class);

    /**
     * Adds a message to the persistence
     *
     * @param cid         conversation id
     * @param creatorId   user id of the creator of the message
     * @param messageType type of the message code
     * @param body        text of the message
     * @param createdAt   timestamp of creation
     * @return newly created message
     * @throws SystemException
     */
    @Override
    public Message addMessage(long cid,
                              long creatorId,
                              int messageType,
                              String body,
                              Date createdAt) throws SystemException {

        // Create new conversation object
        Message messageModel = messagePersistence.create(counterLocalService.increment());

        // Map properties
        messageModel.setCid(cid);
        messageModel.setCreatorId(creatorId);
        messageModel.setMessageType(messageType);
        messageModel.setBody(body);
        messageModel.setCreatedAt(createdAt);

        // Update model
        messageModel = messagePersistence.update(messageModel, false);

        return messageModel;
    }

    /**
     * Returns a list of messages related to the conversation
     *
     * @param cid       id of the conversation
     * @param pageSize  size of the list
     * @param stopperId id of the stopper messages
     * @param readMore  true if the list should be extended
     * @return a list of messages
     * @throws SystemException
     */
    @Override
    public List<Object[]> readMessages(Long cid,
                                       Integer pageSize,
                                       Long stopperId,
                                       Boolean readMore) throws SystemException {

        // The beginning of the list. No stopper yet. Return message list that
        // starts from the beginning and has a default page size.
        if (stopperId == null) {
            // Find via message finder
            return messageFinder.findAllMessages(cid, pageSize);
        }

        // Get the message related to the stopperId
        Message stopper = messagePersistence.fetchByPrimaryKey(stopperId);

        // Message shouldn't be null. However, if it is show at least some messages
        if (stopper == null) {
            // Find via message finder
            return messageFinder.findAllMessages(cid, pageSize);
        }

        int extendedPageSize;
        // Get the size of the possible list
        Integer messagesCount = messageFinder.countAllMessages(cid, stopper);

        // User is at the middle of the list and has requested more messages. Extend the
        // message list beyond the stopper message.
        if (readMore) {
            // We need to extend the size of the returned list since the client
            // provided the stopper id and want to read more. We will simply take
            // the size of the current list and increase it by another page.
            extendedPageSize = messagesCount + pageSize;
        }
        // User didn't request more messages. However the page size needs to remain.
        else {
            extendedPageSize = messagesCount;
        }

        // Return found message
        return messageFinder.findAllMessages(cid, extendedPageSize);
    }

    /**
     * Returns the first message in the conversation
     *
     * @param cid id of the conversation related to the messages
     * @return first message in the conversation
     * @throws SystemException
     */
    @Override
    public Object[] firstMessage(Long cid) throws SystemException {
        return messageFinder.firstMessage(cid);
    }

    /**
     * Returns the last message in the conversation
     *
     * @param cid id of the conversation related to the messages
     * @return last message in the conversation
     * @throws SystemException
     */
    @Override
    public Object[] lastMessage(Long cid) throws SystemException {
        return messageFinder.lastMessage(cid);
    }

    /**
     * Counts number of messages for the given conversation
     *
     * @param cid id of the conversation
     * @return number of messages
     */
    @Override
    public Integer countMessages(Long cid) throws SystemException {
        return messagePersistence.countByCid(cid);
    }

    /**
     * Returns conversation based on its sync id SUC
     *
     * @param syncIdSUC of the conversation
     * @return found message or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Message fetchBySyncIdSUC(long syncIdSUC) throws SystemException {
        return messagePersistence.fetchBySyncIdSUC(syncIdSUC);
    }

    /**
     * Returns conversation based on its sync id SUC
     *
     * @param syncIdSUC of the conversation
     * @param useCache true if the cache should be used
     * @return found message or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Message fetchBySyncIdSUC(long syncIdSUC, boolean useCache) throws SystemException {
        return messagePersistence.fetchBySyncIdSUC(syncIdSUC, useCache);
    }

    /**
     * Returns conversation based on its sync id Chat Portlet
     *
     * @param syncIdChatPortlet of the conversation
     * @param useCache true if the cache should be used
     * @return found message or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Message fetchBySyncIdChatPortlet(long syncIdChatPortlet, boolean useCache) throws SystemException {
        return messagePersistence.fetchBySyncIdChatPortlet(syncIdChatPortlet, useCache);
    }

    /**
     * Creates new plain message
     *
     * @return created message
     * @throws SystemException
     */
    @Override
    public Message createMessage() throws SystemException {
        return messagePersistence.create(counterLocalService.increment());
    }

    /**
     * Save message to database
     *
     * @param message Message
     * @throws SystemException
     */
    @Override
    public void saveMessage(Message message) throws SystemException {
        messagePersistence.update(message, false);
    }
}