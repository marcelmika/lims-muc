/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 * <p/>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p/>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.marcelmika.limsmuc.persistence.generated.service.impl;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.persistence.generated.model.Settings;
import com.marcelmika.limsmuc.persistence.generated.service.base.SettingsLocalServiceBaseImpl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * The implementation of the settings local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.SettingsLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil
 */
public class SettingsLocalServiceImpl extends SettingsLocalServiceBaseImpl {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SettingsLocalServiceImpl.class);

    /*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil} to access the settings local service.
	 */

    /**
     * Returns settings related to the user whose id is given in the parameter
     *
     * @param userId id of the user whose setting you are requesting
     * @return Settings
     * @throws SystemException
     */
    @Override
    public Settings getSettingsByUser(long userId) throws SystemException {
        // Find user settings
        Settings settings = settingsPersistence.fetchByUserId(userId);

        // No settings found => create a new one
        if (settings == null) {
            long sid = counterLocalService.increment();
            settings = settingsPersistence.create(sid);
            settings.setUserId(userId);
            settings.setChatEnabled(true);
            settings.setPresence("presence.online");
            settings.setPresenceUpdatedAt(Calendar.getInstance().getTime());
            settings.setConnectedAt(Calendar.getInstance().getTime());
            settingsPersistence.update(settings, true);
        }

        return settings;
    }

    /**
     * Fetches settings based on the user id
     *
     * @param userId long
     * @return Settings or null if nothing found
     * @throws SystemException
     */
    @Override
    public Settings fetchByUserId(long userId) throws SystemException {
        return settingsPersistence.fetchByUserId(userId);
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
    public Settings fetchByUserId(long userId, boolean useCache) throws SystemException {
        return settingsPersistence.fetchByUserId(userId, useCache);
    }

    /**
     * Creates new settings object
     *
     * @return Settings or null if nothing found
     * @throws SystemException
     */
    @Override
    public Settings createSettings() throws SystemException {
        return settingsPersistence.create(counterLocalService.increment());
    }

    /**
     * Saves settings object to persistence
     *
     * @param settings Settings model
     * @return Updated Settings
     * @throws SystemException
     */
    @Override
    public Settings saveSettings(Settings settings) throws SystemException {
        return settingsPersistence.update(settings, false);
    }

    /**
     * Updates user presence
     *
     * @param userId   id of the user whose presence should be updated
     * @param presence new value of the presence
     * @throws SystemException
     */
    @Override
    public void changePresence(long userId, String presence) throws SystemException {
        // Get user settings
        Settings settings = getSettingsByUser(userId);
        // Change presence
        if (settings != null) {
            // Update presence
            settings.setPresence(presence);
            // Save the time of change
            Calendar calendar = Calendar.getInstance();
            settings.setPresenceUpdatedAt(calendar.getTime());

            settingsPersistence.update(settings, true);
        }
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
    public Settings updateConnection(Long userId, boolean isConnected) throws SystemException {
        // Get user settings
        Settings settings = getSettingsByUser(userId);

        // Get current timestamp
        Date now = Calendar.getInstance().getTime();

        // If we change the settings connected flag we also need to update the presence updated at timestamp.
        // Otherwise the caching of groups wouldn't work since the groups are not refreshed until the
        // presence is updated.
        if (settings.getConnected() != isConnected) {
            settings.setPresenceUpdatedAt(now);
        }

        // The user is connected
        settings.setConnected(isConnected);
        // Update timestamp
        settings.setConnectedAt(now);

        // Save
        settingsPersistence.update(settings, false);

        return settings;
    }

    /**
     * Updates connections that have the connected at value below the threshold
     */
    @Override
    public List<Settings> updateAllConnections(int connectionThreshold) throws SystemException {

        // Check the input
        if (connectionThreshold < 1) {
            connectionThreshold = 1;
        }

        // Get current timestamp
        Date now = Calendar.getInstance().getTime();

        // Get threshold timestamp
        Calendar threshold = Calendar.getInstance();
        // Minus the connection threshold
        threshold.add(Calendar.MINUTE, -1 * (connectionThreshold));

        // Create dynamic query
        DynamicQuery query = DynamicQueryFactoryUtil.forClass(Settings.class);
        // Add the restriction that will find all connections that are older than one minute
        query.add(RestrictionsFactoryUtil.le("connectedAt", threshold.getTime()));
        query.add(RestrictionsFactoryUtil.eq("connected", true));

        // Get the results
        @SuppressWarnings("unchecked")
        List<Settings> results = dynamicQuery(query);

        // Set the connected flag to false for all found settings
        for (Settings setting : results) {
            setting.setConnected(false);
            setting.setPresenceUpdatedAt(now);
            settingsPersistence.update(setting, false);
        }

        return results;
    }

    /**
     * Returns a list of userIds of connected users
     *
     * @return List of ids
     */
    @Override
    public List<Long> getConnectedUsers() throws SystemException {
        // Create dynamic query
        DynamicQuery query = DynamicQueryFactoryUtil.forClass(Settings.class);
        // Add the restrictions that will find all connected user ids
        query.add(RestrictionsFactoryUtil.eq("connected", true));

        // Get the results
        @SuppressWarnings("unchecked")
        List<Settings> results = dynamicQuery(query);

        // Create a list of ids
        List<Long> connectedUsers = new LinkedList<Long>();

        // Map to list of ids
        for (Settings settings : results) {
            //
            connectedUsers.add(settings.getUserId());
        }

        return connectedUsers;
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
    public void setChatEnabled(long userId, boolean enabled) throws SystemException {
        // Get user settings
        Settings settings = getSettingsByUser(userId);
        // Change value
        if (settings != null) {
            settings.setChatEnabled(enabled);
            settingsPersistence.update(settings, true);
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////
    // ALL GROUP
    /////////////////////////////////////////////////////////////////////////////////////////////

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
    public Integer countAllUsers(Long userId,
                                 boolean ignoreDefaultUser,
                                 boolean ignoreDeactivatedUser) throws SystemException {
        // Count via settings finder
        return settingsFinder.countAllUsers(userId, ignoreDefaultUser, ignoreDeactivatedUser);
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
    public List<Object[]> findAllGroups(Long userId,
                                       boolean ignoreDefaultUser,
                                       boolean ignoreDeactivatedUser,
                                       int start,
                                       int end) throws SystemException {
        // Find via settings finder
        return settingsFinder.findAllGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );
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
    public List<Object[]> searchAllBuddies(Long userId,
                                           String searchQuery,
                                           boolean ignoreDefaultUser,
                                           boolean ignoreDeactivatedUser,
                                           int start,
                                           int end) throws SystemException {
        // Search via settings finder
        return settingsFinder.searchAllBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );
    }


    /////////////////////////////////////////////////////////////////////////////////////////////
    // SITES GROUP
    /////////////////////////////////////////////////////////////////////////////////////////////

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
    public Integer countSitesGroupUsers(Long userId,
                                        Long groupId,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser) throws SystemException {
        // Count via settings finder
        return settingsFinder.countSitesGroupUsers(userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser);
    }

    /**
     * Returns sites groups ids where the user belongs
     *
     * @param userId                id of the user
     * @param excludedSites         list of names of sites which should be excluded
     * @return List of objects where each object contains group name and user info
     * @throws SystemException
     */
    @Override
    public List<Object[]> findSitesGroups(Long userId, String[] excludedSites) throws SystemException {
        // Find via settings finder
        return settingsFinder.findSitesGroups(userId, excludedSites);
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
    public List<Object[]> readSitesGroup(Long userId,
                                         Long groupId,
                                         boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser,
                                         int start,
                                         int end) throws SystemException {
        // Find via settings finder
        return settingsFinder.readSitesGroup(
                userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );
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
    public List<Object[]> searchSitesBuddies(Long userId,
                                             String searchQuery,
                                             boolean ignoreDefaultUser,
                                             boolean ignoreDeactivatedUser,
                                             String[] excludedSites,
                                             int start,
                                             int end) throws SystemException {

        // Search via settings finder
        return settingsFinder.searchSitesBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
        );
    }


    /////////////////////////////////////////////////////////////////////////////////////////////
    // SOCIAL GROUP
    /////////////////////////////////////////////////////////////////////////////////////////////

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
    public Integer countSocialGroupUsers(Long userId,
                                         Long groupId,
                                         boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser) throws SystemException {
        // Count via settings finder
        return settingsFinder.countSocialGroupUsers(userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser);
    }

    /**
     * Returns social groups ids where the user belongs
     *
     * @param userId                of the user whose social relations are we looking for
     * @param relationTypes         an array of relation type codes that we are looking for
     * @return List objects where each object contains relation type and user info
     * @throws SystemException
     */
    @Override
    public List<Object[]> findSocialGroups(Long userId,
                                          int[] relationTypes) throws SystemException {
        // Find via settings finder
        return settingsFinder.findSocialGroups(userId, relationTypes);
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
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> readSocialGroup(Long userId,
                                          Long groupId,
                                          boolean ignoreDefaultUser,
                                          boolean ignoreDeactivatedUser,
                                          int start,
                                          int end) throws SystemException {
        // Read via settings finder
        return settingsFinder.readSocialGroup(
                userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );
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
    public List<Object[]> searchSocialBuddies(Long userId,
                                              String searchQuery,
                                              boolean ignoreDefaultUser,
                                              boolean ignoreDeactivatedUser,
                                              int[] relationTypes,
                                              int start,
                                              int end) throws SystemException {

        // Search via settings finder
        return settingsFinder.searchSocialBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, relationTypes, start, end
        );
    }


    /////////////////////////////////////////////////////////////////////////////////////////////
    // USER GROUP
    /////////////////////////////////////////////////////////////////////////////////////////////

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
    public Integer countUserGroupUsers(Long userId,
                                       Long groupId,
                                       boolean ignoreDefaultUser,
                                       boolean ignoreDeactivatedUser) throws SystemException {
        // Count via settings finder
        return settingsFinder.countUserGroupUsers(userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser);
    }

    /**
     * Returns a list of user's groups
     *
     * @param userId                of the user whose groups are we looking for
     * @param excludedGroups        list of names of groups which should be excluded
     * @return List of objects where each object contains group name and user info
     * @throws SystemException
     */
    @Override
    public List<Object[]> findUserGroups(Long userId,
                                        String[] excludedGroups) throws SystemException {
        // Find via settings finder
        return settingsFinder.findUserGroups(userId, excludedGroups);
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
    public List<Object[]> readUserGroup(Long userId,
                                        Long groupId,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser,
                                        int start,
                                        int end) throws SystemException {
        // Find via settings finder
        return settingsFinder.readUserGroup(
                userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );
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
    public List<Object[]> searchUserGroupsBuddies(Long userId,
                                                  String searchQuery,
                                                  boolean ignoreDefaultUser,
                                                  boolean ignoreDeactivatedUser,
                                                  String[] excludedGroups,
                                                  int start,
                                                  int end) throws SystemException {

        // Search via settings finder
        return settingsFinder.searchUserGroupsBuddies(
                userId, searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedGroups, start, end
        );
    }
}