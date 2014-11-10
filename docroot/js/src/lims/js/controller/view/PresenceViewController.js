/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Presence View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.PresenceViewController = Y.Base.create('presenceViewController', Y.LIMS.Core.ViewController, [], {

    /**
     *  The initializer runs when a Presence View Controller instance is created.
     */
    initializer: function () {
        // This needs to be called in each view controller
        this.setup(this.get('container'), this.get('controllerId'));
    },

    /**
     * Panel Did Load is called when the panel is attached to the controller
     */
    onPanelDidLoad: function () {
        // Events
        this._attachEvents();
    },

    /**
     * Attaches events to DOM elements from container
     *
     * @private
     */
    _attachEvents: function () {
        // Attach click on panel's item
        this.getPanel().get('container').delegate('click', function (event) {
            var presence, target = event.currentTarget;
            // Element was li
            if (target.ancestor('li')) {
                // Fire event
                presence = target.getAttribute('data-status');
                Y.fire('presenceChanged', {
                    presence: presence
                });
            }
        }, 'li');

        // Global events
        Y.on('presenceChanged', this._onPresenceChanged, this);
        Y.on('settingsUpdated', this._onSettingsUpdated, this);
        Y.on('chatDisabled', this._onChatDisabled, this);
    },

    /**
     * Updates presence indicator based on the presence passed in param
     *
     * @param presence
     * @private
     */
    _updatePresenceIndicator: function (presence) {
        // Vars
        var presenceClass = "";

        // Set status indicator based on the presence type
        switch (presence) {
            case "ACTIVE":
                presenceClass = "online";
                break;
            case "AWAY":
                presenceClass = "busy";
                break;
            case "DND":
                presenceClass = "unavailable";
                break;
            case "OFFLINE":
                presenceClass = "off";
                break;
            default:
                presenceClass = "off";
                break;
        }

        // Update status indicator
        this.get('statusIndicator').setAttribute('class', "status-indicator " + presenceClass);
    },

    /**
     * Updates buddy details presence based on the parameter
     *
     * @param presence
     * @private
     */
    _updateBuddyDetails: function (presence) {
        // Vars
        var buddyDetails = this.get('buddyDetails');

        // Set status indicator based on the presence type
        switch (presence) {
            case "ACTIVE":
                buddyDetails.updatePresence('ACTIVE');
                break;
            case "AWAY":
                buddyDetails.updatePresence('AWAY');
                break;
            case "DND":
                buddyDetails.updatePresence('DND');
                break;
            case "OFFLINE":
                buddyDetails.updatePresence('OFFLINE');
                break;
            default:
                buddyDetails.updatePresence('UNRECOGNIZED');
                break;
        }
    },

    /**
     * Called when the settings model is updated
     *
     * @private
     */
    _onSettingsUpdated: function () {
        // Var
        var model = this.get('model');

        // Update presence indicator
        this._updatePresenceIndicator(model.get('presence'));
    },

    /**
     * Presence changed event
     *
     * @param event
     * @private
     */
    _onPresenceChanged: function (event) {
        // Update presence indicator and buddy details
        this._updatePresenceIndicator(event.presence);
        this._updateBuddyDetails(event.presence);

        // Disable chat if needed
        if (event.presence === "OFFLINE") {
            Y.fire("chatDisabled");
        } else {
            Y.fire("chatEnabled");
        }

        // Dismiss controller
        this.dismissViewController();
    },

    /**
     * Called when the chat is disabled
     * @private
     */
    _onChatDisabled: function () {
        // Since if we call chatDisabled all controllers will be automatically
        // hidden. Thus we need to show our controller again.
        this.showViewController();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Id of the controller
         *
         * {string}
         */
        controllerId: {
            value: "presence"
        },

        /**
         * Container node attached to the controller
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Model attached to controller
         *
         * {Y.LIMS.Model.SettingsModel}
         */
        model: {
            value: null // to be set
        },


        /**
         * Container for the status indicator
         *
         * {Node}
         */
        statusIndicator: {
            valueFn: function () {
                return this.get('container').one('.status-indicator');
            }
        },

        /**
         * Currently logged user
         *
         * {Y.LIMS.ModelBuddyModelItem}
         */
        buddyDetails: {
            value: null // to be set
        }
    }
});
