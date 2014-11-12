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

package com.marcelmika.lims.persistence.generated.service.base;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.IdentifiableBean;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.model.PersistedModel;
import com.liferay.portal.service.BaseLocalServiceImpl;
import com.liferay.portal.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.service.persistence.UserPersistence;

import com.marcelmika.lims.persistence.generated.model.Participant;
import com.marcelmika.lims.persistence.generated.service.ParticipantLocalService;
import com.marcelmika.lims.persistence.generated.service.persistence.ConversationPersistence;
import com.marcelmika.lims.persistence.generated.service.persistence.MessageFinder;
import com.marcelmika.lims.persistence.generated.service.persistence.MessagePersistence;
import com.marcelmika.lims.persistence.generated.service.persistence.PanelPersistence;
import com.marcelmika.lims.persistence.generated.service.persistence.ParticipantFinder;
import com.marcelmika.lims.persistence.generated.service.persistence.ParticipantPersistence;
import com.marcelmika.lims.persistence.generated.service.persistence.SettingsFinder;
import com.marcelmika.lims.persistence.generated.service.persistence.SettingsPersistence;
import com.marcelmika.lims.persistence.generated.service.persistence.SynchronizationFinder;
import com.marcelmika.lims.persistence.generated.service.persistence.SynchronizationPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the participant local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.marcelmika.lims.persistence.generated.service.impl.ParticipantLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.marcelmika.lims.persistence.generated.service.impl.ParticipantLocalServiceImpl
 * @see com.marcelmika.lims.persistence.generated.service.ParticipantLocalServiceUtil
 * @generated
 */
