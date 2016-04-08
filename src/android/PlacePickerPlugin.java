package org.apache.cordova.placepicker;


import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

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
                LatLng latlng = place.getLatLng();
                String placeId = place.getId();
                JSONObject json = new JSONObject();
                try {
                    json.put("lat", String.valueOf(latlng.latitude));
                    json.put("lng", String.valueOf(latlng.longitude));
                    json.put("placeId", String.valueOf(placeId));
                    mCallBackContext.success(json);
                } catch (JSONException e) {
                    e.printStackTrace();
                    mCallBackContext.error("JSON parse failed");
                }
            }
        }
    }
    
}
