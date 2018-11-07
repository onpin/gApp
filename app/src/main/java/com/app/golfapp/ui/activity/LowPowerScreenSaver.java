package com.app.golfapp.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SignalStrength;
import android.test.UiThreadTest;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.app.golfapp.R;
//import com.app.golfapp.ui.Services.PowerSaveModeReceiver;
import com.app.golfapp.utils.BaseCommandUtil;
import com.app.golfapp.utils.BatterySaverModeUtil;
import com.cunoraz.gifview.library.GifView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import static com.app.golfapp.ui.activity.SettingsActivity.context;
import static junit.framework.Assert.assertTrue;

public class LowPowerScreenSaver extends AppCompatActivity {
    //    PowerSaveModeReceiver receiver;
    PowerManager.WakeLock mWakeLock;
    private Handler mToastHandler;
    GifView gifView1;
    RelativeLayout rlLowPOwer;

    private int currentApiVersion;
    Handler _handler;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_low_power_screen_saver);
          _handler = new Handler();

                currentApiVersion = android.os.Build.VERSION.SDK_INT;

                final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

                // This work only for android 4.4+
                if(currentApiVersion >= Build.VERSION_CODES.KITKAT)
                {

                    getWindow().getDecorView().setSystemUiVisibility(flags);

                    // Code below is to handle presses of Volume up or Volume down.
                    // Without this, after pressing volume buttons, the navigation bar will
                    // show up and won't hide
                    final View decorView = getWindow().getDecorView();
                    decorView
                            .setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener()
                            {

                                @Override
                                public void onSystemUiVisibilityChange(int visibility)
                                {
                                    if((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0)
                                    {
                                        decorView.setSystemUiVisibility(flags);
                                    }
                                }
                            });
                }

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            setBatterySaverMode();

            // only for gingerbread and newer versions
        } else {

        }
        rlLowPOwer = findViewById(R.id.rl_low);
        rlLowPOwer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LowPowerScreenSaver.this, SplashScreen.class);
                startActivity(intent);

            }
        });

        gifView1 = (GifView) findViewById(R.id.gif1);
        gifView1.setVisibility(View.VISIBLE);
        gifView1.play();

        gifView1.setGifResource(R.drawable.golf_gif);
        gifView1.getGifResource();


    }

    @SuppressLint("NewApi")
    private void setBatterySaverMode() {
        PowerManager manager = (PowerManager) getSystemService(Context.POWER_SERVICE);
       boolean batterySaverModeNow = manager.isPowerSaveMode();
        if (!batterySaverModeNow)
            BatterySaverModeUtil.enable();
        else
            BatterySaverModeUtil.disable();

    }



    public static class BatterySaverModeUtil extends BaseCommandUtil {
        private static String COMMAND_ENABLE = "settings put global low_power 1\n" +
                "am broadcast -a android.os.action.POWER_SAVE_MODE_CHANGED --ez mode true\n";
        private static String COMMAND_DISABLE = "settings put global low_power 0\n" +
                "am broadcast -a android.os.action.POWER_SAVE_MODE_CHANGED --ez mode false\n";

        static void enable() {
            runCommandWithRoot(COMMAND_ENABLE);
        }

        static void disable() {
            runCommandWithRoot(COMMAND_DISABLE);
        }

    }




}



