/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.persistence.synchronization;

import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 22/11/14
 * Time: 18:57
 */
public interface QueryFactory {

    /**
     * Returns sql query for the settings table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    public SQLQuery createSettingsQuery(Version version, Session session);

    /**
     * Returns sql query for the panel table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    public SQLQuery createPanelQuery(Version version, Session session);

    /**
     * Returns sql query for the conversation table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    public SQLQuery createConversationQuery(Version version, Session session);

    /**
     * Returns sql query for the participant table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    public SQLQuery createParticipantQuery(Version version, Session session);

    /**
     * Returns sql query for the message table based on the given version
     *
     * @param version of the query
     * @param session database session
     * @return SQLQuery
     */
    public SQLQuery createMessageQuery(Version version, Session session);
}
