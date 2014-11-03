/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
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
            // Start timer that periodically updates timestamps of messages
            this._startTimer();
            // Subscribe to key up event
            this._subscribeKeyUp();
        },

        /**
         * Panel Did Disappear is called when the panel disappeared from the screen
         */
        onPanelDidDisappear: function () {
            // Stop poller
            this._stopPolling();
            // No need to updated message timestamps since they will be updated whenever
            // the panel appears again
            this._pauseTimer();
            // Detach the key up event
            this._detachKeyUp();
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
            var newConversationButton = this.get('newConversationButton'),
                newConversationView = this.get('newConversationView');

            // Local events
            newConversationButton.on('click', this._onNewConversationClick, this);
            newConversationView.on('selectedBuddies', this._onNewConversationSelectedBuddies, this);

            // Remote events
            Y.on('conversationPanelOpened', this._onConversationPanelOpened, this);
            Y.on('connectionError', this._onConnectionError, this);
            Y.on('connectionOK', this._onConnectionOK, this);
        },

        /**
         * Subscribes to the global key up event
         *
         * @private
         */
        _subscribeKeyUp: function () {
            if (Y.one('doc')) {
                // Save the subscription to the key up event
                this.set('keyUpSubscription', Y.one('doc').on('keyup', this._onKeyPress, this));
            }
        },

        /**
         * Detaches the subscription to the global key up event
         *
         * @private
         */
        _detachKeyUp: function () {
            // Vars
            var keyUpSubscription = this.get('keyUpSubscription');

            if (keyUpSubscription) {
                keyUpSubscription.detach();
            }
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
        },

        /**
         * Starts timer which periodically refreshes conversation list
         *
         * @private
         */
        _startTimer: function () {
            // Vars
            var properties = this.get('properties'),
                listView = this.get('listView'),
                timerInterval = this.get('timerInterval');

            // Start only if the chat is enabled
            if (properties.isChatEnabled()) {

                // Update all timestamps
                listView.updateTimestamps();
                // Start periodical update
                this.set('timer', setInterval(function () {
                    // Load model
                    listView.updateTimestamps();
                }, timerInterval));
            }
        },

        /**
         * Pauses timer which periodically refreshes group list
         *
         * @private
         */
        _pauseTimer: function () {
            // Vars
            var timer = this.get('timer');
            // Pause
            clearTimeout(timer);
        },


        /**
         * Called when the user click on the new conversation button
         *
         * @private
         */
        _onNewConversationClick: function () {
            // Vars
            var newConversationView = this.get('newConversationView');

            newConversationView.toggleView();
        },

        /**
         * Called when user selects buddies via the new conversation view
         *
         * @private
         */
        _onNewConversationSelectedBuddies: function (event) {
            // Vars
            var newConversationView = this.get('newConversationView'),
                buddies = event.buddies || [];

            // Hide the new conversation view
            newConversationView.hideView();

            // Exactly one buddy was selected
            if (buddies.length === 1) {
                // Create single user chat conversation
                Y.fire('buddySelected', {
                    buddy: buddies[0]
                });
            }
            // More than one buddy was selected
            else if (buddies.length > 1) {
                // Create multi user chat conversation
                Y.fire('buddiesSelected', {
                    buddies: buddies
                });
            }
        },


        /**
         * Called whenever the user presses any kay in the browser
         *
         * @param event
         * @private
         */
        _onKeyPress: function (event) {
            // Vars
            var newConversationView = this.get('newConversationView');

            // User pressed ESC key
            if (event.keyCode === 27) {
                // Hide the new conversation view
                newConversationView.hideView();
            }
        },

        /**
         * Called whenever an error with connection occurred
         *
         * @private
         */
        _onConnectionError: function () {
            this.showError(Y.LIMS.Core.i18n.values.connectionErrorMessage);
        },

        /**
         * Called when there are no more connection errors
         *
         * @private
         */
        _onConnectionOK: function () {
            this.hideError();
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
                value: "conversation-feed"
            },

            /**
             * Container attached to the controller
             *
             * {Y.Node}
             */
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
             * Panel node
             *
             * {Node}
             */
            panel: {
                valueFn: function () {
                    return this.get('container').one('.panel');
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
                        model = this.get('model'),
                        activityIndicator = this.get('activityIndicator'),
                        buddyDetails = this.get('buddyDetails');

                    // Create view
                    return new Y.LIMS.View.ConversationFeedList({
                        container: container,
                        model: model,
                        activityIndicator: activityIndicator,
                        buddyDetails: buddyDetails
                    });
                }
            },

            /**
             * New conversation view
             *
             * {Y.LIMS.View.NewConversationView}
             */
            newConversationView: {
                valueFn: function () {
                    // Vars
                    var container = this.get('panel'),
                        view = new Y.LIMS.View.NewConversationView();

                    // Render the view
                    view.render();
                    // Add it to the container
                    container.append(view.get('container'));

                    return view;
                }
            },

            /**
             * Activity indicator node
             *
             * {Node}
             */
            activityIndicator: {
                valueFn: function () {
                    return this.get('container').one('.preloader');
                }
            },

            /**
             * Node with the new conversation button
             *
             * {Node}
             */
            newConversationButton: {
                valueFn: function () {
                    return this.get('container').one('.panel-button.new-conversation');
                }
            },

            /**
             * Cached global key up subscription
             *
             * {event}
             */
            keyUpSubscription: {
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
            },

            /**
             * Timer that is used to refresh date nodes periodically
             *
             * {timer}
             */
            timer: {
                value: null // to be set
            },

            /**
             * Length of the timer period that is used to refresh date nodes periodically
             *
             * {integer}
             */
            timerInterval: {
                value: 60000 // one minute
            }
        }
    });
