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
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListSocialRelation;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.limsmuc.persistence.domain.Buddy;
import com.marcelmika.limsmuc.persistence.domain.Group;
import com.marcelmika.limsmuc.persistence.domain.GroupCollection;
import com.marcelmika.limsmuc.persistence.domain.Page;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/4/14
 * Time: 9:45 AM
 */
public class GroupManagerImpl implements GroupManager {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(GroupManagerImpl.class);

    /**
     * Returns Group Collection of all groups related to the user
     *
     * @param userId Long id of the user
     * @param page   Page pagination object
     * @return GroupCollection of groups related to the user
     * @throws Exception
     */
    @Override
    public GroupCollection getGroups(Long userId, Page page) throws Exception {
        // TODO: REMOVE
        int start = 0;
        int end = Environment.getBuddyListMaxBuddies();

        // Get selected list strategy
        Environment.BuddyListStrategy strategy = Environment.getBuddyListStrategy();
        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        // Some sites or groups may be excluded
        String[] excludedSites = Environment.getBuddyListSiteExcludes();
        String[] excludedGroups = Environment.getBuddyListGroupExcludes();
        // Relation types
        BuddyListSocialRelation[] relationTypes = Environment.getBuddyListSocialRelations();

        // All buddies
        if (strategy == BuddyListStrategy.ALL) {
            return findAllGroup(userId, true, ignoreDeactivatedUser, page);
        }
        // Buddies from sites
        else if (strategy == BuddyListStrategy.SITES) {
            return findSitesGroups(userId, true, ignoreDeactivatedUser, excludedSites, page);
        }
        // Socialized buddies
        else if (strategy == BuddyListStrategy.SOCIAL) {
            return getSocialGroups(
                    userId, true, ignoreDeactivatedUser, relationTypes, start, end
            );
        }
        // Socialized and buddies from sites
        else if (strategy == BuddyListStrategy.SITES_AND_SOCIAL) {
            return getSitesAndSocialGroups(
                    userId, true, ignoreDeactivatedUser, excludedSites, relationTypes, start, end
            );
        }
        // User Groups
        else if (strategy == BuddyListStrategy.USER_GROUPS) {
            return getUserGroups(userId, true, ignoreDeactivatedUser, excludedGroups, start, end);
        }
        // Unknown
        else {
            throw new Exception("Unknown buddy list strategy");
        }
    }

    /**
     * Returns Group
     *
     * @param userId       Long id of the user
     * @param groupId      Long id of the group
     * @param listStrategy String group list strategy
     * @param page         Page pagination object
     * @return Group
     * @throws Exception
     */
    @Override
    public Group getGroup(Long userId, Long groupId, BuddyListStrategy listStrategy, Page page) throws Exception {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();

        // All buddies
        if (listStrategy == BuddyListStrategy.ALL) {
            return readAllGroup(userId, true, ignoreDeactivatedUser, page);
        }
        // Sites group
        else if (listStrategy == BuddyListStrategy.SITES) {
            return readSitesGroup(userId, groupId, true, ignoreDeactivatedUser, page);
        }
        // Unknown
        else {
            throw new Exception("Unknown buddy list strategy");
        }
    }

