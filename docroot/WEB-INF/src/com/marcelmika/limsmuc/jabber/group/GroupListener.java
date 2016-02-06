/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.group;

import com.marcelmika.limsmuc.jabber.domain.Buddy;
import com.marcelmika.limsmuc.jabber.domain.Presence;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 06/02/16
 * Time: 11:24
 */
public interface GroupListener {

    /**
     * Called when the presence changed for the given user
     *
     * @param buddy Buddy
     * @param presence Presence
     */
    void presenceChanged(Buddy buddy, Presence presence);
}
