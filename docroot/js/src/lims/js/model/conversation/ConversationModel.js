/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Conversation Model
 *
 * This class is responsible for data related to the conversation. Such as conversation
 * metadata, list of participants and list of messages.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ConversationModel = Y.Base.create('conversationModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Adds message to conversation. Sends request to server.
     *
     * @param message
     * @param callback
     */
    addMessage: function (message, callback) {

        // Vars
        var messageList = this.get('messageList'),  // List of messages
            offset = this.get('serverTimeOffset'),  // Server time offset
            createdAt,                              // Creation date of message
            instance = this;                        // Save scope

        // Message has a conversation id same as the conversation id stored in this model
        message.set('conversationId', this.get('conversationId'));

        // This will send the message to the server
        message.save(function (err) {
            // Trigger event if the message wasn't sent
            if (err) {
                instance.fire('messageError');
            }

            // Pass the callback
            if (callback) {
                callback(err, message);
            }
        });

        // Timestamp needs to be updated because of the possible difference between
        // server time and client time
        createdAt = message.get('createdAt');
        message.set('createdAt', createdAt + offset);

        // Add it locally. We are async here. In other words we are not
        // waiting for the response and directly add the message to the list
        // and present it to the user. If the request fails we keep the message
        // in the list and let the user to resend the message. Apparently we don't do
        // this here since this is not a responsibility of the model.
        messageList.add(message);

        // Notify about the event
        this.fire('messageAdded', {
            message: message
        });
    },

    /**
     * Closes the conversation for currently logged user. The conversation however still
     * exists in the system. Only it's not visible to the user. If the counterpart of the
     * conversation sends message to closed conversation the conversation will open again.
     */
    closeConversation: function () {

        // Vars
        var parameters = Y.JSON.stringify({
            conversationId: this.get('conversationId')
        });

        // Send the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "CloseSingleUserConversation",
                parameters: parameters
            },
            on: {
                // There isn't much we can do. If the request ends with success
                // we simply don't do anything. If it fails the user will see
                // the conversation whenever the user goes to another web page
                // and try it again.
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }
                }
            }
        });
    },

    /**
     * Resets counter of unread messages. This should happen when the user opens conversation.
     * Since it's opened all messages have been read. Thus we can reset the counter.
     */
    resetUnreadMessagesCounter: function (callback) {

        // There is no need to send anything to server if the counter is already set to zero
        if (this.get('unreadMessagesCount') === 0) {
            if (callback) {
                callback(null, this);
            }
            // End here
            return;
        }

        // Vars
        var instance = this,
            parameters = Y.JSON.stringify({
                conversationId: this.get('conversationId')
            });

        // Send the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "ResetUnreadMessagesCounter",
                parameters: parameters
            },
            on: {
                success: function () {

                    // Update unread messages count
                    instance.set('unreadMessagesCount', 0);

                    if (callback) {
                        callback(null, instance);
                    }
                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }

                    if (callback) {
                        callback('cannot reset unread messages', instance);
                    }
                }
            }
        });
    },

    /**
     * Sends a request to the server that switches position of two conversation controllers
     *
     * @param secondModel {Y.LIMS.Model.ConversationModel}
     * @param callback
     * @private
     */
    switchConversations: function (secondModel, callback) {

        // Vars
        var instance = this,
            isCreated = this.get('isCreated'),
            parameters = Y.JSON.stringify({
                firstConversationId: this.get('conversationId'),
                secondConversationId: secondModel.get('conversationId')
            });

        // Conversation is not created yet
        if (!isCreated) {

            // Wait until it's created
            this.after('isCreatedChange', function (event) {
                // Conversation was successfully created
                if (event.newVal === true) {
                    // We can now switch conversations
                    instance.switchConversations(secondModel, callback);
                }
            }, this);
        }
        // Conversation was created
        else {
            // Send the request
            Y.io(this.getServerRequestUrl(), {
                method: "POST",
                data: {
                    query: "SwitchConversations",
                    parameters: parameters
                },
                on: {
                    success: function () {

                        // Call success
                        if (callback) {
                            callback(null, instance);
                        }

                        // Fire an event
                        instance.fire('switchConversationsSuccess', instance);
                    },
                    failure: function (x, o) {
                        // If the attempt is unauthorized session has expired
                        if (o.status === 401) {
                            // Notify everybody else
                            Y.fire('userSessionExpired');
                        }

                        // Fire an event
                        instance.fire('switchConversationsError', instance);

                        // Call error
                        if (callback) {
                            callback('cannot leave conversation', instance);
                        }
                    }
                }
            });
        }
    },

    /**
     * Adds more participants to the conversation
     *
     * @param participants [Y.LIMS.Model.BuddyItemModel]
     * @param callback
     */
    addMoreParticipants: function (participants, callback) {

        // Vars
        var instance = this,
            parameters = Y.JSON.stringify({
                conversationId: this.get('conversationId')
            }),
            content = Y.JSON.stringify(participants);

        // Send the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "AddParticipants",
                parameters: parameters,
                content: content
            },
            on: {
                success: function () {
                    // Call the success
                    callback(null, instance);

                    // Fire an event
                    instance.fire('addParticipantsSuccess', instance);
                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }

                    // Fire an event
                    instance.fire('addParticipantsError', instance);

                    // Call error
                    callback('cannot add participants', instance);
                }
            }
        });
    },

    /**
     * Leaves multi user chat conversation
     *
     * @param callback
     */
    leaveConversation: function (callback) {

        // Vars
        var instance = this,
            parameters = Y.JSON.stringify({
                conversationId: this.get('conversationId')
            });

        // Send the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "LeaveConversation",
                parameters: parameters
            },
            on: {
                success: function () {
                    // Call the success
                    callback(null, instance);

                    // Fire an event
                    instance.fire('leaveConversationSuccess', instance);
                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }

                    // Fire an event
                    instance.fire('leaveConversationError', instance);

                    // Call error
                    callback('cannot leave conversation', instance);
                }
            }
        });
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
        var content,            // Content which will be sent as body in request
            parameters,         // Request parameters
            instance = this,    // Save the instance so we can call its methods in diff context
            response,           // Response from the server
            stopperId,          // Stopper message id
            readMore,           // True if the read more parameter should be included
            resetEtag,          // True if the etag should be reset
            resetStopper,       // True if the stopper id should be reset
            etag = this.get('etag');

        // Map values from options
        readMore = options ? (options.readMore || false) : false;
        resetEtag = options ? (options.resetEtag || false) : false;
        resetStopper = options ? (options.resetStopper || false) : false;

        // We need to reset the etag if we want to read more. It is quite possible
        // that the conversation hasn't changed yet. So the etag is the same. As
        // a result nothing would be returned. However we need to return larger
        // list. This could be easily done by resetting the etag property.
        if (readMore || resetEtag) {
            etag = -1;
        }

        // Whenever we want to read the first page of the messages and nothing more we need
        // to reset the stopper. If the stopper is not reset whenever the conversation etag
        // has been changed the message feed will grove infinitely.
        if (resetStopper) {
            this.set('stopperId', null);
        }

        // Read the stopper id
        stopperId = this.get('stopperId');

        switch (action) {

            // This is called whenever the conversation is created i.e whenever
            // the user clicks on any of the buddies in the group
            case 'create':

                // Fire begin event
                instance.fire('createBegin');

                // Simply take the conversation object, serialize it to json
                // and send.
                content = Y.JSON.stringify(this.toJSON());

                // Send the request
                Y.io(this.getServerRequestUrl(), {
                    method: "POST",
                    data: {
                        query: "CreateSingleUserConversation",
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
                                callback(new Y.LIMS.Model.ErrorMessage({
                                    code: 400,
                                    message: exception
                                }));
                                // Fire failure event
                                instance.fire('createError');
                                // End here
                                return;
                            }

                            // Conversation model is readable not
                            instance.set('isCreated', true);

                            // Fire success event
                            instance.fire('createSuccess');

                            // Call success
                            callback(null, instance);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Fire failure event
                            instance.fire('createError');

                            // Deserialize response
                            response = Y.JSON.parse(o.responseText);

                            // Call failure
                            callback(new Y.LIMS.Model.ErrorMessage(response));
                        }
                    }
                });
                return;

            // Called whenever the load() method is called. Sends a request
            // to server which loads a list of messages related to the conversation.
            case 'read':

                // Fire begin event
                instance.fire('readBegin');

                // Construct parameters
                parameters = Y.JSON.stringify({
                    conversationId: this.get('conversationId'),
                    etag: etag,
                    pagination: {
                        readMore: readMore,
                        stopperId: stopperId
                    }
                });

                // Send the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "ReadSingleUserConversation",
                        parameters: parameters
                    },
                    on: {
                        success: function (id, o) {
                            // If nothing has change the server return 304 (not modified)
                            // As a result we don't need to refresh anything
                            if (o.status === 304) {
                                callback(null, instance);
                                // Fire success event
                                instance.fire('readSuccess');
                                return;
                            }

                            // Deserialize
                            try {
                                // Deserialize response
                                response = Y.JSON.parse(o.responseText);
                            }
                            catch (exception) {
                                // Fire failure event
                                instance.fire('readError');
                                // JSON.parse throws a SyntaxError when passed invalid JSON
                                callback(new Y.LIMS.Model.ErrorMessage({
                                    code: 400,
                                    message: exception
                                }));
                                // End here
                                return;
                            }

                            // Update message list
                            instance.updateConversation(response, readMore);

                            // Fire success event
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

                            // Fire failure event
                            instance.fire('readError');

                            // Deserialize
                            response = Y.JSON.parse(o.responseText);

                            // Callback the error
                            callback(new Y.LIMS.Model.ErrorMessage(response));
                        }
                    }
                });

                return;

            default:
                callback('Invalid action');
        }
    },

    /**
     * Updates message list with the messages passed as a parameter.
     * Contains logic which removes duplicates and keep the correct order
     * of messages.
     *
     * @param conversation
     * @param readMore {boolean}
     */
    updateConversation: function (conversation, readMore) {

        // Vars
        var messageList = this.get('messageList'),        // List of messages
            offset = this.get('serverTimeOffset'),        // Server time offset
            createdAt,                                    // Stores message time of creation
            messageModels = [],                           // Holds message models
            notAcknowledgedModels,                        // Message models from list that are not yet acknowledged
            message,                                      // Deserialized message
            postStopperId,                                // New stopper id
            preStopperId = this.get('stopperId'),         // Previous stopper id
            index;                                        // Used for iteration

        // Update from response
        this.setAttrs({
            conversationType: conversation.conversationType,
            title: conversation.title,
            etag: conversation.etag,
            unreadMessagesCount: conversation.unreadMessagesCount,
            firstMessage: conversation.firstMessage,
            lastMessage: conversation.lastMessage,
            participants: conversation.participants
        });

        // Keep a copy of messages that haven't been sent to server yet
        notAcknowledgedModels = messageList.getNotAcknowledged();

        // Parse messages from conversation messages
        for (index = 0; index < conversation.messages.length; index++) {

            // Deserialize message
            message = new Y.LIMS.Model.MessageItemModel(conversation.messages[index]);

            // Timestamp needs to be updated because of the possible difference between
            // server time and client time
            createdAt = message.get('createdAt');
            message.set('createdAt', createdAt + offset);

            // Add message to message list
            messageModels.push(message);
        }

        // Add not yet acknowledged messages at the and.
        // Note: This is quite old school however the fastest possible way
        for (index = 0; index < notAcknowledgedModels.length; index++) {
            messageModels.push(notAcknowledgedModels[index]);
        }

        // Rewrite old models with the new ones
        messageList.reset(messageModels);

        // Save the stopper id. In other words we need to remember the first message
        // in the current feed. Thanks to that whenever the user scrolls to the top
        // we can send a request with the stopper id thus retrieve more messages.
        if (messageList.item(0)) {
            postStopperId = messageList.item(0).get('messageId');
            this.set('stopperId', postStopperId);
        }

        // Check if we have already reached the top
        if (this.get('firstMessage')) {
            if (this.get('firstMessage').get('messageId') === postStopperId) {
                this.set('reachedTop', true);
            } else {
                this.set('reachedTop', false);
            }
        }

        // Notify about the event
        this.fire('messagesUpdated', {
            messageList: messageList,
            readMore: readMore,
            postStopperId: postStopperId,
            preStopperId: preStopperId
        });
    },

    /**
     * Finds a buddy in the group model buddies
     *
     * @param buddyId
     * @return {*}
     */
    findBuddy: function(buddyId) {
        // Var
        var buddies = this.get('participants');

        if (buddies) {
            return Y.Array.find(buddies, function (buddy) {
                return buddy.get('buddyId') === buddyId;
            });
        }
    },

    /**
     * Updates presence related to the buddies
     *
     * @param updatedBuddies
     */
    updatePresences: function (updatedBuddies) {
        // Vars
        var instance = this;

        Y.Array.each(updatedBuddies, function(updatedBuddy) {
            // Search local buddy
            var buddy = instance.findBuddy(updatedBuddy.get('buddyId'));

            if (buddy) {
                buddy.set('presence', updatedBuddy.get('presence'));
                buddy.set('connected', updatedBuddy.get('connected'));
            }
        });
    }


}, {
    ATTRS: {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        /**
         * Id of the conversation
         *
         * {string}
         */
        conversationId: {
            value: null
        },

        /**
         * Type of the conversation
         *
         * {SINGLE_USER|MULTI_USER}
         */
        conversationType: {
            value: null // to be set
        },

        /**
         * Title of the conversation
         *
         * {string}
         */
        title: {
            value: ""
        },

        /**
         * Creator of the conversation
         */
        creator: {
            value: null // to be set
        },

        /**
         * Participants in the conversation
         */
        participants: {
            /**
             * Setter
             *
             * @param objects
             * @return {[]}
             */
            setter: function (objects) {

                // Nothing was passed
                if (!objects) {
                    return null;
                }

                // Empty array was passed
                if (objects.length === 0) {
                    return [];
                }

                var index, models = [objects.length];

                // Map all objects to instances of buddy model item
                for (index = 0; index < objects.length; index++) {
                    // Create a model instance from value object
                    if (objects[index].name !== "buddyModelItem") {
                        models[index] = new Y.LIMS.Model.BuddyModelItem(objects[index]);
                    } else {
                        models[index] = objects[index];
                    }
                }

                return models;
            }
        },

        /**
         * ETag related to the conversation
         *
         * {string}
         */
        etag: {
            value: null
        },

        /**
         * Number of unread messages
         *
         * {number}
         */
        unreadMessagesCount: {
            value: 0, // default value
            /**
             * Getter
             * @param value
             * @return {number}
             */
            getter: function (value) {
                // Just to be sure that the returned value will be a number
                return parseInt(value, 10);
            }
        },

        /**
         * Server time offset between the server and client
         *
         * {number}
         */
        serverTimeOffset: {
            value: null // to be set
        },

        /**
         * List of message related to the conversation
         *
         * {Y.LIMS.Model.MessageListModel}
         */
        messageList: {
            valueFn: function () {
                return new Y.LIMS.Model.MessageListModel();
            }
        },

        /**
         * First message in the conversation
         *
         * {Y.LIMS.Model.MessageItemModel}
         */
        firstMessage: {
            /**
             * Setter
             *
             * @param object
             * @return {Y.LIMS.Model.MessageItemModel|null}
             */
            setter: function (object) {

                // No first message was set
                if (!object) {
                    return null;
                }

                // Create a model instance from value object
                if (object.name !== "messageItemModel") {
                    return new Y.LIMS.Model.MessageItemModel(object);
                }

                // Value is already an instance of Y.LIMS.Model.MessageItemModel
                return object;
            }
        },

        /**
         * Last message in the conversation
         *
         * {Y.LIMS.Model.MessageItemModel}
         */
        lastMessage: {
            /**
             * Setter
             *
             * @param object
             * @return {Y.LIMS.Model.MessageItemModel|null}
             */
            setter: function (object) {
                // No last message was set
                if (!object) {
                    return null;
                }

                // Create a model instance from value object
                if (object.name !== "messageItemModel") {
                    return new Y.LIMS.Model.MessageItemModel(object);
                }

                // Value is already an instance of Y.LIMS.Model.MessageItemModel
                return object;
            }
        },

        /**
         * Id of the last visible message
         *
         * {Number}
         */
        stopperId: {
            value: null // to be set
        },

        /**
         * True if the list is already full. In other words true if the user has already
         * reached the top by going up and reading more
         *
         * {boolean}
         */
        reachedTop: {
            value: false // default value
        },

        /**
         * True if the model is created. If the conversation isn't
         * created yet on the server and we call the load()
         * method server will return 403 or 404 error.
         */
        isCreated: {
            value: true // default value
        }
    }
});
