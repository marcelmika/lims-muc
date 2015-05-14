/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.hooks;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.LoginBuddyResponseEvent;
import com.marcelmika.limsmuc.core.service.BuddyCoreService;
import com.marcelmika.limsmuc.core.service.BuddyCoreServiceUtil;
import com.marcelmika.limsmuc.portal.domain.Buddy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Listens to login post action. Whenever it occurs it tries to login to the Jabber
 * server with the credentials from the request.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 7:10 PM
 */
public class LoginPostAction extends Action {

    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    // Log
    private static Log log = LogFactoryUtil.getLog(LoginPostAction.class);

    @Override
    public void run(HttpServletRequest request, HttpServletResponse response) {

        try {
            // Create buddy from portal request
            Buddy buddy = Buddy.fromPortalServletRequest(request);

            // Login
            loginBuddy(buddy);
        }
        // Failure
        catch (Exception e) {
            // Log error
            if (log.isErrorEnabled()) {
                log.error(e);
            }
        }
    }

    /**
     * Login buddy
     *
     * @param buddy Buddy
     */
    private void loginBuddy(Buddy buddy) {

        // Log
        if (log.isDebugEnabled()) {
            log.debug("Login user " + buddy.getScreenName());
        }

        // Login buddy
        LoginBuddyResponseEvent responseEvent = coreService.loginBuddy(
                new LoginBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Failure
        if (!responseEvent.isSuccess()) {

            // Notify the admin about the error
            if (log.isWarnEnabled()) {
                log.warn(String.format(
                        "Login error " + responseEvent.getStatus() + ": " + responseEvent.getExceptionMessage()
                ));
            }

            // Provide more detailed description of the issue by printing the exception
            if (log.isDebugEnabled()) {
                log.debug(responseEvent.getException());
            }
        }
    }
}