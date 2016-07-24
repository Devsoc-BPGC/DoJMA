package com.csatimes.dojma;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class POSTDownloaderActivity extends AppCompatActivity {
    int initProgress = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Window window;
    private CircularFillableLoaders circularFillableLoaders;
    private ImageView imageView;
    private String[] images = {"https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/1.jpg", "https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/2.jpg", "https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/3.jpg", "https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/4.jpg", "https://raw.githubusercontent" +
            ".com/MobileApplicationsClub/test-repo/master/5.jpg"};

    private BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_postdownloader);
        circularFillableLoaders = (CircularFillableLoaders) findViewById(R.id.loading_image);
        imageView = (ImageView) findViewById(R.id.loading_dojma);
        int random = new Random().nextInt(4);
        Picasso.with(this).load(Uri.parse(images[random])).into(imageView);

        preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();


        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.statusBarColor));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.navigationBarColor));
        }


        //Set up filter options in sharedprefs
        for (int i = 0; i < getResources().getStringArray(R.array.filter_options).length; i++) {
            editor.putBoolean(DHC.FILTER_SUFFIX + getResources().getStringArray(R.array
                    .filter_options)[i], false);
        }
        //Set up sort options in sharedprefs
        for (int i = 0; i < getResources().getStringArray(R.array.sort_options).length; i++) {
            editor.putBoolean(DHC.SORT_SUFFIX + getResources().getStringArray(R.array
                    .sort_options)[i], false);
        }
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        editor.putBoolean(DHC.SORT_SUFFIX + getResources().getStringArray(R.array
                .sort_options)[1], true);
        editor.apply();

        //Gave 5% progress to setting shared preference randomly
        initProgress = 5;
        circularFillableLoaders.setProgress(initProgress);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equalsIgnoreCase(PostDownloadService.UPDATE_PROGRESS)) {
                    circularFillableLoaders.setProgress(intent.getIntExtra(PostDownloadService.UPDATE_PROGRESS, 5));
                } else if (intent.getAction().equalsIgnoreCase(PostDownloadService.SUCCESS)) {
                    circularFillableLoaders.setProgress(100);
                    editor.putBoolean(getString(R.string.SP_first_install), false);
                    editor.apply();
                    Intent i = new Intent(context, HomeActivity.class);
                    startActivity(i);
                    finishDownloaderActivity();
                } else if (intent.getAction().equalsIgnoreCase(PostDownloadService.ZERO_ARTICLES_DOWNLOADED)) {
                    Snackbar snackbar = Snackbar.make(circularFillableLoaders, "Failed to download " +
                            "even a single article. Please try " +
                            "again later", Snackbar.LENGTH_LONG);
                    snackbar.getView().setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                    snackbar.show();
                }
            }
        };
        if (PostDownloadService.instance == null)
            startService(new Intent(this, PostDownloadService.class));
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
        IntentFilter intf = new IntentFilter();
        intf.addAction(PostDownloadService.ZERO_ARTICLES_DOWNLOADED);
        intf.addAction(PostDownloadService.UPDATE_PROGRESS);
        intf.addAction(PostDownloadService.SUCCESS);
        registerReceiver(broadcastReceiver, intf);
    }
}

