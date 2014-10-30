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