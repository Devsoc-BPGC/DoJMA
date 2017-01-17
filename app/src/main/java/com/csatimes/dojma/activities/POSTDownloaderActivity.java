package com.csatimes.dojma.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.services.PostDownloadService;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.utilities.SimpleAlertDialog;

public class POSTDownloaderActivity extends AppCompatActivity {
    int initProgress = 0;
    private SharedPreferences.Editor editor;
    private TextView textView;
    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_postdownloader);
        textView = (TextView) findViewById(R.id.post_text);

        SharedPreferences preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();


        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.navigationBarColor));
        }

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase(PostDownloadService.UPDATE_PROGRESS)) {
                } else if (intent.getAction().equalsIgnoreCase(PostDownloadService.SUCCESS)) {
                    editor.putBoolean(getString(R.string.SP_first_install), false);
                    editor.apply();
                    Intent i = new Intent(context, HomeActivity.class);
                    startActivity(i);
                    finishDownloaderActivity();

                } else if (intent.getAction().equalsIgnoreCase(PostDownloadService.ZERO_ARTICLES_DOWNLOADED)) {

                    Snackbar snackbar = Snackbar.make(textView, "Failed to download " +
                            "even a single article. Please try " +
                            "again later", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                    TextView snackText = (TextView) snackbar.getView().findViewById(android
                            .support.design.R.id.snackbar_text);
                    snackText.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    snackbar.show();

                    textView.setText("NO ARTICLES DOWNLOADED. TRY AGAIN LATER");

                    SimpleAlertDialog sad = new SimpleAlertDialog();
                    sad.showDialog(POSTDownloaderActivity.this, "Download Status", "Articles could " +
                            "not be downloaded from" +
                            " " +
                            "csatimes.co.in/dojma OR internet is currently not available. If " +
                            "internet is working fine please check that the website is not down. " +
                            "If that is the case please bear with us as it is not in the control " +
                            "of" +
                            " MAC " +
                            "or DoJMA", "Continue", "Cancel", true, true);
                    sad.setClickListener(new SimpleAlertDialog.ClickListener() {
                        @Override
                        public void onPosButtonClick() {
                            editor.putBoolean(getString(R.string.SP_first_install), false);
                            editor.apply();
                            Intent i = new Intent(POSTDownloaderActivity.this, HomeActivity.class);
                            startActivity(i);
                            finishDownloaderActivity();
                        }

                        @Override
                        public void onNegButtonClick() {

                        }
                    });
                }
            }
        };

    }

    private void finishDownloaderActivity() {
        finish();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(broadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (PostDownloadService.instance == null)
            startService(new Intent(this, PostDownloadService.class));

        IntentFilter intf = new IntentFilter();
        intf.addAction(PostDownloadService.ZERO_ARTICLES_DOWNLOADED);
        intf.addAction(PostDownloadService.UPDATE_PROGRESS);
        intf.addAction(PostDownloadService.SUCCESS);
        registerReceiver(broadcastReceiver, intf);
    }
}

