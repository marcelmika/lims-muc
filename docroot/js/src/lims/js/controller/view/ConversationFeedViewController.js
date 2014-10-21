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
 * Conversation Feed View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationFeedViewController = Y.Base.create('conversationFeedViewController',
    Y.LIMS.Core.ViewController, [], {

        /**
         *  The initializer runs when the view controller instance is created.
         */
        initializer: function () {
            // This needs to be called in each view controller
            this.setup(this.get('container'), this.get('controllerId'));
        },

        /**
         * Panel Did Load is called when the panel is attached to the controller
         */
        onPanelDidLoad: function () {
            // Events
            this._attachEvents();
        },

        /**
         * Panel Did Appear is called when the panel did appear on the screen
         */
        onPanelDidAppear: function () {
            // Start poller
            this._startPolling();
        },

        /**
         * Panel Did Disappear is called when the panel disappeared from the screen
         */
        onPanelDidDisappear: function () {
            // Stop poller
            this._stopPolling();
        },

        /**
         * Session Expired is called whenever the user session has expired. Provide all necessary cleaning like
         * invalidation of timer, etc. At the end of the method the controller will be automatically hidden from
         * the screen.
         */
        onSessionExpired: function () {
            this._stopPolling();
        },

        /**
         * Attaches events to DOM elements from container
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
//            var model = this.get('model');

        },

        /**
         * Starts poller that periodically refreshes the group list
         *
         * @private
         */
        _startPolling: function () {

            // Vars
            var model = this.get('model'),
                poller = this.get('poller'),
                properties = this.get('properties');

            // Start only if the chat is enabled
            if (properties.isChatEnabled()) {

                // Register model to the poller
                poller.register('conversationFeedViewController:model', new Y.LIMS.Core.PollerEntry({
                    model: model,       // Model that will be periodically refreshed
                    interval: 10000     // 10 seconds period
                }));
            }
        },

        /**
         * Stops poller that periodically refreshes the group list
         *
         * @private
         */
        _stopPolling: function () {
            // Vars
            var poller = this.get('poller');
            // Pause
            poller.unregister('conversationFeedViewController:model');
        }

    }, {

        // Specify attributes and static properties for your View here.
        ATTRS: {

            // Id of the controller
            controllerId: {
                value: "conversation-feed"
            },

            // Container Node
            container: {
                value: null // to be set
            },

            /**
             * Controller model
             *
             * {Y.LIMS.Model.ConversationFeedList}
             */
            model: {
                valueFn: function () {
                    return new Y.LIMS.Model.ConversationFeedList();
                }
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
             * View that holds the list of conversations
             *
             * {Y.LIMS.View.ConversationFeedList}
             */
            listView: {
                valueFn: function () {
                    // Vars
                    var container = this.get('panelContent'),
                        model = this.get('model');

                    // Create view
                    return new Y.LIMS.View.ConversationFeedList({
                        container: container,
                        model: model
                    });
                }
            },

            /**
             * Properties object
             *
             * {Y.LIMS.Core.Properties}
             */
            properties: {
                value: null // to be set
            },

            /**
             * An instance of poller that periodically refreshes models that are subscribed
             *
             * {Y.LIMS.Core.Poller}
             */
            poller: {
                value: null // to be set
            }
        }
    });
