/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Message List Model
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * MessageItemModel instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.MessageListModel = Y.Base.create('messageListModel', Y.ModelList, [], {

    // This tells the list what kind of model is using
    model: Y.LIMS.Model.MessageItemModel,

    /**
     * Returns an array of all message item models in this list that are now acknowledged
     *
     * @returns []
     */
    getNotAcknowledged: function () {
        return Y.Array.filter(this.toArray(), function (model) {
            return model.get('acknowledged') === false;
        });
    },

    /**
     * Returns a comparator used in the sort() function.
     * When a model is added to a list, it's automatically inserted at the correct index
     * to maintain the sort order of the list.
     *
     * @param model
     * @returns {*}
     */
    comparator: function (model) {
        return model.get('createdAt');
    }

}, {

    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.
    }
});
