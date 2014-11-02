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
 * Group View Item
 *
 * The class extends Y.View. It represents the view for a single group item.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationItemView = Y.Base.create('conversationViewItem', Y.View, [], {

    // This customizes the HTML used for this view's container node.
    containerTemplate: '<li class="conversation-item"/>',

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.MessageItemModel,

    // Check the templates.jspf to see all templates
    regularTemplate: Y.one('#lims-conversation-item-regular-template').get('innerHTML'),
    leftTemplate: Y.one('#lims-conversation-item-left-template').get('innerHTML'),
    addedTemplate: Y.one('#lims-conversation-item-added-template').get('innerHTML'),
    errorTemplate: Y.one('#lims-conversation-item-error-template').get('innerHTML'),

    /**
     * Called on initialization of object
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Returns an offset of the message from the top
     *
     * @return {Number}
     */
    getTopOffset: function () {
        return this.get('container').getDOM().offsetTop;
    },

    /**
     * Updates node that holds the creation time of message
     */
    updateTimestamp: function () {
        // Vars
        var dateNode = this.get('dateNode'),        // Node that holds date
            formatter = this.get('dateFormatter'),  // Prettify date formatter
            model = this.get('model');              // Message model

        // It is possible that the view doesn't have the date node (for example the left or add message)
        if (dateNode) {
            // Update time
            dateNode.set('innerHTML', formatter.prettyDate(model.get('createdAt')));
        }
    },

    /**
     * Renders view
     *
     * @returns {ConversationItemView}
     */
    render: function () {
        // Vars
        var model = this.get('model');

        // Regular type of the message
        if (model.get('messageType') === 'REGULAR') {
            this._renderRegularMessage();
        }
        // Left type of the message
        else if (model.get('messageType') === 'LEFT') {
            this._renderLeftMessage();
        }
        // Added type of the message
        else if (model.get('messageType') === 'ADDED') {
            this._renderAddedMessage();
        }

        return this;
    },

    /**
     * Renders regular type of the message
     *
     * @private
     */
    _renderRegularMessage: function () {
        // Vars
        var container = this.get('container'),      // Container that holds the view
            model = this.get('model'),              // Message model
            from = model.get('from'),               // Creator of the message
            formatter = this.get('dateFormatter');  // Prettify date formatter

        // Fill data from model to template and set it to container
        container.set('innerHTML', Y.Lang.sub(this.regularTemplate, {
                createdPrettified: formatter.prettyDate(model.get('createdAt')),
                created: formatter.formatDate(new Date(model.get('createdAt'))),
                fullName: from.get('fullName'),
                content: Y.Escape.html(model.get('body')),
                portrait: this._renderPortrait(from)
            })
        );

        // Set date node
        this.set('dateNode', container.one('.conversation-item-date'));
        this.set('messageTextNode', container.one('.conversation-item-text'));

        // Add subviews to the view
        this._addSubviews();
    },

    /**
     * Renders left type of message
     *
     * @private
     */
    _renderLeftMessage: function () {
        // Vars
        var container = this.get('container'),      // Container that holds the view
            from = this.get('model').get('from');   // User that left

        // Fill data from model to template and set it to container
        container.set('innerHTML', Y.Lang.sub(this.leftTemplate, {
                fullName: '<span class="name">' + from.get('fullName') + '</span>'
            })
        );
    },

    /**
     * Renders added type of message
     *
     * @private
     */
    _renderAddedMessage: function () {
        // Vars
        var container = this.get('container'),      // Container that holds the view
            from = this.get('model').get('from');   // User who was added

        // Fill data from model to template and set ti to container
        container.set('innerHTML', Y.Lang.sub(this.addedTemplate, {
                fullName: '<span class="name">' + from.get('fullName') + '</span>'
            })
        );
    },

    /**
     * Adds additional subviews like error message to the already rendered view
     *
     * @private
     */
    _addSubviews: function () {

        // Vars
        var container = this.get('container'),      // Container that holds the view
            model = this.get('model'),              // Message model,
            errorContainer = this.get('errorContainer'),
            instance = this;

        // If there is a error container from past, remove it
        if (errorContainer) {
            errorContainer.remove();
        }

        // If the message is already acknowledged don't dim it
        if (model.get('acknowledged') === true) {
            this._brightMessageText(false);
        }

        // Add error container node if needed
        if (model.get('error') === true) {
            errorContainer = Y.Node.create(Y.Lang.sub(this.errorTemplate));
            // Create error node from template and add it to the container
            container.append(errorContainer);

            // Attach click on delete button event
            container.one('.delete-button').on('click', function (event) {
                event.preventDefault();
                instance._onDeleteButtonClick();
            });
            // Attach click on resend button event
            container.one('.resend-button').on('click', function (event) {
                event.preventDefault();
                instance._onResendButtonClick();
            });

            this.set('errorContainer', errorContainer);
        }

        // It is possible that addSubviews was called many times, thus if there
        // is no error anymore and error container is still in DOM we
        // need to remove it
        if (model.get('error') === false && errorContainer !== null) {
            errorContainer.remove();
        }
    },

    /**
     * Attach listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var model = this.get('model');

        // Local events
        model.after('messageBegin', this._onMessageBegin, this);
        model.after('messageSent', this._onMessageSent, this);
        model.after('messageError', this._onMessageError, this);
        model.after('destroy', this._onDestroy, this);
    },

    /**
     * Renders portrait based on screenName and returns the rendered HTML
     *
     * @param user whose portrait should be rendered
     * @returns HTML of the rendered portrait
     * @private
     */
    _renderPortrait: function (user) {
        // Vars
        var portraitView = new Y.LIMS.View.PortraitView({
            users: [user],
            small: true
        });
        // Render
        portraitView.render();

        return portraitView.get('container').get('outerHTML');
    },

    /**
     * Performs dim effect on the message text node
     *
     * @private
     */
    _dimMessageText: function () {
        // Vars
        var messageTextNode = this.get('messageTextNode');

        // Dim the node
        messageTextNode.setStyle('opacity', 0.5);
    },

    /**
     * Performs bright animated effect on the message text node
     *
     * @private
     */
    _brightMessageText: function (animated) {

        // Vars
        var messageTextNode = this.get('messageTextNode'),
            animation;

        // Animate
        if (animated) {
            // Animate the message
            animation = new Y.Anim({
                node: messageTextNode,
                duration: 0.1,
                from: {opacity: 0.6},
                to: {opacity: 1}
            });
            // Run the animation
            animation.run();
        }
        // Don't animate
        else {
            messageTextNode.setStyle('opacity', 1);
        }
    },

    /**
     * Called when the message sending begins
     *
     * @private
     */
    _onMessageBegin: function () {
        this._dimMessageText();
    },

    /**
     * Called when the message is successfully sent
     *
     * @private
     */
    _onMessageSent: function () {
        this._addSubviews();
        this._brightMessageText(true);
    },

    /**
     * Called when the message cannot be sent
     *
     * @private
     */
    _onMessageError: function () {
        this._addSubviews();
        this._dimMessageText();
    },

    /**
     * Called when the user presses resend button
     *
     * @private
     */
    _onResendButtonClick: function () {
        // Vars
        var model = this.get('model'),
            resendButtonNode = this.get('container').one('.resend-button'),
            deleteButtonNode = this.get('container').one('.delete-button');

        // Prevent user to click on preloader more than once
        if (!resendButtonNode.hasClass('preloader')) {
            // Add preloader to the resend button
            resendButtonNode.addClass('preloader');
            // We don't want the user to delete the message
            // that is being processed
            deleteButtonNode.hide();

            // Save the model again
            model.save();
        }
    },

    /**
     * Called when the user presses delete button
     *
     * @private
     */
    _onDeleteButtonClick: function () {
        // Vars
        var model = this.get('model');

        // Destroy model
        model.destroy();
    },

    /**
     * Called when the model is destroyed
     *
     * @private
     */
    _onDestroy: function () {
        // Vars
        var container = this.get('container');

        // Remove container from DOM
        container.remove();

        // Destroying a view no longer also destroys the view's container node by default.
        // To destroy a view's container node when destroying the view, pass {remove: true}
        // to the view's destroy() method.
        this.destroy({
            remove: true
        });
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container that holds the message
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                // Create node from template on initialization
                return Y.Node.create(this.containerTemplate);
            }
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
         * Return message text node from container
         *
         * {Node}
         */
        messageTextNode: {
            value: null // to be set
        },

        /**
         * Instance of model attached to view
         *
         * {Y.LIMS.Model.MessageItemModel}
         */
        model: {
            value: null // default value
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
        },

        /**
         * Error container node
         *
         * {Node}
         */
        errorContainer: {
            value: null // will be set if needed
        }
    }
});

