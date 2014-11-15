/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Notification
 */
Y.namespace('LIMS.Core');

Y.LIMS.Core.Notification = Y.Base.create('notification', Y.View, [], {

    // Templates
    audioSoundTemplate: Y.one('#limsmuc-notification-audio-template').get('innerHTML'),
    embedSoundTemplate: Y.one('#limsmuc-notification-embed-template').get('innerHTML'),

    // Locations of the sound files
    mp3SoundFile: '/lims-muc-portlet/audio/notification.mp3',
    wavSoundFile: '/lims-muc-portlet/audio/notification.wav',


    /**
     * Called after the instance is initializer
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Notifies user about new messages by showing this in the title of the page.
     * If the sound is enabled it also plays a sound.
     *
     * @param lastMessage {Y.LIMS.Model.MessageItemModel}
     */
    notify: function (lastMessage) {
        // Vars
        var settings = this.get('settings');

        // Save the last message
        this.set('lastMessage', lastMessage);

        // If sound is enabled
        if (!settings.isMute()) {
            this._playSound();
        }

        // Update title
        this._updatePageTitleMessage();
    },

    /**
     * Call this method whenever the notification should be removed from the title
     */
    suppress: function () {
        // Vars
        var timer = this.get('timer');

        // Clear refresh timer
        clearTimeout(timer);
        // Return to the default page title
        Y.config.doc.title = this.get('defaultPageTitle');
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {

        // Global events
        Y.one(Y.config.win).on('focus', this._onWindowFocus, this);
        Y.one(Y.config.win).on('blur', this._onWindowBlur, this);
    },

    /**
     * Plays notification sound
     *
     * @private
     */
    _playSound: function () {
        // Vars
        var container = this.get('container'),
            audioSoundPlayer = this.get('audioSoundPlayer'),
            embedSoundPlayer = this.get('embedSoundPlayer');

        // Play via HTML5 audio tag
        if (this._canPlayAudio()) {
            // Add to doc it it's not there yet
            if (!audioSoundPlayer.inDoc()) {
                container.append(audioSoundPlayer);
            }
            // Play
            audioSoundPlayer.getDOM().play();
        }
        // Play via embed player
        else {
            if (embedSoundPlayer.inDoc()) {
                embedSoundPlayer.remove();
            }
            container.set('innerHTML', embedSoundPlayer.get('innerHTML'));
        }
    },

    /**
     * Returns true if the browser is capable of playing the sound notification
     * via HTML5 audio tag
     *
     * @returns {boolean}
     * @see http://diveintohtml5.info/everything.html
     * @private
     */
    _canPlayAudio: function () {
        // Cars
        var audio = document.createElement('audio'),
            canPlay,
            canPlayWAV,
            canPlayMP3;

        canPlay = !!audio.canPlayType;
        canPlayWAV = canPlay && !!audio.canPlayType('audio/wav; codecs="1"').replace(/no/, '');
        canPlayMP3 = canPlay && !!audio.canPlayType('audio/mpeg;').replace(/no/, '');

        // Check if browser can play either WAV or MP3
        return (canPlay && (canPlayWAV || canPlayMP3));
    },

    /**
     * Takes the last message and updates the page title
     *
     * @private
     */
    _updatePageTitleMessage: function () {
        // Vars
        var defaultPageTitle = this.get('defaultPageTitle'),
            lastMessage = this.get('lastMessage'),
            timerInterval = this.get('timerInterval'),
            timer = this.get('timer'),
            windowHasFocus = this.get('windowHasFocus'),
            properties = this.get('properties');

        // Chat is not enabled
        if (!properties.isChatEnabled()) {
            Y.config.doc.title = defaultPageTitle;
        }
        // Start message notification
        else if (!windowHasFocus && lastMessage) {

            // Clear the old timer
            clearTimeout(timer);
            // Set a new one
            this.set('timer', setInterval(function () {
                // Vars
                var title = Y.config.doc.title,
                    notificationTitle = Y.Lang.sub(Y.LIMS.Core.i18n.values.incomingMessageTitleText, {
                        0: lastMessage.get('from').get('fullName')
                    });

                // Update the title
                Y.config.doc.title = (title === defaultPageTitle ? notificationTitle : defaultPageTitle);

            }, timerInterval));
        }
    },

    /**
     * Called when the window obtains focus
     *
     * @private
     */
    _onWindowFocus: function () {
        // Remember
        this.set('windowHasFocus', true);
        // Suppress notifications
        this.suppress();
    },

    /**
     * Called when the window losses focus
     *
     * @private
     */
    _onWindowBlur: function () {
        // Remember
        this.set('windowHasFocus', false);
    }

}, {

    // Specify attributes and static properties for your View here.
    ATTRS: {

        /**
         * Main container node
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Instance of settings
         *
         * {Y.LIMS.Model.SettingsModel}
         */
        settings: {
            value: null // to be set
        },

        /**
         * Portlet properties
         *
         * {Y.LIMS.Core.Properties}
         */
        properties: {
            value: null // to be set
        },

        /**
         * Node with an audio HTML5 tag that should play the notification sound
         *
         * {Node}
         */
        audioSoundPlayer: {
            valueFn: function () {
                return Y.Node.create(Y.Lang.sub(this.audioSoundTemplate, {
                    wav: this.wavSoundFile,
                    mp3: this.mp3SoundFile
                }));
            }
        },

        /**
         * Node with en embed tag that should play the notification sound
         *
         * {Node}
         */
        embedSoundPlayer: {
            valueFn: function () {
                return Y.Node.create(Y.Lang.sub(this.embedSoundTemplate, {
                    wav: this.wavSoundFile
                }));
            }
        },

        /**
         * Last message sent to the user
         *
         * {Y.LIMS.Model.MessageItemModel}
         */
        lastMessage: {
            value: null // default value
        },

        /**
         * Cached page title
         *
         * {string}
         */
        defaultPageTitle: {
            valueFn: function () {
                return Y.config.doc.title;
            }
        },

        /**
         * True if the window has the focus
         *
         * {boolean}
         */
        windowHasFocus: {
            value: true // default value
        },

        /**
         * Timer that is used to refresh title
         *
         * {timer}
         */
        timer: {
            value: null // to be set
        },

        /**
         * Length of the timer period that is used to refresh title
         *
         * {integer}
         */
        timerInterval: {
            value: 1500 // 1,5 seconds
        }
    }
});
