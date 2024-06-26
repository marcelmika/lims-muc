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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.InvokableLocalService;

/**
 * Provides the local service utility for Conversation. This utility wraps
 * {@link com.marcelmika.limsmuc.persistence.generated.service.impl.ConversationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ConversationLocalService
 * @see com.marcelmika.limsmuc.persistence.generated.service.base.ConversationLocalServiceBaseImpl
 * @see com.marcelmika.limsmuc.persistence.generated.service.impl.ConversationLocalServiceImpl
 * @generated
 */
public class ConversationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.marcelmika.limsmuc.persistence.generated.service.impl.ConversationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the conversation to the database. Also notifies the appropriate model listeners.
	*
	* @param conversation the conversation
	* @return the conversation that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation addConversation(
		com.marcelmika.limsmuc.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addConversation(conversation);
	}

	/**
	* Creates a new conversation with the primary key. Does not add the conversation to the database.
	*
	* @param cid the primary key for the new conversation
	* @return the new conversation
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation createConversation(
		long cid) {
		return getService().createConversation(cid);
	}

	/**
	* Deletes the conversation with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param cid the primary key of the conversation
	* @return the conversation that was removed
	* @throws PortalException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation deleteConversation(
		long cid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteConversation(cid);
	}

	/**
	* Deletes the conversation from the database. Also notifies the appropriate model listeners.
	*
	* @param conversation the conversation
	* @return the conversation that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation deleteConversation(
		com.marcelmika.limsmuc.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().deleteConversation(conversation);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.ConversationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.ConversationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation fetchConversation(
		long cid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchConversation(cid);
	}

	/**
	* Returns the conversation with the primary key.
	*
	* @param cid the primary key of the conversation
	* @return the conversation
	* @throws PortalException if a conversation with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation getConversation(
		long cid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getConversation(cid);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the conversations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.limsmuc.persistence.generated.model.impl.ConversationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of conversations
	* @param end the upper bound of the range of conversations (not inclusive)
	* @return the range of conversations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.limsmuc.persistence.generated.model.Conversation> getConversations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getConversations(start, end);
	}

	/**
	* Returns the number of conversations.
	*
	* @return the number of conversations
	* @throws SystemException if a system exception occurred
	*/
	public static int getConversationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getConversationsCount();
	}

	/**
	* Updates the conversation in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param conversation the conversation
	* @return the conversation that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation updateConversation(
		com.marcelmika.limsmuc.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateConversation(conversation);
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

	/**
	* Adds a new conversation to the system. If such conversation is already there does nothing and returns it.
	*
	* @param conversationId       of the new conversation
	* @param conversationTypeCode of the new conversation
	* @return created conversation
	* @throws SystemException
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation addConversation(
		java.lang.String conversationId, int conversationTypeCode)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addConversation(conversationId, conversationTypeCode);
	}

	/**
	* Returns conversation based on the cid
	*
	* @param cid of the conversation
	* @return found conversation or null if nothing was found
	* @throws SystemException
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation fetchByCid(
		long cid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchByCid(cid);
	}

	/**
	* Returns conversation based on the conversation id
	*
	* @param conversationId of the conversation
	* @return found conversation or null if nothing was found
	* @throws SystemException
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation fetchByConversationId(
		java.lang.String conversationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchByConversationId(conversationId);
	}

	/**
	* Returns conversation based on the conversation id
	*
	* @param conversationId of the conversation
	* @param useCache       true if the cache should be used
	* @return found conversation or null if nothing was found
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation fetchByConversationId(
		java.lang.String conversationId, boolean useCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchByConversationId(conversationId, useCache);
	}

	/**
	* Returns conversation based on the sync id SUC
	*
	* @param syncIdSUC of the conversation
	* @return found conversation or null if nothing was found
	* @throws SystemException
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation fetchBySyncIdSUC(
		long syncIdSUC)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchBySyncIdSUC(syncIdSUC);
	}

	/**
	* Returns conversation based on the sync id SUC
	*
	* @param syncIdSUC of the conversation
	* @param useCache  true if the cache should be used
	* @return found conversation or null if nothing was found
	* @throws SystemException
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation fetchBySyncIdSUC(
		long syncIdSUC, boolean useCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().fetchBySyncIdSUC(syncIdSUC, useCache);
	}

	/**
	* Create new conversation
	*
	* @return created conversation
	* @throws SystemException
	*/
	public static com.marcelmika.limsmuc.persistence.generated.model.Conversation createConversation()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().createConversation();
	}

	/**
	* Saves conversation to database
	*
	* @param conversation to be saved
	* @throws SystemException
	*/
	public static void saveConversation(
		com.marcelmika.limsmuc.persistence.generated.model.Conversation conversation)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().saveConversation(conversation);
	}

	/**
	* Sets the conversation updated at timestamp to the current timestamp
	*
	* @param cid of the conversation
	* @throws SystemException
	*/
	public static void updateConversationTimestamp(long cid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().updateConversationTimestamp(cid);
	}

	public static void clearService() {
		_service = null;
	}

	public static ConversationLocalService getService() {
		if (_service == null) {
			InvokableLocalService invokableLocalService = (InvokableLocalService)PortletBeanLocatorUtil.locate(ClpSerializer.getServletContextName(),
					ConversationLocalService.class.getName());

			if (invokableLocalService instanceof ConversationLocalService) {
				_service = (ConversationLocalService)invokableLocalService;
			}
			else {
				_service = new ConversationLocalServiceClp(invokableLocalService);
			}

			ReferenceRegistry.registerReference(ConversationLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setService(ConversationLocalService service) {
	}

	private static ConversationLocalService _service;
}