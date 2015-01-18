/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Core Extension
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.CoreExtension = function () {

};

Y.LIMS.Core.CoreExtension.prototype = {

    // Root node of the lims portlet
    rootNode: '#limsmuc-container',

    // Container of the whole portlet
    portletContainer: '.lims-muc',

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
    }
};



