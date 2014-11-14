/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.session.store;

import com.marcelmika.limsmuc.jabber.session.UserSession;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 07/09/14
 * Time: 20:48
 */
public interface UserSessionStoreListener {

    /**
     * Called whenever the user session is added to the user session store
     *
     * @param userSession UserSession
     */
    public void userSessionAdded(UserSession userSession);

    /**
     * Called whenever the user session with the particular id is removed from the user session store
     *
     * @param id of the user session
     */
    public void userSessionRemoved(Long id);

}
