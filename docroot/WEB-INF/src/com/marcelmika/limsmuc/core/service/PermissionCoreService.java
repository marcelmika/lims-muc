/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionResponseEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 04/05/16
 * Time: 15:43
 */
public interface PermissionCoreService {

    /**
     * Returns display permissions
     *
     * @param event Request Event
     * @return Response Event
     */
    GetDisplayPermissionResponseEvent getDisplayPermission(GetDisplayPermissionRequestEvent event);

    /**
     * Returns instance key
     *
     * @param event Request Event
     * @return Response Event
     */
    GetInstanceKeyResponseEvent getInstanceKey(GetInstanceKeyRequestEvent event);

}
