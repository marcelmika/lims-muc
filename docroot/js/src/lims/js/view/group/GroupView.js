/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group View
 *
 * The class extends Y.View. It represents the view for a single group item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupView = Y.Base.create('groupView', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="group-item"/>',

    // Template for read more button
    loadMoreButtonTemplate: '<div class="load-more" />',

    // Template for the activity indicator
    activityIndicator: '<div class="preloader read-more-preloader" />',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.GroupModel,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#limsmuc-group-item-template').get('innerHTML'),

    /**
     * The initializer runs when the instance is created, and gives
     * us an opportunity to set up the view.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Renders the view
     *
     * @returns {Y.LIMS.View.GroupView}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            socialRelation = model.get('socialRelation'),
            name;

        // If the group contains social relation we need to localize
        // the social relation name.
        if (socialRelation) {
            name = socialRelation.getLocalizedName();
        }
        // If not, use the name from model
        else {
            name = model.get('name');
        }

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                name: name,
                title: name
            })
        );

        // Add group icon
        this._renderGroupIcon();

        // Render counter
        this._renderGroupCounter();

        // Hide group name if nothing was set. This is used in ALL list strategy.
        if (!model.get('name')) {
            Y.LIMS.Core.Util.hide(container.one('.group-name'));
        }

        // Render buddy list
        this._renderBuddyList();

        // Remember the height
        this.set('clientHeight', container.get('clientHeight'));

        // Show load more button if needed
        this._showLoadMoreButton();

        // Attach events to rendered container
        container.one('.group-name').on('click', this._onGroupNameClick, this);

        return this;
    },

    /**
     * Adds group icon to container group
     *
     * @private
     */
    _renderGroupIcon: function () {
        // Vars
        var container = this.get('container'),
            listStrategy = this.get('model').get('listStrategy'),
            listGroup = this.get('model').get('listGroup'),
            groupIcon = container.one('.group-icon');

        // Groups
        if (listStrategy === 'GROUPS') {

            // Sites
            if (listGroup === 'SITE') {
                groupIcon.addClass('group-sites');
                groupIcon.set('title', Y.LIMS.Core.i18n.values.groupIconSites);
            }
            // Social
            else if (listGroup === 'SOCIAL') {
                groupIcon.addClass('group-social');
                groupIcon.set('title', Y.LIMS.Core.i18n.values.groupIconSocial);
            }
            // User
            else if (listGroup === 'USER') {
                groupIcon.addClass('group-user');
                groupIcon.set('title', Y.LIMS.Core.i18n.values.groupIconUser);
            }
        }

        // Jabber
        else if (listStrategy === 'JABBER') {
            groupIcon.addClass('group-jabber');
            groupIcon.set('title', Y.LIMS.Core.i18n.values.groupIconJabber);
        }
    },

    /**
     * Renders group counter
     *
     * @private
     */
    _renderGroupCounter: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            counter;

        // Get the counter
        counter = container.one('.group-counter');

        if (counter) {
            counter.set('innerHTML',
                model.get('buddies').size() + '/' + model.get('page').get('totalElements')
            );
        }
    },

    /**
     * Renders buddy list
     *
     * @private
     */
    _renderBuddyList: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            buddiesView;

        // Create view
        buddiesView = new Y.LIMS.View.GroupBuddyListView({
            model: model.get('buddies')
        });

        // Render
        buddiesView.render();

        // Add to container
        container.append(buddiesView.get("container"));

        // Remember the view
        this.set('buddyListView', buddiesView);
    },

    /**
     * Attaches listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model'),
            loadMoreButton = this.get('loadMoreButton');

        // Local events
        model.on('beforeLoad', this._onBeforeGroupRead, this);
        model.after('load', this._onGroupReadSuccess, this);
        model.after('error', this._onGroupReadError, this);
        loadMoreButton.on('click', this._onLoadMoreButtonClick, this);
    },

    /**
     * Shows activity indicator
     *
     * @private
     */
    _showActivityIndicator: function () {
        // Vars
        var indicator = this.get('activityIndicator'),
            container = this.get('container');

        if (!indicator.inDoc()) {
            container.append(indicator);
        }
    },

    /**
     * Hide activity indicator
     *
     * @private
     */
    _hideActivityIndicator: function () {
        // Vars
        var indicator = this.get('activityIndicator');

        if (indicator.inDoc()) {
            indicator.remove();
        }
    },

    /**
     * Shows the load more button
     *
     * @private
     */
    _showLoadMoreButton: function () {
        // Vars
        var loadMoreButton = this.get('loadMoreButton'),
            model = this.get('model'),
            container = this.get('container');

        if (!loadMoreButton.inDoc()) {

            // Show only if the load more button is needed
            if (!model.hasReachedBottom() && model.get('listStrategy') !== 'ALL') {
                container.append(loadMoreButton);
            }
        }
    },

    /**
     * Hides the load more button
     *
     * @private
     */
    _hideLoadMoreButton: function () {
        // Vars
        var loadMoreButton = this.get('loadMoreButton');

        if (loadMoreButton.inDoc()) {
            loadMoreButton.remove();
        }
    },

    /**
     * Called before the model is loaded
     *
     * @private
     */
    _onBeforeGroupRead: function () {
        // Hide button
        this._hideLoadMoreButton();
        // Show activity indicator
        this._showActivityIndicator();
    },

    /**
     * Called when the group is successfully read
     *
     * @private
     */
    _onGroupReadSuccess: function () {
        // Render the view
        this.render();
    },

    /**
     * Called when the group is not read
     * @private
     */
    _onGroupReadError: function () {
        // TODO: Decide what to do
        console.log('error');
    },

    /**
     * Called when user clicks on load more button
     *
     * @private
     */
    _onLoadMoreButtonClick: function () {
        // Vars
        var model = this.get('model');

        // Load the model
        model.load({
            readMore: true
        });
    },

    /**
     * Called when the user clicks on the group name title
     *
     * @private
     */
    _onGroupNameClick: function () {
        // Vars
        var buddyListView = this.get('buddyListView'),
            instance = this;

        // Closed
        if (!buddyListView.get('closed')) {
            instance._hideLoadMoreButton();
        }

        // Open/Close the buddy list view
        buddyListView.toggle(function () {

            // Opened
            if (!buddyListView.get('closed')) {
                instance._showLoadMoreButton();
            }
        });
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {
        // Override the default container attribute.

        /**
         * Container node
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        /**
         * Model
         *
         * {Y.LIMS.Model.GroupModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * List of all group buddy views
         *
         * [Y.LIMS.View.GroupBuddyListView]
         */
        buddyListView: {
            value: null // default value
        },

        /**
         * Read more activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return Y.Node.create(this.activityIndicator);
            }
        },

        /**
         * Read More Button node
         *
         * {Node}
         */
        loadMoreButton: {
            valueFn: function () {
                // Vars
                var node = Y.Node.create(this.loadMoreButtonTemplate);

                // Set button content
                node.set('innerHTML', Y.LIMS.Core.i18n.values.groupLoadMore);

                return node;
            }
        }
    }
});

