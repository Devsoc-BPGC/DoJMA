package com.csatimes.dojma;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.widget.Toast;

/**
 * Created by yash on 2/7/16.
 */


public class CopyLinkBroadcastReceiver extends BroadcastReceiver {
    

    @Override
    public void onReceive(Context context, Intent intent) {
        Uri uri = intent.getData();
        if (uri != null) {
            ClipboardManager clipboardManager =
                    (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newUri(null, uri.toString(), uri);
            clipboardManager.setPrimaryClip(clipData);
            Toast.makeText(context," Link Copied to Clipboard",Toast.LENGTH_SHORT).show();
        }
    }

}
