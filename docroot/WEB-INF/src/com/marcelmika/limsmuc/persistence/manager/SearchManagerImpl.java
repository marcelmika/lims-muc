/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.manager;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.limsmuc.persistence.domain.Buddy;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/09/14
 * Time: 10:03
 */
public class SearchManagerImpl implements SearchManager {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SearchManagerImpl.class);

    /**
     * Returns a list of buddies based on the search query. The search will be performed
     * in first name, middle name, last name, screen name and email.
     *
     * @param userId      Long
     * @param searchQuery String
     * @param start       of the list
     * @param end         of the list
     * @return List of buddies
     * @throws Exception
     */
    @Override
    public List<Buddy> searchBuddies(Long userId, String searchQuery, int start, int end) throws Exception {

        // Get selected list strategy
        Environment.BuddyListStrategy strategy = Environment.getBuddyListStrategy();

        // All
        if (strategy == BuddyListStrategy.ALL) {
            return searchAllBuddies(userId, searchQuery, start, end);
        }
        // Groups
        else if (strategy == BuddyListStrategy.GROUPS) {

            Set<Buddy> buddies = new HashSet<Buddy>();

            // Sites
            if (Environment.isBuddyListGroupSiteEnabled()) {
                // Add buddies to set
                buddies.addAll(searchSitesBuddies(userId, searchQuery, start, end));
            }

            // Social
            if (Environment.isBuddyListGroupSocialEnabled()) {
                // Add buddies to set
                buddies.addAll(searchSocialBuddies(userId, searchQuery, start, end));
            }

            // User
            if (Environment.isBuddyListGroupUserEnabled()) {
                // Add buddies to set
                buddies.addAll(searchUserGroupsBuddies(userId, searchQuery, start, end));
            }

            return new LinkedList<Buddy>(buddies);
        }
        // Unknown
        else {
            throw new Exception("Unknown buddy list strategy");
        }
    }


    /**
     * Returns a list of buddies related to the user based on the search query
     *
     * @param userId      Long
     * @param searchQuery String
     * @param start       of the list
     * @param end         of the list
     * @return List of buddies
     * @throws Exception
     */
    private List<Buddy> searchAllBuddies(Long userId,
                                         String searchQuery,
                                         int start,
                                         int end) throws Exception {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();

        // Get from persistence
        List<Object[]> users = SettingsLocalServiceUtil.searchAllBuddies(
                userId, searchQuery, true, ignoreDeactivatedUser, start, end
        );

        // Return deserialized result
        return deserializeBuddyListFromUserObjects(users);
    }

    /**
     * Returns a list of buddies. The list is made of all buddies based on the search query in the sites
     * where the user participates
     *
     * @param userId      which should be excluded from the list
     * @param searchQuery search query string
     * @param start       of the list
     * @param end         of the list
     * @return List of buddies
     * @throws Exception
     */
    private List<Buddy> searchSitesBuddies(Long userId,
                                           String searchQuery,
                                           int start,
                                           int end) throws Exception {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        String[] excludedSites = Environment.getBuddyListSiteExcludes();

        // Get from persistence
        List<Object[]> users = SettingsLocalServiceUtil.searchSitesBuddies(
                userId, searchQuery, true, ignoreDeactivatedUser, excludedSites, start, end
        );

        // Return deserialized result
        return deserializeBuddyListFromUserObjects(users);
    }

    /**
     * Returns a list of buddies. This list is made of all buddies based on the search query with whom the user
     * has a social relationships given in the parameter.
     *
     * @param userId      which should be excluded from the list
     * @param searchQuery search query string
     * @param start       of the list
     * @param end         of the list
     * @return List of buddies
     * @throws Exception
     */
    private List<Buddy> searchSocialBuddies(Long userId,
                                            String searchQuery,
                                            int start,
                                            int end) throws Exception {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        Environment.BuddyListSocialRelation[] relationTypes = Environment.getBuddyListSocialRelations();

        // Get int codes from relation types since the persistence consumes an int array only.
        int[] relationCodes = new int[relationTypes.length];
        for (int i = 0; i < relationTypes.length; i++) {
            relationCodes[i] = relationTypes[i].getCode();
        }

        // Get from persistence
        List<Object[]> users = SettingsLocalServiceUtil.searchSocialBuddies(
                userId, searchQuery, true, ignoreDeactivatedUser, relationCodes, start, end
        );

        // Return deserialized result
        return deserializeBuddyListFromUserObjects(users);
    }

    /**
     * Returns a list of buddies. This list is made of all buddies based on the search query that are
     * in the same user group as the user.
     *
     * @param userId      which should be excluded from the list
     * @param searchQuery search query string
     * @param start       of the list
     * @param end         of the list
     * @return a list of buddies
     * @throws Exception
     */
    private List<Buddy> searchUserGroupsBuddies(Long userId,
                                                String searchQuery,
                                                int start,
                                                int end) throws Exception {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        String[] excludedGroups = Environment.getBuddyListGroupExcludes();

        // Get user groups
        List<Object[]> users = SettingsLocalServiceUtil.searchUserGroupsBuddies(
                userId, searchQuery, true, ignoreDeactivatedUser, excludedGroups, start, end
        );

        // Return deserialized result
        return deserializeBuddyListFromUserObjects(users);
    }

    /**
     * Deserialize user objects to the list of buddies
     *
     * @param userObjects a list of user data stored in an object array
     * @return List of buddies
     */
    private List<Buddy> deserializeBuddyListFromUserObjects(List<Object[]> userObjects) {

        // Deserialize user info in plain objects to buddy
        List<Buddy> buddies = new LinkedList<Buddy>();
        for (Object[] userObject : userObjects) {
            // Deserialize
            Buddy buddy = Buddy.fromPlainObject(userObject, 0);
            // Add to list
            buddies.add(buddy);
        }

        return buddies;
    }
}