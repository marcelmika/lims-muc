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
 * Utility class that holds an instance of SettingsCoreService.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 2/22/14
 * Time: 2:44 PM
 */
public class SettingsCoreServiceUtil {

    private static SettingsCoreService settingsCoreService;

    /**
     * Returns SettingsCoreService implementation
     *
     * @return SettingsCoreService
     */
    public static SettingsCoreService getSettingsCoreService() {
        return settingsCoreService;
    }

    /**
     * Injects proper SettingsCoreService via Dependency Injection
     *
     * @param settingsCoreService SettingsCoreService
     */
    public void setSettingsCoreService(SettingsCoreService settingsCoreService) {
        SettingsCoreServiceUtil.settingsCoreService = settingsCoreService;
    }

}
