/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
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
    initialsTemplate: '<div class="initials"/>',

    /**
     * Renders portrait
     *
     * @returns Y.LIMS.View.PortraitView
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            users = this.get('users'),
            small = this.get('small');

        // Do not render the view if no users were set
        if (!users) {
            return this;
        }

        // If there is exactly one user to be rendered
        if (users.length === 1) {
            this._renderSingleUser(users[0]);
        }
        // Multiple users to be rendered
        else if (users.length > 1) {
            this._renderMultipleUsers(users);
        }

        // Check if the portrait view should be small
        if (small) {
            container.addClass('small');
        }

        return this;
    },

    /**
     * Renders single user
     *
     * @param user
     * @private
     */
    _renderSingleUser: function (user) {
        // Vars
        var container = this.get('container'),
            portrait;

        // No portrait id was set, render default portrait
        if (user && user.get('portraitId') === 0) {
            portrait = this._renderDefaultPortrait(user);
        }
        // Render user portrait
        else {
            portrait = this._renderImagePortrait(user);
        }

        // Append rendered portrait to container
        container.append(portrait);

        return portrait;
    },

    /**
     * Renders multiple users
     *
     * @param users
     * @private
     */
    _renderMultipleUsers: function (users) {
        // Vars
        var container = this.get('container'),
            portrait,
            user,
            index,
            shiftIndex = 0,
            shift = 3;

        // We have more than 3 users, thus we will have 3 layered portrait view
        if (users.length >= 3) {
            index = 2;
        }
        // There are 2 users, thus we will have 2 layered portrait view
        else if (users.length === 2) {
            index = 1;
        }
        // Multiple users portrait view must have more than 2 users
        else {
            return null;
        }

        // Create multi layer portrait view
        for (index; index >= 0; index--) {

            // Get the user from array
            user = users[index];

            // No portrait id was set, render default portrait
            if (user && user.get('portraitId') === 0) {
                portrait = this._renderDefaultPortrait(user);
            }
            // Render user portrait
            else {
                portrait = this._renderImagePortrait(user);
            }

            // Shift views
            portrait.setStyle('top', shift * shiftIndex);
            portrait.setStyle('left', shift * shiftIndex);

            // Move the shift index up
            shiftIndex++;

            // Append rendered portrait to container
            container.append(portrait);
        }

        // Add multiple users class to container
        container.addClass('multiple');
    },

    /**
     * Renders default portrait
     *
     * @private
     */
    _renderDefaultPortrait: function (user) {
        // Vars
        var defaultPortrait = Y.Node.create(this.defaultTemplate),
            initials = Y.Node.create(this.initialsTemplate),
            initialText = '';

        // If no first name was set take the screen name
        if (user.get('firstName') === '' && user.get('screenName') !== '') {
            initialText = Y.LIMS.Core.Util.firstCharacter(user.get('screenName'));
        }
        // If no first name of screen name was set take full name
        else {
            initialText = Y.LIMS.Core.Util.firstCharacter(user.get('fullName'));
        }
        // Add last name if set
        if (user.get('lastName') !== '') {
            initialText = initialText.concat(Y.LIMS.Core.Util.firstCharacter(user.get('lastName')));
        }

        // Set initials
        initials.set('innerHTML', initialText);

        // Set color
        defaultPortrait.addClass(this._getUserColor(user));

        // Add it do default portrait
        defaultPortrait.append(initials);

        return defaultPortrait;
    },

    /**
     * Renders user image portrait
     *
     * @private
     */
    _renderImagePortrait: function (user) {
        // Vars
        var imagePortrait = Y.Node.create(this.imageTemplate);

        // Set portrait image src attribute based on the screen name
        imagePortrait.set('src', this.getPortraitUrl(user));

        return imagePortrait;
    },

    /**
     * Returns a color related to the user
     * @private
     */
    _getUserColor: function (user) {
        // Vars
        var colors = this.get('colors');

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
         * A list of users that should be rendered
         *
         * [Y.LIMS.Model.BuddyModelItem]
         */
        users: {
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
                    'yellow',
                    'orange'
                ];
            }
        }
    }
});
