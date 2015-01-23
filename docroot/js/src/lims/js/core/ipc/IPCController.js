/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Inter Portlet Communication Controller
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.IPCController = Y.Base.create('IPCController', Y.Base, [], {

    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Attach events to elements
     */
    _attachEvents: function () {
        // Vars
        var publisher = this.get('publisher');

        publisher.on('lims:openConversation', this._onOpenConversation);
    },

    /**
     * Called on lims:openConversation IPC event
     *
     * @param event
     * @private
     */
    _onOpenConversation: function (event) {
        console.log('Hit!', event);

        var buddy = new Y.LIMS.Model.BuddyModelItem({
            buddyId: 10546,
            screenName: 'dlc2'
        });

        Y.fire('buddySelected', {
            buddy: buddy
        });
    }

}, {
    // Object attributes
    ATTRS: {

        /**
         * Publisher is an object on which all the 'fire' and 'on' events are called.
         */
        publisher: {
            value: null // to be set
        }
    }
});
