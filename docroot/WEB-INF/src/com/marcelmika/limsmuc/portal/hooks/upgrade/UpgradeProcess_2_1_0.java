/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.hooks.upgrade;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 12/05/16
 * Time: 09:41
 */
public class UpgradeProcess_2_1_0 extends UpgradeProcess {

    // Log
    private static Log log = LogFactoryUtil.getLog(UpgradeProcess_2_1_0.class);

    /**
     * Upgrade process version
     *
     * @return int
     */
    public int getThreshold() {
        return 210;
    }

    /**
     * Called when upgrade process is triggered
     *
     * @throws Exception
     */
    protected void doUpgrade() throws Exception {
        log.info("Upgrade Process (2.1.0) Started");

        // Add notifications enabled column if such column does not exist
        if (!tableHasColumn("Limsmuc_Settings", "notificationsEnabled")) {
            runSQL("alter table Limsmuc_Settings add column notificationsEnabled BOOLEAN");
        }

        log.info("Upgrade Process (2.1.0) Finished");
    }

}
