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

package com.marcelmika.lims.persistence.generated.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderObjectInputStream;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BaseModel;

import com.marcelmika.lims.persistence.generated.model.ConversationClp;
import com.marcelmika.lims.persistence.generated.model.MessageClp;
import com.marcelmika.lims.persistence.generated.model.PanelClp;
import com.marcelmika.lims.persistence.generated.model.ParticipantClp;
import com.marcelmika.lims.persistence.generated.model.SettingsClp;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ClpSerializer {
	public static String getServletContextName() {
		if (Validator.isNotNull(_servletContextName)) {
			return _servletContextName;
		}

		synchronized (ClpSerializer.class) {
			if (Validator.isNotNull(_servletContextName)) {
				return _servletContextName;
			}

			try {
				ClassLoader classLoader = ClpSerializer.class.getClassLoader();

				Class<?> portletPropsClass = classLoader.loadClass(
						"com.liferay.util.portlet.PortletProps");

				Method getMethod = portletPropsClass.getMethod("get",
						new Class<?>[] { String.class });

				String portletPropsServletContextName = (String)getMethod.invoke(null,
						"lims-portlet-deployment-context");

				if (Validator.isNotNull(portletPropsServletContextName)) {
					_servletContextName = portletPropsServletContextName;
				}
			}
			catch (Throwable t) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Unable to locate deployment context from portlet properties");
				}
			}

			if (Validator.isNull(_servletContextName)) {
				try {
					String propsUtilServletContextName = PropsUtil.get(
							"lims-portlet-deployment-context");

					if (Validator.isNotNull(propsUtilServletContextName)) {
						_servletContextName = propsUtilServletContextName;
					}
				}
				catch (Throwable t) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Unable to locate deployment context from portal properties");
					}
				}
			}

			if (Validator.isNull(_servletContextName)) {
				_servletContextName = "lims-portlet";
			}

			return _servletContextName;
		}
	}

	public static Object translateInput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(ConversationClp.class.getName())) {
			return translateInputConversation(oldModel);
		}

		if (oldModelClassName.equals(MessageClp.class.getName())) {
			return translateInputMessage(oldModel);
		}

		if (oldModelClassName.equals(PanelClp.class.getName())) {
			return translateInputPanel(oldModel);
		}

		if (oldModelClassName.equals(ParticipantClp.class.getName())) {
			return translateInputParticipant(oldModel);
		}

		if (oldModelClassName.equals(SettingsClp.class.getName())) {
			return translateInputSettings(oldModel);
		}

		return oldModel;
	}

	public static Object translateInput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateInput(curObj));
		}

		return newList;
	}

	public static Object translateInputConversation(BaseModel<?> oldModel) {
		ConversationClp oldClpModel = (ConversationClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getConversationRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputMessage(BaseModel<?> oldModel) {
		MessageClp oldClpModel = (MessageClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getMessageRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputPanel(BaseModel<?> oldModel) {
		PanelClp oldClpModel = (PanelClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getPanelRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputParticipant(BaseModel<?> oldModel) {
		ParticipantClp oldClpModel = (ParticipantClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getParticipantRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInputSettings(BaseModel<?> oldModel) {
		SettingsClp oldClpModel = (SettingsClp)oldModel;

		BaseModel<?> newModel = oldClpModel.getSettingsRemoteModel();

		newModel.setModelAttributes(oldClpModel.getModelAttributes());

		return newModel;
	}

	public static Object translateInput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateInput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateInput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Object translateOutput(BaseModel<?> oldModel) {
		Class<?> oldModelClass = oldModel.getClass();

		String oldModelClassName = oldModelClass.getName();

		if (oldModelClassName.equals(
					"com.marcelmika.lims.persistence.generated.model.impl.ConversationImpl")) {
			return translateOutputConversation(oldModel);
		}

		if (oldModelClassName.equals(
					"com.marcelmika.lims.persistence.generated.model.impl.MessageImpl")) {
			return translateOutputMessage(oldModel);
		}

		if (oldModelClassName.equals(
					"com.marcelmika.lims.persistence.generated.model.impl.PanelImpl")) {
			return translateOutputPanel(oldModel);
		}

		if (oldModelClassName.equals(
					"com.marcelmika.lims.persistence.generated.model.impl.ParticipantImpl")) {
			return translateOutputParticipant(oldModel);
		}

		if (oldModelClassName.equals(
					"com.marcelmika.lims.persistence.generated.model.impl.SettingsImpl")) {
			return translateOutputSettings(oldModel);
		}

		return oldModel;
	}

	public static Object translateOutput(List<Object> oldList) {
		List<Object> newList = new ArrayList<Object>(oldList.size());

		for (int i = 0; i < oldList.size(); i++) {
			Object curObj = oldList.get(i);

			newList.add(translateOutput(curObj));
		}

		return newList;
	}

	public static Object translateOutput(Object obj) {
		if (obj instanceof BaseModel<?>) {
			return translateOutput((BaseModel<?>)obj);
		}
		else if (obj instanceof List<?>) {
			return translateOutput((List<Object>)obj);
		}
		else {
			return obj;
		}
	}

	public static Throwable translateThrowable(Throwable throwable) {
		if (_useReflectionToTranslateThrowable) {
			try {
				UnsyncByteArrayOutputStream unsyncByteArrayOutputStream = new UnsyncByteArrayOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(unsyncByteArrayOutputStream);

				objectOutputStream.writeObject(throwable);

				objectOutputStream.flush();
				objectOutputStream.close();

				UnsyncByteArrayInputStream unsyncByteArrayInputStream = new UnsyncByteArrayInputStream(unsyncByteArrayOutputStream.unsafeGetByteArray(),
						0, unsyncByteArrayOutputStream.size());

				Thread currentThread = Thread.currentThread();

				ClassLoader contextClassLoader = currentThread.getContextClassLoader();

				ObjectInputStream objectInputStream = new ClassLoaderObjectInputStream(unsyncByteArrayInputStream,
						contextClassLoader);

				throwable = (Throwable)objectInputStream.readObject();

				objectInputStream.close();

				return throwable;
			}
			catch (SecurityException se) {
				if (_log.isInfoEnabled()) {
					_log.info("Do not use reflection to translate throwable");
				}

				_useReflectionToTranslateThrowable = false;
			}
			catch (Throwable throwable2) {
				_log.error(throwable2, throwable2);

				return throwable2;
			}
		}

		Class<?> clazz = throwable.getClass();

		String className = clazz.getName();

		if (className.equals(PortalException.class.getName())) {
			return new PortalException();
		}

		if (className.equals(SystemException.class.getName())) {
			return new SystemException();
		}

		if (className.equals(
					"com.marcelmika.lims.persistence.generated.NoSuchConversationException")) {
			return new com.marcelmika.lims.persistence.generated.NoSuchConversationException();
		}

		if (className.equals(
					"com.marcelmika.lims.persistence.generated.NoSuchMessageException")) {
			return new com.marcelmika.lims.persistence.generated.NoSuchMessageException();
		}

		if (className.equals(
					"com.marcelmika.lims.persistence.generated.NoSuchPanelException")) {
			return new com.marcelmika.lims.persistence.generated.NoSuchPanelException();
		}

		if (className.equals(
					"com.marcelmika.lims.persistence.generated.NoSuchParticipantException")) {
			return new com.marcelmika.lims.persistence.generated.NoSuchParticipantException();
		}

		if (className.equals(
					"com.marcelmika.lims.persistence.generated.NoSuchSettingsException")) {
			return new com.marcelmika.lims.persistence.generated.NoSuchSettingsException();
		}

		return throwable;
	}

	public static Object translateOutputConversation(BaseModel<?> oldModel) {
		ConversationClp newModel = new ConversationClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setConversationRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputMessage(BaseModel<?> oldModel) {
		MessageClp newModel = new MessageClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setMessageRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputPanel(BaseModel<?> oldModel) {
		PanelClp newModel = new PanelClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setPanelRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputParticipant(BaseModel<?> oldModel) {
		ParticipantClp newModel = new ParticipantClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setParticipantRemoteModel(oldModel);

		return newModel;
	}

	public static Object translateOutputSettings(BaseModel<?> oldModel) {
		SettingsClp newModel = new SettingsClp();

		newModel.setModelAttributes(oldModel.getModelAttributes());

		newModel.setSettingsRemoteModel(oldModel);

		return newModel;
	}

	private static Log _log = LogFactoryUtil.getLog(ClpSerializer.class);
	private static String _servletContextName;
	private static boolean _useReflectionToTranslateThrowable = true;
}