/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.request.parameters;

import java.util.Date;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 01/04/15
 * Time: 13:42
 */
public class ReadOpenedConversationsParameters {

    private Date since;

    public Date getSince() {
        return since;
    }

    public void setSince(Date since) {
        this.since = since;
    }
}
