/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group View Item
 *
 * The class extends Y.View. It represents the view for a single group item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupViewItem = Y.Base.create('groupViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="group-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.GroupModel,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#limsmuc-group-item-template').get('innerHTML'),

    /**
     * Renders the view
     *
     * @returns {Y.LIMS.View.GroupViewItem}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            socialRelation = model.get('socialRelation'),
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
        buddiesView = new Y.LIMS.View.GroupBuddyViewList({model: model.get('buddies')});
        buddiesView.render();
        container.append(buddiesView.get("container"));

        return this;
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
        }
    }

});

