/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.portlet;

import com.liferay.compat.portal.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.theme.ThemeDisplay;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 09/10/14
 * Time: 12:30
 */
public class PermissionDetector {


    /**
     * Check if the user that sent the request is also an admin
     *
     * @param request ResourceRequest
     * @return true if the user is admin
     */
    public static boolean isAdmin(ResourceRequest request) {
        // Cast to portlet request
        PortletRequest portletRequest = PortalUtil.getLiferayPortletRequest(request);

        return isAdmin(portletRequest);
    }

    /**
     * Checks if the user that sent the request is also an admin
     *
     * @param request PortletRequest
     * @return true if the user is admin
     */
    public static boolean isAdmin(PortletRequest request) {
        // Get permission checker
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
        PermissionChecker permissionChecker = themeDisplay.getPermissionChecker();

        // Returns true if the user is a universal administrator.
        return permissionChecker.isOmniadmin();
    }

}
