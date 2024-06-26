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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * @author Brian Wing Shun Chan
 */
public class SynchronizationFinderUtil {
	public static java.util.List<java.lang.Object[]> findSettings(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findSettings(version, start, end);
	}

	public static java.util.List<java.lang.Object[]> findPanel(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findPanel(version, start, end);
	}

	public static java.util.List<java.lang.Object[]> findConversation(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findConversation(version, start, end);
	}

	public static java.util.List<java.lang.Object[]> findParticipant(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findParticipant(version, start, end);
	}

	public static java.util.List<java.lang.Object[]> findMessage(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findMessage(version, start, end);
	}

	public static SynchronizationFinder getFinder() {
		if (_finder == null) {
			_finder = (SynchronizationFinder)PortletBeanLocatorUtil.locate(com.marcelmika.limsmuc.persistence.generated.service.ClpSerializer.getServletContextName(),
					SynchronizationFinder.class.getName());

			ReferenceRegistry.registerReference(SynchronizationFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SynchronizationFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SynchronizationFinderUtil.class,
			"_finder");
	}

	private static SynchronizationFinder _finder;
}