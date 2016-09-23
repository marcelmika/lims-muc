/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.portlet

import com.liferay.portal.kernel.log.LogFactoryUtil
import com.liferay.util.bridges.mvc.MVCPortlet
import javax.portlet.RenderRequest
import javax.portlet.RenderResponse

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/09/16
 * Time: 15:51
 */
class SettingsPortlet: MVCPortlet() {

    // Views
    private val VIEW_JSP_PATH = "/html/settings/view.jsp"

    // Log
    private val log = LogFactoryUtil.getLog(SettingsPortlet::class.java)

    override fun doView(renderRequest: RenderRequest?, renderResponse: RenderResponse?) {


        // Set response to view.jsp
        include(VIEW_JSP_PATH, renderRequest, renderResponse)
    }
}