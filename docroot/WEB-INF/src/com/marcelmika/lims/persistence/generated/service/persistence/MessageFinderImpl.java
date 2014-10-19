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

import com.liferay.compat.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.dao.orm.*;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.lims.persistence.generated.model.Message;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 19/10/14
 * Time: 09:55
 */
public class MessageFinderImpl extends BasePersistenceImpl<Message> implements MessageFinder {

    // Log
    private static Log log = LogFactoryUtil.getLog(MessageFinderImpl.class);

    // Find messages SQL
    private static final String FIND_ALL_MESSAGES = MessageFinder.class.getName() + ".findAllMessages";

    // Placeholders
    private static final String PLACEHOLDER_STOPPER = "[$STOPPER$]";

    /**
     * Finds all messages related to the given conversation.
     * If stopper id is set the result will not contain messages that are
     * before the stopper message
     *
     * @param cid       id of the conversation related to the messages
     * @param pageSize  size of the returned list
     * @param stopperId id of the message that server as a stopper
     * @return List of objects where each objects contains a message info
     * @throws Exception
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findAllMessages(Long cid,
                                          Integer pageSize,
                                          Long stopperId) throws Exception {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getFindAllMessagesSQL(stopperId);

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("mid", Type.LONG);
            query.addScalar("creatorId", Type.LONG);
            query.addScalar("createdAd", Type.DATE);
            query.addScalar("body", Type.STRING);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(cid);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), 0, pageSize);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }


    /**
     * Prepares SQL for the find all message query
     *
     * @param stopperId id of the message stopper
     * @return SQL string
     * @throws Exception
     */
    private String getFindAllMessagesSQL(Long stopperId) throws Exception {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(FIND_ALL_MESSAGES);

        // Replace stopper id
        if (stopperId != null) {
            sql = StringUtil.replace(sql, PLACEHOLDER_STOPPER,
                    String.format("AND Limsmuc_Message.mid >= %d", stopperId));
        } else {
            sql = StringUtil.replace(sql, PLACEHOLDER_STOPPER, StringPool.BLANK);
        }

        return sql;
    }
}
