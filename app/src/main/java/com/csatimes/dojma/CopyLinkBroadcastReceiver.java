package com.csatimes.dojma;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by yash on 2/7/16.
 */


public class CopyLinkBroadcastReceiver extends BroadcastReceiver {
    private static String currentURL;

    @Override
    public void onReceive(Context context, Intent intent) {
        currentURL = intent.getStringExtra("URL");
        if (currentURL != null) {

            ClipboardManager clipboardManager =
                    (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newUri(null, currentURL.toString(), Uri.parse(currentURL));
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(context, "Link Copied To ClipBoard " , Toast.LENGTH_SHORT).show();
        }
    }
}
