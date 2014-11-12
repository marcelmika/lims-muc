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

import com.marcelmika.lims.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SettingsLocalServiceClpInvoker {
	public SettingsLocalServiceClpInvoker() {
		_methodName0 = "addSettings";

		_methodParameterTypes0 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Settings"
			};

		_methodName1 = "createSettings";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteSettings";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteSettings";

		_methodParameterTypes3 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Settings"
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

		_methodName10 = "fetchSettings";

		_methodParameterTypes10 = new String[] { "long" };

		_methodName11 = "getSettings";

		_methodParameterTypes11 = new String[] { "long" };

		_methodName12 = "getPersistedModel";

		_methodParameterTypes12 = new String[] { "java.io.Serializable" };

		_methodName13 = "getSettingses";

		_methodParameterTypes13 = new String[] { "int", "int" };

		_methodName14 = "getSettingsesCount";

		_methodParameterTypes14 = new String[] {  };

		_methodName15 = "updateSettings";

		_methodParameterTypes15 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Settings"
			};

		_methodName60 = "getBeanIdentifier";

		_methodParameterTypes60 = new String[] {  };

		_methodName61 = "setBeanIdentifier";

		_methodParameterTypes61 = new String[] { "java.lang.String" };

		_methodName66 = "getSettingsByUser";

		_methodParameterTypes66 = new String[] { "long" };

		_methodName67 = "saveSettings";

		_methodParameterTypes67 = new String[] {
				"com.marcelmika.lims.persistence.generated.model.Settings"
			};

		_methodName68 = "changePresence";

		_methodParameterTypes68 = new String[] { "long", "java.lang.String" };

		_methodName69 = "updateConnection";

		_methodParameterTypes69 = new String[] { "java.lang.Long", "boolean" };

		_methodName70 = "updateAllConnections";

		_methodParameterTypes70 = new String[] { "int" };

		_methodName71 = "setChatEnabled";

		_methodParameterTypes71 = new String[] { "long", "boolean" };

		_methodName72 = "getAllGroups";

		_methodParameterTypes72 = new String[] {
				"java.lang.Long", "boolean", "boolean", "int", "int"
			};

		_methodName73 = "getSitesGroups";

		_methodParameterTypes73 = new String[] {
				"java.lang.Long", "boolean", "boolean", "java.lang.String[][]",
				"int", "int"
			};

		_methodName74 = "getSocialGroups";

		_methodParameterTypes74 = new String[] {
				"java.lang.Long", "boolean", "boolean", "int[][]", "int", "int"
			};

		_methodName75 = "getUserGroups";

		_methodParameterTypes75 = new String[] {
				"java.lang.Long", "boolean", "boolean", "java.lang.String[][]",
				"int", "int"
			};

		_methodName76 = "searchSitesBuddies";

		_methodParameterTypes76 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"java.lang.String[][]", "int", "int"
			};

		_methodName77 = "searchAllBuddies";

		_methodParameterTypes77 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"int", "int"
			};

		_methodName78 = "searchSocialBuddies";

		_methodParameterTypes78 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"int[][]", "int", "int"
			};

		_methodName79 = "searchUserGroupsBuddies";

		_methodParameterTypes79 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"java.lang.String[][]", "int", "int"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return SettingsLocalServiceUtil.addSettings((com.marcelmika.lims.persistence.generated.model.Settings)arguments[0]);
		}

		if (_methodName1.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes1, parameterTypes)) {
			return SettingsLocalServiceUtil.createSettings(((Long)arguments[0]).longValue());
		}

		if (_methodName2.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes2, parameterTypes)) {
			return SettingsLocalServiceUtil.deleteSettings(((Long)arguments[0]).longValue());
		}

		if (_methodName3.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes3, parameterTypes)) {
			return SettingsLocalServiceUtil.deleteSettings((com.marcelmika.lims.persistence.generated.model.Settings)arguments[0]);
		}

		if (_methodName4.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes4, parameterTypes)) {
			return SettingsLocalServiceUtil.dynamicQuery();
		}

		if (_methodName5.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes5, parameterTypes)) {
			return SettingsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName6.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes6, parameterTypes)) {
			return SettingsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue());
		}

		if (_methodName7.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes7, parameterTypes)) {
			return SettingsLocalServiceUtil.dynamicQuery((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				((Integer)arguments[1]).intValue(),
				((Integer)arguments[2]).intValue(),
				(com.liferay.portal.kernel.util.OrderByComparator)arguments[3]);
		}

		if (_methodName8.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes8, parameterTypes)) {
			return SettingsLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0]);
		}

		if (_methodName9.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes9, parameterTypes)) {
			return SettingsLocalServiceUtil.dynamicQueryCount((com.liferay.portal.kernel.dao.orm.DynamicQuery)arguments[0],
				(com.liferay.portal.kernel.dao.orm.Projection)arguments[1]);
		}

		if (_methodName10.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes10, parameterTypes)) {
			return SettingsLocalServiceUtil.fetchSettings(((Long)arguments[0]).longValue());
		}

		if (_methodName11.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes11, parameterTypes)) {
			return SettingsLocalServiceUtil.getSettings(((Long)arguments[0]).longValue());
		}

		if (_methodName12.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes12, parameterTypes)) {
			return SettingsLocalServiceUtil.getPersistedModel((java.io.Serializable)arguments[0]);
		}

		if (_methodName13.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes13, parameterTypes)) {
			return SettingsLocalServiceUtil.getSettingses(((Integer)arguments[0]).intValue(),
				((Integer)arguments[1]).intValue());
		}

		if (_methodName14.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes14, parameterTypes)) {
			return SettingsLocalServiceUtil.getSettingsesCount();
		}

		if (_methodName15.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes15, parameterTypes)) {
			return SettingsLocalServiceUtil.updateSettings((com.marcelmika.lims.persistence.generated.model.Settings)arguments[0]);
		}

		if (_methodName60.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes60, parameterTypes)) {
			return SettingsLocalServiceUtil.getBeanIdentifier();
		}

		if (_methodName61.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes61, parameterTypes)) {
			SettingsLocalServiceUtil.setBeanIdentifier((java.lang.String)arguments[0]);

			return null;
		}

		if (_methodName66.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes66, parameterTypes)) {
			return SettingsLocalServiceUtil.getSettingsByUser(((Long)arguments[0]).longValue());
		}

		if (_methodName67.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes67, parameterTypes)) {
			return SettingsLocalServiceUtil.saveSettings((com.marcelmika.lims.persistence.generated.model.Settings)arguments[0]);
		}

		if (_methodName68.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes68, parameterTypes)) {
			SettingsLocalServiceUtil.changePresence(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName69.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes69, parameterTypes)) {
			return SettingsLocalServiceUtil.updateConnection((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName70.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes70, parameterTypes)) {
			SettingsLocalServiceUtil.updateAllConnections(((Integer)arguments[0]).intValue());

			return null;
		}

		if (_methodName71.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes71, parameterTypes)) {
			SettingsLocalServiceUtil.setChatEnabled(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());

			return null;
		}

		if (_methodName72.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes72, parameterTypes)) {
			return SettingsLocalServiceUtil.getAllGroups((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue(),
				((Boolean)arguments[2]).booleanValue(),
				((Integer)arguments[3]).intValue(),
				((Integer)arguments[4]).intValue());
		}

		if (_methodName73.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes73, parameterTypes)) {
			return SettingsLocalServiceUtil.getSitesGroups((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue(),
				((Boolean)arguments[2]).booleanValue(),
				(java.lang.String[])arguments[3],
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName74.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes74, parameterTypes)) {
			return SettingsLocalServiceUtil.getSocialGroups((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue(),
				((Boolean)arguments[2]).booleanValue(), (int[])arguments[3],
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName75.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes75, parameterTypes)) {
			return SettingsLocalServiceUtil.getUserGroups((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue(),
				((Boolean)arguments[2]).booleanValue(),
				(java.lang.String[])arguments[3],
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName76.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes76, parameterTypes)) {
			return SettingsLocalServiceUtil.searchSitesBuddies((java.lang.Long)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String[])arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue());
		}

		if (_methodName77.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes77, parameterTypes)) {
			return SettingsLocalServiceUtil.searchAllBuddies((java.lang.Long)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName78.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes78, parameterTypes)) {
			return SettingsLocalServiceUtil.searchSocialBuddies((java.lang.Long)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(), (int[])arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue());
		}

		if (_methodName79.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes79, parameterTypes)) {
			return SettingsLocalServiceUtil.searchUserGroupsBuddies((java.lang.Long)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String[])arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue());
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