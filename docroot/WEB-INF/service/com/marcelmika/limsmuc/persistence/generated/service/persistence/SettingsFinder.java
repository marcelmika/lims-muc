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
public interface SettingsFinder {
	public java.lang.Integer countAllUsers(java.lang.Long userId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> findAllGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> searchAllBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Integer countSitesGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> findSitesGroups(
		java.lang.Long userId, java.lang.String[] excludedSites)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> readSitesGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isMemberOfSitesGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> searchSitesBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedSites, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Integer countSocialGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> findSocialGroups(
		java.lang.Long userId, int[] relationTypes)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> readSocialGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isMemberOfSocialGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> searchSocialBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		int[] relationTypes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.Integer countUserGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> findUserGroups(
		java.lang.Long userId, java.lang.String[] excludedGroups)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> readUserGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isMemberOfUserGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<java.lang.Object[]> searchUserGroupsBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedGroups, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;
}