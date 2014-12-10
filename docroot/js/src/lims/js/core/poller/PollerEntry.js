/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Poller Entry represents a single entry added to a poller. The polling can be started or stopped.
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.PollerEntry = Y.Base.create('pollerEntry', Y.Base, [], {

    /**
     * Called after initialization of the object
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Starts polling
     */
    startPolling: function () {
        // Vars
        var interval = this.get('interval'),
            timer = this.get('timer'),
            instance = this;

        // Timer wasn't set yet
        if (timer === null) {
            // Load the model first
            this._loadModel();
        }
        // Timer was set already, so first clear it before
        // you set a new one
        else {
            clearTimeout(timer);
        }

        // Create timer
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
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model');

        model.on('slowDown', this._onSlowDown, this);
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
    },

    /**
     * Called when the slow down event occurs
     *
     * @param event
     * @private
     */
    _onSlowDown: function (event) {
        // Vars
        var slowDown = event.slowDown,
            interval = this.get('interval'),
            minInterval = this.get('minInterval'),
            maxInterval = this.get('maxInterval');

        // We should slow down and we are not yet at the max
        if (slowDown === true && interval < maxInterval) {
            // Increase time interval by one second
            this.set('interval', (interval + 1000));
            // Restart the polling
            this.startPolling();
        }

        // We should slow down and we are not yet at the min
        if (slowDown === false && interval > minInterval) {
            // Decrease time interval by one second
            this.set('interval', (interval - 1000));
            // Restart the polling
            this.startPolling();
        }
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
            value: 10000 // default value (10 seconds)
        },

        /**
         * Maximal possible interval
         *
         * {number}
         */
        maxInterval: {
            value: 30000 // default value (30 seconds)
        },

        /**
         * Minimal possible interval
         *
         * {number}
         */
        minInterval: {
            value: 10000 // default value (10 seconds)
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


