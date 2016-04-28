/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Settings Model
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.SettingsModel = Y.Base.create('settingsModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Returns true if the user decided that he doesn't want to hear
     * sound notifications
     *
     * @returns {boolean}
     */
    isMute: function () {
        return this.get('isMute');
    },

    /**
     * Returns true if the browser notifications are enabled
     *
     * @return {boolean}
     */
    notificationsEnabled: function () {
        return this.get('notificationsEnabled');
    },

    /**
     * Updates active panel id
     *
     * @param activePanelId
     * @param callback
     */
    updateActivePanel: function (activePanelId, callback) {
        // Vars
        var content;

        // Save locally
        this.set('activePanelId', activePanelId);

        // Serialize
        content = Y.JSON.stringify(this.toJSON());

        // Do the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateActivePanel",
                content: content
            },
            on: {
                success: function (id, o) {
                    if (callback) {
                        callback(null, o.responseText);
                    }
                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }

                    if (callback) {
                        callback("Cannot update active panel", o.responseText);
                    }
                }
            }
        });
    },

    // Custom sync layer.
    sync: function (action, options, callback) {
        // Vars
        var content,
            response,
            instance = this;

        // Serialize
        content = Y.JSON.stringify(this.toJSON());

        switch (action) {
            case 'create':
            case 'update': // There is no difference between create and update

                // Do the request
                Y.io(this.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "UpdateSettings",
                        content: content
                    },
                    on: {
                        success: function () {
                            callback(null);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }
                            callback("Cannot update settings");
                        }
                    }
                });
                break;

            case 'read':

                // Do the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "ReadSettings"
                    },
                    on: {
                        success: function (id, o) {

                            // Check if the poller should slow down
                            if (o.getResponseHeader('X-Slow-Down')) {
                                instance.fire('slowDown', {slowDown: true});
                            } else {
                                instance.fire('slowDown', {slowDown: false});
                            }

                            // Deserialize
                            try {
                                // Deserialize response
                                response = Y.JSON.parse(o.responseText);
                            }
                            catch (exception) {
                                // JSON.parse throws a SyntaxError when passed invalid JSON
                                callback(exception);
                                // End here
                                return;
                            }

                            // Check if the chat enabled property was changed
                            if (response.chatEnabled !== instance.get('chatEnabled')) {
                                if (response.chatEnabled === true) {
                                    Y.fire("chatEnabled");
                                } else {
                                    Y.fire("chatDisabled");
                                }
                            }

                            // Check if the jabber disconnected property was changed
                            if (response.jabberDisconnected !== instance.get('jabberDisconnected')) {
                                if (response.jabberDisconnected === true) {
                                    Y.fire("jabberDisconnected");
                                } else {
                                    Y.fire("jabberConnected");
                                }
                            }

                            // Callback
                            callback(null, response);

                            // Fire event
                            Y.fire('settingsUpdated', {
                                settings: instance
                            });
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }
                            callback("Cannot update connection");
                        }
                    }
                });
                break;


            case 'delete':
                break;

            default:
                callback('Invalid action');
        }
    }

}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        /**
         * Buddy object related to the settings
         *
         * {Y.LIMS.Model.BuddyModelItem}
         */
        buddy: {
            value: null
        },

        /**
         * ID of the active panel
         *
         * {string}
         */
        activePanelId: {
            value: null // default value
        },

        /**
         * True if the chat sounds are switched off
         *
         * {boolean}
         */
        isMute: {
            value: false // default value
        },

        /**
         * True if the browser notifications are turned on
         */
        notificationsEnabled: {
            value: false // default value
        },

        /**
         * True if the admin area is opened
         *
         * {boolean}
         */
        isAdminAreaOpened: {
            value: false // default value
        },

        /**
         * Presence
         *
         * {ACTIVE|AWAY|DND|OFFLINE}
         */
        presence: {
            value: "OFFLINE"
        },

        /**
         * True if the jabber connection is disconnected
         *
         * {boolean}
         */
        jabberDisconnected: {
            value: null // to be set
        }
    }
});
