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

                            // Callback success
                            callback(null, response);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Callback the error
                            if (callback) {
                                callback(o.status, o.responseText);
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

}, {});
