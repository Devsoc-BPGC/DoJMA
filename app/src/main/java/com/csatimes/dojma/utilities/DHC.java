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

import com.csatimes.dojma.R;
import com.google.android.material.snackbar.Snackbar;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.BuildConfig;
import com.csatimes.dojma.models.GazetteItem;

import java.io.File;

/**
 * Dojma Helper Class.
 */
public class DHC {

    public static final int VERSION = 2;

    /**
     * Full name of DoJMA.
     */
    public static final String DoJMA = "Department of Journalism and Media Affairs";

    /**
     * Full name of MAC.
     */
    public static final String MAC = "Mobile Applications Club";

    /**
     * Name of shared preferences file used for the app.
     */
    public static final String USER_PREFERENCES = "USER_PREFS";

    /**
     * Fully qualified package name.
     */
    public static final String PACKAGE_NAME = "com.csatimes.dojma";

    //MainActivity UI codes
    public static final String USER_PREFERENCES_NAVBAR_TITLE = "USER_PREFS_NAVBAR_TITLE";

    public static final String USER_PREFERENCES_TOOLBAR_TITLE = "USER_PREFS_TOOLBAR_TITLE";
    public static final String USER_PREFERENCES_TOOLBAR_SUBTITLE = "USER_PREFS_TOOLBAR_SUBTITLE";
    public static final String USER_PREFERENCES_TOOLBAR_IMAGE_URL = "USER_PREFS_TOOLBAR_IMAGE_URL";
    public static final String USER_PREFERENCES_MISC_CARD_MESSAGE = "USER_PREFS_MISC";

    /**
     * Facebook url link to DoJMA.
     */
    public static final String DoJMA_FACEBOOK_URL = "https://www.facebook.com/DoJMABITSGoa";

    /**
     * Facebook ID of DoJMA.
     */
    public static final String DoJMA_FACEBOOK_PAGE_ID = "DoJMABITSGoa";

    /**
     * Url of LCD news.
     */
    public static final String BITS_GOA_LCD_LINK = "http://cc.bits-goa.ac.in/enotice/Lcd.php";

    /**
     * Name of Realm database used in this app.
     */
    public static final String REALM_DOJMA_DATABASE = "DOJMA_DATABASE";

    /**
     * Intent action for download success message in {@link com.csatimes.dojma.services.UpdateCheckerService}
     */
    public static final String UPDATE_SERVICE_ACTION_DOWNLOAD_SUCCESS = PACKAGE_NAME + ".services.updatecheckerservice.action.dns";

    /**
     * Intent action for download failure message in {@link com.csatimes.dojma.services.UpdateCheckerService}
     */
    public static final String UPDATE_SERVICE_ACTION_NO_SUCCESS = PACKAGE_NAME + ".services.updatecheckerservice.action.ns";
    public static final String UPDATE_SERVICE_INTENT_PAGES = PACKAGE_NAME + ".services.updatecheckerservice.extra.int.pages";
    public static final String UPDATE_SERVICE_INTENT_ENABLE_NOTIFICATION = PACKAGE_NAME + ".services.updatecheckerservice.extra.boolean.notifications";
    public static final String UPDATE_SERVICE_DOJMA_JSON_ADDRESS_PREFIX = "http://bitsherald" +
            ".org/api/blog/?json=all&page=";
    public static final String UPDATE_SERVICE_DOJMA_JSON_ADDRESS_SUFFIX = "/";

    /**
     * Key used in {@link #USER_PREFERENCES} ("{@value #USER_PREFERENCES}") to save latest number of
     * pages.
     */
    public static final String UPDATE_SERVICE_HERALD_PAGES = "HERALD_PAGES";

    /**
     * Firebase node name for contributors.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_CONTRIBUTORS = "contributors";

    /**
     * Firebase node name for gazettes.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_GAZETTES = "gazettes2";

    /**
     * Firebase node name for events.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_EVENTS = "events2";

    /**
     * Firebase node name for contacts.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_CONTACTS = "contacts";

    /**
     * Firebase node name for links.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_LINKS = "links";

    /**
     * Firebase node name for mess.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_MESS = "mess";
    /**
     * Firebase node name for posters.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_POSTERS = "posters";

    /**
     * Firebase node name for misc. data.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_MISC_CARD = "miscCard";

    /**
     * Firebase key name for taxi.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_TAXI = "taxi";

    /**
     * Firebase node name for ui.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_UI = "ui";

    /**
     * Firebase node name for toolbar under <b>{@value #FIREBASE_DATABASE_REFERENCE_UI}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_TOOLBAR = "toolbar";

    /**
     * Firebase node name for nav bar title under <b>{@value #FIREBASE_DATABASE_REFERENCE_UI}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE = "navbarTitle";

    /**
     * Firebase node name for nav bar image under <b>{@value #FIREBASE_DATABASE_REFERENCE_UI}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL = "navbarImage";

    /**
     * Firebase node name for title under <b>{@value #FIREBASE_DATABASE_REFERENCE_TOOLBAR}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_TOOLBAR_TITLE = "title";

    /**
     * Firebase node name for subtitle under <b>{@value #FIREBASE_DATABASE_REFERENCE_TOOLBAR}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_TOOLBAR_SUBTITLE = "subtitle";

    /**
     * Firebase node name for image url under <b>{@value #FIREBASE_DATABASE_REFERENCE_TOOLBAR}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_TOOLBAR_IMAGE_URL = "imageUrl";
    public static final int REQUEST_WRITE_PERMISSION = 400;

    public static final int UPDATE_SERVICE_PENDING_INTENT_CODE = 243;

    /**
     * Current Realm database schema version.
     */
    public static final int REALM_DATABASE_SCHEMA_VERSION = 3;

