/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Buddy Search List Model
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * BuddyModelItem instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddySearchListModel = Y.Base.create('buddySearchListModel', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the BuddyModelItem class.
    model: Y.LIMS.Model.BuddyModelItem,

    /**
     * Updates presence related to the buddies
     *
     * @param updatedBuddies
     */
    updatePresences: function (updatedBuddies) {
        // Vars
        var instance = this;

        Y.Array.each(updatedBuddies, function (updatedBuddy) {
            // Search local buddy
            var buddy = instance.findBuddy(updatedBuddy.get('buddyId'));

            if (buddy) {
                buddy.set('presence', updatedBuddy.get('presence'));
                buddy.set('connected', updatedBuddy.get('connected'));
            }
        });
    },

    /**
     * Finds a buddy id in the group model buddies
     *
     * @param buddyId
     * @return {*}
     */
    findBuddy: function (buddyId) {
        return Y.Array.find(this.toArray(), function (buddy) {
            return buddy.get('buddyId') === buddyId;
        });
    },

    /**
     * Starts a search for buddies based on the search query
     *
     * @param searchQuery
     */
    search: function (searchQuery) {
        // Save the query
        this.set('searchQuery', searchQuery);
        // Load the source
        this.load();
    },

    /**
     * Sync layer
     *
     * @param action
     * @param options
     * @param callback
     */
    sync: function (action, options, callback) {

        var parameters,
            response,
            instance = this,
            searchQuery = this.get('searchQuery');

        switch (action) {

            case 'read':

                // Notify about the beginning of search
                this.fire('searchStarted');

                // Set parameters
                parameters = Y.JSON.stringify({
                    searchQuery: searchQuery
                });

                // Read from server
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "SearchBuddies",
                        parameters: parameters
                    },
                    on: {
                        success: function (id, o) {

                            // Vars
                            var index;

                            // Empty the list
                            instance.reset();

                            // Deserialize
                            try {
                                // Deserialize response
                                response = Y.JSON.parse(o.responseText);
                            }
                            catch (exception) {
                                // Fire error event
                                instance.fire('searchError');
                                // JSON.parse throws a SyntaxError when passed invalid JSON
                                callback(exception);
                                // End here
                                return;
                            }


                            // Deserialize buddies
                            for (index = 0; index < response.length; index++) {
                                // Add buddy to the list
                                instance.add(new Y.LIMS.Model.BuddyModelItem(response[index]));
                            }

                            // Fire success event
                            instance.fire('searchSuccess', {
                                searchList: instance
                            });
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Empty the list
                            instance.reset();
                            // Fire error event
                            instance.fire('searchError');

                            // Callback the error
                            if (callback) {
                                callback("search error", o.responseText);
                            }
                        }
                    }
                });
                break;


            case 'create':
            case 'update':
            case 'delete':
                return;

            default:
                callback('Invalid action');
        }
    }


}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        /**
         * Search query string
         *
         * {string}
         */
        searchQuery: {
            value: null // to be set
        }
    }
});

