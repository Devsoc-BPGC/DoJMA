package com.csatimes.dojma.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.fragments.SettingsFragment;

public class SettingsActivity extends BaseActivity implements SettingsFragment.OnThemeChangedListener {
    private static final String TAG = "TAG";
    SettingsFragment settingsFragment;
    private Window window;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.offline_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        settingsFragment = new SettingsFragment();
        if (getFragmentManager().getBackStackEntryCount() == 0)
            getFragmentManager().beginTransaction().add(R.id.content_settings_frame_layout, settingsFragment).commit();
        else
            getFragmentManager().beginTransaction().replace(R.id.content_settings_frame_layout, settingsFragment).commit();

        settingsFragment.setOnThemeChangedListener(this);


    }

    @Override
    public void onThemeChanged() {
        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
        Toast.makeText(this, "Please restart app for the changes to take place", Toast.LENGTH_LONG).show();
        recreate();
    }
}
