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

package com.marcelmika.limsmuc.persistence.generated.model.impl;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import com.marcelmika.limsmuc.persistence.generated.model.Participant;
import com.marcelmika.limsmuc.persistence.generated.model.ParticipantModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the Participant service. Represents a row in the &quot;Limsmuc_Participant&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.marcelmika.limsmuc.persistence.generated.model.ParticipantModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link ParticipantImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ParticipantImpl
 * @see com.marcelmika.limsmuc.persistence.generated.model.Participant
 * @see com.marcelmika.limsmuc.persistence.generated.model.ParticipantModel
 * @generated
 */
public class ParticipantModelImpl extends BaseModelImpl<Participant>
	implements ParticipantModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a participant model instance should use the {@link com.marcelmika.limsmuc.persistence.generated.model.Participant} interface instead.
	 */
	public static final String TABLE_NAME = "Limsmuc_Participant";
	public static final Object[][] TABLE_COLUMNS = {
			{ "pid", Types.BIGINT },
			{ "cid", Types.BIGINT },
			{ "participantId", Types.BIGINT },
			{ "unreadMessagesCount", Types.INTEGER },
			{ "isOpened", Types.BOOLEAN },
			{ "isCreator", Types.BOOLEAN },
			{ "hasLeft", Types.BOOLEAN },
			{ "openedAt", Types.TIMESTAMP }
		};
	public static final String TABLE_SQL_CREATE = "create table Limsmuc_Participant (pid LONG not null primary key,cid LONG,participantId LONG,unreadMessagesCount INTEGER,isOpened BOOLEAN,isCreator BOOLEAN,hasLeft BOOLEAN,openedAt DATE null)";
	public static final String TABLE_SQL_DROP = "drop table Limsmuc_Participant";
	public static final String ORDER_BY_JPQL = " ORDER BY participant.openedAt ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Limsmuc_Participant.openedAt ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.marcelmika.limsmuc.persistence.generated.model.Participant"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.marcelmika.limsmuc.persistence.generated.model.Participant"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.marcelmika.limsmuc.persistence.generated.model.Participant"),
			true);
	public static long CID_COLUMN_BITMASK = 1L;
	public static long ISOPENED_COLUMN_BITMASK = 2L;
	public static long PARTICIPANTID_COLUMN_BITMASK = 4L;
	public static long OPENEDAT_COLUMN_BITMASK = 8L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.marcelmika.limsmuc.persistence.generated.model.Participant"));

	public ParticipantModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _pid;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setPid(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _pid;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
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

	@Override
	public long getPid() {
		return _pid;
	}

	@Override
	public void setPid(long pid) {
		_pid = pid;
	}

	@Override
	public long getCid() {
		return _cid;
	}

	@Override
	public void setCid(long cid) {
		_columnBitmask |= CID_COLUMN_BITMASK;

		if (!_setOriginalCid) {
			_setOriginalCid = true;

			_originalCid = _cid;
		}

		_cid = cid;
	}

	public long getOriginalCid() {
		return _originalCid;
	}

	@Override
	public long getParticipantId() {
		return _participantId;
	}

	@Override
	public void setParticipantId(long participantId) {
		_columnBitmask |= PARTICIPANTID_COLUMN_BITMASK;

		if (!_setOriginalParticipantId) {
			_setOriginalParticipantId = true;

			_originalParticipantId = _participantId;
		}

		_participantId = participantId;
	}

	public long getOriginalParticipantId() {
		return _originalParticipantId;
	}

	@Override
	public int getUnreadMessagesCount() {
		return _unreadMessagesCount;
	}

	@Override
	public void setUnreadMessagesCount(int unreadMessagesCount) {
		_unreadMessagesCount = unreadMessagesCount;
	}

	@Override
	public boolean getIsOpened() {
		return _isOpened;
	}

	@Override
	public boolean isIsOpened() {
		return _isOpened;
	}

	@Override
	public void setIsOpened(boolean isOpened) {
		_columnBitmask |= ISOPENED_COLUMN_BITMASK;

		if (!_setOriginalIsOpened) {
			_setOriginalIsOpened = true;

			_originalIsOpened = _isOpened;
		}

		_isOpened = isOpened;
	}

	public boolean getOriginalIsOpened() {
		return _originalIsOpened;
	}

	@Override
	public boolean getIsCreator() {
		return _isCreator;
	}

	@Override
	public boolean isIsCreator() {
		return _isCreator;
	}

	@Override
	public void setIsCreator(boolean isCreator) {
		_isCreator = isCreator;
	}

	@Override
	public boolean getHasLeft() {
		return _hasLeft;
	}

	@Override
	public boolean isHasLeft() {
		return _hasLeft;
	}

	@Override
	public void setHasLeft(boolean hasLeft) {
		_hasLeft = hasLeft;
	}

	@Override
	public Date getOpenedAt() {
		return _openedAt;
	}

	@Override
	public void setOpenedAt(Date openedAt) {
		_columnBitmask = -1L;

		_openedAt = openedAt;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			Participant.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Participant toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Participant)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		ParticipantImpl participantImpl = new ParticipantImpl();

		participantImpl.setPid(getPid());
		participantImpl.setCid(getCid());
		participantImpl.setParticipantId(getParticipantId());
		participantImpl.setUnreadMessagesCount(getUnreadMessagesCount());
		participantImpl.setIsOpened(getIsOpened());
		participantImpl.setIsCreator(getIsCreator());
		participantImpl.setHasLeft(getHasLeft());
		participantImpl.setOpenedAt(getOpenedAt());

		participantImpl.resetOriginalValues();

		return participantImpl;
	}

	@Override
	public int compareTo(Participant participant) {
		int value = 0;

		value = DateUtil.compareTo(getOpenedAt(), participant.getOpenedAt());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof Participant)) {
			return false;
		}

		Participant participant = (Participant)obj;

		long primaryKey = participant.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public void resetOriginalValues() {
		ParticipantModelImpl participantModelImpl = this;

		participantModelImpl._originalCid = participantModelImpl._cid;

		participantModelImpl._setOriginalCid = false;

		participantModelImpl._originalParticipantId = participantModelImpl._participantId;

		participantModelImpl._setOriginalParticipantId = false;

		participantModelImpl._originalIsOpened = participantModelImpl._isOpened;

		participantModelImpl._setOriginalIsOpened = false;

		participantModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Participant> toCacheModel() {
		ParticipantCacheModel participantCacheModel = new ParticipantCacheModel();

		participantCacheModel.pid = getPid();

		participantCacheModel.cid = getCid();

		participantCacheModel.participantId = getParticipantId();

		participantCacheModel.unreadMessagesCount = getUnreadMessagesCount();

		participantCacheModel.isOpened = getIsOpened();

		participantCacheModel.isCreator = getIsCreator();

		participantCacheModel.hasLeft = getHasLeft();

		Date openedAt = getOpenedAt();

		if (openedAt != null) {
			participantCacheModel.openedAt = openedAt.getTime();
		}
		else {
			participantCacheModel.openedAt = Long.MIN_VALUE;
		}

		return participantCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{pid=");
		sb.append(getPid());
		sb.append(", cid=");
		sb.append(getCid());
		sb.append(", participantId=");
		sb.append(getParticipantId());
		sb.append(", unreadMessagesCount=");
		sb.append(getUnreadMessagesCount());
		sb.append(", isOpened=");
		sb.append(getIsOpened());
		sb.append(", isCreator=");
		sb.append(getIsCreator());
		sb.append(", hasLeft=");
		sb.append(getHasLeft());
		sb.append(", openedAt=");
		sb.append(getOpenedAt());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append(
			"com.marcelmika.limsmuc.persistence.generated.model.Participant");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>pid</column-name><column-value><![CDATA[");
		sb.append(getPid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>cid</column-name><column-value><![CDATA[");
		sb.append(getCid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>participantId</column-name><column-value><![CDATA[");
		sb.append(getParticipantId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>unreadMessagesCount</column-name><column-value><![CDATA[");
		sb.append(getUnreadMessagesCount());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>isOpened</column-name><column-value><![CDATA[");
		sb.append(getIsOpened());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>isCreator</column-name><column-value><![CDATA[");
		sb.append(getIsCreator());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hasLeft</column-name><column-value><![CDATA[");
		sb.append(getHasLeft());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>openedAt</column-name><column-value><![CDATA[");
		sb.append(getOpenedAt());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Participant.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			Participant.class
		};
	private long _pid;
	private long _cid;
	private long _originalCid;
	private boolean _setOriginalCid;
	private long _participantId;
	private long _originalParticipantId;
	private boolean _setOriginalParticipantId;
	private int _unreadMessagesCount;
	private boolean _isOpened;
	private boolean _originalIsOpened;
	private boolean _setOriginalIsOpened;
	private boolean _isCreator;
	private boolean _hasLeft;
	private Date _openedAt;
	private long _columnBitmask;
	private Participant _escapedModel;
}