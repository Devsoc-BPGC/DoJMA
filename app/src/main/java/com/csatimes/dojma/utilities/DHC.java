package com.csatimes.dojma.utilities;

import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Dojma Helper Class
 */
public class DHC {

    public static final String USER_PREFERENCES = "USER_PREFS";

    public static final int UPDATE_SERVICE_PENDING_INTENT_CODE = 243;
    public static final int UPDATE_SERVICE_NOTIFICATION_CODE = 42;
    public static final int UPDATE_SERVICE_HERALD_DEFAULT_PAGES = 16;
    public static final String UPDATE_SERVICE_DOWNLOAD_SUCCESS = "com.csatimes.dojma.update.service.dns";
    public static final String UPDATE_SERVICE_NO_SUCCESS = "com.csatimes.dojma.update.service.ns";
    public static final String UPDATE_SERVICE_DOJMA_JSON_ADDRESS_PREFIX = "http://csatimes.co.in/dojma/page/";
    public static final String UPDATE_SERVICE_DOJMA_JSON_ADDRESS_SUFFIX = "/?json=all";
    public static final String UPDATE_SERVICE_HERALD_PAGES = "HERALD_PAGES";
    public static final String DoJMA_FACEBOOK_URL = "https://www.facebook.com/DoJMABITSGoa";
    public static final String DoJMA_FACEBOOK_PAGE_ID = "DoJMABITSGoa";
    public static final String BITS_GOA_LCD_LINK = "http://cc.bits-goa.ac.in/enotice/Lcd.php";
    public static final String REALM_DOJMA_DATABASE = "DOJMA_DATABASE";

    public static void log(String message) {
        Log.e("TAG", message);
    }

    public static Snackbar makeCustomSnackbar(View view, String s, int bgColor, int textColor) {
        Snackbar snackbar = Snackbar.make(view, s, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundColor(bgColor);
        TextView snackbarText = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        snackbarText.setTextColor(textColor);
        return snackbar;
    }
}
