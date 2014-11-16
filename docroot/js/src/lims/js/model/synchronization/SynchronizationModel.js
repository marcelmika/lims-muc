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
     * Starts tje synchronization
     *
     * @param callback
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

                    console.log(o);

                    if (callback) {
                        callback(null);
                    }

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
