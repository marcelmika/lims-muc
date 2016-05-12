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
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.marcelmika.limsmuc.api.environment.Environment;
import com.marcelmika.limsmuc.api.environment.License;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsRequestEvent;
import com.marcelmika.limsmuc.api.events.conversation.GetOpenedConversationsResponseEvent;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetDisplayPermissionResponseEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyRequestEvent;
import com.marcelmika.limsmuc.api.events.permission.GetInstanceKeyResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSessionLimitRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSessionLimitResponseEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsRequestEvent;
import com.marcelmika.limsmuc.api.events.settings.ReadSettingsResponseEvent;
import com.marcelmika.limsmuc.core.service.ConversationCoreService;
import com.marcelmika.limsmuc.core.service.ConversationCoreServiceUtil;
import com.marcelmika.limsmuc.core.service.PermissionCoreService;
import com.marcelmika.limsmuc.core.service.PermissionCoreServiceUtil;
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

import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
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

    // Dependencies
    private PortletProcessor processor = PortletProcessorUtil.getPortletProcessor();
    private SettingsCoreService settingsCoreService = SettingsCoreServiceUtil.getSettingsCoreService();
    private PermissionCoreService permissionCoreService = PermissionCoreServiceUtil.getPermissionCoreService();
    private ConversationCoreService conversationCoreService = ConversationCoreServiceUtil.getConversationCoreService();
    private PropertiesManager propertiesManager = PropertiesManagerUtil.getPropertiesManager();

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
    private static final String VARIABLE_IS_OVER_LIMIT = "isOverLimit";
    private static final String VARIABLE_CURRENT_USER = "currentUser";
    private static final String VARIABLE_VERSION = "version";
    private static final String VARIABLE_PRELOADED_IMAGES = "preloadedImages";
    private static final String VARIABLE_INSTANCE_KEY = "instanceKey";
    private static final String VARIABLE_PRODUCT_KEY = "productKey";
    private static final String VARIABLE_CUSTOM_LICENSE_ENABLED = "customLicenseEnabled";
    private static final String VARIABLE_PERMISSION_GRANTED = "permissionGranted";

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

        // Site is excluded
        if (isExcluded(renderRequest)) {
            // Disable portlet
            renderRequest.setAttribute(VARIABLE_IS_ENABLED, false);
        }
        // Check if the display of the portlet is permitted
        else if (!isPermitted(renderRequest)) {
            // Disable portlet
            renderRequest.setAttribute(VARIABLE_IS_ENABLED, false);
        }
        // Site is not excluded
        else {

            // Check the availability of browser
            boolean isSupportedBrowser = BrowserDetector.isSupportedBrowser(renderRequest);

            // Render portlet only if the browser is supported
            if (isSupportedBrowser) {

                // Render only if the user is not over the session limit
                if (isOverSessionLimit(renderRequest)) {
                    // Disable portlet
                    renderRequest.setAttribute(VARIABLE_IS_ENABLED, false);
                    // Notify about over limit reason
                    renderRequest.setAttribute(VARIABLE_IS_OVER_LIMIT, true);
                }

                // Render the portlet
                else {
                    // Settings pane
                    renderSettings(renderRequest);
                    // Conversations pane
                    renderConversations(renderRequest);
                }
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
        if (!isSignedIn(request)) {
            // Return unauthorized response code
            response.setProperty(ResourceResponse.HTTP_STATUS_CODE, HttpStatus.UNAUTHORIZED.toString());
            // End here
            return;
        }

        // Do not continue if the user is over session limit
        if (isOverSessionLimit(request)) {
            // Return unauthorized response code
            response.setProperty(ResourceResponse.HTTP_STATUS_CODE, HttpStatus.UNAUTHORIZED.toString());
            // End here
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
            if (log.isErrorEnabled()) {
                log.error(responseEvent.getException());
            }
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
            if (log.isErrorEnabled()) {
                log.error(responseEvent.getException());
            }
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
        renderRequest.setAttribute(VARIABLE_IS_ENABLED, isSignedIn(renderRequest));
        // Check if the browser is supported
        renderRequest.setAttribute(VARIABLE_IS_SUPPORTED_BROWSER, BrowserDetector.isSupportedBrowser(renderRequest));
        // Check if the browser needs support
        renderRequest.setAttribute(VARIABLE_NEEDS_IE_SUPPORT, BrowserDetector.needsInternetExplorerSupport(renderRequest));
        // User values cannot be accessed via javascript so we need to render it manually
        renderRequest.setAttribute(VARIABLE_CURRENT_USER, buddy);
        // Version
        renderRequest.setAttribute(VARIABLE_VERSION, PortletPropertiesValues.VERSION);
        // Preloaded images
        renderRequest.setAttribute(VARIABLE_PRELOADED_IMAGES, preloadedImages(renderRequest));
        // Custom license
        renderRequest.setAttribute(VARIABLE_CUSTOM_LICENSE_ENABLED, License.isCustomLicenseEnabled());

        // Add instance key variable for admin only when the custom license is enabled
        if (PermissionDetector.isAdmin(renderRequest) && License.isCustomLicenseEnabled()) {
            // Get the instance key
            GetInstanceKeyResponseEvent responseEvent = permissionCoreService.getInstanceKey(new GetInstanceKeyRequestEvent(
                    renderRequest.getPortletSession().getPortletContext().getRealPath("/security"))
            );

            if (responseEvent.isSuccess()) {
                // Set the variable
                renderRequest.setAttribute(VARIABLE_INSTANCE_KEY, responseEvent.getInstanceKey());
            } else {
                // Log error
                log.error(responseEvent.getException());
            }

            // Product Key
            renderRequest.setAttribute(VARIABLE_PRODUCT_KEY, Environment.getProductKey());

            // Check if the permission is granted
            GetDisplayPermissionResponseEvent permissionResponse = permissionCoreService.getDisplayPermission(
                    new GetDisplayPermissionRequestEvent(
                            renderRequest.getPortletSession().getPortletContext().getRealPath("/security"))
            );

            // Permission granted
            renderRequest.setAttribute(VARIABLE_PERMISSION_GRANTED, permissionResponse.isSuccess());
        }
    }

    /**
     * Checks if the user is signed in.
     *
     * @param request PortletRequest
     * @return true if the user is signed in
     */
    private boolean isSignedIn(PortletRequest request) {
        // Check if the user is signed in
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        if (themeDisplay == null) {
            if (log.isErrorEnabled()) {
                log.error("Theme display is null");
            }
            return false;
        }

        // Returns true if the user is signed in
        return themeDisplay.isSignedIn();
    }

    /**
     * Returns true if the portlet is allowed to be displayed
     *
     * @return boolean
     */
    private boolean isPermitted(RenderRequest renderRequest) {

        // If the custom license is disabled user is always permitted
        if (!License.isCustomLicenseEnabled()) {
            return true;
        }

        // User that is not signed in is not permitted
        if (!isSignedIn(renderRequest)) {
            return false;
        }

        // Admin is always permitted
        if (PermissionDetector.isAdmin(renderRequest)) {
            return true;
        }

        // Check if the permission is granted
        GetDisplayPermissionResponseEvent response = permissionCoreService.getDisplayPermission(
                new GetDisplayPermissionRequestEvent(
                        renderRequest.getPortletSession().getPortletContext().getRealPath("/security"))
        );

        // Permission is granted
        if (response.getStatus() == GetDisplayPermissionResponseEvent.Status.GRANTED) {
            return true;
        }
        // Permission is not granted
        else if (response.getStatus() == GetDisplayPermissionResponseEvent.Status.NOT_GRANTED) {
            return false;
        }
        // Error occurred
        else if (response.getStatus() == GetDisplayPermissionResponseEvent.Status.ERROR) {
            if (log.isDebugEnabled()) {
                log.debug(response.getException());
            }
            return false;
        }
        // On any other occasion return false
        else {
            return false;
        }
    }

    /**
     * Returns true if the current site is excluded. Control panel is excluded by default
     *
     * @param request PortletRequest
     * @return true if the current site is excluded
     */
    private boolean isExcluded(PortletRequest request) {

        // Check if the user is signed in
        ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);

        // Check for params
        if (themeDisplay == null) {
            if (log.isErrorEnabled()) {
                log.error("Theme display is null");
            }
            return true;
        }

        try {
            // Exclude control panel
            if (themeDisplay.getLayout().getGroup().isControlPanel()) {
                return true;
            }

            // Admin is never excluded
            if (PermissionDetector.isAdmin(request)) {
                return false;
            }

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

    /**
     * Returns true if the user is not allowed the have the session
     *
     * @param renderRequest RenderRequest
     * @return boolean
     */
    private boolean isOverSessionLimit(RenderRequest renderRequest) {
        // If the user is not logged he can't be over the limit either
        if (!isSignedIn(renderRequest)) {
            return false;
        }

        // Get the buddy from request
        Buddy buddy = Buddy.fromRenderRequest(renderRequest);

        // Check the session availability
        ReadSessionLimitResponseEvent responseEvent = settingsCoreService.readSessionLimit(
                new ReadSessionLimitRequestEvent(buddy.getBuddyId())
        );


        return responseEvent.isOverLimit();
    }

    /**
     * Returns true if the user is not allowed the have the session
     *
     * @param resourceRequest ResourceRequest
     * @return boolean
     */
    private boolean isOverSessionLimit(ResourceRequest resourceRequest) {
        // If the user is not logged he can't be over the limit either
        if (!isSignedIn(resourceRequest)) {
            return false;
        }

        // Get the buddy from request
        Buddy buddy = Buddy.fromResourceRequest(resourceRequest);

        // Check the session availability
        ReadSessionLimitResponseEvent responseEvent = settingsCoreService.readSessionLimit(
                new ReadSessionLimitRequestEvent(buddy.getBuddyId())
        );


        return responseEvent.isOverLimit();
    }

    /**
     * Returns a list of image file names
     *
     * @param renderRequest RenderRequest
     * @return list of image file names
     */
    private String[] preloadedImages(RenderRequest renderRequest) {
        // Find the real path
        String path = renderRequest.getPortletSession().getPortletContext().getRealPath("/images");
        // Path wasn't found
        if (path == null) {
            return new String[]{};
        }

        // Get the list of files in the path
        return FileUtil.listFiles(path);
    }
}
