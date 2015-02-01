/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Model Extension
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ModelExtension = function () {

};

Y.LIMS.Model.ModelExtension.prototype = {


    /**
     * Returns URL of the server request resource
     *
     * @returns {URL}
     */
    getServerRequestUrl: function () {
        return Y.LIMS.Core.Properties.resourceURL;
    }
};
