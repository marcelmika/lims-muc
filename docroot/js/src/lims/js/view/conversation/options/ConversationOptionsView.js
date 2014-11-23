/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Options View
 *
 * The class extends Y.View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationOptionsView = Y.Base.create('conversationOptionsView', Y.View, [], {

    // The template property holds the contents of the #lims-conversation-options-template
    // element, which will be used as the HTML template for the conversation options view
    template: Y.one('#limsmuc-conversation-options-template').get('innerHTML'),

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
    showView: function (callback) {
        // Vars
        var animation = this.get('openAnimation'),
            containerHeight = this.get('containerHeight'),
            event;

        // Update the height
        animation.setAttrs({
            from: { height: 0},
            to: { height: containerHeight}
        });

        // Call back when animation ends
        event = animation.after('end', function () {
            if (callback) {
                callback();
            }
            // Detach from event since we don't want to call it multiple times
            event.detach();
        }, this);

        animation.run();
    },

    /**
     * Hides the view
     */
    hideView: function (callback) {
        // Vars
        var animation = this.get('closeAnimation'),
            containerHeight = this.get('containerHeight'),
            event;

        // Update the height
        animation.setAttrs({
            from: { height: containerHeight},
            to: { height: 0}
        });

        // Call back when animation ends
        event = animation.after('end', function () {
            if (callback) {
                callback();
            }
            // Detach from event since we don't want to call it multiple times
            event.detach();
        }, this);

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
        var model = this.get('model'),
            optionAddMore = this.get('optionAddMore'),
            optionLeaveConversation = this.get('optionLeaveConversation');

        // Local events
        if (optionAddMore) {
            optionAddMore.on('click', this._onOptionAddMoreClick, this);
        }

        if (optionLeaveConversation) {
            optionLeaveConversation.on('click', this._onOptionLeaveConversationClick, this);
        }

        model.on('readSuccess', this._onModelReadSuccess, this);
    },

    /**
     * Decides with options should be displayed and layouts subviews
     *
     * @private
     */
    _layoutSubviews: function () {
        // Vars
        var optionLeaveConversation = this.get('optionLeaveConversation'),
            model = this.get('model'),
            optionsCount,
            optionsSize = 30,
            container = this.get('container'),
            bottomPadding = 2, // Padding added to the bottom of the container
            height;

        // This is a options related to the single user conversation
        if (model.get('conversationType') === 'SINGLE_USER') {
            // SUC doesn't have the leave conversation option
            optionLeaveConversation.remove();
        }

        // Get the number of all options
        optionsCount = container.all('li').size();

        // Calculate container's height
        height = optionsSize * optionsCount + bottomPadding;

        // Remember the height
        this.set('containerHeight', height);
    },

    /**
     * Called when the user clicks on the add more option
     *
     * @private
     */
    _onOptionAddMoreClick: function () {
        this.fire('optionAddMoreClick', this);
    },

    /**
     * Called when the user clicks on the leave conversation option
     *
     * @private
     */
    _onOptionLeaveConversationClick: function () {
        this.fire('optionLeaveConversationClick', this);
    },

    /**
     * Called when the model is successfully read
     *
     * @private
     */
    _onModelReadSuccess: function () {
        this._layoutSubviews();
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
                Y.LIMS.Core.Util.hide(container);

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
                Y.LIMS.Core.Util.hide(content);

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
         * Add more option node
         *
         * {Node}
         */
        optionAddMore: {
            valueFn: function () {
                return this.get('container').one('.add-more');
            }
        },

        /**
         * Leave conversation option node
         *
         * {Node}
         */
        optionLeaveConversation: {
            valueFn: function () {
                return this.get('container').one('.leave-conversation');
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
         * Minimal height of the container
         *
         * {number}
         */
        containerHeight: {
            value: 60 // default value
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
                    Y.LIMS.Core.Util.show(container);

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
                    Y.LIMS.Core.Util.hide(container);
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
                        Y.LIMS.Core.Util.show(content);
                        // Set opacity to 0
                        content.setStyle('opacity', 0);
                    }
                }, this);

                // On animation end
                animation.on('end', function () {
                    // Closing
                    if (animation.get('reverse')) {
                        // Hide the node
                        Y.LIMS.Core.Util.hide(content);
                    }
                }, this);

                return animation;
            }
        }
    }
});

