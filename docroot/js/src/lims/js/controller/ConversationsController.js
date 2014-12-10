/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Main Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.ConversationsController = Y.Base.create('conversationsController',
    Y.Base, [Y.LIMS.Controller.ControllerExtension], {

        // This customizes the HTML used for this view's container node.
        containerTemplate: '<li class="conversation">',

        // The template property holds the contents of the #lims-conversation-template
        // element, which will be used as the HTML template for conversation controller.
        conversationTemplate: Y.one('#limsmuc-conversation-template').get('innerHTML'),

        // The template property holds the contents of the #lims-conversation-toggle-template
        // element, which will be used as the HTML template for the conversation toggle controller.
        conversationToggleTemplate: Y.one('#limsmuc-conversation-toggle-template').get('innerHTML'),


        // The initializer runs when a MainController instance is created, and gives
        // us an opportunity to set up all sub controllers
        initializer: function () {
            // Bind to already rendered conversations
            this._bindConversations();
            // Attach events
            this._attachEvents();
            // Layout
            this._layoutSubviews();

            // Fire an event that the initialization has been finished
            Y.fire('initializationFinished');
        },

        /**
         * Attach events
         *
         * @private
         */
        _attachEvents: function () {
            // Vars
            var conversationList = this.get('conversationList'),
                instance = this;

            // Local
            conversationList.on('conversationsUpdated', this._onConversationsUpdated, this);

            // Buddy selected in group
            Y.on('buddySelected', this._onBuddySelected, this);
            Y.on('buddiesSelected', this._onBuddiesSelected, this);
            Y.on('conversationSelected', this._onConversationSelected, this);
            Y.on('conversationClosed', this._onConversationClosed, this);
            // Session expired
            Y.on('userSessionExpired', this._onSessionExpired, this);

            // Chat enabled/disabled
            Y.on('chatEnabled', this._onChatEnabled, this);
            Y.on('chatDisabled', this._onChatDisabled, this);

            // Note: "this" cannot be passed as a third parameter for a weird reason
            Y.on('windowresize', function () {
                instance._onWindowResize();
            });
        },

        /**
         * Opens new conversation
         *
         * @param conversationId {string} id of the conversation
         * @param title {string} title of the conversation
         * @param participants [] an array of participants
         * @return {Y.LIMS.Controller.SingleUserConversationViewController}
         * @private
         */
        _openConversation: function (conversationId, title, participants) {
            // Vars
            var map = this.get('conversationMap'),                  // Map that holds all conversation controllers
                buddyDetails = this.get('buddyDetails'),            // Currently logged user
                conversationModel,                                  // Model passed to controller
                conversationContainer,                              // Container node passed to controller
                conversationList = this.get('conversationList'),    // Holds all conversation models
                settings = this.get('settings'),                    // Settings of logged user
                properties = this.get('properties'),                // Portlet properties
                notification = this.get('notification'),            // Notification handler
                controller,                                         // Controller (selected or newly created)
                instance = this;

            // Create new model
            conversationModel = new Y.LIMS.Model.ConversationModel({
                conversationId: conversationId,
                creator: buddyDetails,
                participants: participants,
                title: title,
                serverTimeOffset: properties.getServerTimeOffset(),
                isCreated: false
            });
            // Add model to list
            conversationList.add(conversationModel);

            // Create new container node
            conversationContainer = Y.Node.create(this.containerTemplate);
            // Set attributes
            conversationContainer.setAttribute('data-conversationId', conversationId);
            // Set content from template
            conversationContainer.set('innerHTML',
                Y.Lang.sub(this.conversationTemplate, {
                    conversationTitle: conversationModel.get('title'),
                    triggerTitle: conversationModel.get('title'),
                    unreadMessages: conversationModel.get('unreadMessagesCount')
                })
            );

            // Create new single user conversation controller
            controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                buddyDetails: buddyDetails,
                container: conversationContainer,
                controllerId: conversationId,
                model: conversationModel,
                settings: settings,
                properties: properties,
                notification: notification
            });

            // Remove controller from map if it's unloaded from the screen
            controller.on('panelDidUnload', function (event) {
                delete map[event.controllerId];
                // Layout subviews
                instance._layoutSubviews();
            });

            // Add controller to map
            map[conversationId] = controller;

            // Add panel container to parent container
            this.get('container').append(conversationContainer);

            // Layout subviews
            this._layoutSubviews();

            // Save the model, thanks to that the conversation will be created on server too.
            conversationModel.save();

            return controller;
        },

        /**
         * There may exist opened conversations which are already rendered in jsp and not via javascript.
         * This function will bind to such conversations and create appropriate javascript objects.
         *
         * @private
         */
        _bindConversations: function () {
            // Find all already rendered conversations
            var conversationNodes = this.get('conversationNodes'),  // List of already rendered conversation nodes
                map = this.get('conversationMap'),                  // Map that holds all conversation controllers
                buddyDetails = this.get('buddyDetails'),            // Currently logged user
                conversationId,                                     // Id of the conversation
                conversationType,                                   // Type of the conversation
                conversationTitle,                                  // Title of the conversation
                etag,                                               // Conversation etag
                unreadMessagesCount,                                // Unread messages count
                settings = this.get('settings'),                    // Settings of logged user
                properties = this.get('properties'),                // Portlet properties
                conversationModel,                                  // Model which will be attached to controller
                conversationList = this.get('conversationList'),    // List of conversations
                notification = this.get('notification'),            // Notification handler
                controller,                                         // Bind controller
                instance = this;

            // Create js objects for each node
            conversationNodes.each(function (conversationNode) {
                // Since the conversation was already rendered it contains a conversation ID which we need get
                // from the attribute on conversation node
                conversationId = conversationNode.attr('data-conversationId');
                conversationType = conversationNode.attr('data-conversationType');
                etag = parseInt(conversationNode.attr('data-etag'), 10);
                conversationTitle = conversationNode.attr('data-conversationTitle');
                unreadMessagesCount = conversationNode.attr('data-unreadMessagesCount');

                // Bind controller only if it's not in the map
                if (!map.hasOwnProperty(conversationId)) {

                    // Create new conversation model
                    conversationModel = new Y.LIMS.Model.ConversationModel({
                        conversationId: conversationId,
                        conversationType: conversationType,
                        title: conversationTitle,
                        etag: etag,
                        creator: buddyDetails,
                        unreadMessagesCount: unreadMessagesCount,
                        serverTimeOffset: properties.getServerTimeOffset()
                    });
                    // Add conversation model to list
                    conversationList.add(conversationModel);

                    // Create new single user conversation and add it to the list
                    controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                        // Id of controller is the id of conversation. Thanks to this we can
                        // set the currently opened panel.
                        controllerId: conversationId,
                        container: conversationNode,
                        model: conversationModel,
                        buddyDetails: buddyDetails,
                        settings: settings,
                        properties: properties,
                        notification: notification
                    });

                    // Remove controller from map if it's unloaded from the screen
                    controller.on('panelDidUnload', function (event) {
                        delete map[event.controllerId];
                        // Layout subviews
                        instance._layoutSubviews();
                    });

                    // Add to map
                    map[conversationId] = controller;
                }
            });
        },

        /**
         * Returns size of the conversation nodes
         *
         * @return {number}
         * @private
         */
        _conversationNodesSize: function () {
            // Vars
            var size = 0,
                averageConversationNodeSize = this.get('averageConversationNodeSize'),
                conversationNodes = this.get('openedConversationNodes');

            conversationNodes.each(function (conversationNode) {
                if (!Y.LIMS.Core.Util.isHidden(conversationNode)) {
                    size += averageConversationNodeSize;
                }
            });

            return size;
        },

        /**
         * Returns last conversation node that is not currently hidden.
         * Returns null if all conversation nodes are hidden
         *
         * @return {Node|null}
         * @private
         */
        _lastNotHiddenConversation: function () {
            // Vars
            var conversationNodes = this.get('openedConversationNodes'),
                conversationNode,
                index;

            for (index = conversationNodes.size() - 1; index >= 0; index--) {
                // Get the node from the list
                conversationNode = conversationNodes.item(index);
                // Check if the node is not hidden, if so return it
                if (!Y.LIMS.Core.Util.isHidden(conversationNode)) {
                    return conversationNode;
                }
            }

            // No visible conversations available
            return null;
        },

        /**
         * Returns first conversation node that is currently hidden.
         * Returns null if there is no hidden conversations.
         *
         * @return {Node|null}
         * @private
         */
        _firstHiddenConversation: function () {
            // Vars
            var hiddenConversations = this._hiddenConversations();

            if (hiddenConversations.size() > 0) {
                return hiddenConversations.item(0);
            }

            return null;
        },

        /**
         * Returns a Node List of the conversation that are hidden
         *
         * @return {NodeList}
         * @private
         */
        _hiddenConversations: function () {
            // Vars
            var conversationNodes = this.get('openedConversationNodes');

            return conversationNodes.filter(function (conversationNode) {
                return Y.LIMS.Core.Util.isHidden(conversationNode);
            });
        },

        /**
         * Returns a Node List of the conversation that are not hidden
         *
         * @return {NodeList}
         * @private
         */
        _visibleConversations: function () {
            // Vars
            var conversationNodes = this.get('openedConversationNodes');

            return conversationNodes.filter(function (conversationNode) {
                return !Y.LIMS.Core.Util.isHidden(conversationNode);
            });
        },

        /**
         * Recalculates the layout
         *
         * @recursive
         * @private
         */
        _layoutSubviews: function (recursionDepth) {
            // Vars
            var depth = recursionDepth || 0,
                winWidth = Y.one(Y.config.win).get('winWidth'), // Width of the whole document
                padding = this.get('barPadding'),               // Extra padding on both left and right side
                extraWidth = this.get('mandatoryPanelsWidth'),  // Width of other than conversation panels
                staticPart,                                     // The part that has a static size (other panels)
                dynamicPart,                                    // The part that has a dynamic size (conversations)
                conversationNodeSizes,                          // Size of all rendered conversation nodes
                ratio,                                          // Ratio between the node sizes and dynamic part
                hiddenConversations,                            // List of hidden conversation nodes
                visibleConversations,                           // List of visible conversation nodes
                firstHiddenConversation,                        // First conversation node that is hidden
                lastVisibleConversation,                        // Last conversation node that is visible
                controller,                                     // Conversation controller
                averageConversationNodeSize = this.get('averageConversationNodeSize'),  // Get the node size
                conversationToggleController = this.get('conversationToggleController'),
                properties = this.get('properties'),
                willFit;    // True if the conversation will fit into the dynamic part while making the window bigger


            // Don't layout subviews if chat is not enabled. The method will be called
            // whenever the chat is enabled
            if (!properties.isChatEnabled()) {
                return;
            }

            // This is just a defensive programming check. Since the layoutSubviews method can call itself
            // in a recursion it is possible that when there is a bug the method may call itself forever
            // and freeze the browser window. To be sure that nothing like that happens the layoutSubview method
            // will pass a depth parameter whenever it's called from within itself. You don't need to specify
            // the recursionDepth parameter when you call the method.
            if (depth > 100) {
                // The maximal depth was exceeded so end up here
                return;
            }

            // Calculate static part. This is a part that has a static width.
            // In other words no matter what conversations will be added/removed
            // the count of panels within the static part will remain the same as well their size.
            staticPart = padding + extraWidth;

            // Calculate dynamic part. This is a part which content may have variable count
            // or size. In other words the dynamic part holds all conversation windows
            // and the conversation toggle.
            dynamicPart = winWidth - staticPart;

            // Just to protect the equilibrium in universe and all kitten all over the world
            // be sure that we are not about to divide by zero
            if (dynamicPart <= 0) {
                dynamicPart = 1; // Prevent dividing by zero
            }

            // Sum all rendered nodes sizes
            conversationNodeSizes = this._conversationNodesSize();

            // Count ratio between the size and dynamic part
            ratio = conversationNodeSizes / dynamicPart;

            // Get all the hidden conversations
            hiddenConversations = this._hiddenConversations();
            visibleConversations = this._visibleConversations();

            // If the ratio is less than zero it means that the size of all conversation nodes is less
            // than the size of the dynamic part size. In other words it is possible that some conversation
            // might be visible again
            if (ratio <= 1) {

                // Check if the conversation will fit. We simply take the size of all visible conversation
                // nodes and add the size of the extra conversation node. Than we check if the conversation
                // node will fit
                willFit = conversationNodeSizes + averageConversationNodeSize < dynamicPart;

                // If the conversation will fit and there is at least one conversation which is hidden
                if (willFit && hiddenConversations.size() > 0) {

                    // Get the first hidden conversation
                    firstHiddenConversation = this._firstHiddenConversation();

                    // And make it visible again
                    if (firstHiddenConversation) {

                        // Show the node
                        Y.LIMS.Core.Util.show(firstHiddenConversation);

                        // Find the controller related to the node
                        controller = this._getControllerFromMap(firstHiddenConversation.attr('data-conversationId'));
                        // Add it to the toggle only if the controller exists
                        if (controller) {
                            // Add the controller to toggle
                            conversationToggleController.removeConversation(controller.get('model'));
                        }

                        // Recalculate the size of the conversations
                        hiddenConversations = this._hiddenConversations();

                        // If there are no hidden conversation the conversation toggle
                        // is not needed anymore
                        if (hiddenConversations.size() === 0) {
                            this._hideConversationToggle();
                        }

                        // Call recursion to check if more conversation should be visible again
                        this._layoutSubviews(++depth);
                    }
                }

            }
            // The window is smaller then the size of the lims bar so we need to hide some conversations
            else {

                // Get the last conversation in the list that is not hidden
                lastVisibleConversation = this._lastNotHiddenConversation();

                // At least one conversation must remain visible
                if (lastVisibleConversation && visibleConversations.size() > 1) {

                    // Hide the node
                    Y.LIMS.Core.Util.hide(lastVisibleConversation);

                    // Find the controller related to the node
                    controller = this._getControllerFromMap(lastVisibleConversation.attr('data-conversationId'));
                    // Add it to the toggle only if the controller exists
                    if (controller) {
                        // Add the controller to toggle
                        conversationToggleController.addConversation(controller.get('model'));
                    }

                    // Since there is at least one conversation hidden show the conversation toggle
                    this._showConversationToggle();

                    // Call recursion to check if more conversation should be hidden
                    this._layoutSubviews(++depth);
                }
            }
        },

        /**
         * Starts poller which periodically refreshes the conversation list
         *
         * @private
         */
        _startPolling: function () {
            // Vars
            var conversationList = this.get('conversationList'),
                poller = this.get('poller');

            // Register model to the poller
            poller.register('conversationController:model', new Y.LIMS.Core.PollerEntry({
                model: conversationList,        // Model that will be periodically refreshed
                interval: 7000,                 // 7 seconds period
                maxInterval: 15000,             // 15 seconds period
                minInterval: 7000,              // 7 seconds period
                connectionMonitor: true         // Fires connection success/error event
            }));
        },

        /**
         * Stops poller which periodically refreshes the conversation list
         *
         * @private
         */
        _stopPolling: function () {
            // Vars
            var poller = this.get('poller');
            // Pause
            poller.unregister('conversationController:model');
        },

        /**
         * Shows conversation toggle controller
         *
         * @private
         */
        _showConversationToggle: function () {
            // Vars
            var controller = this.get('conversationToggleController'),
                container = this.get('container');

            container.append(controller.get('container'));
        },

        /**
         * Hides conversation toggle controller
         *
         * @private
         */
        _hideConversationToggle: function () {
            // Vars
            var controller = this.get('conversationToggleController');

            if (controller.get('container').inDoc()) {
                controller.get('container').remove();
            }
        },

        /**
         * Adds a controller to the proper position
         *
         * @param controller {Y.LIMS.Controller.SingleUserConversationViewController}
         * @private
         */
        _positionController: function (controller) {
            // Vars
            var toggleController = this.get('conversationToggleController'),
                lastConversationNode,
                lastConversationController,
                model = controller.get('model'),
                isHidden = controller.isHidden();

            // Toggle is visible that means that the controller given in parameter will be set as
            // a last visible controller right after the conversation toggle only if the controller
            // is hidden within the toggle
            if (toggleController.isVisible() && isHidden) {

                // Get the last conversation's node that is visible before the toggle
                lastConversationNode = this._lastNotHiddenConversation();
                // Add the conversation node before the last visible conversation
                lastConversationNode.insert(controller.get('container'), 'before');

                // Show the container since it might have been hidden
                Y.LIMS.Core.Util.show(controller.get('container'));

                // If the conversation is in the toggle remove it from there. Since it's not needed anymore
                toggleController.removeConversation(controller.get('model'));

                // Find the controller related to the node. We need this because we need to send
                // a request to server that will switch the position of those two controllers
                // on a server side as well. Thanks to that if the user refreshes page
                // the newly positioned controller will remain at its position.
                lastConversationController = this._getControllerFromMap(
                    lastConversationNode.attr('data-conversationId')
                );

                // Only if the controller exists
                if (lastConversationController) {
                    model.switchConversations(lastConversationController.get('model'));
                }

                // Layout subviews again since the last conversation node is still presented and
                // it hasn't been added to the toggle. However if we call the layout subviews method
                // now everything will be recalculated again.
                this._layoutSubviews();
            }
        },

        /**
         * Returns controller stored in map. Returns null if no controller was found
         *
         * @param controllerId
         * @return {Y.LIMS.Controller.SingleUserConversationViewController|null}
         * @private
         */
        _getControllerFromMap: function (controllerId) {
            // Vars
            var map = this.get('conversationMap');   // Map that holds all conversation controllers

            // No such controller was found
            if (!map.hasOwnProperty(controllerId)) {
                return null;
            }

            return map[controllerId];
        },

        /**
         * Called when the user selects conversation from the conversation feed list.
         *
         * @param event
         * @private
         */
        _onConversationSelected: function (event) {

            // Vars
            var conversation = event.conversation,                  // Take conversation from the event
                map = this.get('conversationMap'),                  // Map that holds all conversation controllers
                conversationId,                                     // Id of the conversation passed to controller
                controller;                                         // Controller (selected or newly created)

            // Generate conversation id
            conversationId = conversation.get('conversationId');

            // Such conversation is already in the map
            if (map.hasOwnProperty(conversationId)) {
                // Find it, later on we will present it to the user
                controller = this._getControllerFromMap(conversationId);
            }
            // No such conversation
            else {
                controller = this._openConversation(conversationId, conversation.get('title'), []);
            }

            // Only if the controller exists
            if (controller) {
                // Add the controller to the proper position
                this._positionController(controller);

                // At the end show the controller to the user
                controller.presentViewController();
            }
        },

        /**
         * Called whenever the buddy is selected from conversations.
         * If a conversation with the particular buddy already exists we
         * just open the conversation for the user. If not, we first create
         * new conversation and then present it to the user.
         *
         * @param event
         * @private
         */
        _onBuddySelected: function (event) {

            // Vars
            var buddy = event.buddy,                                // Take buddy from the event
                map = this.get('conversationMap'),                  // Map that holds all conversation controllers
                buddyDetails = this.get('buddyDetails'),            // Currently logged user
                conversationId,                                     // Id of the conversation passed to controller
                controller;                                         // Controller (selected or newly created)

            // Generate conversation id
            conversationId = Y.LIMS.Model.ConversationModelUtil.generateSUCConversationId(
                buddyDetails.get('screenName'), buddy.get('screenName')
            );

            // Such conversation is already in the map
            if (map.hasOwnProperty(conversationId)) {
                // Find it, later on we will present it to the user
                controller = this._getControllerFromMap(conversationId);
            }
            // No such conversation
            else {
                controller = this._openConversation(conversationId, buddy.get('fullName'), [buddy]);
            }

            // Only if the controller exists
            if (controller) {
                // Add the controller to the proper position
                this._positionController(controller);
                // At the end show the controller to the user
                controller.presentViewController();
            }
        },

        /**
         * Called whenever a list of buddies is selected from conversations.
         * A new multi user conversation should thus be created
         *
         * @param event
         * @private
         */
        _onBuddiesSelected: function (event) {
            // Vars
            var conversationId,
                buddies = event.buddies || false,
                controller,
                buddyDetails = this.get('buddyDetails');

            // Filter out the currently logged user
            buddies = Y.LIMS.Model.ConversationModelUtil.filterBuddy(buddies, buddyDetails);

            // Only if the required parameter was set
            if (buddies) {

                // Generate conversation ID
                conversationId = Y.LIMS.Model.ConversationModelUtil.generateMUCConversationId(buddies, buddyDetails);

                // Create controller
                controller = this._openConversation(
                    conversationId, Y.LIMS.Model.ConversationModelUtil.generateMUCTitle(buddies), buddies
                );

                // Add the controller to the proper position
                this._positionController(controller);

                // Open the controller
                controller.presentViewController();
            }
        },

        /**
         * Called when the user attempts to close the conversation
         * @private
         */
        _onConversationClosed: function (event) {
            // Vars
            var model = event.conversation || null,
                conversationToggleController = this.get('conversationToggleController'),
                controller;

            // Parameters check
            if (!model) {
                return;
            }

            // Get the controller from the map
            controller = this._getControllerFromMap(model.get('conversationId'));

            // Only if the controller exists
            if (controller) {
                // Close the controller
                controller.getPanel().close();
                // Add the controller to toggle
                conversationToggleController.removeConversation(model);
                // Layout subviews
                this._layoutSubviews();
            }

        },

        /**
         * Called whenever we receive a notification from long pooling that conversations have been updated
         *
         * @private
         */
        _onConversationsUpdated: function () {
            // Vars
            var map = this.get('conversationMap'),                      // Map that holds all conversation controllers
                controller,                                             // Controller from map or newly created
                instance = this,                                        // This
                buddyDetails = this.get('buddyDetails'),                // Currently logged user
                conversationList = this.get('conversationList'),        // Holds all conversation models
                conversationContainer,                                  // Container node passed to controller
                container = this.get('container'),                      // Container of all conversations
                notification = this.get('notification'),                // Notification handler
                settings = this.get('settings'),                        // Settings of logged user
                properties = this.get('properties'),                    // Portlet properties
                conversationId;                                         // Id of the conversation passed to controller

            // For each conversation check if new controller should be created if some
            // of the participants send a message to the user. Or if we should only update
            // existing conversation
            conversationList.each(function (conversationModel) {
                // Get the conversation id from model
                conversationId = conversationModel.get('conversationId');

                // Controller exists
                if (map.hasOwnProperty(conversationId)) {
                    // Find the controller
                    controller = instance._getControllerFromMap(conversationId);

                    // Only if the controller exists
                    if (controller) {
                        controller.updateModel(conversationModel);
                    }
                }
                // Create new controller from conversation model only if there is
                // a pending message. This check needs to be here because it is possible
                // that the user closed the conversation but in a mean time received
                // the long poll update. This will open the conversation again. Obviously we
                // don't want that.
                else if (conversationModel.get('unreadMessagesCount') !== 0) {
                    // Add creator to model
                    conversationModel.set('creator', buddyDetails);
                    // etag must be send to 0 because we want the controller
                    // to refresh the message list. Since we have received
                    // model with already set etag we need to set it to 0 here.
                    conversationModel.set('etag', 0);
                    // Add it to list
                    conversationList.add(conversationModel);

                    // Create container node
                    conversationContainer = Y.Node.create(instance.containerTemplate);
                    // Set attributes
                    conversationContainer.setAttribute('data-conversationId', conversationId);
                    // Set content from template
                    conversationContainer.set('innerHTML',
                        Y.Lang.sub(instance.conversationTemplate, {
                            conversationTitle: conversationModel.get('title') || Y.LIMS.Core.i18n.values.conversationLoading,
                            triggerTitle: conversationModel.get('title') || Y.LIMS.Core.i18n.values.conversationLoading,
                            unreadMessages: conversationModel.get('unreadMessagesCount') || 0
                        })
                    );

                    // Create new single user conversation controller
                    controller = new Y.LIMS.Controller.SingleUserConversationViewController({
                        buddyDetails: buddyDetails,
                        container: conversationContainer,
                        controllerId: conversationId,
                        model: conversationModel,
                        settings: settings,
                        properties: properties,
                        notification: notification
                    });

                    // Remove controller from map if it's unloaded from the screen
                    controller.on('panelDidUnload', function (event) {
                        delete map[event.controllerId];
                        // Layout subviews
                        instance._layoutSubviews();
                    });

                    // Add controller to map
                    map[conversationId] = controller;

                    // Load conversation model (e.g. messages, etc.)
                    conversationModel.load(function (err) {
                        if (!err) {
                            // Add panel container to parent container
                            container.append(conversationContainer);
                            // Show the controller to the user
                            controller.showViewController();
                            // Layout subviews
                            instance._layoutSubviews();
                            // We have created a controller based on long polling message.
                            // We thus need to notify the user about received messages.
                            notification.notify(conversationModel.get('lastMessage'));
                        }
                    });
                }
            });
        },

        /**
         * Called whenever the user session expires
         *
         * @private
         */
        _onSessionExpired: function () {
            // Stop poller
            this._stopPolling();
        },

        /**
         * Called whenever the user enables the chat
         *
         * @private
         */
        _onChatEnabled: function () {
            // Re-enable polling
            this._startPolling();
            // Layout subviews
            this._layoutSubviews();
        },

        /**
         * Called whenever the user disables the chat
         *
         * @private
         */
        _onChatDisabled: function () {
            // Disable the poller
            this._stopPolling();
        },

        /**
         * Called when the window is resized
         *
         * @private
         */
        _onWindowResize: function () {
            this._layoutSubviews();
        }

    },
    {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        ATTRS: {

            /**
             * Main container node
             *
             * {Node}
             */
            container: {
                value: null // to be set
            },

            /**
             * Map that holds all currently opened conversation controllers
             *
             * {object}
             */
            conversationMap: {
                value: {} // default value
            },

            /**
             * An instance of currently logged user details
             *
             * {Y.LIMS.Model.BuddyModelItem}
             */
            buddyDetails: {
                value: null
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
             * Settings of the currently logged user
             *
             * {Y.LIMS.Model.SettingsModel}
             */
            settings: {
                value: null // to be set
            },

            /**
             * Conversation toggle controller
             *
             * {Y.LIMS.Controller.ConversationToggleViewController}
             */
            conversationToggleController: {
                valueFn: function () {
                    // Vars
                    var container = Y.Node.create(this.conversationToggleTemplate),
                        properties = this.get('properties');

                    return new Y.LIMS.Controller.ConversationToggleViewController({
                        container: container,
                        properties: properties
                    });
                }
            },

            /**
             * Average size of the conversation node
             *
             * {number}
             */
            averageConversationNodeSize: {
                value: 150 // default value
            },

            /**
             * Padding on both size of the window for the lims bar
             *
             * {number}
             */
            barPadding: {
                value: 150 // default value
            },

            /**
             * Width of the panels that are visible no matter what happens
             *
             * {number}
             */
            mandatoryPanelsWidth: {
                value: 300 // default value
            },

            /**
             * Conversation list model
             *
             * {Y.LIMS.Model.ConversationListModel}
             */
            conversationList: {
                valueFn: function () {
                    return new Y.LIMS.Model.ConversationListModel();
                }
            },

            /**
             * Notification object responsible for the incoming message notification
             *
             * {Y.LIMS.Core.Notification}
             */
            notification: {
                value: null // to be set
            },

            /**
             * List of already rendered conversation nodes
             *
             * {array}
             */
            conversationNodes: {
                valueFn: function () {
                    return this.get('container').all('.conversation');
                }
            },

            /**
             * List of conversations rendered conversation nodes that are not closed
             *
             * {array}
             */
            openedConversationNodes: {
                getter: function () {
                    return this.get('container').all('.conversation:not(.closed)');
                }
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
    }
)
;
