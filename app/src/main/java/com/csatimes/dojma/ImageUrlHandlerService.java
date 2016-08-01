package com.csatimes.dojma;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ImageUrlHandlerService extends IntentService {

    public static final String IMAGE_SERVICE_SUCCESS = "com.csatimes.dojma.intent.action.iuhs" +
            ".success";
    public static ImageUrlHandlerService instance = null;
    int updates = 0;

    public ImageUrlHandlerService() {
        super("ImageUrlHandlerService");
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
        instance = null;
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Realm database;
        RealmConfiguration realmConfiguration;
        realmConfiguration = new RealmConfiguration.Builder(ImageUrlHandlerService.this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();

        String address = "http://csatimes.co.in/dojma/";
        String urlPrefix = "http://csatimes.co.in/dojma/page/";
        SharedPreferences preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        int pages = preferences.getInt("HERALD_PAGES", 11);
        for (int i = 1; i <= pages; i++)
            try {
                Document document;
                if (i != 1)
                    document = Jsoup.connect(urlPrefix + i).get();
                else document = Jsoup.connect(address).get();
                if (document != null) {
                    Log.e("ImageUrlService", "imagehandler service downloading " + document.location());
                    Elements elements = document.getElementsByTag("article");
                    for (final Element element : elements) {
                        final String postID = element.attributes().get("id").substring(5);
                        database.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                if (realm.where(HeraldNewsItemFormat.class).equalTo("postID", postID).findAll().size() != 0) {

                                    HeraldNewsItemFormat temp = realm.where(HeraldNewsItemFormat.class).equalTo("postID", postID).findFirst();
                                    try {
                                        temp.setImageURL(element.child(0).child(0).child(0).attributes().get
                                                ("src"));
                                        updates++;
                                    } catch (Exception e) {
                                        temp.setImageURL("false");
                                    }
                                }
                            }
                        });
                    }
                }
            } catch (IOException e) {
                Log.e("ImageUrlService", "EXCEPTION");
            }
        if (updates != 0) {
            Intent intent1 = new Intent();
            intent1.setAction(IMAGE_SERVICE_SUCCESS);
            sendBroadcast(intent1);
        }
        Log.e("ImageUrlService", "closing database");
        database.close();
        Log.e("ImageUrlService", "stopping self");
        stopSelf();


    }

}
