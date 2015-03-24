/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group List View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupListView = Y.Base.create('groupListView', Y.View, [Y.LIMS.View.ViewExtension], {

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.GroupListModel,

    // Template for the activity indicator
    readMoreActivityIndicator: '<div class="preloader read-more-preloader" />',

    /**
     * The initializer runs when the instance is created, and gives
     * us an opportunity to set up the view.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Renders group list
     *
     * @private
     */
    render: function () {
        // Vars
        var model = this.get('model'),
            groupList = this.get('groupList');

        if (groupList) {

            // Empty node
            groupList.set('innerHTML', '');

            // Create views for groups
            Y.Array.each(model.toArray(), function (groupModel) {

                // Create new group view
                var groupView = new Y.LIMS.View.GroupView({model: groupModel});
                // Render group
                groupView.render();
                // Add it to group list
                groupList.append(groupView.get('container'));
            });
        }
    },

    /**
     * Attaches listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model'),
            errorView = this.get('errorView'),
            container = this.get('container');

        // Local events
        model.after('load', this._onGroupsReadSuccess, this);
        model.after('error', this._onGroupsReadError, this);
        errorView.on('resendButtonClick', this._onResendButtonClick, this);
        container.after('mouseenter', this._onContainerContentMouseEnter, this);
        container.before('mouseleave', this._onContainerContentMouseLeave, this);
        container.on('scroll', this._onContentScroll, this);
    },

    /**
     * Call this method when the view should attach mouse wheel event
     */
    _attachMouseWheel: function () {
        // The previous attachment needs to be cleared first
        this._detachMouseWheel();
        // Subscribe to the mouse wheel event
        this.set('mouseWheelSubscription', Y.on('mousewheel', this._onContainerMouseWheel, this));
    },

    /**
     * Call this method when the view should detach mouse wheel event
     */
    _detachMouseWheel: function () {
        // Vars
        var mouseWheelSubscription = this.get('mouseWheelSubscription');

        if (mouseWheelSubscription) {
            // Detach subscription
            mouseWheelSubscription.detach();
        }
    },

    /**
     * Shows group list
     *
     * @private
     */
    _showGroups: function () {
        // Vars
        var groupList = this.get('groupList'),
            container = this.get('container'),
            animation;

        // If the group list is already in the document don't animate it
        if (groupList && !groupList.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: groupList,
                duration: 0.5,
                from: {opacity: 0},
                to: {opacity: 1}
            });

            // Opacity needs to be set to zero otherwise there will
            // be a weird blink effect
            groupList.setStyle('opacity', 0);

            // Add group list to the container
            container.append(groupList);

            // Run the effect animation
            animation.run();
        }
    },

    /**
     * Hides group list
     *
     * @private
     */
    _hideGroups: function () {
        // Vars
        var groupList = this.get('groupList'),
            animation;

        // Run the animation only if the group list is in DOM
        if (groupList && groupList.inDoc()) {

            // Create the animation instance
            animation = new Y.Anim({
                node: groupList,
                duration: 0.5,
                from: {opacity: 1},
                to: {opacity: 0}
            });

            // Listen to the end of the animation
            animation.on('end', function () {
                animation.get('node').remove();
            });

            // Run!
            animation.run();
        }
    },

    /**
     * Shows read more activity indicator
     *
     * @private
     */
    _showReadMoreActivityIndicator: function () {
        // Vars
        var indicator = this.get('readMoreActivityIndicator'),
            container = this.get('container');

        if (!indicator.inDoc()) {
            container.append(indicator);
        }
    },

    /**
     * Hide read more activity indicator
     *
     * @private
     */
    _hideReadMoreActivityIndicator: function () {
        // Vars
        var indicator = this.get('readMoreActivityIndicator');

        if (indicator.inDoc()) {
            indicator.remove();
        }
    },

    /**
     * Called when the groups model is read
     *
     * @private
     */
    _onGroupsReadSuccess: function () {

        // Vars
        var activityIndicator = this.get('activityIndicator'),
            errorView = this.get('errorView'),
            infoView = this.get('infoView'),
            model = this.get('model');

        // Render
        this.render();

        // If there was any error, hide it
        errorView.hideErrorMessage(true);

        // Model is still being loaded
        if (model.get('loading')) {
            // Hide indicator
            Y.LIMS.Core.Util.show(activityIndicator);
        }
        // No groups found
        else if (model.isEmpty()) {
            // Show info message
            infoView.showInfoMessage(true);
            // Hide groups
            this._hideGroups();
            // Hide indicator
            Y.LIMS.Core.Util.hide(activityIndicator);
        } else {
            // Show groups
            this._showGroups();
            // Hide info message
            infoView.hideInfoMessage(true);
            // Hide indicator
            Y.LIMS.Core.Util.hide(activityIndicator);
        }
    },

    /**
     * Called when there groups wasn't loaded due to an error
     *
     * @private
     */
    _onGroupsReadError: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator'),
            infoView = this.get('infoView'),
            errorView = this.get('errorView');

        // Hide indicator
        Y.LIMS.Core.Util.hide(activityIndicator);
        // Hide groups
        this._hideGroups();
        // Hide info about empty groups
        infoView.hideInfoMessage(true);
        // Show error
        errorView.showErrorMessage(true);
    },

    /**
     * Called when the user click on the resend button within the error message view
     *
     * @private
     */
    _onResendButtonClick: function () {
        // Vars
        var model = this.get('model');

        // Load the model data again
        model.load();
    },

    /**
     * Called when the user enters container content with the mouse cursor
     *
     * @private
     */
    _onContainerContentMouseEnter: function () {
        // Attach the mouse wheel event since we need to track
        // users scrolling
        this._attachMouseWheel();
    },

    /**
     * Called when the user leaves container content with the mouse cursor
     *
     * @private
     */
    _onContainerContentMouseLeave: function () {
        // Detach the mouse wheel event since there is no need to track
        // scrolling since the cursor is not above the panel content
        this._detachMouseWheel();
    },

    /**
     * Called when user scrolls with the content
     *
     * @private
     */
    _onContentScroll: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            readMoreScrollOffset = this.get('readMoreScrollOffset'),
            group,
            instance = this;

        // Auto loading is only available when the list
        // strategy is set to ALL
        if (model.get('listStrategy') === 'ALL') {

            // If the user scrolled to bottom and if we aren't already reading more
            if (this.hasScrolledToBottom(container, readMoreScrollOffset) && !this.get('isReadingMore')) {

                // Get the 'all' group
                group = model.item(0);

                // If the group exists and it's not yet reached the bottom
                if (group && !group.hasReachedBottom()) {
                    // Set the flag so we are not going to read multiple times
                    this.set('isReadingMore', true);
                    // Show the activity indicator
                    this._showReadMoreActivityIndicator();

                    // Load more
                    group.load({readMore: true}, function () {
                        // We are not reading anymore
                        instance.set('isReadingMore', false);
                        // Hide the preloader
                        instance._hideReadMoreActivityIndicator();
                    });
                }
            }
        }
    },

    /**
     * Called when the user scrolls with mouse wheel over the container
     *
     * @param event
     * @return {*}
     * @private
     */
    _onContainerMouseWheel: function (event) {
        // Vars
        var container = this.get('container');

        // Prevent scrolling of the whole window
        return this.preventScroll(event, container);
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Group view container node
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Group list model
         *
         * {Y.LIMS.Model.GroupListModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * Group list node
         *
         * {Node}
         */
        groupList: {
            valueFn: function () {
                // Vars
                var groupList = this.get('container').one('.group-list');

                if (groupList) {
                    // Remove it from the DOM, since it will be shown after
                    // the list is loaded from the server
                    groupList.remove();
                }

                return groupList;
            }
        },

        /**
         * Activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            value: null // to be set
        },

        /**
         * Event subscription for the mouse wheel event
         *
         * {Subscription}
         */
        mouseWheelSubscription: {
            value: null
        },

        /**
         * Read more activity indicator node
         *
         * {Node}
         */
        readMoreActivityIndicator: {
            valueFn: function () {
                return Y.Node.create(this.readMoreActivityIndicator);
            }
        },

        /**
         * Set to true if the user scrolled to bottom and more
         * group buddies are being loaded from server
         *
         * {boolean}
         */
        isReadingMore: {
            value: false
        },

        /**
         * Offset from the bottom when the
         * read more loading starts
         *
         * {number}
         */
        readMoreScrollOffset: {
            value: 50
        },

        /**
         * Error view with error message and resend button
         *
         * {Y.LIMS.View.ErrorNotificationView}
         */
        errorView: {
            valueFn: function () {
                // Vars
                var container = this.get('container');
                // Create view
                return new Y.LIMS.View.ErrorNotificationView({
                    container: container,
                    errorMessage: Y.LIMS.Core.i18n.values.groupListErrorMessage
                });
            }
        },

        /**
         * Info view with info message
         *
         * {Y.LIMS.View.InfoNotificationView}
         */
        infoView: {
            valueFn: function () {
                // Vars
                var container = this.get('container');
                // Create view
                return new Y.LIMS.View.InfoNotificationView({
                    container: container,
                    infoMessage: Y.LIMS.Core.i18n.values.groupListEmptyInfoMessage
                });
            }
        }
    }
});
