/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.portlet

import com.marcelmika.limsmuc.portal.domain.Properties
import org.springframework.stereotype.Component
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.portlet.bind.annotation.RenderMapping

import javax.portlet.RenderRequest
import javax.portlet.RenderResponse

/**
 * @author Ing. Marcel Mika
 * *
 * @link http://marcelmika.com
 * * Date: 24/09/16
 * * Time: 11:45
 */
@Component
@Suppress("unused")
@RequestMapping("view")
open class SettingsPortlet {

    //---------------------------------------------------------------------------------------------
    // Properties
    //---------------------------------------------------------------------------------------------

    // Holds views related to the portlet
    enum class View(val path: String) {
        Main("settings/view")
    }

    // Holds attributes passed to view
    enum class Attribute(val key: String) {
        Properties("properties")
    }

    @RenderMapping
    fun handleRenderRequest(request: RenderRequest, response: RenderResponse, model: ModelMap): String {


        // Pass attributes to view
        request.setAttribute(Attribute.Properties.key, Properties.fromEnvironment())


        return View.Main.path
    }

}
