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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ViewPagerAdapter;
import com.csatimes.dojma.fragments.Events;
import com.csatimes.dojma.fragments.Gazettes;
import com.csatimes.dojma.fragments.Herald;
import com.csatimes.dojma.fragments.Utilities;
import com.csatimes.dojma.interfaces.OnTitleUpdateListener;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.services.AlarmReceiver;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.csatimes.dojma.services.UpdateCheckerService;
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import io.realm.Realm;

import static android.app.AlarmManager.INTERVAL_DAY;
import static android.app.AlarmManager.RTC_WAKEUP;
import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Intent.ACTION_SEND;
import static android.content.Intent.ACTION_VIEW;
import static android.content.Intent.EXTRA_REFERRER;
import static android.content.Intent.EXTRA_TEXT;
import static android.content.Intent.URI_ANDROID_APP_SCHEME;
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.support.v4.view.GravityCompat.START;
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
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TOOLBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TOOLBAR_SUBTITLE;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_TOOLBAR_TITLE;
import static com.csatimes.dojma.utilities.DHC.FIREBASE_DATABASE_REFERENCE_UI;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_EVENTS_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_GAZETTES_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_HERALD_POS;
import static com.csatimes.dojma.utilities.DHC.MAIN_ACTIVITY_UTILITIES_POS;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_NAVBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_NAVBAR_TITLE;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_TOOLBAR_IMAGE_URL;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_TOOLBAR_SUBTITLE;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_TOOLBAR_TITLE;

