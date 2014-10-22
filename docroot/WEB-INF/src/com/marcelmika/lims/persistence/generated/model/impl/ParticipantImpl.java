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

package com.marcelmika.lims.persistence.generated.model.impl;

import com.marcelmika.lims.persistence.generated.model.Participant;

import java.util.LinkedList;
import java.util.List;

/**
 * The extended model implementation for the Participant service. Represents a row in the &quot;lims_Participant&quot; database table, with each column mapped to a property of this class.
 * <p/>
 * <p>
 * Helper methods and all application logic should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.marcelmika.lims.persistence.generated.model.Participant} interface.
 * </p>
 *
 * @author Brian Wing Shun Chan
 */
public class ParticipantImpl extends ParticipantBaseImpl {
    /*
     * NOTE FOR DEVELOPERS:
     *
     * Never reference this class directly. All methods that expect a participant model instance should use the {@link com.marcelmika.lims.persistence.generated.model.Participant} interface instead.
     */
    public ParticipantImpl() {
    }

    /**
     * Factory method which creates participant from plain java object usually retrieved from database
     *
     * @param object       Object[] array that contains participant data
     * @param firstElement determines first element in Object[] where the serialization should start
     * @return Participant
     */
    public static Participant fromPlainObject(Object[] object, int firstElement) {
        // Create new participant
        Participant participant = new ParticipantImpl();
        // Map data form object
        participant.setPid((Long) object[firstElement++]);
        participant.setCid((Long) object[firstElement++]);
        participant.setParticipantId((Long) object[firstElement++]);
        participant.setUnreadMessagesCount((Integer) object[firstElement++]);
        participant.setIsOpened((Boolean) object[firstElement++]);
        participant.setOpenedAt((Long) object[firstElement]); // TODO: Change to calendar

        return participant;
    }

    /**
     * Factory method which creates a list of participants from a list of plain objects
     *
     * @param plainObjects List of plain objects
     * @param firstElement determines first element in Object[] where the serialization should start
     * @return list of participants
     */
    public static List<Participant> fromPlainObjectList(List<Object[]> plainObjects, int firstElement) {
        // Create new list
        List<Participant> participants = new LinkedList<Participant>();

        for (Object[] plainObject : plainObjects) {
            participants.add(ParticipantImpl.fromPlainObject(plainObject, firstElement));
        }

        return participants;
    }
}