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

package com.marcelmika.limsmuc.persistence.generated.service.persistence;

/**
 * @author Brian Wing Shun Chan
 */
public interface MessageFinder {
	public java.util.List<java.lang.Object[]> findAllMessages(
		java.lang.Long cid, java.lang.Integer pageSize)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Integer countAllMessages(java.lang.Long cid,
		com.marcelmika.limsmuc.persistence.generated.model.Message stopper)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Object[] firstMessage(java.lang.Long cid)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Object[] lastMessage(java.lang.Long cid)
		throws com.liferay.portal.kernel.exception.SystemException;
}