package com.csatimes.dojma.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.csatimes.dojma.R;

/**
 * Base activity that updates all activities.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
    }

    /**
     * Set theme on create.
     */
    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this)
                .getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }
}
