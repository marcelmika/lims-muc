/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Localization
 *
 * Serves as an adapter to the localization texts
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.MobilePatch = Y.Base.create('mobilePatch', Y.Base, [Y.LIMS.Core.CoreExtension], {

    // Viewport with enable scalability
    viewPortZoomEnabled: '<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=1" />',
    // Viewport with disabled scalability
    viewPortZoomDisabled: '<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0" />',

    /**
     * The method will update the viewport meta tag to enable user zoom
     *
     * @private
     */
    enableZoom: function () {
        // Vars
        var head = Y.one('head'),
            defaultViewPort = this.get('defaultViewPort'),
            viewport;

        // Only for mobile devices
        if (!Y.UA.mobile) {
            return;
        }

        // Search for the view port
        viewport = head.one('meta[name=viewport]');

        if (viewport) {
            // Remove the previous view port
            viewport.remove();
        }

        // We are going to enable the zooming right after the user ends the
        // touch gesture. Because of that we wait another second before
        // we actually perform this action
        setTimeout(function () {
            // Add the default view port
            head.prepend(defaultViewPort);
        }, 1000);
    },

    /**
     * This method will update the viewport meta tag to disable user zoom
     *
     * @private
     */
    disableZoom: function () {
        // Vars
        var head = Y.one('head'),
            disabledZoomViewPort = this.get('disabledZoomViewPort'),
            viewport;

        // Only for mobile devices
        if (!Y.UA.mobile) {
            return;
        }

        // Search for the view port
        viewport = head.one('meta[name=viewport]');

        if (viewport) {
            // Remove the previous view port
            viewport.remove();
        }

        // Add the view port with disabled zoom
        head.prepend(disabledZoomViewPort);

        console.log('zoom disabled');
    }

}, {

    ATTRS: {

        /**
         * Already rendered view port or view port with enabled zoom
         *
         * {Node}
         */
        defaultViewPort: {
            valueFn: function () {
                // Vars
                var viewPort = Y.one('head meta[name=viewport]');

                // No view port found
                if (!viewPort) {
                    // Create viewport with enabled scalability
                    viewPort = Y.Node.create(this.viewPortZoomEnabled);
                }

                return viewPort;
            }
        },

        /**
         * View port with disabled zoom
         *
         * {Node}
         */
        disabledZoomViewPort: {
            valueFn: function () {
                return Y.Node.create(this.viewPortZoomDisabled);
            }
        }
    }
});
