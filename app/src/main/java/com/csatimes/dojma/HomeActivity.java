package com.csatimes.dojma;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.customtabs.CustomTabsIntent;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SharedPreferences.Editor editor;
    private boolean landscape = false;
    private DrawerLayout drawer;

    @Override
    protected void onDestroy() {
        scheduleAlarmForUpdateService();
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("TAG", FirebaseInstanceId.getInstance().getToken() + " ");
        SharedPreferences preferences = this.getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);

        //If app has been isGoogleChromeInstalled for the first time download the articles and their data
        //and then change shared preferences key "FIRST_TIME_INSTALL"
        //This key is saved in strings.xml to avoid confusion
        //It is called using android method getString(resID)
        if (preferences.getBoolean(getString(R.string.SP_first_install), true)) {
            Intent startFirstTimeDownloader = new Intent(this, POSTDownloaderActivity.class);
            startActivity(startFirstTimeDownloader);
            finish();
        }
        if (isOnline() && UpdateCheckerService.instance == null && ImageUrlHandlerService.instance == null)
            startService(new Intent(this, UpdateCheckerService.class));

        landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        setContentView(R.layout.activity_home);

        editor = preferences.edit();
        editor.putBoolean("APP_STARTED", true);

        boolean isGoogleChromeInstalled = appInstalledOrNot("com.android.chrome");
        if (isGoogleChromeInstalled)
            editor.putBoolean(getString(R.string.SP_chrome_install_status), true);
        else editor.putBoolean(getString(R.string.SP_chrome_install_status), false);
        editor.apply();

        View activityHomeView = findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        Toolbar toolbarObject = (Toolbar) findViewById(R.id.offline_toolbar);

        setSupportActionBar(toolbarObject);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        //If on starting the app, no internet connection is available, error Snackbar is
        // shown.
        if (!isOnline()) {
            Snackbar snack = Snackbar.make(activityHomeView, "No Internet. Can't check for updates.", Snackbar.LENGTH_LONG);
            snack.show();
        }


        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (savedInstanceState != null) {
            viewPager.setCurrentItem(savedInstanceState.getInt("currentItem", 0));
        }

        //Of the Home Activity was started using the app shortcut with the intent action then it is detected here and
        // accordingly the viewpager position is set to open Events tab for example
        if (getString(R.string.shortcut_events).equals(getIntent().getAction())) {
            viewPager.setCurrentItem(2);
        } else if (getString(R.string.shortcut_utilities).equals(getIntent().getAction())) {
            viewPager.setCurrentItem(3);
        }

        //Custom method to set icons for the tabs
        setupTabIcons();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbarObject, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
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
        super.onStop();
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

        adapter.addFragment(new Herald(), "Herald");
        adapter.addFragment(new Gazette(), "Gazette");
        adapter.addFragment(new Events(), "Events");
        adapter.addFragment(new Utilities(), "Utilities");

        viewPager.setAdapter(adapter);
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
            Intent intent = new Intent(this, Searchable.class);
            startActivity(intent);
        } else if (id == R.id.action_about_us) {
            Intent aboutUs = new Intent(this, AboutUs.class);
            startActivity(aboutUs);
        } else if (id == R.id.action_about_dojma) {
            Intent intent = new Intent(this, AboutDojma.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_main_issues) {
            Intent intent = new Intent(this, CategoryListView.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_archives) {
            {
                Intent intent = new Intent((Intent.ACTION_SEND));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, "https://issuu.com/bitsherald");

                Intent copy_intent = new Intent(this, CopyLinkBroadcastReceiver.class);
                PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(this, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
                String copy_label = "Copy Link";

                CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                        .setShowTitle(true)
                        .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                        .setCloseButtonIcon(BitmapFactory.decodeResource(this.getResources(),
                                R.drawable.ic_arrow_back_white_24dp))
                        .addMenuItem(copy_label, copy_pendingIntent)
                        .setStartAnimations(this, R.anim.slide_in_right, R.anim.fade_out)
                        .setExitAnimations(this, R.anim.fade_in, R.anim.slide_out_right)
                        .setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.amber500))
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
            Intent intent = new Intent(HomeActivity.this, Favourites.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_gallery) {
            Intent intent = new Intent(this, ImagesAndMedia.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_about) {
            Intent intent = new Intent(HomeActivity.this, AboutDojma.class);
            startActivity(intent);
        } else if (id == R.id.nav_main_fb) {

            try {
                Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
                String facebookUrl = getFacebookPageURL(this);
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
                    PendingIntent.FLAG_UPDATE_CURRENT);
            String copy_label = "Copy Link";

            CustomTabsIntent customTabsIntent = new CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                    .setCloseButtonIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_arrow_back_white_24dp))
                    .addMenuItem(copy_label, copy_pendingIntent)
                    //.setStartAnimations(this, R.anim.slide_in_right, R.anim.fade_out)
                    //.setExitAnimations(this, R.anim.fade_in, R.anim.slide_out_right)
                    .setSecondaryToolbarColor(ContextCompat.getColor(this, R.color.amber500))
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
                            intent.putExtra(Intent.EXTRA_REFERRER, Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + getPackageName()));
                            startActivity(intent);
                        }
                    });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private boolean isOnline() {
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
        // Setup periodic alarm every 1 day
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_DAY, pIntent);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currenItem", viewPager.getCurrentItem());
    }

    //method to get the right URL to use in the intent
    public String getFacebookPageURL(Context context) {
        PackageManager packageManager = context.getPackageManager();
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentListTitle = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
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

        void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentListTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentListTitle.get(position);
        }
    }
}
