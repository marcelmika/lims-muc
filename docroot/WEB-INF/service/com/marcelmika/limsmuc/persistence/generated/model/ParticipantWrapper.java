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

package com.marcelmika.limsmuc.persistence.generated.model;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Participant}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Participant
 * @generated
 */
public class ParticipantWrapper implements Participant,
	ModelWrapper<Participant> {
	public ParticipantWrapper(Participant participant) {
		_participant = participant;
	}

	@Override
	public Class<?> getModelClass() {
		return Participant.class;
	}

	@Override
	public String getModelClassName() {
		return Participant.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("pid", getPid());
		attributes.put("cid", getCid());
		attributes.put("participantId", getParticipantId());
		attributes.put("unreadMessagesCount", getUnreadMessagesCount());
		attributes.put("isOpened", getIsOpened());
		attributes.put("isCreator", getIsCreator());
		attributes.put("hasLeft", getHasLeft());
		attributes.put("openedAt", getOpenedAt());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long pid = (Long)attributes.get("pid");

		if (pid != null) {
			setPid(pid);
		}

		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		Long participantId = (Long)attributes.get("participantId");

		if (participantId != null) {
			setParticipantId(participantId);
		}

		Integer unreadMessagesCount = (Integer)attributes.get(
				"unreadMessagesCount");

		if (unreadMessagesCount != null) {
			setUnreadMessagesCount(unreadMessagesCount);
		}

		Boolean isOpened = (Boolean)attributes.get("isOpened");

		if (isOpened != null) {
			setIsOpened(isOpened);
		}

		Boolean isCreator = (Boolean)attributes.get("isCreator");

		if (isCreator != null) {
			setIsCreator(isCreator);
		}

		Boolean hasLeft = (Boolean)attributes.get("hasLeft");

		if (hasLeft != null) {
			setHasLeft(hasLeft);
		}

		Date openedAt = (Date)attributes.get("openedAt");

		if (openedAt != null) {
			setOpenedAt(openedAt);
		}
	}

	/**
	* Returns the primary key of this participant.
	*
	* @return the primary key of this participant
	*/
	@Override
	public long getPrimaryKey() {
		return _participant.getPrimaryKey();
	}

	/**
	* Sets the primary key of this participant.
	*
	* @param primaryKey the primary key of this participant
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_participant.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the pid of this participant.
	*
	* @return the pid of this participant
	*/
	@Override
	public long getPid() {
		return _participant.getPid();
	}

	/**
	* Sets the pid of this participant.
	*
	* @param pid the pid of this participant
	*/
	@Override
	public void setPid(long pid) {
		_participant.setPid(pid);
	}

	/**
	* Returns the cid of this participant.
	*
	* @return the cid of this participant
	*/
	@Override
	public long getCid() {
		return _participant.getCid();
	}

	/**
	* Sets the cid of this participant.
	*
	* @param cid the cid of this participant
	*/
	@Override
	public void setCid(long cid) {
		_participant.setCid(cid);
	}

	/**
	* Returns the participant ID of this participant.
	*
	* @return the participant ID of this participant
	*/
	@Override
	public long getParticipantId() {
		return _participant.getParticipantId();
	}

	/**
	* Sets the participant ID of this participant.
	*
	* @param participantId the participant ID of this participant
	*/
	@Override
	public void setParticipantId(long participantId) {
		_participant.setParticipantId(participantId);
	}

	/**
	* Returns the unread messages count of this participant.
	*
	* @return the unread messages count of this participant
	*/
	@Override
	public int getUnreadMessagesCount() {
		return _participant.getUnreadMessagesCount();
	}

	/**
	* Sets the unread messages count of this participant.
	*
	* @param unreadMessagesCount the unread messages count of this participant
	*/
	@Override
	public void setUnreadMessagesCount(int unreadMessagesCount) {
		_participant.setUnreadMessagesCount(unreadMessagesCount);
	}

	/**
	* Returns the is opened of this participant.
	*
	* @return the is opened of this participant
	*/
	@Override
	public boolean getIsOpened() {
		return _participant.getIsOpened();
	}

	/**
	* Returns <code>true</code> if this participant is is opened.
	*
	* @return <code>true</code> if this participant is is opened; <code>false</code> otherwise
	*/
	@Override
	public boolean isIsOpened() {
		return _participant.isIsOpened();
	}

	/**
	* Sets whether this participant is is opened.
	*
	* @param isOpened the is opened of this participant
	*/
	@Override
	public void setIsOpened(boolean isOpened) {
		_participant.setIsOpened(isOpened);
	}

	/**
	* Returns the is creator of this participant.
	*
	* @return the is creator of this participant
	*/
	@Override
	public boolean getIsCreator() {
		return _participant.getIsCreator();
	}

	/**
	* Returns <code>true</code> if this participant is is creator.
	*
	* @return <code>true</code> if this participant is is creator; <code>false</code> otherwise
	*/
	@Override
	public boolean isIsCreator() {
		return _participant.isIsCreator();
	}

	/**
	* Sets whether this participant is is creator.
	*
	* @param isCreator the is creator of this participant
	*/
	@Override
	public void setIsCreator(boolean isCreator) {
		_participant.setIsCreator(isCreator);
	}

	/**
	* Returns the has left of this participant.
	*
	* @return the has left of this participant
	*/
	@Override
	public boolean getHasLeft() {
		return _participant.getHasLeft();
	}

	/**
	* Returns <code>true</code> if this participant is has left.
	*
	* @return <code>true</code> if this participant is has left; <code>false</code> otherwise
	*/
	@Override
	public boolean isHasLeft() {
		return _participant.isHasLeft();
	}

	/**
	* Sets whether this participant is has left.
	*
	* @param hasLeft the has left of this participant
	*/
	@Override
	public void setHasLeft(boolean hasLeft) {
		_participant.setHasLeft(hasLeft);
	}

	/**
	* Returns the opened at of this participant.
	*
	* @return the opened at of this participant
	*/
	@Override
	public java.util.Date getOpenedAt() {
		return _participant.getOpenedAt();
	}

	/**
	* Sets the opened at of this participant.
	*
	* @param openedAt the opened at of this participant
	*/
	@Override
	public void setOpenedAt(java.util.Date openedAt) {
		_participant.setOpenedAt(openedAt);
	}

	@Override
	public boolean isNew() {
		return _participant.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_participant.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _participant.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_participant.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _participant.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _participant.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_participant.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _participant.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_participant.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_participant.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_participant.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ParticipantWrapper((Participant)_participant.clone());
	}

	@Override
	public int compareTo(
		com.marcelmika.limsmuc.persistence.generated.model.Participant participant) {
		return _participant.compareTo(participant);
	}

	@Override
	public int hashCode() {
		return _participant.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.marcelmika.limsmuc.persistence.generated.model.Participant> toCacheModel() {
		return _participant.toCacheModel();
	}

	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Participant toEscapedModel() {
		return new ParticipantWrapper(_participant.toEscapedModel());
	}

	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Participant toUnescapedModel() {
		return new ParticipantWrapper(_participant.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _participant.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _participant.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_participant.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ParticipantWrapper)) {
			return false;
		}

		ParticipantWrapper participantWrapper = (ParticipantWrapper)obj;

		if (Validator.equals(_participant, participantWrapper._participant)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Participant getWrappedParticipant() {
		return _participant;
	}

	@Override
	public Participant getWrappedModel() {
		return _participant;
	}

	@Override
	public void resetOriginalValues() {
		_participant.resetOriginalValues();
	}

	private Participant _participant;
}