/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Toggle View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationToggleViewController = Y.Base.create('conversationToggleViewController',
    Y.LIMS.Core.ViewController, [], {

        /**
         *  The initializer runs when a Presence View Controller instance is created.
         */
        initializer: function () {
            // This needs to be called in each view controller
            this.setup(this.get('container'), this.get('controllerId'));

            // Toggle is active
            if (this.get('properties').getActivePanelId() === this.get('controllerId')) {
                this.getPanel().show();
            }
        },

        /**
         * Renders view controller's views
         */
        render: function () {
            // Vars
            var conversationList = this.get('conversationList'),
                container = this.get('container'),
                countNode = this.get('countNode'),
                panelContent = this.get('panelContent'),
                listView = this.get('listView');

            // If no conversation are in the list remove the container from the doc
            if (conversationList.length === 0) {
                // Remove from doc
                container.remove();
                // End here
                return;
            }

            // Render title
            countNode.set('innerHTML', conversationList.length);

            // Update conversation list in the view
            listView.set('conversationList', conversationList);
            // Render it
            listView.render();

            // Add it the panel content
            panelContent.set('innerHTML', '');
            panelContent.append(listView.get('container'));

            // Render unread messages badge
            this._renderUnreadBadge();
        },

        /**
         * Returns true if the container is visible
         *
         * @return {boolean}
         */
        isVisible: function () {
            return this.get('container').inDoc();
        },

        /**
         * Adds conversation model to the toggle view
         *
         * @param conversation {Y.LIMS.Model.ConversationModel}
         */
        addConversation: function (conversation) {
            // Vars
            var conversationList = this.get('conversationList'),
                conversationId = conversation.get('conversationId') || null;


            // Parameter check
            if (!conversationId) {
                return;
            }

            // If there is no such controller in the map yet
            if (!this._containsConversation(conversationId)) {
                // Add it to the list
                conversationList.push(conversation);
                // Register events
                conversation.on('readSuccess', this._onConversationReadSuccess, this);
            }

            // Render
            this.render();
        },

        /**
         * Removes conversation model from the list
         *
         * @param conversation {Y.LIMS.Model.ConversationModel}
         */
        removeConversation: function (conversation) {
            // Vars
            var conversationId = conversation.get('conversationId') || null;

            // Parameter check
            if (!conversationId) {
                return;
            }

            if (this._containsConversation(conversationId)) {
                // If there is such controller in the map
                this._removeConversation(conversationId);
                // Detach subscription
                conversation.detach('readSuccess', this._onConversationReadSuccess, this);

                // Render
                this.render();
            }
        },

        /**
         * Renders unread badge
         *
         * @private
         */
        _renderUnreadBadge: function () {
            // Vars
            var conversationList = this.get('conversationList'),
                unreadBadge = this.get('unreadBadge'),
                unreadMessagesSum = 0;

            // Count the sum of unread messages over all registered conversations
            Y.Array.each(conversationList, function (conversation) {
                unreadMessagesSum += conversation.get('unreadMessagesCount');
            });

            // Fill in the sum
            unreadBadge.set('innerHTML', unreadMessagesSum);

            // Decide if the unread badge should be shown or hidden
            if (unreadMessagesSum === 0) {
                unreadBadge.hide();
            } else {
                unreadBadge.show();
            }
        },

        /**
         * Returns true if such conversation is found in the list
         *
         * @param conversationId {string}
         * @return {boolean}
         * @private
         */
        _containsConversation: function (conversationId) {
            // Vars
            var conversationList = this.get('conversationList'),
                foundConversation;

            // Find the model in the conversation list
            foundConversation = Y.Array.find(conversationList, function (model) {
                return model.get('conversationId') === conversationId;
            });

            return foundConversation !== null;
        },

        /**
         * Removes conversation from the conversation list
         *
         * @param conversationId
         * @private
         */
        _removeConversation: function (conversationId) {
            // Vars
            var conversationList = this.get('conversationList'),
                updatedList;

            // Filter out the model with the conversation id
            updatedList = Y.Array.filter(conversationList, function (model) {
                return model.get('conversationId') !== conversationId;
            });

            // Save the list
            this.set('conversationList', updatedList);
        },

        /**
         * Called when any of the registered conversation models is successfully read
         *
         * @private
         */
        _onConversationReadSuccess: function () {
            // Update badge
            this._renderUnreadBadge();
        }

    }, {

        // Specify attributes and static properties for your View here.
        ATTRS: {

            /**
             * Id of the controller
             *
             * {string}
             */
            controllerId: {
                value: "conversation-toggle"
            },

            /**
             * Container node attached to the controller
             *
             * {Node}
             */
            container: {
                value: null // to be set
            },

            /**
             * Properties object that holds the global portlet properties
             *
             * {Y.LIMS.Core.Properties}
             */
            properties: {
                value: null // to be set
            },

            /**
             * List View
             *
             * {Y.LIMS.View.ConversationToggleList}
             */
            listView: {
                valueFn: function () {
                    return new Y.LIMS.View.ConversationToggleList();
                }
            },

            /**
             * List that holds all currently opened conversation models
             *
             * [Y.LIMS.Model.ConversationModel]
             */
            conversationList: {
                value: [] // default value
            },

            /**
             * Panel content node
             *
             * {Node}
             */
            panelContent: {
                valueFn: function () {
                    return this.get('container').one('.panel-content');
                }
            },

            /**
             * Panel trigger node
             *
             * {Node}
             */
            panelTrigger: {
                valueFn: function () {
                    return this.get('container').one('.panel-trigger');
                }
            },

            /**
             * Panel trigger name node
             *
             * {Node}
             */
            panelTriggerName: {
                valueFn: function () {
                    return this.get('panelTrigger').one('.trigger-name');
                }
            },

            /**
             * Count node
             *
             * {Node}
             */
            countNode: {
                valueFn: function () {
                    return this.get('panelTrigger').one('.count');
                }
            },

            /**
             * Unread badge node
             *
             * {Node}
             */
            unreadBadge: {
                valueFn: function () {
                    return this.get('panelTrigger').one('.unread');
                }
            }
        }
    });
