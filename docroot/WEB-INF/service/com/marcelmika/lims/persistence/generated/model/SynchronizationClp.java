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

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.marcelmika.lims.persistence.generated.service.ClpSerializer;
import com.marcelmika.lims.persistence.generated.service.SynchronizationLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class SynchronizationClp extends BaseModelImpl<Synchronization>
	implements Synchronization {
	public SynchronizationClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return Synchronization.class;
	}

	@Override
	public String getModelClassName() {
		return Synchronization.class.getName();
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
		attributes.put("sucSync", getSucSync());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long sid = (Long)attributes.get("sid");

		if (sid != null) {
			setSid(sid);
		}

		Boolean sucSync = (Boolean)attributes.get("sucSync");

		if (sucSync != null) {
			setSucSync(sucSync);
		}
	}

	@Override
	public long getSid() {
		return _sid;
	}

	@Override
	public void setSid(long sid) {
		_sid = sid;

		if (_synchronizationRemoteModel != null) {
			try {
				Class<?> clazz = _synchronizationRemoteModel.getClass();

				Method method = clazz.getMethod("setSid", long.class);

				method.invoke(_synchronizationRemoteModel, sid);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public boolean getSucSync() {
		return _sucSync;
	}

	@Override
	public boolean isSucSync() {
		return _sucSync;
	}

	@Override
	public void setSucSync(boolean sucSync) {
		_sucSync = sucSync;

		if (_synchronizationRemoteModel != null) {
			try {
				Class<?> clazz = _synchronizationRemoteModel.getClass();

				Method method = clazz.getMethod("setSucSync", boolean.class);

				method.invoke(_synchronizationRemoteModel, sucSync);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	public BaseModel<?> getSynchronizationRemoteModel() {
		return _synchronizationRemoteModel;
	}

	public void setSynchronizationRemoteModel(
		BaseModel<?> synchronizationRemoteModel) {
		_synchronizationRemoteModel = synchronizationRemoteModel;
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

		Class<?> remoteModelClass = _synchronizationRemoteModel.getClass();

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

		Object returnValue = method.invoke(_synchronizationRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() throws SystemException {
		if (this.isNew()) {
			SynchronizationLocalServiceUtil.addSynchronization(this);
		}
		else {
			SynchronizationLocalServiceUtil.updateSynchronization(this);
		}
	}

	@Override
	public Synchronization toEscapedModel() {
		return (Synchronization)ProxyUtil.newProxyInstance(Synchronization.class.getClassLoader(),
			new Class[] { Synchronization.class },
			new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		SynchronizationClp clone = new SynchronizationClp();

		clone.setSid(getSid());
		clone.setSucSync(getSucSync());

		return clone;
	}

	@Override
	public int compareTo(Synchronization synchronization) {
		long primaryKey = synchronization.getPrimaryKey();

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

		if (!(obj instanceof SynchronizationClp)) {
			return false;
		}

		SynchronizationClp synchronization = (SynchronizationClp)obj;

		long primaryKey = synchronization.getPrimaryKey();

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
	public String toString() {
		StringBundler sb = new StringBundler(5);

		sb.append("{sid=");
		sb.append(getSid());
		sb.append(", sucSync=");
		sb.append(getSucSync());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(10);

		sb.append("<model><model-name>");
		sb.append(
			"com.marcelmika.lims.persistence.generated.model.Synchronization");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>sid</column-name><column-value><![CDATA[");
		sb.append(getSid());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>sucSync</column-name><column-value><![CDATA[");
		sb.append(getSucSync());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _sid;
	private boolean _sucSync;
	private BaseModel<?> _synchronizationRemoteModel;
}