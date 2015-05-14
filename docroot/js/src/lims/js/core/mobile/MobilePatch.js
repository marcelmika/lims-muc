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

    /**
     * The method will update the viewport meta tag to enable user zoom
     */
    enableZoom: function () {
        // Vars
        var head = Y.one('head'),
            content,
            viewport;

        // Only for mobile devices
        if (!Y.UA.mobile) {
            return;
        }

        // Search for the view port
        viewport = head.one('meta[name=viewport]');

        if (!viewport) {
            // We don't need to do anything since there is no view port set
            // and user-scalable=1 is default
            return;
        }

        // Get the content value of the viewport meta tag
        content = viewport.get('content');

        if (content.indexOf("user-scalable=1") > -1) {
            // We don't need to do anything because zoom is already enabled
            return;
        }

        // If the scalability is disabled
        if (content && content.indexOf("user-scalable=1") === -1) {

            // User scalability is already set
            if (content.indexOf("user-scalable=0") > -1) {
                // Replace value
                content = content.replace('user-scalable=0', 'user-scalable=1');
            }
            // User scalability is not set
            else {
                // Add it to content
                content += ', user-scalable=1';
            }

            // Update content of the meta tag
            viewport.set('content', content);
        }
    },

    /**
     * This method will update the viewport meta tag to disable user zoom
     */
    disableZoom: function () {
        // Vars
        var head = Y.one('head'),
            content,
            viewport;

        // Only for mobile devices
        if (!Y.UA.mobile) {
            return;
        }

        // Search for the view port
        viewport = head.one('meta[name=viewport]');

        if (!viewport) {
            // Create new view port met tag
            viewport = Y.Node.create(this.viewPortZoomEnabled);
            // Add the view port with disabled zoom
            head.prepend(viewport);
        }

        // Get the content value of the viewport meta tag
        content = viewport.get('content');

        if (content.indexOf("user-scalable=0") > -1) {
            // We don't need to do anything because zoom is already disabled
            return;
        }

        // If the scalability is not disabled
        if (content && content.indexOf("user-scalable=0") === -1) {

            // User scalability is already set
            if (content.indexOf("user-scalable=1") > -1) {
                // Replace value
                content = content.replace('user-scalable=1', 'user-scalable=0');
            }
            // User scalability is not set
            else {
                // Add it to content
                content += ', user-scalable=0';
            }

            // Update content of the meta tag
            viewport.set('content', content);
        }
    },

    /**
     * Adds mobile-device class to the root node if the device is mobile
     */
    detectMobileDevice: function () {
        if (Y.UA.mobile) {
            this.getPortletContainer().addClass('mobile-device');
        }
    }

});