public abstract class ParticipantLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements ParticipantLocalService,
		IdentifiableBean {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.marcelmika.lims.persistence.generated.service.ParticipantLocalServiceUtil} to access the participant local service.
	 */

	/**
	 * Adds the participant to the database. Also notifies the appropriate model listeners.
	 *
	 * @param participant the participant
	 * @return the participant that was added
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Participant addParticipant(Participant participant)
		throws SystemException {
		participant.setNew(true);

		return participantPersistence.update(participant);
	}

	/**
	 * Creates a new participant with the primary key. Does not add the participant to the database.
	 *
	 * @param pid the primary key for the new participant
	 * @return the new participant
	 */
	@Override
	public Participant createParticipant(long pid) {
		return participantPersistence.create(pid);
	}

	/**
	 * Deletes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param pid the primary key of the participant
	 * @return the participant that was removed
	 * @throws PortalException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Participant deleteParticipant(long pid)
		throws PortalException, SystemException {
		return participantPersistence.remove(pid);
	}

	/**
	 * Deletes the participant from the database. Also notifies the appropriate model listeners.
	 *
	 * @param participant the participant
	 * @return the participant that was removed
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public Participant deleteParticipant(Participant participant)
		throws SystemException {
		return participantPersistence.remove(participant);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(Participant.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return participantPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return participantPersistence.findWithDynamicQuery(dynamicQuery, start,
			end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	@SuppressWarnings("rawtypes")
	public List dynamicQuery(DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return participantPersistence.findWithDynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery)
		throws SystemException {
		return participantPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows that match the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows that match the dynamic query
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) throws SystemException {
		return participantPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public Participant fetchParticipant(long pid) throws SystemException {
		return participantPersistence.fetchByPrimaryKey(pid);
	}

	/**
	 * Returns the participant with the primary key.
	 *
	 * @param pid the primary key of the participant
	 * @return the participant
	 * @throws PortalException if a participant with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Participant getParticipant(long pid)
		throws PortalException, SystemException {
		return participantPersistence.findByPrimaryKey(pid);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException, SystemException {
		return participantPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the participants.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of participants
	 * @param end the upper bound of the range of participants (not inclusive)
	 * @return the range of participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Participant> getParticipants(int start, int end)
		throws SystemException {
		return participantPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of participants.
	 *
	 * @return the number of participants
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int getParticipantsCount() throws SystemException {
		return participantPersistence.countAll();
	}

	/**
	 * Updates the participant in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param participant the participant
	 * @return the participant that was updated
	 * @throws SystemException if a system exception occurred
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public Participant updateParticipant(Participant participant)
		throws SystemException {
		return participantPersistence.update(participant);
	}

	/**
	 * Returns the conversation local service.
	 *
	 * @return the conversation local service
	 */
	public com.marcelmika.lims.persistence.generated.service.ConversationLocalService getConversationLocalService() {
		return conversationLocalService;
	}

	/**
	 * Sets the conversation local service.
	 *
	 * @param conversationLocalService the conversation local service
	 */
	public void setConversationLocalService(
		com.marcelmika.lims.persistence.generated.service.ConversationLocalService conversationLocalService) {
		this.conversationLocalService = conversationLocalService;
	}

	/**
	 * Returns the conversation persistence.
	 *
	 * @return the conversation persistence
	 */
	public ConversationPersistence getConversationPersistence() {
		return conversationPersistence;
	}

	/**
	 * Sets the conversation persistence.
	 *
	 * @param conversationPersistence the conversation persistence
	 */
	public void setConversationPersistence(
		ConversationPersistence conversationPersistence) {
		this.conversationPersistence = conversationPersistence;
	}

	/**
	 * Returns the message local service.
	 *
	 * @return the message local service
	 */
	public com.marcelmika.lims.persistence.generated.service.MessageLocalService getMessageLocalService() {
		return messageLocalService;
	}

	/**
	 * Sets the message local service.
	 *
	 * @param messageLocalService the message local service
	 */
	public void setMessageLocalService(
		com.marcelmika.lims.persistence.generated.service.MessageLocalService messageLocalService) {
		this.messageLocalService = messageLocalService;
	}

	/**
	 * Returns the message persistence.
	 *
	 * @return the message persistence
	 */
	public MessagePersistence getMessagePersistence() {
		return messagePersistence;
	}

	/**
	 * Sets the message persistence.
	 *
	 * @param messagePersistence the message persistence
	 */
	public void setMessagePersistence(MessagePersistence messagePersistence) {
		this.messagePersistence = messagePersistence;
	}

	/**
	 * Returns the message finder.
	 *
	 * @return the message finder
	 */
	public MessageFinder getMessageFinder() {
		return messageFinder;
	}

	/**
	 * Sets the message finder.
	 *
	 * @param messageFinder the message finder
	 */
	public void setMessageFinder(MessageFinder messageFinder) {
		this.messageFinder = messageFinder;
	}

	/**
	 * Returns the panel local service.
	 *
	 * @return the panel local service
	 */
	public com.marcelmika.lims.persistence.generated.service.PanelLocalService getPanelLocalService() {
		return panelLocalService;
	}

	/**
	 * Sets the panel local service.
	 *
	 * @param panelLocalService the panel local service
	 */
	public void setPanelLocalService(
		com.marcelmika.lims.persistence.generated.service.PanelLocalService panelLocalService) {
		this.panelLocalService = panelLocalService;
	}

	/**
	 * Returns the panel persistence.
	 *
	 * @return the panel persistence
	 */
	public PanelPersistence getPanelPersistence() {
		return panelPersistence;
	}

	/**
	 * Sets the panel persistence.
	 *
	 * @param panelPersistence the panel persistence
	 */
	public void setPanelPersistence(PanelPersistence panelPersistence) {
		this.panelPersistence = panelPersistence;
	}

	/**
	 * Returns the participant local service.
	 *
	 * @return the participant local service
	 */
	public com.marcelmika.lims.persistence.generated.service.ParticipantLocalService getParticipantLocalService() {
		return participantLocalService;
	}

	/**
	 * Sets the participant local service.
	 *
	 * @param participantLocalService the participant local service
	 */
	public void setParticipantLocalService(
		com.marcelmika.lims.persistence.generated.service.ParticipantLocalService participantLocalService) {
		this.participantLocalService = participantLocalService;
	}

	/**
	 * Returns the participant persistence.
	 *
	 * @return the participant persistence
	 */
	public ParticipantPersistence getParticipantPersistence() {
		return participantPersistence;
	}

	/**
	 * Sets the participant persistence.
	 *
	 * @param participantPersistence the participant persistence
	 */
	public void setParticipantPersistence(
		ParticipantPersistence participantPersistence) {
		this.participantPersistence = participantPersistence;
	}

	/**
	 * Returns the participant finder.
	 *
	 * @return the participant finder
	 */
	public ParticipantFinder getParticipantFinder() {
		return participantFinder;
	}

	/**
	 * Sets the participant finder.
	 *
	 * @param participantFinder the participant finder
	 */
	public void setParticipantFinder(ParticipantFinder participantFinder) {
		this.participantFinder = participantFinder;
	}

	/**
	 * Returns the settings local service.
	 *
	 * @return the settings local service
	 */
	public com.marcelmika.lims.persistence.generated.service.SettingsLocalService getSettingsLocalService() {
		return settingsLocalService;
	}

	/**
	 * Sets the settings local service.
	 *
	 * @param settingsLocalService the settings local service
	 */
	public void setSettingsLocalService(
		com.marcelmika.lims.persistence.generated.service.SettingsLocalService settingsLocalService) {
		this.settingsLocalService = settingsLocalService;
	}

	/**
	 * Returns the settings persistence.
	 *
	 * @return the settings persistence
	 */
	public SettingsPersistence getSettingsPersistence() {
		return settingsPersistence;
	}

	/**
	 * Sets the settings persistence.
	 *
	 * @param settingsPersistence the settings persistence
	 */
	public void setSettingsPersistence(SettingsPersistence settingsPersistence) {
		this.settingsPersistence = settingsPersistence;
	}

	/**
	 * Returns the settings finder.
	 *
	 * @return the settings finder
	 */
	public SettingsFinder getSettingsFinder() {
		return settingsFinder;
	}

	/**
	 * Sets the settings finder.
	 *
	 * @param settingsFinder the settings finder
	 */
	public void setSettingsFinder(SettingsFinder settingsFinder) {
		this.settingsFinder = settingsFinder;
	}

	/**
	 * Returns the synchronization local service.
	 *
	 * @return the synchronization local service
	 */
	public com.marcelmika.lims.persistence.generated.service.SynchronizationLocalService getSynchronizationLocalService() {
		return synchronizationLocalService;
	}

	/**
	 * Sets the synchronization local service.
	 *
	 * @param synchronizationLocalService the synchronization local service
	 */
	public void setSynchronizationLocalService(
		com.marcelmika.lims.persistence.generated.service.SynchronizationLocalService synchronizationLocalService) {
		this.synchronizationLocalService = synchronizationLocalService;
	}

	/**
	 * Returns the synchronization persistence.
	 *
	 * @return the synchronization persistence
	 */
	public SynchronizationPersistence getSynchronizationPersistence() {
		return synchronizationPersistence;
	}

	/**
	 * Sets the synchronization persistence.
	 *
	 * @param synchronizationPersistence the synchronization persistence
	 */
	public void setSynchronizationPersistence(
		SynchronizationPersistence synchronizationPersistence) {
		this.synchronizationPersistence = synchronizationPersistence;
	}

	/**
	 * Returns the synchronization finder.
	 *
	 * @return the synchronization finder
	 */
	public SynchronizationFinder getSynchronizationFinder() {
		return synchronizationFinder;
	}

	/**
	 * Sets the synchronization finder.
	 *
	 * @param synchronizationFinder the synchronization finder
	 */
	public void setSynchronizationFinder(
		SynchronizationFinder synchronizationFinder) {
		this.synchronizationFinder = synchronizationFinder;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user remote service.
	 *
	 * @return the user remote service
	 */
	public com.liferay.portal.service.UserService getUserService() {
		return userService;
	}

	/**
	 * Sets the user remote service.
	 *
	 * @param userService the user remote service
	 */
	public void setUserService(
		com.liferay.portal.service.UserService userService) {
		this.userService = userService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		PersistedModelLocalServiceRegistryUtil.register("com.marcelmika.lims.persistence.generated.model.Participant",
			participantLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.marcelmika.lims.persistence.generated.model.Participant");
	}

	/**
	 * Returns the Spring bean ID for this bean.
	 *
	 * @return the Spring bean ID for this bean
	 */
	@Override
	public String getBeanIdentifier() {
		return _beanIdentifier;
	}

	/**
	 * Sets the Spring bean ID for this bean.
	 *
	 * @param beanIdentifier the Spring bean ID for this bean
	 */
	@Override
	public void setBeanIdentifier(String beanIdentifier) {
		_beanIdentifier = beanIdentifier;
	}

	@Override
	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != _classLoader) {
			currentThread.setContextClassLoader(_classLoader);
		}

		try {
			return _clpInvoker.invokeMethod(name, parameterTypes, arguments);
		}
		finally {
			if (contextClassLoader != _classLoader) {
				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	protected Class<?> getModelClass() {
		return Participant.class;
	}

	protected String getModelClassName() {
		return Participant.class.getName();
	}

	/**
	 * Performs an SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) throws SystemException {
		try {
			DataSource dataSource = participantPersistence.getDataSource();

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql, new int[0]);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.marcelmika.lims.persistence.generated.service.ConversationLocalService.class)
	protected com.marcelmika.lims.persistence.generated.service.ConversationLocalService conversationLocalService;
	@BeanReference(type = ConversationPersistence.class)
	protected ConversationPersistence conversationPersistence;
	@BeanReference(type = com.marcelmika.lims.persistence.generated.service.MessageLocalService.class)
	protected com.marcelmika.lims.persistence.generated.service.MessageLocalService messageLocalService;
	@BeanReference(type = MessagePersistence.class)
	protected MessagePersistence messagePersistence;
	@BeanReference(type = MessageFinder.class)
	protected MessageFinder messageFinder;
	@BeanReference(type = com.marcelmika.lims.persistence.generated.service.PanelLocalService.class)
	protected com.marcelmika.lims.persistence.generated.service.PanelLocalService panelLocalService;
	@BeanReference(type = PanelPersistence.class)
	protected PanelPersistence panelPersistence;
	@BeanReference(type = com.marcelmika.lims.persistence.generated.service.ParticipantLocalService.class)
	protected com.marcelmika.lims.persistence.generated.service.ParticipantLocalService participantLocalService;
	@BeanReference(type = ParticipantPersistence.class)
	protected ParticipantPersistence participantPersistence;
	@BeanReference(type = ParticipantFinder.class)
	protected ParticipantFinder participantFinder;
	@BeanReference(type = com.marcelmika.lims.persistence.generated.service.SettingsLocalService.class)
	protected com.marcelmika.lims.persistence.generated.service.SettingsLocalService settingsLocalService;
	@BeanReference(type = SettingsPersistence.class)
	protected SettingsPersistence settingsPersistence;
	@BeanReference(type = SettingsFinder.class)
	protected SettingsFinder settingsFinder;
	@BeanReference(type = com.marcelmika.lims.persistence.generated.service.SynchronizationLocalService.class)
	protected com.marcelmika.lims.persistence.generated.service.SynchronizationLocalService synchronizationLocalService;
	@BeanReference(type = SynchronizationPersistence.class)
	protected SynchronizationPersistence synchronizationPersistence;
	@BeanReference(type = SynchronizationFinder.class)
	protected SynchronizationFinder synchronizationFinder;
	@BeanReference(type = com.liferay.counter.service.CounterLocalService.class)
	protected com.liferay.counter.service.CounterLocalService counterLocalService;
	@BeanReference(type = com.liferay.portal.service.ResourceLocalService.class)
	protected com.liferay.portal.service.ResourceLocalService resourceLocalService;
	@BeanReference(type = com.liferay.portal.service.UserLocalService.class)
	protected com.liferay.portal.service.UserLocalService userLocalService;
	@BeanReference(type = com.liferay.portal.service.UserService.class)
	protected com.liferay.portal.service.UserService userService;
	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	private String _beanIdentifier;
	private ClassLoader _classLoader;
	private ParticipantLocalServiceClpInvoker _clpInvoker = new ParticipantLocalServiceClpInvoker();
}