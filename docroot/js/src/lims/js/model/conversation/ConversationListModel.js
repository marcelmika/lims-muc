/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * ConversationModel instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ConversationListModel = Y.Base.create('conversationListModel', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the ConversationModel class.
    model: Y.LIMS.Model.ConversationModel,


    /**
     * Returns true if the list contains conversation
     *
     * @param conversationId
     * @returns {boolean}
     */
    containsConversation: function (conversationId) {
        return this.getByConversationId(conversationId) !== null;
    },

    /**
     * Returns a conversation based on it's id
     *
     * @param conversationId
     * @returns {ConversationModel|null}
     */
    getByConversationId: function (conversationId) {

        // Vars
        var conversations;

        // Find conversation
        conversations = Y.Array.filter(this.toArray(), function (model) {
            return model.get('conversationId') === conversationId;
        });

        // Even though the conversation should be unique the filter always returns an array
        if (conversations.length > 0) {
            return conversations[0];
        }

        // Nothing was found
        return null;
    },

    /**
     * Updates list of conversations from the response
     *
     * @param response
     */
    updateConversationList: function (response) {

        // Vars
        var index,
            conversation,
            conversations = response.conversations || [],
            changedPresences = response.changedPresences || [],
            conversationModels = [],
            presencesModels = [];

        // Create model instance from response
        for (index = 0; index < conversations.length; index++) {
            conversation = conversations[index];
            conversationModels.push(new Y.LIMS.Model.ConversationModel(conversation));
        }
        // Repopulate the list
        this.reset(conversationModels);

        this.fire('conversationsUpdated', {
            conversationList: this
        });

        // Called when some of the users changed their presence
        if (changedPresences.length) {
            // Map models
            Y.Array.each(changedPresences, function (presence) {
                presencesModels.push(new Y.LIMS.Model.BuddyModelItem(presence));
            });

            this.fire('presencesChanged', {
                buddyList: presencesModels
            });
        }
    },

    /**
     * Custom sync layer
     *
     * @param action
     * @param options
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var instance = this,    // Save the instance so we can call its methods in diff context
            parameters,         // Request parameters
            response,           // Response from the server
            lastSuccessTimestamp = this.get('lastSuccessTimestamp'); // Timestamp of the last successful response

        switch (action) {

            // Called whenever the load() method is called. Sends a request
            // to server which loads a list of opened conversations
            case 'read':

                // Set parameters
                parameters = Y.JSON.stringify({
                    since: lastSuccessTimestamp
                });

                // Send the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "ReadOpenedConversations",
                        parameters: parameters
                    },
                    timeout: 30000, // 30 seconds
                    on: {
                        success: function (id, o) {

                            // Check if the poller should slow down
                            if (o.getResponseHeader('X-Slow-Down')) {
                                instance.fire('slowDown', {slowDown: true});
                            } else {
                                instance.fire('slowDown', {slowDown: false});
                            }

                            // Deserialize
                            try {
                                // Deserialize response
                                response = Y.JSON.parse(o.responseText);
                            }
                            catch (exception) {
                                // JSON.parse throws a SyntaxError when passed invalid JSON
                                callback(exception);
                                // End here
                                return;
                            }

                            // Update the timestamp
                            instance._updateLastSuccessTimestamp();

                            // Update conversation list
                            instance.updateConversationList(response);
                            // Call success
                            callback(null, response);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }
                            // Call failure
                            callback("Cannot read conversations", o);
                        }
                    }
                });

                return;

            case 'create':
            case 'delete':
            case 'update':
                break;
            default:
                callback('Invalid action');
        }
    },

    /**
     * Updates the timestamp of the successful response
     *
     * @private
     */
    _updateLastSuccessTimestamp: function () {
        // Vars
        var offset = this.get('offset'),
            offsetPadding = this.get('offsetPadding');

        // Save the new timestamp
        this.set('lastSuccessTimestamp', (new Date().getTime() - offset - offsetPadding));
    }

}, {

    ATTRS: {

        /**
         * Server time offset (in milliseconds)
         *
         * {number}
         */
        offset: {
            value: null // to be set
        },

        /**
         * Extra padding added to offset. It's useful since we want be sure
         * that we get all the presence changes
         *
         * {number}
         */
        offsetPadding: {
            value: 1000 // one second
        },

        /**
         * Timestamp of the last successful response
         *
         * {timestamp}
         */
        lastSuccessTimestamp: {
            valueFn: function () {
                // Vars
                var offset = this.get('offset'),
                    offsetPadding = this.get('offsetPadding');

                return (new Date().getTime() - offset - offsetPadding);
            }
        }
    }
});