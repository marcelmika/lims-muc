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

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Synchronization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Synchronization
 * @generated
 */
public class SynchronizationWrapper implements Synchronization,
	ModelWrapper<Synchronization> {
	public SynchronizationWrapper(Synchronization synchronization) {
		_synchronization = synchronization;
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

	/**
	* Returns the primary key of this synchronization.
	*
	* @return the primary key of this synchronization
	*/
	@Override
	public long getPrimaryKey() {
		return _synchronization.getPrimaryKey();
	}

	/**
	* Sets the primary key of this synchronization.
	*
	* @param primaryKey the primary key of this synchronization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_synchronization.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the sid of this synchronization.
	*
	* @return the sid of this synchronization
	*/
	@Override
	public long getSid() {
		return _synchronization.getSid();
	}

	/**
	* Sets the sid of this synchronization.
	*
	* @param sid the sid of this synchronization
	*/
	@Override
	public void setSid(long sid) {
		_synchronization.setSid(sid);
	}

	/**
	* Returns the suc sync of this synchronization.
	*
	* @return the suc sync of this synchronization
	*/
	@Override
	public boolean getSucSync() {
		return _synchronization.getSucSync();
	}

	/**
	* Returns <code>true</code> if this synchronization is suc sync.
	*
	* @return <code>true</code> if this synchronization is suc sync; <code>false</code> otherwise
	*/
	@Override
	public boolean isSucSync() {
		return _synchronization.isSucSync();
	}

	/**
	* Sets whether this synchronization is suc sync.
	*
	* @param sucSync the suc sync of this synchronization
	*/
	@Override
	public void setSucSync(boolean sucSync) {
		_synchronization.setSucSync(sucSync);
	}

	@Override
	public boolean isNew() {
		return _synchronization.isNew();
	}

	@Override
	public void setNew(boolean n) {
		_synchronization.setNew(n);
	}

	@Override
	public boolean isCachedModel() {
		return _synchronization.isCachedModel();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_synchronization.setCachedModel(cachedModel);
	}

	@Override
	public boolean isEscapedModel() {
		return _synchronization.isEscapedModel();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _synchronization.getPrimaryKeyObj();
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_synchronization.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _synchronization.getExpandoBridge();
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.model.BaseModel<?> baseModel) {
		_synchronization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_synchronization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_synchronization.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new SynchronizationWrapper((Synchronization)_synchronization.clone());
	}

	@Override
	public int compareTo(
		com.marcelmika.limsmuc.persistence.generated.model.Synchronization synchronization) {
		return _synchronization.compareTo(synchronization);
	}

	@Override
	public int hashCode() {
		return _synchronization.hashCode();
	}

	@Override
	public com.liferay.portal.model.CacheModel<com.marcelmika.limsmuc.persistence.generated.model.Synchronization> toCacheModel() {
		return _synchronization.toCacheModel();
	}

	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization toEscapedModel() {
		return new SynchronizationWrapper(_synchronization.toEscapedModel());
	}

	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization toUnescapedModel() {
		return new SynchronizationWrapper(_synchronization.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _synchronization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _synchronization.toXmlString();
	}

	@Override
	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_synchronization.persist();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SynchronizationWrapper)) {
			return false;
		}

		SynchronizationWrapper synchronizationWrapper = (SynchronizationWrapper)obj;

		if (Validator.equals(_synchronization,
					synchronizationWrapper._synchronization)) {
			return true;
		}

		return false;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedModel}
	 */
	public Synchronization getWrappedSynchronization() {
		return _synchronization;
	}

	@Override
	public Synchronization getWrappedModel() {
		return _synchronization;
	}

	@Override
	public void resetOriginalValues() {
		_synchronization.resetOriginalValues();
	}

	private Synchronization _synchronization;
}