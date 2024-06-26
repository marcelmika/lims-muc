/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Feed List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * ConversationModel instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ConversationFeedList = Y.Base.create('conversationFeedList', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the ConversationModel class.
    model: Y.LIMS.Model.ConversationModel,

    /**
     * Custom sync layer
     *
     * @param action
     * @param options
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var instance = this,            // Remember the instance
            etag = this.get('etag'),    // Etag
            parameters,                 // Parameters of the request
            response,                   // Response from the server
            readMore = options.readMore || false,
            pageSize = this.get('pageSize'),
            cachedItems = this.get('cachedItems');

        // We need to reset the etag if we want to read more. It is quite possible
        // that the conversation feed hasn't changed yet. So the etag is the same. As
        // a result nothing would be returned. However we need to return larger
        // list. This could be easily done by resetting the etag property.
        if (readMore) {
            this.set('etag', -1);
            etag = -1;
        } else {
            // If we don't want to read more the page size should be set to null. If the server
            // returns 304 not modified nothing will be changed. However, if there are new conversations
            // page should be reset so we are not going to refresh the whole feed. This is fine since
            // if there are new conversations the feed will scroll to top.
            pageSize = null;
        }

        switch (action) {

            // Called whenever the load() method is called. Sends a request
            // to server which loads a list of opened conversations
            case 'read':

                // Let the listeners know that we have began
                instance.fire('readBegin');

                // Set parameters
                parameters = Y.JSON.stringify({
                    // Send etag to server so it knows if it should send groups again or we should keep
                    // the old cached values
                    etag: etag,
                    pagination: {
                        readMore: readMore,
                        pageSize: pageSize
                    }
                });

                // Send the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "ReadConversations",
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

                            // If nothing has change the server returns 304 (not modified)
                            // As a result we don't need to refresh anything
                            if (o.status === 304) {
                                // Return callback
                                callback(null, cachedItems);
                                // Call the success
                                instance.fire('readSuccess');
                                // End here
                                return;
                            }

                            // Deserialize
                            try {
                                // Deserialize response
                                response = Y.JSON.parse(o.responseText);
                            }
                            catch (exception) {
                                // Clear etag otherwise when we load the data again it
                                // might still be cached
                                instance.set('etag', -1);
                                // Call error
                                instance.fire('readError');
                                // JSON.parse throws a SyntaxError when passed invalid JSON
                                callback(exception);
                                // End here
                                return;
                            }


                            // Update conversation list
                            instance._updateConversationList(response, readMore);
                            // Call success
                            instance.fire('readSuccess');

                            // Call success
                            callback(null, instance);

                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Clear etag otherwise when we load the data again it
                            // might still be cached
                            instance.set('etag', -1);

                            // Call error
                            instance.fire('readError');

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
     * Updates list of conversations from the response
     *
     * @param response
     * @param readMore true if the request was of a read more type
     */
    _updateConversationList: function (response, readMore) {

        // Vars
        var conversations = response.conversations,
            etag = this.get('etag'),
            index,
            conversation,
            conversationModels = [];


        // Set current size
        this.set('pageSize', response.pageSize || null);
        this.set('maxSize', response.maxSize || null);

        // Update conversation list only if the etag has changed
        if (response.etag !== null && etag.toString() !== response.etag.toString()) {

            // Save new etag
            this.set('etag', response.etag);

            // Create model instance from response
            for (index = 0; index < conversations.length; index++) {
                conversation = conversations[index];
                // Map model from the json response
                conversationModels.push(new Y.LIMS.Model.ConversationModel(conversation));
            }

            // Repopulate the list
            this.reset(conversationModels);
            // Cache items
            this.set('cachedItems', conversationModels);

            // Fire the event
            this.fire('conversationFeedUpdated', {
                readMore: readMore
            });
        }

    }

}, {
    ATTRS: {

        /**
         * Etag of the conversation feed. This is used for caching. If the requested etag
         * is the same like the one currently cached there is no need to send
         * the data.
         */
        etag: {
            value: -1 // default value
        },

        /**
         * Current size of the list
         *
         * {number}
         */
        pageSize: {
            value: null // to be set
        },

        /**
         * Maximal size of the list
         *
         * {number}
         */
        maxSize: {
            value: null // to be set
        },

        /**
         * Returns true if the list has the full size already
         *
         * {boolean}
         */
        reachedTop: {
            getter: function () {
                return this.get('pageSize') === this.get('maxSize');
            }
        },

        /**
         * Cached items that should be returned if server responses
         * with the not modified status
         */
        cachedItems: {
            value: [] // default value
        }

    }
});