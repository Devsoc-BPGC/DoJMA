package com.csatimes.dojma;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

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
    private TextView downloadStatusTV;
    private String url = "http://csatimes.co.in/dojma/?json=all";
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdownloader);

        preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();


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
        editor.putBoolean(DHC.SORT_SUFFIX + getResources().getStringArray(R.array
                .sort_options)[1], true);
        editor.apply();


        downloadStatusTV = (TextView) findViewById(R.id.json_download_status);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        downloadStatusTV.setText("Received response from server");
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
                            if (post.getJSONArray("attachments").length() != 0)
                                entry.setImageURL(post.getJSONArray("attachments").getJSONObject(post.getJSONArray("attachments").length() - 1).getString("url"));
                            else
                                entry.setImageURL("");
                            database.commitTransaction();
                        }
                        database.close();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            downloadStatusTV.setText("There are 10 more pages left");
                            Log.e("TAG", "starting async");
                            downloadOthers(noOfPages);
                        }
                    });
                } catch (JSONException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            downloadStatusTV.setText("Response from server is not a JSON string");
                        }
                    });
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                downloadStatusTV.setText("pehle step pe hi fail hogaya chake");

            }
        });

        downloadStatusTV.setText("Starting download process");
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
            downloadStatusTV.setText("DONE");
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder
                    (POSTDownloaderActivity.this)
                    .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
            Realm.setDefaultConfiguration(realmConfiguration);
            Realm database = Realm.getDefaultInstance();
            if (database.where(HeraldNewsItemFormat.class).findAll().size() != 0) {
                Log.e("TAG", "starting home activity");
                Intent intent = new Intent(POSTDownloaderActivity.this, HomeActivity.class);
                startActivity(intent);
                database.close();
                editor.putBoolean(getString(R.string.SP_first_install), false);
                editor.apply();
                finish();
            } else {
                downloadStatusTV.setText("Failed to download even a single article. Please try " +
                        "again later");
                database.delete(HeraldNewsItemFormat.class);
                database.close();

            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            downloadStatusTV.setText("There are " + values[0] + " more pages left");
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
                    publishProgress(10 - j + 2);
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
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            });

                        }

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

