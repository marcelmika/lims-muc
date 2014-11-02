/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.portal.processor;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 8/9/14
 * Time: 6:01 PM
 */
public class PortletProcessorUtil {

    private static PortletProcessor portletProcessor;

    /**
     * Return Portlet Processor implementation
     *
     * @return PortletProcessor
     */
    public static PortletProcessor getPortletProcessor() {
        return portletProcessor;
    }

    /**
     * Injects proper PortletProcessor via Dependency Injection
     *
     * @param portletProcessor PortletProcessor
     */
    public void setPortletProcessor(PortletProcessor portletProcessor) {
        PortletProcessorUtil.portletProcessor = portletProcessor;
    }

}
