/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.core.license;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 04/05/16
 * Time: 14:49
 */
public class License {

    private static final boolean buddyLimitEnabled = false;
    private static final int buddyLimit = 99;

    /**
     * Returns true only if the buddy limit is enabled and the count exceeded the limit
     *
     * @param count number of currently connected buddies
     * @return boolean
     */
    public static boolean buddyLimitExceeded(Integer count) {
        // If the buddy limit is not enabled return false because
        // the buddy limit will never be exceeded
        if (!buddyLimitEnabled) {
            return false;
        }

        // Limit exceed if the count is greater than the buddy limit
        return count > buddyLimit;
    }
}
