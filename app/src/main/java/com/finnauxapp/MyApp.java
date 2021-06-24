package com.finnauxapp;

import android.app.Application;

import com.finnauxapp.Util.TypefaceUtil;

public class MyApp extends Application {
    private static MyApp instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
     // TypefaceUtil.overrideFont(getApplicationContext(), "normal", "fonts/Poppins-Regular.ttf");
    }


    public static MyApp getContext() {
        return instance;
    }

}
