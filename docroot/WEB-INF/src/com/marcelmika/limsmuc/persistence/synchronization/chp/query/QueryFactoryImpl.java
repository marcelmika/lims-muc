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
import com.marcelmika.limsmuc.persistence.synchronization.QueryFactory;
import com.marcelmika.limsmuc.persistence.synchronization.Version;

/**
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com
 * Date: 21/11/14
 * Time: 18:20
 */
public class QueryFactoryImpl implements QueryFactory {
    @Override
    public SQLQuery createSettingsQuery(Version version, Session session) {
        return null;
    }

    @Override
    public SQLQuery createPanelQuery(Version version, Session session) {
        return null;
    }

    @Override
    public SQLQuery createConversationQuery(Version version, Session session) {
        return null;
    }

    @Override
    public SQLQuery createParticipantQuery(Version version, Session session) {
        return null;
    }

    @Override
    public SQLQuery createMessageQuery(Version version, Session session) {
        return null;
    }
}
