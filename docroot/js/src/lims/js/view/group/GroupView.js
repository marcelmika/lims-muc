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
            loadMoreButton = this.get('loadMoreButton'),
            buddiesView,
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


        // Render Group:
        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                name: name
            })
        );

        // Hide group name if nothing was set
        if (!model.get('name')) {
            Y.LIMS.Core.Util.hide(container.one('.group-name'));
        }

        // Render Buddies
        buddiesView = new Y.LIMS.View.GroupBuddyListView({model: model.get('buddies')});
        buddiesView.render();
        container.append(buddiesView.get("container"));

        // Read more button
        if (!model.hasReachedBottom()) {
            container.append(loadMoreButton);
        }

        return this;
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
        model.after('load', this._onGroupReadSuccess, this);
        model.after('error', this._onGroupReadError, this);
        loadMoreButton.on('click', this._onLoadMoreButtonClick, this);
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
         * Read More Button node
         *
         * {Node}
         */
        loadMoreButton: {
            valueFn: function () {
                // Vars
                var node = Y.Node.create(this.loadMoreButtonTemplate);

                // TODO: i18n
                // Set button content
                node.set('innerHTML', "Load More");

                return node;
            }
        }
    }
});

