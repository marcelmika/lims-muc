/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.service;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 04/05/16
 * Time: 15:44
 */
public class PermissionCoreServiceUtil {

    private static PermissionCoreService permissionCoreService;

    /**
     * Returns PermissionCoreService implementation
     *
     * @return PermissionCoreService
     */
    public static PermissionCoreService getPermissionCoreService() {
        return permissionCoreService;
    }

    /**
     * Injects implementation of PermissionCoreService
     *
     * @param permissionCoreService PermissionCoreService
     */
    public void setPermissionCoreService(PermissionCoreService permissionCoreService) {
        PermissionCoreServiceUtil.permissionCoreService = permissionCoreService;
    }
}
