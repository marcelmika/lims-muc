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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Message}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Message
 * @generated
 */
public class MessageWrapper implements Message, ModelWrapper<Message> {
	public MessageWrapper(Message message) {
		_message = message;
	}

	@Override
	public Class<?> getModelClass() {
		return Message.class;
	}

	@Override
	public String getModelClassName() {
		return Message.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mid", getMid());
		attributes.put("cid", getCid());
		attributes.put("creatorId", getCreatorId());
		attributes.put("createdAt", getCreatedAt());
		attributes.put("body", getBody());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mid = (Long)attributes.get("mid");

		if (mid != null) {
			setMid(mid);
		}

		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		Long creatorId = (Long)attributes.get("creatorId");

		if (creatorId != null) {
			setCreatorId(creatorId);
		}

		Date createdAt = (Date)attributes.get("createdAt");

		if (createdAt != null) {
			setCreatedAt(createdAt);
		}

		String body = (String)attributes.get("body");

		if (body != null) {
			setBody(body);
		}
	}

	/**
	* Returns the primary key of this message.
	*
	* @return the primary key of this message
	*/
	@Override
	public long getPrimaryKey() {
		return _message.getPrimaryKey();
	}

	/**
	* Sets the primary key of this message.
	*
	* @param primaryKey the primary key of this message
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_message.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the mid of this message.
	*
	* @return the mid of this message
	*/
	@Override
	public long getMid() {
		return _message.getMid();
	}

	/**
	* Sets the mid of this message.
	*
	* @param mid the mid of this message
	*/
	@Override
	public void setMid(long mid) {
		_message.setMid(mid);
	}

	/**
	* Returns the cid of this message.
	*
	* @return the cid of this message
	*/
	@Override
	public long getCid() {
		return _message.getCid();
	}

	/**
	* Sets the cid of this message.
	*
	* @param cid the cid of this message
	*/
	@Override
	public void setCid(long cid) {
		_message.setCid(cid);
	}

	/**
	* Returns the creator ID of this message.
	*
	* @return the creator ID of this message
	*/
	@Override
	public long getCreatorId() {
		return _message.getCreatorId();
	}

	/**
	* Sets the creator ID of this message.
	*
	* @param creatorId the creator ID of this message
	*/
	@Override
	public void setCreatorId(long creatorId) {
		_message.setCreatorId(creatorId);
	}

	/**
	* Returns the created at of this message.
	*
	* @return the created at of this message
	*/
	@Override
	public java.util.Date getCreatedAt() {
		return _message.getCreatedAt();
	}

	/**
	* Sets the created at of this message.
	*
	* @param createdAt the created at of this message
	*/
	@Override
	public void setCreatedAt(java.util.Date createdAt) {
		_message.setCreatedAt(createdAt);
	}

	/**
	* Returns the body of this message.
	*
	* @return the body of this message
	*/
	@Override
	public java.lang.String getBody() {
		return _message.getBody();
	}

	/**
	* Sets the body of this message.
	*
	* @param body the body of this message
	*/
	@Override
	public void setBody(java.lang.String body) {
		_message.setBody(body);
	}

	@Override
	public boolean isNew() {
		return _message.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_message.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _message.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_message.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _message.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _message.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_message.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _message.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_message.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_message.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_message.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new MessageWrapper((Message)_message.clone());
	}

	@Override
	public int compareTo(
		com.marcelmika.lims.persistence.generated.model.Message message) {
		return _message.compareTo(message);
	}

	@Override
	public int hashCode() {
		return _message.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.marcelmika.lims.persistence.generated.model.Message> toCacheModel() {
		return _message.toCacheModel();
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Message toEscapedModel() {
		return new MessageWrapper(_message.toEscapedModel());
	}

	@Override
	public com.marcelmika.lims.persistence.generated.model.Message toUnescapedModel() {
		return new MessageWrapper(_message.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _message.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _message.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_message.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MessageWrapper)) {
			return false;
		}

		MessageWrapper messageWrapper = (MessageWrapper)obj;

		if (Validator.equals(_message, messageWrapper._message)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Message getWrappedMessage() {
		return _message;
	}

	@Override
	public Message getWrappedModel() {
		return _message;
	}

	@Override
	public void resetOriginalValues() {
		_message.resetOriginalValues();
	}

	private Message _message;
}