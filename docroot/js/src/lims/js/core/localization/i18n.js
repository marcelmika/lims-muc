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

Y.LIMS.Core.i18n = Y.Base.create('i18n', Y.Base, [], {

    /**
     * Holds the values object which contains all translations for the current language
     */
    values: null // This is set in main.js
    
});
