/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Toggle Item View
 *
 * The class extends Y.View. It represents the view for the toggle list item
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationToggleItem = Y.Base.create('conversationToggleItem', Y.View, [], {

    // Main container template
    template: '<li class="toggle-item">',

    // The template property holds the contents of the #lims-conversation-toggle-item-template
    // element, which will be used as the HTML template for conversation toggle list.
    conversationTemplate: Y.one('#limsmuc-conversation-toggle-item-template').get('innerHTML'),

    /**
     * Called after the instance is created
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     *  Renders single conversation item
     *
     * @return {Y.LIMS.View.ConversationToggleItem}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            unreadBadge;

        // Cannot render view without model
        if (!model) {
            return this;
        }

        // Set the inner HTML from the template
        container.set('innerHTML', Y.Lang.sub(this.conversationTemplate, {
            title: model.get('title'),
            unreadMessages: model.get('unreadMessagesCount')
        }));

        // Hide the unread badge if there are no unread messages
        if (model.get('unreadMessagesCount') === 0) {
            // Get the unread badge
            unreadBadge = this.get('unreadBadge');
            // Hide it
            Y.LIMS.Core.Util.hide(unreadBadge);
        }

        return this;
    },

    /**
     * Attach event to the elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model'),
            container = this.get('container');

        // Local events
        model.on('readSuccess', this._onModelReadSuccess, this);

        // Delegates
        container.delegate('click', function (event) {
            // Vars
            var className = event.currentTarget.get('className');

            // Close button clicked
            if (className === 'close') {
                // Fire event
                Y.fire('conversationClosed', {
                    conversation: model
                });
            }
            // Title clicked
            else if (className === 'title') {
                // Fire event
                Y.fire('conversationSelected', {
                    conversation: model
                });
            }
        }, '.close, .title');
    },

    /**
     * Called when the model is successfully read
     *
     * @private
     */
    _onModelReadSuccess: function () {
        // Re-render the view again
        this.render();
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
                return Y.Node.create(this.template);
            }
        },

        /**
         * Model attached to view
         *
         * {Y.LIMS.Model.ConversationModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * Unread badge node
         *
         * {Node}
         */
        unreadBadge: {
            getter: function () {
                return this.get('container').one('.unread');
            }
        }
    }
});

