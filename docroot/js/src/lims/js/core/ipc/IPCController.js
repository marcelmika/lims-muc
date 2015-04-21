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
    readPresenceEventId: 'lims:readPresence',
    presenceUpdatedEvent: 'lims:presenceUpdated',

    // Max # of buddies for the read presence request
    maxReadPresence: 100,

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
        publisher.on(this.readPresenceEventId, this._onReadPresence, this);

        // Global events
        Y.on('presencesChanged', this._onPresencesChanged, this);
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
            failure = Y.LIMS.Core.Util.validateFunction(event.failure),
            data = event.data || null;

        // Validate
        if (!data || data.length === 0) {
            // Failure
            failure(
                Y.LIMS.Core.IPCErrorCode.wrongInput,
                'Pass an object with data property that contains list of user ids'
            );
            // End here
            return;
        }

        // First create buddy list form the array of ids
        this._mapBuddyListFromData(data, function (err, participantList) {

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
                    failure(Y.LIMS.Core.IPCErrorCode.serverError, serverError.get('message'));
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
                            failure(Y.LIMS.Core.IPCErrorCode.serverError, err.get('message'));
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
                            failure(Y.LIMS.Core.IPCErrorCode.serverError, err.get('message'));
                        }
                    });
                }
            });
        });
    },

    /**
     * Called on readPresence IPC event
     *
     * @param event
     * @private
     */
    _onReadPresence: function (event) {
        // Vars
        var success = Y.LIMS.Core.Util.validateFunction(event.success),
            failure = Y.LIMS.Core.Util.validateFunction(event.failure),
            data = event.data || null;

        // Validate
        if (!data || data.length === 0) {
            // Failure
            failure(
                Y.LIMS.Core.IPCErrorCode.wrongInput,
                'Pass an object with data property that contains list of user ids'
            );
            // End here
            return;
        }

        // Check threshold
        if (data.length > this.maxReadPresence) {
            // Failure
            failure(
                Y.LIMS.Core.IPCErrorCode.inputToBig,
                'Max number of user ids is ' + this.maxReadPresence + '. You passed ' + data.length + '.'
            );
            // End here
            return;
        }

        // First create buddy list form the array of ids
        this._mapBuddyListFromData(data, function (err, participantList) {

            // Error during mapping
            if (err) {
                // Call failure
                failure(err.code, err.reason);
                // End here
                return;
            }

            // Load the participant list from server, include presence
            participantList.load({readPresence: true}, function (serverError) {

                if (serverError) {
                    // Call failure
                    failure(Y.LIMS.Core.IPCErrorCode.serverError, serverError.get('message'));
                    // End here
                    return;
                }

                // Create response
                var response = [];
                // Map to response
                Y.Array.each(participantList.toArray(), function (participant) {
                    response.push({
                        userId: participant.get('buddyId'),
                        presence: participant.get('presence')
                    });
                });

                // Success
                success(response);
            });
        });
    },

    /**
     * Called when presences are changed
     *
     * @param event
     * @private
     */
    _onPresencesChanged: function (event) {
        // Vars
        var buddyList = event.buddyList || null,
            users = [],
            publisher = this.get('publisher');

        if (buddyList) {

            // Map local buddies to IPC users
            Y.Array.each(buddyList, function (buddy) {
                // Vars
                var presence = buddy.get('connected') ? buddy.get('presence') : 'OFFLINE';

                users.push({
                    userId: buddy.get('buddyId'),
                    presence: presence
                });
            }, this);

            // Fire the IPC event
            publisher.fire(this.presenceUpdatedEvent);
        }
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
            value;

        // Prepare participants
        participantList = new Y.LIMS.Model.BuddyModelList();

        // Map input to a list of buddies
        for (index = 0; index < data.length; index++) {

            // Get a user id
            value = Y.LIMS.Core.Util.toInteger(data[index]);

            // Validate user id
            if (!Y.LIMS.Core.Util.isInteger(value)) {
                // Failure
                callback({
                    code: Y.LIMS.Core.IPCErrorCode.wrongInput,
                    reason: 'Wrong input parameters. All values must be integers.'
                });
                // End here
                return;
            }

            // Add buddy to list
            participantList.add({
                buddyId: value
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
