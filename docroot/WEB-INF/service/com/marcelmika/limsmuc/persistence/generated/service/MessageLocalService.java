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
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.BaseLocalService;
import com.liferay.portal.service.InvokableLocalService;
import com.liferay.portal.service.PersistedModelLocalService;

/**
 * Provides the local service interface for Message. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author Brian Wing Shun Chan
 * @see MessageLocalServiceUtil
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.MessageLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.impl.MessageLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MessageLocalService extends BaseLocalService,
	InvokableLocalService, PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link MessageLocalServiceUtil} to access the message local service. Add custom service methods to {@link com.marcelmika.limsmuc.persistence.generated.service.impl.MessageLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the message to the database. Also notifies the appropriate model listeners.
	*
	* @param message the message
	* @return the message that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Message addMessage(
		com.marcelmika.limsmuc.persistence.generated.model.Message message)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new message with the primary key. Does not add the message to the database.
	*
	* @param mid the primary key for the new message
	* @return the new message
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Message createMessage(
		long mid);

	/**
	* Deletes the message with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param mid the primary key of the message
	* @return the message that was removed
	* @throws PortalException if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Message deleteMessage(
		long mid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the message from the database. Also notifies the appropriate model listeners.
	*
	* @param message the message
	* @return the message that was removed
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Message deleteMessage(
		com.marcelmika.limsmuc.persistence.generated.model.Message message)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.MessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.MessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	public com.marcelmika.limsmuc.persistence.generated.model.Message fetchMessage(
		long mid) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the message with the primary key.
	*
	* @param mid the primary key of the message
	* @return the message
	* @throws PortalException if a message with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Message getMessage(
		long mid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a range of all the messages.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.MessageModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of messages
	* @param end the upper bound of the range of messages (not inclusive)
	* @return the range of messages
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Message> getMessages(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the number of messages.
	*
	* @return the number of messages
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMessagesCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the message in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param message the message
	* @return the message that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Message updateMessage(
		com.marcelmika.limsmuc.persistence.generated.model.Message message)
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
	* Adds a message to the persistence
	*
	* @param cid         conversation id
	* @param creatorId   user id of the creator of the message
	* @param messageType type of the message code
	* @param body        text of the message
	* @param createdAt   timestamp of creation
	* @return newly created message
	* @throws SystemException
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Message addMessage(
		long cid, long creatorId, int messageType, java.lang.String body,
		java.util.Date createdAt)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns a list of messages related to the conversation
	*
	* @param cid       id of the conversation
	* @param pageSize  size of the list
	* @param stopperId id of the stopper messages
	* @param readMore  true if the list should be extended
	* @return a list of messages
	* @throws SystemException
	*/
	public java.util.List<java.lang.Object[]> readMessages(java.lang.Long cid,
		java.lang.Integer pageSize, java.lang.Long stopperId,
		java.lang.Boolean readMore)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the first message in the conversation
	*
	* @param cid id of the conversation related to the messages
	* @return first message in the conversation
	* @throws SystemException
	*/
	public java.lang.Object[] firstMessage(java.lang.Long cid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns the last message in the conversation
	*
	* @param cid id of the conversation related to the messages
	* @return last message in the conversation
	* @throws SystemException
	*/
	public java.lang.Object[] lastMessage(java.lang.Long cid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts number of messages for the given conversation
	*
	* @param cid id of the conversation
	* @return number of messages
	*/
	public java.lang.Integer countMessages(java.lang.Long cid)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns conversation based on its sync id SUC
	*
	* @param syncIdSUC of the conversation
	* @return found conversation or null if nothing was found
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Message fetchBySyncIdSUC(
		long syncIdSUC)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Returns conversation based on its sync id SUC
	*
	* @param syncIdSUC of the conversation
	* @param useCache true if the cache should be used
	* @return found conversation or null if nothing was found
	* @throws SystemException
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.marcelmika.limsmuc.persistence.generated.model.Message fetchBySyncIdSUC(
		long syncIdSUC, boolean useCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates new plain message
	*
	* @return created message
	* @throws SystemException
	*/
	public com.marcelmika.limsmuc.persistence.generated.model.Message createMessage()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Save message to database
	*
	* @param message Message
	* @throws SystemException
	*/
	public void saveMessage(
		com.marcelmika.limsmuc.persistence.generated.model.Message message)
		throws com.liferay.portal.kernel.exception.SystemException;
}