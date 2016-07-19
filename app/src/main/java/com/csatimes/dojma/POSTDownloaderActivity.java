package com.csatimes.dojma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;

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

public class POSTDownloaderActivity extends AppCompatActivity {
    private String url = "http://csatimes.co.in/dojma/?json=all";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Window window;
    private CircularFillableLoaders circularFillableLoaders;
    private int progress = 0;
    private int initProgress;
    private SimpleDraweeView simpleDraweeView;
    private String[] images = {"https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/1.jpg", "https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/2.jpg", "https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/3.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        setContentView(R.layout.activity_postdownloader);
        circularFillableLoaders = (CircularFillableLoaders) findViewById(R.id.loading_image);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.loading_dojma);

        simpleDraweeView.setImageURI(Uri.parse(images[0]));


        preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();


        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.navigationBarColor));
        }


        circularFillableLoaders.setProgress(progress);
        //Set up filter options in sharedprefs
        for (int i = 0; i < getResources().getStringArray(R.array.filter_options).length; i++) {
            editor.putBoolean(DHC.FILTER_SUFFIX + getResources().getStringArray(R.array
                    .filter_options)[i], false);
        }
        //Set up sort options in sharedprefs
        for (int i = 0; i < getResources().getStringArray(R.array.sort_options).length; i++) {
            editor.putBoolean(DHC.SORT_SUFFIX + getResources().getStringArray(R.array
                    .sort_options)[i], false);
        }
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        editor.putBoolean(DHC.SORT_SUFFIX + getResources().getStringArray(R.array
                .sort_options)[1], true);
        editor.apply();

        //Gave 5% progress to setting shared preference randomly
        initProgress = 5;
        progress = initProgress;
        circularFillableLoaders.setProgress(progress);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });

                try {
                    JSONObject page = new JSONObject(response);
                    final int noOfPages = page.getInt("pages");
                    JSONArray posts = page.getJSONArray("posts");
                    if (posts != null) {

                        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                                (POSTDownloaderActivity.this)
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
                            entry.setOriginalMonthYear(post.getString("date").substring(0,7));
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
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            circularFillableLoaders.setProgress(progress + (100 - initProgress)
                                    / noOfPages);
                            progress += (100 - initProgress)
                                    / noOfPages;
                            downloadOthers(noOfPages);
                        }
                    });
                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Snackbar snackbar = Snackbar.make(circularFillableLoaders, "Response " +
                                    "from server is not a JSON string", Snackbar.LENGTH_LONG);
                            snackbar.getView().setBackgroundColor(ContextCompat.getColor
                                    (POSTDownloaderActivity.this, R.color.colorPrimary));
                            snackbar.show();

                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar snackbar = Snackbar.make(circularFillableLoaders, "Hmm...bad thigs have happened. Try again later", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor
                        (POSTDownloaderActivity.this, R.color.colorPrimary));
                snackbar.show();
            }
        });

        queue.add(request);

    }

    private void downloadOthers(int pages) {
        new DownloadTask().execute(pages);
    }

    public class DownloadTask extends AsyncTask<Integer, Integer, Void> {
        public DownloadTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                    (POSTDownloaderActivity.this)
                    .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            Realm database = Realm.getDefaultInstance();
            if (database.where(HeraldNewsItemFormat.class).findAll().size() != 0) {
                //Since image urls are linking to HD images, we need to start the Image handler
                // service to get small sized image urls
                circularFillableLoaders.setProgress(100);
                startService(new Intent(POSTDownloaderActivity.this, ImageUrlHandlerService.class));
                Log.e("TAG", "starting home activity");
                Intent intent = new Intent(POSTDownloaderActivity.this, HomeActivity.class);
                startActivity(intent);
                database.close();
                editor.putBoolean(getString(R.string.SP_first_install), false);
                editor.apply();
                finish();
            } else {
                Snackbar snackbar = Snackbar.make(circularFillableLoaders, "Failed to download even a single article. Please try " +
                        "again later", Snackbar.LENGTH_LONG);
                snackbar.getView().setBackgroundColor(ContextCompat.getColor
                        (POSTDownloaderActivity.this, R.color.colorPrimary));
                snackbar.show();
                database.delete(HeraldNewsItemFormat.class);
                database.close();

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            circularFillableLoaders.setProgress(values[1]);
        }

        @Override
        protected void onCancelled(Void aVoid) {
            super.onCancelled(aVoid);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            String url1 = "http://csatimes.co.in/dojma/page/";
            String url2 = "/?json=all";
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                    (POSTDownloaderActivity.this)
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
                                        entry.setOriginalMonthYear(post.getString("date").substring(0,7));
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

                        publishProgress(10 - j + 2, (progress + (100 - initProgress) / integers[0]));
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

