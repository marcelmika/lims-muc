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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for Settings. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SettingsLocalServiceUtil
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.SettingsLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.impl.SettingsLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SettingsLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SettingsLocalServiceUtil} to access the settings local service. Add custom service methods to {@link com.marcelmika.limsmuc.persistence.generated.service.impl.SettingsLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the settings to the database. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was added
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings addSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new settings with the primary key. Does not add the settings to the database.
	*
	* @param sid the primary key for the new settings
	* @return the new settings
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Settings createSettings(
		long sid);

	/**
	* Deletes the settings with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the settings
	* @return the settings that was removed
	* @throws PortalException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings deleteSettings(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the settings from the database. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was removed
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings deleteSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings fetchSettings(
		long sid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the settings with the primary key.
	*
	* @param sid the primary key of the settings
	* @return the settings
	* @throws PortalException if a settings with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings getSettings(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Settings> getSettingses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of settingses.
	*
	* @return the number of settingses
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSettingsesCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the settings in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param settings the settings
	* @return the settings that was updated
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings updateSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	/**
	* Returns settings related to the user whose id is given in the parameter
	*
	* @param userId id of the user whose setting you are requesting
	* @return Settings
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings getSettingsByUser(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Fetches settings based on the user id
	*
	* @param userId long
	* @return Settings or null if nothing found
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings fetchByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Fetches settings based on the user id
	*
	* @param userId   long
	* @param useCache true if the cache should be used
	* @return Settings or null if nothing found
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Settings fetchByUserId(
		long userId, boolean useCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates new settings object
	*
	* @return Settings or null if nothing found
	* @throws SystemException
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Settings createSettings()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Saves settings object to persistence
	*
	* @param settings Settings model
	* @return Updated Settings
	* @throws SystemException
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Settings saveSettings(
		com.marcelmika.limsmuc.persistence.generated.model.Settings settings)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates user presence
	*
	* @param userId   id of the user whose presence should be updated
	* @param presence new value of the presence
	* @throws SystemException
	*/
	public void changePresence(long userId, java.lang.String presence)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all user settings that have changed their presence since the particular timestamp
	*
	* @param since position in a time from which we count the presence updates
	* @return list of settings
	* @throws SystemException
	*/
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Settings> findByPresenceUpdatedSince(
		java.util.Date since)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates user's connection
	*
	* @param userId      id of the user whose connection should be updated
	* @param isConnected true if the user connected flag should be set to true
	* @return Settings updated settings model
	* @throws SystemException
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Settings updateConnection(
		java.lang.Long userId, boolean isConnected)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates connections that have the connected at value below the threshold
	*/
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Settings> updateAllConnections(
		int connectionThreshold)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a list of userIds of connected users
	*
	* @return List of ids
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Long> getConnectedUsers()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates chat enabled value. If set to true the portlet is fully functional. If set to
	* false the chat will be disabled.
	*
	* @param userId  id of the user whose chat should be enabled/disabled
	* @param enabled if set to true the chat will be enabled. If set to false it will be disabled.
	* @throws SystemException
	*/
	public void setChatEnabled(long userId, boolean enabled)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all buddies in the system who have the settings
	*
	* @param userId                of excluded user
	* @param ignoreDefaultUser     true if default users should be ignored
	* @param ignoreDeactivatedUser true if deactivated users should be ignored
	* @return number of users
	* @throws SystemException
	*/
	public java.lang.Integer countAllUsers(java.lang.Long userId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<java.lang.Object[]> findAllGroups(
		java.lang.Long userId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object[]> searchAllBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.lang.Integer countSitesGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns sites groups ids where the user belongs
	*
	* @param userId        id of the user
	* @param excludedSites list of names of sites which should be excluded
	* @return List of objects where each object contains group name and user info
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findSitesGroups(
		java.lang.Long userId, java.lang.String[] excludedSites)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<java.lang.Object[]> readSitesGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns true if the user is a member of the sites group
	*
	* @param userId  id of the user
	* @param groupId id of the group
	* @return boolean
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isMemberOfSitesGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object[]> searchSitesBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedSites, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.lang.Integer countSocialGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns social groups ids where the user belongs
	*
	* @param userId        of the user whose social relations are we looking for
	* @param relationTypes an array of relation type codes that we are looking for
	* @return List objects where each object contains relation type and user info
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findSocialGroups(
		java.lang.Long userId, int[] relationTypes)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<java.lang.Object[]> readSocialGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns true if the user is a member of the social group
	*
	* @param userId  id of the user
	* @param groupId id of the group
	* @return boolean
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isMemberOfSocialGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object[]> searchSocialBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		int[] relationTypes, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.lang.Integer countUserGroupUsers(java.lang.Long userId,
		java.lang.Long groupId, boolean ignoreDefaultUser,
		boolean ignoreDeactivatedUser)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a list of user's groups
	*
	* @param userId         of the user whose groups are we looking for
	* @param excludedGroups list of names of groups which should be excluded
	* @return List of objects where each object contains group name and user info
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findUserGroups(
		java.lang.Long userId, java.lang.String[] excludedGroups)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<java.lang.Object[]> readUserGroup(
		java.lang.Long userId, java.lang.Long groupId,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns true if the user is a member of the user group
	*
	* @param userId  id of the user
	* @param groupId id of the group
	* @return boolean
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean isMemberOfUserGroup(java.lang.Long userId,
		java.lang.Long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<java.lang.Object[]> searchUserGroupsBuddies(
		java.lang.Long userId, java.lang.String searchQuery,
		boolean ignoreDefaultUser, boolean ignoreDeactivatedUser,
		java.lang.String[] excludedGroups, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;
}