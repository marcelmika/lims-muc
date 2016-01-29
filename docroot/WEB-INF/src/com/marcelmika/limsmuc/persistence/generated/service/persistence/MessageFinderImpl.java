/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.generated.service.persistence;

import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.limsmuc.persistence.generated.model.Message;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 19/10/14
 * Time: 09:55
 */
public class MessageFinderImpl extends BasePersistenceImpl<Message> implements MessageFinder {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(MessageFinderImpl.class);

    // SQL routes
    private static final String FIND_ALL_MESSAGES = MessageFinder.class.getName() + ".findAllMessages";
    private static final String COUNT_ALL_MESSAGES = MessageFinder.class.getName() + ".countAllMessages";
    private static final String FIRST_MESSAGE = MessageFinder.class.getName() + ".firstMessage";
    private static final String LAST_MESSAGE = MessageFinder.class.getName() + ".lastMessage";

    // Placeholders
    private static final String PLACEHOLDER_STOPPER = "[$STOPPER$]";

    // Date formatter
    private static final DateFormat dateFormatISO8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private static final String ORACLE_ISO8601_DATE_FORMAT = "yyyy-MM-dd\"T\"HH24:mi:ss\"Z\"";

    /**
     * Finds all messages related to the given conversation.
     * If stopper id is set the result will not contain messages that are
     * before the stopper message
     *
     * @param cid      id of the conversation related to the messages
     * @param pageSize size of the returned list
     * @return List of objects where each objects contains a message info
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findAllMessages(Long cid, Integer pageSize) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getFindAllMessagesSQL();

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("mid", Type.LONG);
            query.addScalar("messageType", Type.INTEGER);
            query.addScalar("creatorId", Type.LONG);
            query.addScalar("createdAd", Type.CALENDAR);
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
     * Counts number of messages related to the given conversation.
     * If stopper id is set the result will not contain messages that are
     * before the stopper message
     *
     * @param cid     id of the conversation related to the messages
     * @param stopper message
     * @return number of messages
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Integer> is unchecked
    public Integer countAllMessages(Long cid, Message stopper) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getCountAllMessagesSQL(stopper);

            // Create query from SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("messageCount", Type.INTEGER);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(cid);

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
     * Returns the first message in the conversation
     *
     * @param cid id of the conversation related to the messages
     * @return first message in the conversation
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public Object[] firstMessage(Long cid) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getFirstMessageSQL();

            // Create query form SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("mid", Type.LONG);
            query.addScalar("messageType", Type.INTEGER);
            query.addScalar("creatorId", Type.LONG);
            query.addScalar("createdAd", Type.CALENDAR);
            query.addScalar("body", Type.STRING);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(cid);

            // Get the result
            List<Object[]> result = (List<Object[]>) QueryUtil.list(query, getDialect(), 0, 1);

            // Return the message object
            return result.size() != 0 ? result.get(0) : null;

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Returns the last message in the conversation
     *
     * @param cid id of the conversation related to the messages
     * @return last message in the conversation
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public Object[] lastMessage(Long cid) throws SystemException {

        Session session = null;

        try {
            // Open the database session
            session = openSession();
            // Generate SQL
            String sql = getLastMessageSQL();

            // Create query form SQL
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("mid", Type.LONG);
            query.addScalar("messageType", Type.INTEGER);
            query.addScalar("creatorId", Type.LONG);
            query.addScalar("createdAd", Type.CALENDAR);
            query.addScalar("body", Type.STRING);

            // Add parameters to query
            QueryPos queryPos = QueryPos.getInstance(query);
            queryPos.add(cid);

            // Get the result
            List<Object[]> result = (List<Object[]>) QueryUtil.list(query, getDialect(), 0, 1);

            // Return the message object
            return result.size() != 0 ? result.get(0) : null;

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Prepares SQL for the find all messages query
     *
     * @return SQL string
     * @throws SystemException
     */
    private String getFindAllMessagesSQL() throws SystemException {
        // Get custom query sql (check /src/custom-sql/default.xml)
        return CustomSQLUtil.get(FIND_ALL_MESSAGES);
    }

    /**
     * Prepares SQL for the count all messages query
     *
     * @param stopper message
     * @return SQL string
     * @throws SystemException
     */
    private String getCountAllMessagesSQL(Message stopper) throws SystemException {

        // Get custom query sql (check /src/custom-sql/default.xml)
        String sql = CustomSQLUtil.get(COUNT_ALL_MESSAGES);

        // Replace stopper id
        if (stopper != null && stopper.getCreatedAt() != null) {
            // Format the date
            String createdAt = dateFormatISO8601.format(stopper.getCreatedAt());
            String placeholderStopper;

            // Fix for issue #362 with the "not a valid month_ on Oracle" error
            if (getDB().getType().equals(DB.TYPE_ORACLE)) {
                placeholderStopper = String.format(
                        "AND Limsmuc_Message.createdAt >= to_date('%s', '%s')", createdAt, ORACLE_ISO8601_DATE_FORMAT
                );
            } else {
                placeholderStopper = String.format("AND Limsmuc_Message.createdAt >= '%s'", createdAt);
            }

            sql = StringUtil.replace(sql, PLACEHOLDER_STOPPER, placeholderStopper);

        } else {
            sql = StringUtil.replace(sql, PLACEHOLDER_STOPPER, StringPool.BLANK);
        }

        return sql;
    }

    /**
     * Prepares SQL for the first message query
     *
     * @return SQL string
     * @throws SystemException
     */
    private String getFirstMessageSQL() throws SystemException {
        // Get custom query sql (check /src/custom-sql/default.xml)
        return CustomSQLUtil.get(FIRST_MESSAGE);
    }

    /**
     * Prepares SQL for the last message query
     *
     * @return SQL string
     * @throws SystemException
     */
    private String getLastMessageSQL() throws SystemException {
        // Get custom query sql (check /src/custom-sql/default.xml)
        return CustomSQLUtil.get(LAST_MESSAGE);
    }
}
