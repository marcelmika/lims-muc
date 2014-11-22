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
public class SynchronizerUtil {

    // Lims SUC
    public static Synchronizer suc_1_2_0;
    public static Synchronizer suc_1_1_0;
    public static Synchronizer suc_1_0_1;

    // Chat Portlet
    public static Synchronizer chatPortlet_2_0_5;

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
            synchronizer = suc_1_2_0;
        }
        // SUC v1.1.0
        else if (version == Version.SUC_1_1_0) {
            synchronizer = suc_1_1_0;
        }
        // SUC v1.0.1
        else if (version == Version.SUC_1_0_1) {
            synchronizer = suc_1_0_1;
        }
        // Chat portlet v2.0.5
        else if (version == Version.CHAT_PORTLET_2_0_5) {
            synchronizer = chatPortlet_2_0_5;
        }

        return synchronizer;
    }

    /**
     * Inject SUC v1.2.0 Synchronizer via Dependency Injection
     *
     * @param suc_1_2_0 Synchronizer
     */
    public void setSuc_1_2_0(Synchronizer suc_1_2_0) {
        SynchronizerUtil.suc_1_2_0 = suc_1_2_0;
    }

    /**
     * Inject SUC v1.1.0 Synchronizer via Dependency Injection
     *
     * @param suc_1_1_0 Synchronizer
     */
    public void setSuc_1_1_0(Synchronizer suc_1_1_0) {
        SynchronizerUtil.suc_1_1_0 = suc_1_1_0;
    }

    /**
     * Inject SUC v1.0.1 Synchronizer via Dependency Injection
     *
     * @param suc_1_0_1 Synchronizer
     */
    public void setSuc_1_0_1(Synchronizer suc_1_0_1) {
        SynchronizerUtil.suc_1_0_1 = suc_1_0_1;
    }

    /**
     * Inject Chat Portlet v2.0.5 Synchronizer via Dependency Injection
     *
     * @param chatPortlet_2_0_5 Synchronizer
     */
    public void setChatPortlet_2_0_5(Synchronizer chatPortlet_2_0_5) {
        SynchronizerUtil.chatPortlet_2_0_5 = chatPortlet_2_0_5;
    }
}
