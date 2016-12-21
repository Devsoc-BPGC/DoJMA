package com.csatimes.dojma;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * Created by Vikramaditya Kukreja on 12-07-2016.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    boolean nightMode = false;
    OnThemeChangedListener onThemeChangedListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        nightMode = getPreferenceManager().getSharedPreferences().getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        boolean mode = sharedPreferences.getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode != nightMode) {
            if (onThemeChangedListener != null) onThemeChangedListener.onThemeChanged();
        }
    }

    public void setOnThemeChangedListener(OnThemeChangedListener onThemeChangedListener) {
        this.onThemeChangedListener = onThemeChangedListener;
    }

    public interface OnThemeChangedListener {
        void onThemeChanged();
    }
}
