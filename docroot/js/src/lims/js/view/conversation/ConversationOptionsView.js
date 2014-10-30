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
 * Conversation Options View
 *
 * The class extends Y.View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConversationOptionsView = Y.Base.create('conversationOptionsView', Y.View, [], {

    // The template property holds the contents of the #lims-conversation-options-template
    // element, which will be used as the HTML template for the conversation options view
    template: Y.one('#lims-conversation-options-template').get('innerHTML'),

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
        var optionAddMore = this.get('optionAddMore'),
            optionLeaveConversation = this.get('optionLeaveConversation');

        // Local events
        if (optionAddMore) {
            optionAddMore.on('click', this._onOptionAddMoreClick, this);
        }

        if (optionLeaveConversation) {
            optionLeaveConversation.on('click', this._onOptionLeaveConversationClick, this);
        }
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
                var container = Y.Node.create(this.template),
                    model = this.get('model'),
                    bottomPadding = 7, // Padding added to the bottom of the container
                    height;

                // This is a options related to the single user conversation
                if (model.get('conversationType') === 'SINGLE_USER') {
                    // SUC doesn't have the leave conversation option
                    container.one('.leave-conversation').remove();
                }

                // Calculate container's height
                height = Y.LIMS.Core.Util.calculateHeight(container) + bottomPadding;

                // Remember the height
                this.set('containerHeight', height);

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

