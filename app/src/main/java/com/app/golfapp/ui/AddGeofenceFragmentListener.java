package com.app.golfapp.ui;

import android.support.v4.app.Fragment;

import com.app.golfapp.ui.model.NamedGeofence;

/**
 * Created by nnarayan on 7/7/2018.**/
 public interface AddGeofenceFragmentListener {
 void onDialogPositiveClick(Fragment dialog, NamedGeofence geofence);
 void onDialogNegativeClick(Fragment dialog);
 }