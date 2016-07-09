package com.csatimes.dojma;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

import static com.csatimes.dojma.DHC.directory;


public class UpdateCheckerService extends IntentService {

    public static final String DOWNLOAD_SUCCESS_ACTION = "com.csatimes.dojma.intent.action.dns";
    public static final String UPDATE_CHECK_OVER = "com.csatimes.dojma.intent.action.uco";
    public static UpdateCheckerService instance;
    public static volatile boolean stop = false;
    private Realm database;
    private RealmConfiguration realmConfiguration;
    private boolean isUpdatePresent;
    private int noOfArticlesUpdatedByService = 0;
    private int noOfArticlesDownloadedByService = 0;

    public UpdateCheckerService() {
        super("UpdateCheckerService");
    }

    public static boolean isInstanceCreated() {
        return instance != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("TAG", "onHandleIntent");
        noOfArticlesDownloadedByService = 0;
        noOfArticlesUpdatedByService = 0;
        isUpdatePresent = false;
        stop = false;

        try {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        } catch (Exception ignore) {
        }

        realmConfiguration = new RealmConfiguration.Builder(UpdateCheckerService.this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();

        RealmList<HeraldLinks> links = new RealmList<>();
        links.addAll(database.where(HeraldLinks.class).findAll());

        for (HeraldLinks link : links) {
            if (!stop) {
                try {
                    Document HTMLDocument = Jsoup.connect(link.getAddress()).get();

                    if (HTMLDocument != null) {
                        Elements documentElements = HTMLDocument.getElementsByTag("article");
                        Attributes postIDAttribute, mainAttribute, dateAttrib, updateDateAttrib, imageURLAttribute;
                        String imageURL;

                        for (Element element : documentElements) {
                            isUpdatePresent = false;
                            postIDAttribute = element.attributes();
                            mainAttribute = element.child(0).child(0).attributes();
                            dateAttrib = element.child(1).child(0).child(0).child(0)
                                    .child(0).attributes
                                            ();
                            final String postIDTemp = postIDAttribute.get("id");

                            try {
                                updateDateAttrib = element.child(1).child(0).child(0).child(0)
                                        .child(1).attributes
                                                ();

                            } catch (Exception e) {
                                updateDateAttrib = dateAttrib;
                            }

                            if (!element.getElementsByAttribute("src").isEmpty()) {
                                imageURLAttribute = element.child(0).child(0).child(0)
                                        .attributes();
                                imageURL = imageURLAttribute.get
                                        ("src");
                            } else {
                                imageURL = "-1";
                            }

                            RealmResults<HeraldNewsItemFormat> results = database.where(HeraldNewsItemFormat
                                    .class).equalTo("postID", postIDTemp).findAll();

                            if (results.size() != 0) {
                                HeraldNewsItemFormat temp = results.get(0);
                                if (temp.getTitle().compareToIgnoreCase(mainAttribute.get("title")) != 0) {

                                    database.beginTransaction();
                                    temp.setTitle(mainAttribute.get("title"));
                                    database.commitTransaction();

                                    isUpdatePresent = true;
                                }
                                if (temp.getUpdateTime().compareTo(updateDateAttrib.get("datetime")
                                        .substring(10, 16)) !=
                                        0) {


                                    database.beginTransaction();
                                    temp.setUpdateTime(updateDateAttrib.get("datetime").substring(10, 16));
                                    database.commitTransaction();

                                    isUpdatePresent = true;
                                }
                                if (temp.getOriginalTime().compareTo(dateAttrib.get("datetime")
                                        .substring(10, 16)) !=
                                        0) {


                                    database.beginTransaction();
                                    temp.setOriginalTime(dateAttrib.get("datetime").substring(10, 16));
                                    database.commitTransaction();

                                    isUpdatePresent = true;
                                }

                                if (temp.getUpdateDate().compareTo(updateDateAttrib.get("datetime")
                                        .substring(0, 10)) != 0) {

                                    database.beginTransaction();
                                    temp.setUpdateDate(updateDateAttrib.get("datetime").substring(0, 10));
                                    database.commitTransaction();

                                    isUpdatePresent = true;
                                }


                                if (temp.getLink().compareTo(mainAttribute.get("href")) != 0) {

                                    database.beginTransaction();
                                    temp.setLink(mainAttribute.get("href"));
                                    database.commitTransaction();

                                    isUpdatePresent = true;
                                }

                                if (temp.getImageURL().compareTo(imageURL) != 0) {

                                    database.beginTransaction();
                                    temp.setImageURL(imageURL);
                                    database.commitTransaction();

                                    isUpdatePresent = true;
                                }
                                if (isUpdatePresent)
                                    noOfArticlesUpdatedByService++;
                            } else {
                                String description = null;
                                {
                                    Document desc = Jsoup.connect(mainAttribute.get("href")).get();
                                    Elements elements = desc.getElementsByTag("p");
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (Element element1 : elements) {
                                        if (element1.hasText())
                                            stringBuilder.append(element1.text() + "\n");
                                    }
                                    description = stringBuilder.toString();
                                }
                                database.beginTransaction();

                                HeraldNewsItemFormat _temp = database.createObject(HeraldNewsItemFormat
                                        .class);
                                _temp.setTitle(mainAttribute.get("title"));
                                _temp.setPostID(postIDTemp);
                                _temp.setAuthor("dojma_admin");
                                _temp.setUpdateDate(updateDateAttrib.get("datetime").substring(0, 10));
                                _temp.setLink(mainAttribute.get("href"));
                                _temp.setOriginalDate(dateAttrib.get("datetime").substring(0, 10));
                                _temp.setImageURL(imageURL);
                                _temp.setDismissed(false);
                                _temp.setDesc(description);
                                _temp.setOriginalTime(dateAttrib.get("datetime").substring(10, 16));
                                _temp.setUpdateTime(updateDateAttrib.get("datetime").substring(10, 16));
                                _temp.setImageSavedLoc(directory + "/" + postIDTemp + ".jpeg");

                                database.commitTransaction();
                                noOfArticlesDownloadedByService++;

                            }
                        }
                    }

                } catch (IOException e) {
                }
            }
        }

        String message = null;

        if (noOfArticlesDownloadedByService != 0 || noOfArticlesUpdatedByService != 0) {

            //Send update available broadcast if Herald fragment is attached
            Intent i = new Intent();
            i.setAction(DOWNLOAD_SUCCESS_ACTION);
            sendBroadcast(i);

            Log.e("TAG", noOfArticlesDownloadedByService + " " + noOfArticlesUpdatedByService);

            //create message for notification
            message = setNotificationMessage(noOfArticlesDownloadedByService, noOfArticlesUpdatedByService);
            if (message != null) {
                Intent openHerald = new Intent(this, HomeActivity.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(this,
                        DHC.UPDATE_SERVICE_PENDING_INTENT_CODE, openHerald, PendingIntent.FLAG_CANCEL_CURRENT);

                NotificationCompat.Builder downloadNotif =
                        new NotificationCompat.Builder(this).setAutoCancel(true)
                                .setSmallIcon(R.drawable.dojma)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dojma))
                                .setColor(Color.BLACK)
                                .setContentTitle("DoJMA update ")
                                .setContentText(message);

                downloadNotif.setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(DHC.UPDATE_SERVICE_NOTIFICATION_CODE, downloadNotif.build());
            }
        } else {

            Intent i = new Intent();
            i.setAction(UPDATE_CHECK_OVER);
            sendBroadcast(i);
        }
        database.close();
    }

    @NonNull
    private String setNotificationMessage(int downloads, int updates) {
        String foo = null;

        if (updates != 0 && downloads != 0) {
            if (downloads == 1)
                foo = "1 article downloaded and " + updates + " " +
                        "updated";
            else
                foo = downloads + " articles downloaded and " +
                        updates + " updated";

        } else if (downloads == 0) {
            if (updates == 1)
                foo = "1 article updated";
            else {
                foo = updates + " articles updated";
            }
        } else if (updates == 0) {
            if (downloads == 1)
                foo = "1 article downloaded";
            else foo = downloads +
                    " articles downloaded";
        } else return null;

        return foo;
    }


}
