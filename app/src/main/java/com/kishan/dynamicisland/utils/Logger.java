package com.kishan.dynamicisland.utils;

import android.util.Log;

public class Logger {

    public static void d(Class<?> clsName, String tag, String msg) {
        Log.d(clsName.getSimpleName(), tag + ": "+  msg);
    }

    public static void e(Class<?> clsName, String tag, String msg) {
        Log.e(clsName.getSimpleName(), tag + ": "+  msg);
    }

}
