package com.csatimes.dojma;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

public class Settings extends AppCompatActivity {
    private Window window;
    private Tracker mTracker;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.offline_toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //Check if analytics is allowed by user
        boolean sharedPrefAnalytics = sharedPref.getBoolean("pref_other_analytics", true);

        if (sharedPrefAnalytics) {
            AnalyticsApplication application = (AnalyticsApplication) getApplication();
            mTracker = application.getDefaultTracker();
            mTracker.setScreenName("Settings");
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        Bundle bundle = getIntent().getExtras();
        try {
            int color = bundle.getInt("pageColor");

            if (Build.VERSION.SDK_INT >= 21) {
                window.setStatusBarColor(color);
                window.setNavigationBarColor(color);
            }
            toolbar.setBackgroundColor(color);

        } catch (Exception ignore) {
            finish();
        }

        getFragmentManager().beginTransaction().add(R.id.content_settings_frame, new SettingsFragment())
                .commit();

    }

}
