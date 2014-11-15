/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Info notification view
 *
 * The class extends Y.View. It represents a view that holds info message
 */
Y.namespace('LIMS.View');

Y.LIMS.View.InfoNotificationView = Y.Base.create('infoNotificationView', Y.View, [], {

    // The template property holds the contents of the #lims-info-notification-template
    // element, which will be used as the HTML template for info notifications
    // Check the templates.jspf to see all templates
    infoTemplate: Y.one('#limsmuc-info-notification-template').get('innerHTML'),

    /**
     * Shows info message
     *
     * @param animated true if the event should be animated
     */
    showInfoMessage: function (animated) {
        // Vars
        var infoContainer = this.get('infoContainer'),
            container = this.get('container'),
            animation;

        // If the info container is already in the document do nothing
        if (!infoContainer.inDoc()) {

            // Event should be animated
            if (animated) {
                // Create an instance of animation
                animation = new Y.Anim({
                    node: infoContainer,
                    duration: 0.5,
                    from: {opacity: 0},
                    to: {opacity: 1}
                });

                // Opacity needs to be set to zero otherwise there will
                // be a weird blink effect
                infoContainer.setStyle('opacity', 0);

                // Add info to the container
                container.append(infoContainer);

                // Run the animation
                animation.run();
            }
            // Don't animate the event
            else {
                // Simply add it to the container
                container.append(infoContainer);
            }

            // Set layout based on the content
            this.layoutSubviews();
        }
    },

    /**
     * Hides info message
     *
     * @param animated true if the event should be animated
     */
    hideInfoMessage: function (animated) {
        // Vars
        var infoContainer = this.get('infoContainer'),
            animation;

        // Hide the info message only if the info container is in DOM
        if (infoContainer.inDoc()) {

            // Animate the event
            if (animated) {

                // Create an instance of animation
                animation = new Y.Anim({
                    node: infoContainer,
                    duration: 0.3,
                    from: {opacity: 1},
                    to: {opacity: 0}
                });

                // Listen to the end of the animation
                animation.on('end', function () {
                    // Remove the info node from DOM
                    animation.get('node').remove();
                });

                // Run the animation
                animation.run();
            }
            // Don't animate the event
            else {
                // Simply remove the container from DOM
                infoContainer.remove();
            }
        }
    },

    /**
     * Counts and sets layout of subviews
     */
    layoutSubviews: function () {
        // Vars
        var infoContainer = this.get('infoContainer'),
            infoMessageContainer = this.get('infoMessageContainer'),
            marginTop;

        // Reset margin
        infoMessageContainer.setStyle('margin-top', 0);
        // Count centered vertical position
        marginTop = infoContainer.get('clientHeight') / 2 - infoMessageContainer.get('clientHeight');
        // Set new margin
        infoMessageContainer.setStyle('margin-top', marginTop);
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Container attached to info notification view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Info message
         *
         * {string}
         */
        infoMessage: {
            value: null // to be set
        },

        /**
         * Info container that holds info content
         *
         * {Node}
         */
        infoContainer: {
            valueFn: function () {
                // Vars
                var infoMessage = this.get('infoMessage');
                // Create from template
                return Y.Node.create(Y.Lang.sub(this.infoTemplate, {
                    infoMessage: infoMessage
                }));
            }
        },

        /**
         * Info message container that holds info message
         *
         * {Node}
         */
        infoMessageContainer: {
            valueFn: function () {
                return this.get('infoContainer').one('.info-message');
            }
        }
    }
});


