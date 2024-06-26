/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.service;

import com.marcelmika.limsmuc.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.DisableChatResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.EnableChatResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.GetConnectedBuddiesRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.GetConnectedBuddiesResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateActivePanelRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateActivePanelResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateAllConnectionsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateAllConnectionsResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.UpdateSettingsResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 3/30/14
 * Time: 8:29 PM
 */
public interface SettingsPersistenceService {

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event);

    /**
     * Update buddy's active panel (panel which is open)
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event);

    /**
     * Update buddy's settings
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    UpdateSettingsResponseEvent updateSettings(UpdateSettingsRequestEvent event);

    /**
     * Update all users connections
     *
     * @param event Request event
     * @return Response event
     */
    UpdateAllConnectionsResponseEvent updateAllConnections(UpdateAllConnectionsRequestEvent event);

    /**
     * Returns a list of buddies that are currently connected
     *
     * @param event Request event
     * @return Response event
     */
    GetConnectedBuddiesResponseEvent getConnectedBuddies(GetConnectedBuddiesRequestEvent event);

    /**
     * Enables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    EnableChatResponseEvent enableChat(EnableChatRequestEvent event);

    /**
     * Disables chat for buddy
     *
     * @param event Request event for logout method
     * @return Response event for logout method
     */
    DisableChatResponseEvent disableChat(DisableChatRequestEvent event);

}
