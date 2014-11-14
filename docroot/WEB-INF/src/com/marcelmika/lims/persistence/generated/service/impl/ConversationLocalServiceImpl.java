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
import com.marcelmika.lims.persistence.generated.NoSuchConversationException;
import com.marcelmika.lims.persistence.generated.model.Conversation;
import com.marcelmika.lims.persistence.generated.service.base.ConversationLocalServiceBaseImpl;

import java.util.Calendar;

/**
 * The implementation of the conversation local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.ConversationLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.ConversationLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.ConversationLocalServiceUtil
 */
public class ConversationLocalServiceImpl
	extends ConversationLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.persistence.generated.service.ConversationLocalServiceUtil} to access the conversation local service.
	 */

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ConversationLocalServiceImpl.class);

    /**
     * Adds a new conversation to the system. If such conversation is already there does nothing and returns it.
     *
     * @param conversationId of the new conversation
     * @param conversationTypeCode of the new conversation
     * @return created conversation
     * @throws SystemException
     */
    @Override
    public Conversation addConversation(String conversationId, int conversationTypeCode) throws SystemException {
        // Fetch possible existing conversation
        Conversation conversationModel = conversationPersistence.fetchByConversationId(conversationId);

        if (conversationModel == null) {
            conversationModel = conversationPersistence.create(counterLocalService.increment());
            conversationModel.setConversationId(conversationId);
            conversationModel.setConversationType(conversationTypeCode);

            // Update calendar time
            Calendar calendar = Calendar.getInstance();
            conversationModel.setUpdatedAt(calendar.getTime());

            // Update model
            conversationModel = conversationPersistence.update(conversationModel, false);
        }

        return conversationModel;
    }

    /**
     * Returns conversation based on the cid
     *
     * @param cid of the conversation
     * @return found conversation or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Conversation fetchByCid(long cid) throws SystemException {
        return conversationPersistence.fetchByPrimaryKey(cid);
    }

    /**
     * Returns conversation based on the conversation id
     *
     * @param conversationId of the conversation
     * @return found conversation or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Conversation fetchByConversationId(String conversationId) throws SystemException {
        return conversationPersistence.fetchByConversationId(conversationId);
    }

    /**
     * Returns conversation based on the sync id SUC
     *
     * @param syncIdSUC of the conversation
     * @return found conversation or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Conversation fetchBySyncIdSUC(long syncIdSUC) throws SystemException {
        return conversationPersistence.fetchBySyncIdSUC(syncIdSUC);
    }

    /**
     * Create new conversation
     *
     * @return created conversation
     * @throws SystemException
     */
    @Override
    public Conversation createConversation() throws SystemException {
        return conversationPersistence.create(counterLocalService.increment());
    }

    /**
     * Saves conversation to database
     *
     * @param conversation to be saved
     * @throws SystemException
     */
    @Override
    public void saveConversation(Conversation conversation) throws SystemException {
        conversationPersistence.update(conversation, false);
    }

    /**
     * Sets the conversation updated at timestamp to the current timestamp
     *
     * @param cid of the conversation
     * @throws SystemException
     */
    @Override
    public void updateConversationTimestamp(long cid) throws SystemException {

        // Find conversation
        Conversation conversation = conversationPersistence.fetchByPrimaryKey(cid);

        // Do nothing if no conversation was found
        if (conversation != null) {
            // Update to current time
            Calendar calendar = Calendar.getInstance();
            conversation.setUpdatedAt(calendar.getTime());

            // Save
            conversationPersistence.update(conversation, false);
        }
    }
}