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

package com.marcelmika.limsmuc.persistence.generated.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for Synchronization. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see SynchronizationLocalServiceUtil
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.SynchronizationLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.impl.SynchronizationLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface SynchronizationLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link SynchronizationLocalServiceUtil} to access the synchronization local service. Add custom service methods to {@link com.marcelmika.limsmuc.persistence.generated.service.impl.SynchronizationLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the synchronization to the database. Also notifies the appropriate model listeners.
	*
	* @param synchronization the synchronization
	* @return the synchronization that was added
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization addSynchronization(
		com.marcelmika.limsmuc.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new synchronization with the primary key. Does not add the synchronization to the database.
	*
	* @param sid the primary key for the new synchronization
	* @return the new synchronization
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization createSynchronization(
		long sid);

	/**
	* Deletes the synchronization with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization that was removed
	* @throws PortalException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization deleteSynchronization(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the synchronization from the database. Also notifies the appropriate model listeners.
	*
	* @param synchronization the synchronization
	* @return the synchronization that was removed
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.DELETE)
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization deleteSynchronization(
		com.marcelmika.limsmuc.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.SynchronizationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization fetchSynchronization(
		long sid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the synchronization with the primary key.
	*
	* @param sid the primary key of the synchronization
	* @return the synchronization
	* @throws PortalException if a synchronization with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization getSynchronization(
		long sid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Synchronization> getSynchronizations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of synchronizations.
	*
	* @return the number of synchronizations
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getSynchronizationsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the synchronization in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param synchronization the synchronization
	* @return the synchronization that was updated
	* @throws SystemException if a system exception occurred
	*/
	@com.liferay.portal.kernel.search.Indexable(type = IndexableType.REINDEX)
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization updateSynchronization(
		com.marcelmika.limsmuc.persistence.generated.model.Synchronization synchronization)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier();

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier);

	@Override
	public java.lang.Object invokeMethod(java.lang.String name,
		java.lang.String[] parameterTypes, java.lang.Object[] arguments)
		throws java.lang.Throwable;

	/**
	* Synchronizes with the given version
	*
	* @throws SystemException
	*/
	public void synchronize(java.lang.String version)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds settings rows for the given version
	*
	* @param version of the settings table
	* @param start   int
	* @param end     int
	* @return list of objects with settings rows
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findSettings(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds panel rows for the given version
	*
	* @param version of the panel table
	* @param start   int
	* @param end     int
	* @return list of objects with panel rows
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findPanel(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds conversation rows for the given version
	*
	* @param version of the conversation table
	* @param start   int
	* @param end     int
	* @return list of objects with conversation rows
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findConversation(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds participant rows for the given version
	*
	* @param version of the conversation table
	* @param start   int
	* @param end     int
	* @return list of objects with participant rows
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findParticipant(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds message rows for the given version
	*
	* @param version of the conversation table
	* @param start   int
	* @param end     int
	* @return list of objects with message rows
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> findMessage(
		java.lang.String version, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns synchronization object
	*
	* @return Synchronization
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Synchronization getSynchronization()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Sets the synchronization suc synced flag
	*
	* @param synced true if the SUC was synced
	* @throws SystemException
	*/
	public void setSucSynced(boolean synced)
		throws com.liferay.portal.kernel.exception.SystemException;
}