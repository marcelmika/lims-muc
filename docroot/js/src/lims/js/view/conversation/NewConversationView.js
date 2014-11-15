/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * New Conversation View
 *
 * The class extends Y.View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.NewConversationView = Y.Base.create('newConversationView', Y.View, [], {

    // The template property holds the contents of the #lims-panel-new-conversation-template
    // element, which will be used as the HTML template for the new conversation panel
    // Check the templates.jspf to see all templates
    template: Y.one('#limsmuc-panel-new-conversation-template').get('innerHTML'),

    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var doneButton = this.get('doneButton');

        // Local events
        doneButton.on('click', this._onDoneButtonClick, this);
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
     * Called when user click on done button
     *
     * @private
     */
    _onDoneButtonClick: function () {
        // Vars
        var participants = this.get('participants');

        // Fire event
        this.fire('selectedBuddies', {
            buddies: participants.get('selectedBuddies')
        });
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Returns new conversation
         */
        container: {
            valueFn: function () {
                // Vars
                var container = Y.Node.create(this.template);
                // Hide the node from the beginning
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
                // Hide the content at the beginning
                content.hide();

                return content;
            }
        },

        /**
         * Done button
         *
         * {Node}
         */
        doneButton: {
            valueFn: function () {
                return this.get('container').one('.button');
            }
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
         * Minimal height of the container
         *
         * {number}
         */
        containerMinHeight: {
            value: 85 // default value
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
                    containerMinHeight = this.get('containerMinHeight'),
                    contentAnimation = this.get('contentAnimation'),
                    animation;

                // Create animation
                animation = new Y.Anim({
                    node: container,
                    duration: 0.5,
                    from: { "min-height": 0},
                    to: {"min-height": containerMinHeight},
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
                    containerMinHeight = this.get('containerMinHeight'),
                    contentAnimation = this.get('contentAnimation'),
                    participants = this.get('participants'),
                    animation;

                // Create animation
                animation = new Y.Anim({
                    node: container,
                    duration: 0.5,
                    from: { "min-height": containerMinHeight},
                    to: {"min-height": 0},
                    easing: 'backIn'
                });

                // On animation end
                animation.on('end', function () {
                    // Hide the container
                    container.hide();
                    // Clear all tokens in participants input view
                    participants.clear();
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
