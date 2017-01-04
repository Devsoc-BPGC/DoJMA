package com.csatimes.dojma;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

/**
 * Created by vikramaditya on 15/12/16.
 */

public class UtilitiesMessMenu extends AppCompatActivity {


    private RecyclerView messRecyclerView;

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_mess_menus_toolbar);
        messRecyclerView = (RecyclerView) findViewById(R.id.activity_mess_menu_rv);

        setSupportActionBar(toolbar);





    }
}