    /**
     * Returns group collection which contains all buddies in the system.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param page                  pagination object
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection findAllGroup(Long userId,
                                         boolean ignoreDefaultUser,
                                         boolean ignoreDeactivatedUser,
                                         Page page) throws Exception {

        // Read the group
        Group group = readAllGroup(userId, ignoreDefaultUser, ignoreDeactivatedUser, page);
        // Create group collection which will hold the only group that holds all users
        GroupCollection groupCollection = new GroupCollection();
        // Add group to collection only if there are any buddies
        groupCollection.addGroup(group);
        // Set list strategy
        groupCollection.setListStrategy(BuddyListStrategy.ALL);
        // Add last modified date
        groupCollection.setLastModified(Calendar.getInstance().getTime());

        return groupCollection;
    }

    private Group readAllGroup(Long userId,
                               boolean ignoreDefaultUser,
                               boolean ignoreDeactivatedUser,
                               Page page) throws Exception {

        // Count the size of the ALL group
        Integer totalElements = SettingsLocalServiceUtil.countAllUsers(
                userId, ignoreDefaultUser, ignoreDeactivatedUser
        );

        // Get number and size
        int number = page.getNumber();
        int size = page.getSize();

        // Calculated start and end
        int start = number * size;
        int end = start + size;

        // Add info to the page
        page.setTotalElements(totalElements);
        page.setTotalPages((int) Math.ceil(totalElements / (double) size));

        // Get users from persistence
        List<Object[]> users = SettingsLocalServiceUtil.getAllGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );

        // Create group which will contain all users
        Group group = new Group();

        // Add page to group
        group.setPage(page);
        group.setListStrategy(BuddyListStrategy.ALL);

        // Since we get an Object[] from persistence we need to map it to the persistence Buddy object
        for (Object[] userObject : users) {
            // Deserialize
            Buddy buddy = Buddy.fromPlainObject(userObject, 0);
            // Add to group
            group.addBuddy(buddy);
        }

        return group;
    }

    /**
     * Returns group collection which contains groups that represents sites where the user participates.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param excludedSites         names of sites (groups) that should be excluded from the group collection
     * @param page                  pagination object
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection findSitesGroups(Long userId,
                                            boolean ignoreDefaultUser,
                                            boolean ignoreDeactivatedUser,
                                            String[] excludedSites,
                                            Page page) throws Exception {

        // Get sites groups
        List<Object[]> groupIds = SettingsLocalServiceUtil.findSitesGroups(userId, excludedSites);

        // Create group collection
        GroupCollection groupCollection = new GroupCollection();

        for (Object object : groupIds) {

            // Get the group Id
            Long groupId = (Long) object;
            // Read the group
            Group group = readSitesGroup(userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser, page);

            // Add to collection
            groupCollection.addGroup(group);
        }

        // Add last modified date
        groupCollection.setLastModified(Calendar.getInstance().getTime());
        // Set list strategy
        groupCollection.setListStrategy(BuddyListStrategy.SITES);

        return groupCollection;
    }

    /**
     * Returns group and their users based on the page parameter
     *
     * @param userId                which should be excluded from the list
     * @param groupId               id of the group
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param page                  pagination object
     * @return Group
     * @throws Exception
     */
    private Group readSitesGroup(Long userId,
                                 Long groupId,
                                 boolean ignoreDefaultUser,
                                 boolean ignoreDeactivatedUser,
                                 Page page) throws Exception {

        // Get number and size
        int number = page.getNumber();
        int size = page.getSize();

        // Calculated start and end
        int start = number * size;
        int end = start + size;

        // Get sites groups
        List<Object[]> objects = SettingsLocalServiceUtil.readSitesGroup(
                userId, groupId, ignoreDefaultUser, ignoreDeactivatedUser, start, end
        );

        // Prepare group
        Group group = null;

        // Because all group requests are cached we need to determine what was the latest modified
        // date. If the user modifies his presence we need to change the etag so the client will load
        // it from server.
        Date lastModifiedDate = new Date(0);

        for (Object[] object : objects) {

            // Parse group if this is the first iteration
            if (group == null) {
                // Deserialize group from object, group starts with 0
                group = Group.fromPlainObject(object, 0);
                // Count the size of the sites group
                Integer totalElements = SettingsLocalServiceUtil.countSitesGroupUsers(
                        userId, group.getGroupId(), ignoreDefaultUser, ignoreDeactivatedUser
                );

                // Crate page
                Page groupPage = new Page();
                groupPage.setNumber(number);
                groupPage.setSize(size);
                groupPage.setTotalElements(totalElements);
                groupPage.setTotalPages((int) Math.ceil(totalElements / (double) size));

                // Add page and set list strategy
                group.setPage(groupPage);
                group.setListStrategy(BuddyListStrategy.SITES);
            }

            // Deserialize buddy from object, buddy starts at 1
            Buddy buddy = Buddy.fromPlainObject(object, 2);

            // Add it to group
            group.addBuddy(buddy);

            // If the presence was updated later then the last one overwrite the counter
            if (buddy.getPresenceUpdatedAt().after(lastModifiedDate)) {
                lastModifiedDate = buddy.getPresenceUpdatedAt();
            }
        }

        return group;
    }

