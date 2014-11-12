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

/**
 * @author Brian Wing Shun Chan
 */
public interface SynchronizationFinder {
	public java.util.List<java.lang.Object[]> findSUCSettings_1_2_0(int start,
		int end) throws org.omg.CORBA.SystemException;

	public java.util.List<java.lang.Object[]> findSUCPanel_1_2_0(int start,
		int end) throws org.omg.CORBA.SystemException;

	public java.util.List<java.lang.Object[]> findSUCConversation_1_2_0(
		int start, int end) throws org.omg.CORBA.SystemException;

	public java.util.List<java.lang.Object[]> findSUCParticipant_1_2_0(
		int start, int end) throws org.omg.CORBA.SystemException;
}