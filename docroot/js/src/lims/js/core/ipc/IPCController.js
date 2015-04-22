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
    openConversationEventId: 'lims:openConversation',
    sendMessageEventId: 'lims:sendMessage',
    readPresenceEventId: 'lims:readPresence',
    presenceUpdatedEvent: 'lims:presenceUpdated',
    unreadMessagesCountUpdated: 'lims:unreadMessagesCountUpdated',

    // Max # of buddies for the read presence request
    maxReadPresence: 100,

    /**
     * Called on initialization
     */
    initializer: function () {
        // Vars
        var publisher = this.get('publisher');

        // Fire IPC Ready
        publisher.fire(this.readyEventId);

        // Attach events
        this._attachEvents();

        // The method needs to be called here because IPCController wasn't
        // instantiated when notification object called the first event
        // thus we need to read it now
        this._onUnreadMessagesUpdate();
    },

    /**
     * Attach events to elements
     */
    _attachEvents: function () {
        // Vars
        var publisher = this.get('publisher');

        // IPC events
        publisher.on(this.createConversationEventId, this._onCreateConversation, this);
        publisher.on(this.openConversationEventId, this._onOpenConversation, this);
        publisher.on(this.sendMessageEventId, this._onSendMessage, this);
        publisher.on(this.readPresenceEventId, this._onReadPresence, this);

        // Global events
        Y.on('presencesChanged', this._onPresencesChanged, this);
        Y.on('unreadMessagesUpdate', this._onUnreadMessagesUpdate, this);
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
     * Called on openConversation IPC event
     *
     * @param event
     * @private
     */
    _onOpenConversation: function (event) {

        // Vars
        var success = Y.LIMS.Core.Util.validateFunction(event.success),
            failure = Y.LIMS.Core.Util.validateFunction(event.failure),
            model,
            data = event.data || null;

        // Validate
        if (!data || !data.conversationId) {
            // Failure
            failure(Y.LIMS.Core.IPCErrorCode.wrongInput, 'Pass a data object with conversationId');
            // End here
            return;
        }

        // Create model
        model = new Y.LIMS.Model.ConversationModel({
            conversationId: data.conversationId
        });

        // Read the conversation
        model.load(function (err) {
            // Vars
            var code;

            if (err) {

                // Map the error code
                if (err.get('code') === 403) {
                    code = Y.LIMS.Core.IPCErrorCode.forbidden;
                } else if (err.get('code') === 404) {
                    code = Y.LIMS.Core.IPCErrorCode.notFound;
                } else {
                    code = Y.LIMS.Core.IPCErrorCode.serverError;
                }

                // Failure
                failure(code, err.get('message'));
                // End here
                return;
            }

            // Fire event
            Y.fire('conversationSelected', {
                conversation: model,
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

            // Success
            success();
        });
    },

    /**
     * Called on sendMessage IPC event
     *
     * @param event
     * @private
     */
    _onSendMessage: function (event) {

        // Vars
        var success = Y.LIMS.Core.Util.validateFunction(event.success),
            failure = Y.LIMS.Core.Util.validateFunction(event.failure),
            properties = this.get('properties'),
            buddyDetails = this.get('buddyDetails'),
            model,
            data = event.data || null;

        // Validate
        if (!data || !data.conversationId || !data.message) {
            // Failure
            failure(Y.LIMS.Core.IPCErrorCode.wrongInput, 'Pass a data object with conversationId');
            // End here
            return;
        }

        // Create model
        model = new Y.LIMS.Model.ConversationModel({
            conversationId: data.conversationId
        });

        // Read the conversation
        model.load(function (err) {
            // Vars
            var code,
                message,
                offset = properties.getServerTimeOffset(),  // Server time offset
                now = new Date().getTime(),                 // Current client time
                createdAt;                                  // Created at timestamp

            if (err) {

                // Map the error code
                if (err.get('code') === 403) {
                    code = Y.LIMS.Core.IPCErrorCode.forbidden;
                } else if (err.get('code') === 404) {
                    code = Y.LIMS.Core.IPCErrorCode.notFound;
                } else {
                    code = Y.LIMS.Core.IPCErrorCode.serverError;
                }

                // Failure
                failure(code, err.get('message'));
                // End here
                return;
            }

            // Add the offset to the created at timestamp
            createdAt = now - offset;

            // Create message
            message = new Y.LIMS.Model.MessageItemModel({
                messageType: 'REGULAR',
                from: buddyDetails,
                body: data.message,
                createdAt: createdAt
            });

            // Add new message to the conversation
            model.addMessage(message, function (err) {
                // Failure
                if (err) {
                    failure(err);
                }
                // Success
                else {

                    // Refresh conversation so the other conversation controllers
                    // can read the message immediately
                    Y.fire('refreshConversation', {
                        conversationId: model.get('conversationId')
                    });

                    // Call success
                    success({
                        conversationId: model.get('conversationId'),
                        message: data.message
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
            publisher.fire(this.presenceUpdatedEvent, {
                users: users
            });
        }
    },

    /**
     * Called when unread messages badge changes
     *
     * @private
     */
    _onUnreadMessagesUpdate: function () {

        // Vars
        var notification = this.get('notification'),
            count = notification.get('unreadMessagesCount'),
            publisher = this.get('publisher');

        if (count !== null) {
            publisher.fire(this.unreadMessagesCountUpdated, {
                count: count
            });
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
        },

        /**
         * Notification object responsible for the incoming message notification
         *
         * {Y.LIMS.Core.Notification}
         */
        notification: {
            value: null // to be set
        },

        /**
         * An instance of the portlet properties object
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        }
    }
});
