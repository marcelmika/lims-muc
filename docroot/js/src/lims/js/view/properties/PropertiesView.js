/*
 * Copyright (c) 2014 Marcel Mika, marcelmika.com - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Marcel Mika <marcelmika.com>, 2014
 */


/**
 * Admin Properties View
 *
 * Handles properties settings
 */
Y.namespace('LIMS.View');

Y.LIMS.View.PropertiesView = Y.Base.create('propertiesView', Y.View, [], {

    /**
     * Called before initialization
     */
    initializer: function () {
        // Attach events
        this._attachEvents();
    },

    /**
     * Opens or closes properties view
     */
    toggleView: function () {
        // Vars
        var openAnimation = this.get('openAnimation');

        // Run the animation
        openAnimation.run();
    },

    /**
     * Returns true if the properties view is opened
     *
     * {boolean}
     */
    isOpened: function () {
        return this.get('settingsContainer').hasClass('opened');
    },

    /**
     * Attach event to elements
     *
     * @private
     */
    _attachEvents: function () {
        // Vars
        var openButton = this.get('openButton'),
            buddyListStrategy = this.get('buddyListStrategy'),
            buddyListSocialRelations = this.get('buddyListSocialRelations'),
            buddyListIgnoreDeactivatedUser = this.get('buddyListIgnoreDeactivatedUser'),
            buddyListGroupSiteEnabled = this.get('buddyListGroupSiteEnabled'),
            buddyListGroupSocialEnabled = this.get('buddyListGroupSocialEnabled'),
            buddyListGroupUserEnabled = this.get('buddyListGroupUserEnabled'),
            excludedSites = this.get('excludedSites'),
            buddyListSiteExcludes = this.get('buddyListSiteExcludes'),
            buddyListGroupExcludes = this.get('buddyListGroupExcludes'),
            mobileUserScalableDisabled = this.get('mobileUserScalableDisabled'),
            jabberEnabled = this.get('jabberEnabled'),
            jabberSecurityEnabled = this.get('jabberSecurityEnabled'),
            jabberImportUserEnabled = this.get('jabberImportUserEnabled'),
            jabberHost = this.get('jabberHost'),
            jabberPort = this.get('jabberPort'),
            jabberServiceName = this.get('jabberServiceName'),
            jabberResource = this.get('jabberResource'),
            jabberTestConnectionButton = this.get('jabberTestConnectionButton'),
            jabberSharedSecretEnabled = this.get('jabberSharedSecretEnabled'),
            jabberSharedSecret = this.get('jabberSharedSecret'),
            ipcEnabled = this.get('ipcEnabled'),
            synchronizationSUC = this.get('synchronizationSUC'),
            synchronizationChatPortlet = this.get('synchronizationChatPortlet');

        // Local events
        openButton.on('click', this._onOpenButtonClick, this);
        buddyListStrategy.on('choiceClick', this._onBuddyListStrategySelected, this);
        buddyListSocialRelations.on('choiceClick', this._onBuddyListSocialRelationsSelected, this);
        buddyListIgnoreDeactivatedUser.on('switchClick', this._onBuddyListIgnoreDeactivatedUserClick, this);
        buddyListGroupSiteEnabled.on('switchClick', this._onBuddyListGroupSiteEnabledClick, this);
        buddyListGroupSocialEnabled.on('switchClick', this._onBuddyListGroupSocialEnabledClick, this);
        buddyListGroupUserEnabled.on('switchClick', this._onBuddyListGroupUserEnabledClick, this);
        excludedSites.on('inputUpdate', this._onExcludedSitesUpdate, this);
        buddyListSiteExcludes.on('inputUpdate', this._onBuddyListSiteExcludesUpdate, this);
        buddyListGroupExcludes.on('inputUpdate', this._onBuddyListGroupExcludesUpdate, this);
        mobileUserScalableDisabled.on('switchClick', this._onMobileUserScalableDisabledClick, this);
        jabberEnabled.on('switchClick', this._onJabberEnabledClick, this);
        jabberSecurityEnabled.on('switchClick', this._onJabberSecurityEnabledClick, this);
        jabberImportUserEnabled.on('switchClick', this._onJabberImportUserEnabledClick, this);
        jabberHost.on('inputUpdate', this._onJabberHostUpdate, this);
        jabberPort.on('inputUpdate', this._onJabberPortUpdate, this);
        jabberServiceName.on('inputUpdate', this._onJabberServiceNameUpdate, this);
        jabberResource.on('inputUpdate', this._onJabberResourceUpdate, this);
        jabberTestConnectionButton.on('click', this._onJabberTestConnectionButtonClick, this);
        jabberSharedSecretEnabled.on('switchClick', this._onJabberSharedSecretEnabledClick, this);
        jabberSharedSecret.on('inputUpdate', this._onJabberSharedSecretUpdate, this);
        ipcEnabled.on('switchClick', this._onIpcEnabledClick, this);
        synchronizationSUC.on('okClick', this._onSynchronizationSucOkClick, this);
        synchronizationChatPortlet.on('okClick', this._onSynchronizationChatPortletOkClick, this);
    },

    /**
     * Called when the open button is clicked
     *
     * @private
     */
    _onOpenButtonClick: function () {
        // Open properties view
        this.toggleView();
    },

    /**
     * Called when user selects one of the buddy list strategies
     *
     * @param event
     * @private
     */
    _onBuddyListStrategySelected: function (event) {
        // Vars
        var choice = event.choice,                                           // Choice id is passed in event
            buddyListStrategy = this.get('buddyListStrategy'),               // Get the buddy list strategy view
            preSelectedChoices = event.preSelectedChoices,                   // Choices before selection
            model;                                                           // Properties model

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListStrategy: choice
        });

        // Disable view
        buddyListStrategy.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListStrategy.selectChoices(preSelectedChoices);
            }

            // Re-enable the view so the user can interact with it again
            buddyListStrategy.enable();
        });
    },

    /**
     * Called when user selects one of the buddy list social relations
     *
     * @param event
     * @private
     */
    _onBuddyListSocialRelationsSelected: function (event) {
        // Vars
        var buddyListSocialRelations = this.get('buddyListSocialRelations'),
            preSelectedChoices = event.preSelectedChoices,
            postSelectedChoices = event.postSelectedChoices,
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListSocialRelations: postSelectedChoices
        });

        // Disable view
        buddyListSocialRelations.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListSocialRelations.reset();
                buddyListSocialRelations.selectChoices(preSelectedChoices);
            }
            // Re-enable the view so the user can interact with it again
            buddyListSocialRelations.enable();
        });
    },

    /**
     * Called when the user click on the buddy list ignore deactivated user switch
     *
     * @private
     */
    _onBuddyListIgnoreDeactivatedUserClick: function () {
        // Vars
        var switchView = this.get('buddyListIgnoreDeactivatedUser'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListIgnoreDeactivatedUser: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user clicks on the group site enabled switch
     * @private
     */
    _onBuddyListGroupSiteEnabledClick: function () {
        // Vars
        var switchView = this.get('buddyListGroupSiteEnabled'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListGroupSiteEnabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user clicks on the group social enabled switch
     * @private
     */
    _onBuddyListGroupSocialEnabledClick: function () {
        // Vars
        var switchView = this.get('buddyListGroupSocialEnabled'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListGroupSocialEnabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user clicks on the group user enabled switch
     * @private
     */
    _onBuddyListGroupUserEnabledClick: function () {
        // Vars
        var switchView = this.get('buddyListGroupUserEnabled'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListGroupUserEnabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user updates excluded sites property
     *
     * @param event
     * @private
     */
    _onExcludedSitesUpdate: function (event) {
        // Vars
        var excludedSites = this.get('excludedSites'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            excludedSites: postValue
        });

        // Disable view
        excludedSites.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                excludedSites.setValues(preValue);
            }
            // Re-enable the view so the user can interact with it again
            excludedSites.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            excludedSites.focus();
        });
    },


    /**
     * Called when the user updates buddy list sites excludes property
     *
     * @param event
     * @private
     */
    _onBuddyListSiteExcludesUpdate: function (event) {
        // Vars
        var buddyListSiteExcludes = this.get('buddyListSiteExcludes'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListSiteExcludes: postValue
        });

        // Disable view
        buddyListSiteExcludes.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListSiteExcludes.setValues(preValue);
            }
            // Re-enable the view so the user can interact with it again
            buddyListSiteExcludes.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            buddyListSiteExcludes.focus();
        });
    },

    /**
     * Called when the user updates buddy list group excludes property
     *
     * @param event
     * @private
     */
    _onBuddyListGroupExcludesUpdate: function (event) {
        // Vars
        var buddyListGroupExcludes = this.get('buddyListGroupExcludes'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            buddyListGroupExcludes: postValue
        });

        // Disable view
        buddyListGroupExcludes.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                buddyListGroupExcludes.setValues(preValue);
            }
            // Re-enable the view so the user can interact with it again
            buddyListGroupExcludes.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            buddyListGroupExcludes.focus();
        });
    },


    /**
     * Called when the user clicks on the ipc enabled switch
     *
     * @private
     */
    _onMobileUserScalableDisabledClick: function () {
        // Vars
        var switchView = this.get('mobileUserScalableDisabled'),
            mobilePatch = new Y.LIMS.Core.MobilePatch(),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            mobileUserScalableDisabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();

            // Switch was turned on
            if (switchView.isOn()) {
                // Disable user scalability
                mobilePatch.disableZoom();
            }
            // Switch was turned off
            else {
                // Enable user scalability
                mobilePatch.enableZoom();
            }
        });
    },

    /**
     * Called when the user clicks on the jabber enabled switch
     *
     * @private
     */
    _onJabberEnabledClick: function () {
        // Vars
        var switchView = this.get('jabberEnabled'),
            buddyListStrategy = this.get('buddyListStrategy'),
            selectedChoices = buddyListStrategy.get('selectedChoices'),
            model;

        // Disable view
        switchView.disable();

        // Enabling jabber
        if (switchView.isOn()) {
            buddyListStrategy.enableChoice('JABBER');

            // Prepare the model
            model = new Y.LIMS.Model.PropertiesModel({
                jabberEnabled: switchView.isOn()
            });
        }
        // Disabling jabber
        else {
            // Don't let the user to select jabber buddy list strategy
            if (selectedChoices.length > 0 && selectedChoices[0] === 'JABBER') {
                // Select ALL as default
                buddyListStrategy.selectChoice('ALL');
            }
            // Disable the jabber choice
            buddyListStrategy.disableChoice('JABBER');

            // Prepare the model
            model = new Y.LIMS.Model.PropertiesModel({
                jabberEnabled: switchView.isOn(),
                buddyListStrategy: "ALL"
            });
        }


        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user clicks on the jabber security enabled switch
     *
     * @private
     */
    _onJabberSecurityEnabledClick: function () {
        // Vars
        var switchView = this.get('jabberSecurityEnabled'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberSecurityEnabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the user clicks on the jabber import user enabled switch
     *
     * @private
     */
    _onJabberImportUserEnabledClick: function () {
        // Vars
        var switchView = this.get('jabberImportUserEnabled'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberImportUserEnabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the jabber host input is updated
     *
     * @private
     */
    _onJabberHostUpdate: function (event) {
        // Vars
        var jabberHost = this.get('jabberHost'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberHost: postValue
        });

        // Disable view
        jabberHost.disable();
        // Show activity
        jabberHost.showActivityIndicator();

        // Save the model
        model.save(function (err) {
            // Hide activity
            jabberHost.hideActivityIndicator();

            if (err) {
                // Return everything to the previous state
                jabberHost.setValue(preValue);
                // Show error
                jabberHost.showError(Y.LIMS.Core.i18n.values.valueUpdateError);
            }
            // Re-enable the view so the user can interact with it again
            jabberHost.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            jabberHost.focus();
        });
    },

    /**
     * Called when the jabber port input is updated
     *
     * @private
     */
    _onJabberPortUpdate: function (event) {
        // Vars
        var jabberPort = this.get('jabberPort'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberPort: postValue
        });

        // Disable view
        jabberPort.disable();
        // Show activity
        jabberPort.showActivityIndicator();

        // Save the model
        model.save(function (err) {
            // Hide activity
            jabberPort.hideActivityIndicator();

            if (err) {
                // Return everything to the previous state
                jabberPort.setValue(preValue);
                // Show error
                jabberPort.showError(Y.LIMS.Core.i18n.values.valueUpdateError);
            }
            // Re-enable the view so the user can interact with it again
            jabberPort.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            jabberPort.focus();
        });
    },

    /**
     * Called when the jabber service name input is updated
     *
     * @private
     */
    _onJabberServiceNameUpdate: function (event) {
        // Vars
        var jabberServiceName = this.get('jabberServiceName'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberServiceName: postValue
        });

        // Disable view
        jabberServiceName.disable();
        // Show activity
        jabberServiceName.showActivityIndicator();

        // Save the model
        model.save(function (err) {
            // Hide activity
            jabberServiceName.hideActivityIndicator();

            if (err) {
                // Return everything to the previous state
                jabberServiceName.setValue(preValue);
                // Show error
                jabberServiceName.showError(Y.LIMS.Core.i18n.values.valueUpdateError);
            }
            // Re-enable the view so the user can interact with it again
            jabberServiceName.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            jabberServiceName.focus();
        });
    },

    /**
     * Called when the jabber resource input is updated
     *
     * @private
     */
    _onJabberResourceUpdate: function (event) {
        // Vars
        var jabberResource = this.get('jabberResource'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberResource: postValue
        });

        // Disable view
        jabberResource.disable();
        // Show activity
        jabberResource.showActivityIndicator();

        // Save the model
        model.save(function (err) {
            // Hide activity
            jabberResource.hideActivityIndicator();

            if (err) {
                // Return everything to the previous state
                jabberResource.setValue(preValue);
                // Show error
                jabberResource.showError(Y.LIMS.Core.i18n.values.valueUpdateError);
            }
            // Re-enable the view so the user can interact with it again
            jabberResource.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            jabberResource.focus();
        });
    },

    /**
     * Called when the user clicks on the jabber test connection button
     * @private
     */
    _onJabberTestConnectionButtonClick: function () {
        // Vars
        var button = this.get('jabberTestConnectionButton'),
            preloader = this.get('jabberTestConnectionPreloader'),
            message = this.get('jabberTestConnectionMessage'),
            jabberHost = this.get('jabberHost'),
            jabberPort = this.get('jabberPort'),
            jabberServiceName = this.get('jabberServiceName'),
            jabberResource = this.get('jabberResource'),
            isValid,
            model;


        // Validate all mandatory fields
        isValid =
            jabberHost.validate() &&
            jabberPort.validate() &&
            jabberServiceName.validate() &&
            jabberResource.validate();

        if (!isValid) {
            // Set the error text
            message.set('innerHTML', Y.LIMS.Core.i18n.values.testConnectionValidationError);
            // Set the failure class
            message.addClass('failure');
            // Show the message
            Y.LIMS.Core.Util.show(message);
            // End here
            return;
        }

        // Create model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberHost: jabberHost.getValue(),
            jabberPort: jabberPort.getValue(),
            jabberServiceName: jabberServiceName.getValue(),
            jabberResource: jabberResource.getValue()
        });

        // Disable the button
        button.set('disabled', true);
        // Show the preloader
        Y.LIMS.Core.Util.show(preloader);
        // Hide the message
        Y.LIMS.Core.Util.hide(message);
        // Remove extra classes
        message.removeClass('success');
        message.removeClass('failure');

        // Test the connection
        model.testConnection(function (err) {
            // Re-enable the button
            button.set('disabled', null);
            // Hide the preloader
            Y.LIMS.Core.Util.hide(preloader);

            if (err) {
                // Set the error text
                message.set('innerHTML', err.get('message'));
                // Set the failure class
                message.addClass('failure');
                // Show the message
                Y.LIMS.Core.Util.show(message);
                // End here
                return;
            }

            // Set the success text
            message.set('innerHTML', Y.LIMS.Core.i18n.values.testConnectionSuccess);
            // Set the success class
            message.addClass('success');
            // Show the message
            Y.LIMS.Core.Util.show(message);
        });
    },

    /**
     * Called when the user clicks on the jabber shared secret enabled switch
     *
     * @private
     */
    _onJabberSharedSecretEnabledClick: function () {
        // Vars
        var switchView = this.get('jabberSharedSecretEnabled'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberSharedSecretEnabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }
            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },

    /**
     * Called when the jabber shared secret input is updated
     *
     * @private
     */
    _onJabberSharedSecretUpdate: function (event) {
        // Vars
        var jabberSharedSecret = this.get('jabberSharedSecret'),
            preValue = event.preValue,      // Previously selected value
            postValue = event.postValue,    // Currently selected value
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            jabberSharedSecret: postValue
        });

        // Disable view
        jabberSharedSecret.disable();
        // Show activity
        jabberSharedSecret.showActivityIndicator();

        // Save the model
        model.save(function (err) {
            // Hide activity
            jabberSharedSecret.hideActivityIndicator();

            if (err) {
                // Return everything to the previous state
                jabberSharedSecret.setValue(preValue);
                // Show error
                jabberSharedSecret.showError(Y.LIMS.Core.i18n.values.valueUpdateError);
            }
            // Re-enable the view so the user can interact with it again
            jabberSharedSecret.enable();
            // Since if we called the disable()
            // function token input looses it's focus. So if we enable it
            // we need to re-enable the focus again.
            jabberSharedSecret.focus();
        });
    },

    /**
     * Called when the user clicks on the ipc enabled switch
     *
     * @private
     */
    _onIpcEnabledClick: function () {
        // Vars
        var switchView = this.get('ipcEnabled'),
            properties = this.get('properties'),
            model;

        // Prepare the model
        model = new Y.LIMS.Model.PropertiesModel({
            ipcEnabled: switchView.isOn()
        });

        // Disable view
        switchView.disable();

        // Save the model
        model.save(function (err) {
            if (err) {
                // Return everything to the previous state
                switchView.toggle();
            }

            // Update in properties
            properties.set('isIPCEnabled', switchView.isOn());

            // Re-enable the view so the user can interact with it again
            switchView.enable();
        });
    },


    /**
     * Called when user confirms synchronization with SUC
     *
     * @private
     */
    _onSynchronizationSucOkClick: function () {
        // Vars
        var confirmView = this.get('synchronizationSUC'),
            model = this.get('synchronizationModel');

        // Show the activity
        confirmView.showActivityIndicator();

        // Start synchronization
        model.synchronizeSUC(function (err, inProgress) {
            // Hide the preloader
            confirmView.hideActivityIndicator();

            // Error
            if (err) {
                confirmView.showErrorMessage(Y.LIMS.Core.i18n.values.sucSynchronizationError);
            }
            // In progress
            else if (inProgress) {
                confirmView.showInfoMessage(Y.LIMS.Core.i18n.values.sucSynchronizationInProgress);
            }
            // Success
            else {
                confirmView.showSuccessMessage(Y.LIMS.Core.i18n.values.sucSynchronizationSuccess);
            }
        });
    },

    /**
     * Called when user confirms synchronization with Chat Portlet
     * @private
     */
    _onSynchronizationChatPortletOkClick: function () {
        // Vars
        var confirmView = this.get('synchronizationChatPortlet'),
            model = this.get('synchronizationModel');

        // Show the activity
        confirmView.showActivityIndicator();

        // Start synchronization
        model.synchronizeChatPortlet(function (err, inProgress) {
            // Hide the preloader
            confirmView.hideActivityIndicator();

            // Error
            if (err) {
                confirmView.showErrorMessage(Y.LIMS.Core.i18n.values.chatPortletSynchronizationError);
            }
            // In progress
            else if (inProgress) {
                confirmView.showInfoMessage(Y.LIMS.Core.i18n.values.chatPortletSynchronizationInProgress);
            }
            // Success
            else {
                confirmView.showSuccessMessage(Y.LIMS.Core.i18n.values.chatPortletSynchronizationSuccess);
            }
        });
    }


}, {

    ATTRS: {

        /**
         * Container node attached to the view
         *
         * {Node}
         */
        container: {
            value: null // to be set
        },

        /**
         * Synchronization model
         *
         * {Y.LIMS.Model.SynchronizationModel}
         */
        synchronizationModel: {
            valueFn: function () {
                return new Y.LIMS.Model.SynchronizationModel();
            }
        },

        /**
         * Settings container
         *
         * {Node}
         */
        settingsContainer: {
            valueFn: function () {
                return this.get('container').one('.settings');
            }
        },

        /**
         * Container node with the open/close button
         *
         * {Node}
         */
        openButton: {
            valueFn: function () {
                return this.get('container').one('.open-button');
            }
        },

        /**
         * Help button node
         *
         * {Node}
         */
        helpButton: {
            valueFn: function () {
                return this.get('container').one('.help');
            }
        },

        /**
         * Returns animation that will open or close the whole view
         *
         * {Y.Anim}
         */
        openAnimation: {
            valueFn: function () {
                // Vars
                var openButtonAnimation = this.get('openButtonAnimation'),
                    settingsContainer = this.get('settingsContainer'),
                    openButton = this.get('openButton'),
                    helpButton = this.get('helpButton'),
                    animation;

                // Create the opening animation
                animation = new Y.Anim({
                    node: settingsContainer,
                    duration: 0.8,
                    from: {
                        width: 250,
                        height: 0,
                        opacity: 0
                    },
                    to: {
                        width: 440,
                        height: 300,
                        opacity: 1
                    },
                    easing: 'backOut'
                });

                // Check if the animation should be reversed
                if (settingsContainer.hasClass('opened')) {
                    // Reverse the animation
                    animation.set('reverse', true);
                }

                // On animation start
                animation.before('start', function () {
                    // Hide the open button, we don't want the user to interact now
                    openButton.setStyle('opacity', 0);
                    // Hide buttons
                    Y.LIMS.Core.Util.hide(openButton);
                    Y.LIMS.Core.Util.hide(helpButton);

                }, this);

                // On animation end
                animation.after('end', function () {

                    // Closing
                    if (animation.get('reverse')) {
                        // Update open button text
                        openButton.set('innerHTML', Y.LIMS.Core.i18n.values.adminAreaOpen);
                        // Settings container doesn't need the closed class anymore
                        settingsContainer.removeClass('opened');
                        settingsContainer.addClass('closed');
                        settingsContainer.setStyle('opacity', 0);
                        // Fire the event
                        this.fire('propertiesClosed');
                    }
                    // Opening
                    else {
                        // Update open button text
                        openButton.set('innerHTML', Y.LIMS.Core.i18n.values.adminAreaClose);
                        // Settings container doesn't need the closed class anymore
                        settingsContainer.removeClass('closed');
                        settingsContainer.addClass('opened');
                        // Hide help button
                        Y.LIMS.Core.Util.show(helpButton);
                        // Fire the event
                        this.fire('propertiesOpened');
                    }

                    // Show the button
                    Y.LIMS.Core.Util.show(openButton);
                    // And run the animation
                    openButtonAnimation.run();

                    // Toggle the animation direction
                    animation.set('reverse', !animation.get('reverse'));

                }, this);

                return animation;
            }
        },

        /**
         * Return open button animation
         *
         * {Y.Anim}
         */
        openButtonAnimation: {
            valueFn: function () {
                // Vars
                var openButton = this.get('openButton');

                // Create button animation
                return new Y.Anim({
                    node: openButton,
                    duration: 0.5,
                    from: {opacity: 0},
                    to: {opacity: 1}
                });
            }
        },

        /**
         * View for buddy list strategy
         *
         * {Y.LIMS.View.ChoiceElementView}
         */
        buddyListStrategy: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-strategy');

                return new Y.LIMS.View.ChoiceElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list social relations
         *
         * {Y.LIMS.View.ChoiceElementView}
         */
        buddyListSocialRelations: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-social-relations');

                return new Y.LIMS.View.ChoiceElementView({
                    container: container,
                    isExclusive: false
                });
            }
        },

        /**
         * View for buddy list group site enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        buddyListGroupSiteEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-group-site-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list group social enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        buddyListGroupSocialEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-group-social-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list group user enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        buddyListGroupUserEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-group-user-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list ignore deactivated user
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        buddyListIgnoreDeactivatedUser: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-ignore-deactivated-user');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for excluded sites
         *
         * {Y.LIMS.View.TokenInputElementView}
         */
        excludedSites: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.excluded-sites');

                return new Y.LIMS.View.TokenInputElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list excluded sites
         *
         * {Y.LIMS.View.TokenInputElementView}
         */
        buddyListSiteExcludes: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-site-excludes');

                return new Y.LIMS.View.TokenInputElementView({
                    container: container
                });
            }
        },

        /**
         * View for buddy list excluded groups
         *
         * {Y.LIMS.View.TokenInputElementView}
         */
        buddyListGroupExcludes: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.buddy-list-group-excludes');

                return new Y.LIMS.View.TokenInputElementView({
                    container: container
                });
            }
        },

        /**
         * View for mobile user scalable disabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        mobileUserScalableDisabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.mobile-user-scalable-disabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for jabber enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        jabberEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for jabber security enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        jabberSecurityEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-security-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for jabber import user enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        jabberImportUserEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-import-user-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for jabber host
         *
         * {Y.LIMS.View.InputElementView}
         */
        jabberHost: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-host');

                return new Y.LIMS.View.InputElementView({
                    container: container,
                    notEmpty: true  // Host cannot be empty
                });
            }
        },

        /**
         * View for jabber port
         *
         * {Y.LIMS.View.InputElementView}
         */
        jabberPort: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-port');

                return new Y.LIMS.View.InputElementView({
                    container: container,
                    notEmpty: true,  // Port cannot be empty
                    isInteger: true, // Must be an integer
                    minValue: 1,     // Ports start with 1
                    maxValue: 65536  // Ports end with 65536
                });
            }
        },

        /**
         * View for jabber service name
         *
         * {Y.LIMS.View.InputElementView}
         */
        jabberServiceName: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-service-name');

                return new Y.LIMS.View.InputElementView({
                    container: container,
                    notEmpty: true  // Service name cannot be empty
                });
            }
        },

        /**
         * View for jabber resource
         *
         * {Y.LIMS.View.InputElementView}
         */
        jabberResource: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-resource');

                return new Y.LIMS.View.InputElementView({
                    container: container,
                    notEmpty: true  // Resource cannot be empty
                });
            }
        },

        /**
         * Button node for jabber test connection
         *
         * {Node}
         */
        jabberTestConnectionButton: {
            valueFn: function () {
                // Vars
                return this.get('container').one('.jabber-test-connection button');
            }
        },

        /**
         * Jabber test connection preloader node
         *
         * {Node}
         */
        jabberTestConnectionPreloader: {
            valueFn: function () {
                // Vars
                return this.get('container').one('.jabber-test-connection .preloader');
            }
        },

        /**
         * Jabber test connection message
         *
         * {Node}
         */
        jabberTestConnectionMessage: {
            valueFn: function () {
                // Vars
                return this.get('container').one('.jabber-test-connection .message');
            }
        },

        /**
         * View for jabber shared secret enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        jabberSharedSecretEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-shared-secret-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for jabber host
         *
         * {Y.LIMS.View.InputElementView}
         */
        jabberSharedSecret: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.jabber-shared-secret');

                return new Y.LIMS.View.InputElementView({
                    container: container,
                    notEmpty: true  // Shared secrets cannot be empty
                });
            }
        },

        /**
         * View for ipc enabled
         *
         * {Y.LIMS.View.SwitchElementView}
         */
        ipcEnabled: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.ipc-enabled');

                return new Y.LIMS.View.SwitchElementView({
                    container: container
                });
            }
        },

        /**
         * View for synchronization with SUC
         *
         * {Y.LIMS.View.ConfirmElementView}
         */
        synchronizationSUC: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.synchronization-suc .confirm');

                return new Y.LIMS.View.ConfirmElementView({
                    container: container
                });
            }
        },

        /**
         * View for synchronization with Chat Portlet
         *
         * {Y.LIMS.View.ConfirmElementView}
         */
        synchronizationChatPortlet: {
            valueFn: function () {
                // Vars
                var container = this.get('container').one('.synchronization-chat-portlet .confirm');

                return new Y.LIMS.View.ConfirmElementView({
                    container: container
                });
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
