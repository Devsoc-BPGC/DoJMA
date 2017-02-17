package com.csatimes.dojma.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Vikramaditya Kukreja on 01-07-2016.
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final int REQUEST_CODE = 1283;
    public static final String TAG = "services.AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context, UpdateCheckerService.class);
        context.startService(i);
    }
}
