<%--
  ~ Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
  ~
  ~ Unauthorized copying of this file, via any medium is strictly prohibited
  ~ Proprietary and confidential
  ~
  ~ Written by Marcel Mika <marcelmika.com>, 2014
  --%>

<%-- Variables --%>
<%--@elvariable id="isEnabled" type="boolean"--%>
<%--@elvariable id="isOverLimit" type="boolean"--%>
<%--@elvariable id="isSupportedBrowser" type="boolean"--%>
<%--@elvariable id="needsIESupport" type="boolean"--%>

<%-- Taglib --%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui" %>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme" %>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui" %>
<%@ taglib uri="http://liferay.com/tld/util" prefix="liferay-util" %>
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet" %>

<%-- Define Objects --%>
<liferay-theme:defineObjects/>
<portlet:defineObjects/>

<c:choose>
    <c:when test="${needsIESupport}">
        <c:set var="ieSupportClass" value="ie-support"/>
    </c:when>
    <c:otherwise>
        <c:set var="ieSupportClass" value=""/>
    </c:otherwise>
</c:choose>

<%-- Render only if the portlet is enabled --%>
<c:if test="${isEnabled}">

    <%-- LIMS bar --%>
    <div id="limsmuc-container" class="covered ${ieSupportClass}">

            <%-- Get the resource URL used in AJAX calls --%>
        <portlet:resourceURL var="portletResourceUrl"></portlet:resourceURL>

            <%-- Browser is supported --%>
        <c:if test="${isSupportedBrowser}">

            <%-- Portlet content --%>
            <div class="lims-bar">
                <div class="lims-sound"></div>
                <div class="lims-tabs-container">
                    <ul class="lims-tabs">
                        <%@ include file="/html/tabs/fragments/status-panel.jspf" %>
                        <%@ include file="/html/tabs/fragments/settings-panel.jspf" %>
                        <%@ include file="/html/tabs/fragments/group-list-panel.jspf" %>
                        <%@ include file="/html/tabs/fragments/conversation-feed.jspf" %>
                        <%@ include file="/html/tabs/fragments/conversations.jspf" %>
                    </ul>
                </div>
            </div>

            <%-- Javascript Templates --%>
            <%@ include file="/html/tabs/fragments/templates.jspf" %>

            <%-- Rendered properties passed to client --%>
            <%@ include file="/html/tabs/fragments/properties.jspf" %>

            <%-- Rendered i18n string used on client --%>
            <%@ include file="/html/tabs/fragments/i18n.jspf" %>

        </c:if>

            <%-- Browser is not supported--%>
        <c:if test="${!isSupportedBrowser}">
            <div class="unsupported-browser">
                <a href="${properties.urlUnsupportedBrowser}" target="_blank">
                    <liferay-ui:message key="unsupported-browser-message"/>
                </a>
            </div>
        </c:if>
    </div>

    <%-- Preloaded Images --%>
    <%@ include file="/html/tabs/fragments/preloaded-images.jspf" %>

    <%-- Conflict notifications --%>
    <%@ include file="/html/tabs/fragments/conflict.jspf" %>

</c:if>

<%-- Show over limit info --%>
<c:if test="${isOverLimit}">

    <div class="portlet-notification over-limit">
        <button class="close-notification"></button>
        <span class="notification-content">
            <liferay-ui:message key="over-limit-message"/>
        </span>
    </div>

</c:if>
