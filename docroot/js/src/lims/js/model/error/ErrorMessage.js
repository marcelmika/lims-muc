/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Error Message
 *
 * Contains error message
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ErrorMessage = Y.Base.create('ErrorMessage', Y.Model, [], {}, {

    ATTRS: {

        /**
         * Error code
         *
         * {Number}
         */
        code: {
            value: null // to be set
        },

        /**
         * Error message content
         *
         * {string}
         */
        message: {
            value: null // to be set
        }

    }
});