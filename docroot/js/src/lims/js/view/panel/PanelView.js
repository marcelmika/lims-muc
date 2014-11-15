/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Panel View
 *
 * The class extends Y.View. It contains all necessary methods to handle panels with content
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PanelView = Y.Base.create('panelView', Y.View, [], {

    // The template property holds the contents of the #lims-conversation-item-error-template
    // element, which will be used as the HTML template for an error message
    // Check the templates.jspf to see all templates
    errorTemplate: Y.one('#limsmuc-panel-error-template').get('innerHTML'),

    /**
     * Constructor
     *
     * @param options {container, panelId} are required
     */
    initializer: function (options) {
        this.set("container", options.container);
        this.set("panelId", options.panelId);

        // Handle already opened panel
        if (options.container.hasClass('selected')) {
            // Fire event
            Y.fire('panelShown', this);
            // Set flag
            this.set('isOpened', true);
        }

        // Attach events to DOM elements from container
        this._attachEvents();
    },

    /**
     * Returns true if the panel is opened
     *
     * @return {boolean}
     */
    isOpened: function () {
        return this.get('isOpened');
    },

    /**
     * Shows the panel
     */
    show: function () {
        var container = this.get('container');
        // Show container
        container.removeClass('closed');
        container.addClass('selected');
        // Set flag
        this.set('isOpened', true);
        // Fire event
        Y.fire('panelShown', this);
    },

    /**
     * Hides panel
     */
    hide: function () {
        var container = this.get('container');
        // Hide container
        container.removeClass('selected');
        // Set flag
        this.set('isOpened', false);
        // Fire event
        Y.fire('panelHidden', this);
    },

    /**
     * Toggles panel. If it's shown it hides it, if it's hidden it shows it.
     */
    toggle: function () {
        var isOpened = this.get('isOpened');
        if (isOpened) {
            this.hide();
        } else {
            this.show();
        }
    },

    /**
     * Completely removes panel from tabs
     */
    close: function () {
        var container = this.get('container');
        // Remove container
        container.addClass('closed');
        // Set flag
        this.set('isOpened', false);
        // Fire event
        Y.fire('panelClosed', this);
    },

    /**
     * Shows the error message notification in the panel
     *
     * @param errorMessage
     */
    showError: function (errorMessage) {
        // Vars
        var panelTitle = this.get('panelTitle'),                        // Title of the panel node
            errorContainer = this.get('errorContainer'),                // Container with error
            errorMessageNode = errorContainer.one('.error-message'),    // Error message node
            animation;                                                  // Animation that will make it nicer


        // Set the error message
        errorMessageNode.set('innerHTML', errorMessage);

        // If the error container is already in the document don't animate it
        if (!errorContainer.inDoc()) {

            // Create an instance of animation
            animation = new Y.Anim({
                node: errorContainer,
                duration: 0.5,
                from: {opacity: 0},
                to: {opacity: 1}
            });

            // Opacity needs to be set to zero otherwise there will
            // be a weird blink effect
            errorContainer.setStyle('opacity', 0);
            // Add it after title
            panelTitle.insert(errorContainer, 'after');

            // Run the effect animation
            animation.run();
        }
    },

    /**
     * Hides the error message notification
     */
    hideError: function () {
        // Vars
        var errorContainer = this.get('errorContainer'),
            animation;

        // Run the animation only if the error container is in DOM
        if (errorContainer.inDoc()) {

            // Create the animation instance
            animation = new Y.Anim({
                node: errorContainer,
                duration: 0.5,
                from: {opacity: 1},
                to: {opacity: 0}
            });

            // Listen to the end of the animation
            animation.on('end', function () {
                // Remove container from DOM at the end of the animation
                animation.get('node').remove();
            });

            // Run!
            animation.run();
        }
    },


    /**
     * Starts the title notification blinking effect
     *
     * @private
     */
    startTitleBlinking: function () {
        // Vars
        var panelTitle = this.get('panelTitle'),
            trigger = this.get('trigger'),
            blinkingTitleTimer = this.get('blinkingTitleTimer'),
            blinkingTitleInterval = this.get('blinkingTitleInterval');

        // If there is a title interval from the past invalidate it
        if (blinkingTitleInterval) {
            clearInterval(blinkingTitleTimer);
        }

        // Create new timer that will run the blinking effect
        this.set('blinkingTitleTimer', setInterval(function () {

            // Toggle panel title highlight
            if (panelTitle.hasClass('highlight')) {
                trigger.removeClass('highlight');
                panelTitle.removeClass('highlight');
            } else {
                trigger.addClass('highlight');
                panelTitle.addClass('highlight');
            }

        }, blinkingTitleInterval));
    },

    /**
     * Stops the title notification blinking effect
     *
     * @private
     */
    stopTitleBlinking: function () {
        // Vars
        var panelTitle = this.get('panelTitle'),
            trigger = this.get('trigger'),
            blinkingTitleTimer = this.get('blinkingTitleTimer');

        // Remove the highlight class
        panelTitle.removeClass('highlight');
        trigger.removeClass('highlight');
        // Clear the timeout
        clearInterval(blinkingTitleTimer);
        // Set the value to null
        this.set('blinkingTitleTimer', null);
    },


    /**
     * Updates badge value
     *
     * @param value
     * @param animated
     * @private
     */
    updateBadge: function (value, animated) {
        // Vars
        var badge = this.get('badge');

        // No unread messages
        if (value === 0) {
            this.hideBadge();
        }
        // At least one unread message
        else {
            // Update value
            if (badge) {
                badge.set('innerHTML', value);
            }
            // Show badge
            this.showBadge(animated);
        }
    },

    /**
     * Shows badge
     *
     * @private
     */
    showBadge: function (animated) {
        // Vars
        var badge = this.get('badge'),
            badgeAnimation = this.get('badgeAnimation');

        // Show badge
        if (badge) {

            // Show the badge
            badge.show();

            // Run the animation
            if (animated) {
                // Move the badge outside of the visible area
                badge.setStyle('top', 15);
                badgeAnimation.run();
            } else {
                badge.setStyle('top', 4);
            }
        }
    },

    /**
     * Hides badge
     *
     * @private
     */
    hideBadge: function () {
        // Vars
        var badge = this.get('badge');

        // Hide badge
        if (badge) {
            badge.hide();
        }
    },

    /**
     * Makes the badge brighter
     *
     * @private
     */
    brightBadge: function () {
        // Vars
        var badge = this.get('badge');

        if (badge) {
            badge.removeClass('dimmed');
        }
    },

    /**
     * Makes the badge less noticeable
     *
     * @private
     */
    dimBadge: function () {
        // Vars
        var badge = this.get('badge');

        if (badge) {
            badge.addClass('dimmed');
        }
    },

    /**
     * Attaches events to all necessary elements from the container
     *
     * @private
     */
    _attachEvents: function () {
        // Attach trigger event
        this.get('trigger').on('click', this._onTriggerClick, this);
        // Attach panel button event
        this.get('panelButtons').on('click', this._onPanelButtonsClick, this);
    },

    /**
     * Trigger click handler
     *
     * @private
     */
    _onTriggerClick: function () {
        // Toggle panel while clicked on trigger
        this.toggle();
    },

    /**
     * Panel buttons click handler
     *
     * @param event
     * @private
     */
    _onPanelButtonsClick: function (event) {
        var target = event.currentTarget;
        // Minimize button
        if (target.hasClass('minimize')) {
            this.hide();
        }
        // Close button
        else if (target.hasClass('close')) {
            this.close();
        }
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        // Panel container
        container: {
            value: null // To be set in initializer
        },

        // Id of the panel
        panelId: {
            value: null // To be set in initializer
        },

        // Indicates if the panel is opened
        isOpened: {
            value: false // default value
        },

        // Panel title
        panelTitle: {
            valueFn: function () {
                return this.get('container').one('.panel-title');
            }
        },

        // Tab bar item which shows/hides panel
        trigger: {
            valueFn: function () {
                return this.get('container').one('.panel-trigger');
            }
        },

        // All buttons related to panel like e.g. minimize or close button
        panelButtons: {
            valueFn: function () {
                return this.get('container').all('.panel-button');
            }
        },

        /**
         * Badge that shows number of unread messages node
         *
         * {Node}
         */
        badge: {
            valueFn: function () {
                // Vars
                var container = this.get('container');
                // Find badge
                return container.one('.unread');
            }
        },

        /**
         * Animation of the updated badge
         *
         * {Y.Anim}
         */
        badgeAnimation: {
            valueFn: function () {
                // Vars
                var badge = this.get('badge');

                // Only if the panel contains badge
                if (badge) {
                    return new Y.Anim({
                        node: badge,
                        duration: 0.3,
                        from: {top: 15},
                        to: {top: 4},
                        easing: 'backOut'
                    });
                }
            }
        },

        // Search container
        searchContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-search');
            }
        },

        // Error container
        errorContainer: {
            valueFn: function () {
                return  Y.Node.create(this.errorTemplate);
            }
        },

        /**
         * Timer that is used fro the blinking title effect
         *
         * {timer}
         */
        blinkingTitleTimer: {
            value: null // to be set
        },

        /**
         * Length of the blinking title period
         */
        blinkingTitleInterval: {
            value: 1000 // 1 second
        }
    }

});

