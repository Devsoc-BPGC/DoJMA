package com.csatimes.dojma.utilities;

import android.util.Log;

/**
 * Created by Vikramaditya Kukreja on 07-06-2016.
 * DojmaHelper Class
 */
public class DHC {

    public static final String USER_PREFERENCES = "USER_PREFS";

    public static final int UPDATE_SERVICE_PENDING_INTENT_CODE = 243;
    public static final int UPDATE_SERVICE_NOTIFICATION_CODE = 42;
    public static final String DoJMA_FACEBOOK_URL = "https://www.facebook.com/DoJMABITSGoa";
    public static final String DoJMA_FACEBOOK_PAGE_ID = "DoJMABITSGoa";
    public static final String BITS_GOA_LCD_LINK = "http://cc.bits-goa.ac.in/enotice/Lcd.php";
    public static final String REALM_DOJMA_DATABASE = "DOJMA_DATABASE";

    public static final String dojmaFolderName = "dojma";

    public static void log(String message) {
        Log.e("TAG", message);
    }

}
