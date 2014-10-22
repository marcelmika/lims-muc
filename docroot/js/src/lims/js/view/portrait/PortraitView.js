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
 * Portrait
 *
 * Represent buddy's portrait
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PortraitView = Y.Base.create('portraitView', Y.View, [Y.LIMS.View.ViewExtension], {

    // Main container
    containerTemplate: '<div class="portrait-container"/>',

    // Container for the user image portrait
    imageTemplate: '<img alt="" class="portrait"/>',

    // Container for the default value
    defaultTemplate: '<div class="default-portrait" />',

    // Initials template
    initialsTemplate: '<dic class="initials"/>',

    /**
     * Renders portrait
     *
     * @returns Y.LIMS.View.PortraitView
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            user = this.get('user'),
            small = this.get('small');

        // No portrait id was set, render default portrait
        if (user && user.get('portraitId') === 0) {
            this._renderDefaultPortrait();
        }
        // Render user portrait
        else {
            this._renderImagePortrait();
        }

        // Check if the portrait view should be small
        if (small) {
            container.addClass('small');
        }

        return this;
    },

    /**
     * Renders default portrait
     *
     * @private
     */
    _renderDefaultPortrait: function () {
        // Vars
        var container = this.get('container'),
            defaultPortrait = this.get('defaultPortrait'),
            initials = this.get('initials'),
            user = this.get('user'),
            initialText = '';

        // Portrait cannot be rendered without user data
        if (user) {

            // If no first name was set take the screen name
            if (user.get('firstName') === '' && user.get('screenName') !== '') {
                initialText = user.get('screenName').charAt(0);
            } else {
                initialText = user.get('fullName').charAt(0);
            }
            // Add last name if set
            if (user.get('lastName') !== '') {
                initialText = initialText + user.get('lastName').charAt(0);
            }

            // Set initials
            initials.set('innerHTML', initialText);

            // Set color
            defaultPortrait.addClass(this._getUserColor());

            // Add it do default portrait
            defaultPortrait.append(initials);

            // Append it to the container
            container.append(defaultPortrait);
        }
    },

    /**
     * Renders user image portrait
     *
     * @private
     */
    _renderImagePortrait: function () {
        // Vars
        var container = this.get('container'),
            imagePortrait = this.get('imagePortrait'),
            user = this.get('user');

        // Portrait cannot be rendered without user data
        if (user) {

            // Set portrait image src attribute based on the screen name
            imagePortrait.set('src', this.getPortraitUrl(user.get('portraitId')));

            // Append it to the container
            container.append(imagePortrait);
        }
    },

    /**
     * Returns a color related to the user
     * @private
     */
    _getUserColor: function () {
        // Vars
        var colors = this.get('colors'),
            user = this.get('user');

        if (user && user.get('buddyId')) {
            return colors[user.get('buddyId') % colors.length];
        } else {
            return '';
        }
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container attached to the view
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        /**
         * Default portrait node
         *
         * {Node}
         */
        defaultPortrait: {
            valueFn: function () {
                return Y.Node.create(this.defaultTemplate);
            }
        },

        /**
         * Image portrait node
         *
         * {Node}
         */
        imagePortrait: {
            valueFn: function () {
                return Y.Node.create(this.imageTemplate);
            }
        },

        /**
         * Initials node
         *
         * {Node}
         */
        initials: {
            valueFn: function () {
                return Y.Node.create(this.initialsTemplate);
            }
        },

        /**
         * Portrait id
         *
         * {Y.LIMS.Model.BuddyModelItem}
         */
        user: {
            value: null
        },

        /**
         * Set to true if the portrait should be small
         *
         * {boolean}
         */
        small: {
            value: false // default value
        },

        /**
         * Contains an array of colors used as a background for the default portrait
         *
         * []
         */
        colors: {
            valueFn: function () {
                return [
                    'red',
                    'green',
                    'blue',
                    'purple',
                    'yellow'
                ];
            }
        }
    }
});
