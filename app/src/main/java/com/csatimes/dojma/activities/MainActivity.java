package com.csatimes.dojma.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.browser.customtabs.CustomTabsIntent;

import com.csatimes.dojma.fragments.EventsFragment;
import com.csatimes.dojma.fragments.IssuesFragment;
import com.csatimes.dojma.fragments.favouritesfragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.SlideshowPagerAdapter;
import com.csatimes.dojma.adapters.ViewPagerAdapter;
import com.csatimes.dojma.fragments.Gazettes;
import com.csatimes.dojma.fragments.HeraldFragment;
import com.csatimes.dojma.fragments.Utilities;
import com.csatimes.dojma.interfaces.OnTitleUpdateListener;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.SlideshowItem;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.csatimes.dojma.services.UpdateCheckerService;
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.utilities.StackTransformer;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import io.realm.Realm;
import io.realm.RealmList;
import me.relex.circleindicator.CircleIndicator;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC_WAKEUP;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.EXTRA_REFERRER;
import static android.content.Intent.EXTRA_TEXT;
import static android.content.Intent.URI_ANDROID_APP_SCHEME;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static androidx.core.view.GravityCompat.START;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
import static com.csatimes.dojma.utilities.DHC.ALARM_RECEIVER_ACTION_UPDATE;
import static com.csatimes.dojma.utilities.DHC.ALARM_RECEIVER_REQUEST_CODE;
import static com.csatimes.dojma.utilities.DHC.BITS_GOA_LCD_LINK;
import static com.csatimes.dojma.utilities.DHC.DoJMA;
import static com.csatimes.dojma.utilities.DHC.DoJMA_FACEBOOK_PAGE_ID;
import static com.csatimes.dojma.utilities.DHC.DoJMA_FACEBOOK_URL;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TOOLBAR;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TOOLBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TOOLBAR_SUBTITLE;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TOOLBAR_TITLE;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_UI;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_EVENTS_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_GAZETTES_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_HERALD_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_UTILITIES_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_FAVOURITES_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_ISSUES_POS;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_NAVBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_NAVBAR_TITLE;
import static com.csatimes.dojma.utilities.DHC.log;

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
    private SharedPreferences                         mPreferences;

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
