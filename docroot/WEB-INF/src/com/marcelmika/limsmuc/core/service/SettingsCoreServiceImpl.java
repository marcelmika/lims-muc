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
import com.marcelmika.limsmuc.api.events.settings.*;
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

    /**
     * Constructor
     *
     * @param settingsPersistenceService persistence service
     */
    public SettingsCoreServiceImpl(final SettingsPersistenceService settingsPersistenceService,
                                   final SettingsJabberService settingsJabberService,
                                   final BuddyJabberService buddyJabberService) {
        this.settingsPersistenceService = settingsPersistenceService;
        this.settingsJabberService = settingsJabberService;
        this.buddyJabberService = buddyJabberService;
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
