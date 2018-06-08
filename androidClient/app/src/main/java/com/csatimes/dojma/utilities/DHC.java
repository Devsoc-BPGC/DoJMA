package com.csatimes.dojma.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.BuildConfig;
import com.csatimes.dojma.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * Dojma Helper Class.
 */
public class DHC {

    /**
     * Milliseconds in day.
     */
    public static final int MS_IN_DAY = 24 * 60 * 60 * 1000;

    /**
     * Days in fortnight.
     */
    public static final int DAYS_IN_FN = 14;

    /**
     * Days in a month.
     */
    public static final int DAYS_IN_MONTH = 30;

    /**
     * Days in a year.
     */
    public static final int DAYS_IN_YR = 365;

    public static final String TAG_PREFIX = "mac.";

    public static final String MIME_TYPE_PLAINTEXT = "text/plain";

    public static final int VERSION = 2;

    /**
     * Full name of DoJMA.
     */
    public static final String DoJMA = "Department of Journalism and Media Affairs";

    /**
     * Name of shared preferences file used for the app.
     */
    public static final String USER_PREFERENCES = "USER_PREFS";

    /**
     * Fully qualified package name.
     */
    public static final String PACKAGE_NAME = BuildConfig.APPLICATION_ID;

    //MainActivity UI codes
    public static final String USER_PREFERENCES_NAVBAR_TITLE = "USER_PREFS_NAVBAR_TITLE";

    public static final String USER_PREFERENCES_MISC_CARD_MESSAGE = "USER_PREFS_MISC";

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
     * Firebase node name for nav bar title under <b>{@value #FIREBASE_DATABASE_REFERENCE_UI}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE = "navbarTitle";

    /**
     * Firebase node name for nav bar image under <b>{@value #FIREBASE_DATABASE_REFERENCE_UI}</b>.
     */
    public static final String FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL = "navbarImage";

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
    public static final int SEARCH_ITEM_TYPE_EVENT = 4;
    public static final int SEARCH_ITEM_TYPE_CONTACT = 5;
    public static final int SEARCH_ITEM_TYPE_LINK = 6;
    public static final int SEARCH_ITEM_TYPE_MESS = 7;

    public static final int CONTACT_ITEM_TYPE_TITLE = 9;
    public static final int CONTACT_ITEM_TYPE_CONTACT = 10;

    public static final int CONTACTS = 0;
    public static final int CONTACTS_TAXI = 1;
    public static final int MESS = 2;
    public static final int LINKS = 3;
    public static final int MISC = 4;
    public static final int UTILITIES_ITEM_TYPE_MAP = 5;

    public static final int MAIN_ACTIVITY_EVENTS_POS = 3;

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
     * Specific purpose log printing method where TAG is mentioned as one of the arg
     * and the <b>Error</b> level is used
     *
     * @param tag Tag which follows <b>{@value DHC#PACKAGE_NAME}.</b>tag
     * @param message Log message
     */
    public static void e(final String tag, final String message) {
        Log.e(PACKAGE_NAME + "." + tag, message);
    }

    /**
     * Get a colored snackbar with time period {@code Snackbar.LENGTH_SHORT}
     *
     * @param view View paramter to Snackbar
     * @param s Message to be shown
     * @param bgColor Background color in value {@code int}
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
     * {@code getContext()} if calling from a fragment.
     * <p>
     * NOTE: Does not check if internet is working fine or not
     * @return {@code true} if connected, {@code false} otherwise
     */
    public static boolean isOnline(final Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }


}
