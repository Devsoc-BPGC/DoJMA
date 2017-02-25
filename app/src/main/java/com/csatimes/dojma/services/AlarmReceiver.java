package com.csatimes.dojma.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.csatimes.dojma.activities.MainActivity;
import com.csatimes.dojma.utilities.DHC;

/**
 * Broadcast receiver that is called as per the alarm set in {@link MainActivity#scheduleAlarmForUpdateService()}
 */

public class AlarmReceiver extends BroadcastReceiver {

    public static final String TAG = "services.AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        DHC.log(TAG, "AlarmReceiver called");
        if (intent != null && intent.getAction().equals(DHC.ALARM_RECEIVER_ACTION_UPDATE)) {
            Intent i = new Intent(context, UpdateCheckerService.class);
            context.startService(i);
        }
    }
}
