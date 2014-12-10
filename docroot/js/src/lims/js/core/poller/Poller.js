/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Poller holds all instances of poller entries
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Poller = Y.Base.create('poller', Y.Base, [], {


    /**
     * Register poller entry to the poller and start polling
     *
     * @param entryId
     * @param pollerEntry Y.LIMS.Core.PollerEntry
     */
    register: function (entryId, pollerEntry) {
        // Vars
        var entries = this.get('entries');

        // Stop the previous entry if there was any
        if (entries.hasOwnProperty(entryId)) {
            entries[entryId].stopPolling();
        }

        // Add the new one
        entries[entryId] = pollerEntry;

        // Start the polling
        pollerEntry.startPolling();
    },

    /**
     * Unregister poller entry of the poller and stop polling
     *
     * @param entryId
     */
    unregister: function (entryId) {
        // Vars
        var entries = this.get('entries');

        if (entries.hasOwnProperty(entryId)) {
            // Stop polling
            entries[entryId].stopPolling();
            // Remove from the entries array
            delete entries[entryId];
        }
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Object that holds poller entries
         */
        entries: {
            value: {}
        }
    }

});

