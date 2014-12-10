/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Buddy Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single Buddy.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddyModelItem = Y.Base.create('buddyModelItem', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Updates buddy presence
     *
     * @param presence
     * @param callback
     * @private
     */
    updatePresence: function (presence, callback) {
        // Vars
        var content;

        // Update locally
        this.set('presence', presence);

        // Deserialize
        content = Y.JSON.stringify(this.toJSON());

        // Do the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "UpdateBuddyPresence",
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
                        callback("Cannot update buddy presence", o.responseText);
                    }
                }
            }
        });
    }

}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        /**
         * Id of the buddy
         *
         * {integer|null}
         */
        buddyId: {
            value: null // default value
        },

        /**
         * Id of the company where the user belongs
         *
         * {integer|null}
         */
        companyId: {
            value: null // default value
        },

        /**
         * Screen name of the buddy
         *
         * {string}
         */
        screenName: {
            value: "" // default value
        },

        /**
         * Portrait id of the buddy
         *
         * {integer}
         */
        portraitId: {
            value: 0 // default value
        },

        /**
         * Full name of the buddy
         *
         * {string}
         */
        fullName: {
            value: "" // default value
        },

        /**
         * First name of the buddy
         *
         * {string}
         */
        firstName: {
            value: "" // default value
        },

        /**
         * Middle name of the buddy
         *
         * {string}
         */
        middleName: {
            value: "" // default value
        },

        /**
         * Last name of the buddy
         *
         * {string}
         */
        lastName: {
            value: "" // default value
        },

        /**
         * Presence of the buddy
         *
         * {string}
         */
        presence: {
            value: null // default value
        },

        /**
         * True if the user is connected
         *
         * {boolean}
         */
        connected: {
            value: false // default value
        }
    }
});
