package com.kishan.dynamicisland.observers;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;

import com.kishan.dynamicisland.utils.Logger;

public class NotificationListener extends NotificationListenerService {
    @Override
    public int onStartCommand(Intent intent, int i7, int i8) {
        return Service.START_STICKY;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Logger.d(NotificationListener.class, "onNotificationPosted", sbn.toString());
        handleMusicApp(sbn);
    }


    private void handleMusicApp(StatusBarNotification sbn) {
        //  if (sbn.getPackageName().equals("com.android.music")) {
        Notification notification = sbn.getNotification();
        Bundle extras = notification.extras;
        String title = extras.getString(Notification.EXTRA_TITLE);
        String artist = extras.getString(Notification.EXTRA_TEXT);
        int progress = extras.getInt(Notification.EXTRA_PROGRESS);
        int maxProgress = extras.getInt(Notification.EXTRA_PROGRESS_MAX);
        Logger.d(NotificationListener.class, "handleMusicApp", "title: " + title + ", artist: " + artist
                + ", progress: " + progress + ", maxProgress: " + maxProgress);

        //   }
    }
}
