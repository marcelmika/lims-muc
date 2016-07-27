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
import com.marcelmika.limsmuc.portal.service.PermissionPortalService;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 04/05/16
 * Time: 15:43
 */
public class PermissionCoreServiceImpl implements PermissionCoreService {

    // Dependencies
    private PermissionPortalService permissionPortalService;

    /**
     * Constructor
     *
     * @param permissionPortalService PermissionPortalService
     */
    public PermissionCoreServiceImpl(PermissionPortalService permissionPortalService) {
        this.permissionPortalService = permissionPortalService;
    }

    /**
     * Returns display permissions
     *
     * @param event Request Event
     * @return Response Event
     */
    @Override
    public GetDisplayPermissionResponseEvent getDisplayPermission(GetDisplayPermissionRequestEvent event) {
        // Ask portal if the permission can be granted
        return permissionPortalService.getDisplayPermission(event);
    }

    /**
     * Returns instance key
     *
     * @param event Request Event
     * @return Response Event
     */
    @Override
    public GetInstanceKeyResponseEvent getInstanceKey(GetInstanceKeyRequestEvent event) {
        return permissionPortalService.getInstanceKey(event);
    }
}
