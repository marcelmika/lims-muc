/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Inter Portlet Communication Controller
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.IPCController = Y.Base.create('IPCController', Y.Base, [], {

    // Event ids
    readyEventId: 'lims:ready',
    createConversationEventId: 'lims:createConversation',

    /**
     * Called on initialization
     */
    initializer: function () {
        // Vars
        var publisher = this.get('publisher');

        // Attach events
        this._attachEvents();

        // Fire IPC Ready
        publisher.fire(this.readyEventId);
    },

    /**
     * Attach events to elements
     */
    _attachEvents: function () {
        // Vars
        var publisher = this.get('publisher');

        // IPC events
        publisher.on(this.createConversationEventId, this._onCreateConversation, this);
    },

    /**
     * Called on createConversation IPC event
     *
     * @param event
     * @private
     */
    _onCreateConversation: function (event) {

        // Vars
        var success = Y.LIMS.Core.Util.validateFunction(event.success),
            failure = Y.LIMS.Core.Util.validateFunction(event.failure);

        // First create buddy list form the array of ids
        this._mapBuddyListFromData(event.data, function (err, participantList) {

            // Error during mapping
            if (err) {
                // Call failure
                failure(err.code, err.reason);
                // End here
                return;
            }

            // Load the participant list from server
            participantList.load(function (serverError) {

                if (serverError) {
                    // Call failure
                    failure(5000, serverError);
                    // End here
                    return;
                }

                // Exactly one buddy was selected
                if (participantList.size() === 1) {

                    // Create single user chat conversation
                    Y.fire('buddySelected', {
                        // Pass buddy
                        buddy: participantList.item(0),
                        // Success
                        success: function (model) {
                            // Call success
                            success({
                                conversationId: model.get('conversationId')
                            });
                        },
                        // Failure
                        failure: function (err) {
                            // Call failure
                            failure(5001, err);
                        }
                    });
                }
                // More than one buddy was selected
                else if (participantList.size() > 1) {
                    // Create multi user chat conversation
                    Y.fire('buddiesSelected', {
                        buddies: participantList.toArray(),
                        // Success
                        success: function (model) {
                            // Call success
                            success({
                                conversationId: model.get('conversationId')
                            });
                        },
                        // Failure
                        failure: function (err) {
                            // Call failure
                            failure(5001, err);
                        }
                    });
                }
            });
        });
    },

    /**
     * Creates Buddy model list from array of ids
     *
     * @param data
     * @param callback
     * @private
     */
    _mapBuddyListFromData: function (data, callback) {
        // Vars
        var index,
            participantList,
            buddyId = this.get('buddyDetails').get('buddyId');

        // Validate
        if (!data || data.length === 0) {
            // Failure
            callback({
                code: 4000,
                reason: 'Wrong input parameters. Pass an object with data property that contains list of user ids'
            });
            // End here
            return;
        }

        // Prepare participants
        participantList = new Y.LIMS.Model.BuddyModelList();

        // Map input to a list of buddies
        for (index = 0; index < data.length; index++) {

            // Validate user id
            if (!Y.LIMS.Core.Util.isInteger(data[index])) {
                // Failure
                callback({
                    code: 4001,
                    reason: 'Wrong input parameters. All values must be integers. Passed value: ' + data[index]
                });
                // End here
                return;
            }

            // Check if the user is not in the list
            if (data[index] === buddyId) {
                // Failure
                callback({
                    code: 4002,
                    reason: 'You cannot create conversation with yourself.'
                });
                // End here
                return;
            }

            // Add buddy to list
            participantList.add({
                buddyId: data[index]
            });
        }

        // Success
        callback(null, participantList);
    }

}, {
    // Object attributes
    ATTRS: {

        /**
         * Publisher is an object on which all the 'fire' and 'on' events are called.
         */
        publisher: {
            value: null // to be set
        },

        /**
         * Currently logged user
         */
        buddyDetails: {
            value: null // to be set
        }
    }
});
