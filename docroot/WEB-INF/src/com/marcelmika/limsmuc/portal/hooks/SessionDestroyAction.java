/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.hooks;

import com.liferay.portal.kernel.events.SessionAction;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.buddy.LogoutBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.LogoutBuddyResponseEvent;
import com.marcelmika.limsmuc.core.service.BuddyCoreService;
import com.marcelmika.limsmuc.core.service.BuddyCoreServiceUtil;
import com.marcelmika.limsmuc.portal.domain.Buddy;

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
        // Logout
        logoutBuddy(buddy);
    }

    /**
     * Logs out buddy
     *
     * @param buddy Buddy
     */
    private void logoutBuddy(Buddy buddy) {

        // Logout buddy
        LogoutBuddyResponseEvent responseEvent = coreService.logoutBuddy(
                new LogoutBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Log error
        if (!responseEvent.isSuccess()) {
            if (log.isWarnEnabled()) {
                log.warn(String.format("Logout %s: %s", responseEvent.getStatus(), responseEvent.getExceptionMessage()));
            }
        }

        // Log debug
        if (log.isDebugEnabled()) {
            log.debug(String.format("Session Destroyed for user %s", buddy.getBuddyId()));
        }
    }
}