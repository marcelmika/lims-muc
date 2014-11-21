/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 13/11/14
 * Time: 19:59
 */
public class SynchronizerFactory {

    // Lims SUC
    public static final Synchronizer SUC_1_2_0 =
            new com.marcelmika.limsmuc.persistence.synchronization.suc.version.v1_2_0.SynchronizerImpl();
    public static final Synchronizer SUC_1_1_0 =
            new com.marcelmika.limsmuc.persistence.synchronization.suc.version.v1_1_0.SynchronizerImpl();
    public static final Synchronizer SUC_1_0_1 =
            new com.marcelmika.limsmuc.persistence.synchronization.suc.version.v1_0_1.SynchronizerImpl();

    // Chat Portlet
    public static final Synchronizer CHAT_PORTLET_2_0_5 =
            new com.marcelmika.limsmuc.persistence.synchronization.chp.version.v2_0_5.SynchronizerImpl();

    /**
     * Returns synchronizer based on the version. Null if no synchronizer for the given version is found
     *
     * @param version of the synchronized process
     * @return Synchronizer
     */
    public static Synchronizer createSynchronizer(Version version) {

        Synchronizer synchronizer = null;

        // SUC v1.2.0
        if (version == Version.SUC_1_2_0) {
            synchronizer = SUC_1_2_0;
        }
        // SUC v1.1.0
        else if (version == Version.SUC_1_1_0) {
            synchronizer = SUC_1_1_0;
        }
        // SUC v1.0.1
        else if (version == Version.SUC_1_0_1) {
            synchronizer = SUC_1_0_1;
        }
        // Chat portlet v2.0.5
        else if (version == Version.CHAT_PORTLET_2_0_5) {
            synchronizer = CHAT_PORTLET_2_0_5;
        }

        return synchronizer;
    }

}
