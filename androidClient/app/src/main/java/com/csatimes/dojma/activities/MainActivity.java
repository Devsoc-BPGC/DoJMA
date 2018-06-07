package com.csatimes.dojma.activities;


import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.csatimes.dojma.fragments.EventsFragment;
import com.csatimes.dojma.fragments.IssuesFragment;
import com.csatimes.dojma.fragments.favouritesfragment;
import com.csatimes.dojma.services.UpdateCheckerService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.csatimes.dojma.R;
import com.csatimes.dojma.fragments.HeraldFragment;
import com.csatimes.dojma.fragments.Utilities;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.iid.FirebaseInstanceId;

import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.EXTRA_TEXT;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES;

public class MainActivity
        extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    /**
     * Logging Tag for this class.
     */
    private final String TAG = "activities." + MainActivity.class.getSimpleName();

    /**
     * By default landscape is assumed to be false.
     */
    private boolean landscape = false;
    private SharedPreferences   mPreferences;
    androidx.appcompat.widget.Toolbar mToolbar;

    private Boolean loadFragment(Fragment fragment){
        if(fragment !=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch(item.getItemId()) {
            case R.id.action_refresh:
                intent = new Intent(this, UpdateCheckerService.class);
                startService(intent);
                Toast.makeText(MainActivity.this,"Refresh Works!",Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_about_dojma:
                intent = new Intent(this, AboutDojmaActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_about_us:
                intent = new Intent(this, AboutUsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_shareapp:
                intent = new Intent();
                intent.setAction(ACTION_SEND);
                intent.putExtra(EXTRA_TEXT, getString(R.string.message_app_share));
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share app url via ... "));
                return true;
            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_shorts:
                intent = new Intent(this, ShortsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DHC.e(TAG, FirebaseInstanceId.getInstance().getToken() + "");
        mPreferences = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        landscape = getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
        setContentView(R.layout.activity_home);

        if (mPreferences.getBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), true)) {
            Intent intent = new Intent(this, POSTDownloaderActivity.class);
            startActivity(intent);
            finish();
        }

        mToolbar = (Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(mToolbar);
        //mToolbar.setSubtitle("Test Subtitle");
        //mToolbar.inflateMenu(R.menu.home_menu);

        /*mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;
                switch(item.getItemId()) {
                    case R.id.action_refresh:
                        return true;
                    case R.id.action_about_dojma:
                        intent = new Intent(MainActivity.this, AboutDojmaActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_about_us:
                        intent = new Intent(MainActivity.this, AboutUsActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.action_shareapp:
                        return true;
                    case R.id.action_settings:
                        intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        });*/

        /**
         ** BOTTOM NAVBAR IMPLEMENTATION:
         **/

        //Loading the default fragment
        loadFragment(new HeraldFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView homeBottomNav = findViewById(R.id.nav_view);
        homeBottomNav.setOnNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment = null;
        switch (menuItem.getItemId()) {
            case R.id.bottom_herald:
                fragment = new HeraldFragment();
                break;

            case R.id.bottom_issues:
                fragment = new IssuesFragment();
                break;

            case R.id.bottom_favourites:
                fragment = new favouritesfragment();
                //Intent intent = new Intent(this, FavouritesActivity.class);
                //startActivity(intent);
                break;

            case R.id.bottom_events:
                fragment = new EventsFragment();
                break;

            case R.id.bottom_utilities:
                fragment = new Utilities();
                break;
        }
        return loadFragment(fragment);
    }
}
