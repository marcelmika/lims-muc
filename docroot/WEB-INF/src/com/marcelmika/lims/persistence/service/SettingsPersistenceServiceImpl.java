/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.api.entity.BuddyDetails;
import com.marcelmika.lims.api.entity.SettingsDetails;
import com.marcelmika.lims.api.events.settings.*;
import com.marcelmika.lims.persistence.domain.Buddy;
import com.marcelmika.lims.persistence.domain.Settings;
import com.marcelmika.lims.persistence.generated.service.PanelLocalServiceUtil;
import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;


/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 8:29 PM
 */
public class SettingsPersistenceServiceImpl implements SettingsPersistenceService {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SettingsPersistenceServiceImpl.class);

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event) {
        // Get buddy
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            Settings settings = Settings.fromServiceBuilderModel(
                    PanelLocalServiceUtil.getPanelByUser(buddy.getBuddyId()),
                    SettingsLocalServiceUtil.updateConnection(buddy.getBuddyId(), true)
            );

            // Success
            return ReadSettingsResponseEvent.success(settings.toSettingsDetails());

        } catch (Exception exception) {
            // Failure
            return ReadSettingsResponseEvent.failure(
                    ReadSettingsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Update buddy's active panel (panel which is open)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event) {

        // Check params
        if (event.getBuddyId() == null) {
            return UpdateActivePanelResponseEvent.failure(
                    UpdateActivePanelResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        try {
            // Update active panel
            PanelLocalServiceUtil.updateActivePanel(event.getBuddyId(), event.getActivePanel());

            // Success
            return UpdateActivePanelResponseEvent.success(event.getActivePanel());

        } catch (Exception exception) {
            // Failure
            return UpdateActivePanelResponseEvent.failure(
                    UpdateActivePanelResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event) {
        SettingsDetails details = event.getSettingsDetails();

        // Check params
        if (event.getBuddyId() == null) {
            return UpdateSettingsResponseEvent.failure(
                    UpdateSettingsResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        try {
            // Get settings
            com.marcelmika.lims.persistence.generated.model.Settings settings = SettingsLocalServiceUtil.getSettingsByUser(
                    event.getBuddyId()
            );
            // Set new values
            settings.setMute(details.isMute());
            settings.setAdminAreaOpened(details.isAdminAreaOpened());
            // Save
            SettingsLocalServiceUtil.saveSettings(settings);

            // Success
            return UpdateSettingsResponseEvent.success(details);

        } catch (Exception exception) {
            // Failure
            return UpdateSettingsResponseEvent.failure(
                    UpdateSettingsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Update all users connections
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public UpdateAllConnectionsResponseEvent updateAllConnections(UpdateAllConnectionsRequestEvent event) {

        // Update all connections
        try {
            SettingsLocalServiceUtil.updateAllConnections(event.getConnectionThreshold());
        }
        // Failure
        catch (Exception exception) {
            return UpdateAllConnectionsResponseEvent.failure(
                    UpdateAllConnectionsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }

        // Success
        return UpdateAllConnectionsResponseEvent.success();
    }

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event) {
        // Get buddy
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            SettingsLocalServiceUtil.setChatEnabled(buddy.getBuddyId(), true);
            // Success
            return EnableChatResponseEvent.success("User chat enabled");

        } catch (Exception e) {
            // Failure
            return EnableChatResponseEvent.failure("Cannot enable chat", e);
        }
    }

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public DisableChatResponseEvent disableChat(DisableChatRequestEvent event) {
        // Get buddy
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            SettingsLocalServiceUtil.setChatEnabled(buddy.getBuddyId(), false);
            // Success
            return DisableChatResponseEvent.success("Chat disabled");

        } catch (Exception e) {
            // Failure
            return DisableChatResponseEvent.failure("Cannot disable chat", e);
        }
    }
}
