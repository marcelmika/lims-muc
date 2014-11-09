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
import com.marcelmika.lims.api.environment.Environment;
import com.marcelmika.lims.api.events.settings.UpdateAllConnectionsRequestEvent;
import com.marcelmika.lims.api.events.settings.UpdateAllConnectionsResponseEvent;
import com.marcelmika.lims.core.service.SettingsCoreService;
import com.marcelmika.lims.core.service.SettingsCoreServiceUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/11/14
 * Time: 11:13
 */
public class UpdateConnectionsJob implements MessageListener {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(UpdateConnectionsJob.class);

    // Services
    SettingsCoreService coreService = SettingsCoreServiceUtil.getSettingsCoreService();

    @Override
    public void receive(Message message) throws MessageListenerException {

        // Get the connection threshold
        Integer connectionThreshold = Environment.getConnectionLostThreshold();

        // Update connections is turned off
        if (connectionThreshold == 0) {
            if (log.isDebugEnabled()) {
                log.debug("[JOB] Update connections: TURNED OFF");
            }
            // End here
            return;
        }

        // This is for time profiling purposes
        long start = 0;

        // Log debug
        if (log.isDebugEnabled()) {
            start = System.currentTimeMillis();
        }

        // Update connections
        UpdateAllConnectionsResponseEvent responseEvent = coreService.updateAllConnections(
                new UpdateAllConnectionsRequestEvent(connectionThreshold)
        );

        // Log error
        if (!responseEvent.isSuccess()) {
            // Log
            if (log.isErrorEnabled()) {
                log.error(responseEvent.getException());
            }
        }

        // Log debug
        if (log.isDebugEnabled()) {
            long end = System.currentTimeMillis();
            log.debug(String.format("[JOB] Update connections: %dms", (end - start)));
        }

    }
}
