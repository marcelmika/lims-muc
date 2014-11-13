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

import com.marcelmika.lims.persistence.generated.service.ParticipantLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class ParticipantLocalServiceClpInvoker {
	public ParticipantLocalServiceClpInvoker() {
		_methodName0 = "addParticipant";

		_methodParameterTypes0 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Participant"
			};

		_methodName1 = "createParticipant";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteParticipant";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteParticipant";

		_methodParameterTypes3 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Participant"
			};

		_methodName4 = "dynamicQuery";

		_methodParameterTypes4 = new String[] {  };

		_methodName5 = "dynamicQuery";

		_methodParameterTypes5 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName6 = "dynamicQuery";

		_methodParameterTypes6 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int"
			};

		_methodName7 = "dynamicQuery";

		_methodParameterTypes7 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery", "int", "int",
				"com.liferay.portal.kernel.util.OrderByComparator"
			};

		_methodName8 = "dynamicQueryCount";

		_methodParameterTypes8 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery"
			};

		_methodName9 = "dynamicQueryCount";

		_methodParameterTypes9 = new String[] {
				"com.liferay.portal.kernel.dao.orm.DynamicQuery",
				"com.liferay.portal.kernel.dao.orm.Projection"
			};

		_methodName10 = "fetchParticipant";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getParticipant";

		_methodParameterTypes11 = new String[] { "long" };

		_methodName12 = "getPersistedModel";

		_methodParameterTypes12 = new String[] { "java.io.Serializable" };

		_methodName13 = "getParticipants";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getParticipantsCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateParticipant";

		_methodParameterTypes15 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Participant"
			};

		_methodName60 = "getBeanIdentifier";

		_methodParameterTypes60 = new String[] {  };

		_methodName61 = "setBeanIdentifier";

		_methodParameterTypes61 = new String[] { "java.lang.String" };

		_methodName66 = "addParticipant";

		_methodParameterTypes66 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName67 = "updateParticipants";

		_methodParameterTypes67 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName68 = "closeConversation";

		_methodParameterTypes68 = new String[] {
				"java.lang.String", "java.lang.Long"
			};

		_methodName69 = "resetUnreadMessagesCounter";

		_methodParameterTypes69 = new String[] {
				"java.lang.String", "java.lang.Long"
			};

		_methodName70 = "getOpenedConversations";

		_methodParameterTypes70 = new String[] { "java.lang.Long" };

		_methodName71 = "switchConversations";

		_methodParameterTypes71 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Participant",
				"com.marcelmika.lims.persistence.generated.model.Participant"
			};

		_methodName72 = "getConversationParticipants";

		_methodParameterTypes72 = new String[] { "java.lang.Long" };

		_methodName73 = "getParticipant";

		_methodParameterTypes73 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName74 = "getConversations";

		_methodParameterTypes74 = new String[] {
				"java.lang.Long", "java.lang.Integer", "java.lang.Integer",
				"java.lang.Integer", "java.lang.Boolean"
			};

		_methodName75 = "getConversationsCount";

		_methodParameterTypes75 = new String[] { "java.lang.Long" };

		_methodName76 = "leaveConversation";

		_methodParameterTypes76 = new String[] {
				"java.lang.Long", "java.lang.Long"
			};

		_methodName77 = "addParticipant";

		_methodParameterTypes77 = new String[] {
				"java.lang.Long", "java.lang.Long", "boolean"
			};

		_methodName78 = "createParticipant";

		_methodParameterTypes78 = new String[] {  };

		_methodName79 = "saveParticipant";

		_methodParameterTypes79 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Participant"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return ParticipantLocalServiceUtil.addParticipant((com.marcelmika.lims.persistence.generated.model.Participant)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return ParticipantLocalServiceUtil.createParticipant(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return ParticipantLocalServiceUtil.deleteParticipant(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return ParticipantLocalServiceUtil.deleteParticipant((com.marcelmika.lims.persistence.generated.model.Participant)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return ParticipantLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return ParticipantLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return ParticipantLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return ParticipantLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return ParticipantLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return ParticipantLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return ParticipantLocalServiceUtil.fetchParticipant(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return ParticipantLocalServiceUtil.getParticipant(((Long)arguments[0]).longValue());
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return ParticipantLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return ParticipantLocalServiceUtil.getParticipants(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return ParticipantLocalServiceUtil.getParticipantsCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return ParticipantLocalServiceUtil.updateParticipant((com.marcelmika.lims.persistence.generated.model.Participant)arguments[0]);
		}

		if (_methodName60.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes60, parameterTypes)) {
			return ParticipantLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName61.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes61, parameterTypes)) {
			ParticipantLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName66.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes66, parameterTypes)) {
			return ParticipantLocalServiceUtil.addParticipant((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName67.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes67, parameterTypes)) {
			ParticipantLocalServiceUtil.updateParticipants((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);

			return null;
		}

		if (_methodName68.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes68, parameterTypes)) {
			ParticipantLocalServiceUtil.closeConversation((java.lang.String)arguments[0],
				(java.lang.Long)arguments[1]);

			return null;
		}

		if (_methodName69.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes69, parameterTypes)) {
			ParticipantLocalServiceUtil.resetUnreadMessagesCounter((java.lang.String)arguments[0],
				(java.lang.Long)arguments[1]);

			return null;
		}

		if (_methodName70.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes70, parameterTypes)) {
			return ParticipantLocalServiceUtil.getOpenedConversations((java.lang.Long)arguments[0]);
		}

		if (_methodName71.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes71, parameterTypes)) {
			ParticipantLocalServiceUtil.switchConversations((com.marcelmika.lims.persistence.generated.model.Participant)arguments[0],
				(com.marcelmika.lims.persistence.generated.model.Participant)arguments[1]);

			return null;
		}

		if (_methodName72.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes72, parameterTypes)) {
			return ParticipantLocalServiceUtil.getConversationParticipants((java.lang.Long)arguments[0]);
		}

		if (_methodName73.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes73, parameterTypes)) {
			return ParticipantLocalServiceUtil.getParticipant((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);
		}

		if (_methodName74.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes74, parameterTypes)) {
			return ParticipantLocalServiceUtil.getConversations((java.lang.Long)arguments[0],
				(java.lang.Integer)arguments[1],
				(java.lang.Integer)arguments[2],
				(java.lang.Integer)arguments[3], (java.lang.Boolean)arguments[4]);
		}

		if (_methodName75.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes75, parameterTypes)) {
			return ParticipantLocalServiceUtil.getConversationsCount((java.lang.Long)arguments[0]);
		}

		if (_methodName76.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes76, parameterTypes)) {
			ParticipantLocalServiceUtil.leaveConversation((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1]);

			return null;
		}

		if (_methodName77.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes77, parameterTypes)) {
			return ParticipantLocalServiceUtil.addParticipant((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1],
				((Boolean)arguments[2]).booleanValue());
		}

		if (_methodName78.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes78, parameterTypes)) {
			return ParticipantLocalServiceUtil.createParticipant();
		}

		if (_methodName79.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes79, parameterTypes)) {
			ParticipantLocalServiceUtil.saveParticipant((com.marcelmika.lims.persistence.generated.model.Participant)arguments[0]);

			return null;
		}

		throw new UnsupportedOperationException();
	}

	private String _methodName0;
	private String[] _methodParameterTypes0;
	private String _methodName1;
	private String[] _methodParameterTypes1;
	private String _methodName2;
	private String[] _methodParameterTypes2;
	private String _methodName3;
	private String[] _methodParameterTypes3;
	private String _methodName4;
	private String[] _methodParameterTypes4;
	private String _methodName5;
	private String[] _methodParameterTypes5;
	private String _methodName6;
	private String[] _methodParameterTypes6;
	private String _methodName7;
	private String[] _methodParameterTypes7;
	private String _methodName8;
	private String[] _methodParameterTypes8;
	private String _methodName9;
	private String[] _methodParameterTypes9;
	private String _methodName10;
	private String[] _methodParameterTypes10;
	private String _methodName11;
	private String[] _methodParameterTypes11;
	private String _methodName12;
	private String[] _methodParameterTypes12;
	private String _methodName13;
	private String[] _methodParameterTypes13;
	private String _methodName14;
	private String[] _methodParameterTypes14;
	private String _methodName15;
	private String[] _methodParameterTypes15;
	private String _methodName60;
	private String[] _methodParameterTypes60;
	private String _methodName61;
	private String[] _methodParameterTypes61;
	private String _methodName66;
	private String[] _methodParameterTypes66;
	private String _methodName67;
	private String[] _methodParameterTypes67;
	private String _methodName68;
	private String[] _methodParameterTypes68;
	private String _methodName69;
	private String[] _methodParameterTypes69;
	private String _methodName70;
	private String[] _methodParameterTypes70;
	private String _methodName71;
	private String[] _methodParameterTypes71;
	private String _methodName72;
	private String[] _methodParameterTypes72;
	private String _methodName73;
	private String[] _methodParameterTypes73;
	private String _methodName74;
	private String[] _methodParameterTypes74;
	private String _methodName75;
	private String[] _methodParameterTypes75;
	private String _methodName76;
	private String[] _methodParameterTypes76;
	private String _methodName77;
	private String[] _methodParameterTypes77;
	private String _methodName78;
	private String[] _methodParameterTypes78;
	private String _methodName79;
	private String[] _methodParameterTypes79;
}