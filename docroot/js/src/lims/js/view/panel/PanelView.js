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

    // Holds all errors
    errorContainerTemplate: '<div class="panel-error"/>',

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
     * @param errorId Each error message must have an id
     * @param errorMessage Content of the error
     */
    showError: function (errorId, errorMessage) {
        // Vars
        var panelTitle = this.get('panelTitle'),                        // Title of the panel node
            errorView,                                                  // Single error view
            errorContainer = this.get('errorContainer'),                // Container with error
            animation;                                                  // Animation that will make it nicer

        // The error is new
        if (!this._findErrorNode(errorId)) {
            // Add it to error view collection
            errorView = Y.Node.create(Y.Lang.sub(this.errorTemplate, {
                errorId: errorId,
                errorMessage: errorMessage
            }));

            // If it's not in the error container yet add it
            errorContainer.append(errorView);
        }

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
     *
     * @param errorId Each error message must have an id
     */
    hideError: function (errorId) {
        // Vars
        var errorNode;

        // Find the error node based on the error id
        errorNode = this._findErrorNode(errorId);

        // If such node exists remove it
        if (errorNode) {
            errorNode.remove();
        }
    },


    /**
     * Starts the title notification blinking effect
     *
     * @private
     */
    startTitleBlinking: function () {
        // Vars
        var instance = this,
            blinkingTitleTimer = this.get('blinkingTitleTimer'),
            blinkingTitleInterval = this.get('blinkingTitleInterval');

        // If there is a title interval from the past invalidate it
        if (blinkingTitleInterval) {
            clearInterval(blinkingTitleTimer);
        }

        // Toggle blinking
        instance.toggleTitleBlinking();
        // Create new timer that will run the blinking effect
        this.set('blinkingTitleTimer', setInterval(function () {
            // Toggle blinking
            instance.toggleTitleBlinking();
        }, blinkingTitleInterval));
    },

    /**
     * Toggles blinking effect
     */
    toggleTitleBlinking: function () {
        // Vars
        var trigger = this.get('trigger');

        // Toggle panel title highlight
        if (trigger.hasClass('highlight')) {
            trigger.removeClass('highlight');
        } else {
            trigger.addClass('highlight');
        }
    },

    /**
     * Stops the title notification blinking effect
     *
     * @private
     */
    stopTitleBlinking: function () {
        // Vars
        var trigger = this.get('trigger'),
            blinkingTitleTimer = this.get('blinkingTitleTimer');

        // Remove the highlight class
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
            Y.LIMS.Core.Util.show(badge);

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
            Y.LIMS.Core.Util.hide(badge);
        }
    },

    /**
     * Shows the trigger close button
     */
    showTriggerClose: function () {
        // Vars
        var triggerClose = this.get('triggerClose'),
            badge = this.get('badge');

        if (triggerClose && badge) {
            badge.addClass('covered');
            triggerClose.removeClass('covered');
        }
    },

    /**
     * Hides the trigger close button
     */
    hideTriggerClose: function () {
        // Vars
        var triggerClose = this.get('triggerClose'),
            badge = this.get('badge');

        if (triggerClose && badge) {
            badge.removeClass('covered');
            triggerClose.addClass('covered');
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
        // Vars
        var trigger = this.get('trigger'),
            panelButtons = this.get('panelButtons'),
            instance = this;

        // Local events
        trigger.delegate('click', function (event) {
            // Vars
            var className = event.currentTarget.get('className');

            // Trigger name clicked
            if (className === 'trigger-name') {
                // Fire event
                instance._onTriggerClick(event);
            }
            // Close button clicked
            else if (className === 'close') {
                // Fire event
                instance._onTriggerCloseClick(event);
            }

        }, '.trigger-name, .close');

        // Attach trigger events
        trigger.on('mouseenter', this._onTriggerMouseEnter, this);
        trigger.on('mouseleave', this._onTriggerMouseLeave, this);
        // Attach panel button event
        panelButtons.on('click', this._onPanelButtonsClick, this);
    },

    /**
     * Returns error node based on the error id, null otherwise
     *
     * @param errorId
     * @return {Node|null}
     * @private
     */
    _findErrorNode: function (errorId) {
        // Vars
        var errorContainer = this.get('errorContainer'),
            errorNode = null;

        // Iterate over child node
        errorContainer.get('childNodes').each(function (node) {
            // Find the error node
            if (node.attr('data-id') === errorId) {
                errorNode = node;
            }
        }, this);

        return errorNode;
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
     * Called when the close button is clicked
     *
     * @private
     */
    _onTriggerCloseClick: function () {
        // Close the panel
        this.close();
    },

    /**
     * Called when the user enters trigger with the mouse
     *
     * @private
     */
    _onTriggerMouseEnter: function () {
        // Show trigger close button
        this.showTriggerClose();
    },

    /**
     * Called when the user leaves the trigger with mouse
     *
     * @private
     */
    _onTriggerMouseLeave: function () {
        // Hide trigger close button
        this.hideTriggerClose();
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

        /**
         * Panel container
         *
         * {Node}
         */
        container: {
            value: null // To be set in initializer
        },

        /**
         * Id of the panel
         *
         * {string}
         */
        panelId: {
            value: null // To be set in initializer
        },

        /**
         * Indicates if the panel is opened
         *
         * {boolean}
         */
        isOpened: {
            value: false // default value
        },

        /**
         * Panel title
         *
         * {Node}
         */
        panelTitle: {
            valueFn: function () {
                return this.get('container').one('.panel-title');
            }
        },

        /**
         * Tab bar item which shows/hides panel
         *
         * {Node}
         */
        trigger: {
            valueFn: function () {
                return this.get('container').one('.panel-trigger');
            }
        },

        /**
         * All buttons related to panel like e.g. minimize or close button
         *
         * [Node]
         */
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
                return this.get('container').one('.unread');
            }
        },

        /**
         * Close button in panel trigger
         *
         * {Node}
         */
        triggerClose: {
            valueFn: function () {
                return this.get('container').one('.close');
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

        /**
         * Search container node
         *
         * {Node}
         */
        searchContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-search');
            }
        },

        /**
         * Error container node
         *
         * {Node}
         */
        errorContainer: {
            valueFn: function () {
                return Y.Node.create(this.errorContainerTemplate);
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
         *
         * {integer}
         */
        blinkingTitleInterval: {
            value: 2000 // 1 second
        }
    }

});

