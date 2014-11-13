/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.marcelmika.lims.persistence.generated.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.lims.persistence.generated.service.base.SynchronizationLocalServiceBaseImpl;
import com.marcelmika.lims.persistence.synchronization.SynchronizerFactory;
import com.marcelmika.lims.persistence.synchronization.Version;
import com.marcelmika.lims.persistence.synchronization.Synchronizer;

import java.util.List;

/**
 * The implementation of the synchronization local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.service.SynchronizationLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.base.SynchronizationLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.SynchronizationLocalServiceUtil
 */
public class SynchronizationLocalServiceImpl
        extends SynchronizationLocalServiceBaseImpl {

	/*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.lims.persistence.generated.service.SynchronizationLocalServiceUtil} to access the synchronization local service.
	 */

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(SynchronizationLocalServiceImpl.class);


    /**
     * Synchronizes LIMS SUC
     *
     * @throws SystemException
     */
    @Override
    public void synchronizeSUC(String version) throws SystemException {
        // Find the proper synchronizer based on the version
        Synchronizer synchronizer = SynchronizerFactory.createSynchronizer(Version.fromDescription(version));

        // Synchronizer was found
        if (synchronizer != null) {
            // Run the sync
            synchronizer.run();
        }
    }

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
    public List<Object[]> findSettings(String version, int start, int end) throws SystemException {
        return synchronizationFinder.findSettings(version, start, end);
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
    public List<Object[]> findPanel(String version, int start, int end) throws SystemException {
        return synchronizationFinder.findPanel(version, start, end);
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
    public List<Object[]> findConversation(String version, int start, int end) throws SystemException {
        return synchronizationFinder.findConversation(version, start, end);
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
    public List<Object[]> findParticipant(String version, int start, int end) throws SystemException {
        return synchronizationFinder.findParticipant(version, start, end);
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
    public List<Object[]> findMessage(String version, int start, int end) throws SystemException {
        return synchronizationFinder.findMessage(version, start, end);
    }
}