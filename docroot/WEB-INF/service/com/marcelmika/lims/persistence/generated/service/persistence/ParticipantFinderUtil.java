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
public class ParticipantFinderUtil {
	public static java.util.List<java.lang.Object[]> findParticipatedConversations(
		java.lang.Long participantId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findParticipatedConversations(participantId, start, end);
	}

	public static java.lang.Integer countParticipatedConversations(
		java.lang.Long participantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countParticipatedConversations(participantId);
	}

	public static ParticipantFinder getFinder() {
		if (_finder == null) {
			_finder = (ParticipantFinder)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					ParticipantFinder.class.getName());

			ReferenceRegistry.registerReference(ParticipantFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(ParticipantFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(ParticipantFinderUtil.class,
			"_finder");
	}

	private static ParticipantFinder _finder;
}