AUI().use('limsmuc-core', 'limsmuc-model', 'limsmuc-view', 'limsmuc-controller', 'limsmuc-plugin', function (A) {

    // Vars
    var conflictMessage;

    // If there is no chat bar do nothing
    if (!A.one('#limsmuc-container .lims-bar')) {
        return; // Stop the app
    }

    // There is an instance of SUC already running
    else if (A.one('#lims-container')) {

        // Vars
        conflictMessage = A.one('.lims-muc .conflict-suc');
        // Show the warning
        if (conflictMessage) {
            A.one('.lims-muc .conflict-suc').removeClass('hide');
        }

        return; // Stop the app

    }

    // There is an instance of Chat Portlet already running
    else if (A.one('#chatBar')) {

        // Vars
        conflictMessage = A.one('.lims-muc .conflict-chat-portlet');
        // Show the warning
        if (conflictMessage) {
            A.one('.lims-muc .conflict-chat-portlet').removeClass('hide');
        }

        return;
    }

    // This needs to be here because main.js is imported twice when the portlet is firstly loaded
    Liferay.publish('limsmucPortletReady', {
        defaultFn: function () {

            // Vars
            var mainController;

            // Set global settings
            A.LIMS.Core.Properties.pathImage = Liferay.ThemeDisplay.getPathImage();
            A.LIMS.Core.Properties.isIE = Liferay.Browser.isIe();

            // Parse localization values from template
            A.LIMS.Core.i18n.values = A.JSON.parse(A.one('#limsmuc-i18n').get('innerHTML'));
            A.LIMS.Core.Properties.values = A.JSON.parse(A.one('#limsmuc-properties').get('innerHTML'));

            // Start the app!
            mainController = new A.LIMS.Controller.MainController({
                userId: Liferay.ThemeDisplay.getUserId(),
                companyId: Liferay.ThemeDisplay.getCompanyId(),
                pathImage: Liferay.ThemeDisplay.getPathImage()
            });

            // Notify main controller when the user session expires
            Liferay.bind('sessionExpired', function () {
                mainController.sessionExpired();
            });
        },
        fireOnce: true
    });

    // Dom ready startup
    A.on('domready', function () {
        Liferay.fire('limsmucPortletReady');
    });
});