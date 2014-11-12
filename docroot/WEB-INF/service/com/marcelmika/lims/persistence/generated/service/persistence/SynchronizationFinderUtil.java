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
public class SynchronizationFinderUtil {
	public static java.util.List<java.lang.Object[]> findSUCSettings_1_2_0(
		int start, int end) throws java.lang.Exception {
		return getFinder().findSUCSettings_1_2_0(start, end);
	}

	public static java.lang.Integer countSUCSettings_1_2_0()
		throws java.lang.Exception {
		return getFinder().countSUCSettings_1_2_0();
	}

	public static SynchronizationFinder getFinder() {
		if (_finder == null) {
			_finder = (SynchronizationFinder)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
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