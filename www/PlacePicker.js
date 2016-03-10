function PlacePicker() {}

PlacePicker.prototype.open = function (successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "PlacePicker", "", []);
};


PlacePicker.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

  window.plugins.placepicker = new PlacePicker();
  return window.plugins.placepicker;
};

cordova.addConstructor(PlacePicker.install);