package com.app.golfapp.utils;

import android.content.Context;
import android.provider.Settings;

/**
 * Created by hmnsharma on 12/11/2017.
 */

public class ApiConstants {

    public static final String BASE_URL = "http://quakemail.org/ios-webservices/";

    public static final String ACTION = "action";

    public static final String DEVICE_ID = "deviceId";

    public static String TokenID = "tokenid";
    public static final String ACTION_CONNECTION_ERROR =
            "com.example.android.geofence.ACTION_CONNECTION_ERROR";

    public static final String ACTION_CONNECTION_SUCCESS =
            "com.example.android.geofence.ACTION_CONNECTION_SUCCESS";

    public static final String ACTION_GEOFENCES_ADDED =
            "com.example.android.geofence.ACTION_GEOFENCES_ADDED";

    public static final String ACTION_GEOFENCES_REMOVED =
            "com.example.android.geofence.ACTION_GEOFENCES_DELETED";

    public static final String ACTION_GEOFENCE_ERROR =
            "com.example.android.geofence.ACTION_GEOFENCES_ERROR";

    public static final String ACTION_GEOFENCE_TRANSITION =
            "com.example.android.geofence.ACTION_GEOFENCE_TRANSITION";

    public static final String ACTION_GEOFENCE_TRANSITION_ERROR =
            "com.example.android.geofence.ACTION_GEOFENCE_TRANSITION_ERROR";

    // The Intent category used by all Location Services sample apps
    public static final String CATEGORY_LOCATION_SERVICES =
            "com.example.android.geofence.CATEGORY_LOCATION_SERVICES";

    // Keys for extended data in Intents
    public static final String EXTRA_CONNECTION_CODE =
            "com.example.android.EXTRA_CONNECTION_CODE";

    public static final String EXTRA_CONNECTION_ERROR_CODE =
            "com.example.android.geofence.EXTRA_CONNECTION_ERROR_CODE";

    public static final String EXTRA_CONNECTION_ERROR_MESSAGE =
            "com.example.android.geofence.EXTRA_CONNECTION_ERROR_MESSAGE";

    public static final String EXTRA_GEOFENCE_STATUS =
            "com.example.android.geofence.EXTRA_GEOFENCE_STATUS";


    public static String getdeviceID(Context context){

     return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getDeviceToken(Context context){

        return null;
    }

}

