/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.api.entity.BuddyDetails;
import com.marcelmika.limsmuc.api.entity.SettingsDetails;
import com.marcelmika.limsmuc.api.events.settings.*;
import com.marcelmika.limsmuc.persistence.domain.Settings;
import com.marcelmika.limsmuc.persistence.generated.service.PanelLocalServiceUtil;
import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.List;


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

        // Get buddy from details
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            Settings settings = Settings.fromServiceBuilderModel(
                    PanelLocalServiceUtil.getPanelByUser(buddy.getBuddyId()),
                    SettingsLocalServiceUtil.updateConnection(buddy.getBuddyId(), true)
            );

            // Success
            return ReadSettingsResponseEvent.success(settings.toSettingsDetails());

        }
        // Failure
        catch (Exception exception) {
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
        }
        // Failure
        catch (Exception exception) {
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

        // Get the details
        SettingsDetails details = event.getSettingsDetails();

        // Check params
        if (event.getBuddyId() == null) {
            return UpdateSettingsResponseEvent.failure(
                    UpdateSettingsResponseEvent.Status.ERROR_WRONG_PARAMETERS
            );
        }

        try {
            // Get settings
            com.marcelmika.limsmuc.persistence.generated.model.Settings settings =
                    SettingsLocalServiceUtil.getSettingsByUser(event.getBuddyId());

            // Set new values
            settings.setMute(details.isMute());
            settings.setAdminAreaOpened(details.isAdminAreaOpened());
            // Save
            SettingsLocalServiceUtil.saveSettings(settings);

            // Success
            return UpdateSettingsResponseEvent.success(details);
        }
        // Failure
        catch (Exception exception) {
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

        try {
            // Update all connections
            List<Settings> settings = Settings.fromSettingsModel(
                    SettingsLocalServiceUtil.updateAllConnections(event.getConnectionThreshold())
            );

            // Map updated settings to settings details
            List<SettingsDetails> settingsDetails = Settings.toSettingsDetails(settings);

            // Success
            return UpdateAllConnectionsResponseEvent.success(settingsDetails);
        }
        // Failure
        catch (Exception exception) {
            return UpdateAllConnectionsResponseEvent.failure(
                    UpdateAllConnectionsResponseEvent.Status.ERROR_PERSISTENCE, exception
            );
        }
    }

    /**
     * Returns a list of buddies that are currently connected
     *
     * @param event Request event
     * @return Response event
     */
    @Override
    public GetConnectedBuddiesResponseEvent getConnectedBuddies(GetConnectedBuddiesRequestEvent event) {

        try {
            // Read connected buddies
            List<Long> connectedUsers = SettingsLocalServiceUtil.getConnectedUsers();

            // Success
            return GetConnectedBuddiesResponseEvent.success(connectedUsers);
        }
        // Failure
        catch (SystemException e) {
            return GetConnectedBuddiesResponseEvent.failure(
                    GetConnectedBuddiesResponseEvent.Status.ERROR_PERSISTENCE, e
            );
        }
    }

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    @Override
    public EnableChatResponseEvent enableChat(EnableChatRequestEvent event) {

        // Get buddy from details
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            SettingsLocalServiceUtil.setChatEnabled(buddy.getBuddyId(), true);

            // Success
            return EnableChatResponseEvent.success();
        }
        // Failure
        catch (Exception e) {
            return EnableChatResponseEvent.failure(
                    EnableChatResponseEvent.Status.ERROR_PERSISTENCE, e
            );
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

        // Get buddy from details
        BuddyDetails buddy = event.getBuddyDetails();

        try {
            // Save
            SettingsLocalServiceUtil.setChatEnabled(buddy.getBuddyId(), false);

            // Success
            return DisableChatResponseEvent.success();
        }
        // Failure
        catch (Exception e) {
            return DisableChatResponseEvent.failure(
                    DisableChatResponseEvent.Status.ERROR_PERSISTENCE, e
            );
        }
    }
}
