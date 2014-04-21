/**
 * Group Model Item
 *
 * The class extends Y.Model and customizes it to use a localStorage
 * sync provider or load data via ajax. It represent a single group item.
 */
Y.namespace('LIMS.Model');

Y.LIMS.Model.ConversationItemModel = Y.Base.create('conversationItemModel', Y.Model, [], {

    // Custom sync layer.
    sync: function (action, options, callback) {
        var data;

        switch (action) {
            case 'create':
                Y.log("create");
                data = this.toJSON();
                Y.log(data);
                return;
//                // Use the current timestamp as an id just to simplify the example. In a
//                // real sync layer, you'd want to generate an id that's more likely to
//                // be globally unique.
//                data.id = Y.Lang.now();
//
//                // Store the new record in localStorage, then call the callback.
//                localStorage.setItem(data.id, Y.JSON.stringify(data));
//                callback(null, data);
//                return;

            case 'read':
                Y.log("read");
                return;

            // Look for an item in localStorage with this model's id.
//                data = localStorage.getItem(this.get('id'));
//
//                if (data) {
//                    callback(null, data);
//                } else {
//                    callback('Model not found.');
//                }
//
//                return;

            case 'update':
                Y.log("update");
                return;
//
//                data = this.toJSON();
//
//                // Update the record in localStorage, then call the callback.
//                localStorage.setItem(this.get('id'), Y.JSON.stringify(data));
//                callback(null, data);
//                return;

            case 'delete':
                Y.log("delete");
                return;
//                localStorage.removeItem(this.get('id'));
//                callback();
//                return;

            default:
                callback('Invalid action');
        }
    }

}, {
    ATTRS: {
        // Add custom model attributes here. These attributes will contain your
        // model's data. See the docs for Y.Attribute to learn more about defining
        // attributes.

        buddy: {
            value: "" // default value
        },

        message: {
            value: "" // default value
        },

        timestamp: {
            value: "" // default value
        }
    }
});
