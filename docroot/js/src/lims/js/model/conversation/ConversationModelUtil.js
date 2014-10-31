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
     * @private
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

     /**
     * Generates title of the conversation
     * @param participants [Y.LIMS.Model.BuddyItemModel]
     * // TODO: i18n
     * @private
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

            title = this.generateBuddyTitleName(participants[0]) + " and " +
                this.generateBuddyTitleName(participants[1]);

        }
        // We have more than two participants
        else {
            title = this.generateBuddyTitleName(participants[0]) + " and " + (participants.length - 1) + " others";
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
        var name;

        // If the user has first name, use that
        if (buddy.get('firstName')) {
            name = buddy.get('firstName');
        }
        // If not use the last name
        else if (buddy.get('lastName')) {
            name = buddy.get('lastName');
        }
        // If none of those were set, use the full name
        else {
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