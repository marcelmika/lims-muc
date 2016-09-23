/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.portlet

import com.liferay.portal.model.Group
import com.liferay.portal.model.Portlet
import com.liferay.portal.security.permission.PermissionChecker
import com.liferay.portlet.BaseControlPanelEntry

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/09/16
 * Time: 16:05
 */
class SettingsControlPanelEntry: BaseControlPanelEntry() {

    override fun hasAccessPermission(permissionChecker: PermissionChecker?, group: Group?, portlet: Portlet?): Boolean {
        return true
    }
}