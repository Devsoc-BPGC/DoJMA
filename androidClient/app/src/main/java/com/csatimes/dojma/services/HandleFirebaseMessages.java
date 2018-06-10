package com.csatimes.dojma.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.MainActivity;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;


public class HandleFirebaseMessages extends FirebaseMessagingService {

    private static final int DEFAULT_ID = 42;
    private static final String TAG = HandleFirebaseMessages.class.getSimpleName();

    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        final Map<String, String> data = remoteMessage.getData();

        if (data != null) {
            //In case data is wrongly formatted
            //we use try catch block

            final int version = DHC.VERSION;

            if (data.get("version") != null) {
                final int supportsMin = Integer.parseInt(data.get("version"));
                if (version < supportsMin) return;
            }

            final NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

            int notificationId = DEFAULT_ID;
            final String type = data.get("type");
            final String id = data.get("id");
            final String smallTitle = data.get("smallTitle");
            final String smallSubTitle = data.get("smallSubTitle");
            final String contentInfo = data.get("contentInfo");
            final String ticker = data.get("ticker");
            final String link = data.get("link");
            final String className = data.get("className");

            if (type != null) {
                if (type.compareTo("1") == 0) {
                    //Large Text Style corresponds to "1"
                    final String bigTitle = data.get("bigTitle");
                    final String bigSubTitle = data.get("bigSubTitle");
                    final String bigSummaryText = data.get("bigSummaryText");

                    final NotificationCompat.BigTextStyle notificationBigText
                            = new NotificationCompat.BigTextStyle();

                    if (bigTitle != null) notificationBigText.setBigContentTitle(bigTitle);
                    if (bigSubTitle != null) notificationBigText.bigText(bigSubTitle);
                    if (bigSummaryText != null) notificationBigText.setSummaryText(bigSummaryText);

                    builder.setStyle(notificationBigText);
                } else if (type.compareTo("2") == 0) {
                    //BigPicture style specific

                    final String imageUrl = data.get("imageUrl");
                    final String bigSummaryText = data.get("bigSummaryText");
                    final String bigTitle = data.get("bigTitle");

                    final NotificationCompat.BigPictureStyle notificationBigPicture
                            = new NotificationCompat.BigPictureStyle();
                    if (imageUrl != null) {
                        final Bitmap image = getBitmapFromURL(imageUrl);
                        if (image != null) {
                            notificationBigPicture.bigPicture(image);
                        }
                    }
                    if (bigSummaryText != null) {
                        notificationBigPicture.setSummaryText(bigSummaryText);
                    }
                    if (bigTitle != null) notificationBigPicture.setBigContentTitle(bigTitle);
                    builder.setStyle(notificationBigPicture);
                }
            }


            //General things to be added for any kind of notification
            if (smallTitle != null) builder.setContentTitle(smallTitle);
            if (smallSubTitle != null) builder.setContentText(smallSubTitle);
            if (id != null) notificationId = Integer.parseInt(id);
            builder.setContentIntent(addWebsiteLinkPendingIntent(notificationId, link, className));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder.setCategory(Notification.CATEGORY_SOCIAL);
            }

            builder.setSmallIcon(R.drawable.ic_stat_d);
            builder.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
            builder.setAutoCancel(true);

            if (ticker != null) {
                builder.setTicker(ticker);
            } else {
                builder.setTicker("New campus news!");
            }

            if (contentInfo != null) {
                builder.setContentInfo(contentInfo);
            } else {
                builder.setContentInfo("DoJMA");
            }

            final NotificationManagerCompat mNotificationManager
                    = NotificationManagerCompat.from(this);
            mNotificationManager.notify(notificationId, builder.build());

        }
    }

    public static Bitmap getBitmapFromURL(final String src) {
        final URL url;
        final HttpURLConnection connection;
        try {
            url = new URL(src);
        } catch (final MalformedURLException e) {
            Log.e(TAG, e.getMessage(), e.fillInStackTrace());
            return null;
        }
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            final InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (final IOException e) {
            Log.e(TAG, e.getMessage(), e.fillInStackTrace());
            return null;
        }
    }

    private PendingIntent addWebsiteLinkPendingIntent(final int id,
                                                      final String link,
                                                      final String className) {
        Intent intent;

        if (link != null) {
            //TODO Change to ChromeCustomTabs later
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
        } else if (className != null) {
            try {
                intent = new Intent(this, Class.forName("com.csatimes.dojma." + className));
                //TODO check for page number
            } catch (final ClassNotFoundException e) {
                intent = new Intent(this, MainActivity.class);
            }
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        } else {
            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        }

        return PendingIntent.getActivity(
                this,
                id,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }
}
