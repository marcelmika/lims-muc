/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */


/**
 * Properties Model
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.PropertiesModel = Y.Base.create('propertiesModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Tests connection with the jabber server
     *
     * @param callback
     */
    testConnection: function (callback) {

        // Vars
        var url = this.getServerRequestUrl();

        // First, save the properties
        this.save(function (err) {

            // The properties cannot be saved
            if (err) {
                callback(err);
                // End here
                return;
            }

            // Do the test request
            Y.io(url, {
                method: "POST",
                data: {
                    query: "TestConnection"
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

                        callback(o.responseText);
                    }
                }
            });
        });
    },

    /**
     * Custom sync layer
     *
     * @param action  [create|read|update|delete]
     * @param options extra parameters
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var content;

        switch (action) {

            case 'create':
            case 'update': // Intentional fall through

                // Deserialize
                content = Y.JSON.stringify(this.toJSON());

                // Do the request
                Y.io(this.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "PatchProperties",
                        content: content
                    },
                    on: {
                        success: function () {

                            if (callback) {
                                callback(null);
                            }
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            if (callback) {
                                callback('Cannot update property');
                            }
                        }
                    }
                });

                break;

            case 'read':
            case 'delete':
                break;

            default:
                if (callback) {
                    callback('Invalid action');
                }
                break;
        }
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Buddy list strategy
         *
         * {string}
         */
        buddyListStrategy: {
            value: null // to be set
        },

        /**
         * Buddy list social relations
         *
         * []
         */
        buddyListSocialRelations: {
            value: null // to be set
        },

        /**
         * Buddy list ignore default user
         *
         * {boolean}
         */
        buddyListIgnoreDefaultUser: {
            value: null // to be set
        },

        /**
         * Buddy list ignore deactivated
         *
         * {boolean}
         */
        buddyListIgnoreDeactivatedUser: {
            value: null // to be set
        },

        /**
         * Buddy list max buddies
         *
         * {Number}
         */
        buddyListMaxBuddies: {
            value: null // to be set
        },

        /**
         * Buddy list max search
         *
         * {Number}
         */
        buddyListMaxSearch: {
            value: null // to be set
        },

        /**
         * Conversation list max messages
         *
         * {Number}
         */
        conversationListMaxMessages: {
            value: null // to be set
        },

        /**
         * Conversation feed max conversations
         *
         * {Number}
         */
        conversationFeedMaxConversations: {
            value: null // to be set
        },

        /**
         * Excluded sites
         *
         * []
         */
        excludedSites: {
            value: null // to be set
        },

        /**
         * Buddy list site excludes
         *
         * []
         */
        buddyListSiteExcludes: {
            value: null // to be set
        },

        /**
         * Buddy list group excludes
         *
         * []
         */
        buddyListGroupExcludes: {
            value: null // to be set
        },

        /**
         * Jabber enabled
         *
         * {boolean}
         */
        jabberEnabled: {
            value: null // to be set
        },

        /**
         * Jabber security enabled
         *
         * {boolean}
         */
        jabberSecurityEnabled: {
            value: null // to be set
        },

        /**
         * Jabber import user enabled
         *
         * {boolean}
         */
        jabberImportUserEnabled: {
            value: null // to be set
        },

        /**
         * Jabber host
         *
         * {string}
         */
        jabberHost: {
            value: null // to be set
        },

        /**
         * Jabber port
         *
         * {integer}
         */
        jabberPort: {
            value: null // to be set
        },

        /**
         * Jabber service name
         *
         * {string}
         */
        jabberServiceName: {
            value: null // to be set
        },

        /**
         * Jabber resource
         *
         * {string}
         */
        jabberResource: {
            value: null // to be set
        }
    }
});
