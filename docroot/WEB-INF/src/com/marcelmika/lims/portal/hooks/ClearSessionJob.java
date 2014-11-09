/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.hooks;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/11/14
 * Time: 11:13
 */
public class ClearSessionJob implements MessageListener {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ClearSessionJob.class);


    @Override
    public void receive(Message message) throws MessageListenerException {
        log.info("SCHEDULED JOB");
    }
}
