package com.csatimes.dojma.utilities;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v13.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.BuildConfig;
import com.csatimes.dojma.models.GazetteItem;

import java.io.File;

/**
 * Dojma Helper Class
 */
public class DHC {

    public static final String USER_PREFERENCES = "USER_PREFS";

    public static final String USER_PREFERENCES_NAVBAR_TITLE = "USER_PREFS_NAVBAR_TITLE";
    public static final String USER_PREFERENCES_NAVBAR_IMAGE_URL = "USER_PREFS_NAVBAR_IMAGE_URL";

    public static final String USER_PREFERENCES_MISC_CARD_MESSAGE = "USER_PREFS_NAVBAR_IMAGE_URL";

    public static final String DoJMA_FACEBOOK_URL = "https://www.facebook.com/DoJMABITSGoa";
    public static final String DoJMA_FACEBOOK_PAGE_ID = "DoJMABITSGoa";
    public static final String BITS_GOA_LCD_LINK = "http://cc.bits-goa.ac.in/enotice/Lcd.php";
    public static final String REALM_DOJMA_DATABASE = "DOJMA_DATABASE";

    public static final String UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS = "com.csatimes.dojma.services.updatecheckerservice.action.dns";
    public static final String UPDATE_SERVICE_ACTION_NO_SUCCESS = "com.csatimes.dojma.services.updatecheckerservice.action.ns";
    public static final String UPDATE_SERVICE_INTENT_PAGES = "com.csatimes.dojma.services.updatecheckerservice.extra.int.pages";
    public static final String UPDATE_SERVICE_INTENT_ENABLE_NOTIFICATION = "com.csatimes.dojma.services.updatecheckerservice.extra.boolean.notifications";
    public static final String UPDATE_SERVICE_DOJMA_JSON_ADDRESS_PREFIX = "http://bitsherald.org/blog/page/";
    public static final String UPDATE_SERVICE_DOJMA_JSON_ADDRESS_SUFFIX = "/?json=all";
    public static final String UPDATE_SERVICE_HERALD_PAGES = "HERALD_PAGES";

    public static final String FIREBASE_DATABASE_REFERENCE_GAZETTES = "gazettes2";
    public static final String FIREBASE_DATABASE_REFERENCE_EVENTS = "events2";
    public static final String FIREBASE_DATABASE_REFERENCE_CONTACTS = "contacts";
    public static final String FIREBASE_DATABASE_REFERENCE_LINKS = "links";
    public static final String FIREBASE_DATABASE_REFERENCE_MESS = "mess";
    public static final String FIREBASE_DATABASE_REFERENCE_NAVBAR = "navbar";
    public static final String FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE = "title";
    public static final String FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL = "image";
    public static final String FIREBASE_DATABASE_REFERENCE_POSTERS = "posters";
    public static final String FIREBASE_DATABASE_REFERENCE_MISC_CARD = "miscCard";
    public static final String FIREBASE_DATABASE_REFERENCE_TAXI = "taxi";

    public static final int REQUEST_WRITE_PERMISSION = 400;

    public static final int UPDATE_SERVICE_PENDING_INTENT_CODE = 243;
    public static final int UPDATE_SERVICE_NOTIFICATION_CODE = 42;
    public static final int UPDATE_SERVICE_HERALD_DEFAULT_PAGES = 23;

    public static final int SEARCH_ITEM_TYPE_TITLE = 0;
    public static final int SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE = 1;
    public static final int SEARCH_ITEM_TYPE_HERALD_ARTICLE = 2;
    public static final int SEARCH_ITEM_TYPE_GAZETTE = 3;
    public static final int SEARCH_ITEM_TYPE_EVENT = 4;
    public static final int SEARCH_ITEM_TYPE_CONTACT = 5;
    public static final int SEARCH_ITEM_TYPE_LINK = 6;
    public static final int SEARCH_ITEM_TYPE_MESS = 7;
    public static final int SEARCH_ITEM_TYPE_POSTER = 8;

    public static final int CONTACT_ITEM_TYPE_TITLE = 9;
    public static final int CONTACT_ITEM_TYPE_CONTACT = 10;

    public static final int UTILITIES_ITEM_TYPE_CONTACTS = 0;
    public static final int UTILITIES_ITEM_TYPE_CONTACTS_TAXI = 1;
    public static final int UTILITIES_ITEM_TYPE_MESS = 2;
    public static final int UTILITIES_ITEM_TYPE_LINKS = 3;
    public static final int UTILITIES_ITEM_TYPE_MISC = 4;
    public static final int UTILITIES_ITEM_TYPE_MAP = 5;

    public static final int MAIN_ACTIVITY_HERALD_POS = 0;
    public static final int MAIN_ACTIVITY_GAZETTES_POS = 1;
    public static final int MAIN_ACTIVITY_EVENTS_POS = 2;
    public static final int MAIN_ACTIVITY_UTILITIES_POS = 3;

    public static void log(String message) {
        Log.e("com.csatimes.dojma", message);
    }

    public static void log(String tag, String message) {
        Log.e("com.csatimes.dojma." + tag, message);
    }

    public static Snackbar makeCustomSnackbar(View view, String s, int bgColor, int textColor) {
        Snackbar snackbar = Snackbar.make(view, s, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(bgColor);
        TextView snackbarText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarText.setTextColor(textColor);
        return snackbar;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }


    public static void getGazette(Activity context, GazetteItem gi) {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_WRITE_PERMISSION);

            }
        } else {


            File pdf = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), gi.getTitle() + " " + gi.getReleaseDateFormatted() + ".pdf");
            if (pdf.exists()) {
                Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", pdf);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(uri);
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                if (intent.resolveActivity(context.getPackageManager()) != null)
                    context.startActivity(intent);
                else {
                    Toast.makeText(context, "Could not load from local storage, Downloading again", Toast.LENGTH_SHORT).show();
                    downloadPDF(context, gi);
                }
            } else {
                downloadPDF(context, gi);
            }
        }
    }

    private static void downloadPDF(Context context, GazetteItem gi) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(
                new DownloadManager.Request(Uri.parse(gi.getUrl()))
                        .setTitle(gi.getReleaseDateFormatted())
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, gi.getTitle() + " " + gi.getReleaseDateFormatted() + ".pdf")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setMimeType("application/pdf"));
        Toast.makeText(context, "Check notifications for download progress", Toast.LENGTH_SHORT).show();

    }
}
