/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Tooltip view
 */
Y.namespace('LIMS.View');

Y.LIMS.View.Tooltip = Y.Base.create('tooltip', Y.View, [Y.LIMS.View.ViewExtension], {

    // Tooltip container template
    tooltipContainerTemplate: '<div class="tooltip-container" />',

    /**
     * Called on init
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Renders all tooltips on the page
     */
    render: function () {
        // Vars
        var container = this.get('container'),
            tooltipContainer = this.get('tooltipContainer'),
            zIndex = this.get('zIndex'),
            fadeInDuration = this.get('fadeInDuration'),
            fadeOutDuration = this.get('fadeOutDuration'),
            tooltip;

        // Container must be present
        if (container) {
            // Add tooltip only if it's not already there
            if (!tooltipContainer.inDoc()) {
                container.append(tooltipContainer);
            }
        }

        // Create tooltip overlay object
        tooltip = new Y.Overlay({
            srcNode: tooltipContainer,
            visible: false,
            zIndex: zIndex
        }).plug(Y.Plugin.WidgetAnim);

        // Add tooltip animation
        tooltip.anim.get('animShow').set('duration', fadeInDuration);
        tooltip.anim.get('animHide').set('duration', fadeOutDuration);

        // Render tooltip
        tooltip.render();

        // Make it accessible
        this.set('tooltip', tooltip);
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var tooltipContainer = this.get('tooltipContainer'),
            selector = this.get('selector');

        // Local
        tooltipContainer.on('mouseleave', this._onMouseLeave, this);

        // Global
        Y.delegate('mousemove', this._onMouseMove, this.getPortletContainer(), selector, this);
        Y.delegate('mouseleave', this._onMouseLeave, this.getPortletContainer(), selector, this);
    },

    /**
     * Called when the user moves with mouse over the tooltip
     *
     * @param event
     * @private
     */
    _onMouseMove: function (event) {
        // Vars
        var tooltip = this.get('tooltip'),
            tooltipContainer = this.get('tooltipContainer'),
            waitingToShow = this.get('waitingToShow'),
            align = Y.WidgetPositionAlign,
            fadeInDelay = this.get('fadeInDelay'),
            textAttribute = this.get('textAttribute'),
            instance = this,
            target = event.target;

        // If the tooltip is not visible show it
        if (tooltip && tooltip.get('visible') === false) {
            // Set opacity to zero because it will be animated
            tooltipContainer.setStyle('opacity', '0');
        }

        // If it's not being presented
        if (waitingToShow === false) {

            // While it's still hidden, move the tooltip adjacent to the cursor
            tooltip.set('align', {
                node: target,
                points: [align.BL, align.BR] // Align to bottom
            });

            // Wait a bit, then show the tooltip
            setTimeout(function () {
                // Show the tooltip only if it's meant to be shown
                if (instance.get('waitingToShow') === true) {
                    // Set the full opacity
                    tooltipContainer.setStyle('opacity', 1);
                    // Show the tooltip
                    tooltip.show();
                }
            }, fadeInDelay);

            // While waiting to show tooltip, don't let other
            // "mousemoves" try to show tooltip too.
            this.set('waitingToShow', true);

            if (target) {
                // Show the tooltip to user
                tooltip.setStdModContent('body', target.getAttribute(textAttribute));
            }

        }
    },

    /**
     * Called when the user leaves the tooltip with mouse
     * @param event
     * @private
     */
    _onMouseLeave: function (event) {
        // Vars
        var tooltip = this.get('tooltip');

        // This check prevents hiding the tooltip
        // when the cursor moves over the tooltip itself
        if ((event.relatedTarget) && (event.relatedTarget.hasClass('yui3-widget-bd') === false)) {
            // Hide the tooltip
            tooltip.hide();
            // Tooltip anim is not waiting
            this.set('waitingToShow', false);
        }
    }


}, {
    ATTRS: {

        /**
         * Main container
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                // Root node is the container for tooltip container
                return this.getBar();
            }
        },

        /**
         * Tooltip container
         *
         * {Node}
         */
        tooltipContainer: {
            valueFn: function () {
                return Y.Node.create(this.tooltipContainerTemplate);
            }
        },

        /**
         * Tooltip object
         *
         * {Y.Overlay | null}
         */
        tooltip: {
            value: null // to be set
        },

        /**
         * Selector used to find all tooltips
         *
         * {string}
         */
        selector: {
            value: '.tooltip-button' // default value
        },

        /**
         * Z-Index of the tooltip overlay
         *
         * {integer}
         */
        zIndex: {
            value: 40 // default value
        },

        /**
         * Attribute used to read the text of the tooltip
         *
         * {string}
         */
        textAttribute: {
            value: 'data-text'
        },

        /**
         * Delay before the tooltip appears
         *
         * {integer}
         */
        fadeInDelay: {
            value: 500 // half a second
        },

        /**
         * Duration of the fade in animation of the tooltip
         *
         * {integer}
         */
        fadeInDuration: {
            value: 0.3 // default value
        },

        /**
         * Duration of the fade out animation of the tooltip
         */
        fadeOutDuration: {
            value: 0.1 // default value
        },

        /**
         * Set to true if the tooltip is in the show process
         *
         * {boolean}
         */
        waitingToShow: {
            value: false // default value
        }

    }
});
