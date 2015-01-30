/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * View Extension
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ViewExtension = function () {

};

Y.LIMS.View.ViewExtension.prototype = {

    // Root node of the lims portlet
    rootNode: '#limsmuc-container',

    // Container of the whole portlet
    portletContainer: '.lims-muc',

    /**
     * Returns url of the portrait for the given buddy
     *
     * @param buddy {Y.LIMS.Model.BuddyModelItem}
     * @returns {string}
     */
    getPortraitUrl: function (buddy) {
        // Vars
        var pathImage = Y.LIMS.Core.Properties.pathImage,
            male = buddy.get('male'),
            portraitId = buddy.get('portraitId'),
            portraitToken = buddy.get('portraitToken'),
            portraitImageToken = buddy.get('portraitImageToken'),
            pathUrl,
            portraitUrl;

        // Male
        if (male === true) {
            pathUrl = '/user_male_portrait';
        }
        // Female
        else if (male === false) {
            pathUrl = '/user_female_portrait';
        }
        // Other
        else {
            pathUrl = '/user_portrait';
        }

        // Compose the portrait url
        portraitUrl = pathImage + pathUrl + '?img_id=' + portraitId;

        // Add the portrait image token if needed
        if (portraitImageToken) {
            portraitUrl += ('&img_id_token=' + portraitImageToken);
        }

        // Add the portrait token if needed
        if (portraitToken) {
            portraitUrl += ('&t=' + portraitToken);
        }

        return portraitUrl;
    },

    /**
     * Returns root container node
     *
     * @returns {Node}
     */
    getRootNode: function () {
        return Y.one(this.rootNode);
    },

    /**
     * Returns portlet's container
     */
    getPortletContainer: function () {
        return Y.one(this.portletContainer);
    },

    /**
     * Returns true if the portlet is in the IE support
     *
     * @returns {boolean}
     */
    hasIESupport: function () {
        return Y.one(this.rootNode).hasClass('ie-support');
    },

    /**
     * Prevents scrolling of descendant nodes
     *
     * @param event {Y.EventFacade} mouse wheel event
     * @param node {Y.Node}
     * @return {boolean}
     */
    preventScroll: function (event, node) {
        // Vars
        var scrollTop = node.get('scrollTop'),
            scrollHeight = node.get('scrollHeight'),
            height = node.get('clientHeight'),
            delta = event.wheelDelta,
            up = delta > 0,
            preventScrollFunction;

        // Function which stops the propagation of the scrolling event.
        // Thanks to that no precedents divs will scroll
        preventScrollFunction = function () {
            event.stopPropagation();
            event.preventDefault();
            event.returnValue = false;
            return false;
        };

        // Scroll down
        if (!up && -delta > scrollHeight - height - scrollTop) {
            // Scrolling down, but this will take us past the bottom.
            node.set('scrollTop', scrollHeight);

            // Prevent scrolling of precedents
            return preventScrollFunction();
        }
        // Scroll up
        else if (up && delta > scrollTop) {
            // Scrolling up, but this will take us past the top.
            node.set('scrollTop', 0);
            // Prevent scrolling of precedents
            return preventScrollFunction();
        }

        return true;
    }
};


