/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.settings;

import com.marcelmika.limsmuc.api.events.RequestEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/11/14
 * Time: 11:23
 */
public class UpdateAllConnectionsRequestEvent extends RequestEvent {

    private Integer connectionThreshold;

    public UpdateAllConnectionsRequestEvent(Integer connectionThreshold) {
        this.connectionThreshold = connectionThreshold;
    }

    public Integer getConnectionThreshold() {
        return connectionThreshold;
    }
}
