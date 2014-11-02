/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.jabber.service;

import com.marcelmika.lims.api.entity.ConversationDetails;
import com.marcelmika.lims.api.entity.MessageDetails;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 07/09/14
 * Time: 21:25
 */
public interface ConversationJabberServiceListener {

    /**
     * Called whenever a message is received from Jabber
     *
     * @param conversation ConversationDetails
     * @param message      MessageDetails
     */
    public void messageReceived(ConversationDetails conversation, MessageDetails message);

}
