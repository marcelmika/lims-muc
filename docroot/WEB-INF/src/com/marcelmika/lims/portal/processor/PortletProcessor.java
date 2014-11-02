/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.processor;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

/**
 * Portlet processor contains business logic which decides what controller
 * should be called based on the query received in request parameter.
 * This is an important part of the system. Every time you introduce new controller
 * or add a method to the existing one you need to add mapping to the processRequest() method.
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 6:01 PM
 */
public interface PortletProcessor {

    /**
     * Adds request further to the system and writes data to response if needed.
     * Contains logic that decides which resource should be accessed.
     *
     * @param request  ResourceRequest
     * @param response ResourceResponse
     */
    public void processRequest(ResourceRequest request, ResourceResponse response);

}
