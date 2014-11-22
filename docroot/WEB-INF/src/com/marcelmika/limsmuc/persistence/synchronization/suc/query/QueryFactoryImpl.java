/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization.suc.query;

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
 * Date: 13/11/14
 * Time: 14:50
 */
public class QueryFactoryImpl implements QueryFactory {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(QueryFactoryImpl.class);

    // Tables
    private static final String SETTINGS_SUC = SynchronizationFinder.class.getName() + ".Settings.SUC-";
    private static final String PANEL_SUC = SynchronizationFinder.class.getName() + ".Panel.SUC-";
    private static final String CONVERSATION_SUC = SynchronizationFinder.class.getName() + ".Conversation.SUC-";
    private static final String PARTICIPANT_SUC = SynchronizationFinder.class.getName() + ".Participant.SUC-";
    private static final String MESSAGE_SUC = SynchronizationFinder.class.getName() + ".Message.SUC-";

    // Versions
    private static final String VERSION_1_2_0 = "1.2.0";
    private static final String VERSION_1_1_0 = "1.1.0";
    private static final String VERSION_1_0_1 = "1.0.1";

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

        // SUC version v1.2.0
        if (version == Version.SUC_1_2_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(SETTINGS_SUC + VERSION_1_2_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("sid", Type.LONG);
            query.addScalar("userId", Type.LONG);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.LONG);
            query.addScalar("mute", Type.BOOLEAN);
            query.addScalar("chatEnabled", Type.BOOLEAN);
            query.addScalar("adminAreaOpened", Type.BOOLEAN);
        }
        // SUC version v1.1.0
        else if (version == Version.SUC_1_1_0) {

            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(SETTINGS_SUC + VERSION_1_1_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("sid", Type.LONG);
            query.addScalar("userId", Type.LONG);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.LONG);
            query.addScalar("mute", Type.BOOLEAN);
            query.addScalar("chatEnabled", Type.BOOLEAN);
        }
        // SUC version v1.0.1
        else if (version == Version.SUC_1_0_1) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(SETTINGS_SUC + VERSION_1_0_1);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("sid", Type.LONG);
            query.addScalar("userId", Type.LONG);
            query.addScalar("presence", Type.STRING);
            query.addScalar("mute", Type.BOOLEAN);
            query.addScalar("chatEnabled", Type.BOOLEAN);
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

        // SUC version v1.2.0
        if (version == Version.SUC_1_2_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(PANEL_SUC + VERSION_1_2_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("userId", Type.LONG);
            query.addScalar("activePanelId", Type.STRING);
        }
        // SUC version v1.1.0
        else if (version == Version.SUC_1_1_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(PANEL_SUC + VERSION_1_1_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("userId", Type.LONG);
            query.addScalar("activePanelId", Type.STRING);
        }
        // SUC version v1.0.1
        else if (version == Version.SUC_1_0_1) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(PANEL_SUC + VERSION_1_0_1);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("userId", Type.LONG);
            query.addScalar("activePanelId", Type.STRING);
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

        // SUC version v1.2.0
        if (version == Version.SUC_1_2_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(CONVERSATION_SUC + VERSION_1_2_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("cid", Type.LONG);
            query.addScalar("conversationId", Type.STRING);
            query.addScalar("conversationType", Type.STRING);
            query.addScalar("updatedAt", Type.CALENDAR);
        }
        // SUC version v1.1.0
        else if (version == Version.SUC_1_1_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(CONVERSATION_SUC + VERSION_1_1_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("cid", Type.LONG);
            query.addScalar("conversationId", Type.STRING);
            query.addScalar("conversationType", Type.STRING);
            query.addScalar("updatedAt", Type.CALENDAR);
        }
        // SUC version v1.0.1
        else if (version == Version.SUC_1_0_1) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(CONVERSATION_SUC + VERSION_1_0_1);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("cid", Type.LONG);
            query.addScalar("conversationId", Type.STRING);
            query.addScalar("conversationType", Type.STRING);
            query.addScalar("updatedAt", Type.CALENDAR);
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

        // SUC version v1.2.0
        if (version == Version.SUC_1_2_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(PARTICIPANT_SUC + VERSION_1_2_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("participantId", Type.LONG);
            query.addScalar("unreadMessagesCount", Type.INTEGER);
            query.addScalar("isOpened", Type.BOOLEAN);
            query.addScalar("openedAt", Type.LONG);
        }
        // SUC version v1.1.0
        else if (version == Version.SUC_1_1_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(PARTICIPANT_SUC + VERSION_1_1_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("participantId", Type.LONG);
            query.addScalar("unreadMessagesCount", Type.INTEGER);
            query.addScalar("isOpened", Type.BOOLEAN);
            query.addScalar("openedAt", Type.LONG);
        }
        // SUC version v1.1.0
        else if (version == Version.SUC_1_0_1) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(PARTICIPANT_SUC + VERSION_1_0_1);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("participantId", Type.LONG);
            query.addScalar("unreadMessagesCount", Type.INTEGER);
            query.addScalar("isOpened", Type.BOOLEAN);
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

        // SUC version v1.2.0
        if (version == Version.SUC_1_2_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(MESSAGE_SUC + VERSION_1_2_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("mid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("creatorId", Type.LONG);
            query.addScalar("createdAt", Type.CALENDAR);
            query.addScalar("body", Type.STRING);
        }
        // SUC version v1.1.0
        else if (version == Version.SUC_1_1_0) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(MESSAGE_SUC + VERSION_1_1_0);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("mid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("creatorId", Type.LONG);
            query.addScalar("createdAt", Type.CALENDAR);
            query.addScalar("body", Type.STRING);
        }
        // SUC version v1.0.1
        else if (version == Version.SUC_1_0_1) {
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(MESSAGE_SUC + VERSION_1_0_1);

            // Create query from sql
            query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("mid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("creatorId", Type.LONG);
            query.addScalar("createdAt", Type.CALENDAR);
            query.addScalar("body", Type.STRING);
        }

        return query;
    }
}
