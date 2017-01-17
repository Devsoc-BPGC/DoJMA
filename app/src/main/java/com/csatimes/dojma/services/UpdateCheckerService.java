package com.csatimes.dojma.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.HomeActivity;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.utilities.DHC;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import io.realm.Realm;

import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_DOWNLOAD_SUCCESS;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_NO_SUCCESS;


public class UpdateCheckerService extends IntentService {

    public static UpdateCheckerService instance;
    /**
     * This variable should be used only during debug stages
     * Set to false if you want to enable article downloading, otherwise true
     */
    boolean enableDownloadingInDebugMode = true;
    private int mDownloads;

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
        mDownloads = 0;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (enableDownloadingInDebugMode) {
            SharedPreferences sp = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
            final SharedPreferences.Editor editor = sp.edit();

            String urlPrefix = DHC.UPDATE_SERVICE_DOJMA_JSON_ADDRESS_PREFIX;
            String urlSuffix = DHC.UPDATE_SERVICE_DOJMA_JSON_ADDRESS_SUFFIX;

            Realm database = Realm.getDefaultInstance();

            for (int j = 0; j <= sp.getInt(DHC.UPDATE_SERVICE_HERALD_PAGES, DHC.UPDATE_SERVICE_HERALD_DEFAULT_PAGES); j++) {
                try {
                    //Handle malformed URL exception
                    URL url = new URL(urlPrefix + j + urlSuffix);

                    DHC.log(url.toString() + " at csatimes");
                    // Read all the text returned by the server
                    String response = getServerResponse(url);

                    JSONObject jsonResponse;
                    JSONArray posts;

                    //Handle parse error
                    jsonResponse = new JSONObject(response);

                    posts = jsonResponse.getJSONArray("posts");

                    //Update total pages
                    int pages = jsonResponse.getInt("pages");
                    editor.putInt(DHC.UPDATE_SERVICE_HERALD_PAGES, pages);

                    for (int i = 0; i < posts.length(); i++) {
                        final JSONObject post = posts.getJSONObject(i);

                        if (database.where(HeraldItem.class).equalTo
                                ("postID", post.getInt("id") + "").findFirst() != null) {

                            database.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    try {
                                        HeraldItem entry = realm.where(HeraldItem.class).equalTo("postID", post.getInt("id") + "").findFirst();

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
                                        }
                                        //Save image information
                                        if (post.getJSONArray("attachments").length() != 0) {
                                            entry.setImageURL(post.getJSONArray("attachments").getJSONObject(post.getJSONArray("attachments").length() - 1).getString("url"));
                                            entry.setBigImageUrl(post.getJSONArray("attachments").getJSONObject
                                                    (post.getJSONArray("attachments").length() - 1).getString("url"));
                                        } else {
                                            entry.setImageURL("");
                                            entry.setBigImageUrl("");
                                        }
                                    } catch (JSONException e) {
                                        DHC.log("wrong json format");
                                    }
                                }
                            });
                        } else {
                            final int len = post.getJSONArray("attachments").length();
                            database.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    try {
                                        HeraldItem entry = realm.createObject
                                                (HeraldItem.class, post.getInt("id") + "");
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
                                            ;//  entry.setImageURL(post.getJSONArray("attachments").getJSONObject(len - 1).getString("url"));
                                        else entry.setImageURL("");
                                        mDownloads++;
                                    } catch (Exception e) {
                                        DHC.log("wrong json format");
                                    }
                                }
                            });
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    DHC.log("json parse error ");
                } catch (IOException e) {
                    e.printStackTrace();
                    DHC.log("IOException/MalformedURL");
                }

            }
            //Apply changes if any
            editor.apply();

            String message = generateMessage(mDownloads, 0);

            if (message != null) {

                //Send update available broadcast if Herald fragment is attached
                Intent i = new Intent();
                i.setAction(UPDATE_SERVICE_DOWNLOAD_SUCCESS);
                database.close();
                sendBroadcast(i);

                Intent openHerald = new Intent(this, HomeActivity.class);
                openHerald.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        this,
                        DHC.UPDATE_SERVICE_PENDING_INTENT_CODE,
                        openHerald,
                        PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder downloadNotif =
                        new NotificationCompat.Builder(this)
                                .setAutoCancel(true)
                                .setSmallIcon(R.drawable.ic_stat_d)
                                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.dojma))
                                .setContentTitle("DoJMA articles update ")
                                .setContentText(message);

                downloadNotif.setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(DHC.UPDATE_SERVICE_NOTIFICATION_CODE, downloadNotif.build());

            } else {
                Intent i = new Intent();
                i.setAction(UPDATE_SERVICE_NO_SUCCESS);
                database.close();
                sendBroadcast(i);
            }
            startService(new Intent(this, ImageUrlHandlerService.class));
        }
        stopSelf();
    }

    private String generateMessage(int mDownloads, int mUpdates) {
        if (mDownloads != 0 && mUpdates != 0) {
            return generateMessage(mDownloads, 0) + "and" + generateMessage(0, mUpdates);
        } else if (mDownloads != 0 && mUpdates == 0) {
            if (mDownloads == 1) return " One article downloaded ";
            else return mDownloads + " articles downloaded ";
        } else if (mUpdates != 0 && mDownloads == 0) {
            if (mUpdates == 1) return " One article updated ";
            else return mUpdates + " articles updated ";
        }
        return null;
    }

    @NonNull
    private String getServerResponse(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        String response = sb.toString();
        in.close();
        return response;
    }

}
