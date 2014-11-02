/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.properties;

import com.marcelmika.lims.portal.domain.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 14/10/14
 * Time: 10:00
 */
public interface PropertiesManager {

    /**
     * Sets up all portlet properties. Decides which source of properties should be taken into account.
     * It doesn't matter how many times the method is called. The setup will be called just once.
     *
     * @param preferences PortletPreferences
     */
    public void setup(PortletPreferences preferences);

    /**
     * Updates portlet preferences based on the properties. Preferences are stored and updated
     * in current instance of Environment as well. Thanks to that the changes are visible immediately.
     * If the property in the properties object is null, nothing is updated.
     *
     * @param preferences PortletPreferences
     * @param properties  Properties
     * @throws Exception
     */
    public void updatePortletPreferences(PortletPreferences preferences, Properties properties) throws Exception;

}
