package com.csatimes.dojma;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class UpdateCheckerService extends IntentService {

    public static final String DOWNLOAD_SUCCESS_ACTION = "com.csatimes.dojma.intent.action.dns";
    public static final String UPDATE_CHECK_OVER = "com.csatimes.dojma.intent.action.uco";
    public static UpdateCheckerService instance;
    public static volatile boolean stop = false;
    private boolean isUpdatePresent;
    private int noOfArticlesUpdatedByService = 0;
    private int noOfArticlesDownloadedByService = 0;
    private RealmConfiguration realmConfiguration;
    private Realm database;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
        noOfArticlesDownloadedByService = 0;
        noOfArticlesUpdatedByService = 0;
        isUpdatePresent = false;
        stop = false;

        sharedPreferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        try {
            WakefulBroadcastReceiver.completeWakefulIntent(intent);
        } catch (Exception ignore) {
        }

        String address = "http://csatimes.co.in/dojma/?json=all";
        String urlPrefix = "http://csatimes.co.in/dojma/page/";
        String urlSuffix = "/?json=all";

        int pages = 0;

        realmConfiguration = new RealmConfiguration.Builder(UpdateCheckerService.this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();

        for (int j = 1; j <= sharedPreferences.getInt("HERALD_PAGES", 11); j++) {
            try {
                URL url;
                if (j != 1) url = new URL(urlPrefix + j + urlSuffix);
                else
                    url = new URL(address);
                Log.e("TAG", url.toString());
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                StringBuilder sb = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                }
                String response = sb.toString();
                in.close();


                if (response != null) {

                    JSONObject page = new JSONObject(response);
                    JSONArray posts = page.getJSONArray("posts");

                    pages = page.getInt("pages");
                    editor.putInt("HERALD_PAGES", pages);
                    editor.apply();
                    if (page.getInt("count") != 16)
                        stop = true;
                    else Log.e("TAG", "count is " + page.getInt("count"));

                    for (int i = 0; i < posts.length(); i++) {
                        JSONObject postss = posts.getJSONObject(i);
                        final JSONObject post = postss;
                        database.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                try {
                                    if (database.where(HeraldNewsItemFormat.class).equalTo
                                            ("postID", post.getInt("id") + "").findAll().size()
                                            != 0) {
                                        HeraldNewsItemFormat entry = realm.where
                                                (HeraldNewsItemFormat.class).equalTo("postID",
                                                post.getInt("id") + "").findFirst();
                                        entry.setType(post.getString("type"));
                                        entry.setSlug(post.getString("slug"));
                                        entry.setUrl(post.getString("url"));
                                        entry.setStatus(post.getString("status"));
                                        entry.setTitle(post.getString("title"));
                                        entry.setTitle_plain(post.getString("title_plain"));
                                        entry.setContent(post.getString("content"));
                                        entry.setExcerpt(post.getString("excerpt"));
                                        entry.setOriginalDate(post.getString("date").substring(0, 10));
                                        entry.setUpdateDate(post.getString("date").substring(0, 10));
                                        entry.setOriginalTime(post.getString("date").substring(11));
                                        entry.setUpdateTime(post.getString("date").substring(11));
                                        entry.setAuthorName(post.getJSONObject("author").getString("name"));
                                        entry.setAuthorFName(post.getJSONObject("author").getString("first_name"));
                                        entry.setAuthorLName(post.getJSONObject("author").getString("last_name"));
                                        entry.setAuthorNName(post.getJSONObject("author").getString("nickname"));
                                        entry.setAuthorURL(post.getJSONObject("author").getString("url"));
                                        entry.setAuthorSlug(post.getJSONObject("author").getString("slug"));
                                        entry.setAuthorDesc(post.getJSONObject("author").getString("description"));
                                        entry.setComment_count(post.getInt("comment_count"));
                                        entry.setComment_status(post.getString("comment_status"));
                                    } else {
                                        noOfArticlesDownloadedByService++;
                                        int len = post.getJSONArray("attachments").length();
                                        HeraldNewsItemFormat entry = realm.createObject
                                                (HeraldNewsItemFormat.class);
                                        entry.setPostID(post.getInt("id") + "");
                                        entry.setType(post.getString("type"));
                                        entry.setSlug(post.getString("slug"));
                                        entry.setUrl(post.getString("url"));
                                        entry.setStatus(post.getString("status"));
                                        entry.setTitle(post.getString("title"));
                                        entry.setTitle_plain(post.getString("title_plain"));
                                        entry.setContent(post.getString("content"));
                                        entry.setExcerpt(post.getString("excerpt"));
                                        entry.setOriginalDate(post.getString("date").substring(0, 10));
                                        entry.setUpdateDate(post.getString("date").substring(0, 10));
                                        entry.setOriginalTime(post.getString("date").substring(11));
                                        entry.setUpdateTime(post.getString("date").substring(11));
                                        entry.setAuthorName(post.getJSONObject("author").getString("name"));
                                        entry.setAuthorFName(post.getJSONObject("author").getString("first_name"));
                                        entry.setAuthorLName(post.getJSONObject("author").getString("last_name"));
                                        entry.setAuthorNName(post.getJSONObject("author").getString("nickname"));
                                        entry.setAuthorURL(post.getJSONObject("author").getString("url"));
                                        entry.setAuthorSlug(post.getJSONObject("author").getString("slug"));
                                        entry.setAuthorDesc(post.getJSONObject("author").getString("description"));
                                        entry.setComment_count(post.getInt("comment_count"));
                                        entry.setComment_status(post.getString("comment_status"));
                                        if (len != 0)
                                            entry.setImageURL(post.getJSONArray("attachments").getJSONObject(len - 1).getString("url"));
                                        else entry.setImageURL("");

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });

                    }


                } else stop = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String message = null;

        if (noOfArticlesDownloadedByService != 0) {

            //Send update available broadcast if Herald fragment is attached
            Intent i = new Intent();
            i.setAction(DOWNLOAD_SUCCESS_ACTION);
            sendBroadcast(i);

            if (noOfArticlesDownloadedByService != 1)
                message = "1 new article was downlaoded";
            else message = noOfArticlesDownloadedByService + " articles downloaded";
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

        } else {

            Intent i = new Intent();
            i.setAction(UPDATE_CHECK_OVER);
            sendBroadcast(i);
        }
        database.close();
        stopSelf();
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
