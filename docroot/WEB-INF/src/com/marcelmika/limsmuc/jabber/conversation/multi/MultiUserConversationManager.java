/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.jabber.conversation.multi;

import org.jivesoftware.smack.XMPPConnection;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/3/14
 * Time: 11:39 PM
 */
public interface MultiUserConversationManager {

    /**
     * Sets connection
     *
     * @param connection Connection
     */
    void setConnection(XMPPConnection connection);

    /**
     * Sets company id to the group manager
     *
     * @param companyId Long
     */
    void setCompanyId(Long companyId);

}
