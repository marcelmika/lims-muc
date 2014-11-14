/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.properties;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 14/10/14
 * Time: 09:57
 */
public class PropertiesManagerUtil {

    private static PropertiesManager propertiesManager;

    /**
     * Returns PropertiesManager implementation
     *
     * @return PropertiesManager
     */
    public static PropertiesManager getPropertiesManager() {
        return propertiesManager;
    }

    /**
     * Injects proper PropertiesManager via Dependency Injection
     *
     * @param propertiesManager PropertiesManager
     */
    public void setPropertiesManager(PropertiesManager propertiesManager) {
        PropertiesManagerUtil.propertiesManager = propertiesManager;
    }
}
