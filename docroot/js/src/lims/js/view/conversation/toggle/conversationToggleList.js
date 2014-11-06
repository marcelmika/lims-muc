/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Toggle View
 *
 * The class extends Y.View. It represents the view for the toggle list
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationToggleList = Y.Base.create('conversationToggleList', Y.View, [], {

    // This customizes the HTML used for this view's list node.
    listTemplate: '<ul class="toggle-list">',

    /**
     * Renders list view
     *
     * @return {Y.LIMS.View.ConversationToggleList}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            conversationList = this.get('conversationList'),
            conversationItem,
            index;

        // Delete previous content
        container.set('innerHTML', '');

        // Render all conversations
        for (index = 0; index < conversationList.length; index++) {
            // Create new conversation toggle item view
            conversationItem = new Y.LIMS.View.ConversationToggleItem({
                model: conversationList[index]
            });
            // Render it
            conversationItem.render();
            // Append it to the container
            container.append(conversationItem.get('container'));
        }

        return this;
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container node
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.listTemplate);
            }
        },

        /**
         * List that holds all currently opened conversation models
         *
         * [Y.LIMS.Model.ConversationModel]
         */
        conversationList: {
            value: [] // default value
        }
    }
});

