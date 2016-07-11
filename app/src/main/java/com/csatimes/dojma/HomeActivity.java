package com.csatimes.dojma;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.miguelcatalan.materialsearchview.MaterialSearchView;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static boolean appStarted = true;
    private static int[] pageColors = new int[DHC.NUMBEROFPAGES];
    public FloatingActionButton fab;
    private Toolbar toolbarObject;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Animation animation;
    private Window window;
    private int[] fabColors;
    private Drawable[] fabIcons;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private NavigationView navigationView;
    private Realm database;
    private RealmConfiguration realmConfiguration;
    private ImageView nav_bar_background;
    private Tracker mTracker;
    private RealmResults<HeraldNewsItemFormat> results;
    private MaterialSearchView searchView;
    private ViewPagerAdapter adapter;
    private boolean isGoogleChromeInstalled;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LeakCanary.install(getApplication());

        preferences = this.getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);

        //If app has been isGoogleChromeInstalled for the first time download the articles and their data
        //and then change shared preferences key "FIRST_TIME_INSTALL"
        //This key is saved in strings.xml to avoid confusion
        //It is called using android method getString(resID)

        if (preferences.getBoolean(getString(R.string.SP_first_install), true)) {
            Intent startFirstTimeDownloader = new Intent(this, DownloadForFirstTimeActivity.class);
            startActivity(startFirstTimeDownloader);
            finish();
        }

        setContentView(R.layout.activity_home);


        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Home Activity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        editor = preferences.edit();
        editor.putBoolean("APP_STARTED", true);

        isGoogleChromeInstalled = appInstalledOrNot("com.android.chrome");
        if (isGoogleChromeInstalled)
            editor.putBoolean(getString(R.string.SP_chrome_install_status), true);
        else editor.putBoolean(getString(R.string.SP_chrome_install_status), false);

        editor.apply();

        View activityHomeView = findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        toolbarObject = (Toolbar) findViewById(R.id.offline_toolbar);
        setSupportActionBar(toolbarObject);

        //reference fab button
        fab = (FloatingActionButton) findViewById(R.id.fab);


        //Set the latest topic in the navigation bar
        //custom method
        setLatestTopicAsNavBarTitle();

        setupColors();

        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        searchView = (MaterialSearchView) findViewById(R.id.material_search_view);
        searchView.setCursorDrawable(R.drawable.cursor_material_search);
        searchView.setVoiceSearch(true);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Do some magic
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //Do some magic
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
        // finally change the color

        //If on starting the app, no internet connection is available, error Snackbar is
        // shown.
        // If on clicking fab button, no internet is there, Custom SimpleAlertDialog is generated
        if (!isOnline()) {
            Snackbar.make(activityHomeView, "No Internet. Can't check for updates.",
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }


        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //Custom method to set icons for the tabs
        setupTabIcons();


        //Listeners

        // tabLayout listenener
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                DHC.PAGENUMBER = tab.getPosition();
                if (DHC.PAGENUMBER == 0) ;
                toolbarObject.setBackgroundColor(pageColors[DHC.PAGENUMBER]);
                tabLayout.setBackgroundColor(pageColors[DHC.PAGENUMBER]);
                if (Build.VERSION.SDK_INT >= 21) {
                    window.setStatusBarColor(pageColors[DHC.PAGENUMBER]);
                    window.setNavigationBarColor(pageColors[DHC.PAGENUMBER]);
                }
                fab.setBackgroundTintList(ColorStateList.valueOf(fabColors[DHC.PAGENUMBER]));
                fab.setImageDrawable(fabIcons[DHC.PAGENUMBER]);
                viewPager.setCurrentItem(DHC.PAGENUMBER);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    //check for updates
                    //and refresh fragment
                    new SimpleAlertDialog().showDialog(HomeActivity.this, "Message", "details",
                            "Ok", "", true, false);

                    if (!UpdateCheckerService.isInstanceCreated()) {
                        final Intent intent = new Intent(HomeActivity.this, UpdateCheckerService.class);
                        UpdateCheckerService.stop = false;
                        startService(intent);
                        Snackbar.make(v, "Checking for updates ... ", Snackbar.LENGTH_LONG)
                                .setAction("Cancel", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (UpdateCheckerService.isInstanceCreated())
                                            UpdateCheckerService.stop = true;
                                    }
                                }).show();
                    }
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    //gazette
                } else if (tabLayout.getSelectedTabPosition() == 2) {
                    //campus watch
                } else if (tabLayout.getSelectedTabPosition() == 3) {
                    //events
                }

            }
        });
        //ratio for fab animation
        //strictly lying in [0,1)
        //galti se bhi 1 mat bharna
        final float r = 0.6f;

        //set animation
        animation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.fab_anim);

        //fab icon ,color changes,tabs,toolbar color changes are defines here
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                //need to optimise code by moving the setImageDrawable to other method
                //will do later

                //the following is for scale change animation of the fab on change of fragment
                // change ratio above
                if (position < DHC.NUMBEROFPAGES - 1)
                    if (positionOffset < r) {
                        fab.setBackgroundTintList(ColorStateList.valueOf(fabColors[position]));
                        fab.setImageDrawable(fabIcons[position]);
                        fab.setScaleX(1 - positionOffset);
                        fab.setScaleY(1 - positionOffset);
                    } else if (positionOffset >= r && positionOffset < 1) {
                        fab.setImageDrawable(fabIcons[position + 1]);
                        fab.setBackgroundTintList(ColorStateList.valueOf(fabColors[position + 1]));
                        fab.setScaleX((r / (1 - r)) * (positionOffset - r) + (1 - r));
                        fab.setScaleY((r / (1 - r)) * (positionOffset - r) + (1 - r));

                    }

                //following is blending of toolbar/statusbar/system bar colors given in
                // pageColors array

                //special color change check for last page to avoid
                //ArrayOutOfBoundsException
                if (position == DHC.NUMBEROFPAGES - 1) {

                    toolbarObject.setBackgroundColor(pageColors[3]);
                    tabLayout.setBackgroundColor(pageColors[3]);
                    if (Build.VERSION.SDK_INT >= 21) {
                        window.setStatusBarColor(pageColors[3]);
                        window.setNavigationBarColor(pageColors[3]);
                    }
                }


                if (position <= DHC.NUMBEROFPAGES - 2) {
                    // Retrieve the current and next ColorFragment
                    final int from = pageColors[position];
                    final int to = pageColors[position + 1];
                    // Blend the colors and adjust the ActionBar
                    final int blended = DHC.blendColors(to, from, positionOffset);

                    toolbarObject.setBackgroundColor(blended);
                    tabLayout.setBackgroundColor(blended);
                    if (Build.VERSION.SDK_INT >= 21) {
                        window.setStatusBarColor(blended);
                        window.setNavigationBarColor(blended);
                    }
                }

            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarObject, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void setLatestTopicAsNavBarTitle() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView nav_sub_title = (TextView) headerView.findViewById(R.id.navigation_sub_title);
        nav_bar_background = (ImageView) headerView.findViewById(R.id.nav_bar_image);


        realmConfiguration = new RealmConfiguration.Builder(this)
                .name
                        (DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();

        if (database.where(HeraldNewsItemFormat.class).count() != 0) {

            results = database.where(HeraldNewsItemFormat.class)
                    .findAllSorted("originalDate", Sort.DESCENDING);
            nav_bar_background.setImageResource(R.drawable.about_us_gradient);
            RequestQueue queue = Volley.newRequestQueue(this);
            ImageRequest request = new ImageRequest(results.get(0).getImageURL(), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
                    nav_bar_background.setImageBitmap(response);

                }
            }, 0, 0, null, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    nav_bar_background.setImageResource(R.color.colorPrimary);

                }
            });
            queue.add(request);
            nav_sub_title.setText(results.get(0).getTitle());
        }
        database.close();
    }

    private void setupColors() {
        //setup pagecolors
        // pageColors = new int[NUMBEROFPAGES];
        pageColors[0] = ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary);
        pageColors[1] = ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary);
        pageColors[2] = ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary);
        pageColors[3] = ContextCompat.getColor(HomeActivity.this, R.color.facebook_primary);

        //setup fab colors
        fabColors = new int[DHC.NUMBEROFPAGES];
        fabColors[0] = ContextCompat.getColor(HomeActivity.this, R.color.colorAccent);
        fabColors[1] = ContextCompat.getColor(HomeActivity.this, R.color.lightblue500);
        fabColors[2] = ContextCompat.getColor(HomeActivity.this, R.color.green500);
        fabColors[3] = ContextCompat.getColor(HomeActivity.this, R.color.yellowA400);


        //setup fab icons
        fabIcons = new Drawable[DHC.NUMBEROFPAGES];
        fabIcons[0] = ContextCompat.getDrawable(HomeActivity.this, R.drawable.ic_refresh_white_24dp);
        fabIcons[1] = ContextCompat.getDrawable(HomeActivity.this, R.drawable
                .ic_picture_in_picture_white_24dp);
        fabIcons[2] = ContextCompat.getDrawable(HomeActivity.this, R.drawable
                .ic_local_convenience_store_white_24dp);
        fabIcons[3] = ContextCompat.getDrawable(HomeActivity.this, R.drawable
                .ic_edit_white_24dp);
    }


    @Override
    protected void onResume() {
        super.onResume();
        searchView.clearFocus();
        viewPager.requestFocus();
    }

    @Override
    protected void onPause() {
        editor.apply();
        super.onPause();
    }

    @Override
    protected void onStop() {
        editor.putBoolean(getString(R.string.SP_app_started), false);
        editor.apply();

        Intent downloaderBack = new Intent(this, UpdateCheckerService.class);
        startService(downloaderBack);
        /*  final PendingIntent pending = PendingIntent.getService(this, 0, downloaderBack, 0);
        //startService(downloaderBack);
        final AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
        long interval = 30000;//1800000;//milliseconds
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime(), interval, pending);
       */
        super.onStop();
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_chrome_reader_mode_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_picture_in_picture_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_local_convenience_store_white_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_event_note_white_24dp);
    }

    //Setting up the View Pager with the fragments
    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new Herald(), "Herald");
        adapter.addFragment(new Gazette(), "Gazette");
        adapter.addFragment(new CampusWatch(), "Campus Watch");
        adapter.addFragment(new Events(), "Events");

        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);

        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(HomeActivity.this, Settings.class);
            settings.putExtra("pageColor", pageColors[DHC.PAGENUMBER]);
            startActivity(settings);

        } else if (id == R.id.action_about_us) {
            Intent aboutUs = new Intent(HomeActivity.this, AboutUs.class);
            startActivity(aboutUs);
        }

        return super.onOptionsItemSelected(item);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        } else if (id == R.id.nav_idea) {
            Intent intent = new Intent(HomeActivity.this, SuggestFeature.class);
            startActivity(intent);
        } else if (id == R.id.nav_favourites) {
            Intent intent = new Intent(HomeActivity.this, Favourites.class);
            startActivity(intent);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(this, ImagesAndMedia.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * isOnline - Check if there is a NetworkConnection
     * @return boolean
     */
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    public void scheduleAlarmForUpdateService() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(this, AlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_HOUR, pIntent);
    }

    public void cancelAlarm() {
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, AlarmReceiver.REQUEST_CODE,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pIntent);
    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentListTitle = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentListTitle.add(title);

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentListTitle.get(position);
        }
    }

}
