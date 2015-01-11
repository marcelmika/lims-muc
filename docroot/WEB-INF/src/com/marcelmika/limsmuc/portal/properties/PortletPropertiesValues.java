/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.properties;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.util.portlet.PortletProps;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class PortletPropertiesValues {

    // Version
    public static final String VERSION = getString(
            PortletPropertiesKeys.VERSION
    );

    // Properties source
    public static final String PROPERTIES_SOURCE = getString(
            PortletPropertiesKeys.PROPERTIES_SOURCE
    );

    // Excluded - Sites
    public static final String[] EXCLUDED_SITES = getStringValues(
            PortletPropertiesKeys.EXCLUDED_SITES
    );

    // Buddy List - Social Relation Types
    public static final int[] BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES = getIntegerValues(
            PortletPropertiesKeys.BUDDY_LIST_ALLOWED_SOCIAL_RELATION_TYPES
    );

    // Buddy List - Site Excludes
    public static final String[] BUDDY_LIST_SITE_EXCLUDES = getStringValues(
            PortletPropertiesKeys.BUDDY_LIST_SITE_EXCLUDES
    );

    // Buddy List - Group Excludes
    public static final String[] BUDDY_LIST_GROUP_EXCLUDES = getStringValues(
            PortletPropertiesKeys.BUDDY_LIST_GROUP_EXCLUDES
    );

    // Buddy List - Max buddy list count
    public static final Integer BUDDY_LIST_MAX_BUDDIES = getInteger(
            PortletPropertiesKeys.BUDDY_LIST_MAX_BUDDIES
    );

    // Buddy List - Max search count
    public static final Integer BUDDY_LIST_MAX_SEARCH = getInteger(
            PortletPropertiesKeys.BUDDY_LIST_MAX_SEARCH
    );

    // Buddy List - Strategy (All by default)
    public static final String BUDDY_LIST_STRATEGY = getString(
            PortletPropertiesKeys.BUDDY_LIST_STRATEGY
    );

    // Buddy List - Ignore default user
    public static final boolean BUDDY_LIST_IGNORE_DEFAULT_USER = getBoolean(
            PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEFAULT_USER
    );

    // Buddy List - Ignore deactivated user
    public static final boolean BUDDY_LIST_IGNORE_DEACTIVATED_USER = getBoolean(
            PortletPropertiesKeys.BUDDY_LIST_IGNORE_DEACTIVATED_USER
    );

    // Connection - Connection Lost Threshold
    public static final Integer CONNECTION_LOST_THRESHOLD = getInteger(
            PortletPropertiesKeys.CONNECTION_LOST_THRESHOLD
    );

    // Conversations - Max messages count
    public static final int CONVERSATION_LIST_MAX_MESSAGES = getInteger(
            PortletPropertiesKeys.CONVERSATION_LIST_MAX_MESSAGES
    );

    // Conversation Feed - Max conversations count
    public static final int CONVERSATION_FEED_MAX_CONVERSATIONS = getInteger(
            PortletPropertiesKeys.CONVERSATION_FEED_MAX_CONVERSATIONS
    );

    // Polling - Slow down threshold
    public static final int POLLING_SLOW_DOWN_THRESHOLD = getInteger(
            PortletPropertiesKeys.POLLING_SLOW_DOWN_THRESHOLD
    );

    // Jabber (Disabled by default)
    public static final Boolean JABBER_ENABLED = getBoolean(
            PortletPropertiesKeys.JABBER_ENABLED
    );

    // Jabber - Host
    public static final String JABBER_HOST = getString(
            PortletPropertiesKeys.JABBER_HOST
    );

    // Jabber - Port
    public static final int JABBER_PORT = getInteger(
            PortletPropertiesKeys.JABBER_PORT
    );

    // Jabber - Service name
    public static final String JABBER_SERVICE_NAME = getString(
            PortletPropertiesKeys.JABBER_SERVICE_NAME
    );

    // Jabber - Resource name
    public static final String JABBER_RESOURCE = getString(
            PortletPropertiesKeys.JABBER_RESOURCE
    );

    // Jabber - User Import
    public static final boolean JABBER_IMPORT_USER_ENABLED = getBoolean(
            PortletPropertiesKeys.JABBER_IMPORT_USER_ENABLED
    );

    // Jabber - SASL (Disabled by default)
    public static final Boolean JABBER_SASL_PLAIN_ENABLED = getBoolean(
            PortletPropertiesKeys.JABBER_SASL_PLAIN_ENABLED
    );

    // Jabber - SASL - Authentication ID
    public static final String JABBER_SASL_PLAIN_AUTHID = getString(
            PortletPropertiesKeys.JABBER_SASL_PLAIN_AUTHID
    );

    // Jabber - SASL - Password
    public static final String JABBER_SASL_PLAIN_PASSWORD = getString(
            PortletPropertiesKeys.JABBER_SASL_PLAIN_PASSWORD
    );

    // URL - Help
    public static final String URL_HELP = getString(
            PortletPropertiesKeys.URL_HELP
    );

    // URL - Unsupported browser
    public static final String URL_UNSUPPORTED_BROWSER = getString(
            PortletPropertiesKeys.URL_UNSUPPORTED_BROWSER
    );

    // Error Mode
    public static final Boolean ERROR_MODE_ENABLED = getBoolean(
            PortletPropertiesKeys.ERROR_MODE_ENABLED
    );

    /**
     * Returns string value from properties related to the key
     *
     * @param key of the string value
     * @return string representation of value related to the key
     */
    private static String getString(String key) {
        return GetterUtil.getString(PortletProps.get(key));
    }

    /**
     * Returns an array of string values from properties related to the key
     *
     * @param key of the string value
     * @return an array of string representation of values related to the key
     */
    private static String[] getStringValues(String key) {
        return PortletProps.getArray(key);
    }

    /**
     * Returns integer value from properties related to the key
     *
     * @param key of the integer value
     * @return integer representation of value related to the key
     */
    private static int getInteger(String key) {
        return GetterUtil.getInteger(PortletProps.get(key));
    }

    /**
     * Returns and array of int values from properties related to the key
     *
     * @param key of the integer values
     * @return an array of int representation of values related to the key
     */
    private static int[] getIntegerValues(String key) {
        return GetterUtil.getIntegerValues(PortletProps.getArray(key));
    }

    /**
     * Returns boolean value from properties related to the key
     *
     * @param key of the boolean value
     * @return boolean representation of value related to the key
     */
    private static boolean getBoolean(String key) {
        return GetterUtil.getBoolean(PortletProps.get(key));
    }

}