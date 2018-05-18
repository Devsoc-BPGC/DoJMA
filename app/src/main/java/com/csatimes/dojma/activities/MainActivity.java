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
        extends BaseActivity
        implements
        NavigationView.OnNavigationItemSelectedListener,
        OnTitleUpdateListener {

    /**
     * Logging Tag for this class.
     */
    private final String TAG = "activities." + MainActivity.class.getSimpleName();

    /**
     * By default landscape is assumed to be false.
     */
    private boolean landscape = false;

    private DatabaseReference                         mUIReference = FirebaseDatabase.getInstance().getReference().child(FIREBASE_DATABASE_REFERENCE_UI);
    private androidx.drawerlayout.widget.DrawerLayout mDrawerLayout;
    private Realm                                     mDatabase;
    private SimpleDraweeView                          mNavBarImage;
    private SharedPreferences                         mPreferences;
    private SharedPreferences.Editor                  mEditor;
    private SlideshowPagerAdapter                     mSlideshowPagerAdapter;
    private RealmList<SlideshowItem>                  mSlideshowItems;
    private TabLayout                                 mFragmentsTabLayout;
    private TextView                                  mNavBarTitle;
    private ValueEventListener                        mUIListener;
    private ViewPager                                 mFragmentsViewPager;
    private ViewPager                                 mSlideShowViewPager;
    private BottomNavigationView                      mbottomnavigationview;

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

        mbottomnavigationview = findViewById(R.id.bottom_navigation);
        mbottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch(menuItem.getItemId()){
                    case R.id.bottom_herald:mFragmentsViewPager.setCurrentItem(MAIN_ACTIVITY_HERALD_POS);
                                            menuItem.setChecked(true);
                                            break;
                    case R.id.bottom_favourites:mFragmentsViewPager.setCurrentItem(MAIN_ACTIVITY_FAVOURITES_POS);
                                                menuItem.setChecked(true);
                                                break;
                    case R.id.bottom_issues:mFragmentsViewPager.setCurrentItem(MAIN_ACTIVITY_ISSUES_POS);
                                            menuItem.setChecked(true);
                                            break;
                    case R.id.bottom_events:mFragmentsViewPager.setCurrentItem(MAIN_ACTIVITY_EVENTS_POS);
                                            menuItem.setChecked(true);
                                            break;
                    case R.id.bottom_utilities: mFragmentsViewPager.setCurrentItem(MAIN_ACTIVITY_UTILITIES_POS);
                                                menuItem.setChecked(true);
                                                break;
                }
                return false;
            }
        });
        CircleIndicator circleIndicator = findViewById(R.id.app_bar_vp_indicator);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mFragmentsTabLayout = findViewById(R.id.app_bar_home_tabs);
        mFragmentsViewPager = findViewById(R.id.app_bar_home_viewpager);
        com.google.android.material.navigation.NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavBarImage = navigationView.getHeaderView(0).findViewById(R.id.nav_bar_image);
        mNavBarTitle = navigationView.getHeaderView(0).findViewById(R.id.nav_bar_title);
        mSlideShowViewPager = findViewById(R.id.app_bar_home_slideshow_vp);
        Toolbar mToolbar = findViewById(R.id.app_bar_home_toolbar);

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
        setupViewPagers();
        circleIndicator.setViewPager(mSlideShowViewPager);

        //Custom method to set icons for the tabs
        setupTabIcons();

        if (savedInstanceState != null) {
            mFragmentsViewPager.setCurrentItem(savedInstanceState.getInt("currentItem", 0));
        }

        //If the Home Activity was started using the app shortcut with the intent action then it is detected here and
        // accordingly the viewpager position is set to open Events tab for example
        try {
            if (getString(R.string.shortcut_events).compareTo(getIntent().getAction()) == 0) {
                mFragmentsViewPager.setCurrentItem(2);
                log("Changing page to Events");
            } else if (getString(R.string.shortcut_utilities).compareTo(getIntent().getAction()) == 0) {
                mFragmentsViewPager.setCurrentItem(3);
                log("Changing to Utilities");
            } else {
                if (getIntent().getAction() != null) {
                    log(getIntent().getAction());
                } else {
                    log("Got nothing");
                }
            }
        } catch (Exception e) {
            log("error in comparing");
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        mEditor = mPreferences.edit();

        mDrawerLayout.addDrawerListener(toggle);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mFragmentsViewPager.requestFocus();
        boolean alarmUp = (PendingIntent.getBroadcast(this, ALARM_RECEIVER_REQUEST_CODE,
                new Intent(ALARM_RECEIVER_ACTION_UPDATE),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp) {
            DHC.e(TAG, "Alarm is already active");
        } else DHC.e(TAG, "Not set");


    }

    @Override
    protected void onStart() {
        super.onStart();
        mUIListener = returnImageChangeListener();
        mUIReference.addListenerForSingleValueEvent(mUIListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mUIReference.removeEventListener(mUIListener);
        mEditor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE).getBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), true))
            scheduleAlarmForUpdateService();
        mDatabase.close();
    }

    @Override
    protected void onSaveInstanceState(final Bundle outState) {
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
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        if (mFragmentsViewPager.getCurrentItem() == MAIN_ACTIVITY_HERALD_POS)
            menu.findItem(R.id.action_refresh_herald).setVisible(true);
        else menu.findItem(R.id.action_refresh_herald).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
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
            if (!UpdateCheckerService.isInstanceCreated()) {
                Intent intent = new Intent(this, UpdateCheckerService.class);
                startService(intent);
                DHC.makeCustomSnackbar(mFragmentsViewPager, "Starting update checker",
                        getPrimaryColorFromTheme(), Color.WHITE).show();
            } else {
                DHC.makeCustomSnackbar(mFragmentsViewPager, "Still checking for updates", getPrimaryColorFromTheme(),
                        Color.WHITE).show();
            }
        } else if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem item) {
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
                    .setToolbarColor(getPrimaryColorFromTheme())
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
                    .setToolbarColor(getPrimaryColorFromTheme())
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
        } else if (id == R.id.nav_main_about_us) {
            Intent intent = new Intent(this, AboutUsActivity.class);
            startActivity(intent);
        }


        androidx.drawerlayout.widget.DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(START);

        return false;
    }

    private int getPrimaryColorFromTheme() {
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

    /**
     * Setup both viewpagers, one for main fragments and the other for the slideshow.
     */
    private void setupViewPagers() {

        mDatabase = Realm.getDefaultInstance();

        //Setup up main Viewpager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        HeraldFragment heraldFragmentFragment = (HeraldFragment) HeraldFragment.newInstance();
        Gazettes gazettesFragment = (Gazettes) Gazettes.newInstance();
        EventsFragment eventsFragmentFragments = (EventsFragment) EventsFragment.newInstance();
        Utilities utilitiesFragment = (Utilities) Utilities.newInstance();
        IssuesFragment issuesFragment = (IssuesFragment) IssuesFragment.newInstance();
        favouritesfragment favouritesFragment = (favouritesfragment) favouritesfragment.newInstance();

        eventsFragmentFragments.setOnTitleUpdateListener(this);

        adapter.addFragment(heraldFragmentFragment, "Herald", MAIN_ACTIVITY_HERALD_POS);
        adapter.addFragment(gazettesFragment, "Gazettes", MAIN_ACTIVITY_GAZETTES_POS);
        adapter.addFragment(favouritesFragment, "Favourites", MAIN_ACTIVITY_FAVOURITES_POS);
        adapter.addFragment(issuesFragment, "Issues",MAIN_ACTIVITY_ISSUES_POS);
        adapter.addFragment(eventsFragmentFragments, "Events(" + mDatabase.where(EventItem.class).findAll().size() + ")", MAIN_ACTIVITY_EVENTS_POS);
        adapter.addFragment(utilitiesFragment, "Utilities", MAIN_ACTIVITY_UTILITIES_POS);

        mFragmentsViewPager.setAdapter(adapter);
        //Tablyout setup is called only after the viewpager is ready
        mFragmentsTabLayout.setupWithViewPager(mFragmentsViewPager);

        mSlideshowItems = new RealmList<>();
        //Setup Slideshow viewpager
        mSlideshowItems.addAll(mDatabase.where(SlideshowItem.class).findAll());
        mSlideshowPagerAdapter = new SlideshowPagerAdapter(mSlideshowItems);
        mSlideShowViewPager.setPageTransformer(false, new StackTransformer());
        mSlideShowViewPager.setAdapter(mSlideshowPagerAdapter);

    }



    public void scheduleAlarmForUpdateService() {

        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(ALARM_RECEIVER_ACTION_UPDATE);
        // Create a PendingIntent to be triggered when the alarm goes off
        PendingIntent pIntent = PendingIntent.getBroadcast(this, ALARM_RECEIVER_REQUEST_CODE,
                intent, FLAG_UPDATE_CURRENT);

        long days = 5;
        long hoursInADay = 24;
        long minutesInAnHour = 60;
        long secondsInAMinute = 60;
        long millsInASecond = 1000;

        long initialDelay = days * hoursInADay * minutesInAnHour * secondsInAMinute * millsInASecond;

        long firstMillis = System.currentTimeMillis() + initialDelay; // alarm is set 5 days away
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
            public void onDataChange(final DataSnapshot dataSnapshot) {

                DataSnapshot navBarTitleSnapshot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_NAVBAR_TITLE);
                DataSnapshot navBarImageUrlSnapshot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_NAVBAR_IMAGE_URL);

                if (navBarTitleSnapshot.exists()) {
                    String navBarTitle = navBarTitleSnapshot.getValue(String.class);
                    mEditor.putString(USER_PREFERENCES_NAVBAR_TITLE, navBarTitle);
                }
                if (navBarImageUrlSnapshot.exists()) {
                    String navBarImageUrl = navBarImageUrlSnapshot.getValue(String.class);
                    mEditor.putString(USER_PREFERENCES_NAVBAR_IMAGE_URL, navBarImageUrl);
                }
                mEditor.apply();

                mNavBarTitle.setText(mPreferences.getString(USER_PREFERENCES_NAVBAR_TITLE, DoJMA));
                mNavBarImage.setImageURI(mPreferences.getString(USER_PREFERENCES_NAVBAR_IMAGE_URL, ""));


                DataSnapshot toolbarSnapShot = dataSnapshot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR);
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(SlideshowItem.class);
                    }
                });

                for (DataSnapshot childShot : toolbarSnapShot.getChildren()) {
                    final String title, subtitle, imageUrl;
                    if (childShot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_TITLE).exists())
                        title = childShot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_TITLE).getValue(String.class);
                    else title = "";
                    if (childShot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_SUBTITLE).exists())
                        subtitle = childShot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_SUBTITLE).getValue(String.class);
                    else subtitle = "";
                    if (childShot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_IMAGE_URL).exists()) {
                        imageUrl = childShot.child(FIREBASE_DATABASE_REFERENCE_TOOLBAR_IMAGE_URL).getValue(String.class);
                        mDatabase.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                SlideshowItem foo = realm.createObject(SlideshowItem.class);
                                foo.setTitle(title);
                                foo.setSubTitle(subtitle);
                                foo.setImageUrl(imageUrl);
                            }
                        });
                    }
                }
                mSlideshowItems.clear();
                mSlideshowItems.addAll(mDatabase.where(SlideshowItem.class).findAll());
                mSlideshowPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                DHC.e(TAG, "database error " + databaseError.getMessage() + " " + databaseError.getDetails());
            }
        };
    }

    /**
     * An interface callback that notifies if the title of a tab needs to be updated.
     *
     * @param title Updated title
     * @param pos   Position to update
     */
    @Override
    public void onTitleUpdate(final String title, final int pos) {
        mFragmentsTabLayout.getTabAt(pos).setText(title);
    }
}
