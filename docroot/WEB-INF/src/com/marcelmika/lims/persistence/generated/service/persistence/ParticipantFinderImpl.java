/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.lims.persistence.generated.model.Participant;

import javax.management.Query;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/10/14
 * Time: 10:07
 */
public class ParticipantFinderImpl extends BasePersistenceImpl<Participant> implements ParticipantFinder {

    // Log
    private static Log log = LogFactoryUtil.getLog(ParticipantFinderImpl.class);

    // SQL routes
    private static final String PARTICIPATED_CONVERSATIONS = ParticipantFinder.class.getName() + ".participatedConversations";

    /**
     * Finds all participant objects where the user participates
     *
     * @param participantId id of the participant
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
            String sql = getParticipatedConversationsSQL();

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("pid", Type.LONG);
            query.addScalar("cid", Type.LONG);
            query.addScalar("participantId", Type.LONG);
            query.addScalar("unreadMessagesCount", Type.INTEGER);
            query.addScalar("isOpened", Type.BOOLEAN);
            query.addScalar("openedAt", Type.LONG); // TODO: Rewrite to date

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(participantId);

            // Get the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Prepares SQL for the participated conversations query
     *
     * @return SQL string
     * @throws Exception
     */
    private String getParticipatedConversationsSQL() throws Exception {
        // Get custom query sql (check /src/custom-sql/default.xml)
        return CustomSQLUtil.get(PARTICIPATED_CONVERSATIONS);
    }
}
