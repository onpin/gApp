package com.app.golfapp.app;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.app.golfapp.ui.Services.GeofenceTransitionsIntentService;
import com.app.golfapp.ui.model.GeoModel;
import com.app.golfapp.ui.model.NamedGeofence;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GeoFenceController {

    // region Properties

    private final String TAG = GeoFenceController.class.getName();

    private Context context;
    private GoogleApiClient googleApiClient;
    private Gson gson;
    private SharedPreferences prefs;
    private GeofenceControllerListener listener;
    private List<NamedGeofence> namedGeofences;
    public List<NamedGeofence> getNamedGeofences() {
        return namedGeofences;
    }
    private List<NamedGeofence> namedGeofencesToRemove;
    private Geofence geofenceToAdd;
    private NamedGeofence namedGeofenceToAdd;
    // endregion
    // region Shared Instance
    private static GeoFenceController INSTANCE;

    public static GeoFenceController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GeoFenceController();
        }
        return INSTANCE;
    }

    // endregion

    // region Public

    public void init(Context context) {
        this.context = context.getApplicationContext();

        // gson = new Gson();
        namedGeofences = new ArrayList<>();
        //   namedGeofencesToRemove = new ArrayList<>();
        //       prefs = this.context.getSharedPreferences("SHARED_PREFS_GEOFENCES", Context.MODE_PRIVATE);

        //      loadGeofences();
    }

    public void addGeofence(NamedGeofence namedGeofence) {
        this.namedGeofenceToAdd = namedGeofence;
        this.geofenceToAdd = namedGeofence.geofence();
        //this.listener = listener;

        connectWithCallbacks(connectionAddListener);
    }

    public void removeGeofences(NamedGeofence namedGeofencesToRemove) {
        //this.namedGeofencesToRemove = namedGeofencesToRemove;
        //this.listener = listener;

        connectWithCallbacks(connectionRemoveListener);
    }

    public void removeAllGeofences(GeofenceControllerListener listener) {
        namedGeofencesToRemove = new ArrayList<>();
        for (NamedGeofence namedGeofence : namedGeofences) {
            namedGeofencesToRemove.add(namedGeofence);
        }
        this.listener = listener;

        connectWithCallbacks(connectionRemoveListener);
    }

    // endregion

    // region Private

    private void loadGeofences() {
        // Loop over all geofence keys in prefs and add to namedGeofences
        Map<String, ?> keys = prefs.getAll();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            String jsonString = prefs.getString(entry.getKey(), null);
            NamedGeofence namedGeofence = gson.fromJson(jsonString, NamedGeofence.class);
            namedGeofences.add(namedGeofence);
        }

        // Sort namedGeofences by name
        Collections.sort(namedGeofences);

    }


    private void connectWithCallbacks(GoogleApiClient.ConnectionCallbacks callbacks) {
        googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(callbacks)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();
        googleApiClient.connect();
    }

    public void removeClient(){
        googleApiClient.disconnect();
    }

    private GeofencingRequest getAddGeofencingRequest() {
        List<Geofence> geofencesToAdd = new ArrayList<>();
        geofencesToAdd.add(geofenceToAdd);
        GeofencingRequest.Builder builder = new GeofencingRequest.Builder();
        builder.setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER);
        builder.addGeofences(geofencesToAdd);
        return builder.build();
    }

    private void saveGeofence() {
        namedGeofences.add(namedGeofenceToAdd);
        if (listener != null) {
            listener.onGeofencesUpdated();
        }

        String json = gson.toJson(namedGeofenceToAdd);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(namedGeofenceToAdd.id, json);
        editor.apply();
    }

    private void removeSavedGeofences() {
        SharedPreferences.Editor editor = prefs.edit();

        for (NamedGeofence namedGeofence : namedGeofencesToRemove) {
            int index = namedGeofences.indexOf(namedGeofence);
            editor.remove(namedGeofence.id);
            namedGeofences.remove(index);
            editor.apply();
        }

        if (listener != null) {
            listener.onGeofencesUpdated();
        }
    }

    private void sendError() {

      //  Toast.makeText(context, "Error in networking", Toast.LENGTH_LONG).show();
    }

    // endregion

    // region ConnectionCallbacks

    private GoogleApiClient.ConnectionCallbacks connectionAddListener = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            try{
            Intent intent = new Intent(context, GeofenceTransitionsIntentService.class);
            PendingIntent pendingIntent = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling-
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            PendingResult<Status> result = LocationServices.GeofencingApi.addGeofences(googleApiClient, getAddGeofencingRequest(), pendingIntent);
            result.setResultCallback(new ResultCallback<Status>() {
                @Override
                public void onResult(Status status) {
                    if (status.isSuccess()) {
                        namedGeofences.add(namedGeofenceToAdd);
                        Log.e(TAG, "Registering geofence Success:" + status.getStatusMessage() + " : " + status.getStatusCode());
                        //sendError();

                        //                saveGeofence();
                    } else {
                        Log.e(TAG, "Registering geofence failed: " + status.getStatusMessage() + " : " + status.getStatusCode());
                        sendError();
                    }
                }
            });
        }catch (Exception e){
                e.printStackTrace();
            }}

        @Override
        public void onConnectionSuspended(int i) {
            Log.e(TAG, "Connecting to GoogleApiClient suspended.");
            sendError();
        }
    };

    private GoogleApiClient.ConnectionCallbacks connectionRemoveListener = new GoogleApiClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {

            Log.e(TAG, "Removing geofence failed: ");

            sendError();


        }

        @Override
        public void onConnectionSuspended(int i) {

        }
    };


    // endregion

    // region OnConnectionFailedListener

    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener = new GoogleApiClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            Log.e(TAG, "Connecting to GoogleApiClient failed.");
            sendError();

        }
    };

    // endregion

    // region Interfaces

    public interface GeofenceControllerListener {
        void onGeofencesUpdated();

        void onError();
    }

    // end region

}