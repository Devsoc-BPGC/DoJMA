package com.csatimes.dojma;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class HandleFirebaseMessages extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(this, HomeActivity.class);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(this, 69, resultIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dojma))
                        .setSmallIcon(R.drawable.dojma)
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setAutoCancel(true)
                        .setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.

        Map<String, String> map = remoteMessage.getData();
        String summary = "DoJMA update";
        if (map.containsKey("summary")) {
            summary = map.get("summary");
        }

        if (map.containsKey("extra")) {
            mBuilder.setStyle(new NotificationCompat
                    .BigTextStyle()
                    .bigText(map.get("extra"))
                    .setSummaryText(summary));
        } else if (map.containsKey("extras")) {
            mBuilder.setStyle(new NotificationCompat
                    .BigTextStyle()
                    .bigText(map.get("extras"))
                    .setSummaryText(summary));
        }

        mNotificationManager.notify(6969, mBuilder.build());
    }
}
