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
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.marcelmika.lims.persistence.generated.model.Synchronization;
import org.omg.CORBA.SystemException;

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


    // Settings
    private static final String FIND_SETTINGS_SUC_1_2_0 = SynchronizationFinder.class.getName() + ".findSettings.SUC-1.2.0";


    /**
     * Finds settings rows for SUC v1.2.0
     *
     * @param start int
     * @param end   int
     * @return list of objects with settings rows
     * @throws SystemException
     */
    @Override
    @SuppressWarnings("unchecked") // Cast List<Object[]> is unchecked
    public List<Object[]> findSUCSettings_1_2_0(int start, int end) throws SystemException {

        Session session = null;

        try {
            // Open database session
            session = openSession();
            // Generate SQL (check /custom-sql/synchronization.xml)
            String sql = CustomSQLUtil.get(FIND_SETTINGS_SUC_1_2_0);

            // Create query from sql
            SQLQuery query = session.createSQLQuery(sql);

            // Now we need to map types to columns
            query.addScalar("sid", Type.LONG);
            query.addScalar("userId", Type.LONG);
            query.addScalar("presence", Type.STRING);
            query.addScalar("presenceUpdatedAt", Type.LONG);
            query.addScalar("mute", Type.BOOLEAN);
            query.addScalar("chatEnabled", Type.BOOLEAN);
            query.addScalar("adminAreaOpened", Type.BOOLEAN);

            // Return the result
            return (List<Object[]>) QueryUtil.list(query, getDialect(), start, end);

        } finally {
            // Session needs to be closed if something goes wrong
            closeSession(session);
        }
    }

}
