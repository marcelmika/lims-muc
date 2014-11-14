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
 * Utility class that holds an instance of BuddyCoreService.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/6/14
 * Time: 8:42 AM
 */
public class BuddyCoreServiceUtil {

    private static BuddyCoreService buddyCoreService;

    /**
     * Return Buddy Core Service implementation
     *
     * @return BuddyCoreService
     */
    public static BuddyCoreService getBuddyCoreService() {
        return buddyCoreService;
    }

    /**
     * Inject Buddy Core Service via Dependency Injection
     *
     * @param buddyCoreService BuddyCoreService
     */
    public void setBuddyCoreService(BuddyCoreService buddyCoreService) {

        BuddyCoreServiceUtil.buddyCoreService = buddyCoreService;
    }


}
