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

Y.LIMS.View.ConversationFeedList = Y.Base.create('conversationFeedList', Y.View, [], {

    // Specify a model to associate with the view.
    model: Y.LIMS.Model.ConversationFeedList,

    /**
     * The initializer runs when the instance is created, and gives
     * us an opportunity to set up the view.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();

//        // Group list needs to be removed from the DOM since we don't know if there
//        // are any groups yet
//        this.get('groupList').remove();
    },

    /**
     * Attaches listeners to events
     *
     * @private
     */
    _attachEvents: function () {
        var model = this.get('model');
//            errorView = this.get('errorView');

        // Local events
        model.on('conversationFeedUpdated', this._onConversationFeedUpdated, this);
//        model.after('add', this._onGroupAdd, this);
//        model.on('groupReset', this._onGroupReset, this);
//        model.after('groupsReadSuccess', this._onGroupsReadSuccess, this);
//        model.after('groupsReadError', this._onGroupsReadError, this);
//        errorView.on('resendButtonClick', this._onResendButtonClick, this);
    },

    /**
     * Called when the conversation feed is updated
     * @private
     */
    _onConversationFeedUpdated: function () {
        // Vars
        var model = this.get('model'),
            conversationFeedList = this.get('conversationFeedList');

        // Reset the previously rendered conversations
        conversationFeedList.set('innerHTML', '');

        // Create conversation feed items
        model.each(function (conversation) {

            // Read
            var view = new Y.LIMS.View.ConversationFeedItem({
                model: conversation
            });
            view.render();

            conversationFeedList.append(view.get('container'));

        }, this);
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
         * Conversation list node
         *
         * {Node}
         */
        conversationFeedList: {
            valueFn: function () {
                return this.get('container').one('.conversation-feed-list');
            }
        }
    }

});