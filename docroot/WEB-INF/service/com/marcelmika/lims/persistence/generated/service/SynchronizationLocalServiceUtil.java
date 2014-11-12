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

package com.marcelmika.lims.persistence.generated.service;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for Synchronization. This utility wraps
 * {@link com.marcelmika.lims.persistence.generated.service.impl.SynchronizationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see SynchronizationLocalService
 * @see com.marcelmika.lims.persistence.generated.service.base.SynchronizationLocalServiceBaseImpl
 * @see com.marcelmika.lims.persistence.generated.service.impl.SynchronizationLocalServiceImpl
 * @generated
 */
public class SynchronizationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.marcelmika.lims.persistence.generated.service.impl.SynchronizationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the synchronization to the database. Also notifies the appropriate model listeners.
	*
	* @param synchronization the synchronization
	* @return the synchronization that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Synchronization addSynchronization(
		com.marcelmika.lims.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addSynchronization(synchronization);
	}

	/**
	* Creates a new synchronization with the primary key. Does not add the synchronization to the database.
	*
	* @param sid the primary key for the new synchronization
	* @return the new synchronization
	*/
	public static com.marcelmika.lims.persistence.generated.model.Synchronization createSynchronization(
		long sid) {
		return getService().createSynchronization(sid);
	}

	/**
	* Deletes the synchronization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization that was removed
	* @throws PortalException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Synchronization deleteSynchronization(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSynchronization(sid);
	}

	/**
	* Deletes the synchronization from the database. Also notifies the appropriate model listeners.
	*
	* @param synchronization the synchronization
	* @return the synchronization that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Synchronization deleteSynchronization(
		com.marcelmika.lims.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteSynchronization(synchronization);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.marcelmika.lims.persistence.generated.model.Synchronization fetchSynchronization(
		long sid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchSynchronization(sid);
	}

	/**
	* Returns the synchronization with the primary key.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization
	* @throws PortalException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Synchronization getSynchronization(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getSynchronization(sid);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the synchronizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of synchronizations
	* @param end the upper bound of the range of synchronizations (not inclusive)
	* @return the range of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Synchronization> getSynchronizations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSynchronizations(start, end);
	}

	/**
	* Returns the number of synchronizations.
	*
	* @return the number of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public static int getSynchronizationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getSynchronizationsCount();
	}

	/**
	* Updates the synchronization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param synchronization the synchronization
	* @return the synchronization that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Synchronization updateSynchronization(
		com.marcelmika.lims.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateSynchronization(synchronization);
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable {
		return getService().invokeMethod(name, parameterTypes, arguments);
	}

	public static void synchronizeSUC_1_2_0()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().synchronizeSUC_1_2_0();
	}

	public static void clearService() {
		_service = null;
	}

	public static SynchronizationLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					SynchronizationLocalService.class.getName());

			if (invokableLocalService instanceof SynchronizationLocalService) {
				_service = (SynchronizationLocalService)invokableLocalService;
			}
			else {
				_service = new SynchronizationLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(SynchronizationLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(SynchronizationLocalService service) {
	}

	private static SynchronizationLocalService _service;
}