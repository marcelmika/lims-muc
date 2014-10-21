/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Marcel Mika, marcelmika.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
        var instance = this,    // Remember the instance
            response;           // Response from the server

        switch (action) {

            // Called whenever the load() method is called. Sends a request
            // to server which loads a list of opened conversations
            case 'read':

                // Send the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "ReadConversations"
                    },
                    timeout: 30000, // 30 seconds
                    on: {
                        success: function (id, o) {
                            // Deserialize response
                            response = Y.JSON.parse(o.responseText);

                            console.log(response);

                            // Update conversation list
                            instance._updateConversationList(response);

                            // Call success
                            callback(null);
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
     * Updates list of conversations from the response
     *
     * @param response
     */
    _updateConversationList: function (response) {

        // Vars
        var index, conversation, model, conversationModels = [];

        // Create model instance from response
        for (index = 0; index < response.length; index++) {
            conversation = response[index];
            // Map model from the json response
            model = new Y.LIMS.Model.ConversationModel(conversation);
            // Include only those conversations that have last message
            if (model.get('lastMessage')) {
                conversationModels.push(new Y.LIMS.Model.ConversationModel(conversation));
            }
        }

        // Repopulate the list
        this.reset(conversationModels);

        // Fire the event
        this.fire('conversationFeedUpdated', this);
    }

}, {
    ATTRS: {

    }
});