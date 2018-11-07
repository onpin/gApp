package com.app.golfapp.ui.Services;



import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.app.golfapp.R;
import com.app.golfapp.ui.activity.AidImageFragment;
import com.app.golfapp.ui.fragment.XMLParserFragment;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.GeofencingEvent;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GeoFenceServiceGeoConfig extends IntentService {

    private static final String TAG = GeofenceTransitionsIntentService.class.getSimpleName();
    public static final int GEOFENCE_NOTIFICATION_ID = 0;

    public GeoFenceServiceGeoConfig() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // prefs = getApplicationContext().getSharedPreferences(Constants.SharedPrefs.Geofences, Context.MODE_PRIVATE);
        //  gson = new Gson();

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);
        if (event != null) {
            if (event.hasError()) {
                // onError(event.getErrorCode());
            } else {
                int transition = event.getGeofenceTransition();
                if (transition == Geofence.GEOFENCE_TRANSITION_ENTER || transition == Geofence.GEOFENCE_TRANSITION_DWELL || transition == Geofence.GEOFENCE_TRANSITION_EXIT) {
                    List<String> geofenceIds = new ArrayList<>();
                    for (Geofence geofence : event.getTriggeringGeofences()) {
                        geofenceIds.add(geofence.getRequestId());
                    }
                    if (transition == Geofence.GEOFENCE_TRANSITION_ENTER || transition == Geofence.GEOFENCE_TRANSITION_DWELL) {
                        // onEnteredGeofences(geofenceIds);
                        //     Toast.makeText(this,"In",Toast.LENGTH_LONG).show();
                        Log.d("Check","Restricted Area");


                    }
                    else if(transition==Geofence.GEOFENCE_TRANSITION_EXIT){

                        //   Toast.makeText(this,"Out",Toast.LENGTH_LONG).show();

                    }
                }
            }
        }
    }

    // endregion

    // region Private

}
