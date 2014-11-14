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

package com.marcelmika.limsmuc.persistence.generated.service.persistence;

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.marcelmika.limsmuc.persistence.generated.model.Synchronization;

import java.util.List;

/**
 * The persistence utility for the synchronization service. This utility wraps {@link SynchronizationPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SynchronizationPersistence
 * @see SynchronizationPersistenceImpl
 * @generated
 */
public class SynchronizationUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(Synchronization synchronization) {
		getPersistence().clearCache(synchronization);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<Synchronization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Synchronization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Synchronization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Synchronization update(Synchronization synchronization)
		throws SystemException {
		return getPersistence().update(synchronization);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Synchronization update(Synchronization synchronization,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(synchronization, serviceContext);
	}

	/**
	* Caches the synchronization in the entity cache if it is enabled.
	*
	* @param synchronization the synchronization
	*/
	public static void cacheResult(
		com.marcelmika.limsmuc.persistence.generated.model.Synchronization synchronization) {
		getPersistence().cacheResult(synchronization);
	}

	/**
	* Caches the synchronizations in the entity cache if it is enabled.
	*
	* @param synchronizations the synchronizations
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Synchronization> synchronizations) {
		getPersistence().cacheResult(synchronizations);
	}

	/**
	* Creates a new synchronization with the primary key. Does not add the synchronization to the database.
	*
	* @param sid the primary key for the new synchronization
	* @return the new synchronization
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Synchronization create(
		long sid) {
		return getPersistence().create(sid);
	}

	/**
	* Removes the synchronization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization that was removed
	* @throws com.marcelmika.limsmuc.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Synchronization remove(
		long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.limsmuc.persistence.generated.NoSuchSynchronizationException {
		return getPersistence().remove(sid);
	}

	public static com.marcelmika.limsmuc.persistence.generated.model.Synchronization updateImpl(
		com.marcelmika.limsmuc.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(synchronization);
	}

	/**
	* Returns the synchronization with the primary key or throws a {@link com.marcelmika.limsmuc.persistence.generated.NoSuchSynchronizationException} if it could not be found.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization
	* @throws com.marcelmika.limsmuc.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Synchronization findByPrimaryKey(
		long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.limsmuc.persistence.generated.NoSuchSynchronizationException {
		return getPersistence().findByPrimaryKey(sid);
	}

	/**
	* Returns the synchronization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization, or <code>null</code> if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Synchronization fetchByPrimaryKey(
		long sid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(sid);
	}

	/**
	* Returns all the synchronizations.
	*
	* @return the synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Synchronization> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the synchronizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of synchronizations
	* @param end the upper bound of the range of synchronizations (not inclusive)
	* @return the range of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Synchronization> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the synchronizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of synchronizations
	* @param end the upper bound of the range of synchronizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Synchronization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the synchronizations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of synchronizations.
	*
	* @return the number of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static SynchronizationPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (SynchronizationPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.limsmuc.persistence.generated.service.ClpSerializer.getServletContextName(),
					SynchronizationPersistence.class.getName());

			ReferenceRegistry.registerReference(SynchronizationUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(SynchronizationPersistence persistence) {
	}

	private static SynchronizationPersistence _persistence;
}