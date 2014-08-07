/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.MainController = Y.Base.create('mainController', Y.Base, [], {

    // The initializer runs when a MainController instance is created, and gives
    // us an opportunity to set up all sub controller
    initializer: function () {
        var buddyDetails = this.get('buddyDetails'),
            settingsModel = this.get('settingsModel'),
            notification = this.get('notification');
        // Attach events
        this._attachEvents();

        // Group
        new Y.LIMS.Controller.GroupViewController();
        // Presence
        new Y.LIMS.Controller.PresenceViewController({
            buddyDetails: buddyDetails
        });
        // Settings
        new Y.LIMS.Controller.SettingsViewController({
            model: settingsModel
        });
        // Conversation
        new Y.LIMS.Controller.ConversationsController({
            buddyDetails: buddyDetails,
            settings: settingsModel,
            notification: notification
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
        // Panel events
        Y.on('panelShown', this._onPanelShown, this);
        Y.on('panelHidden', this._onPanelHidden, this);
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
        this.get('settingsModel').updateActivePanel(panelId, function() {});
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
            this.get('settingsModel').updateActivePanel('', function() {});
        }
    }

}, {

    ATTRS: {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.
        buddyDetails: {
            valueFn: function () {
                // We need settings to determine user
                var settings = new Y.LIMS.Core.Settings();
                // Get logged user
                return new Y.LIMS.Model.BuddyModelItem({
                    buddyId: settings.getCurrentUserId(),
                    screenName: settings.getCurrentUserScreenName(),
                    fullName: settings.getCurrentUserFullName()
                });
            }
        },

        // Settings related to the logged user
        settingsModel: {
            valueFn: function () {
                return new Y.LIMS.Model.SettingsModel({
                    buddy: this.get('buddyDetails')
                });
            }
        },

        // Notification
        notification: {
            valueFn: function () {
                return new Y.LIMS.Core.Notification({
                    settings: this.get('settingsModel')
                });
            }
        },

        // Current active panel
        activePanelId: {
            value: null // default value
        }
    }
});
