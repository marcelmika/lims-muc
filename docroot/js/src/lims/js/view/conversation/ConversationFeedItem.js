/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * Conversation Feed Item
 *
 * The class extends Y.View. It represents the view for a single conversation feed item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationFeedItem = Y.Base.create('conversationFeedItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="conversation-feed-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.ConversationModel,

    // The template property holds the contents of the #lims-group-item-template
    // element, which will be used as the HTML template for each group item.
    template: Y.one('#lims-conversation-feed-item-template').get('innerHTML'),

    /**
     * Renders the view
     *
     * @returns {Y.LIMS.View.ConversationFeedItem}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            lastMessage = model.get('lastMessage'),
            formatter = this.get('dateFormatter');  // Prettify date formatter

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.template, {
                title: model.get('title'),
                lastMessage: Y.Escape.html(lastMessage.get('body')),
                portrait: this._renderPortrait(model.get('participants')),
                timestampPrettified: formatter.prettyDate(lastMessage.get('createdAt')),
                timestamp: formatter.formatDate(new Date(lastMessage.get('createdAt')))
            })
        );

        // Set date node
        this.set('dateNode', container.one('.timestamp'));

        // Check if the conversation is unread
        if (model.get('unreadMessagesCount') > 0) {
            container.addClass('unread');
        } else {
            container.removeClass('unread');
        }

        // Attach events to newly created container
        this._attachEvents();

        return this;
    },

    /**
     * Updates node that holds the creation time of message
     */
    updateTimestamp: function () {
        // Vars
        var dateNode = this.get('dateNode'),                // Node that holds date
            formatter = this.get('dateFormatter'),          // Prettify date formatter
            model = this.get('model').get('lastMessage');   // Message model

        // Update time
        dateNode.set('innerHTML', formatter.prettyDate(new Date(model.get('createdAt'))));
    },

    /**
     * Attach events to container content
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model'),
            container = this.get('container');

        // Remote events
        Y.on('conversationPanelOpened', this._onConversationPanelOpened, this);

        // Attach click on panel's item
        container.on('click', function (event) {
            event.preventDefault();

            // The message is read
            container.removeClass('unread');

            // Fire event
            Y.fire('conversationSelected', {
                conversation: model
            });

        });
    },

    /**
     * Renders portrait based on screenName and returns the rendered HTML
     *
     * @param participants whose portraits should be rendered
     * @returns HTML of the rendered portrait or null if no correct participant was found
     * @private
     */
    _renderPortrait: function (participants) {
        // Vars
        var portraitView,
            renderedUsers,
            buddyDetails = this.get('buddyDetails');

        // Participants contain the currently logged user as well.
        // Thus we need to filter him first.
        renderedUsers = participants.filter(function (participant) {
            return buddyDetails.get('buddyId') !== participant.get('buddyId');
        });

        // If there are any participants in the conversation render them
        if (renderedUsers.length > 0) {
            // Create portrait view
            portraitView = new Y.LIMS.View.PortraitView({users: renderedUsers});
        }
        // If there is no participants, it means that the only participants left is
        // the logged buddy himself
        else {
            // Create portrait view
            portraitView = new Y.LIMS.View.PortraitView({users: [buddyDetails]});
        }

        // Render
        portraitView.render();
        // Return the HTML
        return portraitView.get('container').get('outerHTML');
    },

    /**
     * Called when the user opens any of the conversations panel
     *
     * @private
     */
    _onConversationPanelOpened: function (event) {
        // Vars
        var model = this.get('model'),
            container = this.get('container'),
            conversation = event.conversation;

        // Our conversation was opened
        if (conversation.get('conversationId') === model.get('conversationId')) {
            // Remove the unread class
            container.removeClass('unread');
        }
    }

}, {

    ATTRS: {

        /**
         * Container node
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.containerTemplate);
            }
        },

        /**
         * Model
         *
         * {Y.LIMS.Model.ConversationModel}
         */
        model: {
            value: null // to be set
        },


        /**
         * Currently logged user
         *
         * {Y.LIMS.ModelBuddyModelItem}
         */
        buddyDetails: {
            value: null // to be set
        },

        /**
         * Node that contains date
         *
         * {Node}
         */
        dateNode: {
            value: null // to be set
        },

        /**
         * Instance of date formatter
         *
         * {DateFormatter}
         */
        dateFormatter: {
            valueFn: function () {
                return new Y.LIMS.Core.DateFormatter();
            }
        }
    }

});

