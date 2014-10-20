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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.persistence.generated.model.Message;
import com.marcelmika.lims.persistence.generated.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.base.MessageLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the message local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.MessageLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.MessageLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.MessageLocalServiceUtil
 */
public class MessageLocalServiceImpl extends MessageLocalServiceBaseImpl {

    // Log
    private static Log log = LogFactoryUtil.getLog(MessageLocalServiceImpl.class);

	/*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.persistence.generated.service.MessageLocalServiceUtil} to access the message local service.
	 */

    public Message addMessage(long cid, long creatorId, String body, Date createdAt) throws Exception {
        // Fetch possible existing conversation
        Message messageModel = messagePersistence.create(counterLocalService.increment());

        // Map properties
        messageModel.setCid(cid);
        messageModel.setCreatorId(creatorId);
        messageModel.setBody(body);
        messageModel.setCreatedAt(createdAt.getTime());

        // Update model
        messageModel = messagePersistence.update(messageModel, false);

        // Update conversation timestamp
        ConversationLocalServiceUtil.updateConversationTimestamp(cid);

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
     * @throws Exception
     */
    public List<Object[]> readMessages(Long cid,
                                       Integer pageSize,
                                       Long stopperId,
                                       Boolean readMore) throws Exception {

        // The beginning of the list. No stopper yet. Return message list that
        // starts from the beginning and has a default page size.
        if (stopperId == null) {
            // Find via message finder
            return messageFinder.findAllMessages(cid, pageSize);
        }

        int extendedPageSize;
        // Get the size of the possible list
        Integer messagesCount = messageFinder.countAllMessages(cid, stopperId);

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
     * @throws Exception
     */
    public Object[] firstMessage(Long cid) throws Exception {
        return messageFinder.firstMessage(cid);
    }

    /**
     * Returns the last message in the conversation
     *
     * @param cid id of the conversation related to the messages
     * @return last message in the conversation
     * @throws Exception
     */
    public Object[] lastMessage(Long cid) throws Exception {
        return messageFinder.lastMessage(cid);
    }
}