    /**
     * Returns group collection which contains groups that represent social relations of the user.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param relationTypes         an array of relation type enums
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getSocialGroups(Long userId,
                                            boolean ignoreDefaultUser,
                                            boolean ignoreDeactivatedUser,
                                            BuddyListSocialRelation[] relationTypes,
                                            int start,
                                            int end) throws Exception {

        // Get int codes from relation types since the persistence only consumes an int array.
        int[] relationCodes = new int[relationTypes.length];
        for (int i = 0; i < relationTypes.length; i++) {
            relationCodes[i] = relationTypes[i].getCode();
        }

        // Get social groups
        List<Object[]> groupObjects = SettingsLocalServiceUtil.getSocialGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, relationCodes, start, end
        );

        // We are about to build a collection of groups that will contain
        // users within the groups. However, we only get "flat" object which contains
        // both group data and user data. Thus we need to create a hash map that will
        // hold each group under the unique key (groupName). This should improved the
        // speed of mapping since we can reuse groups that we already mapped.
        Map<String, Group> groupMap = new HashMap<String, Group>();

        // Because all group requests are cached we need to determine what was the latest modified
        // date. If the user modifies his presence we need to change the etag so the client will load
        // it from server.
        Date lastModifiedDate = new Date(0);

        // Build groups and users
        for (Object[] object : groupObjects) {
            // Relation type is first element
            Integer relationCode = (Integer) object[0];
            // Map to enum
            BuddyListSocialRelation relationType = BuddyListSocialRelation.fromCode(relationCode);
            // Get group name from relation type description
            String groupName = relationType.getDescription();

            // Get cached group
            Group group = groupMap.get(groupName);

            // Check if the group is already cached
            if (groupMap.get(groupName) == null) {
                group = new Group();

                // TODO: Take real group id
//                group.setGroupId(1);
                group.setName(groupName);
                group.setListStrategy(BuddyListStrategy.SOCIAL);
                group.setSocialRelation(relationType);

                // Cache it
                groupMap.put(groupName, group);
            }

            // Deserialize buddy from object, buddy start at 1
            Buddy buddy = Buddy.fromPlainObject(object, 1);

            // Add it to group
            group.addBuddy(buddy);

            // If the presence was updated later then the last one overwrite the counter
            if (buddy.getPresenceUpdatedAt().after(lastModifiedDate)) {
                lastModifiedDate = buddy.getPresenceUpdatedAt();
            }
        }

        // Create group collection
        GroupCollection groupCollection = new GroupCollection();
        // Add groups to collection
        for (Group group : groupMap.values()) {
            groupCollection.addGroup(group);
        }

        // Add last modified date
        groupCollection.setLastModified(lastModifiedDate);
        // Set list strategy
        groupCollection.setListStrategy(BuddyListStrategy.SOCIAL);

        return groupCollection;
    }

    /**
     * Returns group collection which contains groups that represent social relations of the user.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param excludedSites         names of sites (groups) that should be excluded from the group collection
     * @param relationTypes         an array of relation type enums
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getSitesAndSocialGroups(Long userId,
                                                    boolean ignoreDefaultUser,
                                                    boolean ignoreDeactivatedUser,
                                                    String[] excludedSites,
                                                    BuddyListSocialRelation[] relationTypes,
                                                    int start,
                                                    int end) throws Exception {
        // Get site groups
//        GroupCollection sitesGroupCollection = getSitesGroups(
//                userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedSites, start, end
//        );
        // Get social groups
        GroupCollection socialGroupCollection = getSocialGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, relationTypes, start, end
        );

        // Merge site and social groups
        List<Group> mergedGroups = new LinkedList<Group>();
//        mergedGroups.addAll(sitesGroupCollection.getGroups());
        mergedGroups.addAll(socialGroupCollection.getGroups());

        // Merge
        GroupCollection groupCollection = new GroupCollection();
        groupCollection.setGroups(mergedGroups);

        // Decide which group is "newer"
//        if (sitesGroupCollection.getLastModified().after(socialGroupCollection.getLastModified())) {
//            groupCollection.setLastModified(sitesGroupCollection.getLastModified());
//        } else {
//            groupCollection.setLastModified(socialGroupCollection.getLastModified());
//        }

        // Set list strategy
        groupCollection.setListStrategy(BuddyListStrategy.SITES_AND_SOCIAL);

        return groupCollection;
    }

    /**
     * Returns group collection which contains user groups where the user belongs.
     * The groups contain all users that are within except for the users given in param
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param excludedGroups        names of groups that should be excluded from the group collection
     * @param start                 of the list
     * @param end                   of the list
     * @return GroupCollection
     * @throws Exception
     */
    private GroupCollection getUserGroups(Long userId,
                                          boolean ignoreDefaultUser,
                                          boolean ignoreDeactivatedUser,
                                          String[] excludedGroups,
                                          int start,
                                          int end) throws Exception {

        // Get user groups
        List<Object[]> groupObjects = SettingsLocalServiceUtil.getUserGroups(
                userId, ignoreDefaultUser, ignoreDeactivatedUser, excludedGroups, start, end
        );

        // We are about to build a collection of groups that will contain
        // users within the groups. However, we only get "flat" object which contains
        // both group data and user data. Thus we need to create a hash map that will
        // hold each group under the unique key (groupName). This should improve the
        // speed of mapping since we can reuse groups that we already mapped.
        Map<String, Group> groupMap = new HashMap<String, Group>();

        // Because all group requests are cached we need to determine what was the latest modified
        // date. If the user modifies his presence we need to change the etag so the client will load
        // it from server.
        Date lastModifiedDate = new Date(0);

        // Build groups and users
        for (Object[] object : groupObjects) {

            // Deserialize group from object, group starts with 0
            Group group = Group.fromPlainObject(object, 0);

            // Check if the group is already cached
            if (groupMap.get(group.getName()) == null) {

                // TODO: Take the real group id
//                group.setGroupId(group.getName());
                group.setListStrategy(BuddyListStrategy.USER_GROUPS);

                // Cache it
                groupMap.put(group.getName(), group);
            }

            group = groupMap.get(group.getName());

            // Deserialize buddy from object, buddy starts at 1
            Buddy buddy = Buddy.fromPlainObject(object, 1);

            // Add it to group
            group.addBuddy(buddy);

            // If the presence was updated later then the last one overwrite the counter
            if (buddy.getPresenceUpdatedAt().after(lastModifiedDate)) {
                lastModifiedDate = buddy.getPresenceUpdatedAt();
            }
        }

        // Create group collection
        GroupCollection groupCollection = new GroupCollection();
        // Add groups to collection
        for (Group group : groupMap.values()) {
            groupCollection.addGroup(group);
        }
        // Add last modified date
        groupCollection.setLastModified(lastModifiedDate);
        // Set list strategy
        groupCollection.setListStrategy(BuddyListStrategy.USER_GROUPS);

        return groupCollection;
    }
}
