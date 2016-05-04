/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.service;

import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionResponseEvent;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 04/05/16
 * Time: 16:40
 */
public class PermissionPortalServiceImpl implements PermissionPortalService {


    /**
     * Returns display permissions
     *
     * @param event Request Event
     * @return Response Event
     */
    @Override
    public GetDisplayPermissionResponseEvent getDisplayPermission(GetDisplayPermissionRequestEvent event) {
        return GetDisplayPermissionResponseEvent.success();
    }
}
