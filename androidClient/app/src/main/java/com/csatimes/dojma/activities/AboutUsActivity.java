package com.csatimes.dojma.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ContributorsAdapter;
import com.csatimes.dojma.adapters.MaclinksAdapter;
import com.csatimes.dojma.adapters.MorebymacAdapter;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AboutUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        Toolbar toolbar = findViewById(R.id.about_us_toolbar);

        RecyclerView maclinksRecyclerView = findViewById(R.id.content_about_us_maclinks_rv);
        RecyclerView morebymacRecyclerView = findViewById(R.id.content_about_us_morebymac_rv);
        RecyclerView contributorsRecyclerView = findViewById(R.id.content_about_us_contributors_rv);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about_app);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // clear FLAG_TRANSLUCENT_STATUS flag
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.mac_color));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.mac_color));
        }

        maclinksRecyclerView.setHasFixedSize(false);
        maclinksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        maclinksRecyclerView.setAdapter(new MaclinksAdapter());


        morebymacRecyclerView.setHasFixedSize(false);
        morebymacRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        morebymacRecyclerView.setAdapter(new MorebymacAdapter());


        contributorsRecyclerView.setHasFixedSize(false);
        contributorsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        contributorsRecyclerView.setAdapter(new ContributorsAdapter());

        findViewById(R.id.content_about_us_app_name_tv).requestFocus();

    }

}
