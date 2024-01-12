package com.kishan.dynamicisland;

import android.app.Application;

public class App extends Application {
    private static App instance = null;

    public static App getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
