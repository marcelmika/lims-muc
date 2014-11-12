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
import com.marcelmika.lims.persistence.generated.model.Panel;
import com.marcelmika.lims.persistence.generated.model.Settings;
import com.marcelmika.lims.persistence.generated.service.base.SynchronizationLocalServiceBaseImpl;

import java.util.Date;
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
     * Synchronizes LIMS SUC v1.2.0
     *
     * @throws SystemException
     */
    @Override
    public void synchronizeSUC_1_2_0() throws SystemException {
        // Settings
        synchronizeSUCSettings_1_2_0();
        // Panel
        synchronizeSUCPanel_1_2_0();
    }

    /**
     * Sync SUC Settings table for SUC 1.2.0
     *
     * @throws SystemException
     */
    private void synchronizeSUCSettings_1_2_0() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = 100;

        do {
            // Find start and end
            int start = index * step;
            int end = (index + 1) * step;

            // Get from db
            objects = synchronizationFinder.findSUCSettings_1_2_0(start, end);

            for (Object[] object : objects) {

                // Get the user ID
                Long userId = (Long) object[1];

                // If the settings for the particular user are already there do nothing
                Settings settings = settingsPersistence.fetchByUserId(userId);
                if (settings != null) {
                    continue;
                }

                // Map suc settings object to muc settings object
                settings = settingsPersistence.create(counterLocalService.increment());
                settings.setUserId((Long) object[1]);
                settings.setPresence((String) object[2]);
                settings.setPresenceUpdatedAt(new Date((Long) object[3]));
                settings.setMute((Boolean) object[4]);
                settings.setChatEnabled((Boolean) object[5]);
                settings.setAdminAreaOpened((Boolean) object[6]);
                // Presence needs to be set to online because of the different presence system in muc
                if (settings.getPresence().equals("presence.off")) {
                    settings.setPresence("presence.online");
                }
                settings.setConnected(false);
                settings.setConnectedAt(new Date(0));
                // Save
                settingsPersistence.update(settings, false);
            }

            // Increase index
            index++;

        } while (objects.size() != 0); // Continue until there are no more objects
    }

    /**
     * Sync SUC Panel table for SUC 1.2.0
     *
     * @throws SystemException
     */
    private void synchronizeSUCPanel_1_2_0() throws SystemException {

        List<Object[]> objects;
        int index = 0;
        int step = 100;

        do {
            // Find start and end
            int start = index * step;
            int end = (index + 1) * step;

            // Get from db
            objects = synchronizationFinder.findSUCPanel_1_2_0(start, end);

            for (Object[] object : objects) {
                // Get the user ID
                Long userId = (Long) object[1];

                // If the panel row for the particular user is already there do nothing
                Panel panel = panelPersistence.fetchByUserId(userId);
                if (panel != null) {
                    continue;
                }

                // Map suc panel object to muc object
                panel = panelPersistence.create(counterLocalService.increment());
                panel.setUserId((Long) object[1]);
                panel.setActivePanelId((String) object[2]);
                // Save
                panelPersistence.update(panel, false);
            }

            // Increase index
            index++;

        } while (objects.size() != 0); // Continue until there are no more objects
    }
}