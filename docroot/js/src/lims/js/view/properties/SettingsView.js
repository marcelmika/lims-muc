/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Admin Properties View
 *
 * Handles properties settings
 */
Y.namespace('LIMS.View');

Y.LIMS.View.SettingsView = Y.Base.create('settingsView', Y.View, [], {

    /**
     * Called before initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
        // Bind settings
        this._bindSettings();
    },

    /**
     * Attach event to elements
     * @private
     */
    _attachEvents: function () {
        // Vars
        var soundSwitch = this.get('soundSwitch'),
            notificationsSwitch = this.get('notificationsSwitch');

        // Local events
        soundSwitch.on('switchClick', this._onSoundSwitchClick, this);
        notificationsSwitch.on('switchClick', this._onNotificationsSwitchClick, this);
        // Global events
        Y.on('settingsUpdated', this._onSettingsUpdated, this);
    },

    /**
     * Binds settings from rendered HTML
     *
     * @private
     */
    _bindSettings: function () {
        // Vars
        var model = this.get('model'),
            soundSwitch = this.get('soundSwitch'),
            notificationsSwitch = this.get('notificationsSwitch');

        // Set settings
        model.set('isMute', !soundSwitch.isOn());
        model.set('notificationsEnabled', notificationsSwitch.isOn());
    },

    /**
     * Called when the user click on the switch
     *
     * @private
     */
    _onSoundSwitchClick: function () {
        var soundSwitch = this.get('soundSwitch'),
            model = this.get('model');

        // Update model
        model.set('isMute', !soundSwitch.isOn());

        // Disable view
        soundSwitch.disable();

        // Save model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                soundSwitch.toggle();
            }
            // Re-enable the view so the user can interact with it again
            soundSwitch.enable();
        });
    },

    /**
     * Called when the user clicks on the notifications switch
     *
     * @private
     */
    _onNotificationsSwitchClick: function () {
        // Vars
        var notificationsSwitch = this.get('notificationsSwitch'),
            model = this.get('model');


        // Let's check if the browser supports notifications
        if ("Notification" in window) {
            if (notificationsSwitch.isOn()) {
                // Let's check whether notification permissions have already been granted
                if (window.Notification.permission !== "granted") {
                    window.Notification.requestPermission();
                }
            }
        }

        // Update model
        model.set('notificationsEnabled', notificationsSwitch.isOn());

        // Disable view
        notificationsSwitch.disable();

        // Save model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                notificationsSwitch.toggle();
            }
            // Re-enable the view so the user can interact with it again
            notificationsSwitch.enable();
        });
    },

    /**
     * Called when the settings model is updated
     *
     * @private
     */
    _onSettingsUpdated: function () {
        // Vars
        var model = this.get('model'),
            soundSwitch = this.get('soundSwitch'),
            notificationsSwitch = this.get('notificationsSwitch');

        // Mute
        if (model.get('isMute')) {
            soundSwitch.turnOff();
        } else {
            soundSwitch.turnOn();
        }

        // Notifications
        if (model.get('notificationsEnabled')) {
            notificationsSwitch.turnOn();
        } else {
            notificationsSwitch.turnOff();
        }
    }

}, {

    ATTRS: {

        /**
         * Container attached to view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Model attache to view
         *
         * {Y.LIMS.Model.SettingsModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * View for sound switch
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        soundSwitch: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.play-sound');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for notifications switch
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        notificationsSwitch: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.notifications-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        }
    }

});
