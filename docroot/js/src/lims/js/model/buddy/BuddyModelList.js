/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Buddy Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * BuddyModelItem instances
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.BuddyModelList = Y.Base.create('buddyModelList', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the BuddyModelItem class.
    model: Y.LIMS.Model.BuddyModelItem,

    /**
     * Maps all buddies in the list to the array of their ids
     *
     * @return {Array}
     */
    toIdArray: function () {
        // Vars
        var buddyIds = [];

        Y.Array.each(this.toArray(), function (buddy) {
            buddyIds.push(buddy.get('buddyId'));
        });

        return buddyIds;
    },

    // Custom sync layer.
    sync: function (action, options, callback) {

        // Vars
        var response,
            content,
            readPresence = options.readPresence || false,
            instance = this,
            query;

        switch (action) {

            case 'read':

                // Deserialize
                content = Y.JSON.stringify(this.toIdArray());

                // Get the correct URL
                query = readPresence ? 'IPC:ReadPresences' : 'IPC:ReadBuddies';

                // Read from server
                Y.io(this.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: query,
                        content: content
                    },
                    on: {
                        success: function (id, o) {

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
                                if (options.attempts > 10) {
                                    callback(new Y.LIMS.Model.ErrorMessage({
                                        code: 500,
                                        message: "Too many attempts to read the presence"
                                    }));
                                    // End here
                                    return;
                                }

                                // Wait a second and call it again
                                setTimeout(function () {
                                    instance.sync(action, options, callback);
                                }, 1000);

                            }
                            // Server finished loading
                            else {

                                // Deserialize
                                try {
                                    // Deserialize response
                                    response = Y.JSON.parse(o.responseText);
                                }
                                catch (exception) {
                                    // JSON.parse throws a SyntaxError when passed invalid JSON
                                    callback(new Y.LIMS.Model.ErrorMessage({
                                        code: 400,
                                        message: exception
                                    }));
                                    // End here
                                    return;
                                }

                                // Callback success
                                callback(null, response);
                            }
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Deserialize
                            response = Y.JSON.parse(o.responseText);

                            // Callback the error
                            callback(new Y.LIMS.Model.ErrorMessage(response));
                        }
                    }
                });
                break;


            case 'create':
            case 'update':
            case 'delete':
                // Not implemented
                return;

            default:
                // JSON.parse throws a SyntaxError when passed invalid JSON
                callback(new Y.LIMS.Model.ErrorMessage({
                    code: 400,
                    message: "Invalid action"
                }));
        }
    }

}, {});
