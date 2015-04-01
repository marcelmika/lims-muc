/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Created by marcelmika on 24/10/14.
 */

Y.namespace('LIMS.Model');

/**
 * Utility functions for conversation model class
 */
var ConversationModelUtil = {

    /**
     * Each single user chat conversation is represented by the unique string ID.
     * Such ID is composed by participants screenNames separated by "_" sign. First, we need to sort
     * both participants names alphabetically and then separate them with "_". If we don't do that we
     * will have two conversations e.g. A_B and B_A, however both conversations are between the same people.
     * So if we first sort by the screenName we will always have A_B id for conversation even though B created
     * the conversation.
     *
     * @param firstScreenName
     * @param secondScreenName
     * @returns {string} Conversation ID
     */
    generateSUCConversationId: function (firstScreenName, secondScreenName) {
        // Vars
        var screenNames = [firstScreenName, secondScreenName];

        // Sort screen names alphabetically
        screenNames.sort();

        // Return new conversation Id
        return screenNames[0] + "_" + screenNames[1];
    },

    /**
     * Each multi user chat conversation is represented by the unique string ID.
     * Such ID is composed by all participants screen names and current time in milliseconds.
     * Given string is than hashed. To prevent collisions run the function again if the server
     * returns error message that such conversation with such id already exists.
     *
     * @param participants [Y.LIMS.Model.BuddyModelItem]
     * @param creator Y.LIMS.Model.BuddyModelItem
     * @return {*}
     * @private
     */
    generateMUCConversationId: function (participants, creator) {
        // Vars
        var index, input = '', seed, hash;

        // Concatenate screenNames
        for (index = 0; index < participants.length; index++) {
            input += participants[index].get('screenName');
        }

        // Add some salt
        seed = new Date().getTime();

        // Create hash
        hash = Y.LIMS.Core.Util.hash(input, seed);

        // Return id
        return creator.get('screenName') + "_" + hash;
    },

    /**
     * Filters given buddy from the list of buddies
     *
     * @param buddies [Y.LIMS.Model.BuddyItemModel]
     * @param buddy {Y.LIMS.Model.BuddyItemModel}
     */
    filterBuddy: function (buddies, buddy) {
        return Y.Array.filter(buddies, function (result) {
            return result.get('buddyId') !== buddy.get('buddyId');
        });
    },

    /**

     /**
     * Generates title of the conversation
     *
     * @param participants [Y.LIMS.Model.BuddyItemModel]
     */
    generateMUCTitle: function (participants) {
        // Vars
        var title;

        // This is a MUC title, there needs to be at leas two participants
        if (participants.length === 1) {
            title = participants[0].get('fullName');
        }
        // We have exactly two participants
        else if (participants.length === 2) {
            // Compose title
            title = Y.Lang.sub(Y.LIMS.Core.i18n.values.mucTitleTwoParticipants, {
                0: this.generateBuddyTitleName(participants[0]),
                1: this.generateBuddyTitleName(participants[1])
            });
        }
        // We have more than two participants
        else {
            // Compose title
            title = Y.Lang.sub(Y.LIMS.Core.i18n.values.mucTitleOthersParticipants, {
                0: this.generateBuddyTitleName(participants[0]),
                1: (participants.length - 1)
            });
        }

        return title;
    },

    /**
     * Returns name used in title
     *
     * @param buddy
     * @return {string}
     * @private
     */
    generateBuddyTitleName: function (buddy) {

        // Vars
        var name = '?'; // Default name

        // If the user has first name, use that
        if (buddy.get('firstName')) {
            name = buddy.get('firstName');
        }
        // If not use the last name
        else if (buddy.get('lastName')) {
            name = buddy.get('lastName');
        }
        // If none of those were set, use the full name
        else if (buddy.get('fullName')) {
            name = buddy.get('fullName');
        }

        // Limit the maximal size of the message to 10
        return name.substring(0, 10);
    }

};

/**
 * Utility functions
 *
 * @example Y.LIMS.Core.Util.htmlDecode(text)
 */
Y.LIMS.Model.ConversationModelUtil = ConversationModelUtil;