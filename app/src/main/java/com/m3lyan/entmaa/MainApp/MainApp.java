package com.m3lyan.entmaa.MainApp;


import android.app.Application;
import android.content.Context;

import com.m3lyan.entmaa.Network.APIService;
import com.m3lyan.entmaa.Network.ApiUtils;


public class MainApp extends Application {

    public static APIService apiService= ApiUtils.getSOService();

    private static MainApp mInstance;
    public static MainApp getsInstance() {

        return mInstance;
    }

    public static Context getAppContext() {
        return mInstance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}

