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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import com.marcelmika.limsmuc.persistence.generated.model.Message;
import com.marcelmika.limsmuc.persistence.generated.model.MessageModel;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the Message service. Represents a row in the &quot;Limsmuc_Message&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.marcelmika.limsmuc.persistence.generated.model.MessageModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link MessageImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see MessageImpl
 * @see com.marcelmika.limsmuc.persistence.generated.model.Message
 * @see com.marcelmika.limsmuc.persistence.generated.model.MessageModel
 * @generated
 */
public class MessageModelImpl extends BaseModelImpl<Message>
	implements MessageModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a message model instance should use the {@link com.marcelmika.limsmuc.persistence.generated.model.Message} interface instead.
	 */
	public static final String TABLE_NAME = "Limsmuc_Message";
	public static final Object[][] TABLE_COLUMNS = {
			{ "mid", Types.BIGINT },
			{ "cid", Types.BIGINT },
			{ "messageType", Types.INTEGER },
			{ "creatorId", Types.BIGINT },
			{ "createdAt", Types.TIMESTAMP },
			{ "body", Types.VARCHAR },
			{ "syncIdSUC", Types.BIGINT },
			{ "syncIdChatPortlet", Types.BIGINT }
		};
	public static final String TABLE_SQL_CREATE = "create table Limsmuc_Message (mid LONG not null primary key,cid LONG,messageType INTEGER,creatorId LONG,createdAt DATE null,body TEXT null,syncIdSUC LONG,syncIdChatPortlet LONG)";
	public static final String TABLE_SQL_DROP = "drop table Limsmuc_Message";
	public static final String ORDER_BY_JPQL = " ORDER BY message.createdAt ASC";
	public static final String ORDER_BY_SQL = " ORDER BY Limsmuc_Message.createdAt ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.entity.cache.enabled.com.marcelmika.limsmuc.persistence.generated.model.Message"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.finder.cache.enabled.com.marcelmika.limsmuc.persistence.generated.model.Message"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.marcelmika.limsmuc.persistence.generated.model.Message"),
			true);
	public static long CID_COLUMN_BITMASK = 1L;
	public static long CREATORID_COLUMN_BITMASK = 2L;
	public static long SYNCIDCHATPORTLET_COLUMN_BITMASK = 4L;
	public static long SYNCIDSUC_COLUMN_BITMASK = 8L;
	public static long CREATEDAT_COLUMN_BITMASK = 16L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
				"lock.expiration.time.com.marcelmika.limsmuc.persistence.generated.model.Message"));

	public MessageModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _mid;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setMid(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _mid;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
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
		attributes.put("messageType", getMessageType());
		attributes.put("creatorId", getCreatorId());
		attributes.put("createdAt", getCreatedAt());
		attributes.put("body", getBody());
		attributes.put("syncIdSUC", getSyncIdSUC());
		attributes.put("syncIdChatPortlet", getSyncIdChatPortlet());

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

		Integer messageType = (Integer)attributes.get("messageType");

		if (messageType != null) {
			setMessageType(messageType);
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

		Long syncIdSUC = (Long)attributes.get("syncIdSUC");

		if (syncIdSUC != null) {
			setSyncIdSUC(syncIdSUC);
		}

		Long syncIdChatPortlet = (Long)attributes.get("syncIdChatPortlet");

		if (syncIdChatPortlet != null) {
			setSyncIdChatPortlet(syncIdChatPortlet);
		}
	}

	@Override
	public long getMid() {
		return _mid;
	}

	@Override
	public void setMid(long mid) {
		_mid = mid;
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
	public int getMessageType() {
		return _messageType;
	}

	@Override
	public void setMessageType(int messageType) {
		_messageType = messageType;
	}

	@Override
	public long getCreatorId() {
		return _creatorId;
	}

	@Override
	public void setCreatorId(long creatorId) {
		_columnBitmask |= CREATORID_COLUMN_BITMASK;

		if (!_setOriginalCreatorId) {
			_setOriginalCreatorId = true;

			_originalCreatorId = _creatorId;
		}

		_creatorId = creatorId;
	}

	public long getOriginalCreatorId() {
		return _originalCreatorId;
	}

	@Override
	public Date getCreatedAt() {
		return _createdAt;
	}

	@Override
	public void setCreatedAt(Date createdAt) {
		_columnBitmask = -1L;

		_createdAt = createdAt;
	}

	@Override
	public String getBody() {
		if (_body == null) {
			return StringPool.BLANK;
		}
		else {
			return _body;
		}
	}

	@Override
	public void setBody(String body) {
		_body = body;
	}

	@Override
	public long getSyncIdSUC() {
		return _syncIdSUC;
	}

	@Override
	public void setSyncIdSUC(long syncIdSUC) {
		_columnBitmask |= SYNCIDSUC_COLUMN_BITMASK;

		if (!_setOriginalSyncIdSUC) {
			_setOriginalSyncIdSUC = true;

			_originalSyncIdSUC = _syncIdSUC;
		}

		_syncIdSUC = syncIdSUC;
	}

	public long getOriginalSyncIdSUC() {
		return _originalSyncIdSUC;
	}

	@Override
	public long getSyncIdChatPortlet() {
		return _syncIdChatPortlet;
	}

	@Override
	public void setSyncIdChatPortlet(long syncIdChatPortlet) {
		_columnBitmask |= SYNCIDCHATPORTLET_COLUMN_BITMASK;

		if (!_setOriginalSyncIdChatPortlet) {
			_setOriginalSyncIdChatPortlet = true;

			_originalSyncIdChatPortlet = _syncIdChatPortlet;
		}

		_syncIdChatPortlet = syncIdChatPortlet;
	}

	public long getOriginalSyncIdChatPortlet() {
		return _originalSyncIdChatPortlet;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(0,
			Message.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public Message toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (Message)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		MessageImpl messageImpl = new MessageImpl();

		messageImpl.setMid(getMid());
		messageImpl.setCid(getCid());
		messageImpl.setMessageType(getMessageType());
		messageImpl.setCreatorId(getCreatorId());
		messageImpl.setCreatedAt(getCreatedAt());
		messageImpl.setBody(getBody());
		messageImpl.setSyncIdSUC(getSyncIdSUC());
		messageImpl.setSyncIdChatPortlet(getSyncIdChatPortlet());

		messageImpl.resetOriginalValues();

		return messageImpl;
	}

	@Override
	public int compareTo(Message message) {
		int value = 0;

		value = DateUtil.compareTo(getCreatedAt(), message.getCreatedAt());

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

		if (!(obj instanceof Message)) {
			return false;
		}

		Message message = (Message)obj;

		long primaryKey = message.getPrimaryKey();

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
		MessageModelImpl messageModelImpl = this;

		messageModelImpl._originalCid = messageModelImpl._cid;

		messageModelImpl._setOriginalCid = false;

		messageModelImpl._originalCreatorId = messageModelImpl._creatorId;

		messageModelImpl._setOriginalCreatorId = false;

		messageModelImpl._originalSyncIdSUC = messageModelImpl._syncIdSUC;

		messageModelImpl._setOriginalSyncIdSUC = false;

		messageModelImpl._originalSyncIdChatPortlet = messageModelImpl._syncIdChatPortlet;

		messageModelImpl._setOriginalSyncIdChatPortlet = false;

		messageModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<Message> toCacheModel() {
		MessageCacheModel messageCacheModel = new MessageCacheModel();

		messageCacheModel.mid = getMid();

		messageCacheModel.cid = getCid();

		messageCacheModel.messageType = getMessageType();

		messageCacheModel.creatorId = getCreatorId();

		Date createdAt = getCreatedAt();

		if (createdAt != null) {
			messageCacheModel.createdAt = createdAt.getTime();
		}
		else {
			messageCacheModel.createdAt = Long.MIN_VALUE;
		}

		messageCacheModel.body = getBody();

		String body = messageCacheModel.body;

		if ((body != null) && (body.length() == 0)) {
			messageCacheModel.body = null;
		}

		messageCacheModel.syncIdSUC = getSyncIdSUC();

		messageCacheModel.syncIdChatPortlet = getSyncIdChatPortlet();

		return messageCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{mid=");
		sb.append(getMid());
		sb.append(", cid=");
		sb.append(getCid());
		sb.append(", messageType=");
		sb.append(getMessageType());
		sb.append(", creatorId=");
		sb.append(getCreatorId());
		sb.append(", createdAt=");
		sb.append(getCreatedAt());
		sb.append(", body=");
		sb.append(getBody());
		sb.append(", syncIdSUC=");
		sb.append(getSyncIdSUC());
		sb.append(", syncIdChatPortlet=");
		sb.append(getSyncIdChatPortlet());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(28);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.limsmuc.persistence.generated.model.Message");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>mid</column-name><column-value><![CDATA[");
		sb.append(getMid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>cid</column-name><column-value><![CDATA[");
		sb.append(getCid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>messageType</column-name><column-value><![CDATA[");
		sb.append(getMessageType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>creatorId</column-name><column-value><![CDATA[");
		sb.append(getCreatorId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createdAt</column-name><column-value><![CDATA[");
		sb.append(getCreatedAt());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>body</column-name><column-value><![CDATA[");
		sb.append(getBody());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>syncIdSUC</column-name><column-value><![CDATA[");
		sb.append(getSyncIdSUC());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>syncIdChatPortlet</column-name><column-value><![CDATA[");
		sb.append(getSyncIdChatPortlet());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static ClassLoader _classLoader = Message.class.getClassLoader();
	private static Class<?>[] _escapedModelInterfaces = new Class[] {
			Message.class
		};
	private long _mid;
	private long _cid;
	private long _originalCid;
	private boolean _setOriginalCid;
	private int _messageType;
	private long _creatorId;
	private long _originalCreatorId;
	private boolean _setOriginalCreatorId;
	private Date _createdAt;
	private String _body;
	private long _syncIdSUC;
	private long _originalSyncIdSUC;
	private boolean _setOriginalSyncIdSUC;
	private long _syncIdChatPortlet;
	private long _originalSyncIdChatPortlet;
	private boolean _setOriginalSyncIdChatPortlet;
	private long _columnBitmask;
	private Message _escapedModel;
}