public class MainActivity extends AppCompatActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        OnTitleUpdateListener,
        AppBarLayout.OnOffsetChangedListener,
        ViewPager.OnPageChangeListener {


    public final String TAG = "activities." + MainActivity.class.getSimpleName();

    private boolean landscape = false;

    private DatabaseReference mNavBarRef = FirebaseDatabase.getInstance().getReference().child(FIREBASE_DATABASE_REFERENCE_UI);
    private DrawerLayout mDrawerLayout;
    private SimpleDraweeView mNavBarImage;
    private SimpleDraweeView mToolbarBackground;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private Toolbar mToolbar;
    private TabLayout mFragmentsTabLayout;
    private TextView mToolbarTitle;
    private TextView mToolbarSubTitle;
    private TextView mNavBarTitle;
    private ValueEventListener mNavBarListener;
    private ViewPager mFragmentsViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
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

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_home_appbar_layout);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mFragmentsTabLayout = (TabLayout) findViewById(R.id.app_bar_home_tabs);
        mFragmentsViewPager = (ViewPager) findViewById(R.id.app_bar_home_viewpager);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavBarImage = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.nav_bar_image);
        mNavBarTitle = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_bar_title);
        mToolbar = (Toolbar) findViewById(R.id.app_bar_home_toolbar);
        mToolbarBackground = (SimpleDraweeView) findViewById(R.id.app_bar_home_toolbar_background);
        mToolbarTitle = (TextView) findViewById(R.id.app_bar_home_text_title);
        mToolbarSubTitle = (TextView) findViewById(R.id.app_bar_home_text_subtitle);

        setSupportActionBar(mToolbar);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(FLAG_TRANSLUCENT_STATUS);
        }
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        setupViewPager(mFragmentsViewPager);
        //Tablyout setup is called only after the viewpager is ready
        mFragmentsTabLayout.setupWithViewPager(mFragmentsViewPager);

        //Custom method to set icons for the tabs
        setupTabIcons();

        if (savedInstanceState != null) {
            mFragmentsViewPager.setCurrentItem(savedInstanceState.getInt("currentItem", 0));
        }

        //If the Home Activity was started using the app shortcut with the intent action then it is detected here and
        // accordingly the viewpager position is set to open Events tab for example
        if (getString(R.string.shortcut_events).equals(getIntent().getAction())) {
            mFragmentsViewPager.setCurrentItem(2);
        } else if (getString(R.string.shortcut_utilities).equals(getIntent().getAction())) {
            mFragmentsViewPager.setCurrentItem(3);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        mEditor = mPreferences.edit();

        mDrawerLayout.addDrawerListener(toggle);
        appBarLayout.addOnOffsetChangedListener(this);
        mFragmentsViewPager.addOnPageChangeListener(this);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFragmentsViewPager.requestFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mNavBarListener = returnImageChangeListener();
        mNavBarRef.addValueEventListener(mNavBarListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mNavBarRef.removeEventListener(mNavBarListener);
        mEditor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).getBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), true))
            scheduleAlarmForUpdateService();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentItem", mFragmentsViewPager.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(START)) {
            mDrawerLayout.closeDrawer(START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        if (mFragmentsViewPager.getCurrentItem() == MAIN_ACTIVITY_HERALD_POS)
            menu.findItem(R.id.action_refresh_herald).setVisible(true);
        else menu.findItem(R.id.action_refresh_herald).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_search) {
            Intent intent = new Intent(this, SearchableActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_about_us) {
            Intent aboutUs = new Intent(this, AboutUsActivity.class);
            startActivity(aboutUs);
        } else if (id == R.id.action_about_dojma) {
            Intent intent = new Intent(this, AboutDojmaActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_refresh_herald) {
            Intent intent = new Intent(this, UpdateCheckerService.class);
            startService(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_main_issues) {
            Intent intent = new Intent(this, IssuesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_archives) {
            Intent intent = new Intent((ACTION_SEND));
            intent.putExtra(EXTRA_TEXT, "https://issuu.com/bitsherald");

            Intent copy_intent = new Intent(this, CopyLinkBroadcastReceiver.class);
            PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(this, 0, copy_intent, FLAG_UPDATE_CURRENT);
            String copy_label = "Copy Link";
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(getChromeCustomTabColorFromTheme())
                    .setCloseButtonIcon(BitmapFactory.decodeResource(this.getResources(),
                            R.drawable.ic_arrow_back_white_24dp))
                    .addMenuItem(copy_label, copy_pendingIntent)
                    .addDefaultShareMenuItem()
                    .enableUrlBarHiding()
                    .build();

            String issuu = "https://issuu.com/bitsherald";
            CustomTabActivityHelper.openCustomTab(this, customTabsIntent,
                    Uri.parse(issuu),
                    new CustomTabActivityHelper.CustomTabFallback() {
                        @TargetApi(Build.VERSION_CODES.LOLLIPOP_MR1)
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            Intent intent = new Intent(ACTION_VIEW, uri);
                            intent.putExtra(EXTRA_REFERRER, Uri.parse(URI_ANDROID_APP_SCHEME + "//" + getPackageName()));
                            startActivity(intent);
                        }
                    });
        } else if (id == R.id.nav_main_share) {
            Intent intent = new Intent();
            intent.setAction(ACTION_SEND);
            intent.putExtra(EXTRA_TEXT, getString(R.string.message_app_share));
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share app url via ... "));

        } else if (id == R.id.nav_main_favourites) {
            Intent intent = new Intent(this, FavouritesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_gallery) {
            Intent intent = new Intent(this, ImagesAndMediaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_about) {
            Intent intent = new Intent(this, AboutDojmaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_fb) {

            try {
                Intent facebookIntent = new Intent(ACTION_VIEW);
                String facebookUrl = getFacebookPageURL();
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);

            } catch (Exception e) {
                Intent intent = new Intent(ACTION_VIEW, Uri.parse(DoJMA_FACEBOOK_URL));
                startActivity(intent);
            }
        } else if (id == R.id.nav_main_lcd) {
            Intent intent = new Intent((ACTION_SEND));
            intent.putExtra(EXTRA_TEXT, BITS_GOA_LCD_LINK);

            Intent copy_intent = new Intent(this, CopyLinkBroadcastReceiver.class);
            PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(this, 0, copy_intent,
                    FLAG_UPDATE_CURRENT);
            String copy_label = "Copy Link";
            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(getChromeCustomTabColorFromTheme())
                    .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp))
                    .addMenuItem(copy_label, copy_pendingIntent)
                    .addDefaultShareMenuItem()
                    .enableUrlBarHiding()
                    .build();

            CustomTabActivityHelper.openCustomTab(this, customTabsIntent,
                    Uri.parse(BITS_GOA_LCD_LINK),
                    new CustomTabActivityHelper.CustomTabFallback() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            Intent intent = new Intent(ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
        } else if (id == R.id.nav_main_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(START);

        return false;
    }

    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }

    private void setupTabIcons() {
        if (!landscape) {
            mFragmentsTabLayout.getTabAt(0).setIcon(R.drawable.ic_chrome_reader_mode_white_24dp);
            mFragmentsTabLayout.getTabAt(1).setIcon(R.drawable.ic_picture_in_picture_white_24dp);
            mFragmentsTabLayout.getTabAt(2).setIcon(R.drawable.ic_event_note_white_24dp);
            mFragmentsTabLayout.getTabAt(3).setIcon(R.drawable.ic_local_convenience_store_white_24dp);
        }
    }

    //Setting up the View Pager with the fragments
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        Herald heraldFragment = new Herald();
        Gazettes gazettesFragment = new Gazettes();
        Events eventsFragments = new Events();
        Utilities utilitiesFragment = new Utilities();

        eventsFragments.setOnTitleUpdateListener(this);

        Realm database = Realm.getDefaultInstance();

        adapter.addFragment(heraldFragment, "Herald", MAIN_ACTIVITY_HERALD_POS);
        adapter.addFragment(gazettesFragment, "Gazettes", MAIN_ACTIVITY_GAZETTES_POS);
        adapter.addFragment(eventsFragments, "Events(" + database.where(EventItem.class).findAll().size() + ")", MAIN_ACTIVITY_EVENTS_POS);
        adapter.addFragment(utilitiesFragment, "Utilities", MAIN_ACTIVITY_UTILITIES_POS);

        viewPager.setAdapter(adapter);
        database.close();
    }

    public void scheduleAlarmForUpdateService() {

        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.setAction(ALARM_RECEIVER_ACTION_UPDATE);
        // Create a PendingIntent to be triggered when the alarm goes off
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_RECEIVER_REQUEST_CODE,
                intent, FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 3 day
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) getSystemService(ALARM_SERVICE);
        //Cancel old alarm
        alarm.cancel(pIntent);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(RTC_WAKEUP, firstMillis,
                5 * INTERVAL_DAY, pIntent);
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL() {
        PackageManager packageManager = getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" + DoJMA_FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + DoJMA_FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return DoJMA_FACEBOOK_URL; //normal web url
        }
    }

    private ValueEventListener returnImageChangeListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot navBarTitleSnapshot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE);
                DataSnapshot navBarImageUrlSnapshot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL);
                DataSnapshot toolbarTitleSnapshot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_TITLE);
                DataSnapshot toolbarSubtitleSnapshot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_SUBTITLE);
                DataSnapshot toolbarImageUrlSnapshot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_IMAGE_URL);

                if (navBarTitleSnapshot.exists()) {
                    String navBarTitle = navBarTitleSnapshot.getValue(String.class);
                    mEditor.putString(USER_PREFERENCES_NAVBAR_TITLE, navBarTitle);
                }
                if (navBarImageUrlSnapshot.exists()) {
                    String navBarImageUrl = navBarImageUrlSnapshot.getValue(String.class);
                    mEditor.putString(USER_PREFERENCES_NAVBAR_IMAGE_URL, navBarImageUrl);
                }
                if (toolbarTitleSnapshot.exists()) {
                    String toolbarTitle = toolbarTitleSnapshot.getValue(String.class);
                    mEditor.putString(USER_PREFERENCES_TOOLBAR_TITLE, toolbarTitle);
                }
                if (toolbarSubtitleSnapshot.exists()) {
                    String toolbarSubtitle = toolbarSubtitleSnapshot.getValue(String.class);
                    mEditor.putString(USER_PREFERENCES_TOOLBAR_SUBTITLE, toolbarSubtitle);
                }
                if (toolbarImageUrlSnapshot.exists()) {
                    String toolbarImageUrl = toolbarImageUrlSnapshot.getValue(String.class);
                    mEditor.putString(USER_PREFERENCES_TOOLBAR_IMAGE_URL, toolbarImageUrl);
                }

                mEditor.apply();

                mNavBarTitle.setText(mPreferences.getString(USER_PREFERENCES_NAVBAR_TITLE, DoJMA));
                mNavBarImage.setImageURI(mPreferences.getString(USER_PREFERENCES_NAVBAR_IMAGE_URL, ""));
                mToolbarTitle.setText(mPreferences.getString(USER_PREFERENCES_TOOLBAR_TITLE, DoJMA));
                mToolbarSubTitle.setText(mPreferences.getString(USER_PREFERENCES_TOOLBAR_SUBTITLE, "v2"));
                mToolbarBackground.setImageURI(mPreferences.getString(USER_PREFERENCES_TOOLBAR_IMAGE_URL, ""));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.e(TAG, "database error " + databaseError.getMessage() + " " + databaseError.getDetails());
            }
        };
    }

    @Override
    public void onTitleUpdate(String title, int pos) {
        mFragmentsTabLayout.getTabAt(pos).setText(title);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        int alpha = 255 * (appBarLayout.getTotalScrollRange() - mToolbar.getMeasuredHeight() + verticalOffset) / appBarLayout.getTotalScrollRange();
        if (alpha > 230) mToolbarBackground.setImageAlpha(255);
        else if (alpha < 0) mToolbarBackground.setImageAlpha(0);
        else mToolbarBackground.setImageAlpha(alpha);
    }

    /**
     * This method is used to make Refresh menu item visible /invisible depending on tab position<br>
     * If tab position = {@value DHC#MAIN_ACTIVITY_HERALD_POS} then Refresh menu item is shown
     *
     * @param position             current tab position
     * @param positionOffset       offset from base
     * @param positionOffsetPixels offset from base in px
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        invalidateOptionsMenu();
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
