package com.csatimes.dojma;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import static com.csatimes.dojma.DHC.directory;


public class UpdateCheckerService extends IntentService {

    private RealmConfiguration realmConfiguration;
    private Realm database;
    private int noOfArticlesDownloadedByService = 0;
    private boolean isUpdatePresent;
    private int noOfArticlesUpdatedByService = 0;

    public UpdateCheckerService() {
        super("UpdateCheckerService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = DHC.address;
        noOfArticlesDownloadedByService = 0;
        noOfArticlesUpdatedByService = 0;
        isUpdatePresent = false;

        try {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        } catch (Exception ignore) {
        }

        try {
            Document HTMLDocument = Jsoup.connect(url).get();
            realmConfiguration = new RealmConfiguration.Builder(UpdateCheckerService.this)
                    .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            database = Realm.getDefaultInstance();


            if (HTMLDocument != null) {
                Toast.makeText(this, "HTML downloaded", Toast.LENGTH_SHORT).show();
                Elements documentElements = HTMLDocument.getElementsByTag("article");

                Attributes postIDAttribute, mainAttribute, dateAttrib,
                        updateDateAttrib, imageURLAttribute;
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

                    // also //time check
                    // is also important

                    RealmResults<HeraldNewsItemFormat> results = database.where(HeraldNewsItemFormat
                            .class)
                            .equalTo("postID", postIDTemp).findAll();
                    if (results.size() != 0) {
                        HeraldNewsItemFormat temp = results.get(0);
                        if (temp.getTitle().compareTo(mainAttribute.get("title")) != 0) {

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
                        _temp.setOriginalTime(dateAttrib.get("datetime").substring(10, 16));
                        _temp.setUpdateTime(updateDateAttrib.get("datetime").substring(10, 16));
                        _temp.setImageSavedLoc(directory + "/" + postIDTemp + ".jpeg");
                        noOfArticlesDownloadedByService++;
                        Log.e("TAG", "New Articles downloaded");

                        database.commitTransaction();
                    }
                }
            }
            database.close();

        } catch (IOException e) {
        }
        String message = "Downloaded and updated";
        if (noOfArticlesDownloadedByService != 0 || noOfArticlesUpdatedByService != 0) {
            if (noOfArticlesUpdatedByService != 0 && noOfArticlesDownloadedByService != 0)
                message = noOfArticlesDownloadedByService + " articles downloaded and " +
                        noOfArticlesUpdatedByService + " updated";
            else if (noOfArticlesDownloadedByService == 0)
                message = noOfArticlesUpdatedByService + " articles updated";
            else if (noOfArticlesUpdatedByService == 0) {
                message = noOfArticlesDownloadedByService + " articles downloaded";
            }
            Intent openHerald = new Intent(this, HomeActivity.class);

            PendingIntent articlesDnPI = PendingIntent.getActivity(this,
                    69, openHerald, PendingIntent.FLAG_CANCEL_CURRENT);

            NotificationCompat.Builder downloadNotif =
                    new NotificationCompat.Builder(this).setAutoCancel(true)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("Herald Update")
                            .setContentText(message);

            downloadNotif.setContentIntent(articlesDnPI);
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(69, downloadNotif.build());
        }
        stopSelf();
    }


}
