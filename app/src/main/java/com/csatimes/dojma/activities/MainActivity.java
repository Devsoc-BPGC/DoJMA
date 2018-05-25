package com.csatimes.dojma.activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;

import com.csatimes.dojma.fragments.EventsFragment;
import com.csatimes.dojma.fragments.IssuesFragment;
import com.csatimes.dojma.fragments.favouritesfragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.fragment.app.Fragment;
import android.view.MenuItem;

import com.csatimes.dojma.R;
import com.csatimes.dojma.fragments.HeraldFragment;
import com.csatimes.dojma.fragments.Utilities;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.iid.FirebaseInstanceId;
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

    private Boolean loadFragment(Fragment fragment){
        if(fragment !=null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.home_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DHC.e(TAG, FirebaseInstanceId.getInstance().getToken() + "");
        mPreferences = getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);

        if (mPreferences.getBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), true)) {
            Intent intent = new Intent(this, POSTDownloaderActivity.class);
            startActivity(intent);
            finish();
        }

        landscape = getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
        setContentView(R.layout.activity_home);

        /**
         * BOTTOM NAVBAR IMPLEMENTATION:
         */

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
