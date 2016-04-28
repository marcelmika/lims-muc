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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.marcelmika.limsmuc.persistence.generated.model.Settings;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Settings in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Settings
 * @generated
 */
public class SettingsCacheModel implements CacheModel<Settings>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{sid=");
		sb.append(sid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", presence=");
		sb.append(presence);
		sb.append(", presenceUpdatedAt=");
		sb.append(presenceUpdatedAt);
		sb.append(", mute=");
		sb.append(mute);
		sb.append(", notificationsEnabled=");
		sb.append(notificationsEnabled);
		sb.append(", chatEnabled=");
		sb.append(chatEnabled);
		sb.append(", adminAreaOpened=");
		sb.append(adminAreaOpened);
		sb.append(", connected=");
		sb.append(connected);
		sb.append(", connectedAt=");
		sb.append(connectedAt);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Settings toEntityModel() {
		SettingsImpl settingsImpl = new SettingsImpl();

		settingsImpl.setSid(sid);
		settingsImpl.setUserId(userId);

		if (presence == null) {
			settingsImpl.setPresence(StringPool.BLANK);
		}
		else {
			settingsImpl.setPresence(presence);
		}

		if (presenceUpdatedAt == Long.MIN_VALUE) {
			settingsImpl.setPresenceUpdatedAt(null);
		}
		else {
			settingsImpl.setPresenceUpdatedAt(new Date(presenceUpdatedAt));
		}

		settingsImpl.setMute(mute);
		settingsImpl.setNotificationsEnabled(notificationsEnabled);
		settingsImpl.setChatEnabled(chatEnabled);
		settingsImpl.setAdminAreaOpened(adminAreaOpened);
		settingsImpl.setConnected(connected);

		if (connectedAt == Long.MIN_VALUE) {
			settingsImpl.setConnectedAt(null);
		}
		else {
			settingsImpl.setConnectedAt(new Date(connectedAt));
		}

		settingsImpl.resetOriginalValues();

		return settingsImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		sid = objectInput.readLong();
		userId = objectInput.readLong();
		presence = objectInput.readUTF();
		presenceUpdatedAt = objectInput.readLong();
		mute = objectInput.readBoolean();
		notificationsEnabled = objectInput.readBoolean();
		chatEnabled = objectInput.readBoolean();
		adminAreaOpened = objectInput.readBoolean();
		connected = objectInput.readBoolean();
		connectedAt = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(sid);
		objectOutput.writeLong(userId);

		if (presence == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(presence);
		}

		objectOutput.writeLong(presenceUpdatedAt);
		objectOutput.writeBoolean(mute);
		objectOutput.writeBoolean(notificationsEnabled);
		objectOutput.writeBoolean(chatEnabled);
		objectOutput.writeBoolean(adminAreaOpened);
		objectOutput.writeBoolean(connected);
		objectOutput.writeLong(connectedAt);
	}

	public long sid;
	public long userId;
	public String presence;
	public long presenceUpdatedAt;
	public boolean mute;
	public boolean notificationsEnabled;
	public boolean chatEnabled;
	public boolean adminAreaOpened;
	public boolean connected;
	public long connectedAt;
}