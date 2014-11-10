/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Model List
 *
 * The class extends Y.ModelList and customizes it to hold a list of
 * GroupModelItem instances, and to provide some convenience methods for getting
 * information about the Group items in the list.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModelList = Y.Base.create('groupModelList', Y.ModelList, [Y.LIMS.Model.ModelExtension], {

    // This tells the list that it will hold instances of the GroupModelItem class.
    model: Y.LIMS.Model.GroupModelItem,

    // Custom sync layer.
    sync: function (action, options, callback) {

        // Vars
        var parameters,
            response,
            etag = this.get('etag'),
            instance = this;

        switch (action) {

            case 'read':

                // Set parameters
                parameters = Y.JSON.stringify({
                    // Send etag to server so it knows if it should send groups again or we should keep
                    // the old cached values
                    etag: etag
                });

                // Read from server
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "GetGroupList",
                        parameters: parameters
                    },
                    timeout: 30000, // 30 seconds
                    on: {
                        success: function (id, o) {

                            // If nothing has change the server returns 304 (not modified)
                            // As a result we don't need to refresh anything
                            if (o.status === 304) {
                                // Return callback
                                callback(null);
                                // End here
                                return;
                            }

                            // Deserialize
                            try {
                                // Deserialize response
                                response = Y.JSON.parse(o.responseText);
                            }
                            catch (exception) {
                                // Clear etag otherwise when we load the data again it
                                // might still be cached
                                instance.set('etag', -1);
                                // Fire error event
                                instance.fire('groupsReadError');
                                // JSON.parse throws a SyntaxError when passed invalid JSON
                                callback(exception);
                                // End here
                                return;
                            }

                            var i, groups, group, buddies;
                            // Parse groups
                            groups = response.groups;

                            if (response.etag && etag.toString() !== response.etag.toString()) {

                                // Empty the list
                                instance.fire('groupReset');

                                instance.set('etag', response.etag);

                                // Add groups to list
                                for (i = 0; i < groups.length; i++) {
                                    // Create new group
                                    group = new Y.LIMS.Model.GroupModelItem(groups[i]);

                                    // List of buddies
                                    buddies = new Y.LIMS.Model.BuddyModelList();
                                    buddies.add(groups[i].buddies);

                                    // Add buddies to group
                                    group.set('buddies', buddies);

                                    // Add group to group list
                                    instance.add(group);
                                }

                                // Fire success event
                                instance.fire('groupsReadSuccess', {
                                    groupsList: instance
                                });
                            }

                            if (callback) {
                                callback(null);
                            }
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            // Clear etag otherwise when we load the data again it
                            // might still be cached
                            instance.set('etag', -1);

                            // Fire error event
                            instance.fire('groupsReadError');

                            if (callback) {
                                callback("group model error", o.responseText);
                            }
                        }
                    }
                });

                break;

            case 'create':
            case 'delete':
            case 'update':
                // Do nothing
                break;


            default:
                callback('Invalid action');
        }
    }

}, {
    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.
    ATTRS: {

        /**
         * Etag of the groups. This is used for caching. If the requested etag
         * is the same like the one currently cached there is no need to send
         * the data.
         */
        etag: {
            value: -1 // default value
        }
    }
});
