/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */

/**
 * Confirm Element View
 */
Y.namespace('LIMS.View');

Y.LIMS.View.ConfirmElementView = Y.Base.create('confirmElementView', Y.View, [Y.LIMS.View.ViewExtension], {

    /**
     * Called on initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
        // Close confirm
        this.close();
        // Hide preloader
        this.hideActivityIndicator();
    },

    /**
     * Attach events to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var openButton = this.get('openButton'),
            cancelButton = this.get('cancelButton'),
            okButton = this.get('okButton');

        // Local event
        openButton.on('click', this._onOpenButtonClick, this);
        cancelButton.on('click', this._onCancelButtonClick, this);
        okButton.on('click', this._onOkButtonClick, this);
    },

    /**
     * Open confirm
     */
    open: function () {
        // Vars
        var confirmDecision = this.get('confirmDecision'),
            openButton = this.get('openButton');

        Y.LIMS.Core.Util.show(confirmDecision);
        Y.LIMS.Core.Util.hide(openButton);
    },

    /**
     * Close confirm
     */
    close: function () {
        // Vars
        var confirmDecision = this.get('confirmDecision'),
            openButton = this.get('openButton'),
            defaultMessage = this.get('defaultMessage');

        Y.LIMS.Core.Util.hide(confirmDecision);
        Y.LIMS.Core.Util.show(openButton);

        // Set the default message
        this.showDefaultMessage(defaultMessage);
    },

    /**
     * Shows activity indicator
     */
    showActivityIndicator: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator'),
            cancelButton = this.get('cancelButton'),
            okButton = this.get('okButton');

        Y.LIMS.Core.Util.show(activityIndicator);
        Y.LIMS.Core.Util.hide(cancelButton);
        Y.LIMS.Core.Util.hide(okButton);
    },

    /**
     * Hides activity indicator
     * @private
     */
    hideActivityIndicator: function () {
        // Vars
        var activityIndicator = this.get('activityIndicator'),
            cancelButton = this.get('cancelButton'),
            okButton = this.get('okButton');

        Y.LIMS.Core.Util.hide(activityIndicator);
        Y.LIMS.Core.Util.show(cancelButton);
        Y.LIMS.Core.Util.show(okButton);
    },

    /**
     * Shows error message
     *
     * @param message
     */
    showErrorMessage: function (message) {
        // Vars
        var note = this.get('note');

        // Add/remove classes
        note.addClass('error');
        note.removeClass('info');
        note.removeClass('success');

        // Set message
        note.set('innerHTML', message);
    },

    /**
     * Shows error message
     *
     * @param message
     */
    showInfoMessage: function (message) {
        // Vars
        var note = this.get('note');

        // Add/remove classes
        note.addClass('info');
        note.removeClass('error');
        note.removeClass('success');

        // Set message
        note.set('innerHTML', message);
    },

    /**
     * Shows success message
     *
     * @param message
     */
    showSuccessMessage: function (message) {
        // Vars
        var note = this.get('note');

        // Add/remove classes
        note.addClass('success');
        note.removeClass('error');
        note.removeClass('info');

        // Set message
        note.set('innerHTML', message);
    },

    /**
     * Shows the default message
     *
     * @param message
     */
    showDefaultMessage: function (message) {
        // Vars
        var note = this.get('note');

        // Remove classes
        note.removeClass('info');
        note.removeClass('error');
        note.removeClass('success');

        // Set message
        note.set('innerHTML', message);
    },

    /**
     * Called when the user clicks on the open button
     *
     * @private
     */
    _onOpenButtonClick: function () {
        // Open the confirm
        this.open();
        // Fire event
        this.fire('openClick');
    },

    /**
     * Called when the user clicks on the cancel button
     *
     * @private
     */
    _onCancelButtonClick: function () {
        // Close the confirm
        this.close();
        // Fire event
        this.fire('cancelClick');
    },

    /**
     * Called when the user clicks on the ok button
     *
     * @private
     */
    _onOkButtonClick: function () {
        // Fire event
        this.fire('okClick');
    }

}, {

    // Add custom model attributes here. These attributes will contain your
    // model's data. See the docs for Y.Attribute to learn more about defining
    // attributes.

    ATTRS: {

        /**
         * Container attached to the view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Open button node
         *
         * {Node}
         */
        openButton: {
            valueFn: function () {
                return this.get('container').one('.open');
            }
        },

        /**
         * Open button node
         *
         * {Node}
         */
        cancelButton: {
            valueFn: function () {
                return this.get('container').one('.cancel');
            }
        },

        /**
         * Open button node
         *
         * {Node}
         */
        okButton: {
            valueFn: function () {
                return this.get('container').one('.ok');
            }
        },

        /**
         * Note node
         *
         * {Node}
         */
        note: {
            valueFn: function () {
                return this.get('container').one('.note');
            }
        },

        /**
         * Confirm decision container
         *
         * {Node}
         */
        confirmDecision: {
            valueFn: function () {
                return this.get('container').one('.confirm-decision');
            }
        },

        /**
         * Activity indicator node
         *
         * {Node}
         */
        activityIndicator: {
            valueFn: function () {
                return this.get('container').one('.preloader');
            }
        },

        /**
         * Default message
         *
         * {string}
         */
        defaultMessage: {
            valueFn: function () {
                return this.get('note').get('innerHTML');
            }
        }
    }
});