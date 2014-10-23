AUI().use('lims-core', 'lims-model', 'lims-view', 'lims-controller', 'lims-plugin', function (A) {

    // If there is no chat bar do nothing
    if (!A.one('#lims-container .lims-bar')) {
        return;
    }

    // Dom ready startup
    A.on('domready', function () {

        // Vars
        var mainController;

        // Set global settings
        A.LIMS.Core.Properties.pathImage = Liferay.ThemeDisplay.getPathImage();
        A.LIMS.Core.Properties.isIE = Liferay.Browser.isIe();

        // Parse localization values from template
        A.LIMS.Core.i18n.values = A.JSON.parse(A.one('#lims-i18n').get('innerHTML'));
        A.LIMS.Core.Properties.values = A.JSON.parse(A.one('#lims-properties').get('innerHTML'));

        // Start the app!
        mainController = new A.LIMS.Controller.MainController({
            userId: Liferay.ThemeDisplay.getUserId(),
            companyId: Liferay.ThemeDisplay.getCompanyId(),
            pathImage: Liferay.ThemeDisplay.getPathImage()
        });

        // Notify main controller if the user session expires
        Liferay.bind('sessionExpired', function () {
            mainController.sessionExpired();
        });

    });
});