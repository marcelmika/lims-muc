/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group View Controller
 */
Y.namespace('LIMS.Controller');

Y.LIMS.Controller.GroupViewController = Y.Base.create('groupViewController', Y.LIMS.Core.ViewController, [], {

    // The template property holds the contents of the #lims-panel-button-list
    // element, which will be used as the HTML template for the list panel button
    listButtonTemplate: Y.one('#limsmuc-panel-button-list').get('innerHTML'),

    /**
     *  The initializer runs when a Group View Controller instance is created.
     */
    initializer: function () {
        // This needs to be called in each view controller
        this.setup(this.get('container'), this.get('controllerId'));
    },

    /**
     * Panel Did Load is called when the panel is attached to the controller
     */
    onPanelDidLoad: function () {
        // Vars
        var model = this.get('model');

        // Attach events
        this._attachEvents();

        // Load the model
        model.load();
    },

    /**
     * Panel Did Appear is called when the panel did appear on the screen
     */
    onPanelDidAppear: function () {
        // Subscribe to key up event
        this._subscribeKeyUp();
    },

    /**
     * Panel Did Disappear is called when the panel disappeared from the screen
     */
    onPanelDidDisappear: function () {
        // Detach the key up event
        this._detachKeyUp();
    },

    /**
     * Attaches events to DOM elements from container
     *
     * @private
     */
    _attachEvents: function () {

        // Vars
        var searchButton = this.get('searchButton'),
            listButton = this.get('listButton');

        // Local events
        searchButton.on('click', this._onSearchClicked, this);
        listButton.on('click', this._onSearchClosed, this);

        // Global events
        Y.on('presencesChanged', this._onPresencesChanged, this);
        Y.on('buddySelected', this._onBuddySelected, this);
        Y.on('connectionError', this._onConnectionError, this);
        Y.on('connectionOK', this._onConnectionOK, this);
        Y.on('jabberConnected', this._onJabberConnected, this);
        Y.on('jabberDisconnected', this._onJabberDisconnected, this);
    },

    /**
     * Subscribes to the global key up event
     *
     * @private
     */
    _subscribeKeyUp: function () {
        if (Y.one(Y.config.doc)) {
            // Detach the previous subscription
            this._detachKeyUp();
            // Save the subscription to the key up event
            this.set('keyUpSubscription', Y.one(Y.config.doc).on('keyup', this._onKeyPress, this));
        }
    },

    /**
     * Detaches the subscription to the global key up event
     *
     * @private
     */
    _detachKeyUp: function () {
        // Vars
        var keyUpSubscription = this.get('keyUpSubscription');

        if (keyUpSubscription) {
            keyUpSubscription.detach();
        }
    },

    /**
     * Shows the search panel
     *
     * @private
     */
    _showSearchPanel: function () {
        // Vars
        var panelTitle = this.get('panelTitle'),
            searchPanelView = this.get('searchPanelView'),
            searchPanelContainer = searchPanelView.get('container'),
            instance = this;

        // Only show the search panel if not in document already
        if (!searchPanelContainer.inDoc()) {
            // Add search panel to the container
            panelTitle.insert(searchPanelContainer, 'after');
        }

        // Show search panel view
        searchPanelView.showView(function () {
            // Toggle search/list button
            instance._hideSearchButton();
            instance._showListButton();
        });

    },

    /**
     * Hides the search panel
     *
     * @private
     */
    _hideSearchPanel: function () {

        // Vars
        var searchPanelView = this.get('searchPanelView'),
            searchPanelContainer = searchPanelView.get('container'),
            instance = this;

        // Only hide the search panel if it's in the document
        if (searchPanelContainer.inDoc()) {
            // Hide search panel
            searchPanelView.hideView(function () {
                // Remove the search panel from DOM
                searchPanelContainer.remove();
                // Toggle search/list button
                instance._hideListButton();
                instance._showSearchButton();
            });
        }
    },

    /**
     * Shows the search button in panel title
     *
     * @private
     */
    _showSearchButton: function () {
        // Vars
        var searchButton = this.get('searchButton');
        // Show the button
        Y.LIMS.Core.Util.show(searchButton);
    },

    /**
     * Hides the search button in panel title
     *
     * @private
     */
    _hideSearchButton: function () {
        // Vars
        var searchButton = this.get('searchButton');
        // Hide the button
        Y.LIMS.Core.Util.hide(searchButton);
    },

    /**
     * Shows the list button in panel title
     *
     * @private
     */
    _showListButton: function () {
        // Vars
        var listButton = this.get('listButton'),
            minimizeButton = this.get('minimizeButton');

        if (!listButton.inDoc()) {
            minimizeButton.insert(listButton, 'after');
        }

        // Show the button
        Y.LIMS.Core.Util.show(listButton);
    },

    /**
     * Hides the list button in panel title
     *
     * @private
     */
    _hideListButton: function () {
        // Vars
        var listButton = this.get('listButton');
        // Hide the button
        Y.LIMS.Core.Util.hide(listButton);
    },

    /**
     * Called when any user has changed the presence
     *
     * @private
     */
    _onPresencesChanged: function (event) {
        // Vars
        var model = this.get('model'),
            buddies = event.buddyList || [];

        // Update presence at each group
        Y.Array.each(model.toArray(), function(groupModel) {
            groupModel.updatePresences(buddies);
        });
    },

    /**
     * Buddy selected event. Called whenever the user selects one of the buddies from
     * the group list
     *
     * @private
     */
    _onBuddySelected: function () {
        // Hide the controller
        this.dismissViewController();
        // If the buddy was selected from search panel, hide it
        this._hideSearchPanel();
    },

    /**
     * Called when a search button in panel title is pressed
     *
     * @private
     */
    _onSearchClicked: function () {
        // Show the search panel
        this._showSearchPanel();
    },

    /**
     * Called when the search panel is closed
     *
     * @private
     */
    _onSearchClosed: function () {
        // Hide the search panel
        this._hideSearchPanel();
    },

    /**
     * Called whenever the user presses any kay in the browser
     *
     * @param event
     * @private
     */
    _onKeyPress: function (event) {
        // User pressed ESC key
        if (event.keyCode === 27) {
            // Hide the search panel
            this._hideSearchPanel();
        }
    },

    /**
     * Called whenever an error with connection occurred
     *
     * @private
     */
    _onConnectionError: function () {
        this.showError('connectionError', Y.LIMS.Core.i18n.values.connectionErrorMessage);
    },

    /**
     * Called when there are no more connection errors
     *
     * @private
     */
    _onConnectionOK: function () {
        this.hideError('connectionError');
    },

    /**
     * Called when the jabber is connected
     *
     * @private
     */
    _onJabberConnected: function () {
        this.hideError('jabberError');
    },

    /**
     * Called when the jabber is disconnected
     *
     * @private
     */
    _onJabberDisconnected: function () {
        this.showError('jabberError', Y.LIMS.Core.i18n.values.jabberDisconnectedMessage);
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Each controller must have an id
         *
         * {string}
         */
        controllerId: {
            value: "groups"
        },

        /**
         * Controller main container
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Controller model
         *
         * {Y.LIMS.Model.GroupListModel}
         */
        model: {
            valueFn: function () {
                return new Y.LIMS.Model.GroupListModel();
            }
        },

        /**
         * Activity indicator spinner node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one(".panel-content .preloader");
            }
        },

        /**
         * Group list container node
         *
         * {Node}
         */
        panelContentContainer: {
            valueFn: function () {
                return this.get('container').one('.panel-content');
            }
        },

        /**
         * Panel title node
         *
         * {Node}
         */
        panelTitle: {
            valueFn: function () {
                return this.get('container').one('.panel-title');
            }
        },

        /**
         * View that takes care of the search panel
         *
         * {Y.LIMS.View.GroupSearchView}
         */
        searchPanelView: {
            valueFn: function () {
                // Vars
                var model = this.get('searchPanelModel');
                // Create new instance of the view
                return new Y.LIMS.View.GroupSearchView({
                    model: model
                });
            }
        },

        /**
         * Model related to the search panel
         *
         * {Y.LIMS.Model.BuddySearchListModel}
         */
        searchPanelModel: {
            valueFn: function () {
                return new Y.LIMS.Model.BuddySearchListModel();
            }
        },

        /**
         * Search button in panel title node
         *
         * {Node}
         */
        searchButton: {
            valueFn: function () {
                return this.get('container').one('.panel-title .panel-button.search');
            }
        },

        /**
         * Minimize button in panel title node
         *
         * {Node}
         */
        minimizeButton: {
            valueFn: function () {
                return this.get('container').one('.panel-title .panel-button.minimize');
            }
        },

        /**
         * Search button in panel title node
         *
         * {Node}
         */
        listButton: {
            valueFn: function () {
                return Y.Node.create(this.listButtonTemplate);
            }
        },

        /**
         * View for the group list
         *
         * {Y.LIMS.View.GroupListView}
         */
        groupListView: {
            valueFn: function () {
                // Vars
                var container = this.get('panelContentContainer'),
                    model = this.get('model'),
                    activityIndicator = this.get('activityIndicator');

                // Create view
                return new Y.LIMS.View.GroupListView({
                    container: container,
                    model: model,
                    activityIndicator: activityIndicator
                });
            }
        },

        /**
         * Properties object
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        },

        /**
         * Cached global key up subscription
         *
         * {event}
         */
        keyUpSubscription: {
            value: null // to be set
        },

        /**
         * An instance of poller that periodically refreshes models that are subscribed
         *
         * {Y.LIMS.Core.Poller}
         */
        poller: {
            value: null // to be set
        }
    }
});
