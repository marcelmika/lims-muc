/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

package com.marcelmika.limsmuc.portal.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.limsmuc.core.service.ConversationCoreService;
import com.marcelmika.limsmuc.core.service.ConversationCoreServiceUtil;
import com.marcelmika.limsmuc.core.service.SettingsCoreService;
import com.marcelmika.limsmuc.core.service.SettingsCoreServiceUtil;
import com.marcelmika.limsmuc.portal.domain.Buddy;
import com.marcelmika.limsmuc.portal.domain.Conversation;
import com.marcelmika.limsmuc.portal.domain.Properties;
import com.marcelmika.limsmuc.portal.domain.Settings;
import com.marcelmika.limsmuc.portal.http.HttpStatus;
import com.marcelmika.limsmuc.portal.localization.ConversationLocalizationUtil;
import com.marcelmika.limsmuc.portal.processor.PortletProcessor;
import com.marcelmika.limsmuc.portal.processor.PortletProcessorUtil;
import com.marcelmika.limsmuc.portal.properties.PortletPropertiesValues;
import com.marcelmika.limsmuc.portal.properties.PropertiesManager;
import com.marcelmika.limsmuc.portal.properties.PropertiesManagerUtil;

import javax.portlet.*;
import java.io.IOException;
import java.util.List;

/**
 * Main MVC Portlet class for LIMS
 *
 * @author Ing. Marcel Mika
 * @link http://marcelmika.com/lims
 * Date: 11/24/13
 * Time: 11:18 PM
 */
public class LIMSPortlet extends MVCPortlet {

    // Processor Dependency
    PortletProcessor processor = PortletProcessorUtil.getPortletProcessor();

    // Service Dependencies
    SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();
    ConversationCoreService conversationCoreService = ConversationCoreServiceUtil.getConversationCoreService();

    // Properties Dependencies
    PropertiesManager propertiesManager = PropertiesManagerUtil.getPropertiesManager();

    // Constants
    private static final String VIEW_JSP_PATH = "/view.jsp"; // Path to the view.jsp

    // Variable keys
    private static final String VARIABLE_IS_ADMIN = "isAdmin";
    private static final String VARIABLE_PROPERTIES = "properties";
    private static final String VARIABLE_IS_SUPPORTED_BROWSER = "isSupportedBrowser";
    private static final String VARIABLE_NEEDS_IE_SUPPORT = "needsIESupport";
    private static final String VARIABLE_SETTINGS = "settings";
    private static final String VARIABLE_CONVERSATIONS = "conversations";
    private static final String VARIABLE_IS_ENABLED = "isEnabled";
    private static final String VARIABLE_CURRENT_USER = "currentUser";
    private static final String VARIABLE_VERSION = "version";

    // Log
    private static Log log = LogFactoryUtil.getLog(LIMSPortlet.class);

    /**
     * This method is called whenever the view is rendered. All data needed to render the main LIMS view (i.e. panels
     * and their content) should be loaded here. Any heavy computation or long-term database operations should be
     * avoided. Always use asynchronous ajax request (via serveResource() method) to get proper data. This method
     * should be used for panel rendering only.
     * <p/>
     * All data is passed to view.jsp which is responsible for view
     *
     * @param renderRequest  RenderRequest
     * @param renderResponse RenderResponse
     * @throws PortletException
     * @throws IOException
     */
    @Override
    public void doView(RenderRequest renderRequest,
                       RenderResponse renderResponse) throws PortletException, IOException {

        // Environment needs to be set up at the beginning of the request
        propertiesManager.setup(renderRequest.getPreferences());

        // Check if the current site is excluded
        boolean isExcluded = isExcluded(renderRequest);

        // Site is excluded
        if (isExcluded) {
            // Disable portlet
            renderRequest.setAttribute(VARIABLE_IS_ENABLED, false);
        }
        // Site is not excluded
        else {

            // Check the availability of browser
            boolean isSupportedBrowser = BrowserDetector.isSupportedBrowser(renderRequest);

            // Render portlet only if the browser is supported
            if (isSupportedBrowser) {
                // Settings pane
                renderSettings(renderRequest);
                // Conversations pane
                renderConversations(renderRequest);
            }

            // Set correct content type
            renderResponse.setContentType(renderRequest.getResponseContentType());
            // Additional parameters
            renderAdditions(renderRequest);
        }

        // Set response to view.jsp
        include(VIEW_JSP_PATH, renderRequest, renderResponse);
    }

    /**
     * This method is called whenever the server gets an AJAX request from client. All asynchronous requests
     * should go over this method.
     *
     * @param request  Asynchronous request from client
     * @param response Response from server
     * @throws PortletException
     * @throws IOException
     */
    @Override
    public void serveResource(ResourceRequest request, ResourceResponse response) throws PortletException, IOException {
        // Do not continue if the user is not signed in
        if (!isCorrectAttempt(request)) {
            // Return unauthorized response code
            response.setProperty(ResourceResponse.HTTP_STATUS_CODE, HttpStatus.UNAUTHORIZED.toString());
            return;
        }

        // Environment needs to be set up at the beginning of the request
        propertiesManager.setup(request.getPreferences());

        // Response content type is JSON
        response.setContentType(ContentTypes.APPLICATION_JSON);

        // This is an entry point to the whole app. Processor will do all the necessary work and fill the response.
        processor.processRequest(request, response);
    }

