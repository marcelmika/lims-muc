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
 * Time: 14:50
 */
public enum Version {

    /**
     * Unknown version
     */
    UNKNOWN(0, "unknown"),

    /**
     * LIMS SUC v1.2.0
     */
    SUC_1_2_0(1, "SUC-1.2.0"),

    /**
     * LIMS SUC v1.1.0
     */
    SUC_1_1_0(2, "SUC-1.1.0"),

    /**
     * LIMS SUC v1.1.0
     */
    SUC_1_0_1(3, "SUC-1.0.1"),

    /**
     * CHAT PORTLET v2.0.5
     */
    CHAT_PORTLET_2_0_5(100, "CHP-2.0.5");  // Chat portlet version code is always greater than 100


    // Unique integer code
    private int code;
    // Unique string description
    private String description;

    /**
     * Private constructor
     *
     * @param code        integer code of the version
     * @param description string description of the version
     */
    private Version(int code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * Factory method that creates version enum from the code
     *
     * @param code that uniquely represents version
     * @return Version
     */
    public static Version fromCode(int code) {
        // SUC v1.2.0
        if (code == SUC_1_2_0.getCode()) {
            return SUC_1_2_0;
        }
        // SUC v1.1.0
        else if (code == SUC_1_1_0.getCode()) {
            return SUC_1_1_0;
        }
        // SUC v1.0.1
        else if (code == SUC_1_0_1.getCode()) {
            return SUC_1_0_1;
        }
        // Chat portlet v2.0.5
        else if (code == CHAT_PORTLET_2_0_5.getCode()) {
            return CHAT_PORTLET_2_0_5;
        }
        // Unknown
        else {
            return UNKNOWN;
        }
    }

    /**
     * Factory method that creates version enum from the description
     *
     * @param description that uniquely represents version
     * @return Version
     */
    public static Version fromDescription(String description) {
        // SUC v1.2.0
        if (description.equals(SUC_1_2_0.getDescription())) {
            return SUC_1_2_0;
        }
        // SUC v1.1.0
        else if (description.equals(SUC_1_1_0.getDescription())) {
            return SUC_1_1_0;
        }
        // SUC v1.0.1
        else if (description.equals(SUC_1_0_1.getDescription())) {
            return SUC_1_0_1;
        }
        // Chat Portlet v2.0.5
        else if (description.equals(CHAT_PORTLET_2_0_5.getDescription())) {
            return CHAT_PORTLET_2_0_5;
        }
        // Unknown
        else {
            return UNKNOWN;
        }
    }

    /**
     * Returns true if version is of the SUC type
     *
     * @return boolean
     */
    public boolean isSUC() {
        return this.getCode() < 100;
    }

    /**
     * Returns true if version is of the Chat Portlet type
     *
     * @return boolean
     */
    public boolean isChatPortlet() {
        return this.getCode() >= 100;
    }

    /**
     * Returns version integer code
     *
     * @return int
     */
    public int getCode() {
        return code;
    }

    /**
     * Returns version string description
     *
     * @return String
     */
    public String getDescription() {
        return description;
    }

}
