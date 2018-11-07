package com.app.golfapp.ui.activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Handler;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.app.golfapp.Manifest;
import com.app.golfapp.R;
import com.app.golfapp.ui.andrognito.pinlockview.CustomizationOptionsBundle;

import java.io.File;

import static com.app.golfapp.R.drawable.*;
import static com.app.golfapp.R.drawable.welcome_screen;
import static junit.framework.Assert.assertTrue;

public class SplashScreen extends BaseActivity {
    public static String batterystats = "";
    private Handler mToastHandler;
    private SharedPreferences sharedPreferences;
    private String BStatus,BattPowerSource;
    BroadcastReceiver batteryLevelReceiver;
    BroadcastReceiver mBatInfoReceiver1;
    private int REQUEST_CODE;
    boolean permissionStatus;
    MyPhoneStateListener phoneListener;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setSystemUIDisable(true);

       /* permissionStatus = isReadStoragePermissionGranted();
        permissionStatus=  isWriteStoragePermissionGranted();
        permissionStatus=  isLocationPermissionGranted();
        permissionStatus =  isTelephonyManagerPermissionGranted();
*/
        if (isReadStoragePermissionGranted()) {


            validator();
        } else {

            permissionStatus = isReadStoragePermissionGranted();
            ;
        }


    }







    @Override
    public void onDestroy() {

      /*  try{
            if(batteryLevelReceiver!=null)
                unregisterReceiver(batteryLevelReceiver);

        }catch(Exception e){
            e.printStackTrace();
        }*/
        try{
            if(mBatInfoReceiver1!=null)
                unregisterReceiver(mBatInfoReceiver1);

        }catch(Exception e){
            e.printStackTrace();
        }


        super.onDestroy();
    }
    @UiThreadTest
    public void testFlagsChanged() {
        final int uiFlags = getWindow().getDecorView().getSystemUiVisibility();
        //mTestFragment.mImmersiveModeCheckBox.setChecked(true);
        //mTestFragment.mHideNavCheckbox.setChecked(true);
        // mTestFragment.mHideStatusBarCheckBox.setChecked(true);
        toggleUiFlags();
        mToastHandler = new Handler();

        final int newUiFlags = getWindow().getDecorView().getSystemUiVisibility();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                assertTrue("UI Flags didn't toggle.", uiFlags != newUiFlags);


            }
        }, 50);


    }
    @SuppressLint("LongLogTag")
    public void toggleUiFlags() {
        int uiOptions = getWindow().getDecorView().getSystemUiVisibility();
        int newUiOptions = uiOptions;
        ;

        // BEGIN_INCLUDE (get_current_ui_flags)
        // The "Decor View" is the parent view of the Activity.  It's also conveniently the easiest
        // one to find from within a fragment, since there's a handy helper method to pull it, and
        // we don't have to bother with picking a view somewhere deeper in the hierarchy and calling
        // "findViewById" on it.
        boolean isImmersiveModeEnabled =
                ((uiOptions | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == uiOptions);
        if (isImmersiveModeEnabled) {
            Log.i("", "Turning immersive mode mode off. ");
        } else {
            Log.i("", "Turning immersive mode mode on.");
        }

        // Immersive mode: Backward compatible to KitKat (API 19).
        // Note that this flag doesn't do anything by itself, it only augments the behavior
        // of HIDE_NAVIGATION and FLAG_FULLSCREEN.  For the purposes of this sample
        // all three flags are being toggled together.
        // This sample uses the "sticky" form of immersive mode, which will let the user swipe
        // the bars back in again, but will automatically make them disappear a few seconds later.
        newUiOptions ^= View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
        newUiOptions ^= View.SYSTEM_UI_FLAG_FULLSCREEN;
        newUiOptions ^= View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        getWindow().getDecorView().setSystemUiVisibility(newUiOptions);

        // dumpFlagStateToLog(uiOptions);
    }

    private void batteryLevel() {
         batteryLevelReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                context.unregisterReceiver(this);
                int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
                int temp=intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
                int temprature=temp/10;
                int voltage=intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);

                int level = -1;
                if (rawlevel >= 0 && scale > 0) {
                    level = (rawlevel * 100) / scale;
                }
                batterystats = ("Battery Level: " + level + "%");
                sharedPreferences=getSharedPreferences("battery",MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("level",String.valueOf(level));
                editor.putString("temp",String.valueOf(temprature));
                editor.putString("volt",String.valueOf(voltage));
                editor.commit();


            }
        };
        IntentFilter batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(batteryLevelReceiver, batteryLevelFilter);
    }
private void bStatus() {
      mBatInfoReceiver1 = new BroadcastReceiver() {
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
            sharedPreferences=getSharedPreferences("battery",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("status",BStatus);
            editor.putString("batpower",BattPowerSource);
           // editor.putString("volt",String.valueOf(voltage));
            editor.commit();

        }


    };
    IntentFilter batteryLevelFilter1 = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    registerReceiver(mBatInfoReceiver1, batteryLevelFilter1);


}
    public  boolean isReadStoragePermissionGranted() {
        try{
            if (Build.VERSION.SDK_INT >= 23) {
                if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Log.v("","Permission is granted1");
                    return true;
                } else {

                    Log.v("","Permission is revoked1");
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_FINE_LOCATION
                            ,android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.READ_PHONE_STATE,android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 3);
                    return false;
                }
            }
            else {
                Log.v("","Permission is granted1");
                return true;
            }}catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        try{
            switch (requestCode) {


                case 3:
                    Log.d("", "External storage1");
                    if(grantResults.length>3){
                        if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
                            Log.v("","Permission: "+permissions[0]+ "was "+grantResults[0]);
                            validator();

                        }else{

                        }
                    }

                    break;
//
            }
        }catch (Exception exc){
            exc.printStackTrace();
        }

    }
    private void validator() {
        testFlagsChanged();
        phoneListener = new MyPhoneStateListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,


                PhoneStateListener.LISTEN_SERVICE_STATE |
                        PhoneStateListener.LISTEN_SIGNAL_STRENGTH);
        SharedPreferences sharedPreferences=getSharedPreferences("welcomescreen",MODE_PRIVATE);
        if(sharedPreferences.contains("welcome")){
            String welcome =sharedPreferences.getString("welcome","");
            imageView=(ImageView) findViewById(R.id.image_chage);
            File file= new File(welcome);
            Bitmap mBitmap;
            mBitmap = BitmapFactory.decodeFile(file.getPath());
            if(mBitmap == null || mBitmap.equals("")){

                imageView.setImageDrawable(getResources().getDrawable(R.drawable.welcome_screen));

            }
            else{
                imageView.setImageBitmap(mBitmap);

            }



        }
        //phoneListener.getSignalStrengthByName().



        batteryLevel();
        bStatus();


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                startActivity(new Intent(SplashScreen.this, NavigationDrawerActivity.class));
                finish();
            }


        }, 9000);
    }

    public void setSystemUIDisable(boolean enabled) {
        Process proc = null;

        String ProcID = "79"; //HONEYCOMB AND OLDER

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH){
            ProcID = "42"; //ICS AND NEWER
        }

        try {
           // proc = Runtime.getRuntime().exec(new String[] { "su", "-c", "service call activity "+ProcID+" s16 com.android.systemui" });
        } catch (Exception e) {
            Log.w("Main","Failed to kill task bar (1).");
            e.printStackTrace();
        }
        try {
            proc.waitFor();
        } catch (Exception e) {
            Log.w("Main","Failed to kill task bar (2).");
            e.printStackTrace();
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


}







