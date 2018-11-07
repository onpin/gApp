package com.app.golfapp.ui.activity;

import android.Manifest;
import android.accessibilityservice.AccessibilityService;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.test.UiThreadTest;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.amirarcane.lockscreen.activity.*;
import com.app.golfapp.R;
import com.app.golfapp.ui.Services.MyService;
import com.app.golfapp.ui.model.GolfHole;
import com.google.gson.Gson;
import com.jaredrummler.android.device.DeviceName;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static junit.framework.Assert.assertTrue;


public class SettingsActivity extends AppCompatPreferenceActivity implements LocationListener {
    public static final String TAG = "SettingsActivity";


    public static String batterystats = "";
    public static String BStatus = "";
    public static String BattPowerSource = "";
    public static String satelite = "";
    public static String currentlats = "";
    public static String currentlongs = "";
    public static String lSatellites = "";
    private static SettingsActivity instance;
    private List<String> pnr = new ArrayList<>();
    static SharedPreferences prefsPrn;
    private Handler mToastHandler;
    private static GolfHole mGolfhole = new GolfHole();
    private static LocationListener locationListener = new DummyLocationListener();
    private static Activity activity;
    static SwitchPreference mSwitchPreference;


    EditTextPreference cartNumber;
    EditTextPreference sim;
    String DeviceId;
    PowerManager pm;
    double longitude, latitude;
    static LocationManager locationManager = null;
    static Context context;
    private ArrayList permissionsToRequest;
    private static ArrayList<String> satPrn = new ArrayList<>();
    private static ArrayList<String> satSNR = new ArrayList<>();
    private GpsListener gpsListener = new GpsListener();
    //private GpsListener gpsListener = new GpsListener();
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();
    private final static int ALL_PERMISSIONS_RESULT = 101;
    static int status = 1;
    int newUiOptions;
    private GpsStatus gpsStatus;

