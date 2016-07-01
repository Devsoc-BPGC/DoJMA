package com.csatimes.dojma;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * Created by Vikramaditya Kukreja on 01-07-2016.
 */

public class BootBroadcastService extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent startServiceIntent = new Intent(context, UpdateCheckerService.class);
        startWakefulService(context, startServiceIntent);
    }
}
