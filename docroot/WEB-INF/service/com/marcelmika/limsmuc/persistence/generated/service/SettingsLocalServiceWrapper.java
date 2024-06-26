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

package com.marcelmika.limsmuc.persistence.generated.service;

import com.liferay.portal.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SettingsLocalService}.
 *
 * @author Brian Wing Shun Chan
 * @see SettingsLocalService
 * @generated
 */
public class SettingsLocalServiceWrapper implements SettingsLocalService,
	ServiceWrapper<SettingsLocalService> {
	public SettingsLocalServiceWrapper(
		SettingsLocalService settingsLocalService) {
		_settingsLocalService = settingsLocalService;
	}

	/**
	* Adds the settings to the database. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was added
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings addSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.addSettings(settings);
	}

	/**
	* Creates a new settings with the primary key. Does not add the settings to the database.
	*
	* @param sid the primary key for the new settings
	* @return the new settings
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings createSettings(
		long sid) {
		return _settingsLocalService.createSettings(sid);
	}

	/**
	* Deletes the settings with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the settings
	* @return the settings that was removed
	* @throws PortalException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings deleteSettings(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.deleteSettings(sid);
	}

	/**
	* Deletes the settings from the database. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was removed
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings deleteSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.deleteSettings(settings);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _settingsLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.SettingsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.SettingsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@Override
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.dynamicQueryCount(dynamicQuery, projection);
	}

	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings fetchSettings(
		long sid) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.fetchSettings(sid);
	}

	/**
	* Returns the settings with the primary key.
	*
	* @param sid the primary key of the settings
	* @return the settings
	* @throws PortalException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings getSettings(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.getSettings(sid);
	}

	@Override
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the settingses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.SettingsModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of settingses
	* @param end the upper bound of the range of settingses (not inclusive)
	* @return the range of settingses
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Settings> getSettingses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.getSettingses(start, end);
	}

	/**
	* Returns the number of settingses.
	*
	* @return the number of settingses
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public int getSettingsesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.getSettingsesCount();
	}

	/**
	* Updates the settings in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was updated
	* @throws SystemException if a system exception occurred
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings updateSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.updateSettings(settings);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _settingsLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_settingsLocalService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return _settingsLocalService.invokeMethod(name, parameterTypes,
			arguments);
	}

	/**
	* Returns settings related to the user whose id is given in the parameter
	*
	* @param userId id of the user whose setting you are requesting
	* @return Settings
	* @throws SystemException
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings getSettingsByUser(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.getSettingsByUser(userId);
	}

	/**
	* Fetches settings based on the user id
	*
	* @param userId long
	* @return Settings or null if nothing found
	* @throws SystemException
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings fetchByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.fetchByUserId(userId);
	}

	/**
	* Fetches settings based on the user id
	*
	* @param userId   long
	* @param useCache true if the cache should be used
	* @return Settings or null if nothing found
	* @throws SystemException
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings fetchByUserId(
		long userId, boolean useCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.fetchByUserId(userId, useCache);
	}

	/**
	* Creates new settings object
	*
	* @return Settings or null if nothing found
	* @throws SystemException
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings createSettings()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.createSettings();
	}

	/**
	* Saves settings object to persistence
	*
	* @param settings Settings model
	* @return Updated Settings
	* @throws SystemException
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings saveSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.saveSettings(settings);
	}

	/**
	* Updates user presence
	*
	* @param userId   id of the user whose presence should be updated
	* @param presence new value of the presence
	* @throws SystemException
	*/
	@Override
	public void changePresence(long userId, java.lang.String presence)
		throws com.liferay.portal.kernel.exception.SystemException {
		_settingsLocalService.changePresence(userId, presence);
	}

	/**
	* Returns all user settings that have changed their presence since the particular timestamp
	*
	* @param since position in a time from which we count the presence updates
	* @return list of settings
	* @throws SystemException
	*/
	@Override
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Settings> findByPresenceUpdatedSince(
		java.util.Date since)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.findByPresenceUpdatedSince(since);
	}

	/**
	* Updates user's connection
	*
	* @param userId      id of the user whose connection should be updated
	* @param isConnected true if the user connected flag should be set to true
	* @return Settings updated settings model
	* @throws SystemException
	*/
	@Override
	public com.marcelmika.limsmuc.persistence.generated.model.Settings updateConnection(
		java.lang.Long userId, boolean isConnected)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.updateConnection(userId, isConnected);
	}

	/**
	* Updates connections that have the connected at value below the threshold
	*/
	@Override
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Settings> updateAllConnections(
		int connectionThreshold)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.updateAllConnections(connectionThreshold);
	}

	/**
	* Returns a list of userIds of connected users
	*
	* @return List of ids
	*/
	@Override
	public java.util.List<java.lang.Long> getConnectedUsers()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.getConnectedUsers();
	}

	/**
	* Updates chat enabled value. If set to true the portlet is fully functional. If set to
	* false the chat will be disabled.
	*
	* @param userId  id of the user whose chat should be enabled/disabled
	* @param enabled if set to true the chat will be enabled. If set to false it will be disabled.
	* @throws SystemException
	*/
	@Override
	public void setChatEnabled(long userId, boolean enabled)
		throws com.liferay.portal.kernel.exception.SystemException {
		_settingsLocalService.setChatEnabled(userId, enabled);
	}

	/**
	* Counts all buddies in the system who have the settings
	*
	* @param userId                of excluded user
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @return number of users
	* @throws SystemException
	*/
	@Override
	public java.lang.Integer countAllUsers(java.lang.Long userId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.countAllUsers(userId, ignoreDefaultUser,
			ignoreDeactivatedUser);
	}

	/**
	* Returns all buddies in the system
	*
	* @param userId                of excluded user
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @param start                 value of the list
	* @param end                   value of the list
	* @return List of objects where each object contains user info
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> findAllGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.findAllGroups(userId, ignoreDefaultUser,
			ignoreDeactivatedUser, start, end);
	}

	/**
	* Returns all buddies in the system based on the search query
	*
	* @param userId                of excluded user
	* @param searchQuery           search string
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @param start                 value of the list
	* @param end                   value of the list
	* @return List of objects where each object contains user info
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> searchAllBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.searchAllBuddies(userId, searchQuery,
			ignoreDefaultUser, ignoreDeactivatedUser, start, end);
	}

	/**
	* Counts a number of users who belong to the particular site
	*
	* @param userId                id of the excluded user
	* @param groupId               of the group
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @return number of users
	* @throws SystemException
	*/
	@Override
	public java.lang.Integer countSitesGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.countSitesGroupUsers(userId, groupId,
			ignoreDefaultUser, ignoreDeactivatedUser);
	}

	/**
	* Returns sites groups ids where the user belongs
	*
	* @param userId        id of the user
	* @param excludedSites list of names of sites which should be excluded
	* @return List of objects where each object contains group name and user info
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> findSitesGroups(
		java.lang.Long userId, java.lang.String[] excludedSites)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.findSitesGroups(userId, excludedSites);
	}

	/**
	* Returns sites group and its user
	*
	* @param userId                which should be excluded from the list
	* @param groupId               id of the group
	* @param ignoreDefaultUser     boolean set to true if the default user should be excluded
	* @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
	* @param start                 value of the list
	* @param end                   value of the list
	* @return Group
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> readSitesGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.readSitesGroup(userId, groupId,
			ignoreDefaultUser, ignoreDeactivatedUser, start, end);
	}

	/**
	* Returns true if the user is a member of the sites group
	*
	* @param userId  id of the user
	* @param groupId id of the group
	* @return boolean
	* @throws SystemException
	*/
	@Override
	public boolean isMemberOfSitesGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.isMemberOfSitesGroup(userId, groupId);
	}

	/**
	* Returns all buddies in sites where the user participates based on the search query
	*
	* @param userId                of excluded user
	* @param searchQuery           search query string
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @param excludedSites         list of names of sites which should be excluded
	* @param start                 value of the list
	* @param end                   value of the list
	* @return List of objects where each object contains user info
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> searchSitesBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedSites, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.searchSitesBuddies(userId, searchQuery,
			ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end);
	}

	/**
	* Counts a number of users who belong to the particular social group
	*
	* @param userId                of excluded user
	* @param groupId               of the group
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @return number of users
	* @throws SystemException
	*/
	@Override
	public java.lang.Integer countSocialGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.countSocialGroupUsers(userId, groupId,
			ignoreDefaultUser, ignoreDeactivatedUser);
	}

	/**
	* Returns social groups ids where the user belongs
	*
	* @param userId        of the user whose social relations are we looking for
	* @param relationTypes an array of relation type codes that we are looking for
	* @return List objects where each object contains relation type and user info
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> findSocialGroups(
		java.lang.Long userId, int[] relationTypes)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.findSocialGroups(userId, relationTypes);
	}

	/**
	* Returns social group and their users based on the page parameter
	*
	* @param userId                which should be excluded from the list
	* @param groupId               id of the group
	* @param ignoreDefaultUser     boolean set to true if the default user should be excluded
	* @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
	* @param start                 value of the list
	* @param end                   value of the list
	* @return Group
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> readSocialGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.readSocialGroup(userId, groupId,
			ignoreDefaultUser, ignoreDeactivatedUser, start, end);
	}

	/**
	* Returns true if the user is a member of the social group
	*
	* @param userId  id of the user
	* @param groupId id of the group
	* @return boolean
	* @throws SystemException
	*/
	@Override
	public boolean isMemberOfSocialGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.isMemberOfSocialGroup(userId, groupId);
	}

	/**
	* Returns all user's social relations based on the search query
	*
	* @param userId                of the user whose social relations are we looking for
	* @param searchQuery           search query string
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @param relationTypes         an array of relation type codes that we are looking for
	* @param start                 value of the list
	* @param end                   value of the list
	* @return List of objects where each object contains user info
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> searchSocialBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		int[] relationTypes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.searchSocialBuddies(userId, searchQuery,
			ignoreDefaultUser, ignoreDeactivatedUser, relationTypes, start, end);
	}

	/**
	* Counts a number of users who belong to the particular user group
	*
	* @param userId                of excluded user
	* @param groupId               of the group
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @return number of users
	* @throws SystemException
	*/
	@Override
	public java.lang.Integer countUserGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.countUserGroupUsers(userId, groupId,
			ignoreDefaultUser, ignoreDeactivatedUser);
	}

	/**
	* Returns a list of user's groups
	*
	* @param userId         of the user whose groups are we looking for
	* @param excludedGroups list of names of groups which should be excluded
	* @return List of objects where each object contains group name and user info
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> findUserGroups(
		java.lang.Long userId, java.lang.String[] excludedGroups)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.findUserGroups(userId, excludedGroups);
	}

	/**
	* Returns user group and its users based on the page parameter
	*
	* @param userId                which should be excluded from the list
	* @param groupId               id of the group
	* @param ignoreDefaultUser     boolean set to true if the default user should be excluded
	* @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
	* @param start                 value of the list
	* @param end                   value of the list
	* @return Group
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> readUserGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.readUserGroup(userId, groupId,
			ignoreDefaultUser, ignoreDeactivatedUser, start, end);
	}

	/**
	* Returns true if the user is a member of the user group
	*
	* @param userId  id of the user
	* @param groupId id of the group
	* @return boolean
	* @throws SystemException
	*/
	@Override
	public boolean isMemberOfUserGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.isMemberOfUserGroup(userId, groupId);
	}

	/**
	* Returns a list of buddies. This list is made of all buddies based on the search query that are
	* in the same user group as the user.
	*
	* @param userId                which should be excluded from the list
	* @param searchQuery           search query string
	* @param ignoreDefaultUser     boolean set to true if the default user should be excluded
	* @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
	* @param excludedGroups        names of groups that should be excluded from the list of buddies
	* @param start                 of the list
	* @param end                   of the list
	* @return a list of buddies
	* @throws SystemException
	*/
	@Override
	public java.util.List<java.lang.Object[]> searchUserGroupsBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedGroups, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _settingsLocalService.searchUserGroupsBuddies(userId,
			searchQuery, ignoreDefaultUser, ignoreDeactivatedUser,
			excludedGroups, start, end);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public SettingsLocalService getWrappedSettingsLocalService() {
		return _settingsLocalService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedSettingsLocalService(
		SettingsLocalService settingsLocalService) {
		_settingsLocalService = settingsLocalService;
	}

	@Override
	public SettingsLocalService getWrappedService() {
		return _settingsLocalService;
	}

	@Override
	public void setWrappedService(SettingsLocalService settingsLocalService) {
		_settingsLocalService = settingsLocalService;
	}

	private SettingsLocalService _settingsLocalService;
}