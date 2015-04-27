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
import com.marcelmika.limsmuc.api.entity.PresenceDetails;
import com.marcelmika.limsmuc.api.entity.SettingsDetails;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.buddy.UpdatePresenceBuddyRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.DisableChatResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.EnableChatResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.GetConnectedBuddiesRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.GetConnectedBuddiesResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSessionLimitRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSessionLimitResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.TestConnectionRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.TestConnectionResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateActivePanelResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateAllConnectionsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateAllConnectionsResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateSettingsResponseEvent;
import com.marcelmika.limsmuc.core.session.BuddySessionStore;
import com.marcelmika.limsmuc.jabber.service.BuddyJabberService;
import com.marcelmika.limsmuc.jabber.service.SettingsJabberService;
import com.marcelmika.limsmuc.persistence.service.SettingsPersistenceService;

/**
 * Implementation of SettingsCoreService
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:44 PM
 */
public class SettingsCoreServiceImpl implements SettingsCoreService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SettingsCoreServiceImpl.class);


    // Dependencies
    SettingsPersistenceService settingsPersistenceService;
    SettingsJabberService settingsJabberService;
    BuddyJabberService buddyJabberService;
    BuddySessionStore buddySessionStore;

    /**
     * Constructor
     *
     * @param settingsPersistenceService persistence service
     */
    public SettingsCoreServiceImpl(final SettingsPersistenceService settingsPersistenceService,
                                   final SettingsJabberService settingsJabberService,
                                   final BuddyJabberService buddyJabberService,
                                   final BuddySessionStore buddySessionStore) {
        this.settingsPersistenceService = settingsPersistenceService;
        this.settingsJabberService = settingsJabberService;
        this.buddyJabberService = buddyJabberService;
        this.buddySessionStore = buddySessionStore;
    }

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event) {

        // Read settings from persistence
        ReadSettingsResponseEvent persistenceResponseEvent = settingsPersistenceService.readSettings(event);

        // Failure
        if (!persistenceResponseEvent.isSuccess()) {
            return persistenceResponseEvent;
        }

        // Jabber is not enabled
        if (!Environment.isJabberEnabled()) {
            return persistenceResponseEvent;
        }

        // Update the presence
        buddyJabberService.updatePresence(new UpdatePresenceBuddyRequestEvent(
                persistenceResponseEvent.getSettingsDetails().getBuddyId(),
                persistenceResponseEvent.getSettingsDetails().getPresenceDetails()
        ));

        // Read settings from jabber
        return settingsJabberService.readSettings(
                new ReadSettingsRequestEvent(persistenceResponseEvent.getSettingsDetails(), event.getBuddyDetails())
        );
    }

    /**
     * Reads buddy's session limit
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadSessionLimitResponseEvent readSessionLimit(ReadSessionLimitRequestEvent event) {
        // Get the buddy id
        Long buddyId = event.getBuddyId();

        // Check if the user is over the session limit
        boolean isOverLimit = buddySessionStore.isOverSessionLimit(buddyId);

        // Success
        return ReadSessionLimitResponseEvent.success(isOverLimit);
    }

    /**
     * Change buddy's presence
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event) {
        return settingsPersistenceService.updateActivePanel(event);
    }

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event) {
        return settingsPersistenceService.updateSettings(event);
    }

    /**
     * Update all users connections
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public UpdateAllConnectionsResponseEvent updateAllConnections(UpdateAllConnectionsRequestEvent event) {

        // Update connections
        UpdateAllConnectionsResponseEvent responseEvent = settingsPersistenceService.updateAllConnections(event);

        // Failure
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }

        // Get the list of connected users
        GetConnectedBuddiesResponseEvent connectedBuddiesResponseEvent = settingsPersistenceService.getConnectedBuddies(
                new GetConnectedBuddiesRequestEvent()
        );

        // Success
        if (connectedBuddiesResponseEvent.isSuccess()) {
            // Add connected users to store
            buddySessionStore.addBuddies(connectedBuddiesResponseEvent.getBuddies());
        }
        // Failure
        else {
            // Log warning
            if (log.isWarnEnabled()) {
                log.warn("Session store cannot be updated. This may cause incorrect limit session behaviour.");
            }
            // Log error
            if (log.isDebugEnabled()) {
                log.debug(connectedBuddiesResponseEvent.getException());
            }
        }

        // Update user presence in jabber
        if (Environment.isJabberEnabled()) {
            for (SettingsDetails settings : responseEvent.getSettings()) {
                buddyJabberService.updatePresence(new UpdatePresenceBuddyRequestEvent(
                        settings.getBuddyId(), PresenceDetails.OFFLINE
                ));
            }
        }

        // Success
        return responseEvent;
    }

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event) {

        // Enable chat
        EnableChatResponseEvent responseEvent = settingsPersistenceService.enableChat(event);

        // Failure
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }

        // No panels are opened when the chat is being enabled
        UpdateActivePanelResponseEvent updatePanelResponseEvent = settingsPersistenceService.updateActivePanel(
                new UpdateActivePanelRequestEvent(event.getBuddyDetails().getBuddyId(), "")
        );

        // Failure
        if (!updatePanelResponseEvent.isSuccess()) {
            return EnableChatResponseEvent.failure(
                    EnableChatResponseEvent.Status.ERROR_PERSISTENCE, updatePanelResponseEvent.getException()
            );
        }

        // Success
        return EnableChatResponseEvent.success();
    }

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event) {

        // Disable chat
        DisableChatResponseEvent responseEvent = settingsPersistenceService.disableChat(event);

        // Failure
        if (!responseEvent.isSuccess()) {
            return responseEvent;
        }

        // No panels are opened when the chat is being disabled
        UpdateActivePanelResponseEvent updatePanelResponseEvent = settingsPersistenceService.updateActivePanel(
                new UpdateActivePanelRequestEvent(event.getBuddyDetails().getBuddyId(), "")
        );

        // Failure
        if (!responseEvent.isSuccess()) {
            return DisableChatResponseEvent.failure(
                    DisableChatResponseEvent.Status.ERROR_PERSISTENCE, updatePanelResponseEvent.getException()
            );
        }

        // Success
        return DisableChatResponseEvent.success();
    }

    /**
     * Tests connection with the jabber server
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public TestConnectionResponseEvent testConnection(TestConnectionRequestEvent event) {
        return settingsJabberService.testConnection(event);
    }
}
