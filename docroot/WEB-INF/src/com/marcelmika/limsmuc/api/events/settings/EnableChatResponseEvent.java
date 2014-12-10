/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.events.settings;

import com.marcelmika.limsmuc.api.events.ResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 4:25 PM
 */
public class EnableChatResponseEvent extends ResponseEvent {

    public static EnableChatResponseEvent success(String result) {
        EnableChatResponseEvent event = new EnableChatResponseEvent();
        event.result = result;
        event.success = true;

        return event;
    }

    public static EnableChatResponseEvent failure(String result, Throwable exception) {
        EnableChatResponseEvent event = new EnableChatResponseEvent();
        event.result = result;
        event.success = false;
        event.exception = exception;

        return event;
    }

}
