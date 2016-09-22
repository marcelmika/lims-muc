/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * WebSocketClient
 *
 * Controls communication via web socket
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.WebSocketClient = Y.Base.create('webSocketClient', Y.Base, [], {

    // Web socket url template
    WEB_SOCKET_URL: '{protocol}://{host}:{port}/{endpoint}',

    /**
     * Called when the object is created
     */
    initializer: function () {

        console.log('Initializing websocket', this._retrieveSocketPath());

        var socket = new WebSocket(this._retrieveSocketPath());
        socket.onopen = function (e) {
            console.log('open', e);
        };
        socket.onclose = function (e) {
            console.log('close', e);
        };
        socket.onmessage = function (e) {
            console.log('message', e);
        };
        socket.onerror = function (e) {
            console.log('error', e);
        };
    },

    /**
     * Builds path to the socket
     *
     * @return {String}
     * @private
     */
    _retrieveSocketPath: function () {
        // Creates socket path
        return Y.Lang.sub(this.WEB_SOCKET_URL, {
            protocol: (this.get('secured') ? 'wss' : 'ws'),
            host: window.location.hostname,
            endpoint: this.get('endpoint'),
            port: this.get('port')
        });
    }

}, {

    ATTRS: {

        /**
         * Port on which the WS is running
         */
        port: {
            valueFn: function () {
                return this.get('properties').getWebsocketPort();
            }
        },

        /**
         * Endpoint for the websockets
         */
        endpoint: {
            valueFn: function () {
                return this.get('properties').getWebsocketEndpoint();
            }
        },

        /**
         * Set to true if the WSS protocol should be used
         */
        secured: {
            valueFn: function () {
                return this.get('properties').getWebsocketSecured();
            }
        },

        /**
         * Properties object
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        }
    }
});
