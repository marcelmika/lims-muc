/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.core.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.events.ResponseEvent;
import com.marcelmika.lims.api.events.settings.*;
import com.marcelmika.lims.persistence.service.SettingsPersistenceService;

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

    /**
     * Constructor
     *
     * @param settingsPersistenceService persistence service
     */
    public SettingsCoreServiceImpl(final SettingsPersistenceService settingsPersistenceService) {
        this.settingsPersistenceService = settingsPersistenceService;
    }

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event) {
        return settingsPersistenceService.readSettings(event);
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
        return settingsPersistenceService.updateAllConnections(event);
    }

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event) {
        // Chat is enabled
        ResponseEvent responseEvent = settingsPersistenceService.enableChat(event);
        if (!responseEvent.isSuccess()) {
            return EnableChatResponseEvent.failure("Cannot enable chat", responseEvent.getException());
        }

        // No active panel
        responseEvent = settingsPersistenceService.updateActivePanel(
                new UpdateActivePanelRequestEvent(event.getBuddyDetails().getBuddyId(), "")
        );
        if (!responseEvent.isSuccess()) {
            return EnableChatResponseEvent.failure("Cannot enable chat", responseEvent.getException());
        }

        return EnableChatResponseEvent.success("Chat was successfully enabled");
    }

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event) {
        // Chat is disabled
        ResponseEvent responseEvent = settingsPersistenceService.disableChat(event);
        if (!responseEvent.isSuccess()) {
            return DisableChatResponseEvent.failure("Cannot disable chat", responseEvent.getException());
        }

        // No active panel
        responseEvent = settingsPersistenceService.updateActivePanel(
                new UpdateActivePanelRequestEvent(event.getBuddyDetails().getBuddyId(), "")
        );
        if (!responseEvent.isSuccess()) {
            return DisableChatResponseEvent.failure("Cannot disable chat", responseEvent.getException());
        }

        return DisableChatResponseEvent.success("Chat was successfully disabled");
    }
}
