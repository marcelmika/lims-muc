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

import com.liferay.portal.kernel.bean.PortletBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.marcelmika.lims.persistence.generated.model.Participant;

import java.util.List;

/**
 * The persistence utility for the participant service. This utility wraps {@link ParticipantPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ParticipantPersistence
 * @see ParticipantPersistenceImpl
 * @generated
 */
public class ParticipantUtil {
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
	public static void clearCache(Participant participant) {
		getPersistence().clearCache(participant);
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
	public static List<Participant> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<Participant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<Participant> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static Participant update(Participant participant)
		throws SystemException {
		return getPersistence().update(participant);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static Participant update(Participant participant,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(participant, serviceContext);
	}

	/**
	* Returns all the participants where cid = &#63;.
	*
	* @param cid the cid
	* @return the matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByCid(
		long cid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCid(cid);
	}

	/**
	* Returns a range of all the participants where cid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param cid the cid
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @return the range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByCid(
		long cid, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCid(cid, start, end);
	}

	/**
	* Returns an ordered range of all the participants where cid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param cid the cid
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByCid(
		long cid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByCid(cid, start, end, orderByComparator);
	}

	/**
	* Returns the first participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant findByCid_First(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence().findByCid_First(cid, orderByComparator);
	}

	/**
	* Returns the first participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant fetchByCid_First(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCid_First(cid, orderByComparator);
	}

	/**
	* Returns the last participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant findByCid_Last(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence().findByCid_Last(cid, orderByComparator);
	}

	/**
	* Returns the last participant in the ordered set where cid = &#63;.
	*
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant fetchByCid_Last(
		long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCid_Last(cid, orderByComparator);
	}

	/**
	* Returns the participants before and after the current participant in the ordered set where cid = &#63;.
	*
	* @param pid the primary key of the current participant
	* @param cid the cid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant[] findByCid_PrevAndNext(
		long pid, long cid,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence()
				   .findByCid_PrevAndNext(pid, cid, orderByComparator);
	}

	/**
	* Removes all the participants where cid = &#63; from the database.
	*
	* @param cid the cid
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByCid(long cid)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByCid(cid);
	}

	/**
	* Returns the number of participants where cid = &#63;.
	*
	* @param cid the cid
	* @return the number of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCid(long cid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCid(cid);
	}

	/**
	* Returns the participant where cid = &#63; and participantId = &#63; or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchParticipantException} if it could not be found.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant findByCidParticipantId(
		long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence().findByCidParticipantId(cid, participantId);
	}

	/**
	* Returns the participant where cid = &#63; and participantId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant fetchByCidParticipantId(
		long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByCidParticipantId(cid, participantId);
	}

	/**
	* Returns the participant where cid = &#63; and participantId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant fetchByCidParticipantId(
		long cid, long participantId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByCidParticipantId(cid, participantId,
			retrieveFromCache);
	}

	/**
	* Removes the participant where cid = &#63; and participantId = &#63; from the database.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the participant that was removed
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant removeByCidParticipantId(
		long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence().removeByCidParticipantId(cid, participantId);
	}

	/**
	* Returns the number of participants where cid = &#63; and participantId = &#63;.
	*
	* @param cid the cid
	* @param participantId the participant ID
	* @return the number of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByCidParticipantId(long cid, long participantId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByCidParticipantId(cid, participantId);
	}

	/**
	* Returns all the participants where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @return the matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByParticipantIdIsOpened(
		long participantId, boolean isOpened)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByParticipantIdIsOpened(participantId, isOpened);
	}

	/**
	* Returns a range of all the participants where participantId = &#63; and isOpened = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @return the range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByParticipantIdIsOpened(
		long participantId, boolean isOpened, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByParticipantIdIsOpened(participantId, isOpened, start,
			end);
	}

	/**
	* Returns an ordered range of all the participants where participantId = &#63; and isOpened = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findByParticipantIdIsOpened(
		long participantId, boolean isOpened, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByParticipantIdIsOpened(participantId, isOpened, start,
			end, orderByComparator);
	}

	/**
	* Returns the first participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant findByParticipantIdIsOpened_First(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence()
				   .findByParticipantIdIsOpened_First(participantId, isOpened,
			orderByComparator);
	}

	/**
	* Returns the first participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant fetchByParticipantIdIsOpened_First(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByParticipantIdIsOpened_First(participantId, isOpened,
			orderByComparator);
	}

	/**
	* Returns the last participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant findByParticipantIdIsOpened_Last(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence()
				   .findByParticipantIdIsOpened_Last(participantId, isOpened,
			orderByComparator);
	}

	/**
	* Returns the last participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching participant, or <code>null</code> if a matching participant could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant fetchByParticipantIdIsOpened_Last(
		long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByParticipantIdIsOpened_Last(participantId, isOpened,
			orderByComparator);
	}

	/**
	* Returns the participants before and after the current participant in the ordered set where participantId = &#63; and isOpened = &#63;.
	*
	* @param pid the primary key of the current participant
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant[] findByParticipantIdIsOpened_PrevAndNext(
		long pid, long participantId, boolean isOpened,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence()
				   .findByParticipantIdIsOpened_PrevAndNext(pid, participantId,
			isOpened, orderByComparator);
	}

	/**
	* Removes all the participants where participantId = &#63; and isOpened = &#63; from the database.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByParticipantIdIsOpened(long participantId,
		boolean isOpened)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByParticipantIdIsOpened(participantId, isOpened);
	}

	/**
	* Returns the number of participants where participantId = &#63; and isOpened = &#63;.
	*
	* @param participantId the participant ID
	* @param isOpened the is opened
	* @return the number of matching participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countByParticipantIdIsOpened(long participantId,
		boolean isOpened)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .countByParticipantIdIsOpened(participantId, isOpened);
	}

	/**
	* Caches the participant in the entity cache if it is enabled.
	*
	* @param participant the participant
	*/
	public static void cacheResult(
		com.marcelmika.lims.persistence.generated.model.Participant participant) {
		getPersistence().cacheResult(participant);
	}

	/**
	* Caches the participants in the entity cache if it is enabled.
	*
	* @param participants the participants
	*/
	public static void cacheResult(
		java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> participants) {
		getPersistence().cacheResult(participants);
	}

	/**
	* Creates a new participant with the primary key. Does not add the participant to the database.
	*
	* @param pid the primary key for the new participant
	* @return the new participant
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant create(
		long pid) {
		return getPersistence().create(pid);
	}

	/**
	* Removes the participant with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param pid the primary key of the participant
	* @return the participant that was removed
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant remove(
		long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence().remove(pid);
	}

	public static com.marcelmika.lims.persistence.generated.model.Participant updateImpl(
		com.marcelmika.lims.persistence.generated.model.Participant participant)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(participant);
	}

	/**
	* Returns the participant with the primary key or throws a {@link com.marcelmika.lims.persistence.generated.NoSuchParticipantException} if it could not be found.
	*
	* @param pid the primary key of the participant
	* @return the participant
	* @throws com.marcelmika.lims.persistence.generated.NoSuchParticipantException if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant findByPrimaryKey(
		long pid)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.marcelmika.lims.persistence.generated.NoSuchParticipantException {
		return getPersistence().findByPrimaryKey(pid);
	}

	/**
	* Returns the participant with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param pid the primary key of the participant
	* @return the participant, or <code>null</code> if a participant with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.marcelmika.lims.persistence.generated.model.Participant fetchByPrimaryKey(
		long pid) throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(pid);
	}

	/**
	* Returns all the participants.
	*
	* @return the participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
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
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the participants.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.marcelmika.lims.persistence.generated.model.impl.ParticipantModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of participants
	* @param end the upper bound of the range of participants (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of participants
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.marcelmika.lims.persistence.generated.model.Participant> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the participants from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of participants.
	*
	* @return the number of participants
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static ParticipantPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (ParticipantPersistence)PortletBeanLocatorUtil.locate(com.marcelmika.lims.persistence.generated.service.ClpSerializer.getServletContextName(),
					ParticipantPersistence.class.getName());

			ReferenceRegistry.registerReference(ParticipantUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	public void setPersistence(ParticipantPersistence persistence) {
	}

	private static ParticipantPersistence _persistence;
}