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

    // Error message template
    errorMessageTemplate: '<div class="error-message" />',

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
     * Returns input value
     */
    getValue: function () {
        // Vars
        var input = this.get('input');

        return input.get('value');
    },

    /**
     * Validates the input. Returns true if the input value is valid.
     *
     * @return {boolean}
     */
    validate: function () {
        return this._validate(this.getValue());
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
     * Shows error message
     *
     * @param message
     * @private
     */
    showError: function (message) {
        // Vars
        var errorMessage = this.get('errorMessage'),
            input = this.get('input'),
            container = this.get('container');

        // Add error class to input
        input.addClass('error');

        // Update error message
        errorMessage.set('innerHTML', message);

        // Add to container if not presented
        if (!errorMessage.inDoc()) {
            container.append(errorMessage);
        }
    },

    /**
     * Hides error message
     *
     * @private
     */
    hideError: function () {
        // Vars
        var errorMessage = this.get('errorMessage'),
            input = this.get('input');

        // Remove error class
        input.removeClass('error');

        // Empty the message
        errorMessage.set('innerHTML', '');

        // Remove from doc
        if (errorMessage.inDoc()) {
            errorMessage.remove();
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
     * Validates the input. Shows the proper error message. Returns true if the validation was successful.
     *
     * @param value validated value
     * @return {boolean} true if the validation was successful
     * @private
     */
    _validate: function (value) {
        // Vars
        var notEmpty = this.get('notEmpty'),
            isInteger = this.get('isInteger'),
            minValue = this.get('minValue'),
            maxValue = this.get('maxValue');

        // Hide previous error message
        this.hideError();

        // Validate not empty
        if (notEmpty && value.length === 0) {
            // Show error message
            this.showError(Y.LIMS.Core.i18n.values.validationRequired);
            // Failure
            return false;
        }

        // Validate is number
        if (isInteger && !Y.LIMS.Core.Util.isInteger(value)) {
            // Show error message
            this.showError(Y.LIMS.Core.i18n.values.validationIsInteger);
            // Failure
            return false;
        }

        // Validate min and max value
        if ((minValue !== null || maxValue !== null) && (value < minValue || value > maxValue)) {
            // Show error message
            this.showError(Y.LIMS.Core.i18n.values.validationInterval + " " + minValue + " - " + maxValue);
            // Failure
            return false;
        }

        // Success
        return true;
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

            // Validate the value
            if (instance._validate(event.newVal)) {
                // Fire the event
                instance.fire('inputUpdate', {
                    preValue: event.prevVal,
                    postValue: event.newVal
                }, instance);
            }

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
         * Error message template
         *
         * {Node}
         */
        errorMessage: {
            valueFn: function () {
                return Y.Node.create(this.errorMessageTemplate);
            }
        },

        /**
         * Indicates if the input should be integer
         *
         * {boolean}
         */
        isInteger: {
            value: false // default value
        },

        /**
         * Indicates min possible value of input (only works if isInteger is set to true)
         *
         * {number}
         */
        minValue: {
            value: null // default value
        },

        /**
         * Indicates max possible value of input (only works if isInteger is set to false)
         *
         * {number}
         */
        maxValue: {
            value: null // default value
        },

        /**
         * Indicates if the input cannot be empty
         *
         * {boolean}
         */
        notEmpty: {
            value: false // default value
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
         * Delayed interval. Updated event is fired after the delay.
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