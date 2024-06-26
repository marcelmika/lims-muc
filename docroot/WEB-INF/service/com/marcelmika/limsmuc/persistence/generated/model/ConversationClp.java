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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.marcelmika.limsmuc.persistence.generated.service.ClpSerializer;
import com.marcelmika.limsmuc.persistence.generated.service.ConversationLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class ConversationClp extends BaseModelImpl<Conversation>
	implements Conversation {
	public ConversationClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Conversation.class;
	}

	@Override
	public String getModelClassName() {
		return Conversation.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _cid;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setCid(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _cid;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cid", getCid());
		attributes.put("conversationId", getConversationId());
		attributes.put("conversationType", getConversationType());
		attributes.put("updatedAt", getUpdatedAt());
		attributes.put("syncIdSUC", getSyncIdSUC());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cid = (Long)attributes.get("cid");

		if (cid != null) {
			setCid(cid);
		}

		String conversationId = (String)attributes.get("conversationId");

		if (conversationId != null) {
			setConversationId(conversationId);
		}

		Integer conversationType = (Integer)attributes.get("conversationType");

		if (conversationType != null) {
			setConversationType(conversationType);
		}

		Date updatedAt = (Date)attributes.get("updatedAt");

		if (updatedAt != null) {
			setUpdatedAt(updatedAt);
		}

		Long syncIdSUC = (Long)attributes.get("syncIdSUC");

		if (syncIdSUC != null) {
			setSyncIdSUC(syncIdSUC);
		}
	}

	@Override
	public long getCid() {
		return _cid;
	}

	@Override
	public void setCid(long cid) {
		_cid = cid;

		if (_conversationRemoteModel != null) {
			try {
				Class<?> clazz = _conversationRemoteModel.getClass();

				Method method = clazz.getMethod("setCid", long.class);

				method.invoke(_conversationRemoteModel, cid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getConversationId() {
		return _conversationId;
	}

	@Override
	public void setConversationId(String conversationId) {
		_conversationId = conversationId;

		if (_conversationRemoteModel != null) {
			try {
				Class<?> clazz = _conversationRemoteModel.getClass();

				Method method = clazz.getMethod("setConversationId",
						String.class);

				method.invoke(_conversationRemoteModel, conversationId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getConversationType() {
		return _conversationType;
	}

	@Override
	public void setConversationType(int conversationType) {
		_conversationType = conversationType;

		if (_conversationRemoteModel != null) {
			try {
				Class<?> clazz = _conversationRemoteModel.getClass();

				Method method = clazz.getMethod("setConversationType", int.class);

				method.invoke(_conversationRemoteModel, conversationType);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getUpdatedAt() {
		return _updatedAt;
	}

	@Override
	public void setUpdatedAt(Date updatedAt) {
		_updatedAt = updatedAt;

		if (_conversationRemoteModel != null) {
			try {
				Class<?> clazz = _conversationRemoteModel.getClass();

				Method method = clazz.getMethod("setUpdatedAt", Date.class);

				method.invoke(_conversationRemoteModel, updatedAt);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getSyncIdSUC() {
		return _syncIdSUC;
	}

	@Override
	public void setSyncIdSUC(long syncIdSUC) {
		_syncIdSUC = syncIdSUC;

		if (_conversationRemoteModel != null) {
			try {
				Class<?> clazz = _conversationRemoteModel.getClass();

				Method method = clazz.getMethod("setSyncIdSUC", long.class);

				method.invoke(_conversationRemoteModel, syncIdSUC);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getConversationRemoteModel() {
		return _conversationRemoteModel;
	}

	public void setConversationRemoteModel(BaseModel<?> conversationRemoteModel) {
		_conversationRemoteModel = conversationRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _conversationRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_conversationRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			ConversationLocalServiceUtil.addConversation(this);
		}
		else {
			ConversationLocalServiceUtil.updateConversation(this);
		}
	}

	@Override
	public Conversation toEscapedModel() {
		return (Conversation)ProxyUtil.newProxyInstance(Conversation.class.getClassLoader(),
			new Class[] { Conversation.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		ConversationClp clone = new ConversationClp();

		clone.setCid(getCid());
		clone.setConversationId(getConversationId());
		clone.setConversationType(getConversationType());
		clone.setUpdatedAt(getUpdatedAt());
		clone.setSyncIdSUC(getSyncIdSUC());

		return clone;
	}

	@Override
	public int compareTo(Conversation conversation) {
		int value = 0;

		value = DateUtil.compareTo(getUpdatedAt(), conversation.getUpdatedAt());

		value = value * -1;

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

		if (!(obj instanceof ConversationClp)) {
			return false;
		}

		ConversationClp conversation = (ConversationClp)obj;

		long primaryKey = conversation.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{cid=");
		sb.append(getCid());
		sb.append(", conversationId=");
		sb.append(getConversationId());
		sb.append(", conversationType=");
		sb.append(getConversationType());
		sb.append(", updatedAt=");
		sb.append(getUpdatedAt());
		sb.append(", syncIdSUC=");
		sb.append(getSyncIdSUC());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(19);

		sb.append("<model><model-name>");
		sb.append(
			"com.marcelmika.limsmuc.persistence.generated.model.Conversation");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>cid</column-name><column-value><![CDATA[");
		sb.append(getCid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>conversationId</column-name><column-value><![CDATA[");
		sb.append(getConversationId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>conversationType</column-name><column-value><![CDATA[");
		sb.append(getConversationType());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>updatedAt</column-name><column-value><![CDATA[");
		sb.append(getUpdatedAt());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>syncIdSUC</column-name><column-value><![CDATA[");
		sb.append(getSyncIdSUC());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _cid;
	private String _conversationId;
	private int _conversationType;
	private Date _updatedAt;
	private long _syncIdSUC;
	private BaseModel<?> _conversationRemoteModel;
	private Class<?> _clpSerializerClass = com.marcelmika.limsmuc.persistence.generated.service.ClpSerializer.class;
}