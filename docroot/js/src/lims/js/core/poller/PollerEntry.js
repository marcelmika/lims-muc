/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Poller Entry represents a single entry added to a poller. The polling can be started or stoped.
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.PollerEntry = Y.Base.create('pollerEntry', Y.Base, [], {

    /**
     * Starts polling
     */
    startPolling: function () {
        // Vars
        var interval = this.get('interval'),
            instance = this;

        // Load the model first
        this._loadModel();
        // And then periodically
        this.set('timer', setInterval(function () {
            instance._loadModel();
        }, interval));
    },

    /**
     * Stops polling
     */
    stopPolling: function () {
        // Vars
        var timer = this.get('timer');

        // Stop the timer
        clearTimeout(timer);
    },

    /**
     * Calls the load method on model. If the previous request is still in the queue
     * it does nothing. This is important since we don't want to overwhelm the server
     * with a lot of requests at once and we first want to wait for the previous
     * request to return.
     *
     * @private
     */
    _loadModel: function () {
        // Vars
        var model = this.get('model'),
            connectionMonitor = this.get('connectionMonitor'),
            requestInQueue = this.get('requestInQueue'),
            instance = this;

        // Do nothing if the request is still in the queue.
        if (requestInQueue) {
            return;
        }

        // Set the flag
        this.set('requestInQueue', true);

        // Load model
        model.load(function (err) {

            // Set the flag
            instance.set('requestInQueue', false);

            // If the model is also a connection monitor
            if (connectionMonitor) {
                // Broadcast the state of connection
                if (err) {
                    Y.fire('connectionError');
                } else {
                    Y.fire('connectionOK');
                }
            }
        });
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * An instance of model that will be periodically updated
         *
         * {Y.LIMS.Model}
         */
        model: {
            value: null // to be set
        },

        /**
         * Length of the update period in milliseconds
         *
         * {integer}
         */
        interval: {
            value: null // to be set
        },

        /**
         * True if the entry is also a connection monitor that is responsible
         * for firing a connection error of success during polling.
         *
         * {boolean}
         */
        connectionMonitor: {
            value: false // default value
        },

        /**
         * Timer used to periodically call load method on the model
         *
         * {timer}
         */
        timer: {
            value: null // default value
        },

        /**
         * True if the request is currently being proceed
         *
         * {boolean}
         */
        requestInQueue: {
            value: false // default value
        }
    }

});


