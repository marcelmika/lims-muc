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
    rootNode: '#lims-container',

    /**
     * Returns url of the portrait of buddy based on the portrait id
     * @param portraitId
     * @returns {string}
     */
    getPortraitUrl: function (portraitId) {
        var pathImage = Y.LIMS.Core.Properties.pathImage;

        return pathImage + '/user_portrait?img_id=' + portraitId;
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
        var target = event.currentTarget,
            scrollTop = target.get('scrollTop'),
            scrollHeight = target.get('scrollHeight'),
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
            target.set('scrollTop', scrollHeight);

            // Prevent scrolling of precedents
            return preventScrollFunction();
        }
        // Scroll up
        else if (up && delta > scrollTop) {
            // Scrolling up, but this will take us past the top.
            target.set('scrollTop', 0);
            // Prevent scrolling of precedents
            return preventScrollFunction();
        }

        return true;
    }
};


