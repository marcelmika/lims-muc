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
import com.liferay.portal.model.CacheModel;

import com.marcelmika.lims.persistence.generated.model.Synchronization;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing Synchronization in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Synchronization
 * @generated
 */
public class SynchronizationCacheModel implements CacheModel<Synchronization>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{sid=");
		sb.append(sid);
		sb.append(", mucSync=");
		sb.append(mucSync);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Synchronization toEntityModel() {
		SynchronizationImpl synchronizationImpl = new SynchronizationImpl();

		synchronizationImpl.setSid(sid);
		synchronizationImpl.setMucSync(mucSync);

		synchronizationImpl.resetOriginalValues();

		return synchronizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		sid = objectInput.readLong();
		mucSync = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(sid);
		objectOutput.writeBoolean(mucSync);
	}

	public long sid;
	public boolean mucSync;
}