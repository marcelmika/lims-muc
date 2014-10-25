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
 * Conversation Feed List View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationFeedList = Y.Base.create('conversationFeedList', Y.View, [Y.LIMS.View.ViewExtension], {

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.ConversationFeedList,

    // Template for the activity indicator
    readMoreActivityIndicator: '<div class="preloader read-more-preloader" />',

    /**
     * The initializer runs when the instance is created, and gives
     * us an opportunity to set up the view.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Shows view
     */
    showView: function () {
        // Show list view again
        this._showListView();
    },

    /**
     * Hides view
     */
    hideView: function () {
        // Hide list view too
        this._hideListView();
    },

    /**
     * Updates timestamp in each conversations
     */
    updateTimestamps: function () {
        // Vars
        var index,
            conversationItemViews = this.get('conversationItemViews'),
            conversationItem;

        for (index = 0; index < conversationItemViews.length; index++) {
            conversationItem = conversationItemViews[index];
            conversationItem.updateTimestamp();
        }
    },

    /**
     * Attaches listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        var model = this.get('model'),
            container = this.get('container'),
            errorView = this.get('errorView');

        // Local events
        model.on('conversationFeedUpdated', this._onConversationFeedUpdated, this);
        model.on('readSuccess', this._onConversationFeedReadSuccess, this);
        model.on('readError', this._onConversationFeedReadError, this);
        errorView.on('resendButtonClick', this._onConversationFeedReadRetry, this);

        container.on('mousewheel', this._onContainerMouseWheel, this);
        container.on('scroll', this._onContainerContentScroll, this);
    },

    /**
     * Shows list view
     *
     * @param animated true if the action should be animated
     * @private
     */
    _showListView: function (animated) {
        // Vars
        var conversationFeedList = this.get('conversationFeedList'),
            animation = new Y.Anim({
                node: conversationFeedList,
                duration: 0.5,
                from: {
                    opacity: 0
                },
                to: {
                    opacity: 1
                }
            });

        // Opacity needs to be set to zero otherwise there will
        // be a weird blink effect
        if (animated) {
            conversationFeedList.setStyle('opacity', 0);
        }

        conversationFeedList.show();

        // Run the effect animation
        if (animated) {
            animation.run();
        }
    },

    /**
     * Hides list view
     *
     * @private
     */
    _hideListView: function () {
        // Vars
        var conversationFeedList = this.get('conversationFeedList');
        // Hide list view
        conversationFeedList.hide();
    },

    /**
     * Renders conversation list
     *
     * @private
     */
    _renderConversationList: function () {
        // Vars
        var model = this.get('model'),
            conversationFeedList = this.get('conversationFeedList'),
            conversationItemViews = this.get('conversationItemViews'),
            buddyDetails = this.get('buddyDetails'),
            animate = this.get('shouldAnimateList');

        // Reset the previously rendered conversations
        this._resetListView();

        // Create conversation feed items
        model.each(function (conversation) {

            // Create new conversation feed item view
            var view = new Y.LIMS.View.ConversationFeedItem({
                model: conversation,
                buddyDetails: buddyDetails
            });
            // Render the view
            view.render();

            // Remember the view
            conversationItemViews.push(view);
            // Append it to the list
            conversationFeedList.append(view.get('container'));

        }, this);

        // Set the updated conversation item views list
        this.set('conversationItemViews', conversationItemViews);

        // Show it again and animate it if needed
        this._showListView(animate);
    },

    /**
     * Removes the whole content from  list view
     *
     * @private
     */
    _resetListView: function () {
        // Vars
        var conversationFeedList = this.get('conversationFeedList');
        // This will reset the content of the conversation feed list view
        conversationFeedList.set('innerHTML', '');
        // Reset the list of views too
        this.set('conversationItemViews', []);
    },

    /**
     * Shows read more activity indicator
     *
     * @private
     */
    _showReadMoreActivityIndicator: function () {
        // Vars
        var indicator = this.get('readMoreActivityIndicator'),
            conversationFeedList = this.get('conversationFeedList');

        if (!indicator.inDoc()) {
            conversationFeedList.append(indicator);
        }
    },

    /**
     * Hide read more activity indicator
     *
     * @private
     */
    _hideReadMoreActivityIndicator: function () {
        // Vars
        var indicator = this.get('readMoreActivityIndicator');

        if (indicator.inDoc()) {
            indicator.remove();
        }
    },

    /**
     * Scrolls the whole list to top
     *
     * @private
     */
    _scrollToTop: function () {
        // Vars
        var panelContent = this.get('container');

        // Wait a second before we scroll to avoid blinking effect
        setTimeout(function () {
            panelContent.set('scrollTop', 0);
        }, 1);
    },

    /**
     * Called when the conversation feed was successfully read
     *
     * @private
     */
    _onConversationFeedReadSuccess: function () {
        // Vars
        var model = this.get('model'),
            errorView = this.get('errorView'),
            infoView = this.get('infoView'),
            activityIndicator = this.get('activityIndicator'),
            shouldAnimateList = this.get('shouldAnimateList');

        // If there was any error, hide it
        errorView.hideErrorMessage(true);

        // No conversations found
        if (model.isEmpty()) {
            // Show info message
            infoView.showInfoMessage(true);
            // Hide list view
            this._hideListView();
        } else {
            // Show the list view
            this._showListView(shouldAnimateList);
            // Hide info message
            infoView.hideInfoMessage(true);
        }

        // Since the list is already rendered there is no need to
        // animate any other addition to the list
        this.set('shouldAnimateList', false);

        // Hide indicator
        activityIndicator.hide();
    },

    /**
     * Called when there was an error during the conversation feed list reading
     *
     * @private
     */
    _onConversationFeedReadError: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator'),
            infoView = this.get('infoView'),
            errorView = this.get('errorView');

        // Hide indicator
        activityIndicator.hide();
        // Hide conversations
        this._hideListView();
        // Hide info about no conversations
        infoView.hideInfoMessage(true);
        // Show error
        errorView.showErrorMessage(true);

        // Since there was an error and list was hidden, animate it next time
        this.set('shouldAnimateList', true);
    },

    /**
     * Called when the conversation feed is updated
     *
     * @private
     */
    _onConversationFeedUpdated: function (event) {
        // Hide indicator if it wasn't already hidden
        this.get('activityIndicator').hide();
        // Render the list
        this._renderConversationList();

        // If a new conversation was added to top scroll there
        if (!event.readMore) {
            this._scrollToTop();
        }

        // Since the list is already rendered there is no need to
        // animate any other addition to the list
        this.set('shouldAnimateList', false);
    },

    /**
     * Called when the user click on the retry button within the read error view
     *
     * @private
     */
    _onConversationFeedReadRetry: function () {
        // Vars
        var model = this.get('model');

        // Reload model
        model.load();
    },

    /**
     * Called when the user scrolls the panel content
     *
     * @private
     */
    _onContainerContentScroll: function () {
        // Vars
        var scrollPosition = this.get('container').get('scrollTop'),
            clientHeight = this.get('container').get('clientHeight'),
            scrollHeight = this.get('container').get('scrollHeight'),
            model = this.get('model'),
            instance = this,
            hasReachedBottom = function () {
                return scrollPosition + clientHeight === scrollHeight;
            };

        // User has reached the bottom by scrolling and there is still something
        // we can read from the model
        if (hasReachedBottom() && !model.get('reachedTop')) {
            // Show the preloader
            this._showReadMoreActivityIndicator();
            // Load the model
            model.load({readMore: true}, function () {
                instance._hideReadMoreActivityIndicator();
            });
        }
    },

    /**
     * Called when the user scrolls with mouse wheel over the container
     *
     * @param event
     * @return {*}
     * @private
     */
    _onContainerMouseWheel: function (event) {
        // Vars
        var container = this.get('container');
        // Prevent scrolling of the whole window
        return this.preventScroll(event, container);
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Group view container node
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Group list model
         *
         * {Y.LIMS.Model.ConversationFeedList}
         */
        model: {
            value: null // to be set
        },

        /**
         * Activity indicator node
         *
         * {Y.Node}
         */
        activityIndicator: {
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
         * Conversation list node
         *
         * {Node}
         */
        conversationFeedList: {
            valueFn: function () {
                return this.get('container').one('.conversation-feed-list');
            }
        },

        /**
         * Read more activity indicator node
         *
         * {Node}
         */
        readMoreActivityIndicator: {
            valueFn: function () {
                return Y.Node.create(this.readMoreActivityIndicator);
            }
        },

        /**
         * An array that holds all conversations
         *
         * [Y.LIMS.View.ConversationFeedItem]
         */
        conversationItemViews: {
            value: []
        },

        /**
         * Set to true if the appearance of elements in the list should be animated
         *
         * {boolean}
         */
        shouldAnimateList: {
            value: true
        },

        /**
         * Info view with info message
         *
         * {Y.LIMS.View.InfoNotificationView}
         */
        infoView: {
            valueFn: function () {
                // Vars
                var container = this.get('container');
                // Create view
                return new Y.LIMS.View.InfoNotificationView({
                    container: container,
                    infoMessage: Y.LIMS.Core.i18n.values.conversationFeedEmptyInfoMessage
                });
            }
        },

        /**
         * Conversation read error view
         *
         * {Y.LIMS.View.ErrorNotificationView}
         */
        errorView: {
            valueFn: function () {
                // Vars
                var container = this.get('container');
                // Create view
                return new Y.LIMS.View.ErrorNotificationView({
                    container: container,
                    errorMessage: Y.LIMS.Core.i18n.values.conversationFeedReadErrorMessage
                });
            }
        }
    }
});