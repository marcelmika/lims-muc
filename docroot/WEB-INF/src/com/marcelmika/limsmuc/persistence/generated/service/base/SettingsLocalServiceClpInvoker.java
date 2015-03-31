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

package com.marcelmika.limsmuc.persistence.generated.service.base;

import com.marcelmika.limsmuc.persistence.generated.service.SettingsLocalServiceUtil;

import java.util.Arrays;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SettingsLocalServiceClpInvoker {
	public SettingsLocalServiceClpInvoker() {
		_methodName0 = "addSettings";

		_methodParameterTypes0 = new String[] {
				"com.marcelmika.limsmuc.persistence.generated.model.Settings"
			};

		_methodName1 = "createSettings";

		_methodParameterTypes1 = new String[] { "long" };

		_methodName2 = "deleteSettings";

		_methodParameterTypes2 = new String[] { "long" };

		_methodName3 = "deleteSettings";

		_methodParameterTypes3 = new String[] {
				"com.marcelmika.limsmuc.persistence.generated.model.Settings"
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
				"com.marcelmika.limsmuc.persistence.generated.model.Settings"
			};

		_methodName60 = "getBeanIdentifier";

		_methodParameterTypes60 = new String[] {  };

		_methodName61 = "setBeanIdentifier";

		_methodParameterTypes61 = new String[] { "java.lang.String" };

		_methodName66 = "getSettingsByUser";

		_methodParameterTypes66 = new String[] { "long" };

		_methodName67 = "fetchByUserId";

		_methodParameterTypes67 = new String[] { "long" };

		_methodName68 = "fetchByUserId";

		_methodParameterTypes68 = new String[] { "long", "boolean" };

		_methodName69 = "createSettings";

		_methodParameterTypes69 = new String[] {  };

		_methodName70 = "saveSettings";

		_methodParameterTypes70 = new String[] {
				"com.marcelmika.limsmuc.persistence.generated.model.Settings"
			};

		_methodName71 = "changePresence";

		_methodParameterTypes71 = new String[] { "long", "java.lang.String" };

		_methodName72 = "updateConnection";

		_methodParameterTypes72 = new String[] { "java.lang.Long", "boolean" };

		_methodName73 = "updateAllConnections";

		_methodParameterTypes73 = new String[] { "int" };

		_methodName74 = "getConnectedUsers";

		_methodParameterTypes74 = new String[] {  };

		_methodName75 = "setChatEnabled";

		_methodParameterTypes75 = new String[] { "long", "boolean" };

		_methodName76 = "countAllUsers";

		_methodParameterTypes76 = new String[] {
				"java.lang.Long", "boolean", "boolean"
			};

		_methodName77 = "findAllGroups";

		_methodParameterTypes77 = new String[] {
				"java.lang.Long", "boolean", "boolean", "int", "int"
			};

		_methodName78 = "searchAllBuddies";

		_methodParameterTypes78 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"int", "int"
			};

		_methodName79 = "countSitesGroupUsers";

		_methodParameterTypes79 = new String[] {
				"java.lang.Long", "java.lang.Long", "boolean", "boolean"
			};

		_methodName80 = "findSitesGroups";

		_methodParameterTypes80 = new String[] {
				"java.lang.Long", "java.lang.String[][]"
			};

		_methodName81 = "readSitesGroup";

		_methodParameterTypes81 = new String[] {
				"java.lang.Long", "java.lang.Long", "boolean", "boolean", "int",
				"int"
			};

		_methodName82 = "searchSitesBuddies";

		_methodParameterTypes82 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"java.lang.String[][]", "int", "int"
			};

		_methodName83 = "countSocialGroupUsers";

		_methodParameterTypes83 = new String[] {
				"java.lang.Long", "java.lang.Long", "boolean", "boolean"
			};

		_methodName84 = "findSocialGroups";

		_methodParameterTypes84 = new String[] { "java.lang.Long", "int[][]" };

		_methodName85 = "readSocialGroup";

		_methodParameterTypes85 = new String[] {
				"java.lang.Long", "java.lang.Long", "boolean", "boolean", "int",
				"int"
			};

		_methodName86 = "searchSocialBuddies";

		_methodParameterTypes86 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"int[][]", "int", "int"
			};

		_methodName87 = "countUserGroupUsers";

		_methodParameterTypes87 = new String[] {
				"java.lang.Long", "java.lang.Long", "boolean", "boolean"
			};

		_methodName88 = "findUserGroups";

		_methodParameterTypes88 = new String[] {
				"java.lang.Long", "java.lang.String[][]"
			};

		_methodName89 = "readUserGroup";

		_methodParameterTypes89 = new String[] {
				"java.lang.Long", "java.lang.Long", "boolean", "boolean", "int",
				"int"
			};

		_methodName90 = "searchUserGroupsBuddies";

		_methodParameterTypes90 = new String[] {
				"java.lang.Long", "java.lang.String", "boolean", "boolean",
				"java.lang.String[][]", "int", "int"
			};
	}

	public Object invokeMethod(String name, String[] parameterTypes,
		Object[] arguments) throws Throwable {
		if (_methodName0.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes0, parameterTypes)) {
			return SettingsLocalServiceUtil.addSettings((com.marcelmika.limsmuc.persistence.generated.model.Settings)arguments[0]);
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
			return SettingsLocalServiceUtil.deleteSettings((com.marcelmika.limsmuc.persistence.generated.model.Settings)arguments[0]);
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
			return SettingsLocalServiceUtil.updateSettings((com.marcelmika.limsmuc.persistence.generated.model.Settings)arguments[0]);
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
			return SettingsLocalServiceUtil.fetchByUserId(((Long)arguments[0]).longValue());
		}

		if (_methodName68.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes68, parameterTypes)) {
			return SettingsLocalServiceUtil.fetchByUserId(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName69.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes69, parameterTypes)) {
			return SettingsLocalServiceUtil.createSettings();
		}

		if (_methodName70.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes70, parameterTypes)) {
			return SettingsLocalServiceUtil.saveSettings((com.marcelmika.limsmuc.persistence.generated.model.Settings)arguments[0]);
		}

		if (_methodName71.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes71, parameterTypes)) {
			SettingsLocalServiceUtil.changePresence(((Long)arguments[0]).longValue(),
				(java.lang.String)arguments[1]);

			return null;
		}

		if (_methodName72.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes72, parameterTypes)) {
			return SettingsLocalServiceUtil.updateConnection((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue());
		}

		if (_methodName73.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes73, parameterTypes)) {
			return SettingsLocalServiceUtil.updateAllConnections(((Integer)arguments[0]).intValue());
		}

		if (_methodName74.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes74, parameterTypes)) {
			return SettingsLocalServiceUtil.getConnectedUsers();
		}

		if (_methodName75.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes75, parameterTypes)) {
			SettingsLocalServiceUtil.setChatEnabled(((Long)arguments[0]).longValue(),
				((Boolean)arguments[1]).booleanValue());

			return null;
		}

		if (_methodName76.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes76, parameterTypes)) {
			return SettingsLocalServiceUtil.countAllUsers((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue(),
				((Boolean)arguments[2]).booleanValue());
		}

		if (_methodName77.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes77, parameterTypes)) {
			return SettingsLocalServiceUtil.findAllGroups((java.lang.Long)arguments[0],
				((Boolean)arguments[1]).booleanValue(),
				((Boolean)arguments[2]).booleanValue(),
				((Integer)arguments[3]).intValue(),
				((Integer)arguments[4]).intValue());
		}

		if (_methodName78.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes78, parameterTypes)) {
			return SettingsLocalServiceUtil.searchAllBuddies((java.lang.Long)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName79.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes79, parameterTypes)) {
			return SettingsLocalServiceUtil.countSitesGroupUsers((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue());
		}

		if (_methodName80.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes80, parameterTypes)) {
			return SettingsLocalServiceUtil.findSitesGroups((java.lang.Long)arguments[0],
				(java.lang.String[])arguments[1]);
		}

		if (_methodName81.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes81, parameterTypes)) {
			return SettingsLocalServiceUtil.readSitesGroup((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName82.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes82, parameterTypes)) {
			return SettingsLocalServiceUtil.searchSitesBuddies((java.lang.Long)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				(java.lang.String[])arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue());
		}

		if (_methodName83.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes83, parameterTypes)) {
			return SettingsLocalServiceUtil.countSocialGroupUsers((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue());
		}

		if (_methodName84.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes84, parameterTypes)) {
			return SettingsLocalServiceUtil.findSocialGroups((java.lang.Long)arguments[0],
				(int[])arguments[1]);
		}

		if (_methodName85.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes85, parameterTypes)) {
			return SettingsLocalServiceUtil.readSocialGroup((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName86.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes86, parameterTypes)) {
			return SettingsLocalServiceUtil.searchSocialBuddies((java.lang.Long)arguments[0],
				(java.lang.String)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(), (int[])arguments[4],
				((Integer)arguments[5]).intValue(),
				((Integer)arguments[6]).intValue());
		}

		if (_methodName87.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes87, parameterTypes)) {
			return SettingsLocalServiceUtil.countUserGroupUsers((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue());
		}

		if (_methodName88.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes88, parameterTypes)) {
			return SettingsLocalServiceUtil.findUserGroups((java.lang.Long)arguments[0],
				(java.lang.String[])arguments[1]);
		}

		if (_methodName89.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes89, parameterTypes)) {
			return SettingsLocalServiceUtil.readUserGroup((java.lang.Long)arguments[0],
				(java.lang.Long)arguments[1],
				((Boolean)arguments[2]).booleanValue(),
				((Boolean)arguments[3]).booleanValue(),
				((Integer)arguments[4]).intValue(),
				((Integer)arguments[5]).intValue());
		}

		if (_methodName90.equals(name) &&
				Arrays.deepEquals(_methodParameterTypes90, parameterTypes)) {
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
	private String _methodName80;
	private String[] _methodParameterTypes80;
	private String _methodName81;
	private String[] _methodParameterTypes81;
	private String _methodName82;
	private String[] _methodParameterTypes82;
	private String _methodName83;
	private String[] _methodParameterTypes83;
	private String _methodName84;
	private String[] _methodParameterTypes84;
	private String _methodName85;
	private String[] _methodParameterTypes85;
	private String _methodName86;
	private String[] _methodParameterTypes86;
	private String _methodName87;
	private String[] _methodParameterTypes87;
	private String _methodName88;
	private String[] _methodParameterTypes88;
	private String _methodName89;
	private String[] _methodParameterTypes89;
	private String _methodName90;
	private String[] _methodParameterTypes90;
}