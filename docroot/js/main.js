AUI().use('limsmuc-core', 'limsmuc-model', 'limsmuc-view', 'limsmuc-controller', 'limsmuc-plugin', function (A) {

    // If there is no chat bar do nothing
    if (!A.one('#limsmuc-container .lims-bar')) {
        return;
    }

    // Dom ready startup
    A.on('domready', function () {

        // Vars
        var mainController,
            limsSUCContainer = A.one('#lims-container'),
            conflictView;

        // Set global settings
        A.LIMS.Core.Properties.pathImage = Liferay.ThemeDisplay.getPathImage();
        A.LIMS.Core.Properties.isIE = Liferay.Browser.isIe();

        // Parse localization values from template
        A.LIMS.Core.i18n.values = A.JSON.parse(A.one('#limsmuc-i18n').get('innerHTML'));
        A.LIMS.Core.Properties.values = A.JSON.parse(A.one('#limsmuc-properties').get('innerHTML'));

        // If there is a SUC ran as simultaneously show the warning
        if (limsSUCContainer) {

            // Create conflict view
            conflictView = new A.LIMS.View.ConflictNotificationView();

            // Show the view
            conflictView.showConflictMessage(
                A.LIMS.Core.i18n.values.conflictSUCTitle,
                A.LIMS.Core.i18n.values.conflictSUCMessage
            );

        }
        // No SUC installed -> we can run the app
        else {

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
        }

    });
});