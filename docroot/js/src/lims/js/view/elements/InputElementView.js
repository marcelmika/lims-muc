/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Input Element View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.InputElementView = Y.Base.create('inputElementView', Y.View, [Y.LIMS.View.ViewExtension], {

    // Activity indicator template
    activityIndicatorTemplate: '<div class="preloader"/>',

    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Sets the input value
     *
     * @param value
     */
    setValue: function (value) {
        // Vars
        var input = this.get('input');

        input.set('value', value);
    },

    /**
     * Enables the input
     */
    enable: function () {
        // Vars
        var input = this.get('input');

        // Enable the input
        input.set('disabled', null);
    },

    /**
     * Disables the input
     */
    disable: function () {
        // Vars
        var input = this.get('input');

        // Disable the checkbox
        input.set('disabled', true);
    },

    /**
     * Sets focus to input
     */
    focus: function () {
        // Vars
        var input = this.get('input');

        input.focus();
    },

    /**
     * Takes the focus away from input
     */
    blur: function () {
        // Vars
        var input = this.get('input');

        // Take the focus away
        input.blur();
    },

    /**
     * Show input activity indicator
     */
    showActivityIndicator: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator'),
            container = this.get('container');

        // Add activity indicator to container
        if (!activityIndicator.inDoc()) {
            container.append(activityIndicator);
        }
    },

    /**
     * Hides input activity indicator
     */
    hideActivityIndicator: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator');

        // Remove activity indicator from doc
        if (activityIndicator.inDoc()) {
            activityIndicator.remove();
        }
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var input = this.get('input');

        input.on('valuechange', this._onInputValueChanged, this);
    },

    /**
     * Called when the input changes its value
     *
     * @param event
     * @private
     */
    _onInputValueChanged: function (event) {
        // Vars
        var timer = this.get('timer'),
            timerDelayInterval = this.get('timerDelayInterval'),
            instance = this;

        // Clear out the timer first. Since we don't want to send many requests to
        // the server whenever the value changes we just simply wait for a short interval
        clearTimeout(timer);

        // Set a new timer
        this.set('timer', setTimeout(function () {
            // Fire the event
            instance.fire('inputUpdate', {
                preValue: event.prevVal,
                postValue: event.newVal
            }, instance);

        }, timerDelayInterval));
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Container attached to the view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Input node
         *
         * {Node}
         */
        input: {
            valueFn: function () {
                // Vars
                return this.get('container').one('input[type=text]');
            }
        },

        /**
         * Activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return Y.Node.create(this.activityIndicatorTemplate);
            }
        },

        /**
         * Timer used for the delayed event fire
         *
         * {timer}
         */
        timer: {
            value: null // to be set
        },

        /**
         * Delayed interval. Slider updated event is fired after the delay.
         * This is useful because we don't want to overwhelm the server with many
         * request.
         *
         * {integer}
         */
        timerDelayInterval: {
            value: 1500 // one and a half second
        }
    }
});