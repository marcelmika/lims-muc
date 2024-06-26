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

import com.marcelmika.limsmuc.persistence.generated.model.Message;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Message in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Message
 * @generated
 */
public class MessageCacheModel implements CacheModel<Message>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{mid=");
		sb.append(mid);
		sb.append(", cid=");
		sb.append(cid);
		sb.append(", messageType=");
		sb.append(messageType);
		sb.append(", creatorId=");
		sb.append(creatorId);
		sb.append(", createdAt=");
		sb.append(createdAt);
		sb.append(", body=");
		sb.append(body);
		sb.append(", syncIdSUC=");
		sb.append(syncIdSUC);
		sb.append(", syncIdChatPortlet=");
		sb.append(syncIdChatPortlet);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Message toEntityModel() {
		MessageImpl messageImpl = new MessageImpl();

		messageImpl.setMid(mid);
		messageImpl.setCid(cid);
		messageImpl.setMessageType(messageType);
		messageImpl.setCreatorId(creatorId);

		if (createdAt == Long.MIN_VALUE) {
			messageImpl.setCreatedAt(null);
		}
		else {
			messageImpl.setCreatedAt(new Date(createdAt));
		}

		if (body == null) {
			messageImpl.setBody(StringPool.BLANK);
		}
		else {
			messageImpl.setBody(body);
		}

		messageImpl.setSyncIdSUC(syncIdSUC);
		messageImpl.setSyncIdChatPortlet(syncIdChatPortlet);

		messageImpl.resetOriginalValues();

		return messageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mid = objectInput.readLong();
		cid = objectInput.readLong();
		messageType = objectInput.readInt();
		creatorId = objectInput.readLong();
		createdAt = objectInput.readLong();
		body = objectInput.readUTF();
		syncIdSUC = objectInput.readLong();
		syncIdChatPortlet = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mid);
		objectOutput.writeLong(cid);
		objectOutput.writeInt(messageType);
		objectOutput.writeLong(creatorId);
		objectOutput.writeLong(createdAt);

		if (body == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(body);
		}

		objectOutput.writeLong(syncIdSUC);
		objectOutput.writeLong(syncIdChatPortlet);
	}

	public long mid;
	public long cid;
	public int messageType;
	public long creatorId;
	public long createdAt;
	public String body;
	public long syncIdSUC;
	public long syncIdChatPortlet;
}