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

Y.LIMS.Core.Notification = Y.Base.create('notification', Y.View, [Y.LIMS.Core.CoreExtension], {

    // Templates
    audioSoundTemplate: Y.one('#limsmuc-notification-audio-template').get('innerHTML'),
    embedSoundTemplate: Y.one('#limsmuc-notification-embed-template').get('innerHTML'),

    // Locations of the sound files
    mp3SoundFile: '/audio/notification.mp3',
    wavSoundFile: '/audio/notification.wav',


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
        // Turn off the blinking
        this.set('isBlinking', false);
        // Return to the default page title
        Y.config.doc.title = this.get('unreadMessagesPageTitle');
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
        Y.on('badgeUpdated', this._updatePageTitleUnreadMessages, this);
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
            properties = this.get('properties'),
            instance = this;

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
                    unreadMessagesPageTitle = instance.get('unreadMessagesPageTitle'),
                    notificationTitle = Y.Lang.sub(Y.LIMS.Core.i18n.values.incomingMessageTitleText, {
                        0: lastMessage.get('from').get('fullName')
                    });

                // Set the blinking flag
                instance.set('isBlinking', true);

                // Update the title
                Y.config.doc.title = (title === unreadMessagesPageTitle ? notificationTitle : unreadMessagesPageTitle);

            }, timerInterval));
        }
    },

    /**
     * Adds number of unread messages to the title if there are any
     *
     * @private
     */
    _updatePageTitleUnreadMessages: function () {
        // Vars
        var defaultPageTitle = this.get('defaultPageTitle'),
            unreadMessagesPageTitle,
            portletContainer = this.getPortletContainer(),
            properties = this.get('properties'),
            unreadBadges,
            unreadSum = 0;

        // Chat is not enabled
        if (!properties.isChatEnabled()) {
            Y.config.doc.title = defaultPageTitle;
            // End here
            return;
        }

        // Find all the unread badges
        unreadBadges = portletContainer.all('.conversation .unread');

        // Count the sum
        unreadBadges.each(function (badge) {
            // Vars
            var unreadCount = badge.get('innerHTML');

            if (Y.LIMS.Core.Util.isHidden(badge)) {
                unreadCount = 0;
            }

            if (!isNaN(unreadCount)) {
                // Add to sum
                unreadSum += parseInt(unreadCount, 10);
            }
        });

        // There are some unread messages
        if (unreadSum > 0) {
            // Add the number of unread messages to the title
            unreadMessagesPageTitle = '(' + unreadSum + ') ' + defaultPageTitle;
        }
        // No unread messages
        else {
            // Set the title to default
            unreadMessagesPageTitle = defaultPageTitle;
        }

        // Save the new title
        this.set('unreadMessagesPageTitle', unreadMessagesPageTitle);

        // Update the title only if it's not currently blinking
        if (!this.get('isBlinking')) {
            Y.config.doc.title = unreadMessagesPageTitle;
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
                // Vars
                var properties = this.get('properties'),
                    path = properties.getContextPath(),
                    mp3 = path + this.mp3SoundFile,
                    wav = path + this.wavSoundFile;

                return Y.Node.create(Y.Lang.sub(this.audioSoundTemplate, {
                    wav: wav,
                    mp3: mp3
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
                // Vars
                var properties = this.get('properties'),
                    path = properties.getContextPath(),
                    wav = path + this.wavSoundFile;

                return Y.Node.create(Y.Lang.sub(this.embedSoundTemplate, {
                    wav: wav
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
         * Default page title plus unread messages counter
         *
         * {string}
         */
        unreadMessagesPageTitle: {
            valueFn: function () {
                return Y.config.doc.title;
            }
        },

        /**
         * Set to true if the blinking title notification is on
         *
         * {boolean}
         */
        isBlinking: {
            value: false // default value
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
