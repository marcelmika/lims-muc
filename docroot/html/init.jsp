<%--
  ~ Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
  ~
  ~ Unauthorized copying of this file, via any medium is strictly prohibited
  ~ Proprietary and confidential
  ~
  ~ Written by Marcel Mika <marcelmika.com>, 2014
  --%>

<%-- Taglibs --%>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- Liferay Theme --%>
<liferay-theme:defineObjects/>

<%-- Portlet --%>
<portlet:defineObjects/>
<portlet:resourceURL var="resourceURL" />

<%-- Imports --%>
<%@ page import="com.marcelmika.limsmuc.api.environment.Environment" %>