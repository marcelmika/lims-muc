<!DOCTYPE html>
<%--
  ~ Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
  ~
  ~ Unauthorized copying of this file, via any medium is strictly prohibited
  ~ Proprietary and confidential
  ~
  ~ Written by Marcel Mika <marcelmika.com>, 2014
  --%>

<%-- Inits --%>
<%@ include file="/html/init.jsp" %>

<%-- Variables --%>
<%--@elvariable id="isAdmin" type="boolean"--%>
<%--@elvariable id="version" type="String"--%>
<%--@elvariable id="instanceKey" type="String"--%>

<%-- Constants --%>
<c:set var="PREFERENCES" value="<%= Environment.PropertiesSource.PREFERENCES %>"/>

<div id="root"></div>

<%-- ADMIN AREA --%>
<%--<c:if test="${properties.propertiesSource eq PREFERENCES}">--%>
    <%--<div class="admin-area">--%>
        <%--<div class="settings-title">--%>
            <%--<div class="settings-title-text">--%>
                <%--<liferay-ui:message key="panel-settings-admin-area-title"/>--%>
            <%--</div>--%>
            <%--<a class="help" href="${properties.urlHelp}" target="_blank"--%>
               <%--title="<liferay-ui:message key="panel-settings-admin-area-help"/>"></a>--%>

            <%--<div class="clear"></div>--%>
        <%--</div>--%>

        <%--<div class="settings">--%>
            <%--<div class="version">${version}</div>--%>
            <%--<div class="lfr-component list">--%>
                <%--<%@ include file="/html/settings/fragments/excluded-sites.jspf" %>--%>

                    <%--&lt;%&ndash; Contact List &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon contact-list"></div>--%>
                    <%--<span>Contact List</span>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-strategy.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-ignore-deactivated-user.jspf" %>--%>

                    <%--&lt;%&ndash; Site Groups &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon group-sites"></div>--%>
                    <%--<span>Site Groups</span>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-site-enabled.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-site-excludes.jspf" %>--%>

                    <%--&lt;%&ndash; Social Connections Groups &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon group-social"></div>--%>
                    <%--<span>Social Connections Groups</span>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-social-enabled.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-social-relation.jspf" %>--%>

                    <%--&lt;%&ndash; User Groups &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon group-user"></div>--%>
                    <%--<span>User Groups</span>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-group-enabled.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/buddy-list-group-excludes.jspf" %>--%>

                    <%--&lt;%&ndash; Mobile &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon mobile"></div>--%>
                    <%--<span><liferay-ui:message key="panel-settings-admin-area-mobile-category-title"/></span>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/mobile-user-scalable-disabled.jspf" %>--%>

                    <%--&lt;%&ndash; Jabber &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon jabber"></div>--%>
                    <%--<span><liferay-ui:message key="panel-settings-admin-area-jabber-category-title"/></span>--%>
                    <%--<a class="help" href="${properties.urlJabberHelp}" target="_blank"--%>
                       <%--title="<liferay-ui:message key="panel-settings-admin-area-help"/>"></a>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-enabled.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-security-enabled.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-import-user-enabled.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-host.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-port.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-service-name.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-resource.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-test-connection.jspf" %>--%>

                <%--<div class="category">--%>
                    <%--<div class="category-icon jabber"></div>--%>
                    <%--<span>Jabber (Experimental)</span>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-shared-secret-enabled.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/jabber-shared-secret.jspf" %>--%>

                    <%--&lt;%&ndash; IPC &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon ipc"></div>--%>
                    <%--<span><liferay-ui:message key="panel-settings-admin-area-ipc-category-title"/></span>--%>
                    <%--<a class="help" href="${properties.urlIpcHelp}" target="_blank"--%>
                       <%--title="<liferay-ui:message key="panel-settings-admin-area-help"/>"></a>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/ipc-enabled.jspf" %>--%>

                    <%--&lt;%&ndash; Synchrozniation &ndash;%&gt;--%>
                <%--<div class="category">--%>
                    <%--<div class="category-icon synchronization"></div>--%>
                    <%--<span>--%>
                                    <%--<liferay-ui:message key="panel-settings-admin-area-synchronization-category-title"/>--%>
                                <%--</span>--%>
                    <%--<a class="help" href="${properties.urlSynchronizationHelp}" target="_blank"--%>
                       <%--title="<liferay-ui:message key="panel-settings-admin-area-help"/>"></a>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/synchronization-suc.jspf" %>--%>
                <%--<%@ include file="/html/settings/fragments/synchronization-chat-portlet.jspf" %>--%>

                    <%--&lt;%&ndash; Custom license &ndash;%&gt;--%>
                <%--<div class="category">--%>
                                <%--<span>--%>
                                    <%--<liferay-ui:message key="panel-settings-admin-area-custom-license-title"/>--%>
                                <%--</span>--%>
                <%--</div>--%>
                <%--<%@ include file="/html/settings/fragments/custom-license.jspf" %>--%>

            <%--</div>--%>
        <%--</div>--%>
        <%--<div class="clear"></div>--%>
    <%--</div>--%>
<%--</c:if>--%>