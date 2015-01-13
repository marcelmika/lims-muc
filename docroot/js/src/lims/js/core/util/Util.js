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

Y.namespace('LIMS.Core');

/**
 * Utility functions
 */
var Util = {

    /**
     * Decodes html entities
     *
     * @param html
     * @return {string}
     */
    htmlDecode: function (html) {
        // Vars
        var div = Y.Node.create("<div/>");
        // Set the encoded html
        div.set('innerHTML', html);
        // Return decoded html
        return div.get('innerHTML');
    },

    /**
     * Returns first character of the text
     *
     * @param text
     * @return {string}
     */
    firstCharacter: function (text) {
        // Vars
        var decoded = this.htmlDecode(Y.Lang.trim(text));

        return decoded && decoded.length > 0 ? decoded.charAt(0) : '';
    },

    /**
     * Calculates height of the node that can have absolute position and
     * can be hidden. If you count the height directly it will return zero.
     * Thus it first needs to be added to DOM, visibility needs to be set to hidden.
     * We can then calculate the actual height. After the calculation the element is removed
     * from DOM.
     *
     * @param node
     */
    calculateHeight: function (node) {
        // Vars
        var height,
            isInDoc = node.inDoc();

        // Cover it (set visibility to hidden) so it's not going to be visible to the user
        node.addClass('covered');

        // It is possible that the node it's not yet in DOM
        if (!isInDoc) {
            // Add it to the body so we can calculate the height
            Y.one('body').append(node);
        }

        // Count the height
        height = node.get('offsetHeight');
        // Uncover the node
        node.removeClass('covered');

        // If the node wasn't in the DOM
        if (!isInDoc) {
            // Remove it from the DOM
            node.remove();
        }

        return height;
    },

    /**
     * Returns true if node is hidden
     *
     * @param node {Node}
     * @return {boolean}
     */
    isHidden: function (node) {
        return node.hasClass('hide');
    },

    /**
     * Returns true if the value is integer
     *
     * @param value
     * @return {boolean}
     */
    isInteger: function (value) {
        return !isNaN(value) && (parseInt(value, 10) === parseFloat(value));
    },

    /**
     * Shows node
     *
     * @param node {Node}
     */
    show: function (node) {
        node.removeClass('hide');
    },

    /**
     * Hides node
     *
     * @param node {Node}
     */
    hide: function (node) {
        node.addClass('hide');
    },

    /**
     * Takes the string and makes all links clickable
     *
     * @param text
     * @return {string}
     */
    linkify: function (text) {

        // http://, https://, ftp://
        var urlPattern = /\b(?:https?|ftp):&#x2F;&#x2F;[a-z0-9-+&@#\/%?=~_|!:,.;]*[a-z0-9-+&@#\/%=~_|]/gim,
        // Email addresses
            emailAddressPattern = /[\w.]+@[a-zA-Z_\-]+?(?:\.[a-zA-Z]{2,6})+/gim;

        return text
            .replace(urlPattern, '<a class="link" target="_blank" href="$&">$&</a>')
            .replace(emailAddressPattern, '<a class="link" target="_blank" href="mailto:$&">$&</a>');
    },

    /**
     * Creates has code from the input
     *
     * @see https://github.com/garycourt/murmurhash-js
     * @param str
     * @param seed
     * @return {*}
     */
    hash: function (str, seed) {

        /*jslint bitwise: true */
        var
            l = str.length,
            h = seed ^ l,
            i = 0,
            k;

        while (l >= 4) {
            k =
                ((str.charCodeAt(i) & 0xff)) |
                ((str.charCodeAt(++i) & 0xff) << 8) |
                ((str.charCodeAt(++i) & 0xff) << 16) |
                ((str.charCodeAt(++i) & 0xff) << 24);

            k = (((k & 0xffff) * 0x5bd1e995) + ((((k >>> 16) * 0x5bd1e995) & 0xffff) << 16));
            k ^= k >>> 24;
            k = (((k & 0xffff) * 0x5bd1e995) + ((((k >>> 16) * 0x5bd1e995) & 0xffff) << 16));

            h = (((h & 0xffff) * 0x5bd1e995) + ((((h >>> 16) * 0x5bd1e995) & 0xffff) << 16)) ^ k;

            l -= 4;
            ++i;
        }

        switch (l) {
            case 3:
                h ^= (str.charCodeAt(i + 2) & 0xff) << 16;
                break;
            case 2:
                h ^= (str.charCodeAt(i + 1) & 0xff) << 8;
                break;
            case 1:
                h ^= (str.charCodeAt(i) & 0xff);
                h = (((h & 0xffff) * 0x5bd1e995) + ((((h >>> 16) * 0x5bd1e995) & 0xffff) << 16));
                break;
        }

        h ^= h >>> 13;
        h = (((h & 0xffff) * 0x5bd1e995) + ((((h >>> 16) * 0x5bd1e995) & 0xffff) << 16));
        h ^= h >>> 15;

        return h >>> 0;
    }

};

/**
 * Utility functions
 *
 * @example Y.LIMS.Core.Util.htmlDecode(text)
 */
Y.LIMS.Core.Util = Util;