    /**
     * Renders settings pane within the request
     *
     * @param renderRequest RenderRequest
     */
    private void renderSettings(RenderRequest renderRequest) {

        // Get buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);

        // Get buddy's settings
        ReadSettingsResponseEvent responseEvent = settingsCoreService.readSettings(
                new ReadSettingsRequestEvent(buddy.toBuddyDetails())
        );

        // Pass them to jsp only if the request was successful
        if (responseEvent.isSuccess()) {

            // Map settings from details
            Settings settings = Settings.fromSettingsDetails(responseEvent.getSettingsDetails());

            // Pass to jsp
            renderRequest.setAttribute(VARIABLE_SETTINGS, settings);
        }
        // Log failure
        else {
            log.error(responseEvent.getException());
        }
    }

    /**
     * Renders opened conversations. Thanks to this whenever the user goes to different page the opened conversation
     * is already opened. In other words we don't need to wait for the ajax response.
     *
     * @param renderRequest RenderRequest
     */
    private void renderConversations(RenderRequest renderRequest) {

        // Get buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);

        // Get opened conversations
        GetOpenedConversationsResponseEvent responseEvent = conversationCoreService.getOpenedConversations(
                new GetOpenedConversationsRequestEvent(buddy.toBuddyDetails())
        );

        // Pass them to jsp only if the request was successful
        if (responseEvent.isSuccess()) {

            // Map conversation from details
            List<Conversation> conversationList = Conversation.fromConversationDetailsList(
                    responseEvent.getConversationDetails()
            );

            // Localize conversation list
            conversationList = ConversationLocalizationUtil.localizeConversationList(conversationList, renderRequest);

            // Pass to jsp
            renderRequest.setAttribute(VARIABLE_CONVERSATIONS, conversationList);
        }
        // Log failure
        else {
            log.error(responseEvent.getException());
        }
    }

    /**
     * Renders additional parameters needed in jsp
     *
     * @param renderRequest RenderRequest
     */
    private void renderAdditions(RenderRequest renderRequest) {
        // Get buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);
        // Check if the user is admin
        renderRequest.setAttribute(VARIABLE_IS_ADMIN, PermissionDetector.isAdmin(renderRequest));
        // Render properties
        renderRequest.setAttribute(VARIABLE_PROPERTIES, Properties.fromEnvironment());
        // Check if lims is enabled and pass it to jsp as a parameter
        renderRequest.setAttribute(VARIABLE_IS_ENABLED, isCorrectAttempt(renderRequest));
        // Check if the browser is supported
        renderRequest.setAttribute(VARIABLE_IS_SUPPORTED_BROWSER, BrowserDetector.isSupportedBrowser(renderRequest));
        // Check if the browser needs support
        renderRequest.setAttribute(VARIABLE_NEEDS_IE_SUPPORT, BrowserDetector.needsInternetExplorerSupport(renderRequest));
        // User values cannot be accessed via javascript so we need to render it manually
        renderRequest.setAttribute(VARIABLE_CURRENT_USER, buddy);
        // Version
        renderRequest.setAttribute(VARIABLE_VERSION, PortletPropertiesValues.VERSION);
    }

    /**
     * Checks if the server request attempt is correct. In other words checks if the user is signed in.
     *
     * @param request PortletRequest
     * @return true if the request attempt is correct
     */
    private boolean isCorrectAttempt(PortletRequest request) {
        // Check if the user is signed in
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        // Check for params
        if (themeDisplay == null) {
            return false;
        }

        // Returns true if the user is signed in
        return themeDisplay.isSignedIn();
    }


    /**
     * Returns true if the current site is excluded
     *
     * @param request PortletRequest
     * @return true if the current site is excluded
     */
    private boolean isExcluded(PortletRequest request) {
        // Check if the user is signed in
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        // Check for params
        if (themeDisplay == null) {
            return true;
        }

        try {
            // Get the current site name (group == site)
            String siteName = themeDisplay.getLayout().getGroup().getName();
            // Get excluded sites
            String[] excludedSites = Environment.getExcludedSites();

            // Check if the current site belongs to the excluded sites list
            for (String excludedSite : excludedSites) {
                // Site is excluded
                if (excludedSite.equals(siteName)) {
                    return true;
                }
            }

            // No site was found -> site is not excluded
            return false;
        }
        // Failure
        catch (Exception exception) {
            // Log
            if (log.isDebugEnabled()) {
                log.debug(exception);
            }
            // Cannot get the site name -> exclude portlet
            return true;
        }
    }
}
