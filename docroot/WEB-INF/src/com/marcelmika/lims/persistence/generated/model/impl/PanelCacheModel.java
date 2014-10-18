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

package com.marcelmika.lims.persistence.generated.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.marcelmika.lims.persistence.generated.model.Panel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Panel in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Panel
 * @generated
 */
public class PanelCacheModel implements CacheModel<Panel>, Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(7);

		sb.append("{pid=");
		sb.append(pid);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", activePanelId=");
		sb.append(activePanelId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Panel toEntityModel() {
		PanelImpl panelImpl = new PanelImpl();

		panelImpl.setPid(pid);
		panelImpl.setUserId(userId);

		if (activePanelId == null) {
			panelImpl.setActivePanelId(StringPool.BLANK);
		}
		else {
			panelImpl.setActivePanelId(activePanelId);
		}

		panelImpl.resetOriginalValues();

		return panelImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		pid = objectInput.readLong();
		userId = objectInput.readLong();
		activePanelId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(pid);
		objectOutput.writeLong(userId);

		if (activePanelId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(activePanelId);
		}
	}

	public long pid;
	public long userId;
	public String activePanelId;
}