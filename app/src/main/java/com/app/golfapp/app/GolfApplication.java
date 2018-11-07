package com.app.golfapp.app;

import android.app.Application;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.app.golfapp.R;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class GolfApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Precious.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
}
}
