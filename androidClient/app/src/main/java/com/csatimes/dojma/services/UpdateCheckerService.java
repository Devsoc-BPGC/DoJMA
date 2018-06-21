package com.csatimes.dojma.services;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.MainActivity;
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

import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_ACTION_NO_SUCCESS;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_INTENT_ENABLE_NOTIFICATION;
import static com.csatimes.dojma.utilities.DHC.UPDATE_SERVICE_INTENT_PAGES;
import static com.csatimes.dojma.utilities.DHC.log;

public class UpdateCheckerService extends IntentService {

    public static final String TAG = UpdateCheckerService.class.getSimpleName();

    @Nullable
    private static UpdateCheckerService instance;
    /**
     * This variable should be used only during debug stages
     * Set to false if you want to enable article downloading, otherwise true
     */
    boolean enableDownloadingInDebugMode = true;

    /**
     * To disable showing a notification when calling this service, pass a value
     * of {@code false} in the intent
     */
    boolean enableNotifications;

    /**
     * To only download 'n' number of pages override this value by adding the
     * required data in the intent
     * {@link DHC#UPDATE_SERVICE_INTENT_PAGES}<br>
     * Default value is -1
     */
    int defaultPagesToDownload;

    private int mDownloads;

    /**
     * No arguments constructor
     */
    public UpdateCheckerService() {
        super("UpdateCheckerService");
    }

    /**
     * Check if UpdateCheckerService is running or not
     *
     * @return true if running otherwise false
     */
    public static boolean isInstanceCreated() {
        return instance != null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    @Override
    protected void onHandleIntent(final Intent intent) {
        if (enableDownloadingInDebugMode) {
            final SharedPreferences preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
            final SharedPreferences.Editor editor = preferences.edit();

            final String urlPrefix = DHC.UPDATE_SERVICE_DOJMA_JSON_ADDRESS_PREFIX;
            final String urlSuffix = DHC.UPDATE_SERVICE_DOJMA_JSON_ADDRESS_SUFFIX;

            final Realm database = Realm.getDefaultInstance();

            final Bundle extras = intent.getExtras();
            if (extras != null) {
                defaultPagesToDownload = extras.getInt(UPDATE_SERVICE_INTENT_PAGES, -1);
                enableNotifications = extras.getBoolean(UPDATE_SERVICE_INTENT_ENABLE_NOTIFICATION, true);
            } else {
                defaultPagesToDownload = -1;
                enableNotifications = true;
            }

            final int defaultPages = defaultPagesToDownload == -1
                    ? preferences.getInt(DHC.UPDATE_SERVICE_HERALD_PAGES,
                    DHC.UPDATE_SERVICE_HERALD_DEFAULT_PAGES) : defaultPagesToDownload - 1;

            for (int j = 0; j <= defaultPages; j++) {
                try {
                    //Handle malformed URL exception
                    final URL url = new URL(urlPrefix + j + urlSuffix);
                    DHC.e(TAG,url.toString());

                    // Read all the text returned by the server
                    final String response = getServerResponse(url);

                    final JSONObject jsonResponse;
                    final JSONArray posts;

                    //Handle parse error using catch
                    jsonResponse = new JSONObject(response);

                    //Get all posts from the json
                    posts = jsonResponse.getJSONArray("posts");
                    //Update total pages
                    final int pages = jsonResponse.getInt("pages");
                    editor.putInt(DHC.UPDATE_SERVICE_HERALD_PAGES, pages);

                    for (int i = 0; i < posts.length(); i++) {
                        final JSONObject post = posts.getJSONObject(i);
                        final HeraldItem foobar = database.where(HeraldItem.class).equalTo("postID", post.getInt("id") + "").findFirst();

                        if ((foobar == null) || (foobar.getUpdateTime().compareTo(post.getString("modified").substring(11)) != 0)) {
                            database.executeTransaction(new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    try {
                                        HeraldItem entry;
                                        if (foobar == null) entry = realm.createObject
                                                (HeraldItem.class, post.getInt("id") + "");
                                        else
                                            entry = realm.where(HeraldItem.class).equalTo("postID", post.getInt("id") + "").findFirst();

                                        entry.setUrl(post.getString("url"));
                                        entry.setTitle(post.getString("title"));
                                        entry.setTitle_plain(post.getString("title_plain"));
                                        entry.setContent(post.getString("content"));
                                        entry.setExcerpt(post.getString("excerpt"));
                                        entry.setOriginalDate(post.getString("date").substring(0, 10));
                                        entry.setOriginalTime(post.getString("date").substring(11));

                                        try {
                                            String imageUrl = post.getJSONObject("thumbnail_images").getJSONObject
                                                        ("full").getString("url");
                                            if (imageUrl == null)
                                                imageUrl = post.getJSONObject("attachments").getJSONObject("images").getJSONObject("large").getString("url");
                                            entry.setThumbnailUrl(imageUrl);
                                        } catch (Exception e) {
                                            entry.setThumbnailUrl("");
                                            DHC.e(TAG, "Could not get thumbnail");
                                        }


                                        try {
                                            entry.setUpdateDate(post.getString("modified").substring(0, 10));
                                            entry.setUpdateTime(post.getString("modified").substring(11));
                                        } catch (Exception e) {
                                            DHC.e(TAG, "Modified date does not exist yet");
                                            entry.setUpdateDate(entry.getOriginalDate());
                                            entry.setUpdateTime(entry.getOriginalTime());
                                        }
                                        //Save category information
                                        if (post.getJSONArray("categories").length() != 0) {

                                            entry.setCategory(post.getJSONArray("categories").getJSONObject
                                                    (0).getString("title"));
                                        } else {
                                            entry.setCategory("Others");
                                        }

                                        if (foobar == null)
                                            mDownloads++;
                                    } catch (Exception e) {
                                        DHC.e(TAG, "wrong json format " + e.getMessage());
                                    }
                                }
                            });
                        }
                    }
                } catch (JSONException | IOException e) {
                    Log.e(TAG, "json parse error " + e.getMessage());
                }

            }

            //Apply changes if any
            editor.apply();

            DHC.log("total herald items " + database.where(HeraldItem.class).findAll().size());

            if (mDownloads > 0) {

                //Send update available broadcast if Herald fragment is attached
                Intent i = new Intent();
                i.setAction(UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS);
                database.close();
                sendBroadcast(i);

                if (enableNotifications) {
                    String message = generateMessage(mDownloads);
                    Intent openHerald = new Intent(this, MainActivity.class);
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
                                    .setContentTitle("DoJMA articles updated!")
                                    .setContentText(message);

                    downloadNotif.setContentIntent(pendingIntent);

                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(DHC.UPDATE_SERVICE_NOTIFICATION_CODE, downloadNotif.build());
                }
            } else {
                Intent i = new Intent();
                i.setAction(UPDATE_SERVICE_ACTION_NO_SUCCESS);
                database.close();
                sendBroadcast(i);
            }

        }
        stopSelf();
    }

    private String generateMessage(int mDownloads) {
        if (mDownloads == 1) return "Downloaded a new article";
        return "Downloaded " + mDownloads + " articles";
    }

    @NonNull
    private String getServerResponse(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }
        final String response = sb.toString();
        in.close();
        return response;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        instance = null;
    }
}
