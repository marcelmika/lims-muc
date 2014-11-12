/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.core.service;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/11/14
 * Time: 11:42
 */
public class SynchronizationCoreServiceUtil {

    private static SynchronizationCoreService synchronizationCoreService;

    /**
     * Returns SynchronizationCoreService implementation
     *
     * @return SynchronizationCoreService
     */
    public static SynchronizationCoreService getSynchronizationCoreService() {
        return synchronizationCoreService;
    }

    /**
     * Injects proper SynchronizationCoreService via Dependency Injection
     *
     * @param synchronizationCoreService SynchronizationCoreService
     */
    public void setSynchronizationCoreService(SynchronizationCoreService synchronizationCoreService) {
        SynchronizationCoreServiceUtil.synchronizationCoreService = synchronizationCoreService;
    }
}
