/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.marcelmika.lims.persistence.generated.model.Synchronization;
import com.marcelmika.lims.persistence.synchronization.suc.query.SynchronizationQueryFactory;
import com.marcelmika.lims.persistence.synchronization.Version;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 11/11/14
 * Time: 22:14
 */
public class SynchronizationFinderImpl extends BasePersistenceImpl<Synchronization> implements SynchronizationFinder {

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SynchronizationFinderImpl.class);

    /**
     * Finds settings rows for the given version
     *
     * @param version of the settings table
     * @param start   int
     * @param end     int
     * @return list of objects with settings rows
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findSettings(String version, int start, int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();

            // Create query
            SQLQuery query = SynchronizationQueryFactory.createSettingsQuery(Version.fromDescription(version), session);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Finds panel rows for the given version
     *
     * @param version of the panel table
     * @param start   int
     * @param end     int
     * @return list of objects with panel rows
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findPanel(String version, int start, int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();

            // Create query
            SQLQuery query = SynchronizationQueryFactory.createPanelQuery(Version.fromDescription(version), session);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Finds conversation rows for the given version
     *
     * @param version of the conversation table
     * @param start   int
     * @param end     int
     * @return list of objects with conversation rows
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findConversation(String version, int start, int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();

            // Create query
            SQLQuery query = SynchronizationQueryFactory.createConversationQuery(Version.fromDescription(version), session);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be close if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Finds participant rows for the given version
     *
     * @param version of the conversation table
     * @param start   int
     * @param end     int
     * @return list of objects with participant rows
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findParticipant(String version, int start, int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();

            // Create query
            SQLQuery query = SynchronizationQueryFactory.createParticipantQuery(Version.fromDescription(version), session);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

    /**
     * Finds message rows for the given version
     *
     * @param version of the conversation table
     * @param start   int
     * @param end     int
     * @return list of objects with message rows
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findMessage(String version, int start, int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();

            // Create query
            SQLQuery query = SynchronizationQueryFactory.createMessageQuery(Version.fromDescription(version), session);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }
}
