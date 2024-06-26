/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * GroupModel instances, and to provide some convenience methods for getting
 * information about the Group items in the list.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupListModel = Y.Base.create('groupListModel', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the GroupModel class.
    model: Y.LIMS.Model.GroupModel,

    // Custom sync layer.
    sync: function (action, options, callback) {

        // Vars
        var parameters,
            response,
            etag = this.get('etag'),
            instance = this;

        switch (action) {

            case 'read':

                // Set parameters
                parameters = Y.JSON.stringify({
                    // Send etag to server so it knows if it should send groups again or we should keep
                    // the old cached values
                    etag: etag
                });

                // Read from server
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "GetGroupList",
                        parameters: parameters
                    },
                    timeout: 30000, // 30 seconds
                    on: {
                        success: function (id, o) {

                            // If nothing has change the server returns 304 (not modified)
                            // As a result we don't need to refresh anything
                            if (o.status === 304) {
                                // End here
                                return;
                            }

                            // Server is still processing the request
                            if (o.status === 206) {
                                // Add the number of attempts to options
                                if (!options.attempts) {
                                    options.attempts = 1;
                                }
                                // This is not the first attempt
                                else {
                                    // Increase attempt count
                                    options.attempts = options.attempts + 1;
                                }

                                // If the number of attempts is more than the limit end with failure
                                if (options.attempts > 20) {
                                    callback(new Y.LIMS.Model.ErrorMessage({
                                        code: 500,
                                        message: "Too many attempts to read the group list"
                                    }));
                                    // End here
                                    return;
                                }

                                // Wait a second and call it again
                                setTimeout(function () {
                                    instance.sync(action, options, callback);
                                }, 1000);
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
                                // JSON.parse throws a SyntaxError when passed invalid JSON
                                callback(exception);
                                // End here
                                return;
                            }

                            // We need to set list properties manually
                            instance.set('etag', response.etag);
                            instance.set('loading', response.loading);
                            instance.set('listStrategy', response.listStrategy);

                            // Callback
                            callback(null, response);
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
                            instance.set('loading', false);

                            // Callback
                            callback("Group List Model Error");
                        }
                    }
                });

                break;

            case 'create':
            case 'delete':
            case 'update':
                // Do nothing
                break;


            default:
                callback('Invalid action');
        }
    },

    /**
     * Parse method is called after sync
     *
     * @param response
     * @return {*}
     */
    parse: function (response) {
        // Groups are items for the list
        return response.groups;
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Etag of the groups. This is used for caching. If the requested etag
         * is the same like the one currently cached there is no need to send
         * the data.
         *
         * {number}
         */
        etag: {
            value: -1 // default value
        },

        /**
         * Buddy list strategy
         *
         * {string}
         */
        listStrategy: {
            value: null // to be set
        },

        /**
         * Set to true if the group model is still being retrieved from jabber on the server
         *
         * {boolean}
         */
        loading: {
            value: false // default value
        }
    }
});
