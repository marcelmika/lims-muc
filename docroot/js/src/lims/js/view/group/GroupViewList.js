/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group View List
 *
 * The class extends Y.View. It represent a view for a list of groups
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewList = Y.Base.create('groupViewList', Y.View, [Y.LIMS.View.ViewExtension], {

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.GroupModelList,

    /**
     * The initializer runs when the instance is created, and gives
     * us an opportunity to set up the view.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Attaches listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        var model = this.get('model'),
            errorView = this.get('errorView'),
            container = this.get('container');

        // Local events
        model.after('add', this._onGroupAdd, this);
        model.on('groupReset', this._onGroupReset, this);
        model.after('groupsReadSuccess', this._onGroupsReadSuccess, this);
        model.after('groupsReadError', this._onGroupsReadError, this);
        errorView.on('resendButtonClick', this._onResendButtonClick, this);
        container.on('mousewheel', this._onContainerMouseWheel, this);
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
        if (!groupList.inDoc()) {

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
        if (groupList.inDoc()) {

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

        // If there was any error, hide it
        errorView.hideErrorMessage(true);

        // No groups found
        if (model.isEmpty()) {
            // Show info message
            infoView.showInfoMessage(true);
            // Hide groups
            this._hideGroups();
        } else {
            // Show groups
            this._showGroups();
            // Hide info message
            infoView.hideInfoMessage(true);
        }

        // Hide indicator
        activityIndicator.hide();
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
        activityIndicator.hide();
        // Hide groups
        this._hideGroups();
        // Hide info about empty groups
        infoView.hideInfoMessage(true);
        // Show error
        errorView.showErrorMessage(true);
    },

    /**
     * Called whenever the group model is reset
     *
     * @private
     */
    _onGroupReset: function () {
        // Vars
        var groupList = this.get('groupList');

        // Empty node
        groupList.set('innerHTML', '');
    },

    /**
     * Creates a new GroupView instance and renders it into the list whenever a
     * Group item is added to the list.
     *
     * @param e event
     * @private
     */
    _onGroupAdd: function (e) {
        // Vars
        var groupView = new Y.LIMS.View.GroupViewItem({model: e.model}),
            groupList = this.get('groupList');

        // Render group
        groupView.render();
        // Add it to group list
        groupList.append(groupView.get('container'));
    },

    /**
     * Called when the user click on the resend button within the error message view
     *
     * @private
     */
    _onResendButtonClick: function () {
        // Vars
        var model = this.get('model');
        // Try to load the model data again
        model.load();
    },

    /**
     * Called when the user scrolls with mouse wheel over the container
     *
     * @param event
     * @return {*}
     * @private
     */
    _onContainerMouseWheel: function (event) {
        // FIXME: This is s hack that prevents issue #37 to happen
        if (this.name === "groupViewList") {
            // Vars
            var container = this.get('container');
            // Prevent scrolling of the whole window
            return this.preventScroll(event, container);
        }
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
         * {Y.LIMS.Model.GroupModelList}
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
                // Remove it from the DOM, since it will be shown after
                // the list is loaded from the server
                groupList.remove();

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
