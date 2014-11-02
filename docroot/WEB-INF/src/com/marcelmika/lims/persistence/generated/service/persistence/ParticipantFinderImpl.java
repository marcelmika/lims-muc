/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.lims.persistence.generated.model.Participant;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/10/14
 * Time: 10:07
 */
public class ParticipantFinderImpl extends BasePersistenceImpl<Participant> implements ParticipantFinder {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(ParticipantFinderImpl.class);

    // SQL routes
    private static final String FIND_PARTICIPATED_CONVERSATIONS =
            ParticipantFinder.class.getName() + ".findParticipatedConversations";
    private static final String COUNT_PARTICIPATED_CONVERSATIONS =
            ParticipantFinder.class.getName() + ".countParticipatedConversations";

    /**
     * Finds all participant objects where the user participates
     *
     * @param participantId id of the participant
     * @param start         of the list
     * @param end           of the list
     * @return a list of participant objects
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findParticipatedConversations(Long participantId,
                                                        int start,
                                                        int end) throws Exception {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getFindParticipatedConversationsSQL();

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("participantId", Type.LONG);
            query.addScalar("unreadMessagesCount", Type.INTEGER);
            query.addScalar("isOpened", Type.BOOLEAN);
            query.addScalar("openedAt", Type.CALENDAR);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(participantId);
            queryPos.add(participantId);

            // Get the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Counts all participant objects where the user participates.
     * Conversations where there is no message yet are not included.
     *
     * @param participantId id of the participant
     * @return a list of participant objects
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Integer> is unchecked
    public Integer countParticipatedConversations(Long participantId) throws Exception {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getCountParticipatedConversationsSQL();

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("participatedCount", Type.INTEGER);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(participantId);

            // Get the result
            List<Integer> result = (List<Integer>) QueryUtil.list(query, getDialect(), 0, 1);

            // Return the count
            return result.get(0);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Prepares SQL for the find participated conversations query
     *
     * @return SQL string
     * @throws Exception
     */
    private String getFindParticipatedConversationsSQL() throws Exception {
        // Get custom query sql (check /src/custom-sql/default.xml)
        return CustomSQLUtil.get(FIND_PARTICIPATED_CONVERSATIONS);
    }

    /**
     * Prepares SQL for the count participated conversations query
     *
     * @return SQL string
     * @throws Exception
     */
    private String getCountParticipatedConversationsSQL() throws Exception {
        // Get custom query sql (check /src/custom-sql/default.xml)
        return CustomSQLUtil.get(COUNT_PARTICIPATED_CONVERSATIONS);
    }
}
