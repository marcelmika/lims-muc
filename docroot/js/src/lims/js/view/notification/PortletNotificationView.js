/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Portlet notification view
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PortletNotificationView = Y.Base.create('portletNotificationView', Y.View, [], {

    // Notification template
    notificationTemplate: '<div class="portlet-notification"/>',
    // Check the templates.jspf to see all templates
    contentTemplate: Y.one('#limsmuc-portlet-notification-template').get('innerHTML'),

    /**
     * Renders notification
     */
    render: function () {
        // Vars
        var notification = this.get('notification'),
            content = this.get('content');

        // Render content of the portlet notification
        notification.set('innerHTML',
            Y.Lang.sub(this.contentTemplate, {
                buttonTitle: Y.LIMS.Core.i18n.values.portletNotificationCloseButtonTitle,
                content: content
            })
        );

        // Attach events
        this._attachEvents();
    },

    /**
     * Shows the notification
     */
    show: function () {
        // Vars
        var container = this.get('container'),
            notification = this.get('notification'),
            animation;

        // Add notification to container
        if (!notification.inDoc()) {
            // Create animation
            animation = new Y.Anim({
                node: notification,
                duration: 0.5,
                from: {opacity: 0},
                to: {opacity:1}
            });

            // Set the opacity to the starting point
            notification.setStyle('opacity', 0);
            // Add to container
            container.append(notification);
            // Run the animation
            animation.run();
        }

        // Show the notification
        Y.LIMS.Core.Util.show(notification);
    },

    /**
     * Hides the notification
     */
    hide: function () {
        // Vars
        var notification = this.get('notification');

        // Remove from container if needed
        if (notification.inDoc()) {
            notification.remove();
        }

        // Hide the notification
        Y.LIMS.Core.Util.hide(notification);
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var closeButton = this.get('closeButton');

        if (closeButton) {
            closeButton.on('click', this._onCloseButtonClick, this);
        }
    },

    /**
     * Called when the close button is clicked
     *
     * @private
     */
    _onCloseButtonClick: function () {
        // Hide the notification
        this.hide();
    }

}, {

    ATTRS: {

        /**
         * Container node
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Notification node
         */
        notification: {
            valueFn: function () {
                // Vars
                return Y.Node.create(this.notificationTemplate);
            }
        },

        /**
         * Close button node, if the notification is not rendered returns null
         *
         * {Node}
         */
        closeButton: {
            getter: function () {
                return this.get('notification').one('.close-notification');
            }
        },

        /**
         * Content of the notification
         *
         * {string}
         */
        content: {
            value: "" // to be set
        }
    }

});
