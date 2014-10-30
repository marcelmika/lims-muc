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
 * Leave Conversation View
 *
 * The class extends Y.View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.LeaveConversationOption = Y.Base.create('leaveConversationOption', Y.View, [], {

    // The template property holds the contents of the #lims-conversation-option-leave-conversation-template
    // element, which will be used as the HTML template for the leave conversation option view
    template: Y.one('#lims-conversation-option-leave-conversation-template').get('innerHTML'),

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
            leaveButton = this.get('leaveButton');

        // Local event
        cancelButton.on('click', this._onCancelButtonClick, this);
        leaveButton.on('click', this._onLeaveButtonClick, this);
    },

    /**
     * Adds action buttons to the buttons node
     *
     * @private
     */
    _addButtons: function () {
        // Vars
        var cancelButton = this.get('cancelButton'),
            leaveButton = this.get('leaveButton'),
            buttons = this.get('buttons');

        buttons.append(cancelButton);
        buttons.append('&nbsp;');
        buttons.append(leaveButton);
    },

    /**
     * Removes action buttons
     *
     * @private
     */
    _removeButtons: function () {
        // Vars
        var cancelButton = this.get('cancelButton'),
            leaveButton = this.get('leaveButton');

        // Remove buttons
        cancelButton.remove();
        leaveButton.remove();
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
     * Called when the user clicks on the leave button
     *
     * @private
     */
    _onLeaveButtonClick: function () {
        // Vars
        var model = this.get('model'),
            instance = this;

        // Hide buttons
        this._removeButtons();
        // Show preloader
        this._showActivityIndicator();
        // Update the model
        model.leaveConversation(function (err) {
            // Success
            if (!err) {
                // Fire an event
                instance.fire('leaveConversationSuccess', instance);
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
                instance.fire('leaveConversationError', instance);
            }
        });

        // Fire the event
        this.fire('leaveButtonClick', this);
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
            value: 112 // default value
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
        leaveButton: {
            valueFn: function () {
                return this.get('container').one('.leave');
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
                    from: { "height": 0},
                    to: {"height": containerHeight},
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
                    animation;

                // Create animation
                animation = new Y.Anim({
                    node: container,
                    duration: 0.5,
                    from: { "height": containerHeight},
                    to: {"height": 0},
                    easing: 'backIn'
                });

                // On animation end
                animation.on('end', function () {
                    // Hide the container
                    container.hide();
                    // Set the hidden flag
                    this.set('isHidden', true);
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
                    }
                }, this);

                return animation;
            }
        }
    }
});

