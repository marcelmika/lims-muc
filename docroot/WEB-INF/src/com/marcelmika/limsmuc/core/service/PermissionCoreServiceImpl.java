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
import com.marcelmika.limsmuc.core.license.License;
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
        // If the custom license is not enabled permission is always granted
        if (!License.isCustomLicenseEnabled()) {
            return GetDisplayPermissionResponseEvent.success();
        }

        // Ask portal if the permission can be granted
        return permissionPortalService.getDisplayPermission(event);
    }
}
