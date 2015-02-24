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
    },

    /**
     * Returns printable name of the buddy
     *
     * @return {string}
     */
    printableName: function () {
        // Vars
        var fullName = this.get('fullName'),
            screenName = this.get('screenName');

        // Full name
        if (fullName && fullName.length > 0) {
            return fullName;
        }
        // If the full name is not set return the screen name
        if (screenName && screenName.length > 0) {
            return screenName;
        }

        // Otherwise return default
        return Y.LIMS.Core.i18n.values.unknownUserPlaceholder;
    },

    /**
     * Returns printable screen name of the buddy
     *
     * @return {string}
     */
    printableScreenName: function () {
        // Vars
        var screenName = this.get('screenName');

        // Return printable screen name
        if (screenName && screenName.length > 0) {
            return '(' + screenName + ')';
        }

        // User doesn't have a screen name
        return '';
    },

    /**
     * Returns printable user initials
     *
     * @return {string}
     */
    printableInitials: function () {
        // Vars
        var fullName = this.get('fullName'),
            firstName = this.get('firstName'),
            lastName = this.get('lastName'),
            screenName = this.get('screenName'),
            initials = '?'; // Default value

        // First initial
        //
        // If no first name was set take the screen name
        if (firstName === '' && screenName !== '') {
            initials = Y.LIMS.Core.Util.firstCharacter(screenName);
        }
        // If no first name of screen name was set take full name
        else {
            initials = Y.LIMS.Core.Util.firstCharacter(fullName);
        }

        // Second initial
        //
        // Add last name if set
        if (lastName !== '') {
            initials = initials.concat(Y.LIMS.Core.Util.firstCharacter(lastName));
        }

        // If no initials were composed, set the default
        if (initials === '') {
            initials = '?';
        }

        return initials;
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
            value: null, // default value

            // Setter
            setter: function (val) {
                // Just to be sure that the value will be an integer
                return Y.LIMS.Core.Util.toInteger(val);
            },

            // Validate value
            validator: function (val) {
                return val === null || Y.LIMS.Core.Util.isInteger(val);
            }
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
         * Set to true if the buddy is male
         *
         * {boolean|null}
         */
        male: {
            value: null // default value
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
         * Portrait token of the buddy
         *
         * {string|null}
         */
        portraitToken: {
            value: null // default value
        },

        /**
         * Portrait image token of the buddy
         *
         * {string | null}
         */
        portraitImageToken: {
            value: null // default value
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
