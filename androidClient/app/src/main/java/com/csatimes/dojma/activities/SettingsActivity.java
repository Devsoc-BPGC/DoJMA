package com.csatimes.dojma.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.fragments.SettingsFragment;

import androidx.appcompat.widget.Toolbar;

public class SettingsActivity extends BaseActivity
        implements SettingsFragment.OnThemeChangedListener {
    private SettingsFragment settingsFragment;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final Toolbar toolbar = findViewById(R.id.settings_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        settingsFragment = new SettingsFragment();
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            getFragmentManager().beginTransaction()
                    .add(R.id.content_settings_frame_layout, settingsFragment)
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.content_settings_frame_layout, settingsFragment)
                    .commit();
        }
        settingsFragment.setOnThemeChangedListener(this);
    }

    @Override
    public void onThemeChanged() {
        getFragmentManager().beginTransaction().remove(settingsFragment).commit();
        Toast.makeText(this, R.string.restart_on_theme_change_prompt, Toast.LENGTH_LONG)
                .show();
        recreate();
    }
}
