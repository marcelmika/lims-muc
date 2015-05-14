/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Search View
 *
 * The class extends Y.View. It represent a view for a search functionality in contacts panel
 */
Y.namespace('LIMS.View');

Y.LIMS.View.GroupSearchView = Y.Base.create('groupSearchView', Y.View, [], {

    // The template property holds the contents of the #lims-panel-search-template
    // element, which will be used as the HTML template for the search panel
    // Check the templates.jspf to see all templates
    template: Y.one('#limsmuc-panel-search-template').get('innerHTML'),

    /**
     *  The initializer runs when an instance is created.
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Resets the search result
     */
    reset: function () {

        // Vars
        var searchInput = this.get('searchInput'),
            errorView = this.get('errorView');

        // Add a focus on the search input and empty the content
        searchInput.set('value', '');
        searchInput.focus();

        // Render the view
        this.render();
        // No preloader is needed at the beginning
        this._hideActivityIndicator();
        // Show info message
        this._hideNoResultsMessage();
        // View is not fully expanded yet
        this.set('fullyExpanded', false);
        // Hide errors
        errorView.hideErrorMessage(false);
    },

    /**
     * Renders view
     *
     * @returns {Y.LIMS.View.GroupSearchView}
     */
    render: function () {
        // Vars
        var listContainer = this.get('listContainer'),
            model = this.get('model'),
            buddy,
            buddyView,
            index;

        // Empty the list view
        listContainer.set('innerHTML', '');

        // Create view for model instances
        for (index = 0; index < model.size(); index++) {
            // Get buddy from the list
            buddy = model.item(index);
            // Build view
            buddyView = new Y.LIMS.View.GroupBuddyView({model: buddy});
            // Render view
            buddyView.render();
            // Append to container
            listContainer.append(buddyView.get('container'));
        }

        if (model.size() === 0) {
            this._showNoResultsMessage();
        } else {
            this._hideNoResultsMessage();
        }

        return this;
    },


    /**
     * Shows the search panel
     *
     * @param callback that is called whenever the show animation is finished
     */
    showView: function (callback) {

        // Vars
        var model = this.get('model'),
            container = this.get('container'),
            contentAnimation = this.get('contentAnimation'),
            partiallyExpandedHeight = this.get('partiallyExpandedHeight'),
            animation;

        // Set the visibility flag
        this.set('isVisible', true);

        // Create an instance of animation
        animation = new Y.Anim({
            node: container,
            duration: 0.5,
            from: { "min-height": 0},
            to: {"min-height": partiallyExpandedHeight},
            easing: 'bounceOut'
        });

        // Search button will no longer be needed so hide it at the end of the animation
        animation.on('end', function () {
            callback();
        }, this);

        // Before the animation ends
        animation.before('end', function () {
            // Show the content
            contentAnimation.set('reverse', false);
            contentAnimation.run();
        }, this);

        // Opacity needs to be set to zero otherwise there will
        // be a weird blink effect
        container.setStyle('min-height', 0);

        // Reset the previous search
        model.reset();
        // Reset the view
        this.reset();

        // Run the animation
        animation.run();
    },

    showFullView: function (callback) {
        // Vars
        var animation,
            instance = this,
            fullyExpanded = this.get('fullyExpanded'),
            partiallyExpandedHeight = this.get('partiallyExpandedHeight'),
            fullyExpandedHeight = this.get('fullyExpandedHeight'),
            container = this.get('container');

        // View is already fully expanded, do nothing
        if (fullyExpanded) {
            callback();
        }
        // View is not expanded yet, animate it
        else {
            animation = new Y.Anim({
                node: container,
                duration: 0.5,
                from: { "min-height": partiallyExpandedHeight },
                to: { "min-height": fullyExpandedHeight },
                easing: "bounceOut"
            });

            animation.on('end', function () {
                instance.set('fullyExpanded', true);
                callback();
            }, this);

            animation.run();
        }
    },

    /**
     * Hides the search panel
     *
     * @param callback that is called whenever the hide animation is finished
     */
    hideView: function (callback) {

        // Vars
        var container = this.get('container'),
            contentAnimation = this.get('contentAnimation'),
            fullyExpanded = this.get('fullyExpanded'),
            partiallyExpandedHeight = this.get('partiallyExpandedHeight'),
            fullyExpandedHeight = this.get('fullyExpandedHeight'),
            height = fullyExpanded ? fullyExpandedHeight : partiallyExpandedHeight,
            instance = this,
            animation;

        this.set('isVisible', false);

        // Create an instance of animation
        animation = new Y.Anim({
            node: container,
            duration: 0.5,
            from: { "min-height": height},
            to: {"min-height": 0},
            easing: 'backIn'
        });

        // Listen to the end of the animation
        animation.on('end', function () {
            instance.set('fullyExpanded', false);
            // Reset the view
            instance.reset();
            callback();
        }, this);

        // Before the animation starts
        animation.before('start', function () {
            // Hide the content
            contentAnimation.set('reverse', true);
            contentAnimation.run();
        });

        // Run the animation
        animation.run();
    },

    /**
     * Attach listeners to events
     *
     * @private
     */
    _attachEvents: function () {

        // Vars
        var searchInput = this.get('searchInput'),
            model = this.get('model'),
            errorView = this.get('errorView');

        // Local events
        searchInput.on('keyup', this._onSearchInputUpdate, this);
        model.on('searchStarted', this._onSearchStarted, this);
        model.on('searchSuccess', this._onSearchSuccess, this);
        model.on('searchError', this._onSearchError, this);
        errorView.on('resendButtonClick', this._onResendButtonClick, this);
    },

    /**
     * Performs search operation
     *
     * @private
     */
    _search: function () {

        // Vars
        var timer = this.get('timer'),
            timerDelayInterval = this.get('timerDelayInterval'),
            searchInput = this.get('searchInput'),
            model = this.get('model'),
            value;

        // Get rid of new line characters
        value = searchInput.get('value').replace(/\n|\r/gim, '');
        value = Y.Lang.trim(value);

        // Clear out the timer first. Since we don't want to send a request whenever the user
        // types another letter we wait until he stops.
        clearTimeout(timer);

        // Set a new timer
        this.set('timer', setTimeout(function () {
            // If there is no content to be searched do nothing
            if (value !== "") {
                // Search buddies
                model.search(value);
            }

        }, timerDelayInterval));
    },

    /**
     * Shows activity indicator
     *
     * @private
     */
    _showActivityIndicator: function () {

        // Vars
        var activityIndicator = this.get('activityIndicator'),
            container = this.get('container');

        // Show only if it's not already in the document
        if (!activityIndicator.inDoc()) {
            // Add to container
            container.append(activityIndicator);
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

        // Hide only if the activity indicator is in the document
        if (activityIndicator.inDoc()) {
            activityIndicator.remove();
        }
    },

    /**
     * Shows no results info message
     *
     * @private
     */
    _showNoResultsMessage: function () {
        // Vars
        var infoNoResultsView = this.get('infoNoResultsView');
        // Show info view
        infoNoResultsView.showInfoMessage(false);
    },

    /**
     * Hides no results info message
     *
     * @private
     */
    _hideNoResultsMessage: function () {
        // Vars
        var infoNoResultsView = this.get('infoNoResultsView');
        // Hide info
        infoNoResultsView.hideInfoMessage(false);
    },

    /**
     * Called when the search input field is updated
     *
     * @private
     */
    _onSearchInputUpdate: function () {
        // Start search
        this._search();
    },

    /**
     * Called when the search begins
     *
     * @private
     */
    _onSearchStarted: function () {
        // Show the preloader
        this._showActivityIndicator();
    },

    /**
     * Called when the search ends successfully
     *
     * @private
     */
    _onSearchSuccess: function () {
        // Vars
        var errorView = this.get('errorView'),
            isVisible = this.get('isVisible'),
            instance = this;

        // Do something only if the view is visible to the user
        if (isVisible) {

            // Fully expand the view
            this.showFullView(function () {
                // Hide the preloader
                instance._hideActivityIndicator();
                // Hide info message
                instance._hideNoResultsMessage();
                // Render incoming data
                instance.render();
                // Hide error
                errorView.hideErrorMessage(false);
            });
        }
    },

    /**
     * Called when the search end with failure
     *
     * @private
     */
    _onSearchError: function () {
        // Vars
        var instance = this,
            isVisible = this.get('isVisible'),
            errorView = this.get('errorView');

        // Do something only if the view is visible to the user
        if (isVisible) {

            // Fully expand the view
            this.showFullView(function () {
                // Hide the preloader
                instance._hideActivityIndicator();
                // Render
                instance.render();
                // Hide info message
                instance._hideNoResultsMessage();
                // Show error
                errorView.showErrorMessage(true);
            });
        }
    },

    /**
     * Called when the user click on the refresh button in the error view
     *
     * @private
     */
    _onResendButtonClick: function () {
        // Perform the search again
        this._search();
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Main container
         *
         * {Node}
         */
        container: {
            valueFn: function () {
                return Y.Node.create(this.template);
            }
        },

        /**
         * Model related to the view
         *
         * {Y.LIMS.Model.BuddySearchListModel}
         */
        model: {
            value: null // to be set
        },

        /**
         * Search input field node
         *
         * {Node}
         */
        searchInput: {
            valueFn: function () {
                return this.get('container').one('.search-input-field');
            }
        },

        /**
         * Content of the search results node
         *
         * {Node}
         */
        searchContent: {
            valueFn: function () {
                return this.get('container').one('.search-content');
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
                Y.LIMS.Core.Util.hide(content);

                return content;
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
                    searchInput = this.get('searchInput'),
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

                        // Focus in animation is causing issues on bile
                        if (!Y.UA.mobile) {
                            // Add focus to search input
                            searchInput.focus();
                        }
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
                        // Remove focus from search input
                        searchInput.blur();
                    }
                }, this);

                return animation;
            }
        },

        /**
         * True if the search content view is fully expanded
         *
         * {boolean}
         */
        fullyExpanded: {
            value: false // default value
        },

        /**
         * True if the view is visible to the user
         *
         * {boolean}
         */
        isVisible: {
            value: false // default value
        },

        /**
         * Height of the panel when partially expanded
         *
         * {number}
         */
        partiallyExpandedHeight: {
            value: 40 // default value
        },

        /**
         * Height of the panel when fully expanded
         *
         * {number}
         */
        fullyExpandedHeight: {
            value: 248 // default value
        },

        /**
         * Error view with error message and resend button
         *
         * {Y.LIMS.View.ErrorNotificationView}
         */
        errorView: {
            valueFn: function () {
                // Vars
                var container = this.get('searchContent');
                // Create view
                return new Y.LIMS.View.ErrorNotificationView({
                    container: container,
                    errorMessage: Y.LIMS.Core.i18n.values.searchErrorMessage
                });
            }
        },

        /**
         * Info view with an info message about no results
         *
         * {Y.LIMS.View.InfoNotificationView}
         */
        infoNoResultsView: {
            valueFn: function () {
                // Vars
                var container = this.get('searchContent');
                // Create view
                return new Y.LIMS.View.InfoNotificationView({
                    container: container,
                    infoMessage: Y.LIMS.Core.i18n.values.searchNoResultsMessage
                });
            }
        },

        /**
         * List container node
         *
         * {Node}
         */
        listContainer: {
            valueFn: function () {
                return this.get('container').one('.group-buddy-list');
            }
        },

        /**
         * Activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one('.preloader');
            }
        },

        /**
         * Timer used for the delayed key up in text field
         *
         * {timer}
         */
        timer: {
            value: null // to be set
        },

        /**
         * Delayed key up interval. Model is refreshed after the delay.
         * This is useful because we don't want to overwhelm the server with to many requests.
         *
         * {integer}
         */
        timerDelayInterval: {
            value: 500 // half a second
        }
    }
});

