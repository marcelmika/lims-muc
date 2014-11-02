/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Controller Extension
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ControllerExtension = function () {

};

Y.LIMS.Controller.ControllerExtension.prototype = {

    // Root node of the lims portlet
    rootNode: '#lims-container',

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
    }
};
