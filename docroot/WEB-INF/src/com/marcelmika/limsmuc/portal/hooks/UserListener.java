/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.hooks;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portal.model.User;
import com.marcelmika.limsmuc.api.events.buddy.DeleteBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.DeleteBuddyResponseEvent;
import com.marcelmika.limsmuc.core.service.BuddyCoreService;
import com.marcelmika.limsmuc.core.service.BuddyCoreServiceUtil;
import com.marcelmika.limsmuc.portal.domain.Buddy;

/**
 * Listens to the events from portal related to user.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class UserListener extends BaseModelListener<User> {

    // Log
    private static Log log = LogFactoryUtil.getLog(UserListener.class);
    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    @Override
    public void onAfterRemove(User user) {
        // Create buddy from portal user
        Buddy buddy = Buddy.fromPortalUser(user);
        // Logout buddy
        DeleteBuddyResponseEvent responseEvent = coreService.removeBuddy(
                new DeleteBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Log result
        if (!responseEvent.isSuccess()) {
            log.error(responseEvent.getException());
        }
    }
}