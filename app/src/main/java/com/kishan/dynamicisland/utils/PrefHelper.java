package com.kishan.dynamicisland.utils;

import static com.kishan.dynamicisland.utils.Constants.DEFAULT_SPOT_HEIGHT;
import static com.kishan.dynamicisland.utils.Constants.DEFAULT_SPOT_WIDTH;

import android.content.Context;
import android.content.SharedPreferences;

import com.kishan.dynamicisland.App;

public class PrefHelper {
    private static PrefHelper instance = null;
    private final SharedPreferences sharedPreferences;
    private final String PREF_NAME = "local-app-data";

    PrefHelper() {
        sharedPreferences = App.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static PrefHelper getInstance() {
        if (instance == null) {
            instance = new PrefHelper();
        }
        return instance;
    }

    public void showSpot() {
        Logger.d(PrefHelper.class, "showSpot", "");
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(Constants.KEY_VISIBLE_SPOT, true);
        editor.commit();
    }

    public void hideSpot() {
        Logger.d(PrefHelper.class, "hideSpot", "");
        SharedPreferences.Editor editor = getEditor();
        editor.putBoolean(Constants.KEY_VISIBLE_SPOT, false);
        editor.commit();
    }


    public boolean isSpotVisible() {
        Logger.d(PrefHelper.class, "isSpotVisible", "");
        return sharedPreferences.getBoolean(Constants.KEY_VISIBLE_SPOT, false);
    }

    public void updateSpotHeight(int newHeight) {
        Logger.d(PrefHelper.class, "updateSpotHeight", "");
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(Constants.KEY_SPOT_HEIGHT, newHeight);
        editor.commit();
    }

    public int getSpotHeight() {
        Logger.d(PrefHelper.class, "getSpotHeight", "");
        return sharedPreferences.getInt(Constants.KEY_SPOT_HEIGHT, DEFAULT_SPOT_HEIGHT);
    }

    public void updateSpotWidth(int newWidth) {
        Logger.d(PrefHelper.class, "updateSpotWidth", "");
        SharedPreferences.Editor editor = getEditor();
        editor.putInt(Constants.KEY_SPOT_WIDTH, newWidth);
        editor.commit();
    }

    public int getSpotWidth() {
        Logger.d(PrefHelper.class, "getSpotWidth", "");
        return sharedPreferences.getInt(Constants.KEY_SPOT_WIDTH, DEFAULT_SPOT_WIDTH);
    }

    private SharedPreferences.Editor getEditor() {
        return sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
