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

package com.marcelmika.limsmuc.persistence.generated.service.impl;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.marcelmika.limsmuc.persistence.generated.model.Panel;
import com.marcelmika.limsmuc.persistence.generated.service.base.PanelLocalServiceBaseImpl;

/**
 * The implementation of the panel local service.
 * <p/>
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.limsmuc.persistence.generated.service.PanelLocalService} interface.
 * <p/>
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.PanelLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.PanelLocalServiceUtil
 */
public class PanelLocalServiceImpl extends PanelLocalServiceBaseImpl {

    /*
     * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this interface directly. Always use {@link com.marcelmika.limsmuc.persistence.generated.service.PanelLocalServiceUtil} to access the panel local service.
	 */

    // Log
    @SuppressWarnings("unused")
    private static Log log = LogFactoryUtil.getLog(PanelLocalServiceImpl.class);

    /**
     * Returns panel. Creates new if not found
     *
     * @param userId of the user
     * @return created panel
     * @throws SystemException
     */
    @Override
    public Panel getPanelByUser(long userId) throws SystemException {

        // Fetch panel
        Panel panel = panelPersistence.fetchByUserId(userId);

        // No panel found => create a new one
        if (panel == null) {
            long pid = counterLocalService.increment();
            panel = panelPersistence.create(pid);
            panel.setUserId(userId);
            panelPersistence.update(panel, true);
        }

        return panel;
    }

    /**
     * Returns panel based on the user id
     *
     * @param userId of the user
     * @return found panel or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Panel fetchByUserId(long userId) throws SystemException {
        return panelPersistence.fetchByUserId(userId);
    }

    /**
     * Returns panel based on the user id
     *
     * @param userId id of the user
     * @param useCache true if the cache should be used
     * @return found panel or null if nothing was found
     * @throws SystemException
     */
    @Override
    public Panel fetchByUserId(long userId, boolean useCache) throws SystemException {
        return panelPersistence.fetchByUserId(userId, useCache);
    }

    /**
     * Creates new plain panel
     *
     * @return created panel
     * @throws SystemException
     */
    @Override
    public Panel createPanel() throws SystemException {
        return panelPersistence.create(counterLocalService.increment());
    }

    /**
     * Saves panel to database
     *
     * @param panel Panel
     * @throws SystemException
     */
    @Override
    public void savePanel(Panel panel) throws SystemException {
        panelPersistence.update(panel, false);
    }

    /**
     * Updates user's active panel
     *
     * @param userId      of the user
     * @param activePanel that will replace the old value
     * @throws SystemException
     */
    @Override
    public void updateActivePanel(long userId, String activePanel) throws SystemException {
        // Get user settings
        Panel panel = getPanelByUser(userId);

        if (panel != null) {
            panel.setActivePanelId(activePanel);
            panelPersistence.update(panel, true);
        }
    }

}