/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation View
 *
 * The class extends Y.View. It represents the view for a single conversation.
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationListView = Y.Base.create('conversationListView', Y.View, [Y.LIMS.View.ViewExtension], {

    // Specify an optional model to associate with the view.
    model: Y.LIMS.Model.MessageListModel,

    // Template used to define height monitor
    heightMonitorTemplate: '<pre class="chat-height-monitor"/>',

    // Template for the activity indicator
    readMoreActivityIndicator: '<div class="preloader read-more-preloader" />',

    // Template for participants list template
    participantsListTemplate: '<div class="participants-list" />',

    /**
     * Initializer
     *
     * @returns {Y.LIMS.View.ConversationListView}
     */
    initializer: function () {
        // Attach events
        this._attachEvents();

        return this;
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
     * Scrolls to the last message
     *
     * @private
     */
    scrollToBottom: function () {
        // Vars
        var position = this.get('panelContent').get('scrollHeight'); // Scroll to bottom

        // Scroll to given position
        this._scrollToPosition(position);
    },

    /**
     * Scrolls the list the particular message
     *
     * @param messageView
     */
    scrollToMessage: function (messageView) {
        // Vars
        var position;

        if (messageView) {
            // Position is the offset of the message from the top
            position = messageView.getTopOffset();
            // Scroll to the position
            this._scrollToPosition(position);
        }
    },

    /**
     * Sets focus to the text area
     */
    setTextFieldFocus: function () {
        // Vars
        var messageTextField = this.get('messageTextField');
        // Set the focus
        messageTextField.focus();
    },

    /**
     * Shows view
     */
    showView: function () {
        // Show panel view
        this._showPanelInput();
        // Show list view again
        this._showListView();
    },

    /**
     * Hides view
     */
    hideView: function () {
        // Hide panel input
        this._hidePanelInput();
        // Hide list view too
        this._hideListView();
    },

    /**
     * Shows participants list
     */
    showParticipantsList: function () {
        // Vars
        var participantsList = this.get('participantsList');

        if (participantsList.inDoc()) {
            participantsList.show();
        }
    },

    /**
     * Hides participants list
     */
    hideParticipantsList: function () {
        // Vars
        var participantsList = this.get('participantsList');

        if (participantsList.inDoc()) {
            participantsList.hide();
        }
    },

    /**
     * Attaches listener to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var messageTextField = this.get('messageTextField'),
            panelContent = this.get('panelContent'),
            model = this.get('model');

        // Attach events to text field
        if (messageTextField) {
            messageTextField.on('keyup', this._onMessageTextFieldKeyUp, this);
            messageTextField.on('focus', this._onMessageTextFieldFocus, this);
            messageTextField.on('blur', this._onMessageTextFieldBlur, this);
        }

        // Attach events to model
        model.after('messageAdded', this._onMessageAdded, this);
        model.after('messageError', this._onMessageError, this);
        model.after('messagesUpdated', this._onMessagesUpdated, this);

        // Attach events to panel content
        panelContent.on('scroll', this._onPanelContentScroll, this);
        panelContent.on('mouseenter', this._onPanelContentMouseEnter, this);
        panelContent.on('mouseleave', this._onPanelContentMouseLeave, this);
    },

    /**
     * Call this method when the view should attach mouse wheel event
     */
    _attachMouseWheel: function () {
        // Subscribe to the mouse wheel event
        this.set('mouseWheelSubscription', Y.on('mousewheel', this._onPanelContentMouseWheel, this));
    },

    /**
     * Call this method when the view should detach mouse wheel event
     */
    _detachMouseWheel: function () {
        // Vars
        var mouseWheelSubscription = this.get('mouseWheelSubscription');
        // Detach subscription
        mouseWheelSubscription.detach();
    },


    /**
     * Renders and adds a single message to the list
     *
     * @param message
     * @private
     * @return {Y.LIMS.View.ConversationItemView} newly created view
     */
    _addMessage: function (message) {

        // Vars
        var conversationItemView,                                           // Newly created conversation
            panelContentList = this.get('panelContentList'),        // The place where are messages will be rendered to
            conversationList = this.get('conversationItemViews');   // List of conversation views

        // New conversation item
        conversationItemView = new Y.LIMS.View.ConversationItemView({model: message});
        conversationList.push(conversationItemView);
        // Render it
        conversationItemView.render();
        // Append to list
        panelContentList.append(conversationItemView.get('container'));

        return conversationItemView;
    },

    /**
     * Renders participants list
     *
     * @private
     */
    _renderParticipantsList: function () {
        // Vars
        var participantsList = this.get('participantsList'),
            container = this.get('container'),
            model = this.get('model'),
            participants,
            innerHTML = '',
            index;

        // If the list is not yet rendered, add it to the container
        if (!participantsList.inDoc()) {
            // Hide the list, it's going to be shown whenever the user hovers over the title
            participantsList.hide();
            // Add list to container
            container.prepend(participantsList);
        }

        // Get participants of the conversation
        participants = model.get('participants');

        for (index = 0; index < participants.length; index++) {
            // Compose all participants names
            innerHTML += participants[index].get('fullName');
            // Add new line at the end of each participant except for the last one
            if (index < participants.length - 1) {
                innerHTML += '<br/>';
            }
        }

        // Set the HTML to the list
        participantsList.set('innerHTML', innerHTML);
    },

    /**
     * Renders the whole message list
     *
     * @private
     */
    _renderMessagesList: function (readMore, stopperId) {

        // Vars
        var instance = this,
            messageList = this.get('model').get('messageList'),
            messageView,
            animate = this.get('shouldAnimateList');

        // Hide the view and show it after it's rendered
        this._hideListView();
        // Reset content
        this._resetListView();

        // Create view for each message
        messageList.each(function (message) {

            messageView = instance._addMessage(message, false);

            if (readMore) {
                if (message.get('messageId') === stopperId) {
                    instance.set('stopperView', messageView);
                }
            }
        }, this);

        // Show it again and animate it if needed
        this._showListView(animate);

        if (readMore) {
            this.scrollToMessage(instance.get('stopperView'));
        } else {
            // Scroll to bottom so the user sees the message
            this.scrollToBottom();
        }
    },

    /**
     * Removes the whole content from  list view
     *
     * @private
     */
    _resetListView: function () {
        // Vars
        var panelContentList = this.get('panelContentList');
        // This will reset the content of conversation item view
        panelContentList.set('innerHTML', '');
        this.set('conversationItemViews', []);
    },

    /**
     * Shows list view
     *
     * @param animated true if the action should be animated
     * @private
     */
    _showListView: function (animated) {
        // Vars
        var panelContentList = this.get('panelContentList'),
            animation = new Y.Anim({
                node: panelContentList,
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
            panelContentList.setStyle('opacity', 0);
        }

        panelContentList.show();

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
        var panelContentList = this.get('panelContentList');
        // Hide list view
        panelContentList.hide();
    },

    /**
     * Shows panel input
     *
     * @private
     */
    _showPanelInput: function () {
        // Vars
        var panelInput = this.get('panelInput'),
            messageTextField = this.get('messageTextField'),
            animation;

        // Show panel only if it's hidden
        if (panelInput.hasClass('covered')) {

            // Remove the covered class
            panelInput.removeClass('covered');
            // Set the opacity to 0, just to be sure that it wasn't higher
            panelInput.setStyle('opacity', 0);
            // Remove the readonly property if it was set before
            messageTextField.removeAttribute('readonly');
            // Add a focus to the text field
            messageTextField.focus();

            // Create animation instance
            animation = new Y.Anim({
                node: panelInput,
                duration: 0.2,
                from: {
                    opacity: 0
                },
                to: {
                    opacity: 1
                }
            });

            // Run animation
            animation.run();
        }
    },

    /**
     * Hides input panel
     *
     * @private
     */
    _hidePanelInput: function () {
        // Vars
        var panelInput = this.get('panelInput'),
            messageTextField = this.get('messageTextField');

        // Set the opacity only. We don't want to show/hide the panel by calling
        // the show() or hide() method since this will remove it from the visible
        // are and brake the panel size. Thus we only manipulate the opacity
        panelInput.setStyle('opacity', 0);

        // Add the covered class for browsers that don't support opacity
        panelInput.addClass('covered');

        // Make the text field readonly since it's possible to continue writing even though
        // the message field is hidden.
        messageTextField.setAttribute('readonly', 'readonly');
        // This needs to be here, otherwise the message text field will keep the focus on some browsers
        // even though it's hidden. User thus would be able to write messages but nothing
        // would be visible to him.
        messageTextField.blur();
    },

    /**
     * Shows read more activity indicator
     *
     * @private
     */
    _showReadMoreActivityIndicator: function () {
        // Vars
        var indicator = this.get('readMoreActivityIndicator'),
            panelContentList = this.get('panelContentList');

        if (!indicator.inDoc()) {
            panelContentList.prepend(indicator);
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
     * Scrolls list to a particular position
     *
     * @param position
     * @private
     */
    _scrollToPosition: function (position) {
        // Vars
        var panelContent = this.get('panelContent');

        // Wait a second before we scroll to avoid blinking effect
        setTimeout(function () {
            panelContent.set('scrollTop', position);
        }, 1);
    },

    /**
     * Automatically counts the size of the message text field and resizes it if needed.
     * It also moves with the size of the panel content. As a result the whole window
     * has the same height only the content is resized.
     *
     * @private
     */
    _resizeMessageTextField: function () {
        // Vars
        var heightMonitor = this.get('heightMonitor').getDOM(),
            messageTextField = this.get('messageTextField').getDOM(),
            panelContent = this.get('panelContent'),
            panelContentHeightCached = this.get('panelContentHeightCached'),
            messageTextFieldHeightCached = this.get('messageTextFieldHeightCached'),
            maxHeight = this.get('maxMessageTextFieldHeight'),
            minHeight = this.get('minMessageTextFieldHeight'),
            panelInputHeight = this.get('panelInputHeight'),
            panelInputOffset = this.get('panelInputOffset'),
            messageContent = messageTextField.value,
            height,
            textNode;

        // Create a text node that has the same content like text field
        textNode = Y.config.doc.createTextNode(messageContent);

        // Empty height monitor
        heightMonitor.innerHTML = '';
        // Insert text node to the height monitor
        heightMonitor.appendChild(textNode);

        // Read the content from the height monitor
        messageContent = heightMonitor.innerHTML;

        // Add at least to spaces if the content is empty
        if (!messageContent.length) {
            messageContent = '&nbsp;&nbsp;';
        }

        // Internet Explorer uses break instead of new line
        if (Y.LIMS.Core.Properties.isIE) {
            messageContent = messageContent.replace(/\n/g, '<br />');
        }

        // Replace the updated content
        heightMonitor.innerHTML = messageContent;

        // Count the height it suppose to be something between min and max height
        height = Math.min(Math.max(heightMonitor.offsetHeight - 4, minHeight), maxHeight);

        // There is no need to do anything if the height wasn't changed
        if (height !== messageTextFieldHeightCached) {
            // Cache the new height
            this.set('messageTextFieldHeightCached', height);

            // Update message text field height
            messageTextField.style.height = height + 'px';
            // The parent node needs to be updated as well
            messageTextField.parentNode.style.height = (height + panelInputOffset) + 'px';
            // If we reached the maximum height start scrolling
            messageTextField.style.overflowY = (height === maxHeight) ? 'scroll' : 'hidden';

            // Update list height
            panelContent.setStyle('height', panelContentHeightCached + panelInputHeight - ((height + panelInputOffset)));
            // Scroll the list to bottom
            this.scrollToBottom();
        }
    },

    /**
     * Called when the message text field gains focus
     *
     * @param event
     * @private
     */
    _onMessageTextFieldFocus: function (event) {
        // Vars
        var hasMessageTextFieldFocus = this.get('hasMessageTextFieldFocus');

        // We don't want to fire it more than once. Thus there is no
        // need to call a body of the if statement again
        if (hasMessageTextFieldFocus === false) {
            // Set the focus flag
            this.set('hasMessageTextFieldFocus', true);
            // Fire an event
            this.fire('messageTextFieldFocus');
        }

        // Message text field was also updated
        this._onMessageTextFieldUpdated(event);
    },

    /**
     * Called when the message text field loses its focus
     *
     * @private
     */
    _onMessageTextFieldBlur: function () {
        // Vars
        var hasMessageTextFieldFocus = this.get('hasMessageTextFieldFocus');

        // We don't want to fire it more than once. Thus there is  no
        // need to call a body of the if statement again
        if (hasMessageTextFieldFocus === true) {
            // Set the focus flag
            this.set('hasMessageTextFieldFocus', false);
            // Fire an event
            this.fire('messageTextFieldBlur');
        }
    },

    /**
     * Called when the users presses any key while there is a focus on message text field
     *
     * @param event
     * @private
     */
    _onMessageTextFieldKeyUp: function (event) {
        // Message text field was also updated
        this._onMessageTextFieldUpdated(event);
    },

    /**
     * Called when a single message is added to the model
     *
     * @param event
     * @private
     */
    _onMessageAdded: function (event) {
        // Add a single message to the list
        this._addMessage(event.message);
        // Scroll to bottom so the user sees the message
        this.scrollToBottom();
    },

    /**
     * Called when the whole message list is updated
     *
     * @private
     */
    _onMessagesUpdated: function (event) {
        // Hide indicator if it wasn't already hidden
        this.get('activityIndicator').hide();
        // Render the list
        this._renderMessagesList(event.readMore, event.preStopperId);
        // Render participants
        this._renderParticipantsList();
        // Since the list is already rendered there is no need to
        // animate any other addition to the list
        this.set('shouldAnimateList', false);
    },

    /**
     * Called when there is an error during message delivery
     *
     * @private
     */
    _onMessageError: function () {
        // Scroll to bottom otherwise the error wouldn't be visible
        this.scrollToBottom();
    },

    /**
     * Called when the user scrolls the panel content
     *
     * @private
     */
    _onPanelContentScroll: function () {
        // Vars
        var scrollPosition = this.get('panelContent').get('scrollTop'),
            model = this.get('model'),
            instance = this;

        // User has reached the top by scrolling and there is still something
        // we can read from the model
        if (scrollPosition === 0 && !model.get('reachedTop')) {
            // Show the preloader
            this._showReadMoreActivityIndicator();
            // Load the model
            model.load({readMore: true}, function () {
                instance._hideReadMoreActivityIndicator();
            });
        }
    },

    /**
     * Called when the user enters panel content with the mouse cursor
     *
     * @private
     */
    _onPanelContentMouseEnter: function () {
        // Attach the mouse wheel event since we need to track
        // users scrolling
        this._attachMouseWheel();
    },

    /**
     * Called when the user leaves panel content with the mouse cursor
     *
     * @private
     */
    _onPanelContentMouseLeave: function () {
        // Detach the mouse wheel event since there is no need to track
        // scrolling since the cursor is not above the panel content
        this._detachMouseWheel();
    },

    /**
     * Called when the user scrolls with mouse wheel over the panel content
     *
     * @param event
     * @return {*}
     * @private
     */
    _onPanelContentMouseWheel: function (event) {
        // Vars
        var panelContent = this.get('panelContent');
        // Prevent scrolling of the whole window
        return this.preventScroll(event, panelContent);
    },

    /**
     * Called whenever the message field is updated
     *
     * @param event
     * @private
     */
    _onMessageTextFieldUpdated: function (event) {
        var textField = this.get('messageTextField'),
            value;

        // Get rid of new line characters
        value = textField.get('value').replace(/\n|\r/gim, '');
        // Get rid of empty spaces
        value = Y.Lang.trim(value);

        // Send message on enter
        if (event.keyCode === 13 && !event.shiftKey && value.length) {
            event.preventDefault();
            // Empty text field
            textField.set('value', '');
            // Fire an event that message was submitted
            this.fire('messageSubmitted', {
                message: value
            });
        }

        // Resize
        this._resizeMessageTextField();
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Container node
         *
         * {Y.Node}
         */
        container: {
            value: null
        },

        /**
         * Conversation model
         *
         * {Y.LIMS.Model.ConversationModel}
         */
        model: {
            value: null
        },

        /**
         * An array that holds all conversations
         *
         * {array}
         */
        conversationItemViews: {
            value: []
        },

        /**
         * View that contains stopper message
         *
         * {Y.LIMS.View.ConversationItemView}
         */
        stopperView: {
            value: null // to be set
        },

        /**
         * Panel content node
         *
         * {Y.Node}
         */
        panelContent: {
            valueFn: function () {
                return this.get('container').one('.panel-content');
            }
        },

        /**
         * Cached value of panel content height
         *
         * {integer}
         */
        panelContentHeightCached: {
            value: 250
        },

        /**
         * Participants list node
         *
         * {Node}
         */
        participantsList: {
            valueFn: function () {
                return Y.Node.create(this.participantsListTemplate);
            }
        },

        /**
         * Message list node
         *
         * {Y.Node}
         */
        panelContentList: {
            valueFn: function () {
                return this.get('container').one('.panel-content ul');
            }
        },

        /**
         * Activity indicator node
         *
         * {Y.Node}
         */
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one('.preloader');
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
         * Set to true if the appearance of elements in the list should be animated
         *
         * {boolean}
         */
        shouldAnimateList: {
            value: true
        },

        /**
         * Message text field node
         *
         * {Y.Node}
         */
        messageTextField: {
            valueFn: function () {
                return this.get('container').one('.panel-input textarea');
            }
        },

        /**
         * True if the message text field has focus, false if it's blurred
         *
         * {boolean}
         */
        hasMessageTextFieldFocus: {
            value: false // default value
        },

        /**
         * Panel input node that holds message text field
         *
         * {Node}
         */
        panelInput: {
            valueFn: function () {
                return this.get('container').one('.panel-input');
            }
        },

        /**
         * Height of the panel input node i.e. the parent node of message text field
         *
         * {integer}
         */
        panelInputHeight: {
            value: 34
        },

        /**
         * Offset between the panel input and message text field
         *
         * {integer}
         */
        panelInputOffset: {
            value: 12
        },

        /**
         * Cached value of message text field height
         *
         * {integer}
         */
        messageTextFieldHeightCached: {
            value: 0 // to be set
        },

        /**
         * Maximal possible height of message text field
         *
         * {integer}
         */
        maxMessageTextFieldHeight: {
            value: 64
        },

        /**
         * Minimal possible height of message text field
         *
         * {integer}
         */
        minMessageTextFieldHeight: {
            value: 14
        },

        /**
         * Event subscription for the mouse wheel event
         *
         * {Subscription}
         */
        mouseWheelSubscription: {
            value: null
        },

        /**
         * Height monitor is used to calculate proper size of the message text field
         *
         * {Node}
         */
        heightMonitor: {
            valueFn: function () {
                var heightMonitorNode = Y.Node.create(this.heightMonitorTemplate);
                // Set the same width as message text field
                heightMonitorNode.setStyle('width', 248);
                // Add it to container, don't worry css will take it away from the visible window
                heightMonitorNode.appendTo(this.get('container'));

                return heightMonitorNode;
            }
        }
    }
});

