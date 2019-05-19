package com.escom.topsecret.utils;

import android.content.Context;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.escom.topsecret.R;

public class NotificationHelper {

    public static final String CHANNEL_ID = "SECRET_SHARING";
    public static final String CHANNEL_NAME = "Secret Sharing";
    public static final String CHANNEL_DESC = "Secret Sharing Notifications";

    public static void displayNotification(Context context, String title, String body){

        String str;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat mNotificationManager = NotificationManagerCompat.from(context);
        mNotificationManager.notify(1, mBuilder.build());
    }
}