    public static final String USER_PREFERENCES_NAVBAR_IMAGE_URL = "USER_PREFS_NAVBAR_IMAGE_URL";
    public static final int UPDATE_SERVICE_NOTIFICATION_CODE = 42;

    /**
     * Default no. of pages to start downloading. This should be updated before every release.
     */
    public static final int UPDATE_SERVICE_HERALD_DEFAULT_PAGES = 25;

    /**
     * Key used to detect if taxi data has to be shown instead.
     */
    public static final String CONTACTS_SHOW_TAXI_DATA = "showTaxiData";

    /**
     * Used for defining the different view types in
     * {@link com.csatimes.dojma.adapters.SearchAdapter}
     * Notice that {@link DHC#CONTACT_ITEM_TYPE_TITLE} and {@link DHC#CONTACT_ITEM_TYPE_CONTACT}
     * are also included in this series
     */
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
    public static final int MAIN_ACTIVITY_GAZETTES_POS = 5;
    public static final int MAIN_ACTIVITY_EVENTS_POS = 3;
    public static final int MAIN_ACTIVITY_UTILITIES_POS = 4;
    public static final int MAIN_ACTIVITY_FAVOURITES_POS = 2;
    public static final int MAIN_ACTIVITY_ISSUES_POS = 1;

    /**
     * Alarm receiver request code.
     */
    public static final int ALARM_RECEIVER_REQUEST_CODE = 75;

    /**
     * Alarm receiver intent action.
     */
    public static final String ALARM_RECEIVER_ACTION_UPDATE = PACKAGE_NAME + ".services.alarmreceiver.action.update";

    /**
     * Log printing method where TAG is {@value #PACKAGE_NAME}
     * and the <b>Error</b> level is used
     *
     * @param message Log message
     */
    public static void log(final String message) {
        Log.e(PACKAGE_NAME, message);
    }

    /**
     * Log printing method where TAG is mentioned as one of the arg
     * and the <b>Verbose</b> level is used
     *
     * @param tag     Tag which follows <b>{@value DHC#PACKAGE_NAME}.</b>tag
     * @param message Log message
     */
    public static void v(final String tag, final String message) {
        Log.v(PACKAGE_NAME + "." + tag, message);
    }

    /**
     * Log printing method where TAG is mentioned as one of the arg
     * and the <b>Debug</b> level is used
     *
     * @param tag     Tag which follows <b>{@value DHC#PACKAGE_NAME}.</b>tag
     * @param message Log message
     */
    public static void d(final String tag, final String message) {
        Log.d(PACKAGE_NAME + "." + tag, message);
    }

    /**
     * Log printing method where TAG is mentioned as one of the arg
     * and the <b>Info</b> level is used
     *
     * @param tag     Tag which follows <b>{@value DHC#PACKAGE_NAME}.</b>tag
     * @param message Log message
     */
    public static void i(final String tag, final String message) {
        Log.i(PACKAGE_NAME + "." + tag, message);
    }

    /**
     * Log printing method where TAG is mentioned as one of the arg
     * and the <b>Warn</b> level is used
     *
     * @param tag     Tag which follows <b>{@value DHC#PACKAGE_NAME}.</b>tag
     * @param message Log message
     */
    public static void w(final String tag, final String message) {
        Log.w(PACKAGE_NAME + "." + tag, message);
    }

    /**
     * Specific purpose log printing method where TAG is mentioned as one of the arg
     * and the <b>Error</b> level is used
     *
     * @param tag     Tag which follows <b>{@value DHC#PACKAGE_NAME}.</b>tag
     * @param message Log message
     */
    public static void e(final String tag, final String message) {
        Log.e(PACKAGE_NAME + "." + tag, message);
    }

    /**
     * Get a colored snackbar with time period {@code Snackbar.LENGTH_SHORT}
     *
     * @param view      View paramter to Snackbar
     * @param s         Message to be shown
     * @param bgColor   Background color in value {@code int}
     * @param textColor Text color. Value in {@code int}
     * @return Snackbar object with the specified text,colors
     */
    public static Snackbar makeCustomSnackbar(final View view, final String s,
                                              final int bgColor, final int textColor) {
        com.google.android.material.snackbar.Snackbar snackbar = Snackbar.make(view, s, com.google.android.material.snackbar.Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(bgColor);
        TextView snackbarText = snackbar.getView()
                .findViewById(R.id.snackbar_text);
        snackbarText.setTextColor(textColor);
        return snackbar;
    }

    /**
     * Utility function to check whether device is connected to the internet
     *
     * @param context Context object. eg. {@code this} if in Activity/Service or
     *                {@code getContext()} if calling from a fragment.
     *                <p>
     *                NOTE: Does not check if internet is working fine or not
     * @return {@code true} if connected, {@code false} otherwise
     */
    public static boolean isOnline(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }


    /**
     * Method to download gazette in the downloads folder
     *
     * @param context context
     * @param gi      Gazette item to download
     */
    public static void getGazette(final Activity context, final GazetteItem gi) {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (androidx.legacy.app.ActivityCompat.shouldShowRequestPermissionRationale(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {
                androidx.legacy.app.ActivityCompat.requestPermissions(context,
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

    /**
     * Simple method to download pdf from context and GazetteItem object.
     * @param context context to use
     * @param gi gazette
     */
    private static void downloadPDF(final Context context, final GazetteItem gi) {
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
