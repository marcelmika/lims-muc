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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * The base model interface for the Panel service. Represents a row in the &quot;Limsmuc_Panel&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link com.marcelmika.limsmuc.persistence.generated.model.impl.PanelModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link com.marcelmika.limsmuc.persistence.generated.model.impl.PanelImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see Panel
 * @see com.marcelmika.limsmuc.persistence.generated.model.impl.PanelImpl
 * @see com.marcelmika.limsmuc.persistence.generated.model.impl.PanelModelImpl
 * @generated
 */
public interface PanelModel extends BaseModel<Panel> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a panel model instance should use the {@link Panel} interface instead.
	 */

	/**
	 * Returns the primary key of this panel.
	 *
	 * @return the primary key of this panel
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this panel.
	 *
	 * @param primaryKey the primary key of this panel
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the pid of this panel.
	 *
	 * @return the pid of this panel
	 */
	public long getPid();

	/**
	 * Sets the pid of this panel.
	 *
	 * @param pid the pid of this panel
	 */
	public void setPid(long pid);

	/**
	 * Returns the user ID of this panel.
	 *
	 * @return the user ID of this panel
	 */
	public long getUserId();

	/**
	 * Sets the user ID of this panel.
	 *
	 * @param userId the user ID of this panel
	 */
	public void setUserId(long userId);

	/**
	 * Returns the user uuid of this panel.
	 *
	 * @return the user uuid of this panel
	 * @throws SystemException if a system exception occurred
	 */
	public String getUserUuid() throws SystemException;

	/**
	 * Sets the user uuid of this panel.
	 *
	 * @param userUuid the user uuid of this panel
	 */
	public void setUserUuid(String userUuid);

	/**
	 * Returns the active panel ID of this panel.
	 *
	 * @return the active panel ID of this panel
	 */
	@AutoEscape
	public String getActivePanelId();

	/**
	 * Sets the active panel ID of this panel.
	 *
	 * @param activePanelId the active panel ID of this panel
	 */
	public void setActivePanelId(String activePanelId);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(
		com.marcelmika.limsmuc.persistence.generated.model.Panel panel);

	@Override
	public int hashCode();

	@Override
	public CacheModel<com.marcelmika.limsmuc.persistence.generated.model.Panel> toCacheModel();

	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Panel toEscapedModel();

	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Panel toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}