    static boolean uiEnable = true;


    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {

            String stringValue = value.toString();
            String DeviceId = Build.SERIAL;

            Log.v(TAG, "Preferences changed: " + stringValue);

            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("Device")) {
                    preference.setSummary(DeviceId);
                }
            }

            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("Level")) {
                    preference.setSummary(batterystats);
                }
            }

            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("Battery")) {
                    preference.setSummary(BStatus + "(" + BattPowerSource + ")");
                }
            }

            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("Location")) {
                    preference.setSummary(currentlats + " , " + currentlongs);
                }
            }

            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("Stallite")) {
                    SharedPreferences sharedPreferences = getInstance().getSharedPreferences("idData", MODE_PRIVATE);
                    String satelity = sharedPreferences.getString("satellites", "");

                    preference.setSummary(satelity);
                }
            }

            if (preference instanceof EditTextPreference) {
                if (preference.getKey().equals("Cart")) {
                    preference.setSummary(stringValue);
                }
            }

            if (preference instanceof Preference) {
                if (preference.getKey().equals("Change")) {

                    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            changePass();
                            return true;
                        }
                    });
                }

            }

            if (preference instanceof Preference) {
                if (preference.getKey().equals("Refresh")) {

                    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            deleteCache(getInstance());
                            preference.setSummary("Data Cleared Successfully");
                            //clearPreferences();
                            return true;
                        }
                    });
                }

            }

            if (preference instanceof Preference) {
                if (preference.getKey().equals("Reboot")) {

                    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            Reboot();
                            return true;
                        }
                    });
                }

            }

            if (preference instanceof Preference) {
                if (preference.getKey().equals("Reset")) {

                    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {
                            preference.setSummary(currentlats + " " + currentlongs);
                            return true;
                        }
                    });
                }

            }
            if (preference instanceof Preference) {
                if (preference.getKey().equals("Course")) {
                    SharedPreferences sharedPreferences = getInstance().getSharedPreferences("idData", MODE_PRIVATE);
                    String id = sharedPreferences.getString("id", "");
                    preference.setSummary(id);
                }
            }

            if(preference instanceof Preference){
                if(preference.getKey().equals("toggleme")){
                    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {

                            if (uiEnable) {
                                setSystemUiEnable(false);
                                preference.setTitle("Disable Ui");
                                uiEnable = false;
                            } else {
                                setSystemUIDisable(true);
                                uiEnable = true;
                                preference.setTitle("Enable Ui");

                            }

                            return false;
                        }

                    });

            }
        }


            if (preference instanceof Preference) {
                if (preference.getKey().equals("Ad")) {
                    SharedPreferences sharedPreferences = getInstance().getSharedPreferences("idData", MODE_PRIVATE);
                    String id = sharedPreferences.getString("AdVersion", "");
                    preference.setSummary(id);
                }
            }
            if (preference instanceof Preference) {
                if (preference.getKey().equals("Geofence")) {
                    SharedPreferences sharedPreferences = getInstance().getSharedPreferences("idData", MODE_PRIVATE);
                    String id = sharedPreferences.getString("GeoId", "");
                    preference.setSummary(id);
                }
            }
            if (preference instanceof Preference) {
                if (preference.getKey().equals("Facility")) {
                    SharedPreferences sharedPreferences = getInstance().getSharedPreferences("idData", MODE_PRIVATE);
                    String facility = sharedPreferences.getString("facilityname", "");
                    preference.setSummary(facility);
                }
            }


            if (preference instanceof Preference) {
                if (preference.getKey().equals("Exit")) {
                    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {

                            doLogOut();
                            return true;
                        }
                    });

                }
            }


            return true;
        }
    };


    /**
     * Helper method to determine if the device has an extra-large screen. For
     * example, 10" tablets are extra-large.
     */

    public static void doLogOut() {


        Intent intent = new Intent(getInstance(), NavigationDrawerActivity.class);
        getInstance().startActivity(intent);
        getInstance().finish();

    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mBatInfoReceiver1);
        super.onDestroy();
    }

    public static void changeUIstate(){

        if (uiEnable) {
            setSystemUIDisable(true);
            uiEnable = false;
        } else {
            setSystemUiEnable(false);
            uiEnable = true;
        }
    }

    public static void changePass() {

        Intent intent = new Intent(context, EnterPinActivity.class);
        intent.putExtra("setpin", true);
        context.startActivity(intent);
        getInstance().finish();
    }

    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }


    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Set the listener to watch for value changes.
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);

        // Trigger the listener immediately with the preference's
        // current value.
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference, PreferenceManager
                .getDefaultSharedPreferences(preference.getContext())
                .getString(preference.getKey(), ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        this.registerReceiver(this.mBatInfoReceiver1, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        prefsPrn = getSharedPreferences("prn", Context.MODE_PRIVATE);
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        getSupportActionBar().hide();
        context = this;
        activity = SettingsActivity.this;
        //setupActionBar();
        batteryLevel();
        versoinCodeofAndroid();
        deviceInformation();
        addPermission();
        MyService locationTrack = new MyService(SettingsActivity.this);
        if (locationTrack.canGetLocation()) {

            longitude = locationTrack.getLongitude();
            latitude = locationTrack.getLatitude();
        } else {

            locationTrack.showSettingsAlert();
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        gpsStatus = locationManager.getGpsStatus(null);
        locationManager.addGpsStatusListener(gpsListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2 * 1000, 0, locationListener);

        this.registerReceiver(this.mBatInfoReceiver1, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));

        SharedPreferences prefs = getSharedPreferences("CurrentLoc", MODE_PRIVATE);
        currentlats = Double.toString(latitude);
        currentlongs = Double.toString(longitude);
        instance = this;


        SharedPreferences pref = getSharedPreferences("Satellite", MODE_PRIVATE);
        lSatellites = pref.getString("Satellite", "Waiting for details...");
        toggleHideyBar();
    }


    private static void gpsTest() {


        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        GpsStatus gpsStatus = locationManager.getGpsStatus(null);
        if (gpsStatus != null) {
            Iterable<GpsSatellite> satellites = gpsStatus.getSatellites();
            Iterator<GpsSatellite> sat = satellites.iterator();
            int i = 0;
            while (sat.hasNext()) {
                GpsSatellite satellite = sat.next();
                i++;
                satellite.getPrn();
                satPrn.add(String.valueOf(satellite.getPrn()));
                satellite.getSnr();
                satSNR.add(String.valueOf(satellite.getSnr()));

            }
            addData();


        }
    }

    private static void addData() {
        satelite = "";
        for (int j = 0; j < satPrn.size(); j++) {
            satPrn.get(j);
            satSNR.get(j);
            satelite = satelite + String.valueOf(satPrn.get(j)) + " : " + String.valueOf(satSNR.get(j));
            SharedPreferences.Editor editor = prefsPrn.edit();
            editor.putString("satelite", satelite);
            editor.commit();
        }
    }


    private void addPermission() {

        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {


            if (permissionsToRequest.size() > 0)
                requestPermissions((String[]) permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList permissions) {
        ArrayList result = new ArrayList();

        for (Object perm : permissions) {
            if (!hasPermission((String) perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

//ang Developer

    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment.class.getName().equals(fragmentName)
                || DataSyncPreferenceFragment.class.getName().equals(fragmentName);
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

   /* @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Preference exercisesPref = findPreference(key);
        exercisesPref.setSummary(sharedPreferences.getString(key, ""));

        *//*if (mSwitchPreference instanceof Preference) {
            if (key.equals("Enable")) {
                boolean isEnable = sharedPreferences.getBoolean("Enable", false);
                if (isEnable) {
                    Toast.makeText(instance, "Enable", Toast.LENGTH_SHORT).show();
                    setSystemUIDisable(true);
                    mSwitchPreference.setSummary("Enabled");
                } else {
                    setSystemUiEnable(false);
                    mSwitchPreference.setSummary("Disabled");
                }
            }
        }*//*

    }*/

    static class GpsListener implements GpsStatus.Listener {


        @Override
        public void onGpsStatusChanged(int event) {
            gpsTest();


        }
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

            bindPreferenceSummaryToValue(findPreference("Device"));
            bindPreferenceSummaryToValue(findPreference("SIM"));
            bindPreferenceSummaryToValue(findPreference("Message"));
            bindPreferenceSummaryToValue(findPreference("Battery"));
            bindPreferenceSummaryToValue(findPreference("Level"));
            bindPreferenceSummaryToValue(findPreference("Location"));
            bindPreferenceSummaryToValue(findPreference("gps"));
            bindPreferenceSummaryToValue(findPreference("toggleme"));
            //mSwitchPreference = (SwitchPreference) findPreference("Enable");
            bindPreferenceSummaryToValue(findPreference("Reboot"));
            bindPreferenceSummaryToValue(findPreference("Reset"));
            bindPreferenceSummaryToValue(findPreference("Refresh"));
            bindPreferenceSummaryToValue(findPreference("Change"));
            bindPreferenceSummaryToValue(findPreference("Cart"));
            bindPreferenceSummaryToValue(findPreference("Stallite"));
            bindPreferenceSummaryToValue(findPreference("Course"));
            bindPreferenceSummaryToValue(findPreference("Ad"));
            bindPreferenceSummaryToValue(findPreference("Geofence"));
            bindPreferenceSummaryToValue(findPreference("Facility"));
            bindPreferenceSummaryToValue(findPreference("Exit"));

        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), NavigationDrawerActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }


    @SuppressLint("ValidFragment")
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public  static class DataSyncPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_data_sync);
            setHasOptionsMenu(true);
            getInstance().toggleHideyBar();
            bindPreferenceSummaryToValue(findPreference("sync_frequency"));
            Preference preference = (Preference) findPreference("Exit");
            bindPreferenceSummaryToValue(findPreference("Exit"));
            if (preference instanceof Preference) {
                if (preference.getKey().equals("Exit")) {
                    preference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
                        @Override
                        public boolean onPreferenceClick(Preference preference) {

                            doLogOut();
                            return true;
                        }
                    });

                }
            }


        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                startActivity(new Intent(getActivity(), NavigationDrawerActivity.class));
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        @Override
        public void onResume() {
            super.onResume();
            getInstance().toggleHideyBar();
           // unregisterReceiver(mBatInfoReceiver1);
        }

        @Override
        public void unregisterForContextMenu(View view) {
            super.unregisterForContextMenu(view);
        }

        @Override
        public void onPause() {
            super.onPause();
            getInstance().toggleHideyBar();
           // unregisterReceiver(mBatInfoReceiver1);

        }
    }


    private void batteryLevel() {
        BroadcastReceiver  batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                batterystats = ("Battery Level: " + level + "%");
            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }


    public static SettingsActivity getInstance() {
        return instance;
    }


    public void versoinCodeofAndroid() {

        StringBuilder builder = new StringBuilder();
        builder.append("android : ").append(Build.VERSION.RELEASE);

        Field[] fields = Build.VERSION_CODES.class.getFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            int fieldValue = -1;

            try {
                fieldValue = field.getInt(new Object());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }

            if (fieldValue == Build.VERSION.SDK_INT) {
                builder.append(" : ").append(fieldName).append(" : ");
                builder.append("sdk=").append(fieldValue);
            }
        }

        Log.e("version", "OS: " + builder.toString());
    }

    public void deviceInformation() {

        DeviceName.with(this).request(new DeviceName.Callback() {

            @SuppressLint("HardwareIds")
            @Override
            public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                String manufacturer = info.manufacturer;  // "Samsung"
                String name = info.marketName;            // "Galaxy S8+"
                String model = info.model;                // "SM-G955W"
                String codename = info.codename;          // "dream2qltecan"
                String deviceName = info.getName();


                Log.e("manufacturer", "manufacturer: " + manufacturer);
                Log.e("name", "name: " + name);
                Log.e("model", "model: " + model);
                Log.e("codename", "codename: " + codename);
                Log.e("deviceName", "deviceName: " + deviceName);
                Log.i("TAG", "SERIAL: " + Build.SERIAL);
                Log.i("TAG", "MODEL: " + Build.MODEL);
                Log.i("TAG", "ID: " + Build.ID);
                Log.i("TAG", "Manufacture: " + Build.MANUFACTURER);
                Log.i("TAG", "brand: " + Build.BRAND);
                Log.i("TAG", "type: " + Build.TYPE);
                Log.i("TAG", "user: " + Build.USER);
                Log.i("TAG", "BASE: " + Build.VERSION_CODES.BASE);
                Log.i("TAG", "INCREMENTAL " + Build.VERSION.INCREMENTAL);
                Log.i("TAG", "SDK  " + Build.VERSION.SDK);
                Log.i("TAG", "BOARD: " + Build.BOARD);
                Log.i("TAG", "BRAND " + Build.BRAND);
                Log.i("TAG", "HOST " + Build.HOST);
                Log.i("TAG", "FINGERPRINT: " + Build.FINGERPRINT);
                Log.i("TAG", "Version Code: " + Build.VERSION.RELEASE);

                // FYI: We are on the UI thread.
            }
        });
    }

    public static void Reboot() {
        //Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "reboot now"});
        try {
            Process proc = Runtime.getRuntime()
                    .exec(new String[]{ "su", "-c", "reboot" });
            proc.waitFor();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

       /* try {
            Runtime.getRuntime().exec(new String[]{"/system/bin/su", "-c", "reboot"});
        } catch (IOException e) {
            e.printStackTrace();
        }*/

    }

    private  BroadcastReceiver mBatInfoReceiver1 = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {
            try {

                int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

                BStatus = "No Data";
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    BStatus = "Charging";
                }
                if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                    BStatus = "Discharging";
                }
                if (status == BatteryManager.BATTERY_STATUS_FULL) {
                    BStatus = "Full";
                }
                if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                    BStatus = "Not Charging";
                }
                if (status == BatteryManager.BATTERY_STATUS_UNKNOWN) {
                    BStatus = "Unknown";
                }

                int chargePlug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                BattPowerSource = "Not Charging";
                if (chargePlug == BatteryManager.BATTERY_PLUGGED_AC) {
                    BattPowerSource = "AC";
                }
                if (chargePlug == BatteryManager.BATTERY_PLUGGED_USB) {
                    BattPowerSource = "USB";
                }

            } catch (Exception e) {

            }
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(SettingsActivity.this, NavigationDrawerActivity.class));
        finish();
    }


    public void toggleHideyBar() {

        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        newUiOptions = uiOptions;

        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {

        } else {
        }


        newUiOptions ^= View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        newUiOptions ^= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);


    }


    static class DummyLocationListener implements LocationListener {
        //Empty class just to ease instatiation
        @Override
        public void onLocationChanged(Location location) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    }
    public static void setSystemUiEnable(boolean disable) {

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "am startservice -n com.android.systemui/.SystemUIService"});
        } catch (Exception e) {
            Log.w("Main", "Failed to kill task bar (1).");
            e.printStackTrace();
        }
        try {
            proc.waitFor();
        } catch (Exception e) {
            Log.w("Main", "Failed to kill task bar (2).");
            e.printStackTrace();
        }

    }
    public static  void setSystemUIDisable(boolean disable) {
        Process proc = null;

        String ProcID = "79"; //HONEYCOMB AND OLDER

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            ProcID = "42"; //ICS AND NEWER
        }

        try {
            proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "service call activity " + ProcID + " s16 com.android.systemui"});
        } catch (Exception e) {
            Log.w("Main", "Failed to kill task bar (1).");
            e.printStackTrace();
        }
        try {
            proc.waitFor();
        } catch (Exception e) {
            Log.w("Main", "Failed to kill task bar (2).");
            e.printStackTrace();
        }

    }




}