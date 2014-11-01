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
 * Add More Option View
 *
 * The class extends Y.View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.AddMoreOption = Y.Base.create('addMoreOption', Y.View, [], {

    // The template property holds the contents of the #lims-conversation-option-add-more-template
    // element, which will be used as the HTML template for the leave conversation option view
    template: Y.one('#lims-conversation-option-add-more-template').get('innerHTML'),

    // Template for the error message
    errorMessageTemplate: '<div class="error-message" />',

    // Template for the activity indicator
    activityIndicatorTemplate: '<div class="preloader" />',

    /**
     * Called after the initialization of the instance
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Shows the view
     */
    showView: function () {
        // Vars
        var animation = this.get('openAnimation');

        animation.run();
    },

    /**
     * Hides the view
     */
    hideView: function () {
        // Vars
        var animation = this.get('closeAnimation');

        // Hide the error message if it was rendered
        this._hideErrorMessage();

        animation.run();
    },

    /**
     * Shows/Hides view
     */
    toggleView: function () {
        // Vars
        var isHidden = this.get('isHidden');

        if (isHidden) {
            this.showView();
        } else {
            this.hideView();
        }
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var cancelButton = this.get('cancelButton'),
            addButton = this.get('addButton'),
            model = this.get('model');

        // Local event
        cancelButton.on('click', this._onCancelButtonClick, this);
        addButton.on('click', this._onAddButtonClick, this);
        model.on('readSuccess', this._onModelReadSuccess, this);
    },

    /**
     * Adds action buttons to the buttons node
     *
     * @private
     */
    _addButtons: function () {
        // Vars
        var cancelButton = this.get('cancelButton'),
            addButton = this.get('addButton'),
            buttons = this.get('buttons');

        buttons.append(cancelButton);
        buttons.append('&nbsp;');
        buttons.append(addButton);
    },

    /**
     * Removes action buttons
     *
     * @private
     */
    _removeButtons: function () {
        // Vars
        var cancelButton = this.get('cancelButton'),
            addButton = this.get('addButton');

        // Remove buttons
        cancelButton.remove();
        addButton.remove();
    },

    /**
     * Shows activity indicator
     *
     * @private
     */
    _showActivityIndicator: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator'),
            buttons = this.get('buttons');

        // Add activity indicator to the buttons node if it's not already there
        if (!activityIndicator.inDoc()) {
            buttons.append(activityIndicator);
        }
    },

    /**
     * Hides activity indicator
     *
     * @private
     */
    _hideActivityIndicator: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator');

        // Remove the activity indicator only if it's in the doc
        if (activityIndicator.inDoc()) {
            activityIndicator.remove();
        }
    },

    /**
     * Shows the error message
     *
     * @private
     */
    _showErrorMessage: function () {
        // Vars
        var errorMessage = this.get('errorMessage'),
            buttons = this.get('buttons');

        // Add the error message only if it's not already in the doc
        if (!errorMessage.inDoc()) {
            buttons.append(errorMessage);
        }
    },

    /**
     * Hides the error message
     *
     * @private
     */
    _hideErrorMessage: function () {
        // Vars
        var errorMessage = this.get('errorMessage');

        // Remove the error message from the dom if it's already rendered
        if (errorMessage.inDoc()) {
            errorMessage.remove();
        }
    },

    /**
     * Adds participants to the single user chat conversation
     *
     * @param participants [Y.LIMS.Model.BuddyItemModel]
     * @private
     */
    _addParticipantsToSUC: function (participants) {
        // Vars
        var model = this.get('model'),
            buddies;

        // Merge buddies that are already in the conversation with the newly added participants
        buddies = model.get('participants').concat(participants);

        // Create multi user chat conversation
        Y.fire('buddiesSelected', {
            buddies: buddies
        });
    },

    /**
     * Adds participants to the multi user chat conversation
     *
     * @param participants [Y.LIMS.Model.BuddyItemModel]
     * @private
     */
    _addParticipantsToMUC: function (participants) {
        // Vars
        var model = this.get('model'),
            instance = this;

        // Hide buttons
        this._removeButtons();
        // Show preloader
        this._showActivityIndicator();
        // Update the model
        model.addMoreParticipants(participants, function (err) {
            // Success
            if (!err) {
                // Fire an event
                instance.fire('addMoreSuccess', instance);
            }
            // Error
            else {
                // Hide the activity indicator
                instance._hideActivityIndicator();
                // Show error message
                instance._showErrorMessage();
                // Show the buttons again so the user can retry
                instance._addButtons();
                // Fire an event
                instance.fire('addMoreError', instance);
            }
        });
    },

    /**
     * Called when the user clicks on the leave button
     *
     * @private
     */
    _onAddButtonClick: function () {
        // Vars
        var conversationType = this.get('model').get('conversationType'),
            selectedBuddies = this.get('participants').get('selectedBuddies');

        // Only if at least one buddy was selected
        if (selectedBuddies.length > 0) {

            // Single user chat conversation
            if (conversationType === 'SINGLE_USER') {
                // Add participants to the conversation
                this._addParticipantsToSUC(selectedBuddies);
                // Hide the view since the new conversation window was opened
                this.hideView();
            }
            // Multi user chat conversation
            else if (conversationType === 'MULTI_USER') {
                // Add participants to the conversation
                this._addParticipantsToMUC(selectedBuddies);
            }
            // Unknown type of the conversation
            else {
                // Hide the view
                this.hideView();
            }

        }
        // Nothing was selected
        else {
            // Hide the view
            this.hideView();
        }

        // Fire the event
        this.fire('addButtonClick', this);
    },

    /**
     * Called when the user click on the cancel button
     *
     * @private
     */
    _onCancelButtonClick: function () {
        // Hide the view
        this.hideView();
        // Fire the event
        this.fire('cancelButtonClick', this);
    },

    /**
     * Called when the model is successfully read
     *
     * @private
     */
    _onModelReadSuccess: function () {
        // Vars
        var participants = this.get('participants'),
            model = this.get('model');

        // Update a list of excluded participants
        participants.set('excludedBuddies', model.get('participants'));
    }

}, {

    ATTRS: {

        /**
         * Container attached to view
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                // Vars
                var container = Y.Node.create(this.template);

                // Hide the node since it's going to be set visible
                // whenever the user clicks on the option button
                container.hide();

                return container;
            }
        },

        /**
         * Contains node with the container content
         *
         * {Node}
         */
        content: {
            valueFn: function () {
                // Vars
                var content = this.get('container').one('.content');

                // Hide the content at the beginning since it's going to be
                // shown later on
                content.hide();

                return content;
            }
        },

        /**
         * Model attached to the view
         *
         * {Y.LIMS.Model.ConversationModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * True if the view is hidden
         *
         * {boolean}
         */
        isHidden: {
            value: true // default value
        },

        /**
         * Minimal height of the container
         *
         * {number}
         */
        containerHeight: {
            value: 86 // default value
        },

        /**
         * Buttons node
         *
         * {Node}
         */
        buttons: {
            valueFn: function () {
                return this.get('container').one('.buttons');
            }
        },

        /**
         * Cancel button node
         *
         * {Node}
         */
        cancelButton: {
            valueFn: function () {
                return this.get('container').one('.cancel');
            }
        },

        /**
         * Leave button node
         *
         * {Node}
         */
        addButton: {
            valueFn: function () {
                return this.get('container').one('.add');
            }
        },

        /**
         * Activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return Y.Node.create(this.activityIndicatorTemplate);
            }
        },


        /**
         * View for participants input
         *
         * {Y.LIMS.View.TokenInputElementView}
         */
        participants: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.token-input');

                return new Y.LIMS.View.BuddySearchTokenInput({
                    container: container,
                    autocomplete: true
                });
            }
        },

        /**
         * Error message node
         *
         * {Node}
         */
        errorMessage: {
            valueFn: function () {
                // Vars
                var errorMessage = Y.Node.create(this.errorMessageTemplate);

                // Set the error message content
                errorMessage.set('innerHTML',
                    Y.LIMS.Core.i18n.values.conversationConversationOptionsLeaveError
                );

                return errorMessage;
            }
        },

        /**
         * Show/Hide animation
         *
         * {Y.Anim}
         */
        openAnimation: {
            valueFn: function () {
                // Vars
                var container = this.get('container'),
                    containerHeight = this.get('containerHeight'),
                    contentAnimation = this.get('contentAnimation'),
                    animation;

                // Create animation
                animation = new Y.Anim({
                    node: container,
                    duration: 0.5,
                    from: { "min-height": 0},
                    to: {"min-height": containerHeight},
                    easing: 'bounceOut'
                });

                // On animation end
                animation.on('end', function () {
                    // Set the hidden flag
                    this.set('isHidden', false);

                }, this);

                // Before the animation starts
                animation.before('start', function () {
                    // Show the container
                    container.show();

                }, this);

                // Before the animation ends
                animation.before('end', function () {
                    // Show the content
                    contentAnimation.set('reverse', false);
                    contentAnimation.run();
                }, this);

                return animation;
            }
        },

        /**
         * Show/Hide animation
         *
         * {Y.Anim}
         */
        closeAnimation: {
            valueFn: function () {
                // Vars
                var container = this.get('container'),
                    containerHeight = this.get('containerHeight'),
                    contentAnimation = this.get('contentAnimation'),
                    participants = this.get('participants'),
                    animation;

                // Create animation
                animation = new Y.Anim({
                    node: container,
                    duration: 0.5,
                    from: { "min-height": containerHeight},
                    to: {"min-height": 0},
                    easing: 'backIn'
                });

                // On animation end
                animation.on('end', function () {
                    // Hide the container
                    container.hide();
                    // Set the hidden flag
                    this.set('isHidden', true);
                    // Clear token input
                    participants.clear();
                }, this);

                // Before the animation starts
                animation.before('start', function () {
                    // Hide the content
                    contentAnimation.set('reverse', true);
                    contentAnimation.run();
                });

                return animation;
            }
        },

        /**
         * Animation of content within the container
         *
         * {Y.Anim}
         */
        contentAnimation: {
            valueFn: function () {
                // Vars
                var content = this.get('content'),
                    participants = this.get('participants'),
                    animation;

                // Create animation
                animation = new Y.Anim({
                    node: content,
                    duration: 0.3,
                    from: {opacity: 0},
                    to: {opacity: 1}
                });

                // Before the animation starts
                animation.before('start', function () {
                    // Closing
                    if (animation.get('reverse')) {
                        // Set opacity to 1
                        content.setStyle('opacity', 1);
                    }
                    // Opening
                    else {
                        // Show the content node
                        content.show();
                        // Set opacity to 0
                        content.setStyle('opacity', 0);
                    }
                }, this);

                // On animation end
                animation.on('end', function () {
                    // Closing
                    if (animation.get('reverse')) {
                        // Hide the node
                        content.hide();
                        // Remove focus from participants input
                        participants.blur();
                    } else {
                        // Add focus to participants input
                        participants.focus();
                    }
                }, this);

                return animation;
            }
        }
    }
});

