/**
 * Constructor
 */
function PlacePicker() {
  //this._callback;
}

/**
 * show - true to show the ad, false to hide the ad
 */
PlacePicker.prototype.show = function(cb, errCb) {
    cordova.exec(cb, errCb, "PlacePickerPlugin", '', []);
};

var placePicker = new PlacePicker();
module.exports = placePicker;

// Make plugin work under window.plugins
if (!window.plugins) {
    window.plugins = {};
}
if (!window.plugins.placePicker) {
    window.plugins.placePicker = placePicker;
}

PlacePicker.install = function () {
    if (!window.plugins) {
        window.plugins = {};
    }

    window.plugins.placePicker = new PlacePicker();
    return window.plugins.placePicker;
};

cordova.addConstructor(PlacePicker.install);
