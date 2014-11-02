/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Settings View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SettingsViewController = Y.Base.create('settingsViewController',
    Y.LIMS.Core.ViewController, [Y.LIMS.Controller.ControllerExtension], {

        /**
         *  The initializer runs when a Group View Controller instance is created.
         */
        initializer: function () {
            // This needs to be called in each view controller
            this.setup(this.get('container'), this.get('controllerId'));
        },

        /**
         * Panel Did Load is called when the panel is attached to the controller
         */
        onPanelDidLoad: function () {
            // Attach Events
            this._attachEvents();
            // Bind settings
            this._bindSettings();
        },

        /**
         * Attaches events to DOM elements from container
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var adminProperties = this.get('adminProperties');

            // Local events
            if (adminProperties) {
                adminProperties.on('propertiesOpened', this._onAdminPropertiesOpened, this);
                adminProperties.on('propertiesClosed', this._onAdminPropertiesClosed, this);
            }
        },

        /**
         * Binds settings from rendered HTML
         *
         * @private
         */
        _bindSettings: function () {
            // Vars
            var model = this.get('model'),
                adminProperties = this.get('adminProperties');

            // Set settings
            if (adminProperties) {
                model.set('isAdminAreaOpened', adminProperties.isOpened());
            }
        },

        /**
         * Called when the admin properties view is opened
         *
         * @private
         */
        _onAdminPropertiesOpened: function () {
            // Vars
            var model = this.get('model');

            // Set the model value
            model.set('isAdminAreaOpened', true);
            // And save it
            model.save();
        },

        /**
         * Called when the admin properties view is closed
         *
         * @private
         */
        _onAdminPropertiesClosed: function () {
            // Vars
            var model = this.get('model');

            // Set the model value
            model.set('isAdminAreaOpened', false);
            // And save it
            model.save();
        }

    }, {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        ATTRS: {

            /**
             * Id of the controller
             *
             * {string}
             */
            controllerId: {
                value: "settings"
            },

            /**
             * Container attached to controller
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
             * Holds a view related to the admin properties. If the user
             * is not an admin null is returned.
             *
             * {Y.LIMS.View.PropertiesView|null}
             */
            adminProperties: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container').one('.admin-area');

                    if (!container) {
                        return null;
                    }

                    return new Y.LIMS.View.PropertiesView({
                        container: container
                    });
                }
            },

            /**
             * Holds a view related to the user settings
             *
             * {Y.LIMS.View.SettingsView}
             */
            userSettings: {
                valueFn: function () {
                    // Vars
                    var container = this.get('container').one('.user-settings'),
                        model = this.get('model');

                    return new Y.LIMS.View.SettingsView({
                        container: container,
                        model: model
                    });
                }
            }
        }
    });
