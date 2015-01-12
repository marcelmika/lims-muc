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
import com.marcelmika.limsmuc.api.events.buddy.UpdatePasswordRequestEvent;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePasswordResponseEvent;
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

    // Services
    BuddyCoreService coreService = BuddyCoreServiceUtil.getBuddyCoreService();

    // Log
    private static Log log = LogFactoryUtil.getLog(UserListener.class);

    /**
     * Called whenever the user is removed from the portal
     *
     * @param user User
     */
    @Override
    public void onAfterRemove(User user) {

        // Create buddy from portal user
        Buddy buddy = Buddy.fromPortalUser(user);

        // Delete the user
        deleteBuddy(buddy);
    }

    /**
     * Called whenever the user is updated
     *
     * @param user User
     */
    @Override
    public void onAfterUpdate(User user) {

        // Create buddy from portal user
        Buddy buddy = Buddy.fromPortalUser(user);

        // If the password is null there is no need to update it. Since password is always mandatory.
        // Furthermore, if the password is null it means that some other property was updated.
        if (buddy.getPassword() != null) {
            updatePassword(buddy);
        }
    }

    /**
     * Updates buddy's password
     *
     * @param buddy Buddy
     */
    private void updatePassword(Buddy buddy) {

        // Update password
        UpdatePasswordResponseEvent responseEvent = coreService.updatePassword(
                new UpdatePasswordRequestEvent(buddy.toBuddyDetails())
        );

        // Failure
        if (!responseEvent.isSuccess()) {

            // Notify the admin about the error
            if (log.isWarnEnabled()) {
                log.warn(String.format("Update password %s: %s",
                        responseEvent.getStatus(), responseEvent.getExceptionMessage()
                ));
            }

            // Provide more detailed description of the issue by printing the exception
            if (log.isDebugEnabled()) {
                log.debug(responseEvent.getException());
            }
        }
    }

    /**
     * Removes buddy from the system
     *
     * @param buddy Buddy
     */
    private void deleteBuddy(Buddy buddy) {

        // Remove buddy
        DeleteBuddyResponseEvent responseEvent = coreService.removeBuddy(
                new DeleteBuddyRequestEvent(buddy.toBuddyDetails())
        );

        // Failure
        if (!responseEvent.isSuccess()) {

            // Notify the admin about the error
            if (log.isWarnEnabled()) {
                log.warn(String.format("Remove user %s: %s",
                        responseEvent.getStatus(), responseEvent.getExceptionMessage()
                ));
            }
        }
    }
}