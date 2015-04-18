/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Buddy Search Token Input Element
 *
 * Represents a view that holds input text field that support tokens
 */
Y.namespace('LIMS.View');

Y.LIMS.View.BuddySearchTokenInput = Y.Base.create('buddySearchTokenInput', Y.View, [Y.LIMS.Model.ModelExtension], {


    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
        // Fix autocomplete position
        this._fixAutoCompletePosition();
    },

    /**
     * Sets focus to token input
     */
    focus: function () {
        // Vars
        var tokenInputNode = this.get('tokenInputNode');

        // Focus causes issues on mobile
        if (!Y.UA.mobile) {
            // Set focus to the next token
            tokenInputNode.focus();
        }
    },

    /**
     * Takes the focus away from token input
     */
    blur: function () {
        // Vars
        var tokenInputNode = this.get('tokenInputNode');

        // Take th focus away
        tokenInputNode.blur();
    },

    /**
     * Clears token input
     */
    clear: function () {
        // Clear token input
        this.get('inputTokenPlugin').clear();
        // Clear selected buddies
        this.set('selectedBuddies', []);
        // Clear the token input
        this.get('tokenInputNode').set('value', '');
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var inputTokenPlugin = this.get('inputTokenPlugin'),
            autoCompleteNode = this.get('autoCompleteNode');

        // Local events
        inputTokenPlugin.on('removeToken', this._onTokenRemove, this);
        inputTokenPlugin.on('tokensChange', this._onTokensChange, this);

        if (autoCompleteNode) {
            autoCompleteNode.after('select', this._onAutoCompleteSelected, this);
        }
    },

    /**
     * Filters buddies duplicates. Used when the query returns buddies that have been already selected
     *
     * @param results
     * @param selectedBuddies
     * @return {*} filtered result
     * @private
     */
    _filterResults: function (results, selectedBuddies) {

        // Iterate over result, filter those that have been already selected
        return Y.Array.filter(results, function (result) {

            // Map buddy from the raw result
            var buddy = new Y.LIMS.Model.BuddyModelItem(result.raw),
                index;

            // If the buddy has been already selected return false, this will not include
            // the buddy in the list
            for (index = 0; index < selectedBuddies.length; index++) {
                if (selectedBuddies[index].get('buddyId') === buddy.get('buddyId')) {
                    return false;
                }
            }

            // No such buddy was selected, return true and thus include the buddy in the list
            return true;
        });
    },


    /**
     * Scrolls the content to bottom
     *
     * @private
     */
    _scrollToBottom: function () {
        // Vars
        var contentNode = this.get('contentNode'),
            scrollHeight = contentNode.get('scrollHeight');

        // Wait a second before we scroll to avoid blinking effect
        setTimeout(function () {
            contentNode.set('scrollTop', scrollHeight);
        }, 1);
    },

    /**
     * The autocomplete list z-index is broken in Internet Explorer 8 and 9.
     * This method will fix the issue
     *
     * @private
     */
    _fixAutoCompletePosition: function () {
        // Vars
        var ieVersion = Y.UA.ie,
            container = this.get('container').one('.yui3-aclist');

        if (container) {
            if (!(ieVersion === 8 || ieVersion === 9)) {
                // This will remove the relative position that is added to all internet
                // explorers. However in newer version (10 and above) the issue is fixed
                // and the relative position is not needed anymore. Thus move it back to
                // absolute
                container.addClass('fix-position');
            }
        }
    },

    /**
     * Called when a token is removed
     *
     * @param event
     * @private
     */
    _onTokenRemove: function (event) {
        // Vars
        var fullName = event.token || null;

        // Remove buddy from the selected buddies list
        this.set('selectedBuddies', Y.Array.filter(this.get('selectedBuddies'), function (buddy) {
            return buddy.get('fullName') !== fullName;
        }));
    },

    /**
     * Called when the tokens change
     *
     * @private
     */
    _onTokensChange: function (event) {
        // Vars
        var tokensAdded = event.newVal.length > event.prevVal.length; // True if new values were added

        if (tokensAdded) {
            // Scroll the content to bottom
            this._scrollToBottom();
        }
    },

    /**
     * Called when users selects an item from the auto complete list
     *
     * @param event
     * @private
     */
    _onAutoCompleteSelected: function (event) {
        // Vars
        var result = event.result.raw || null,
            inputTokenPlugin = this.get('inputTokenPlugin'),
            input = inputTokenPlugin.get('inputNode'),
            selectedBuddies = this.get('selectedBuddies'),
            tokens,
            buddy;

        if (result) {
            // Map buddy from the result
            buddy = new Y.LIMS.Model.BuddyModelItem(result);

            // We first need to remove all previous tokens
            // Save the tokens for now
            tokens = inputTokenPlugin.get('tokens');
            // Clear previous tokens
            inputTokenPlugin.clear();
            // Add the new token from autocomplete
            tokens.push(buddy.get('fullName'));
            // Add tokens to input node
            inputTokenPlugin.add(tokens);
            // Remember selected buddy
            selectedBuddies.push(buddy);

            // Clear the token input
            input.set('value', '');
        }
    }

}, {

    ATTRS: {

        /**
         * Container attached to the view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Input node
         *
         * {Node}
         */
        inputNode: {
            valueFn: function () {
                // Vars
                var inputNode = this.get('container').one('input'),
                // Token can be selected only by clicking on the autocomplete list
                    tokenize = !this.get('autocomplete'),
                    delimiter = this.get('autocomplete') ? '//~//~//' : ',';

                // Tokenize
                inputNode.plug(Y.Plugin.TokenInputPlugin, {
                    removeButton: true,
                    tokenizeOnBlur: tokenize,
                    tokenizeOnEnter: tokenize,
                    delimiter: delimiter
                });

                return inputNode;
            }
        },

        /**
         * Holds plugged token input
         *
         * {Y.Plugin.TokenInputPlugin}
         */
        inputTokenPlugin: {
            valueFn: function () {
                return this.get('inputNode').tokenInputPlugin;
            }
        },

        /**
         * Auto complete node
         *
         * {Y.AutoComplete|null}
         */
        autoCompleteNode: {
            valueFn: function () {

                // If the autocomplete is disable return null
                if (!this.get('autocomplete')) {
                    return null;
                }

                // Vars
                var container = this.get('container'),
                    instance = this,
                    autoCompleteContainer,
                    autoComplete = new Y.AutoComplete({
                        inputNode: this.get('tokenInputNode'),
                        resultTextLocator: 'fullName',
                        activateFirstItem: true,
                        enableCache: true,
                        maxResults: this.get('maxResults'),
                        zIndex: 10,
                        align: {
                            node: this.get('tokenListNode'),
                            points: ['tl', 'bl']
                        },
                        requestTemplate: this.get('requestTemplate'),
                        source: this.getServerRequestUrl(),
                        render: true,
                        resultFilters: function (query, results) {

                            // Vars
                            var filteredResults;
                            // First, filter duplicates
                            filteredResults = instance._filterResults(results, instance.get('selectedBuddies'));
                            // Than filter excluded buddies
                            filteredResults = instance._filterResults(filteredResults, instance.get('excludedBuddies'));

                            return filteredResults;
                        }
                    });

                // Get the autocomplete container
                autoCompleteContainer = container.one('.yui3-aclist');
                // We need to move the container a bit higher in the hierarchy, otherwise it
                // would be overlapped by the upper elements
                autoCompleteContainer.remove();
                // Now we added to the desired container
                container.append(autoCompleteContainer);

                return autoComplete;
            }
        },

        /**
         * A list of selected buddies
         *
         * [Y.LIMS.Model.BuddyModelItem]
         */
        selectedBuddies: {
            value: [] // default value
        },

        /**
         * A list of buddies that shouldn't be included in the autocomplete list
         *
         * [Y.LIMS.Model.BuddyModelItem]
         */
        excludedBuddies: {
            value: [] // default value
        },

        /**
         * Input node used for tokens
         *
         * {Node}
         */
        tokenInputNode: {
            valueFn: function () {
                return this.get('inputTokenPlugin').get('inputNode');
            }
        },

        /**
         * Token list node
         *
         * {Node}
         */
        tokenListNode: {
            valueFn: function () {
                return this.get('inputTokenPlugin').get('listNode');
            }
        },

        /**
         * Token input content node
         *
         * {Node}
         */
        contentNode: {
            valueFn: function () {
                return this.get('inputTokenPlugin').get('contentBox');
            }
        },

        /**
         * Request template for autocomplete
         *
         * {string}
         */
        requestTemplate: {
            value: "&query=SearchBuddies&parameters=%7B%22searchQuery%22%3A%22{query}%22%7D"
        },

        /**
         * Max auto complete results
         *
         * {number}
         */
        maxResults: {
            value: 6 // default value
        },

        /**
         * Current value
         *
         * []
         */
        value: {
            value: [] // to be set
        },

        /**
         * Previously set value
         *
         * []
         */
        preValue: {
            value: [] // to be set
        },

        /**
         * Set to true if you want to include autocomplete functionality
         *
         * {boolean}
         */
        autocomplete: {
            value: false // default value
        },

        /**
         * True if the token input is disabled
         *
         * {boolean}
         */
        isDisabled: {
            value: false // default value
        }
    }
});
