/**
 * Constructor
 */
function PlacePicker() {}

/**
 * Show the PlacePicker
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
