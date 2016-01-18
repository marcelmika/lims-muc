/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.manager;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListGroup;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListSocialRelation;
import com.marcelmika.limsmuc.api.environment.Environment.BuddyListStrategy;
import com.marcelmika.limsmuc.persistence.domain.Buddy;
import com.marcelmika.limsmuc.persistence.domain.Group;
import com.marcelmika.limsmuc.persistence.domain.GroupCollection;
import com.marcelmika.limsmuc.persistence.domain.Page;
import com.marcelmika.limsmuc.persistence.exception.ForbiddenException;
import com.marcelmika.limsmuc.persistence.exception.PersistenceException;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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

        // Get selected list strategy
        Environment.BuddyListStrategy strategy = Environment.getBuddyListStrategy();

        // All
        if (strategy == BuddyListStrategy.ALL) {
            return findAllGroup(userId, page);
        }
        // Groups
        else if (strategy == BuddyListStrategy.GROUPS) {
            return findGroups(userId, page);
        }
        // Unknown
        else {
            throw new Exception("Unknown buddy list strategy: " + strategy);
        }
    }

    /**
     * Returns Group
     *
     * @param userId       Long id of the user
     * @param groupId      Long id of the group
     * @param listStrategy List strategy
     * @param listGroup    List group
     * @param page         Page pagination object
     * @return Group
     * @throws PersistenceException persistence exception
     * @throws ForbiddenException   not allowed to perform action
     */
    @Override
    public Group getGroup(Long userId,
                          Long groupId,
                          BuddyListStrategy listStrategy,
                          BuddyListGroup listGroup,
                          Page page) throws PersistenceException, ForbiddenException {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();

        try {

            // All
            if (listStrategy == BuddyListStrategy.ALL) {
                return readAllGroup(userId, true, ignoreDeactivatedUser, page);
            }

            // Groups
            else if (listStrategy == BuddyListStrategy.GROUPS) {

                // Sites
                if (listGroup == BuddyListGroup.SITE) {
                    return readSitesGroup(userId, groupId, true, ignoreDeactivatedUser, page);
                }
                // Social
                else if (listGroup == BuddyListGroup.SOCIAL) {
                    return readSocialGroup(userId, groupId, true, ignoreDeactivatedUser, page);
                }
                // User group
                else if (listGroup == BuddyListGroup.USER) {
                    return readUserGroup(userId, groupId, true, ignoreDeactivatedUser, page);
                }
                // Unknown
                else {
                    throw new PersistenceException("Unknown buddy list group: " + listGroup);
                }
            }

            // Unknown
            else {
                throw new PersistenceException("Unknown buddy list strategy: " + listStrategy);
            }

        }
        // Persistence failure
        catch (SystemException systemException) {
            throw new PersistenceException(systemException);
        }
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // ALL STRATEGY
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns group collection which contains all buddies in the system.
     *
     * @param userId which should be excluded from the list
     * @param page   pagination object
     * @return GroupCollection
     * @throws SystemException
     */
    private GroupCollection findAllGroup(Long userId, Page page) throws SystemException {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();

        // Create group collection which will hold the only group that holds all users
        GroupCollection groupCollection = new GroupCollection();

        // Read the group
        Group group = readAllGroup(userId, true, ignoreDeactivatedUser, page);
        // Add group to collection only if there are any buddies
        if (group.getBuddies().size() > 0) {
            groupCollection.addGroup(group);
        }

        // Set list strategy
        groupCollection.setListStrategy(BuddyListStrategy.ALL);
        // Add last modified date
        groupCollection.setLastModified(Calendar.getInstance().getTime());

        return groupCollection;
    }

    /**
     * Returns group and its users based on the page parameter
     *
     * @param userId                which should be excluded from the list
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param page                  pagination object
     * @return Group
     * @throws SystemException
     */
    private Group readAllGroup(Long userId,
                               boolean ignoreDefaultUser,
                               boolean ignoreDeactivatedUser,
                               Page page) throws SystemException {

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
        List<Object[]> users = SettingsLocalServiceUtil.findAllGroups(
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


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // GROUP STRATEGY
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Finds groups that are set in group list strategy
     *
     * @param userId Long
     * @param page   Page
     * @return GroupCollection
     * @throws SystemException
     */
    public GroupCollection findGroups(Long userId, Page page) throws SystemException, ForbiddenException {

        // Prepare groups
        GroupCollection groupCollection = new GroupCollection();
        List<Group> groups = new LinkedList<Group>();

        // Sites
        if (Environment.isBuddyListGroupSiteEnabled()) {
            // Add sites groups
            groups.addAll(findSitesGroups(userId, page));
        }

        // Social
        if (Environment.isBuddyListGroupSocialEnabled()) {
            groups.addAll(findSocialGroups(userId, page));
        }

        // User
        if (Environment.isBuddyListGroupUserEnabled()) {
            groups.addAll(findUserGroups(userId, page));
        }

        // Add last modified date
        groupCollection.setLastModified(Calendar.getInstance().getTime());

        // Set list strategy
        groupCollection.setListStrategy(BuddyListStrategy.GROUPS);

        // Add all aggregated groups
        groupCollection.setGroups(groups);

        return groupCollection;
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SITES GROUP
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns group collection which contains groups that represents sites where the user participates.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId which should be excluded from the list
     * @param page   pagination object
     * @return Groups
     * @throws SystemException
     */
    private List<Group> findSitesGroups(Long userId, Page page) throws SystemException, ForbiddenException {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        // Some sites or groups may be excluded
        String[] excludedSites = Environment.getBuddyListSiteExcludes();

        // Get sites groups
        List<Object[]> groupIds = SettingsLocalServiceUtil.findSitesGroups(userId, excludedSites);

        // Create group list
        List<Group> groups = new LinkedList<Group>();

        for (Object object : groupIds) {

            // Get the group id
            Long groupId = (Long) object;
            // Read the group
            Group group = readSitesGroup(userId, groupId, true, ignoreDeactivatedUser, page);

            // Add to groups
            groups.add(group);
        }

        return groups;
    }

    /**
     * Returns group and its users based on the page parameter
     *
     * @param userId                which should be excluded from the list
     * @param groupId               id of the group
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param page                  pagination object
     * @return Group
     * @throws SystemException
     */
    private Group readSitesGroup(Long userId,
                                 Long groupId,
                                 boolean ignoreDefaultUser,
                                 boolean ignoreDeactivatedUser,
                                 Page page) throws SystemException, ForbiddenException {

        // Check if the user is a member of the group thus allowed to read it
        if (!SettingsLocalServiceUtil.isMemberOfSitesGroup(userId, groupId)) {
            throw new ForbiddenException("User is not allowed to read the sites group");
        }

        // Get number and size
        int number = page.getNumber();
        int size = page.getSize();

        // Calculated start and end
        int start = number * size;
        int end = start + size;

        // Get sites group
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
                group.setListStrategy(BuddyListStrategy.GROUPS);
                group.setListGroup(BuddyListGroup.SITE);
            }

            // Deserialize buddy from object, buddy starts at 2
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


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // SOCIAL GROUP
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns group collection which contains groups that represent social relations of the user.
     * The groups contain all users that are within except for the user given in param.
     *
     * @param userId which should be excluded from the list
     * @param page   pagination object
     * @return list of Groups
     * @throws SystemException
     */
    private List<Group> findSocialGroups(Long userId, Page page) throws SystemException, ForbiddenException {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        // Relation types
        BuddyListSocialRelation[] relationTypes = Environment.getBuddyListSocialRelations();

        // Get int codes from relation types since the persistence only consumes an int array.
        int[] relationCodes = new int[relationTypes.length];
        for (int i = 0; i < relationTypes.length; i++) {
            relationCodes[i] = relationTypes[i].getCode();
        }

        // Get social groups
        List<Object[]> groupIds = SettingsLocalServiceUtil.findSocialGroups(userId, relationCodes);

        // Create group collection
        List<Group> groups = new LinkedList<Group>();

        for (Object object : groupIds) {

            // Get the group id
            Long groupId = (Long) object;
            // Read the group
            Group group = readSocialGroup(userId, groupId, true, ignoreDeactivatedUser, page);

            // Add to collection
            groups.add(group);
        }

        return groups;
    }

    /**
     * Returns social group and its users
     *
     * @param userId                which should be excluded from the list
     * @param groupId               id of the group
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param page                  pagination object
     * @return Group
     * @throws SystemException
     * @throws ForbiddenException
     */
    private Group readSocialGroup(Long userId,
                                  Long groupId,
                                  boolean ignoreDefaultUser,
                                  boolean ignoreDeactivatedUser,
                                  Page page) throws SystemException, ForbiddenException {

        // Check if the user is a member of the group thus allowed to read it
        if (!SettingsLocalServiceUtil.isMemberOfSocialGroup(userId, groupId)) {
            throw new ForbiddenException("User is not allowed to read the social group");
        }

        // Get number and size
        int number = page.getNumber();
        int size = page.getSize();

        // Calculated start and end
        int start = number * size;
        int end = start + size;

        // Get user group
        List<Object[]> objects = SettingsLocalServiceUtil.readSocialGroup(
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

                // Group id is the first element
                Long parsedGroupId = (Long) object[0];

                // Create group
                group = new Group();
                group.setGroupId(parsedGroupId);

                // Count the size of the group
                Integer totalElements = SettingsLocalServiceUtil.countSocialGroupUsers(
                        userId, parsedGroupId, ignoreDefaultUser, ignoreDeactivatedUser
                );

                // Create page
                Page groupPage = new Page();
                groupPage.setNumber(number);
                groupPage.setSize(size);
                groupPage.setTotalElements(totalElements);
                groupPage.setTotalPages((int) Math.ceil(totalElements / (double) size));

                // Add page and set list strategy
                group.setPage(groupPage);
                group.setListStrategy(BuddyListStrategy.GROUPS);
                group.setListGroup(BuddyListGroup.SOCIAL);

                // Relation type is the second element
                Integer relationCode = (Integer) object[1];
                // Map to enum
                BuddyListSocialRelation relationType = BuddyListSocialRelation.fromCode(relationCode);

                // Set relation type
                group.setSocialRelation(relationType);

                // Get group name from relation type description
                String groupName = relationType.getDescription();
                group.setName(groupName);
            }

            // Deserialize buddy from object, buddy starts at 2
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


    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // USER GROUP
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Returns group collection which contains user groups where the user belongs.
     * The groups contain all users that are within except for the users given in param
     *
     * @param userId which should be excluded from the list
     * @param page   pagination object
     * @return list of Groups
     * @throws SystemException
     * @throws ForbiddenException
     */
    private List<Group> findUserGroups(Long userId, Page page) throws SystemException, ForbiddenException {

        // Get the info if the deactivated user should be ignored
        boolean ignoreDeactivatedUser = Environment.getBuddyListIgnoreDeactivatedUser();
        // Get excluded groups from environment
        String[] excludedGroups = Environment.getBuddyListGroupExcludes();

        // Get user groups
        List<Object[]> groupIds = SettingsLocalServiceUtil.findUserGroups(userId, excludedGroups);

        // Create group collection
        List<Group> groups = new LinkedList<Group>();

        for (Object object : groupIds) {

            // Get the group id
            Long groupId = (Long) object;
            // Read the group
            Group group = readUserGroup(userId, groupId, true, ignoreDeactivatedUser, page);

            // Add to collection
            groups.add(group);
        }

        return groups;
    }

    /**
     * Returns user group and their its users
     *
     * @param userId                which should be excluded from the list
     * @param groupId               id of the group
     * @param ignoreDefaultUser     boolean set to true if the default user should be excluded
     * @param ignoreDeactivatedUser boolean set to true if the deactivated user should be excluded
     * @param page                  pagination object
     * @return Group
     * @throws SystemException
     * @throws ForbiddenException
     */
    private Group readUserGroup(Long userId,
                                Long groupId,
                                boolean ignoreDefaultUser,
                                boolean ignoreDeactivatedUser,
                                Page page) throws SystemException, ForbiddenException {

        // Check if the user is a member of the group thus allowed to read it
        if (!SettingsLocalServiceUtil.isMemberOfUserGroup(userId, groupId)) {
            throw new ForbiddenException("User is not allowed to read the user group");
        }

        // Get number and size
        int number = page.getNumber();
        int size = page.getSize();

        // Calculated start and end
        int start = number * size;
        int end = start + size;

        // Get user group
        List<Object[]> objects = SettingsLocalServiceUtil.readUserGroup(
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
                // Count the size of the group
                Integer totalElements = SettingsLocalServiceUtil.countUserGroupUsers(
                        userId, group.getGroupId(), ignoreDefaultUser, ignoreDeactivatedUser
                );

                // Create page
                Page groupPage = new Page();
                groupPage.setNumber(number);
                groupPage.setSize(size);
                groupPage.setTotalElements(totalElements);
                groupPage.setTotalPages((int) Math.ceil(totalElements / (double) size));

                // Add page and set list strategy
                group.setPage(groupPage);
                group.setListStrategy(BuddyListStrategy.GROUPS);
                group.setListGroup(BuddyListGroup.USER);
            }

            // Deserialize buddy from object, buddy starts at 2
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
}
