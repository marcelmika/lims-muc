/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.settings.ReadSessionLimitResponseEvent;
import com.marcelmika.limsmuc.core.domain.Buddy;
import com.marcelmika.limsmuc.core.session.BuddySessionStore;
import com.marcelmika.limsmuc.jabber.service.BuddyJabberService;
import com.marcelmika.limsmuc.persistence.service.BuddyPersistenceService;
import com.marcelmika.limsmuc.api.events.buddy.*;

/**
 * Implementation of BuddyCoreService
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/4/14
 * Time: 11:29 PM
 */
public class BuddyCoreServiceImpl implements BuddyCoreService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(BuddyCoreServiceImpl.class);

    // Dependencies
    BuddyJabberService buddyJabberService;
    BuddyPersistenceService buddyPersistenceService;
    BuddySessionStore buddySessionStore;

    /**
     * Constructor
     *
     * @param buddyJabberService      jabber service
     * @param buddyPersistenceService persistence service
     */
    public BuddyCoreServiceImpl(final BuddyJabberService buddyJabberService,
                                final BuddyPersistenceService buddyPersistenceService,
                                final BuddySessionStore buddySessionStore) {

        this.buddyJabberService = buddyJabberService;
        this.buddyPersistenceService = buddyPersistenceService;
        this.buddySessionStore = buddySessionStore;
    }

    /**
     * Login buddy to System
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public LoginBuddyResponseEvent loginBuddy(LoginBuddyRequestEvent event) {

        // Get the buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        // Login to Jabber if enabled
        if (Environment.isJabberEnabled()) {

            // [1] Connect buddy with jabber server
            ConnectBuddyResponseEvent connectResponseEvent = buddyJabberService.connectBuddy(
                    new ConnectBuddyRequestEvent(event.getDetails())
            );

            // [1.1] Return error on failure
            if (!connectResponseEvent.isSuccess()) {
                return LoginBuddyResponseEvent.failure(
                        LoginBuddyResponseEvent.Status.ERROR_JABBER,
                        connectResponseEvent.getException()
                );
            }

            // [2] Login buddy in jabber server
            LoginBuddyResponseEvent loginResponseEvent = buddyJabberService.loginBuddy(event);
            // [2.1] Return error on failure
            if (!loginResponseEvent.isSuccess()) {
                return loginResponseEvent;
            }

            // [3] Get buddy's stored presence. Thanks to that we can send buddy's last presence to the server.
            // Imagine that user logged out when he/she was e.g. DND. If they login again DND presence will be
            // sent to the jabber server.
            ReadPresenceBuddyResponseEvent readPresenceEvent = buddyPersistenceService.readPresence(
                    new ReadPresenceBuddyRequestEvent(loginResponseEvent.getDetails())
            );
            // [3.1] Update it on server. However, this will be done only if the  buddy's presence read request
            // ended with success. If it fails we simply do nothing. We don't want to interrupt login process
            // only because we can't send initial presence to the jabber server
            if (readPresenceEvent.isSuccess()) {
                buddyJabberService.updatePresence(new UpdatePresenceBuddyRequestEvent(
                                loginResponseEvent.getDetails().getBuddyId(),
                                readPresenceEvent.getPresence())
                );
            }
        }

        // Login user locally only if not over the session limit
        if(!buddySessionStore.isOverSessionLimit(buddy.getBuddyId())) {
            // Login locally
            LoginBuddyResponseEvent loginResponseEvent = buddyPersistenceService.loginBuddy(event);

            // Add buddy to session store
            if (loginResponseEvent.isSuccess()) {
                buddySessionStore.addBuddy(buddy.getBuddyId());
            }

            return loginResponseEvent;
        }
        // User is over the limit
        else {
            // However, we return the success anyway because the same values will be set whenever the user first
            // sends read settings event
            return LoginBuddyResponseEvent.success(buddy.toBuddyDetails());
        }
    }

    /**
     * Logout buddy
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public LogoutBuddyResponseEvent logoutBuddy(LogoutBuddyRequestEvent event) {

        // Get the buddy from details
        Buddy buddy = Buddy.fromBuddyDetails(event.getDetails());

        // Logout from jabber
        if (Environment.isJabberEnabled()) {
            buddyJabberService.logoutBuddy(event);
        }

        // Logout locally
        LogoutBuddyResponseEvent responseEvent = buddyPersistenceService.logoutBuddy(event);

        if (responseEvent.isSuccess()) {
            // Remove buddy from session store
            buddySessionStore.removeBuddy(buddy.getBuddyId());
        }

        return responseEvent;
    }

    /**
     * Completely removes buddy from the System
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public DeleteBuddyResponseEvent removeBuddy(DeleteBuddyRequestEvent event) {
        return buddyPersistenceService.removeBuddy(event);
    }

    /**
     * Change buddy's presence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdatePresenceBuddyResponseEvent updatePresence(UpdatePresenceBuddyRequestEvent event) {

        // Save presence to persistent service
        UpdatePresenceBuddyResponseEvent responseEvent = buddyPersistenceService.updatePresence(event);
        // Do not continue if the change presence event failed
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }
        // Save buddy presence in Jabber as well
        if (Environment.isJabberEnabled()) {
            buddyJabberService.updatePresence(event);
        }

        return responseEvent;
    }

    /**
     * Updates buddy's password
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public UpdatePasswordResponseEvent updatePassword(UpdatePasswordRequestEvent event) {
        // Only if the jabber is enabled
        if (Environment.isJabberEnabled()) {
            return buddyJabberService.updatePassword(event);
        }

        return UpdatePasswordResponseEvent.success();
    }

    /**
     * Search buddies in the system
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public SearchBuddiesResponseEvent searchBuddies(SearchBuddiesRequestEvent event) {

        // Decide if search in jabber or persistence
        boolean isJabber = Environment.getBuddyListStrategy() == Environment.BuddyListStrategy.JABBER &&
                Environment.isJabberEnabled();

        // Jabber
        if (isJabber) {
            return buddyJabberService.searchBuddies(event);
        }
        // Persistence
        else {
            return buddyPersistenceService.searchBuddies(event);
        }
    }
}
