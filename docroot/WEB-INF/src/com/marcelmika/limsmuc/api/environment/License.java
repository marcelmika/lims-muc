/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.api.environment;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 04/05/16
 * Time: 14:49
 */
public final class License {

    // Set to true if the environment was already set up
    private static boolean isSetup = false;
    // Path to the security folder
    private static String securityPath;

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(License.class);

    /**
     * Call to setup license
     *
     * @param securityPath Path to the security folder
     */
    public static void setup(String securityPath) {
        // Do just once
        if (isSetup) {
            return;
        }
        isSetup = true;

        // Remember the security path
        License.securityPath = securityPath;
    }

    /**
     * Returns path to the security folder
     *
     * @return String path
     */
    public static String getSecurityPath() {
        return securityPath;
    }
}
