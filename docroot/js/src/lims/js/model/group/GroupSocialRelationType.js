/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Social Relation Type
 *
 * Represent a social relation type
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupSocialRelationType = Y.Base.create('groupSocialRelationType', Y.Model, [], {

    /**
     * Returns localized name of the social relation type. Empty string is returned if
     * unknown relation types is set
     *
     * @returns {string}
     */
    getLocalizedName: function () {
        // Vars
        var socialRelationType = this.get('socialRelationType');

        // Check which relation type we have and return proper localized name of the
        // social relation type
        switch (socialRelationType) {

            // Unknown
            case 'TYPE_BI_UNKNOWN':
                return Y.LIMS.Core.i18n.values.socialRelationUnknown;

            // Connections
            case 'TYPE_BI_CONNECTION':
                return Y.LIMS.Core.i18n.values.socialRelationConnection;

            // Coworkers
            case 'TYPE_BI_COWORKER':
                return Y.LIMS.Core.i18n.values.socialRelationCoworker;
            // Friends
            case 'TYPE_BI_FRIEND':
                return Y.LIMS.Core.i18n.values.socialRelationFriend;

            // Romantic partner
            case 'TYPE_BI_ROMANTIC_PARTNER':
                return Y.LIMS.Core.i18n.values.socialRelationRomanticPartner;

            // Sibling
            case 'TYPE_BI_SIBLING':
                return Y.LIMS.Core.i18n.values.socialRelationSibling;

            // Unknown relation type was set
            default:
                return '';
        }
    }


}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Type of the social relation
         *
         * {string}
         */
        socialRelationType: {
            value: null
        }
    }

});

