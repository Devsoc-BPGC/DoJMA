package com.csatimes.dojma;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class Settings extends AppCompatActivity {
    private Window window;
    private CustomSettingsAdapter adapter;
    private List<SettingsItem> settingsItemList = new ArrayList<>();
    private ListView listView;

    public class SettingsItem {
        String name = "Setting Name";
        String desc = "Setting Description";

        public SettingsItem(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        Bundle bundle = getIntent().getExtras();
        int color = bundle.getInt("pageColor");

        if (Build.VERSION.SDK_INT >= 21) {
            window.setStatusBarColor(color);
            window.setNavigationBarColor(color);

        }
        toolbar.setBackgroundColor(color);


        //Default adapter for list
        listView = (ListView) findViewById(R.id.settings_list_view);
        settingsItemList.add(new SettingsItem("Rebuild Icons", "Tap to fix broken icons"));
        adapter = new CustomSettingsAdapter(this,settingsItemList);
        listView.setAdapter(adapter);
    }

}
