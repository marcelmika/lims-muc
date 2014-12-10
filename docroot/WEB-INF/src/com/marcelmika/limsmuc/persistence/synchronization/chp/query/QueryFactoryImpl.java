/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization.chp.query;

import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.limsmuc.persistence.generated.service.persistence.SynchronizationFinder;
import com.marcelmika.limsmuc.persistence.synchronization.QueryFactory;
import com.marcelmika.limsmuc.persistence.synchronization.Version;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 21/11/14
 * Time: 18:20
 */
public class QueryFactoryImpl implements QueryFactory {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(QueryFactoryImpl.class);

    // Tables
    private static final String STATUS = SynchronizationFinder.class.getName() + ".Status.CHP-";
    private static final String ENTRY = SynchronizationFinder.class.getName() + ".Entry.CHP-";

    // Versions
    private static final String VERSION_2_0_5 = "2.0.5";

    /**
     * Returns sql query for the settings table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    @Override
    public SQLQuery createSettingsQuery(Version version, Session session) {

        SQLQuery query = null;

        // Chat portlet v2.0.5
        if (version == Version.CHAT_PORTLET_2_0_5) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(STATUS + VERSION_2_0_5);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
            query.addScalar("modifiedDate", Type.LONG);
            query.addScalar("playSound", Type.BOOLEAN);
        }

        return query;
    }

    /**
     * Returns sql query for the panel table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    @Override
    public SQLQuery createPanelQuery(Version version, Session session) {

        SQLQuery query = null;

        // Chat portlet v2.0.5
        if (version == Version.CHAT_PORTLET_2_0_5) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(STATUS + VERSION_2_0_5);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("userId", Type.LONG);
        }

        return query;
    }

    /**
     * Returns sql query for the conversation table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    @Override
    public SQLQuery createConversationQuery(Version version, Session session) {

        SQLQuery query = null;

        // Chat portlet v2.0.5
        if (version == Version.CHAT_PORTLET_2_0_5) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(ENTRY + VERSION_2_0_5);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("fromUserId", Type.LONG);
            query.addScalar("toUserId", Type.LONG);
        }

        return query;
    }

    /**
     * Returns sql query for the participant table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    @Override
    public SQLQuery createParticipantQuery(Version version, Session session) {

        SQLQuery query = null;

        // Chat portlet v2.0.5
        if (version == Version.CHAT_PORTLET_2_0_5) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(ENTRY + VERSION_2_0_5);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("fromUserId", Type.LONG);
            query.addScalar("toUserId", Type.LONG);
        }

        return query;
    }

    /**
     * Returns sql query for the message table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    @Override
    public SQLQuery createMessageQuery(Version version, Session session) {

        SQLQuery query = null;

        // Chat portlet v2.0.5
        if (version == Version.CHAT_PORTLET_2_0_5) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(ENTRY + VERSION_2_0_5);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("entryId", Type.LONG);
            query.addScalar("fromUserId", Type.LONG);
            query.addScalar("toUserId", Type.LONG);
            query.addScalar("createDate", Type.LONG);
            query.addScalar("content", Type.STRING);
        }

        return query;
    }

}
