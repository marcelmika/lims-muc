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
import com.marcelmika.lims.persistence.generated.model.Message;
import com.marcelmika.lims.persistence.generated.service.ConversationLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.base.MessageLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * The implementation of the message local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.MessageLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.MessageLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.MessageLocalServiceUtil
 */
public class MessageLocalServiceImpl extends MessageLocalServiceBaseImpl {

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
        messageModel.setCreatedAt(createdAt);

        // Update model
        messageModel = messagePersistence.update(messageModel, false);

        // Update conversation timestamp
        ConversationLocalServiceUtil.updateConversationTimestamp(cid);

        return messageModel;
    }

    public List<Message> readMessages(long cid, int start, int end) throws SystemException {

        // Get the total count of messages
        int count = messagePersistence.countByCid(cid);
        // Count the beginning
        int begin = count - end;
        // Beginning cannot be less than zero
        if (begin < 0) {
            begin = 0;
        }
        // Find messages related to the conversation
        return messagePersistence.findByCid(cid, begin, count);
    }
}