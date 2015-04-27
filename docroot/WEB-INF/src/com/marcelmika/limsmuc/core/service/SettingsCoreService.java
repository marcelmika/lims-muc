/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.marcelmika.limsmuc.api.events.settings.DisableChatRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.DisableChatResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.EnableChatRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.EnableChatResponseEvent;
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

/**
 * Several settings values are related to each buddy.
 * Those values can be changed via SettingsCoreService that serves as a port to the
 * business logic related to settings.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:43 PM
 */
public interface SettingsCoreService {

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event);

    /**
     * Reads buddy's session limit
     *
     * @param event Request event
     * @return Response event
     */
    ReadSessionLimitResponseEvent readSessionLimit(ReadSessionLimitRequestEvent event);

    /**
     * Update buddy's active panel (panel which is open)
     *
     * @param event Request event
     * @return Response event
     */
    UpdateActivePanelResponseEvent updateActivePanel(UpdateActivePanelRequestEvent event);

    /**
     * Update buddy's settings
     *
     * @param event Request event
     * @return Response event
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
     * Enables chat for buddy
     *
     * @param event Request event
     * @return Response event
     */
    EnableChatResponseEvent enableChat(EnableChatRequestEvent event);

    /**
     * Disables chat for buddy
     *
     * @param event Request event
     * @return Response event
     */
    DisableChatResponseEvent disableChat(DisableChatRequestEvent event);

    /**
     * Tests connection with the jabber server
     *
     * @param event Request event
     * @return Response event
     */
    TestConnectionResponseEvent testConnection(TestConnectionRequestEvent event);

}
