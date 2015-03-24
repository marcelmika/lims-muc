/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Group Model
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single group item.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.GroupModel = Y.Base.create('groupModel', Y.Model, [Y.LIMS.Model.ModelExtension], {

    /**
     * Returns true if all buddies are loaded and there is no need
     * to load anything else
     *
     * @return {boolean}
     */
    hasReachedBottom: function () {
        // Vars
        var page = this.get('page');

        // Reached bottom when there is nothing else to load
        return page.get('number') + 1 === page.get('totalPages');
    },

    /**
     * Custom sync action
     *
     * @param action
     * @param options
     * @param callback
     */
    sync: function (action, options, callback) {

        // Vars
        var parameters,
            number,
            readMore = options.readMore || false,
            groupId = this.get('groupId'),
            listStrategy = this.get('listStrategy'),
            page = this.get('page'),
            instance = this;

        switch (action) {


            ////////////////////////////////////////////////////////////////////////
            // READ
            ///////////////////////////////////////////////////////////////////////

            case 'read':

                // Increase page number if read more options is set to true,
                // otherwise start from beginning
                number = readMore ? page.get('number') + 1 : 0;

                // Set parameters
                parameters = Y.JSON.stringify({
                    groupId: groupId,
                    listStrategy: listStrategy,
                    number: number
                });

                // Send request to server
                Y.io(this.getServerRequestUrl(), {
                    method: "GET",
                    data: {
                        query: "GetGroup",
                        parameters: parameters
                    },
                    timeout: 30000, // 30 seconds
                    on: {
                        success: function (id, o) {

                            // Vars
                            var response, // Parsed response
                                buddies = instance.get('buddies');

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

                            // If read more flag was set
                            if (readMore) {
                                // Add buddies to the already existing list
                                buddies.add(response.buddies);
                                // Append it to the response, thus it can be parsed properly
                                if (response.buddies) {
                                    response.buddies = buddies;
                                }
                            }

                            // Callback
                            callback(null, response);
                        },
                        failure: function (x, o) {
                            // If the attempt is unauthorized session has expired
                            if (o.status === 401) {
                                // Notify everybody else
                                Y.fire('userSessionExpired');
                            }

                            callback("Group Model Item Error");
                        }
                    }
                });

                return;

            case 'create':
            case 'update':
            case 'delete':
                callback('Invalid action');
                break;
        }
    }

}, {
    ATTRS: {

        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        /**
         * Id of the group
         *
         * {string}
         */
        groupId: {
            value: "" // default value
        },

        /**
         * Name of the group
         *
         * {string}
         */
        name: {
            value: "" // default value
        },

        /**
         * List of buddies related to the group
         *
         * {Y.LIMS.Model.BuddyModelList}
         */
        buddies: {
            /**
             * Default value
             *
             * @return {Y.LIMS.Model.BuddyModelList}
             */
            valueFn: function () {
                return new Y.LIMS.Model.BuddyModelList();
            },

            /**
             * Setter
             *
             * @param object
             * @return {Y.LIMS.Model.BuddyModelList}
             */
            setter: function (object) {

                // List cannot be null
                if (!object) {
                    return new Y.LIMS.Model.BuddyModelList();
                }

                // Create a model instance from object
                if (object.name !== "buddyModelList") {
                    return new Y.LIMS.Model.BuddyModelList({
                        items: object
                    });
                }
                // Object is already an instance of BuddyModelList
                return object;
            }
        },

        /**
         * Pagination object related to group buddies
         *
         * {Y.LIMS.Model.PageModel}
         */
        page: {
            /**
             * Default value
             *
             * @return {Y.LIMS.Model.PageModel}
             */
            valueFn: function () {
                return new Y.LIMS.Model.PageModel();
            },
            /**
             * Setter
             *
             * @param object
             * @return {Y.LIMS.Model.PageModel}
             */
            setter: function (object) {

                // Page cannot be null
                if (!object) {
                    return new Y.LIMS.Model.PageModel();
                }

                // Create a model instance from object
                if (object.name !== "pageModel") {
                    return new Y.LIMS.Model.PageModel(object);
                }

                // Object is already an instance of PageModel
                return object;
            }
        },

        /**
         * List strategy related to the group
         *
         * {string}
         */
        listStrategy: {
            value: null // to be set
        },

        /**
         * Social relation type
         *
         * {Y.LIMS.Model.GroupSocialRelationType}
         */
        socialRelation: {
            /**
             * Setter
             *
             * @param object
             * @returns {Y.LIMS.Model.GroupSocialRelationType|null}
             */
            setter: function (object) {
                // No social relation was set
                if (!object) {
                    return null;
                }

                // Create a model instance from value object
                if (object.name !== "groupSocialRelationType") {
                    return new Y.LIMS.Model.GroupSocialRelationType({
                        socialRelationType: object
                    });
                }
                // Object is already an instance of GroupSocialRelationType
                return object;
            }
        }
    }
});
