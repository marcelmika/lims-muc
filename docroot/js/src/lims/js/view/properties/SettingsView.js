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
        var soundSwitch = this.get('soundSwitch');

        // Local events
        soundSwitch.on('switchClick', this._onSoundSwitchClick, this);
    },

    /**
     * Binds settings from rendered HTML
     *
     * @private
     */
    _bindSettings: function () {
        // Vars
        var model = this.get('model'),
            soundSwitch = this.get('soundSwitch');

        // Set settings
        model.set('isMute', !soundSwitch.isOn());
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
        }
    }

});
