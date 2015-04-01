/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Buddy View List
 *
 * The class extends Y.View. It represent a view for a list of buddies within a single group
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupBuddyListView = Y.Base.create('groupBuddyListView', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<ul class="group-buddy-list"/>',

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.BuddyModelList,

    /**
     * Renders view
     *
     * @returns {Y.LIMS.View.GroupBuddyListView}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            views = [];

        Y.Array.each(model.toArray(), function (buddy) {
            // Vars
            var buddyView;

            // Build view from buddy
            buddyView = new Y.LIMS.View.GroupBuddyView({model: buddy});
            // Render view
            buddyView.render();
            // Append to container
            container.append(buddyView.get('container'));

            // Add buddy view to list
            views.push(buddyView);
        });

        // Remember views
        this.set('views', views);

        return this;
    }

}, {
    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Main container node
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        /**
         * Model attached to the view
         */
        model: {
            value: null // to be set
        },

        /**
         * List of rendered views
         *
         * [Y.LIMS.View.GroupBuddyView]
         */
        views: {
            value: [] // to be set
        }
    }
});
