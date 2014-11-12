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

package com.marcelmika.lims.persistence.generated.service.persistence;

import com.liferay.portal.service.persistence.BasePersistence;

import com.marcelmika.lims.persistence.generated.model.Synchronization;

/**
 * The persistence interface for the synchronization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SynchronizationPersistenceImpl
 * @see SynchronizationUtil
 * @generated
 */
public interface SynchronizationPersistence extends BasePersistence<Synchronization> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SynchronizationUtil} to access the synchronization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the synchronization in the entity cache if it is enabled.
	*
	* @param synchronization the synchronization
	*/
	public void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Synchronization synchronization);

	/**
	* Caches the synchronizations in the entity cache if it is enabled.
	*
	* @param synchronizations the synchronizations
	*/
	public void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Synchronization> synchronizations);

	/**
	* Creates a new synchronization with the primary key. Does not add the synchronization to the database.
	*
	* @param sid the primary key for the new synchronization
	* @return the new synchronization
	*/
	public com.marcelmika.lims.persistence.generated.model.Synchronization create(
		long sid);

	/**
	* Removes the synchronization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Synchronization remove(
		long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException;

	public com.marcelmika.lims.persistence.generated.model.Synchronization updateImpl(
		com.marcelmika.lims.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the synchronization with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException} if it could not be found.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization
	* @throws com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Synchronization findByPrimaryKey(
		long sid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException;

	/**
	* Returns the synchronization with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization, or <code>null</code> if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.lims.persistence.generated.model.Synchronization fetchByPrimaryKey(
		long sid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns all the synchronizations.
	*
	* @return the synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Synchronization> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

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
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Synchronization> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns an ordered range of all the synchronizations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of synchronizations
	* @param end the upper bound of the range of synchronizations (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.marcelmika.lims.persistence.generated.model.Synchronization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the synchronizations from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of synchronizations.
	*
	* @return the number of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}