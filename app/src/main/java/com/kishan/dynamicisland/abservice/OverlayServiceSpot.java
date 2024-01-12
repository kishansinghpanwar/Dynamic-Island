package com.kishan.dynamicisland.abservice;

import android.accessibilityservice.AccessibilityService;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.kishan.dynamicisland.App;
import com.kishan.dynamicisland.R;
import com.kishan.dynamicisland.customview.VisualizerView;
import com.kishan.dynamicisland.utils.Constants;
import com.kishan.dynamicisland.utils.Logger;
import com.kishan.dynamicisland.utils.PrefHelper;

import java.util.Objects;
import java.util.Random;

public class OverlayServiceSpot extends AccessibilityService implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int SHOW_UI_CLICKABLE = 17040168;
    private static final int SHOW_UI_NON_CLICKABLE = 16778040;
    LayoutInflater layoutInflater;
    private Context context;
    private WindowManager windowManager;
    private View layoutSpot;

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(OverlayServiceSpot.class, "onCreate", "");

        context = App.getInstance().getApplicationContext();
        layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        PrefHelper.getInstance().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    private View getSpotLayout() {
        if (layoutSpot == null) {
            layoutSpot = layoutInflater.inflate(R.layout.item_spot, null);
            handleSpotClick();
        }
        return layoutSpot;
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        Logger.d(OverlayServiceSpot.class, "onAccessibilityEvent", accessibilityEvent.toString());

    }

    @Override
    public void onInterrupt() {
        Logger.d(OverlayServiceSpot.class, "onInterrupt", "");

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, @Nullable String key) {
        Logger.d(OverlayServiceSpot.class, "onSharedPreferenceChanged", key);
        if (Objects.equals(key, Constants.KEY_VISIBLE_SPOT)) {
            if (PrefHelper.getInstance().isSpotVisible()) {
                showSpot();
            } else {
                hideSpot();
            }
        } else if (Objects.equals(key, Constants.KEY_SPOT_HEIGHT)
                || Objects.equals(key, Constants.KEY_SPOT_WIDTH)) {
            updateSpotPosition();
        }
    }

    private void showSpot() {
        windowManager.addView(getSpotLayout(), getLayoutParams());
    }

    private void updateSpotPosition() {
        windowManager.updateViewLayout(getSpotLayout(), getLayoutParams());
    }

    private WindowManager.LayoutParams getLayoutParams() {
        WindowManager.LayoutParams layoutParams
                = new WindowManager.LayoutParams(getDimen(PrefHelper.getInstance().getSpotWidth()), getDimen(PrefHelper.getInstance().getSpotHeight()),
                WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY, SHOW_UI_CLICKABLE, PixelFormat.TRANSLUCENT);

        //layoutParams.systemUiVisibility = 5124;
        layoutParams.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;
        layoutParams.gravity = Gravity.TOP;
        return layoutParams;
    }

    private void hideSpot() {
        windowManager.removeViewImmediate(getSpotLayout());
    }

    public int getDimen(float f7) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, f7, getResources().getDisplayMetrics());
    }

    private void handleSpotClick() {
        Logger.d(OverlayServiceSpot.class, "handleSpotClick", "inside");
        CardView cardView = layoutSpot.findViewById(R.id.cardView);
        VisualizerView visualizer = layoutSpot.findViewById(R.id.visualizer);
        cardView.setOnClickListener(view -> {
            Logger.d(OverlayServiceSpot.class, "handleSpotClick", "Click");
            Toast.makeText(context, "Hi, hehe", Toast.LENGTH_SHORT).show();

            CountDownTimer countDownTimer = new CountDownTimer(50000,1) {
                @Override
                public void onTick(long l) {
                    Log.e("here","l here:" + l);
                    visualizer.addAmplitude(getRandomNumber(0,50000));
                    visualizer.invalidate();
                }

                @Override
                public void onFinish() {

                }
            };
            countDownTimer.start();
        });



    }


    /**
     * min and max are to be understood inclusively
     */
    public int getRandomNumber(int min, int max) {
        return (new Random()).nextInt((max - min) + 1) + min;
    }
}
