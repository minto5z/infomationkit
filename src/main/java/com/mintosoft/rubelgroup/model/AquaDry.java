package com.mintosoft.rubelgroup.model;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class AquaDry extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}