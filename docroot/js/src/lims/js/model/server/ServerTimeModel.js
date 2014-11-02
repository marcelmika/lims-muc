/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Server Time Model
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ServerTimeModel = Y.Base.create('settingsModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Custom sync layer
     *
     * @param action  [create|read|update|delete]
     * @param options extra parameters
     * @param callback
     */
    sync: function (action, options, callback) {

        var response,           // Response from server
            timeBeforeRequest,  // Current time before the request
            timeAfterResponse,  // Current time after the response
            networkDelayOffset, // Delta between before request and after response time
            instance = this;    // Save scope

        switch (action) {

            // Read action, called on ServerTimeModel.load() method
            case 'read':

                // Remember the time before the request. Thanks to that we can
                // count the network delay in.
                timeBeforeRequest = new Date().getTime();

                // Do the request
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "GetServerTime"
                    },
                    on: {
                        success: function (id, o) {
                            // Remember current time
                            timeAfterResponse = new Date().getTime();
                            // Count the delay offset
                            networkDelayOffset = timeAfterResponse - timeBeforeRequest;

                            // Deserialize response
                            response = Y.JSON.parse(o.responseText);

                            // Update attributes
                            instance.setAttrs(response);

                            // Count the network delay in
                            instance.set('time', instance.get('time') + networkDelayOffset);

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
                                callback("Cannot read server time", o.responseText);
                            }
                        }
                    }
                });

                break;

            case 'create':
            case 'update':
            case 'delete':
                break;

            default:
                if (callback) {
                    callback('Invalid action');
                }
                break;
        }
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Current server time
         *
         * {timestamp}
         */
        time: {
            value: null // to be set
        }
    }
});
