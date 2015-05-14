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
    },

    /**
     * Opens the list
     */
    open: function (callback) {
        // Vars
        var container = this.get('container'),
            offsetHeight = this.get('offsetHeight'),
            animation,
            instance = this;

        // Don't perform another animation if one is in progress
        if (this.get('animating')) {
            return;
        }

        // Prepare open animation
        animation = new Y.Anim({
            node: container,
            duration: 0.3,
            to: {
                height: offsetHeight,
                opacity: 1
            }
        });

        animation.before('start', function () {
            instance.set('animating', true);
        }, this);

        animation.after('end', function () {
            instance.set('animating', false);
            callback();
        }, this);

        // Run the animation
        animation.run();

        // Set the closed flag
        this.set('closed', false);
    },

    /**
     * Closes the list
     */
    close: function (callback) {
        // Vars
        var container = this.get('container'),
            animation,
            instance = this;

        // Don't perform another animation if one is in progress
        if (this.get('animating')) {
            return;
        }

        // Remember the height
        this.set('offsetHeight', container.get('offsetHeight'));

        // Prepare close animation
        animation = new Y.Anim({
            node: container,
            duration: 0.3,
            to: {
                height: 0,
                opacity: 0
            }
        });

        animation.before('start', function () {
            instance.set('animating', true);
        }, this);

        animation.after('end', function () {
            instance.set('animating', false);
            callback();
        }, this);

        // Run the animation
        animation.run();

        // Set the closed flag
        this.set('closed', true);
    },

    /**
     * If the list is closed it opens it and vice versa
     */
    toggle: function (callback) {
        // Closed
        if (this.get('closed')) {
            // Open
            this.open(callback);
        }
        // Opened
        else {
            // Close
            this.close(callback);
        }
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
         * Set to true if the user closed the group by clicking on the group name
         *
         * {boolean}
         */
        closed: {
            value: false
        },

        /**
         * Set to true if the animation is in progress
         *
         * {boolean}
         */
        animating: {
            value: false
        },

        /**
         * Contains height of the the view
         *
         * {number}
         */
        offsetHeight: {
            value: null // to be set
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
