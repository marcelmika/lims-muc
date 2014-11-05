/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Toggle View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationToggleViewController = Y.Base.create('conversationToggleViewController',
    Y.LIMS.Core.ViewController, [], {

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
            value: "conversation-toggle"
        },

        /**
         * Container node attached to the controller
         *
         * {Node}
         */
        container: {
            value: null // to be set
        }
    }
});
