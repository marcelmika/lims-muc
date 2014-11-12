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

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnmodifiableList;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException;
import com.marcelmika.lims.persistence.generated.model.Synchronization;
import com.marcelmika.lims.persistence.generated.model.impl.SynchronizationImpl;
import com.marcelmika.lims.persistence.generated.model.impl.SynchronizationModelImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the synchronization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see SynchronizationPersistence
 * @see SynchronizationUtil
 * @generated
 */
public class SynchronizationPersistenceImpl extends BasePersistenceImpl<Synchronization>
	implements SynchronizationPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link SynchronizationUtil} to access the synchronization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = SynchronizationImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
			SynchronizationModelImpl.FINDER_CACHE_ENABLED,
			SynchronizationImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
			SynchronizationModelImpl.FINDER_CACHE_ENABLED,
			SynchronizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
			SynchronizationModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);

	public SynchronizationPersistenceImpl() {
		setModelClass(Synchronization.class);
	}

	/**
	 * Caches the synchronization in the entity cache if it is enabled.
	 *
	 * @param synchronization the synchronization
	 */
	@Override
	public void cacheResult(Synchronization synchronization) {
		EntityCacheUtil.putResult(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
			SynchronizationImpl.class, synchronization.getPrimaryKey(),
			synchronization);

		synchronization.resetOriginalValues();
	}

	/**
	 * Caches the synchronizations in the entity cache if it is enabled.
	 *
	 * @param synchronizations the synchronizations
	 */
	@Override
	public void cacheResult(List<Synchronization> synchronizations) {
		for (Synchronization synchronization : synchronizations) {
			if (EntityCacheUtil.getResult(
						SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
						SynchronizationImpl.class,
						synchronization.getPrimaryKey()) == null) {
				cacheResult(synchronization);
			}
			else {
				synchronization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all synchronizations.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(SynchronizationImpl.class.getName());
		}

		EntityCacheUtil.clearCache(SynchronizationImpl.class.getName());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the synchronization.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(Synchronization synchronization) {
		EntityCacheUtil.removeResult(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
			SynchronizationImpl.class, synchronization.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<Synchronization> synchronizations) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Synchronization synchronization : synchronizations) {
			EntityCacheUtil.removeResult(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
				SynchronizationImpl.class, synchronization.getPrimaryKey());
		}
	}

	/**
	 * Creates a new synchronization with the primary key. Does not add the synchronization to the database.
	 *
	 * @param sid the primary key for the new synchronization
	 * @return the new synchronization
	 */
	@Override
	public Synchronization create(long sid) {
		Synchronization synchronization = new SynchronizationImpl();

		synchronization.setNew(true);
		synchronization.setPrimaryKey(sid);

		return synchronization;
	}

	/**
	 * Removes the synchronization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param sid the primary key of the synchronization
	 * @return the synchronization that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Synchronization remove(long sid)
		throws NoSuchSynchronizationException, SystemException {
		return remove((Serializable)sid);
	}

	/**
	 * Removes the synchronization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the synchronization
	 * @return the synchronization that was removed
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Synchronization remove(Serializable primaryKey)
		throws NoSuchSynchronizationException, SystemException {
		Session session = null;

		try {
			session = openSession();

			Synchronization synchronization = (Synchronization)session.get(SynchronizationImpl.class,
					primaryKey);

			if (synchronization == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchSynchronizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(synchronization);
		}
		catch (NoSuchSynchronizationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected Synchronization removeImpl(Synchronization synchronization)
		throws SystemException {
		synchronization = toUnwrappedModel(synchronization);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(synchronization)) {
				synchronization = (Synchronization)session.get(SynchronizationImpl.class,
						synchronization.getPrimaryKeyObj());
			}

			if (synchronization != null) {
				session.delete(synchronization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (synchronization != null) {
			clearCache(synchronization);
		}

		return synchronization;
	}

	@Override
	public Synchronization updateImpl(
		com.marcelmika.lims.persistence.generated.model.Synchronization synchronization)
		throws SystemException {
		synchronization = toUnwrappedModel(synchronization);

		boolean isNew = synchronization.isNew();

		Session session = null;

		try {
			session = openSession();

			if (synchronization.isNew()) {
				session.save(synchronization);

				synchronization.setNew(false);
			}
			else {
				session.merge(synchronization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		EntityCacheUtil.putResult(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
			SynchronizationImpl.class, synchronization.getPrimaryKey(),
			synchronization);

		return synchronization;
	}

	protected Synchronization toUnwrappedModel(Synchronization synchronization) {
		if (synchronization instanceof SynchronizationImpl) {
			return synchronization;
		}

		SynchronizationImpl synchronizationImpl = new SynchronizationImpl();

		synchronizationImpl.setNew(synchronization.isNew());
		synchronizationImpl.setPrimaryKey(synchronization.getPrimaryKey());

		synchronizationImpl.setSid(synchronization.getSid());
		synchronizationImpl.setSucSync(synchronization.isSucSync());

		return synchronizationImpl;
	}

	/**
	 * Returns the synchronization with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the synchronization
	 * @return the synchronization
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Synchronization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchSynchronizationException, SystemException {
		Synchronization synchronization = fetchByPrimaryKey(primaryKey);

		if (synchronization == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchSynchronizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return synchronization;
	}

	/**
	 * Returns the synchronization with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException} if it could not be found.
	 *
	 * @param sid the primary key of the synchronization
	 * @return the synchronization
	 * @throws com.marcelmika.lims.persistence.generated.NoSuchSynchronizationException if a synchronization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Synchronization findByPrimaryKey(long sid)
		throws NoSuchSynchronizationException, SystemException {
		return findByPrimaryKey((Serializable)sid);
	}

	/**
	 * Returns the synchronization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the synchronization
	 * @return the synchronization, or <code>null</code> if a synchronization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Synchronization fetchByPrimaryKey(Serializable primaryKey)
		throws SystemException {
		Synchronization synchronization = (Synchronization)EntityCacheUtil.getResult(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
				SynchronizationImpl.class, primaryKey);

		if (synchronization == _nullSynchronization) {
			return null;
		}

		if (synchronization == null) {
			Session session = null;

			try {
				session = openSession();

				synchronization = (Synchronization)session.get(SynchronizationImpl.class,
						primaryKey);

				if (synchronization != null) {
					cacheResult(synchronization);
				}
				else {
					EntityCacheUtil.putResult(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
						SynchronizationImpl.class, primaryKey,
						_nullSynchronization);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(SynchronizationModelImpl.ENTITY_CACHE_ENABLED,
					SynchronizationImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return synchronization;
	}

	/**
	 * Returns the synchronization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param sid the primary key of the synchronization
	 * @return the synchronization, or <code>null</code> if a synchronization with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public Synchronization fetchByPrimaryKey(long sid)
		throws SystemException {
		return fetchByPrimaryKey((Serializable)sid);
	}

	/**
	 * Returns all the synchronizations.
	 *
	 * @return the synchronizations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public List<Synchronization> findAll() throws SystemException {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<Synchronization> findAll(int start, int end)
		throws SystemException {
		return findAll(start, end, null);
	}

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
	@Override
	public List<Synchronization> findAll(int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<Synchronization> list = (List<Synchronization>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_SYNCHRONIZATION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_SYNCHRONIZATION;

				if (pagination) {
					sql = sql.concat(SynchronizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<Synchronization>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = new UnmodifiableList<Synchronization>(list);
				}
				else {
					list = (List<Synchronization>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the synchronizations from the database.
	 *
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void removeAll() throws SystemException {
		for (Synchronization synchronization : findAll()) {
			remove(synchronization);
		}
	}

	/**
	 * Returns the number of synchronizations.
	 *
	 * @return the number of synchronizations
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public int countAll() throws SystemException {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_SYNCHRONIZATION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	/**
	 * Initializes the synchronization persistence.
	 */
	public void afterPropertiesSet() {
		String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
					com.liferay.util.service.ServiceProps.get(
						"value.object.listener.com.marcelmika.lims.persistence.generated.model.Synchronization")));

		if (listenerClassNames.length > 0) {
			try {
				List<ModelListener<Synchronization>> listenersList = new ArrayList<ModelListener<Synchronization>>();

				for (String listenerClassName : listenerClassNames) {
					listenersList.add((ModelListener<Synchronization>)InstanceFactory.newInstance(
							getClassLoader(), listenerClassName));
				}

				listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
			}
			catch (Exception e) {
				_log.error(e);
			}
		}
	}

	public void destroy() {
		EntityCacheUtil.removeCache(SynchronizationImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_SYNCHRONIZATION = "SELECT synchronization FROM Synchronization synchronization";
	private static final String _SQL_COUNT_SYNCHRONIZATION = "SELECT COUNT(synchronization) FROM Synchronization synchronization";
	private static final String _ORDER_BY_ENTITY_ALIAS = "synchronization.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Synchronization exists with the primary key ";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = GetterUtil.getBoolean(PropsUtil.get(
				PropsKeys.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE));
	private static Log _log = LogFactoryUtil.getLog(SynchronizationPersistenceImpl.class);
	private static Synchronization _nullSynchronization = new SynchronizationImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<Synchronization> toCacheModel() {
				return _nullSynchronizationCacheModel;
			}
		};

	private static CacheModel<Synchronization> _nullSynchronizationCacheModel = new CacheModel<Synchronization>() {
			@Override
			public Synchronization toEntityModel() {
				return _nullSynchronization;
			}
		};
}