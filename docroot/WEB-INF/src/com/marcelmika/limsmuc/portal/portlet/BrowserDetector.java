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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.BrowserSnifferUtil;

import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 01/10/14
 * Time: 09:00
 */
public class BrowserDetector {

    // Minimal supported version of Internet Explorer
    private static final float MIN_SUPPORTED_VERSION_IE = 8;

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(BrowserDetector.class);

    /**
     * Returns true if the browser that sent the request is supported
     *
     * @param request RenderRequest
     * @return true if the browser is supported
     */
    public static boolean isSupportedBrowser(RenderRequest request) {
        // Cast to http servlet request
        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);

        return isSupportedBrowser(httpServletRequest);
    }

    /**
     * Returns true if the browser that sent the request is supported
     *
     * @param request HttpServletRequest
     * @return true if the browser is supported
     */
    public static boolean isSupportedBrowser(HttpServletRequest request) {
        // Check the availability of all browsers
        return validateInternetExplorer(request);
    }

    /**
     * Returns true if the browser that sent the request needs support for older
     * internet explorer browsers.
     *
     * @param request RenderRequest
     * @return true if the browser needs internet explorer support
     */
    public static boolean needsInternetExplorerSupport(RenderRequest request) {
        // Cast to http servlet request
        HttpServletRequest httpServletRequest = PortalUtil.getHttpServletRequest(request);

        // Check the availability of Internet Explorer
        boolean isIE = BrowserSnifferUtil.isIe(httpServletRequest);
        float majorVersion = BrowserSnifferUtil.getMajorVersion(httpServletRequest);

        return isIE && majorVersion <= 8;
    }

    /**
     * Returns false if the browser that sent the request is internet explorer in a version
     * that is not supported.
     *
     * @param request HttpServletRequest
     * @return true if the request is valid
     */
    private static boolean validateInternetExplorer(HttpServletRequest request) {

        // Check the availability of Internet Explorer
        boolean isIE = BrowserSnifferUtil.isIe(request);
        float majorVersion = BrowserSnifferUtil.getMajorVersion(request);

        // If it's not IE we stop validation
        return !isIE || majorVersion >= MIN_SUPPORTED_VERSION_IE;
    }
}
