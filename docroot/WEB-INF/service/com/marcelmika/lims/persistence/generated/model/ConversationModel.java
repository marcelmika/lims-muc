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

package com.marcelmika.lims.persistence.generated.model;

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the Conversation service. Represents a row in the &quot;Limsmuc_Conversation&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.marcelmika.lims.persistence.generated.model.impl.ConversationModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.marcelmika.lims.persistence.generated.model.impl.ConversationImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Conversation
 * @see com.marcelmika.lims.persistence.generated.model.impl.ConversationImpl
 * @see com.marcelmika.lims.persistence.generated.model.impl.ConversationModelImpl
 * @generated
 */
public interface ConversationModel extends BaseModel<Conversation> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a conversation model instance should use the {@link Conversation} interface instead.
	 */

	/**
	 * Returns the primary key of this conversation.
	 *
	 * @return the primary key of this conversation
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this conversation.
	 *
	 * @param primaryKey the primary key of this conversation
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the cid of this conversation.
	 *
	 * @return the cid of this conversation
	 */
	public long getCid();

	/**
	 * Sets the cid of this conversation.
	 *
	 * @param cid the cid of this conversation
	 */
	public void setCid(long cid);

	/**
	 * Returns the conversation ID of this conversation.
	 *
	 * @return the conversation ID of this conversation
	 */
	@AutoEscape
	public String getConversationId();

	/**
	 * Sets the conversation ID of this conversation.
	 *
	 * @param conversationId the conversation ID of this conversation
	 */
	public void setConversationId(String conversationId);

	/**
	 * Returns the conversation type of this conversation.
	 *
	 * @return the conversation type of this conversation
	 */
	@AutoEscape
	public String getConversationType();

	/**
	 * Sets the conversation type of this conversation.
	 *
	 * @param conversationType the conversation type of this conversation
	 */
	public void setConversationType(String conversationType);

	/**
	 * Returns the updated at of this conversation.
	 *
	 * @return the updated at of this conversation
	 */
	public Date getUpdatedAt();

	/**
	 * Sets the updated at of this conversation.
	 *
	 * @param updatedAt the updated at of this conversation
	 */
	public void setUpdatedAt(Date updatedAt);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(Conversation conversation);

	@Override
	public int hashCode();

	@Override
	public CacheModel<Conversation> toCacheModel();

	@Override
	public Conversation toEscapedModel();

	@Override
	public Conversation toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}