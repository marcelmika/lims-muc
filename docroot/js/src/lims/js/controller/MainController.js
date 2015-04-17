/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Main Controller
 *
 * This controller creates instances of all controllers in the app and injects objects that are necessary for them.
 * It also holds instances of objects that are needed across the app.
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.MainController = Y.Base.create('mainController', Y.Base, [Y.LIMS.Controller.ControllerExtension], {

    /**
     * The initializer runs when a MainController instance is created, and gives
     * us an opportunity to set up all sub controllers
     */
    initializer: function () {
        var buddyDetails = this.get('buddyDetails'),
            settingsModel = this.get('settingsModel'),
            notification = this.get('notification'),
            properties = this.get('properties'),
            serverTime = this.get('serverTimeModel'),
            poller = this.get('poller'),
            publisher = this.get('publisher'),
            rootNode = this.getRootNode();

        // Attach events
        this._attachEvents();

        // Apply patches
        this._applyPatches();

        // Load the most fresh server time to count server time offset
        serverTime.load(function (err) {

            // Update to the optimal offset that we get from the server.
            // If there is an error properties contain offset read from the
            // html as a fallback.
            if (!err) {
                properties.set('offset', new Date().getTime() - serverTime.get('time'));
            }

            // Group
            new Y.LIMS.Controller.GroupViewController({
                container: rootNode.one('.buddy-list'),
                properties: properties,
                poller: poller
            });

            // Presence
            new Y.LIMS.Controller.PresenceViewController({
                container: rootNode.one('.status-panel'),
                model: settingsModel,
                buddyDetails: buddyDetails
            });

            // Settings
            new Y.LIMS.Controller.SettingsViewController({
                container: rootNode.one('.chat-settings'),
                model: settingsModel,
                properties: properties,
                poller: poller
            });

            // Conversation
            new Y.LIMS.Controller.ConversationsController({
                container: rootNode.one('.lims-tabs'),
                buddyDetails: buddyDetails,
                settings: settingsModel,
                notification: notification,
                properties: properties,
                poller: poller
            });

            // Conversation Feed
            new Y.LIMS.Controller.ConversationFeedViewController({
                container: rootNode.one('.conversation-feed'),
                properties: properties,
                poller: poller,
                buddyDetails: buddyDetails
            });

            // IPC Controller
            new Y.LIMS.Core.IPCController({
                publisher: publisher,
                buddyDetails: buddyDetails
            });

            // Render tooltips
            var tooltip = new Y.LIMS.View.Tooltip();
            tooltip.render();
        });
    },

    /**
     * This is called whenever the user session expires
     */
    sessionExpired: function () {
        // Fire an event so the other controllers know about the expiration
        Y.fire('userSessionExpired');
    },

    /**
     * Attach local functions to events
     *
     * @private
     */
    _attachEvents: function () {
        // Global events
        Y.on('initializationFinished', this._onInitializationFinished, this);
        // Panel events
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
        Y.on('userSessionExpired', this._onSessionExpired, this);
    },

    /**
     * Applies patches
     *
     * @private
     */
    _applyPatches: function () {
        // Vars
        var mobilePatch = this.get('mobilePatch');

        // Don't let the user to zoom on mobile devices
        mobilePatch.disableZoom();
        // Apply mobile device detection
        mobilePatch.detectMobileDevice();
    },

    /**
     * Shows notification about the expired session to the user
     *
     * @private
     */
    _showSessionExpiredNotification: function () {
        // Vars
        var portletNotification = this.get('portletNotification');
        // Render the notification
        portletNotification.render();
        // Show the notification
        portletNotification.show();
    },

    /**
     * Called when the initialization is finished
     *
     * @private
     */
    _onInitializationFinished: function () {
        // We can now show the portlet
        this.showPortlet();
    },

    /**
     * Called when any panel is shown
     *
     * @param panel
     * @private
     */
    _onPanelShown: function (panel) {
        var panelId = panel.get('panelId');
        // Store current active panel id
        this.set('activePanelId', panelId);
        // Update settings
        this.get('settingsModel').updateActivePanel(panelId);
    },

    /**
     * Called when any panel is hidden
     *
     * @param panel
     * @private
     */
    _onPanelHidden: function (panel) {
        // If the hidden panel is currently active panel it means that no panel is currently active
        if (this.get('activePanelId') === panel.get('panelId')) {
            // Update settings
            this.get('settingsModel').updateActivePanel(null);
        }
    },

    /**
     * Called when the user session expires
     *
     * @private
     */
    _onSessionExpired: function () {
        // Hide the whole portlet
        this.hidePortlet();
        // Show the notification
        this._showSessionExpiredNotification();
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Buddy details related of the currently logged user
         *
         * {Y.LIMS.Model.BuddyModelItem}
         */
        buddyDetails: {
            valueFn: function () {
                // We need settings to determine user
                var properties = new Y.LIMS.Core.Properties();
                // Get logged user
                return new Y.LIMS.Model.BuddyModelItem({
                    buddyId: properties.getCurrentUserId(),
                    companyId: properties.getCurrentUserCompanyId(),
                    male: properties.getCurrentUserMale(),
                    portraitId: properties.getCurrentUserPortraitId(),
                    portraitImageToken: properties.getCurrentUserPortraitImageToken(),
                    portraitToken: properties.getCurrentUserPortraitToken(),
                    screenName: properties.getCurrentUserScreenName(),
                    fullName: properties.getCurrentUserFullName(),
                    firstName: properties.getCurrentUserFirstName(),
                    middleName: properties.getCurrentUserMiddleName(),
                    lastName: properties.getCurrentUserLastName()
                });
            }
        },

        /**
         * Settings of the currently logged user
         *
         * {Y.LIMS.Model.SettingsModel}
         */
        settingsModel: {
            valueFn: function () {
                return new Y.LIMS.Model.SettingsModel({
                    buddy: this.get('buddyDetails')
                });
            }
        },

        /**
         * Current server time
         *
         * {Y.LIMS.Model.ServerTimeModel}
         */
        serverTimeModel: {
            valueFn: function () {
                return new Y.LIMS.Model.ServerTimeModel();
            }
        },

        /**
         * Notification object responsible for the incoming message notification
         *
         * {Y.LIMS.Core.Notification}
         */
        notification: {
            valueFn: function () {
                return new Y.LIMS.Core.Notification({
                    settings: this.get('settingsModel'),
                    container: this.getRootNode().one('.lims-sound'),
                    properties: this.get('properties')
                });
            }
        },

        /**
         * Portlet notification
         *
         * {Y.LIMS.View.PortletNotificationView}
         */
        portletNotification: {
            valueFn: function () {
                // Vars
                var container = this.getPortletContainer();

                return new Y.LIMS.View.PortletNotificationView({
                    content: Y.LIMS.Core.i18n.values.sessionExpiredNotificationContent,
                    container: container
                });
            }
        },

        /**
         * An instance of poller that periodically refreshes models that are subscribed
         *
         * {Y.LIMS.Core.Poller}
         */
        poller: {
            valueFn: function () {
                return new Y.LIMS.Core.Poller();
            }
        },

        /**
         * Properties object that holds the global portlet properties
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            valueFn: function () {
                return new Y.LIMS.Core.Properties();
            }
        },

        /**
         * Publisher object user to send IPC calls
         */
        publisher: {
            value: null // to be set
        },

        /**
         * ID of the current active panel
         *
         * {string}
         */
        activePanelId: {
            value: null // default value
        },

        /**
         * Set of mobile patches
         *
         * {Y.LIMS.Core.MobilePatch}
         */
        mobilePatch: {
            valueFn: function () {
                return new Y.LIMS.Core.MobilePatch();
            }
        }
    }
});
