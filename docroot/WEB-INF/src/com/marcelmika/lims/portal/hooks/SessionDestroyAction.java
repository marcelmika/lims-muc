/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.events.buddy.LogoutBuddyRequestEvent;
import com.marcelmika.lims.api.events.buddy.LogoutBuddyResponseEvent;
import com.marcelmika.lims.core.service.BuddyCoreService;
import com.marcelmika.lims.core.service.BuddyCoreServiceUtil;
import com.marcelmika.lims.portal.domain.Buddy;

import javax.servlet.http.HttpSession;

/**
 * Listens to the session destroy action.
 * Whenever it occurs it tries to login the user to the LIMS system
 * server with the credentials from the request.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 */
public class SessionDestroyAction extends SessionAction {

    // Log
    private static Log log = LogFactoryUtil.getLog(SessionDestroyAction.class);
    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    @Override
    public void run(HttpSession session) {

        // Create buddy from session
        Buddy buddy = Buddy.fromHttpSession(session);

        if (log.isDebugEnabled()) {
            log.debug(String.format("Session Destroyed for user %s", buddy.getBuddyId()));
        }

        // Logout buddy
        LogoutBuddyResponseEvent responseEvent = coreService.logoutBuddy(
                new LogoutBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Log error
        if (!responseEvent.isSuccess()) {
            log.error(responseEvent.getException());
        }
    }
}