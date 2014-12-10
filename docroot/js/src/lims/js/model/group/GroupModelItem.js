/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single group item.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModelItem = Y.Base.create('groupModelItem', Y.Model, [], {

    // Custom sync layer.
    sync: function (action, options, callback) {
        var data;

        switch (action) {
            case 'create':
                data = this.toJSON();
                return;
            case 'read':
                return;
            case 'update':
                return;
            case 'delete':
                return;
            default:
                callback('Invalid action');
        }
    }

}, {
    ATTRS: {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        name: {
            value: "" // default value
        },

        buddies: {
            value: [] // default value
        },

        /**
         * Social relation type
         *
         * {Y.LIMS.Model.GroupSocialRelationType}
         */
        socialRelation: {
            /**
             * Setter
             *
             * @param object
             * @returns {Y.LIMS.Model.GroupSocialRelationType|null}
             */
            setter: function (object) {
                // No social relation was set
                if (!object) {
                    return null;
                }

                // Create a model instance from value object
                if (object.name !== "groupSocialRelationType") {
                    return new Y.LIMS.Model.GroupSocialRelationType({
                        socialRelationType: object
                    });
                }
                // Value is already an instance of Y.LIMS.Model.GroupSocialRelationType
                return object;
            }
        }
    }
});
