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

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class MessageFinderUtil {
	public static java.util.List<java.lang.Object[]> findAllMessages(
		java.lang.Long cid, java.lang.Integer pageSize, java.lang.Long stopperId)
		throws java.lang.Exception {
		return getFinder().findAllMessages(cid, pageSize, stopperId);
	}

	public static MessageFinder getFinder() {
		if (_finder == null) {
			_finder = (MessageFinder)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					MessageFinder.class.getName());

			ReferenceRegistry.registerReference(MessageFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(MessageFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(MessageFinderUtil.class, "_finder");
	}

	private static MessageFinder _finder;
}