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
import com.marcelmika.limsmuc.portal.domain.Properties
import javax.portlet.RenderRequest
import javax.portlet.RenderResponse

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 23/09/16
 * Time: 15:51
 */
class SettingsPortlet : MVCPortlet() {

    //---------------------------------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------------------------------

    // Holds views related to the portlet
    enum class View(val path: String) {
        Main("/html/settings/view.jsp")
    }

    // Holds attributes passed to view
    enum class Attribute(val key: String) {
        Properties("properties")
    }

    // Log
    private val log = LogFactoryUtil.getLog(SettingsPortlet::class.java)

    
    //---------------------------------------------------------------------------------------------
    // Lifecycle
    //---------------------------------------------------------------------------------------------

    override fun doView(renderRequest: RenderRequest?, renderResponse: RenderResponse?) {

        // Pass attributes to view
        renderRequest?.setAttribute(Attribute.Properties.key, Properties.fromEnvironment())

        // Set response to view.jsp
        include(View.Main.path, renderRequest, renderResponse)
    }
}