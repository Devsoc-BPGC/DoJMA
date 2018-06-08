package com.csatimes.dojma.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.csatimes.dojma.R;

/**
 * Created by Vikramaditya Kukreja on 12-07-2016.
 */

public class SettingsFragment extends PreferenceFragment
        implements SharedPreferences.OnSharedPreferenceChangeListener {
    boolean nightMode = false;
    OnThemeChangedListener onThemeChangedListener;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        nightMode = getPreferenceManager().getSharedPreferences()
                .getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences,
                                          final String key) {
        if (key.equals(getString(R.string.PREFERENCE_general_night_mode))) {
            final boolean mode = sharedPreferences.getBoolean(key, false);
            if (mode != nightMode) {
                if (onThemeChangedListener != null) {
                    onThemeChangedListener.onThemeChanged();
                }
                nightMode = mode;
            }
        }
    }

    public void setOnThemeChangedListener(final OnThemeChangedListener listener) {
        this.onThemeChangedListener = listener;
    }

    public interface OnThemeChangedListener {
        void onThemeChanged();
    }
}
