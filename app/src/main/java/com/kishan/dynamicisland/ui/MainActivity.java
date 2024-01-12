package com.kishan.dynamicisland.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kishan.dynamicisland.R;
import com.kishan.dynamicisland.utils.PrefHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAccService;
    Button btnNotificationService;
    Button btnShowSpot;
    Button btnHideSpot;
    Button btnIncreaseSpotHeight;
    Button btnDecreaseSpotHeight;
    Button btnIncreaseSpotWidth;
    Button btnDecreaseSpotWidth;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAccService = findViewById(R.id.grantAccService);
        btnNotificationService = findViewById(R.id.grantNotificationServiceAccess);
        btnShowSpot = findViewById(R.id.showSpot);
        btnHideSpot = findViewById(R.id.hideSpot);
        btnIncreaseSpotHeight = findViewById(R.id.btnIncreaseHeight);
        btnDecreaseSpotHeight = findViewById(R.id.btnDecreaseHeight);
        btnIncreaseSpotWidth = findViewById(R.id.btnIncreaseWidth);
        btnDecreaseSpotWidth = findViewById(R.id.btnDecreaseWidth);
        PrefHelper.getInstance().hideSpot();

        btnAccService.setOnClickListener(this);
        btnNotificationService.setOnClickListener(this);
        btnShowSpot.setOnClickListener(this);
        btnHideSpot.setOnClickListener(this);
        btnIncreaseSpotHeight.setOnClickListener(this);
        btnDecreaseSpotHeight.setOnClickListener(this);
        btnIncreaseSpotWidth.setOnClickListener(this);
        btnDecreaseSpotWidth.setOnClickListener(this);
        btnIncreaseSpotHeight.setOnTouchListener(new View.OnTouchListener() {
            private Handler mHandler;
            @SuppressLint("ClickableViewAccessibility")
            private final int DELAY = 50; // delay in milliseconds
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler(Looper.getMainLooper());
                        mHandler.postDelayed(mAction, DELAY);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }
            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    // Perform the action here

                    int height = PrefHelper.getInstance().getSpotHeight();
                    PrefHelper.getInstance().updateSpotHeight(height + 1);
                    mHandler.postDelayed(this, DELAY);
                }
            };
        });
    }

    @Override
    public void onClick(View view) {
        if (view == btnAccService) {
            Intent intent = new Intent("com.samsung.accessibility.installed_service");
            if (intent.resolveActivity(this.getPackageManager()) == null) {
                intent = new Intent("android.settings.ACCESSIBILITY_SETTINGS");
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityForResult(intent, 300);
        } else if (view == btnNotificationService) {
            startActivity(new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"));
        } else if (view == btnShowSpot) {
           PrefHelper.getInstance().showSpot();
        } else if (view == btnHideSpot) {
            PrefHelper.getInstance().hideSpot();
        } else if (view == btnIncreaseSpotHeight) {
            int height = PrefHelper.getInstance().getSpotHeight();
            PrefHelper.getInstance().updateSpotHeight(height + 5);
        } else if (view == btnDecreaseSpotHeight) {
            int height = PrefHelper.getInstance().getSpotHeight();
            PrefHelper.getInstance().updateSpotHeight(height - 5);
        } else if (view == btnIncreaseSpotWidth) {
            int width = PrefHelper.getInstance().getSpotWidth();
            PrefHelper.getInstance().updateSpotWidth(width + 5);
        } else if (view == btnDecreaseSpotWidth) {
            int width = PrefHelper.getInstance().getSpotWidth();
            PrefHelper.getInstance().updateSpotWidth(width - 5);
        }
    }

}