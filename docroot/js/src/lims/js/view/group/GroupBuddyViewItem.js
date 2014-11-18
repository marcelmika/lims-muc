/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Buddy View Item
 *
 * The class extends Y.View. It represents the view for a single group buddy item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupBuddyViewItem = Y.Base.create('groupBuddyViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="group-buddy-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.BuddyModelItem,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#limsmuc-group-buddy-item-template').get('innerHTML'),

    /**
     * Renders the view
     *
     * @return {Y.LIMS.View.GroupBuddyViewItem}
     */
    render: function () {
        // Vars
        var container,
            model,
            portrait,
            presence;

        // Container and model
        container = this.get("container");
        model = this.get("model");

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                name: model.get('fullName')
            })
        );

        // Add portrait
        portrait = container.one('.group-buddy-item-portrait');
        portrait.append(this._getPortrait(model));

        // Add presence
        presence = container.one('.group-buddy-item-presence');
        presence.append(this._getPresence(model.get('presence'), model.get('connected')));

        // Attach events to newly created container
        this._attachEvents();

        return this;
    },

    /**
     * Attach events to container content
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model'),
            container = this.get('container');

        // Attach click on panel's item
        container.on('click', function (event) {
            event.preventDefault();
            // Fire event, add current model (buddy)
            Y.fire('buddySelected', {
                buddy: model
            });
        });
    },

    /**
     * Renders portrait based on screenName and returns the rendered HTML
     *
     * @param user whose portrait should be rendered
     * @returns HTML of the rendered portrait
     * @private
     */
    _getPortrait: function (user) {
        // Vars
        var portraitView = new Y.LIMS.View.PortraitView({
            users: [user],
            small: true
        });

        // Render the portrait
        portraitView.render();
        // Return the HTML
        return portraitView.get('container');
    },

    /**
     * Renders presence based on the presence type
     *
     * @param presenceType
     * @param connected
     * @return HTML of the rendered presence
     * @private
     */
    _getPresence: function (presenceType, connected) {
        // Vars
        var presenceView;

        // User is connected
        if (connected) {
            // Get the presence
            presenceView = new Y.LIMS.View.PresenceView({presenceType: presenceType});
        }
        // User is not connected
        else {
            // It means the user is offline
            presenceView = new Y.LIMS.View.PresenceView({presenceType: "OFFLINE"});
        }

        // Render presence
        presenceView.render();
        // Return the HTML
        return presenceView.get('container');
    }

}, {

    ATTRS: {

        /**
         * Container attached to view
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                // Create from template
                return Y.Node.create(this.containerTemplate);
            }
        },

        /**
         * Model attached to view
         *
         * {Y.LIMS.Model.BuddyModelItem}
         */
        model: {
            value: null // to be set
        }
    }

});

