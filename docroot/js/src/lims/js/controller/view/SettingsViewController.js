/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Settings View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.SettingsViewController = Y.Base.create('settingsViewController', Y.LIMS.Core.ViewController, [], {

    /**
     *  The initializer runs when a Group View Controller instance is created.
     */
    initializer: function () {
        // This needs to be called in each view controller
        this.setup(this.get('container'), this.get('controllerId'));
    },

    /**
     * Panel Did Load is called when the panel is attached to the controller
     */
    onPanelDidLoad: function () {
        // Reads current settings from rendered settings view
        this._bindSettings();
        // Events
        this._attachEvents();
    },

    /**
     * Attaches events to DOM elements from container
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var soundCheckbox = this.get('soundCheckbox');

        // Local events
        soundCheckbox.on('click', this._onSoundCheckboxUpdated, this);
    },

    /**
     * Reads settings from rendered dom
     *
     * @private
     */
    _bindSettings: function () {
        // Vars
        var model = this.get('model'),
            isMute = this.get('soundCheckbox').get('checked') ? false : true;

        // Set to model
        model.set('isMute', isMute);
    },

    /**
     * Sound checkbox changed
     *
     * @private
     */
    _onSoundCheckboxUpdated: function () {
        var model = this.get('model'),
            isMute = this.get('soundCheckbox').get('checked') ? false : true;
        // Update model
        model.set('isMute', isMute).save();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Id of the controller
        controllerId: {
            value: "settings"
        },

        // Container Node
        container: {
            value: null // to be set
        },

        // Y.LIMS.Model.SettingsModel
        model: {
            value: null // to be set
        },

        // Sound checkbox node
        soundCheckbox: {
            valueFn: function () {
                return this.get('container').one("#playSound");
            }
        }
    }
});
