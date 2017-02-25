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
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
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
import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.view.WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS;
import static android.view.WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
import static com.csatimes.dojma.utilities.DHC.ALARM_RECEIVER_ACTION_UPDATE;
import static com.csatimes.dojma.utilities.DHC.ALARM_RECEIVER_REQUEST_CODE;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnTitleUpdateListener,
        AppBarLayout.OnOffsetChangedListener {


    public final String TAG = "activities." + MainActivity.class.getSimpleName();

    private boolean landscape = false;

    private Toolbar toolbar;
    private View fade_backdrop;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout drawer;
    private TextView navBarTitle;
    private AppBarLayout appBarLayout;
    private SimpleDraweeView navBarImage;
    private ValueEventListener navBarListener;
    private SimpleDraweeView toolbarBackground;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private DatabaseReference navBarRef = FirebaseDatabase.getInstance().getReference().child("navbar");

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        DHC.log(TAG, FirebaseInstanceId.getInstance().getToken());
        SharedPreferences preferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);

        if (preferences.getBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), true)) {
            Intent intent = new Intent(this, POSTDownloaderActivity.class);
            startActivity(intent);
            finish();
        }

        landscape = getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE;
        setContentView(R.layout.activity_home);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.app_bar_home_toolbar);
        tabLayout = (TabLayout) findViewById(R.id.app_bar_home_tabs);
        fade_backdrop = findViewById(R.id.app_bar_home_toolbar_back_drop);
        viewPager = (ViewPager) findViewById(R.id.app_bar_home_viewpager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_home_appbar_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navBarTitle = (TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_bar_title);
        toolbarBackground = (SimpleDraweeView) findViewById(R.id.app_bar_home_toolbar_background);
        navBarImage = (SimpleDraweeView) navigationView.getHeaderView(0).findViewById(R.id.nav_bar_image);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.app_bar_home_collapsing_toolbar);

        setSupportActionBar(toolbar);

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

        setupViewPager(viewPager);
        //Tablyout setup is called only after the viewpager is ready
        tabLayout.setupWithViewPager(viewPager);

        //Custom method to set icons for the tabs
        setupTabIcons();

        if (savedInstanceState != null) {
            viewPager.setCurrentItem(savedInstanceState.getInt("currentItem", 0));
        }

        //If the Home Activity was started using the app shortcut with the intent action then it is detected here and
        // accordingly the viewpager position is set to open Events tab for example
        if (getString(R.string.shortcut_events).equals(getIntent().getAction())) {
            viewPager.setCurrentItem(2);
        } else if (getString(R.string.shortcut_utilities).equals(getIntent().getAction())) {
            viewPager.setCurrentItem(3);
        }


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        navBarListener = returnImageChangeListener();
        navBarRef.addValueEventListener(navBarListener);
        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewPager.requestFocus();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        navBarRef.removeEventListener(navBarListener);
        if (!getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE).getBoolean(getString(R.string.USER_PREFERENCES_FIRST_INSTALL), true))
            scheduleAlarmForUpdateService();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentItem", viewPager.getCurrentItem());
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
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
            {
                Intent intent = new Intent((Intent.ACTION_SEND));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "https://issuu.com/bitsherald");

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
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + getPackageName()));
                                startActivity(intent);
                            }
                        });
            }
        } else if (id == R.id.nav_main_share) {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.message_app_share));
            intent.setType("text/plain");
            startActivity(Intent.createChooser(intent, "Share app url via ... "));

        } else if (id == R.id.nav_main_favourites) {
            Intent intent = new Intent(MainActivity.this, FavouritesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_gallery) {
            Intent intent = new Intent(this, ImagesAndMediaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_about) {
            Intent intent = new Intent(MainActivity.this, AboutDojmaActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_fb) {

            try {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL();
                facebookIntent.setData(Uri.parse(facebookUrl));
                startActivity(facebookIntent);

            } catch (Exception e) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(DHC.DoJMA_FACEBOOK_URL));
                startActivity(intent);
            }
        } else if (id == R.id.nav_main_lcd) {
            Intent intent = new Intent((Intent.ACTION_SEND));
            intent.putExtra(android.content.Intent.EXTRA_TEXT, DHC.BITS_GOA_LCD_LINK);

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
                    Uri.parse(DHC.BITS_GOA_LCD_LINK),
                    new CustomTabActivityHelper.CustomTabFallback() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void openUri(Activity activity, Uri uri) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                        }
                    });
        } else if (id == R.id.nav_main_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

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
            tabLayout.getTabAt(0).setIcon(R.drawable.ic_chrome_reader_mode_white_24dp);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_picture_in_picture_white_24dp);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_event_note_white_24dp);
            tabLayout.getTabAt(3).setIcon(R.drawable.ic_local_convenience_store_white_24dp);
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

        adapter.addFragment(heraldFragment, "Herald", DHC.MAIN_ACTIVITY_HERALD_POS);
        adapter.addFragment(gazettesFragment, "Gazettes", DHC.MAIN_ACTIVITY_GAZETTES_POS);
        adapter.addFragment(eventsFragments, "Events(" + database.where(EventItem.class).findAll().size() + ")", DHC.MAIN_ACTIVITY_EVENTS_POS);
        adapter.addFragment(utilitiesFragment, "Utilities", DHC.MAIN_ACTIVITY_UTILITIES_POS);

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
                return "fb://facewebmodal/f?href=" + DHC.DoJMA_FACEBOOK_URL;
            } else { //older versions of fb app
                return "fb://page/" + DHC.DoJMA_FACEBOOK_PAGE_ID;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return DHC.DoJMA_FACEBOOK_URL; //normal web url
        }
    }

    private ValueEventListener returnImageChangeListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Uri uri = Uri.parse(dataSnapshot.child("image").getValue(String.class));
                ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                        .setProgressiveRenderingEnabled(true)
                        .build();
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(navBarImage.getController())
                        .build();
                navBarImage.setController(controller);

                controller = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(toolbarBackground.getController())
                        .build();
                toolbarBackground.setController(controller);

                navBarTitle.setText(dataSnapshot.child("title").getValue(String.class));
                toolbar.setTitle(navBarTitle.getText());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log("database error " + databaseError.getMessage());
            }
        };
    }

    @Override
    public void onTitleUpdate(String title, int pos) {
        tabLayout.getTabAt(pos).setText(title);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        toolbarBackground.setImageAlpha(255 * (appBarLayout.getTotalScrollRange() + verticalOffset) / appBarLayout.getTotalScrollRange());
    }
}
