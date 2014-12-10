/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/11/14
 * Time: 19:01
 */
public class QueryFactoryUtil {

    private static QueryFactory sucQueryFactory;
    private static QueryFactory chatPortletQueryFactory;

    /**
     * Returns query factory based on the given version
     *
     * @param version Version
     * @return QueryFactory
     */
    public static QueryFactory getQueryFactory(Version version) {
        // Chat portlet
        if (version.isChatPortlet()) {
            return chatPortletQueryFactory;
        }
        // LIMS SUC
        else if (version.isSUC()) {
            return sucQueryFactory;
        }

        return null; // Unknown type
    }

    /**
     * Inject SUC Query Factory via Dependency Injection
     *
     * @param sucQueryFactory QueryFactory
     */
    public void setSucQueryFactory(QueryFactory sucQueryFactory) {
        QueryFactoryUtil.sucQueryFactory = sucQueryFactory;
    }

    /**
     * Inject Chat Portlet Factory via Dependency Injection
     *
     * @param chatPortletQueryFactory QueryFactory
     */
    public void setChatPortletQueryFactory(QueryFactory chatPortletQueryFactory) {
        QueryFactoryUtil.chatPortletQueryFactory = chatPortletQueryFactory;
    }
}
