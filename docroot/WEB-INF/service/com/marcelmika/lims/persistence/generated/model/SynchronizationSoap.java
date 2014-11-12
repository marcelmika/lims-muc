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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SynchronizationSoap implements Serializable {
	public static SynchronizationSoap toSoapModel(Synchronization model) {
		SynchronizationSoap soapModel = new SynchronizationSoap();

		soapModel.setSid(model.getSid());
		soapModel.setSucSync(model.getSucSync());

		return soapModel;
	}

	public static SynchronizationSoap[] toSoapModels(Synchronization[] models) {
		SynchronizationSoap[] soapModels = new SynchronizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SynchronizationSoap[][] toSoapModels(
		Synchronization[][] models) {
		SynchronizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SynchronizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SynchronizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SynchronizationSoap[] toSoapModels(
		List<Synchronization> models) {
		List<SynchronizationSoap> soapModels = new ArrayList<SynchronizationSoap>(models.size());

		for (Synchronization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SynchronizationSoap[soapModels.size()]);
	}

	public SynchronizationSoap() {
	}

	public long getPrimaryKey() {
		return _sid;
	}

	public void setPrimaryKey(long pk) {
		setSid(pk);
	}

	public long getSid() {
		return _sid;
	}

	public void setSid(long sid) {
		_sid = sid;
	}

	public boolean getSucSync() {
		return _sucSync;
	}

	public boolean isSucSync() {
		return _sucSync;
	}

	public void setSucSync(boolean sucSync) {
		_sucSync = sucSync;
	}

	private long _sid;
	private boolean _sucSync;
}