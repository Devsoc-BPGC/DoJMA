package com.csatimes.dojma.services;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.csatimes.dojma.utilities.DHC;

/**
 * Created by Vikramaditya Kukreja on 01-07-2016.
 */

public class BootBroadcastService extends WakefulBroadcastReceiver {

    public static final String TAG = "services.BootBroadcastService";

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, UpdateCheckerService.class);
        startServiceIntent.putExtra(DHC.UPDATE_SERVICE_INTENT_PAGES,1);
        startWakefulService(context, startServiceIntent);
        DHC.log(TAG,"BootBroadcast");
    }
}
