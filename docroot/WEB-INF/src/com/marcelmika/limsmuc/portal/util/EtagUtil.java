/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.util;

import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.portal.domain.Group;
import com.marcelmika.limsmuc.portal.domain.GroupCollection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 16/04/15
 * Time: 16:56
 */
public class EtagUtil {

    /**
     * Calculates etag of the group collection
     *
     * @param groupCollection GroupCollection
     * @return generated etag
     */
    public static int calculateEtag(GroupCollection groupCollection) {

        // Etag is made of the last modification date and the list strategy type. Thus if the group collection
        // changes its modification date or its type the client will be forced to download updated version
        int etag = groupCollection.getListStrategy() != null ? groupCollection.getListStrategy().hashCode() : 0;

        // Add deactivated user etag
        if (Environment.getBuddyListIgnoreDeactivatedUser()) {
            etag += 1;
        }

        // Add site group etag if enabled
        if (Environment.isBuddyListGroupSiteEnabled()) {
            etag += Environment.BuddyListGroup.SITE.hashCode();
        }

        // Add social group etag if enabled
        if (Environment.isBuddyListGroupSocialEnabled()) {
            etag += Environment.BuddyListGroup.SOCIAL.hashCode();
        }

        // Add user group etag if enabled
        if (Environment.isBuddyListGroupUserEnabled()) {
            etag += Environment.BuddyListGroup.USER.hashCode();
        }

        // Calculate etag of groups
        for (Group group : groupCollection.getGroups()) {
            if (group.getGroupId() != null) {
                etag += group.getGroupId().hashCode();
            }
        }

        return etag;
    }
}
