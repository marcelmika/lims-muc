/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Settings Model
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.SynchronizationModel = Y.Base.create('synchronizationModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Starts the synchronization with SUC
     *
     * @param callback (error, inProgress)
     */
    synchronizeSUC: function (callback) {

        var instance = this;

        // Do the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "SynchronizeSuc"
            },
            on: {
                success: function (id, o) {

                    // Synchronization was successfully finished
                    if (o.status === 200) {
                        callback(null, false);
                    }
                    // Synchronization is in progress
                    else if (o.status === 204) {
                        callback(null, true);
                    }
                    // Synchronization error
                    else if (o.status === 304) {
                        callback("cannot synchronize", false);
                    }
                    // Unknown success code
                    else {
                        callback(null);
                    }

                    // Fire event
                    instance.fire('synchronizationSuccess');

                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }

                    if (callback) {
                        callback("Cannot synchronize");
                    }

                    instance.fire('synchronizationFailure');
                }
            }
        });
    },

    /**
     * Starts tje synchronization with Chat Portlet
     *
     * @param callback (error, inProgress)
     */
    synchronizeChatPortlet: function (callback) {

        var instance = this;

        // Do the request
        Y.io(this.getServerRequestUrl(), {
            method: "POST",
            data: {
                query: "SynchronizeChatPortlet"
            },
            on: {
                success: function (id, o) {

                    // Synchronization was successfully finished
                    if (o.status === 200) {
                        callback(null, false);
                    }
                    // Synchronization is in progress
                    else if (o.status === 204) {
                        callback(null, true);
                    }
                    // Synchronization error
                    else if (o.status === 304) {
                        callback("cannot synchronize", false);
                    }
                    // Unknown success code
                    else {
                        callback(null);
                    }

                    // Fire event
                    instance.fire('synchronizationSuccess');

                },
                failure: function (x, o) {
                    // If the attempt is unauthorized session has expired
                    if (o.status === 401) {
                        // Notify everybody else
                        Y.fire('userSessionExpired');
                    }

                    if (callback) {
                        callback("Cannot synchronize");
                    }

                    instance.fire('synchronizationFailure');
                }
            }
        });
    }

});
