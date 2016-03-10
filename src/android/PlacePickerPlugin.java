package org.apache.cordova.placepicker;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

public class PlacePickerPlugin extends CordovaPlugin {
    
    int PLACE_PICKER_REQUEST = 1;
    
    private CallbackContext mCallBackContext = null;
    
    @Override
    public boolean execute(final String action, final JSONArray data, final CallbackContext callbackContext) {
        mCallBackContext = callbackContext;
        Log.d("PlacePicker", "PlacePicker called with options: " + data);
        
        cordova.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    cordova.setActivityResultCallback(PlacePickerPlugin.this);
                    PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                    cordova.getActivity().startActivityForResult(builder.build(cordova.getActivity()), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                    callbackContext.error("Repairable");
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                    callbackContext.error("Not available");
                }
            }
        });
        return true;
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == -1) {
                Place place = PlacePicker.getPlace(data, cordova.getActivity());
                JSONArray jsonArray = new JSONArray();
                jsonArray.put(place.getLatLng());
                mCallBackContext.success(jsonArray);
            }
        }
    }
    
}
