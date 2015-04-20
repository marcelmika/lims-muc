/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Settings
 *
 * Contains all necessary settings related to the current user session
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Properties = Y.Base.create('properties', Y.Base, [], {

    pathImage: null,   // This is set in main.js, to access it use Y.LIMS.Core.Properties.pathImage
    isIE: false,       // This is set in main.js, to access it use Y.LIMS.Core.Properties.isIE
    resourceURL: null,  // This is set in main.js, to access it use Y.LIMS.Core.Properties.resourceURL

    /**
     * Values are parsed from HTML in main.js.
     * Check the properties.jspf for the list of all possible properties.
     *
     * {}
     */
    values: null,

    /**
     * Called when the object is created
     */
    initializer: function () {
        // Attach to events
        this._attachEvents();
    },

    /**
     * Returns userId of the currently connected user
     *
     * @returns {number}
     */
    getCurrentUserId: function () {
        return this.get('userId');
    },

    /**
     * Returns companyId of the currently connected user
     *
     * @return {number}
     */
    getCurrentUserCompanyId: function () {
        return this.get('companyId');
    },

    /**
     * Returns true if the current user is male
     *
     * @return {boolean}
     */
    getCurrentUserMale: function () {
        return this.get('male');
    },

    /**
     * Returns portraitId of the currently connected user
     *
     * @return {number}
     */
    getCurrentUserPortraitId: function () {
        return this.get('portraitId');
    },

    /**
     * Returns portrait image token of the currently connected user
     *
     * @return {string}
     */
    getCurrentUserPortraitImageToken: function () {
        return this.get('portraitImageToken');
    },

    /**
     * Returns portrait token of the currently connected user
     *
     * @return {string}
     */
    getCurrentUserPortraitToken: function () {
        return this.get('portraitToken');
    },

    /**
     * Returns screenName of the currently connected user
     *
     * @returns {string}
     */
    getCurrentUserScreenName: function () {
        return this.get('screenName');
    },

    /**
     * Returns fullName of the currently connected user
     *
     * @returns {string}
     */
    getCurrentUserFullName: function () {
        return this.get('fullName');
    },

    /**
     * Returns a first name of the currently connected user
     *
     * @return {string}
     */
    getCurrentUserFirstName: function () {
        return this.get('firstName');
    },

    /**
     * Returns a middle name of the currently connected user
     *
     * @return {string}
     */
    getCurrentUserMiddleName: function () {
        return this.get('middleName');
    },

    /**
     * Returns a last name of the currently connected user
     *
     * @return {string}
     */
    getCurrentUserLastName: function () {
        return this.get('lastName');
    },

    /**
     * Returns an id of the current active panel
     *
     * @return {string}
     */
    getActivePanelId: function () {
        return this.get('activePanelId');
    },

    /**
     * Returns server time offset compared to client time
     *
     * @returns {timestamp}
     */
    getServerTimeOffset: function () {
        return this.get('offset');
    },

    /**
     * Returns portlet's context path
     *
     * @return {string}
     */
    getContextPath: function () {
        return this.get('contextPath');
    },

    /**
     * Returns true if the whole chat is enabled
     *
     * @returns {boolean}
     */
    isChatEnabled: function () {
        return this.get('isChatEnabled');
    },

    /**
     * Returns true if the mobile user scalable is disabled
     *
     * @return {boolean}
     */
    isMobileUserScalableDisabled: function () {
        return this.get('mobileUserScalableDisabled');
    },

    /**
     * Attach dom events
     *
     * @private
     */
    _attachEvents: function () {
        // Chat enabled/disabled
        Y.on('chatEnabled', this._onChatEnabled, this);
        Y.on('chatDisabled', this._onChatDisabled, this);
    },

    /**
     * Called when the chat is enabled (turned on)
     *
     * @private
     */
    _onChatEnabled: function () {
        this.set('isChatEnabled', true);
    },

    /**
     * Called when the chat is disabled (turned off)
     *
     * @private
     */
    _onChatDisabled: function () {
        this.set('isChatEnabled', false);
    }

}, {

    ATTRS: {

        /**
         * User Id of the currently logged user
         *
         * {integer}
         */
        userId: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.userId;
            }
        },

        /**
         * Company Id of the currently logged user
         *
         * {integer}
         */
        companyId: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.companyId;
            }
        },

        /**
         * Set to true if the user is male
         *
         * {boolean}
         */
        male: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.male;
            }
        },

        /**
         * Portrait id of the currently logged user
         *
         * {integer}
         */
        portraitId: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portraitId;
            }
        },

        /**
         * Portrait image token of the currently logged user
         *
         * {string}
         */
        portraitImageToken: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portraitImageToken;
            }
        },

        /**
         * Portrait token of the currently logged user
         *
         * {string}
         */
        portraitToken: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portraitToken;
            }
        },

        /**
         * Screen name of the currently logged user
         *
         * {string}
         */
        screenName: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.screenName;
            }
        },


        /**
         * Full name of the currently logged user
         *
         * {string}
         */
        fullName: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.fullName;
            }
        },

        /**
         * First name of the currently logged user
         *
         * {string}
         */
        firstName: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.firstName;
            }
        },


        /**
         * Middle name of the currently logged user
         *
         * {string}
         */
        middleName: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.middleName;
            }
        },

        /**
         * Last name of the currently logged user
         *
         * {string}
         */
        lastName: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.lastName;
            }
        },

        /**
         * Currently active panel id
         *
         * {string}
         */
        activePanelId: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.activePanelId;
            }
        },

        /**
         * Holds current server time
         *
         * {number}
         */
        serverTime: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.currentServerTime;
            }
        },

        /**
         * Holds delta between current server time and client time
         *
         * {number}
         */
        offset: {
            valueFn: function () {
                // Get server time
                var currentChatServerTime = this.get('serverTime');
                // Count offset
                return new Date().getTime() - currentChatServerTime;
            }
        },

        /**
         * Path to images
         *
         * {string}
         */
        pathImage: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.pathImage;
            }
        },

        /**
         * Portlet context path
         *
         * {string}
         */
        contextPath: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.contextPath;
            }
        },

        /**
         * Set to true if chat is enabled
         *
         * {boolean}
         */
        isChatEnabled: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.portletEnabled;
            }
        },

        /**
         * Set to true if mobile user scalable disabled property is enabled
         *
         * {boolean}
         */
        mobileUserScalableDisabled: {
            valueFn: function () {
                return Y.LIMS.Core.Properties.values.mobileUserScalableDisabled;
            }
        }
    }
});
