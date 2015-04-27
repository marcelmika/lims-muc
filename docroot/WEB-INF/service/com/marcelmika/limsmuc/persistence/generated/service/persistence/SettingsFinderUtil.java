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
public class SettingsFinderUtil {
	public static java.lang.Integer countAllUsers(java.lang.Long userId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countAllUsers(userId, ignoreDefaultUser,
			ignoreDeactivatedUser);
	}

	public static java.util.List<java.lang.Object[]> findAllGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findAllGroups(userId, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	public static java.util.List<java.lang.Object[]> searchAllBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .searchAllBuddies(userId, searchQuery, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	public static java.lang.Integer countSitesGroupUsers(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countSitesGroupUsers(userId, groupId, ignoreDefaultUser,
			ignoreDeactivatedUser);
	}

	public static java.util.List<java.lang.Object[]> findSitesGroups(
		java.lang.Long userId, java.lang.String[] excludedSites)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findSitesGroups(userId, excludedSites);
	}

	public static java.util.List<java.lang.Object[]> readSitesGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .readSitesGroup(userId, groupId, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	public static boolean isMemberOfSitesGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().isMemberOfSitesGroup(userId, groupId);
	}

	public static java.util.List<java.lang.Object[]> searchSitesBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedSites, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .searchSitesBuddies(userId, searchQuery, ignoreDefaultUser,
			ignoreDeactivatedUser, excludedSites, start, end);
	}

	public static java.lang.Integer countSocialGroupUsers(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countSocialGroupUsers(userId, groupId, ignoreDefaultUser,
			ignoreDeactivatedUser);
	}

	public static java.util.List<java.lang.Object[]> findSocialGroups(
		java.lang.Long userId, int[] relationTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findSocialGroups(userId, relationTypes);
	}

	public static java.util.List<java.lang.Object[]> readSocialGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .readSocialGroup(userId, groupId, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	public static boolean isMemberOfSocialGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().isMemberOfSocialGroup(userId, groupId);
	}

	public static java.util.List<java.lang.Object[]> searchSocialBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		int[] relationTypes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .searchSocialBuddies(userId, searchQuery, ignoreDefaultUser,
			ignoreDeactivatedUser, relationTypes, start, end);
	}

	public static java.lang.Integer countUserGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countUserGroupUsers(userId, groupId, ignoreDefaultUser,
			ignoreDeactivatedUser);
	}

	public static java.util.List<java.lang.Object[]> findUserGroups(
		java.lang.Long userId, java.lang.String[] excludedGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findUserGroups(userId, excludedGroups);
	}

	public static java.util.List<java.lang.Object[]> readUserGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .readUserGroup(userId, groupId, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	public static java.util.List<java.lang.Object[]> searchUserGroupsBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedGroups, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .searchUserGroupsBuddies(userId, searchQuery,
			ignoreDefaultUser, ignoreDeactivatedUser, excludedGroups, start, end);
	}

	public static SettingsFinder getFinder() {
		if (_finder == null) {
			_finder = (SettingsFinder)PortletBeanLocatorUtil.locate(com.marcelmika.limsmuc.persistence.generated.service.ClpSerializer.getServletContextName(),
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