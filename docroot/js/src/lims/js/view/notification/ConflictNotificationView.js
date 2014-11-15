/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conflict notification view
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConflictNotificationView = Y.Base.create('conflictNotificationView', Y.View, [Y.LIMS.View.ViewExtension], {

    // This customizes the HTML used for this view's container node.
    template: '<div class="conflict-notification"/>',

    /**
     * Shows conflict error message
     *
     * @param title
     * @param message
     */
    showConflictMessage: function (title, message) {
        // Vars
        var container = this.get('container'),
            rootNode = this.getPortletContainer(),
            content;

        // If the error container is already in the document do nothing
        if (!container.inDoc()) {
            rootNode.append(container);
        }

        // Build the message
        content = '<strong>' + title + '</strong>' + '<br/>' + message;

        // Write it to container
        container.set('innerHTML', content);
    },

    /**
     * Hides conflict error message
     */
    hideConflictMessage: function () {
        // Vars
        var container = this.get('container');

        // Remove container from dom
        if (container.inDoc()) {
            container.remove();
        }
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Container attached to error message
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.template);
            }
        }
    }
});


