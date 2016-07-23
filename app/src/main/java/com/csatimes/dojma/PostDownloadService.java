package com.csatimes.dojma;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class PostDownloadService extends IntentService {
    static final String UPDATE_PROGRESS = "com.csatimes.dojma.action.UPDATE_PROGRESS";
    static final String ZERO_ARTICLES_DOWNLOADED = "com.csatimes.dojma.action.ZERO_ARTICLES_DOWNLOADED";
    static final String SUCCESS = "com.csatimes.dojma.action.SUCCESS";

    public static PostDownloadService instance = null;

    private final String urlString = "http://csatimes.co.in/dojma/?json=all";

    private int progress = 5;
    private int initProgress;

    public PostDownloadService() {
        super("PostDownloadService");
    }

    public static boolean isInstanceCreated() {
        return instance != null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        try {
            URL url = new URL(urlString);
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            StringBuilder sb = new StringBuilder();
            while ((str = in.readLine()) != null) {
                sb.append(str);
            }
            String response = sb.toString();
            in.close();

            JSONObject page = new JSONObject(response);
            final int noOfPages = page.getInt("pages");
            JSONArray posts = page.getJSONArray("posts");
            if (posts != null) {

                RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                        (PostDownloadService.this)
                        .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
                Realm.setDefaultConfiguration(realmConfiguration);
                Realm database = Realm.getDefaultInstance();

                for (int i = 0; i < posts.length(); i++) {
                    JSONObject post = posts.getJSONObject(i);

                    database.beginTransaction();
                    HeraldNewsItemFormat entry = database.createObject
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
                    entry.setOriginalMonthYear(post.getString("date").substring(0, 7));
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
                    //Save category information
                    if (post.getJSONArray("categories").length() != 0) {
                        try {
                            entry.setCategoryID(post.getJSONArray("categories").getJSONObject
                                    (0).getInt("id") + "");
                            entry.setCategoryTitle(post.getJSONArray("categories").getJSONObject
                                    (0).getString("title"));
                            entry.setCategorySlug(post.getJSONArray("categories").getJSONObject
                                    (0).getString("slug"));
                            entry.setCategoryDescription(post.getJSONArray("categories").getJSONObject
                                    (0).getString("description"));
                            entry.setCategoryCount(post.getJSONArray("categories").getJSONObject
                                    (0).getInt("post_count"));
                        } catch (Exception e) {
                            entry.setCategoryID("");
                            entry.setCategoryCount(0);
                            entry.setCategoryDescription("");
                            entry.setCategorySlug("");
                            entry.setCategoryTitle("");
                        }
                    } else {
                        entry.setCategoryID("");
                        entry.setCategoryCount(0);
                        entry.setCategoryDescription("");
                        entry.setCategorySlug("");
                        entry.setCategoryTitle("");
                    }
                    //Save image information
                    if (post.getJSONArray("attachments").length() != 0) {
                        entry.setImageURL(post.getJSONArray("attachments").getJSONObject(post.getJSONArray("attachments").length() - 1).getString("url"));
                        entry.setBigImageUrl(post.getJSONArray("attachments").getJSONObject
                                (post.getJSONArray("attachments").length() - 1).getString("url"));
                    } else {
                        entry.setImageURL("false");
                        entry.setBigImageUrl("false");
                    }
                    database.commitTransaction();
                }
                database.close();
            } else {

            }

            progress += (100 - initProgress) / noOfPages;
            Intent i = new Intent();
            i.setAction(UPDATE_PROGRESS);
            i.putExtra(UPDATE_PROGRESS, progress);
            sendBroadcast(i);
            // downloadOthers(noOfPages);
        } catch (Exception e) {
            // downloadOthers(11);
        }
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                (PostDownloadService.this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        Realm database = Realm.getDefaultInstance();
        if (database.where(HeraldNewsItemFormat.class).findAll().size() != 0) {
            Intent i = new Intent();
            i.setAction(SUCCESS);
            sendBroadcast(i);
        } else {
            Intent i = new Intent();
            i.setAction(ZERO_ARTICLES_DOWNLOADED);
            sendBroadcast(i);
            database.delete(HeraldNewsItemFormat.class);
        }
        database.close();
        stopSelf();
    }

    private void downloadOthers(int pages) {
        new DownloadTask().execute(pages);
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

    public class DownloadTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                    (PostDownloadService.this)
                    .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            Realm database = Realm.getDefaultInstance();
            if (database.where(HeraldNewsItemFormat.class).findAll().size() != 0) {

                //Since image urls are linking to HD images, we need to start the Image handler
                // service to get small sized image urls

                //startService(new Intent(PostDownloadService.this, ImageUrlHandlerService.class));

                Intent i = new Intent();
                i.setAction(SUCCESS);
                sendBroadcast(i);

                database.close();

            } else {
                Intent intent = new Intent();
                intent.setAction(ZERO_ARTICLES_DOWNLOADED);
                sendBroadcast(intent);

                database.delete(HeraldNewsItemFormat.class);
                database.close();

            }
            stopSelf();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            // circularFillableLoaders.setProgress(values[1]);
            Intent intent = new Intent();
            intent.setAction(UPDATE_PROGRESS);
            intent.putExtra(UPDATE_PROGRESS, values[1]);
            sendBroadcast(intent);
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            String url1 = "http://csatimes.co.in/dojma/page/";
            String url2 = "/?json=all";
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                    (PostDownloadService.this)
                    .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            Realm database = Realm.getDefaultInstance();
            for (int j = 2; j <= integers[0]; j++) {
                try {
                    URL url = new URL(url1 + j + url2);
                    // Read all the text returned by the server
                    BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                    String str;
                    StringBuilder sb = new StringBuilder();
                    while ((str = in.readLine()) != null) {
                        sb.append(str);
                    }
                    String response = sb.toString();
                    in.close();

                    JSONObject page = new JSONObject(response);
                    JSONArray posts = page.getJSONArray("posts");
                    if (posts != null) {

                        for (int i = 0; i < posts.length(); i++) {
                            JSONObject postss = posts.getJSONObject(i);
                            final JSONObject post = postss;
                            database.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    try {
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
                                        entry.setOriginalMonthYear(post.getString("date").substring(0, 7));
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
                                        //Save category information
                                        if (post.getJSONArray("categories").length() != 0) {
                                            try {
                                                entry.setCategoryID(post.getJSONArray("categories").getJSONObject
                                                        (0).getInt("id") + "");
                                                entry.setCategoryTitle(post.getJSONArray("categories").getJSONObject
                                                        (0).getString("title"));
                                                entry.setCategorySlug(post.getJSONArray("categories").getJSONObject
                                                        (0).getString("slug"));
                                                entry.setCategoryDescription(post.getJSONArray("categories").getJSONObject
                                                        (0).getString("description"));
                                                entry.setCategoryCount(post.getJSONArray("categories").getJSONObject
                                                        (0).getInt("post_count"));
                                            } catch (Exception e) {
                                                Log.e("TAG", "Exception raised in category for post " + post
                                                        .getInt("id") + "");
                                                entry.setCategoryID("");
                                                entry.setCategoryCount(0);
                                                entry.setCategoryDescription("");
                                                entry.setCategorySlug("");
                                                entry.setCategoryTitle("");
                                            }
                                        } else {
                                            entry.setCategoryID("");
                                            entry.setCategoryCount(0);
                                            entry.setCategoryDescription("");
                                            entry.setCategorySlug("");
                                            entry.setCategoryTitle("");
                                        }
                                        if (len != 0)
                                            entry.setImageURL(post.getJSONArray("attachments").getJSONObject(len - 1).getString("url"));
                                        else entry.setImageURL("");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }

                        publishProgress(10 - j + 2, (progress + (100 - initProgress) /
                                integers[0]));
                        progress += (100 - initProgress) / integers[0];
                    }
                } catch (MalformedURLException e) {
                } catch (IOException e) {
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            database.close();
            return null;
        }
    }
}
