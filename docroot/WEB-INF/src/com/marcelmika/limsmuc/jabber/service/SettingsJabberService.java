/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.service;

import com.marcelmika.limsmuc.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.TestConnectionRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.TestConnectionResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/01/15
 * Time: 21:53
 */
public interface SettingsJabberService {

    /**
     * Tests connection with the jabber server
     *
     * @param event Request event
     * @return Response event
     */
    TestConnectionResponseEvent testConnection(TestConnectionRequestEvent event);

    /**
     * Reads buddy's settings
     *
     * @param event Request event
     * @return Response event
     */
    ReadSettingsResponseEvent readSettings(ReadSettingsRequestEvent event);

}
