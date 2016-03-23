package com.iq.net.skypress;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by Leila Nizam on 1/17/2016.
 */
public class SkyPressApp extends Application{
    private static SkyPressApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;

        Parse.initialize(mInstance, AppConfig.PARSE_APPLICATION_ID, AppConfig.PARSE_CLIENT_KEY);
    }


    public static synchronized SkyPressApp getInstance() {
        return mInstance;
    }
}
