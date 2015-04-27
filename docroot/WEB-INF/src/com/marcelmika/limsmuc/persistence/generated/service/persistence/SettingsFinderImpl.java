/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.generated.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.limsmuc.persistence.generated.model.Settings;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/4/14
 * Time: 10:19 AM
 */
public class SettingsFinderImpl extends BasePersistenceImpl<Settings> implements SettingsFinder {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SettingsFinderImpl.class);

    // Count users SQL
    private static final String COUNT_ALL_USERS = SettingsFinder.class.getName() + ".countAllUsers";
    private static final String COUNT_SITES_GROUP_USERS = SettingsFinder.class.getName() + ".countSitesGroupUsers";
    private static final String COUNT_SOCIAL_GROUP_USERS = SettingsFinder.class.getName() + ".countSocialGroupUsers";
    private static final String COUNT_USER_GROUP_USERS = SettingsFinder.class.getName() + ".countUserGroupUsers";

    // Read users SQL
    private static final String READ_SITES_GROUP = SettingsFinder.class.getName() + ".readSitesGroup";
    private static final String READ_SOCIAL_GROUP = SettingsFinder.class.getName() + ".readSocialGroup";
    private static final String READ_USER_GROUP = SettingsFinder.class.getName() + ".readUserGroup";

    // Find users SQL
    private static final String FIND_ALL_USERS = SettingsFinder.class.getName() + ".findAllUsers";
    private static final String FIND_BY_SITES_GROUPS = SettingsFinder.class.getName() + ".findBySitesGroups";
    private static final String FIND_BY_SOCIAL_GROUPS = SettingsFinder.class.getName() + ".findBySocialGroups";
    private static final String FIND_BY_USER_GROUPS = SettingsFinder.class.getName() + ".findByUserGroups";

    // Is Member SQL
    private static final String IS_MEMBER_OF_SITES_GROUP = SettingsFinder.class.getName() + ".isMemberOfSitesGroup";

    // Search users SQL
    private static final String SEARCH_ALL_USERS = SettingsFinder.class.getName() + ".searchAllUsers";
    private static final String SEARCH_BY_SITES_GROUPS = SettingsFinder.class.getName() + ".searchBySitesGroups";
    private static final String SEARCH_BY_SOCIAL_GROUPS = SettingsFinder.class.getName() + ".searchBySocialGroups";
    private static final String SEARCH_BY_USER_GROUPS = SettingsFinder.class.getName() + ".searchByUserGroups";

    // Search columns count
    private static final Integer SEARCH_COLUMN_COUNT = 3;

    // Placeholders
    private static final String PLACEHOLDER_DEFAULT_USER = "[$DEFAULT_USER$]";
    private static final String PLACEHOLDER_DEACTIVATED_USER = "[$DEACTIVATED_USER$]";
    private static final String PLACEHOLDER_USERS_GROUPS_JOIN = "[$USERS_GROUPS_JOIN$]";
    private static final String PLACEHOLDER_USERS_GROUPS_WHERE = "[$USERS_GROUPS_WHERE$]";
    private static final String PLACEHOLDER_SOCIAL_RELATION_TYPES = "[$SOCIAL_RELATION_TYPES$]";


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ALL GROUP
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Counts all users except the one given in the parameter and their settings
     *
     * @param userId                of excluded user
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return number of all users
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Integer> is unchecked
    public Integer countAllUsers(Long userId,
                                 boolean ignoreDefaultUser,
                                 boolean ignoreDeactivatedUser) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getCountAllUsersSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userCount", Type.INTEGER);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);

            // Get the result
            List<Integer> result = (List<Integer>) QueryUtil.list(query, getDialect(), 0, 1);

            // Return the count
            return result.get(0);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Finds all users except the one given in the parameter and their settings
     *
     * @param userId                of excluded user
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param start                 value of the list
     * @param end                   value of the list
     * @return List of objects where each object contains user info
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findAllGroups(Long userId,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser,
                                        int start,
                                        int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getFindAllUsersSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }


    /**
     * Finds all users based on the search query except the one given in the parameter and their settings.
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
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> searchAllBuddies(Long userId,
                                           String searchQuery,
                                           boolean ignoreDefaultUser,
                                           boolean ignoreDeactivatedUser,
                                           int start,
                                           int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getSearchAllUsersSQL(searchQuery, ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);

            // Search query needs to be lowered
            String lowerCaseSearchQuery = searchQuery.toLowerCase();

            // Create the like statement for search query
            String likeStatement = "%" + lowerCaseSearchQuery + "%";

            // There are 3 possible columns we are about to search
            // (first name, middle name, last name)
            for (int i = 0; i < SEARCH_COLUMN_COUNT; i++) {
                queryPos.add(likeStatement);
                queryPos.add(lowerCaseSearchQuery);
            }

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SITES GROUP
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Counts a number of users who belong to the particular site
     *
     * @param userId                of excluded user
     * @param groupId               of the group
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return number of users
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Integer> is unchecked
    public Integer countSitesGroupUsers(Long userId,
                                        Long groupId,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getCountSitesGroupUsersSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userCount", Type.INTEGER);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            queryPos.add(groupId);

            // Get the result
            List<Integer> result = (List<Integer>) QueryUtil.list(query, getDialect(), 0, 1);

            // Return the count
            return result.get(0);

        } finally {
            // Session needs to be closed at the end
            closeSession(session);
        }
    }

    /**
     * Returns all groups where the user participates
     *
     * @param userId        of excluded user
     * @param excludedSites list of names of sites which should be excluded
     * @return List of objects where each object contains group name and user info
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findSitesGroups(Long userId, String[] excludedSites) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getFindBySitesGroupsSQL(excludedSites);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("groupId", Type.LONG);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            if (excludedSites.length > 0) {
                queryPos.add(excludedSites);
            }
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), 0, 100);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Returns group and their users based on the page parameter
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
    public List<Object[]> readSitesGroup(Long userId,
                                         Long groupId,
                                         boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser,
                                         int start,
                                         int end) throws SystemException {

        Session session = null;

        try {

            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getReadSitesGroupSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("groupId", Type.LONG);
            query.addScalar("groupName", Type.STRING);
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(groupId);
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
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
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public boolean isMemberOfSitesGroup(Long userId, Long groupId) throws SystemException {

        Session session = null;

        try {

            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getIsMemberOfSitesGroupSQL();

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now w need to map types to columns
            query.addScalar("userId", Type.LONG);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(groupId);
            queryPos.add(userId);

            // Return the result
            List<Object[]> result = (List<Object[]>) QueryUtil.list(query, getDialect(), 0, 1);

            // If there user was found (is the member of the group) the list will contain
            // one element. Otherwise, it will be empty.
            return (result.size() > 0);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Finds all users based on the search query except the one given in the parameter and their settings.
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
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> searchSitesBuddies(Long userId,
                                             String searchQuery,
                                             boolean ignoreDefaultUser,
                                             boolean ignoreDeactivatedUser,
                                             String[] excludedSites,
                                             int start,
                                             int end) throws SystemException {
        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getSearchSitesBuddiesSQL(
                    searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites
            );

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            if (excludedSites.length > 0) {
                queryPos.add(excludedSites);
            }
            queryPos.add(userId);

            // Search query needs to be lowered
            String lowerCaseSearchQuery = searchQuery.toLowerCase();

            // Create the like statement for search query
            String likeStatement = "%" + lowerCaseSearchQuery + "%";

            // There are 3 possible columns we are about to search
            // (first name, middle name, last name)
            for (int i = 0; i < SEARCH_COLUMN_COUNT; i++) {
                queryPos.add(likeStatement);
                queryPos.add(lowerCaseSearchQuery);
            }

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SOCIAL GROUP
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    @SuppressWarnings("unchecked") // Cast List<Integer> is unchecked
    public Integer countSocialGroupUsers(Long userId,
                                         Long groupId,
                                         boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getCountSocialGroupUsersSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userCount", Type.INTEGER);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            queryPos.add(groupId);
            queryPos.add(userId);

            // Get the result
            List<Integer> result = (List<Integer>) QueryUtil.list(query, getDialect(), 0, 1);

            // Return the count
            return result.get(0);

        } finally {
            // Session needs to be closed at the end
            closeSession(session);
        }
    }

    /**
     * Returns all user's social relations
     *
     * @param userId        of the user whose social relations are we looking for
     * @param relationTypes an array of relation type codes that we are looking for
     * @return List objects where each object contains relation type and user info
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findSocialGroups(Long userId,
                                           int[] relationTypes) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getFindSocialGroupsSQL(relationTypes);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("groupId", Type.LONG);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            if (relationTypes.length > 0) {
                queryPos.add(relationTypes);
            }
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), 0, 100);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
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

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getReadSocialGroupSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("groupId", Type.LONG);
            query.addScalar("relationType", Type.INTEGER);
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            queryPos.add(groupId);
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
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
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> searchSocialBuddies(Long userId,
                                              String searchQuery,
                                              boolean ignoreDefaultUser,
                                              boolean ignoreDeactivatedUser,
                                              int[] relationTypes,
                                              int start,
                                              int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getSearchSocialBuddiesSQL(
                    searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, relationTypes
            );

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            if (relationTypes.length > 0) {
                queryPos.add(relationTypes);
            }
            queryPos.add(userId);

            // Search query needs to be lowered
            String lowerCaseSearchQuery = searchQuery.toLowerCase();

            // Create the like statement for search query
            String likeStatement = "%" + lowerCaseSearchQuery + "%";

            // There are 3 possible columns we are about to search
            // (first name, middle name, last name)
            for (int i = 0; i < SEARCH_COLUMN_COUNT; i++) {
                queryPos.add(likeStatement);
                queryPos.add(lowerCaseSearchQuery);
            }

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // USER GROUP
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    @SuppressWarnings("unchecked") // Cast List<Integer> is unchecked
    public Integer countUserGroupUsers(Long userId,
                                       Long groupId,
                                       boolean ignoreDefaultUser,
                                       boolean ignoreDeactivatedUser) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getCountUserGroupUsersSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userCount", Type.INTEGER);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            queryPos.add(groupId);

            // Get the result
            List<Integer> result = (List<Integer>) QueryUtil.list(query, getDialect(), 0, 1);

            // Return the count
            return result.get(0);

        } finally {
            // Session needs to be closed at the end
            closeSession(session);
        }
    }

    /**
     * Returns all user groups where the user belongs
     *
     * @param userId         of the user whose social relations are we looking for
     * @param excludedGroups list of names of groups which should be excluded
     * @return List objects where each object contains group name and user info
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findUserGroups(Long userId, String[] excludedGroups) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getFindByUserGroupsSQL(excludedGroups);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("groupId", Type.LONG);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            if (excludedGroups.length > 0) {
                queryPos.add(excludedGroups);
            }
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), 0, 100);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Returns user group and their users based on the page parameter
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
    public List<Object[]> readUserGroup(Long userId,
                                        Long groupId,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser,
                                        int start,
                                        int end) throws SystemException {

        Session session = null;

        try {

            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getReadUserGroupSQL(ignoreDefaultUser, ignoreDeactivatedUser);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            query.addScalar("groupId", Type.LONG);
            query.addScalar("groupName", Type.STRING);
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(groupId);
            queryPos.add(userId);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
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
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> searchUserGroupsBuddies(Long userId,
                                                  String searchQuery,
                                                  boolean ignoreDefaultUser,
                                                  boolean ignoreDeactivatedUser,
                                                  String[] excludedGroups,
                                                  int start,
                                                  int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL
            String sql = getSearchUserGroupsSQL(
                    searchQuery, ignoreDefaultUser, ignoreDeactivatedUser, excludedGroups
            );

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
            query.addScalar("screenName", Type.STRING);
            query.addScalar("firstName", Type.STRING);
            query.addScalar("middleName", Type.STRING);
            query.addScalar("lastName", Type.STRING);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.CALENDAR);
            query.addScalar("connected", Type.BOOLEAN);
            query.addScalar("connectedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(userId);
            if (excludedGroups.length > 0) {
                queryPos.add(excludedGroups);
            }
            queryPos.add(userId);

            // Search query needs to be lowered
            String lowerCaseSearchQuery = searchQuery.toLowerCase();

            // Create the like statement for search query
            String likeStatement = "%" + lowerCaseSearchQuery + "%";

            // There are 3 possible columns we are about to search
            // (first name, middle name, last name)
            for (int i = 0; i < SEARCH_COLUMN_COUNT; i++) {
                queryPos.add(likeStatement);
                queryPos.add(lowerCaseSearchQuery);
            }

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // PRIVATE METHODS
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Generates SQL for count all users query
     *
     * @param ignoreDefaultUser     determines if the default user should be ignored
     * @param ignoreDeactivatedUser determines if the deactivated user should be ignored
     * @return SQL string for count all users query
     */
    private String getCountAllUsersSQL(boolean ignoreDefaultUser,
                                       boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(COUNT_ALL_USERS);

        // Add ignored users queries if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }

    /**
     * Generates SQL for find all users query
     *
     * @param ignoreDefaultUser     determines if the default user should be ignored
     * @param ignoreDeactivatedUser determines if the deactivated user should be ignored
     * @return SQL string for find all users query
     */
    private String getFindAllUsersSQL(boolean ignoreDefaultUser,
                                      boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(FIND_ALL_USERS);

        // Add ignored users queries if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }

    /**
     * Generates SQL for search all users query
     *
     * @param searchQuery           search keyword
     * @param ignoreDefaultUser     determines if the default user should be ignored
     * @param ignoreDeactivatedUser determines if the deactivated user should be ignored
     * @return SQL string for search all users query
     */
    private String getSearchAllUsersSQL(String searchQuery,
                                        boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(SEARCH_ALL_USERS);

        // Replace and_or_null operator, false means that this is a conjunction
        // https://www.liferay.com/community/wiki/-/wiki/Main/Custom+queries+in+Liferay
        sql = CustomSQLUtil.replaceAndOperator(sql, false);

        // Add ignored users queries if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);
        sql = addSearchParametersToSql(sql, searchQuery);

        return sql;
    }

    /**
     * Generates SQL for search sites buddies query
     *
     * @param searchQuery           search keyword
     * @param ignoreDefaultUser     determines if the default user should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param excludedSites         name of sites that should be excluded from the query
     * @return SQL string for search sites query
     */
    private String getSearchSitesBuddiesSQL(String searchQuery,
                                            boolean ignoreDefaultUser,
                                            boolean ignoreDeactivatedUser,
                                            String[] excludedSites) {

        // Get custom sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(SEARCH_BY_SITES_GROUPS);

        // Replace and_or_null operator, false means that this is a conjunction
        // https://www.liferay.com/community/wiki/-/wiki/Main/Custom+queries+in+Liferay
        sql = CustomSQLUtil.replaceAndOperator(sql, false);

        // Add ignored users queries if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);
        sql = addSearchParametersToSql(sql, searchQuery);
        sql = addExcludedSitesToSQL(sql, excludedSites);

        return sql;
    }

    /**
     * Generates SQL for search by social buddies query
     *
     * @param searchQuery           search keyword
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param types                 relation types that should be counted in
     * @return SQL string for find by sites groups query
     */
    private String getSearchSocialBuddiesSQL(String searchQuery,
                                             boolean ignoreDefaultUser,
                                             boolean ignoreDeactivatedUser,
                                             int[] types) {

        // Get custom sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(SEARCH_BY_SOCIAL_GROUPS);

        // Replace and_or_null operator, false means that this is a conjunction
        // https://www.liferay.com/community/wiki/-/wiki/Main/Custom+queries+in+Liferay
        sql = CustomSQLUtil.replaceAndOperator(sql, false);

        // Add ignored users queries if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);
        sql = addSearchParametersToSql(sql, searchQuery);
        sql = addSocialRelationTypesToSQL(sql, types);

        return sql;
    }

    /**
     * Generates SQL for count user group users query
     *
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return SQL string for the query
     */
    private String getCountUserGroupUsersSQL(boolean ignoreDefaultUser,
                                             boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(COUNT_USER_GROUP_USERS);

        // Add ignore default user query if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }


    /**
     * Generates SQL for count sites group users query
     *
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return SQL string for the query
     */
    private String getCountSocialGroupUsersSQL(boolean ignoreDefaultUser,
                                               boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(COUNT_SOCIAL_GROUP_USERS);

        // Add ignore default user query if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }

    /**
     * Generates SQL for count sites group users query
     *
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return SQL string for the query
     */
    private String getCountSitesGroupUsersSQL(boolean ignoreDefaultUser,
                                              boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(COUNT_SITES_GROUP_USERS);

        // Add ignore default user query if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }

    /**
     * Generates SQL for find by sites groups query
     *
     * @param excludedSites names of groups that should be excluded from the query
     * @return SQL string for find by sites groups query
     */
    private String getFindBySitesGroupsSQL(String[] excludedSites) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(FIND_BY_SITES_GROUPS);

        // Map properties to sql
        sql = addExcludedSitesToSQL(sql, excludedSites);

        return sql;
    }

    /**
     * Generates SQL for read sites group query
     *
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return SQL string for read sites group query
     */
    private String getReadSitesGroupSQL(boolean ignoreDefaultUser,
                                        boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(READ_SITES_GROUP);

        // Map properties to sql
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }

    /**
     * Generates SQL for is member of sites group query
     *
     * @return SQL string for the query
     */
    private String getIsMemberOfSitesGroupSQL() {

        // Get custom query sql (check /src/custom-sql/default.xml)
        return CustomSQLUtil.get(IS_MEMBER_OF_SITES_GROUP);
    }

    /**
     * Generates SQL for read social group query
     *
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return SQL string for read user group query
     */
    private String getReadSocialGroupSQL(boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(READ_SOCIAL_GROUP);

        // Map properties to sql
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }

    /**
     * Generates SQL for read user group query
     *
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @return SQL string for read user group query
     */
    private String getReadUserGroupSQL(boolean ignoreDefaultUser,
                                       boolean ignoreDeactivatedUser) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(READ_USER_GROUP);

        // Map properties to sql
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);

        return sql;
    }

    /**
     * Generates SQL for find by social groups query
     *
     * @param types relation types that should be counted in
     * @return SQL string for find by social groups query
     */
    private String getFindSocialGroupsSQL(int[] types) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(FIND_BY_SOCIAL_GROUPS);

        // Add ignore user queries if needed
        sql = addSocialRelationTypesToSQL(sql, types);

        return sql;
    }

    /**
     * Generates SQL for find by user groups query
     *
     * @param excludedGroups names of groups that should be excluded from the query
     * @return SQL string for find by sites groups query
     */
    private String getFindByUserGroupsSQL(String[] excludedGroups) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(FIND_BY_USER_GROUPS);

        // Add ignore user queries if needed
        sql = addExcludedUserGroupsToSQL(sql, excludedGroups);

        return sql;
    }

    /**
     * Generates SQL for find by user groups query
     *
     * @param searchQuery           search query string
     * @param ignoreDefaultUser     true if default users should be ignored
     * @param ignoreDeactivatedUser true if deactivated users should be ignored
     * @param excludedGroups        names of groups that should be excluded from the query
     * @return SQL string for search by sites groups query
     */
    private String getSearchUserGroupsSQL(String searchQuery,
                                          boolean ignoreDefaultUser,
                                          boolean ignoreDeactivatedUser,
                                          String[] excludedGroups) {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(SEARCH_BY_USER_GROUPS);

        // Replace and_or_null operator, false means that this is a conjunction
        // https://www.liferay.com/community/wiki/-/wiki/Main/Custom+queries+in+Liferay
        sql = CustomSQLUtil.replaceAndOperator(sql, false);

        // Add ignore user queries if needed
        sql = addIgnoreDefaultUserToSql(sql, ignoreDefaultUser);
        sql = addIgnoreDeactivatedUserToSql(sql, ignoreDeactivatedUser);
        sql = addSearchParametersToSql(sql, searchQuery);
        sql = addExcludedUserGroupsToSQL(sql, excludedGroups);

        return sql;
    }

    /**
     * Takes sql string from parameter and replaces default user placeholder
     * if set to true in ignoreDefaultUser parameter
     *
     * @param sql               String
     * @param ignoreDefaultUser boolean
     * @return updated sql string
     */
    private String addIgnoreDefaultUserToSql(String sql, boolean ignoreDefaultUser) {
        // Add ignore default user query if needed
        if (ignoreDefaultUser) {
            return StringUtil.replace(sql, PLACEHOLDER_DEFAULT_USER, "AND (User_.defaultUser != [$TRUE$])");
        } else {
            return StringUtil.replace(sql, PLACEHOLDER_DEFAULT_USER, StringPool.BLANK);
        }
    }

    /**
     * Takes sql string from parameter and replaces default user placeholder
     * if set to true in ignoreDefaultUser parameter
     *
     * @param sql                   String
     * @param ignoreDeactivatedUser boolean
     * @return updated sql string
     */
    private String addIgnoreDeactivatedUserToSql(String sql, boolean ignoreDeactivatedUser) {
        // Add ignore default user query if needed
        if (ignoreDeactivatedUser) {
            return StringUtil.replace(sql, PLACEHOLDER_DEACTIVATED_USER, "AND (User_.status = 0)");
        } else {
            return StringUtil.replace(sql, PLACEHOLDER_DEACTIVATED_USER, StringPool.BLANK);
        }
    }

    /**
     * Takes sql string from parameter and replaces excluded sites placeholder with excludedSites
     * as array taken from the parameter
     *
     * @param sql           String
     * @param excludedSites String[]
     * @return updated sql string
     */
    private String addExcludedSitesToSQL(String sql, String[] excludedSites) {
        // If no excluded groups are set clear placeholders and return custom sql
        if (excludedSites.length == 0) {
            return StringUtil.replace(sql,
                    new String[]{PLACEHOLDER_USERS_GROUPS_JOIN, PLACEHOLDER_USERS_GROUPS_WHERE},
                    new String[]{StringPool.BLANK, StringPool.BLANK});
        }

        // Otherwise, we need to build a query which will excluded proper sites
        StringBundler sb = new StringBundler(excludedSites.length * 2 - 1);
        for (int i = 0; i < excludedSites.length; i++) {
            // Add question mark so we can add parameters
            sb.append(StringPool.QUESTION);
            // Add comma if not at the end
            if ((i + 1) < excludedSites.length) {
                sb.append(StringPool.COMMA);
            }
        }

        return StringUtil.replace(sql,
                new String[]{PLACEHOLDER_USERS_GROUPS_JOIN, PLACEHOLDER_USERS_GROUPS_WHERE},
                new String[]{
                        "INNER JOIN Group_ ON Group_.groupId = Users_Groups.groupId",
                        "AND Group_.name NOT IN (" + sb.toString() + ")"
                });
    }

    /**
     * Takes sql string from parameter and replaces relation types placeholder with types taken
     * from the parameter
     *
     * @param sql   String
     * @param types int[]
     * @return updated sql string
     */
    private String addSocialRelationTypesToSQL(String sql, int[] types) {

        // We need to build a query which will add all relation types
        StringBundler sb = new StringBundler(types.length * 2 - 1);
        for (int i = 0; i < types.length; i++) {
            // Add question mark so we can add parameters
            sb.append(StringPool.QUESTION);
            // Add comma if not at the end
            if ((i + 1) < types.length) {
                sb.append(StringPool.COMMA);
            }
        }

        return StringUtil.replace(sql,
                PLACEHOLDER_SOCIAL_RELATION_TYPES,
                "SocialRelation.type_ IN (" + sb.toString() + ")");
    }

    /**
     * Takes sql string from parameter and replaces excluded groups placeholder with excludedGroups
     * as array taken from the parameter
     *
     * @param sql            String
     * @param excludedGroups String[]
     * @return updated sql string
     */
    private String addExcludedUserGroupsToSQL(String sql, String[] excludedGroups) {

        // If no excluded groups are set clear placeholders and return custom sql
        if (excludedGroups.length == 0) {
            return StringUtil.replace(sql,
                    new String[]{PLACEHOLDER_USERS_GROUPS_JOIN, PLACEHOLDER_USERS_GROUPS_WHERE},
                    new String[]{StringPool.BLANK, StringPool.BLANK});
        }

        // Otherwise, we need to build a query which will excluded proper sites
        StringBundler sb = new StringBundler(excludedGroups.length * 2 - 1);
        for (int i = 0; i < excludedGroups.length; i++) {
            // Add question mark so we can add parameters
            sb.append(StringPool.QUESTION);
            // Add comma if not at the end
            if ((i + 1) < excludedGroups.length) {
                sb.append(StringPool.COMMA);
            }
        }

        return StringUtil.replace(sql,
                new String[]{PLACEHOLDER_USERS_GROUPS_JOIN, PLACEHOLDER_USERS_GROUPS_WHERE},
                new String[]{
                        "INNER JOIN UserGroup ON UserGroup.userGroupId = Users_UserGroups.userGroupId",
                        "AND UserGroup.name NOT IN (" + sb.toString() + ")"
                });
    }

    /**
     * Takes sql string from parameter and replaces placeholders with search query
     *
     * @param sql         String
     * @param searchQuery String
     * @return updates sql string
     */
    private String addSearchParametersToSql(String sql, String searchQuery) {

        // Search query needs to be in a lower case since we are lower the columns as well
        String lowerCaseSearchQuery = searchQuery.toLowerCase();

        // Replace
        sql = CustomSQLUtil.replaceKeywords(
                sql, "lower(User_.firstName)", StringPool.LIKE, false,
                new String[]{lowerCaseSearchQuery});
        sql = CustomSQLUtil.replaceKeywords(
                sql, "lower(User_.middleName)", StringPool.LIKE, false,
                new String[]{lowerCaseSearchQuery});
        sql = CustomSQLUtil.replaceKeywords(
                sql, "lower(User_.lastName)", StringPool.LIKE, true,
                new String[]{lowerCaseSearchQuery});

        return sql;
    }

}
