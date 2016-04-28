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
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;
import com.liferay.portal.util.PortalUtil;

import com.marcelmika.limsmuc.persistence.generated.service.ClpSerializer;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class SettingsClp extends BaseModelImpl<Settings> implements Settings {
	public SettingsClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Settings.class;
	}

	@Override
	public String getModelClassName() {
		return Settings.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _sid;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSid(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _sid;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("sid", getSid());
		attributes.put("userId", getUserId());
		attributes.put("presence", getPresence());
		attributes.put("presenceUpdatedAt", getPresenceUpdatedAt());
		attributes.put("mute", getMute());
		attributes.put("notificationsEnabled", getNotificationsEnabled());
		attributes.put("chatEnabled", getChatEnabled());
		attributes.put("adminAreaOpened", getAdminAreaOpened());
		attributes.put("connected", getConnected());
		attributes.put("connectedAt", getConnectedAt());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long sid = (Long)attributes.get("sid");

		if (sid != null) {
			setSid(sid);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String presence = (String)attributes.get("presence");

		if (presence != null) {
			setPresence(presence);
		}

		Date presenceUpdatedAt = (Date)attributes.get("presenceUpdatedAt");

		if (presenceUpdatedAt != null) {
			setPresenceUpdatedAt(presenceUpdatedAt);
		}

		Boolean mute = (Boolean)attributes.get("mute");

		if (mute != null) {
			setMute(mute);
		}

		Boolean notificationsEnabled = (Boolean)attributes.get(
				"notificationsEnabled");

		if (notificationsEnabled != null) {
			setNotificationsEnabled(notificationsEnabled);
		}

		Boolean chatEnabled = (Boolean)attributes.get("chatEnabled");

		if (chatEnabled != null) {
			setChatEnabled(chatEnabled);
		}

		Boolean adminAreaOpened = (Boolean)attributes.get("adminAreaOpened");

		if (adminAreaOpened != null) {
			setAdminAreaOpened(adminAreaOpened);
		}

		Boolean connected = (Boolean)attributes.get("connected");

		if (connected != null) {
			setConnected(connected);
		}

		Date connectedAt = (Date)attributes.get("connectedAt");

		if (connectedAt != null) {
			setConnectedAt(connectedAt);
		}
	}

	@Override
	public long getSid() {
		return _sid;
	}

	@Override
	public void setSid(long sid) {
		_sid = sid;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setSid", long.class);

				method.invoke(_settingsRemoteModel, sid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_settingsRemoteModel, userId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	@Override
	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
	}

	@Override
	public String getPresence() {
		return _presence;
	}

	@Override
	public void setPresence(String presence) {
		_presence = presence;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setPresence", String.class);

				method.invoke(_settingsRemoteModel, presence);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getPresenceUpdatedAt() {
		return _presenceUpdatedAt;
	}

	@Override
	public void setPresenceUpdatedAt(Date presenceUpdatedAt) {
		_presenceUpdatedAt = presenceUpdatedAt;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setPresenceUpdatedAt",
						Date.class);

				method.invoke(_settingsRemoteModel, presenceUpdatedAt);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public boolean getMute() {
		return _mute;
	}

	@Override
	public boolean isMute() {
		return _mute;
	}

	@Override
	public void setMute(boolean mute) {
		_mute = mute;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setMute", boolean.class);

				method.invoke(_settingsRemoteModel, mute);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public boolean getNotificationsEnabled() {
		return _notificationsEnabled;
	}

	@Override
	public boolean isNotificationsEnabled() {
		return _notificationsEnabled;
	}

	@Override
	public void setNotificationsEnabled(boolean notificationsEnabled) {
		_notificationsEnabled = notificationsEnabled;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setNotificationsEnabled",
						boolean.class);

				method.invoke(_settingsRemoteModel, notificationsEnabled);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public boolean getChatEnabled() {
		return _chatEnabled;
	}

	@Override
	public boolean isChatEnabled() {
		return _chatEnabled;
	}

	@Override
	public void setChatEnabled(boolean chatEnabled) {
		_chatEnabled = chatEnabled;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setChatEnabled", boolean.class);

				method.invoke(_settingsRemoteModel, chatEnabled);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public boolean getAdminAreaOpened() {
		return _adminAreaOpened;
	}

	@Override
	public boolean isAdminAreaOpened() {
		return _adminAreaOpened;
	}

	@Override
	public void setAdminAreaOpened(boolean adminAreaOpened) {
		_adminAreaOpened = adminAreaOpened;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setAdminAreaOpened",
						boolean.class);

				method.invoke(_settingsRemoteModel, adminAreaOpened);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public boolean getConnected() {
		return _connected;
	}

	@Override
	public boolean isConnected() {
		return _connected;
	}

	@Override
	public void setConnected(boolean connected) {
		_connected = connected;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setConnected", boolean.class);

				method.invoke(_settingsRemoteModel, connected);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getConnectedAt() {
		return _connectedAt;
	}

	@Override
	public void setConnectedAt(Date connectedAt) {
		_connectedAt = connectedAt;

		if (_settingsRemoteModel != null) {
			try {
				Class<?> clazz = _settingsRemoteModel.getClass();

				Method method = clazz.getMethod("setConnectedAt", Date.class);

				method.invoke(_settingsRemoteModel, connectedAt);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getSettingsRemoteModel() {
		return _settingsRemoteModel;
	}

	public void setSettingsRemoteModel(BaseModel<?> settingsRemoteModel) {
		_settingsRemoteModel = settingsRemoteModel;
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

		Class<?> remoteModelClass = _settingsRemoteModel.getClass();

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

		Object returnValue = method.invoke(_settingsRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			SettingsLocalServiceUtil.addSettings(this);
		}
		else {
			SettingsLocalServiceUtil.updateSettings(this);
		}
	}

	@Override
	public Settings toEscapedModel() {
		return (Settings)ProxyUtil.newProxyInstance(Settings.class.getClassLoader(),
			new Class[] { Settings.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		SettingsClp clone = new SettingsClp();

		clone.setSid(getSid());
		clone.setUserId(getUserId());
		clone.setPresence(getPresence());
		clone.setPresenceUpdatedAt(getPresenceUpdatedAt());
		clone.setMute(getMute());
		clone.setNotificationsEnabled(getNotificationsEnabled());
		clone.setChatEnabled(getChatEnabled());
		clone.setAdminAreaOpened(getAdminAreaOpened());
		clone.setConnected(getConnected());
		clone.setConnectedAt(getConnectedAt());

		return clone;
	}

	@Override
	public int compareTo(Settings settings) {
		long primaryKey = settings.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SettingsClp)) {
			return false;
		}

		SettingsClp settings = (SettingsClp)obj;

		long primaryKey = settings.getPrimaryKey();

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
		StringBundler sb = new StringBundler(21);

		sb.append("{sid=");
		sb.append(getSid());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", presence=");
		sb.append(getPresence());
		sb.append(", presenceUpdatedAt=");
		sb.append(getPresenceUpdatedAt());
		sb.append(", mute=");
		sb.append(getMute());
		sb.append(", notificationsEnabled=");
		sb.append(getNotificationsEnabled());
		sb.append(", chatEnabled=");
		sb.append(getChatEnabled());
		sb.append(", adminAreaOpened=");
		sb.append(getAdminAreaOpened());
		sb.append(", connected=");
		sb.append(getConnected());
		sb.append(", connectedAt=");
		sb.append(getConnectedAt());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(34);

		sb.append("<model><model-name>");
		sb.append("com.marcelmika.limsmuc.persistence.generated.model.Settings");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>sid</column-name><column-value><![CDATA[");
		sb.append(getSid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>presence</column-name><column-value><![CDATA[");
		sb.append(getPresence());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>presenceUpdatedAt</column-name><column-value><![CDATA[");
		sb.append(getPresenceUpdatedAt());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mute</column-name><column-value><![CDATA[");
		sb.append(getMute());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>notificationsEnabled</column-name><column-value><![CDATA[");
		sb.append(getNotificationsEnabled());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>chatEnabled</column-name><column-value><![CDATA[");
		sb.append(getChatEnabled());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>adminAreaOpened</column-name><column-value><![CDATA[");
		sb.append(getAdminAreaOpened());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>connected</column-name><column-value><![CDATA[");
		sb.append(getConnected());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>connectedAt</column-name><column-value><![CDATA[");
		sb.append(getConnectedAt());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _sid;
	private long _userId;
	private String _userUuid;
	private String _presence;
	private Date _presenceUpdatedAt;
	private boolean _mute;
	private boolean _notificationsEnabled;
	private boolean _chatEnabled;
	private boolean _adminAreaOpened;
	private boolean _connected;
	private Date _connectedAt;
	private BaseModel<?> _settingsRemoteModel;
	private Class<?> _clpSerializerClass = com.marcelmika.limsmuc.persistence.generated.service.ClpSerializer.class;
}