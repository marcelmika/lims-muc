/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Switch Element View
 *
 * Represents a view that holds switch button
 */
Y.namespace('LIMS.View');

Y.LIMS.View.SwitchElementView = Y.Base.create('switchElementView', Y.View, [Y.LIMS.View.ViewExtension], {


    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Returns true if the switch is on
     *
     * {boolean}
     */
    isOn: function () {
        // Vars
        var checkbox = this.get('checkbox');

        return checkbox.get('checked') ? true : false;
    },

    /**
     * Turns the switch on
     */
    turnOn: function () {
        // Vars
        var checkbox = this.get('checkbox');

        // Check the checkbox
        checkbox.set('checked', 'checked');
    },

    /**
     * Turns the switch off
     */
    turnOff: function () {
        // Vars
        var checkbox = this.get('checkbox');

        // Un-check the checkbox
        checkbox.set('checked', null);
    },

    /**
     * Toggles the switch
     */
    toggle: function () {
        if (this.isOn()) {
            this.turnOff();
        } else {
            this.turnOn();
        }
    },

    /**
     * Enables the switch
     */
    enable: function () {
        // Vars
        var checkbox = this.get('checkbox');

        // Enable the checkbox
        checkbox.set('disabled', null);
    },

    /**
     * Disables the switch
     */
    disable: function () {
        // Vars
        var checkbox = this.get('checkbox');

        // Disable the checkbox
        checkbox.set('disabled', true);
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var checkbox = this.get('checkbox');

        checkbox.on('click', this._onCheckboxClick, this);
    },

    /**
     * Called when the user clicks on the checkbox
     *
     * @private
     */
    _onCheckboxClick: function () {
        // Fire the event
        this.fire('switchClick');
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
         * Checkbox node
         *
         * {Node}
         */
        checkbox: {
            valueFn: function () {
                // Vars
                var checkbox = this.get('container').one('input[type=checkbox]');

                // Remove switch classes from the checkbox if the portlet
                // is in the IE support mode since the fancy switch doesn't
                // work in older browsers
                if (this.hasIESupport()) {
                    checkbox.ancestor().removeClass('switch');
                }

                return checkbox;
            }
        }
    }
});