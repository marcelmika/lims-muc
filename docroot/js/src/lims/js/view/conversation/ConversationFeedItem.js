/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
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

    // Check the templates.jspf to see all templates
    regularTemplate: Y.one('#limsmuc-conversation-feed-item-regular-template').get('innerHTML'),
    leftTemplate: Y.one('#limsmuc-conversation-feed-item-left-template').get('innerHTML'),
    addedTemplate: Y.one('#limsmuc-conversation-feed-item-added-template').get('innerHTML'),


    /**
     * Updates node that holds the creation time of message
     */
    updateTimestamp: function () {
        // Vars
        var dateNode = this.get('dateNode'),                // Node that holds date
            formatter = this.get('dateFormatter'),          // Prettify date formatter
            model = this.get('model').get('lastMessage');   // Message model

        // It is possible that the view doesn't have the date node (for example the left or add message)
        if (dateNode) {
            // Update time
            dateNode.set('innerHTML', formatter.prettyDate(new Date(model.get('createdAt'))));
        }
    },

    /**
     * Renders the view
     *
     * @returns {Y.LIMS.View.ConversationFeedItem}
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            lastMessage = model.get('lastMessage');

        // Regular type of the message
        if (lastMessage.get('messageType') === 'REGULAR') {
            this._renderRegularMessage();
        }
        // Left type of the message
        else if (lastMessage.get('messageType') === 'LEFT') {
            this._renderLeftMessage();
        }
        // Added type of the message
        else if (lastMessage.get('messageType') === 'ADDED') {
            this._renderAddedMessage();
        }

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
     * Renders regular type of message
     * @private
     */
    _renderRegularMessage: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            lastMessage = model.get('lastMessage'),
            portrait,
            formatter = this.get('dateFormatter');  // Prettify date formatter

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.regularTemplate, {
                title: model.get('title'),
                lastMessage: Y.Escape.html(lastMessage.get('body')),
                timestampPrettified: formatter.prettyDate(lastMessage.get('createdAt')),
                timestamp: formatter.formatDate(new Date(lastMessage.get('createdAt')))
            })
        );

        // Add portrait
        portrait = container.one('.item-portrait');
        portrait.append(this._renderPortrait(model.get('participants')));

        // Set date node
        this.set('dateNode', container.one('.timestamp'));
    },

    /**
     * Renders left type of message
     *
     * @private
     */
    _renderLeftMessage: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            lastMessage = model.get('lastMessage'),
            from = lastMessage.get('from'),
            portrait,
            formatter = this.get('dateFormatter');  // Prettify date formatter

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.leftTemplate, {
                title: model.get('title'),
                fullName: '<span class="name">' + from.printableName() + '</span>',
                timestampPrettified: formatter.prettyDate(lastMessage.get('createdAt')),
                timestamp: formatter.formatDate(new Date(lastMessage.get('createdAt')))
            })
        );

        // Add portrait
        portrait = container.one('.item-portrait');
        portrait.append(this._renderPortrait(model.get('participants')));
    },

    /**
     * Renders added type of message
     *
     * @private
     */
    _renderAddedMessage: function () {
        // Vars
        var container = this.get('container'),
            model = this.get('model'),
            lastMessage = model.get('lastMessage'),
            from = lastMessage.get('from'),
            portrait,
            formatter = this.get('dateFormatter');  // Prettify date formatter

        // Fill data from model to template and set it to container
        container.set('innerHTML',
            Y.Lang.sub(this.addedTemplate, {
                title: model.get('title'),
                fullName: '<span class="name">' + from.printableName() + '</span>',
                timestampPrettified: formatter.prettyDate(lastMessage.get('createdAt')),
                timestamp: formatter.formatDate(new Date(lastMessage.get('createdAt')))
            })
        );

        // Add portrait
        portrait = container.one('.item-portrait');
        portrait.append(this._renderPortrait(model.get('participants')));
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
        renderedUsers = Y.Array.filter(participants, function (participant) {
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
        return portraitView.get('container');
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

