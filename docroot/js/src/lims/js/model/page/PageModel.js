/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */


/**
 * Page Model
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.PageModel = Y.Base.create('pageModel', Y.Model, [], {}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Current page number
         *
         * {number}
         */
        number: {
            value: 0 // default value
        },

        /**
         * Size of the page
         *
         * {number}
         */
        size: {
            value: 0 // default value
        },

        /**
         * Number of all elements
         *
         * {number}
         */
        totalElements: {
            value: 0 // default value
        },

        /**
         * Number of all pages
         *
         * {number}
         */
        totalPages: {
            value: 0 // default value
        }
    }
});