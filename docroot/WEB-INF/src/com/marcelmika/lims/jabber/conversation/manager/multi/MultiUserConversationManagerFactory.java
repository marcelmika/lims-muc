/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.jabber.conversation.manager.multi;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 4/4/14
 * Time: 7:32 PM
 */
public class MultiUserConversationManagerFactory {


    public static MultiUserConversationManager buildManager() {
        return new MultiUserConversationManagerImpl();
    }

}
