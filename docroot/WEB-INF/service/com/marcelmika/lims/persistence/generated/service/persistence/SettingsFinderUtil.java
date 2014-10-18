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
public class SettingsFinderUtil {
	public static java.util.List<java.lang.Object[]> findAllGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, int start, int end)
		throws java.lang.Exception {
		return getFinder()
				   .findAllGroups(userId, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	public static java.util.List<java.lang.Object[]> findSitesGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, java.lang.String[] excludedSites,
		int start, int end) throws java.lang.Exception {
		return getFinder()
				   .findSitesGroups(userId, ignoreDefaultUser,
			ignoreDeactivatedUser, excludedSites, start, end);
	}

	public static java.util.List<java.lang.Object[]> findSocialGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, int[] relationTypes, int start, int end)
		throws java.lang.Exception {
		return getFinder()
				   .findSocialGroups(userId, ignoreDefaultUser,
			ignoreDeactivatedUser, relationTypes, start, end);
	}

	public static java.util.List<java.lang.Object[]> findUserGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, java.lang.String[] excludedGroups,
		int start, int end) throws java.lang.Exception {
		return getFinder()
				   .findUserGroups(userId, ignoreDefaultUser,
			ignoreDeactivatedUser, excludedGroups, start, end);
	}

	public static java.util.List<java.lang.Object[]> searchSitesBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedSites, int start, int end)
		throws java.lang.Exception {
		return getFinder()
				   .searchSitesBuddies(userId, searchQuery, ignoreDefaultUser,
			ignoreDeactivatedUser, excludedSites, start, end);
	}

	public static java.util.List<java.lang.Object[]> searchAllBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws java.lang.Exception {
		return getFinder()
				   .searchAllBuddies(userId, searchQuery, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	public static java.util.List<java.lang.Object[]> searchSocialBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		int[] relationTypes, int start, int end) throws java.lang.Exception {
		return getFinder()
				   .searchSocialBuddies(userId, searchQuery, ignoreDefaultUser,
			ignoreDeactivatedUser, relationTypes, start, end);
	}

	public static java.util.List<java.lang.Object[]> searchUserGroupsBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedGroups, int start, int end)
		throws java.lang.Exception {
		return getFinder()
				   .searchUserGroupsBuddies(userId, searchQuery,
			ignoreDefaultUser, ignoreDeactivatedUser, excludedGroups, start, end);
	}

	public static SettingsFinder getFinder() {
		if (_finder == null) {
			_finder = (SettingsFinder)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					SettingsFinder.class.getName());

			ReferenceRegistry.registerReference(SettingsFinderUtil.class,
				"_finder");
		}

		return _finder;
	}

	public void setFinder(SettingsFinder finder) {
		_finder = finder;

		ReferenceRegistry.registerReference(SettingsFinderUtil.class, "_finder");
	}

	private static SettingsFinder _finder;
}