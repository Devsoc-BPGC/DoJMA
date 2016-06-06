package com.csatimes.dojma;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    public static int NUMBEROFPAGES = 4;
    public static int PAGENUMBER = 0;
    public static boolean appStarted = true;
    public static String USER_PREFERENCES = "USER_PREFS";
    private static int[] pageColors = new int[NUMBEROFPAGES];
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



    // DP <-> PX static converter method
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    static int blendColors(int from, int to, float ratio) {
        final float inverseRation = 1f - ratio;
        final float r = Color.red(from) * ratio + Color.red(to) * inverseRation;
        final float g = Color.green(from) * ratio + Color.green(to) * inverseRation;
        final float b = Color.blue(from) * ratio + Color.blue(to) * inverseRation;
        return Color.rgb((int) r, (int) g, (int) b);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("TAG", "Home activity paused");
        editor.apply();

    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.e("TAG", "Home activity stopped");
        editor.putBoolean("AppStarted", false);
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        preferences = getApplicationContext().getSharedPreferences(USER_PREFERENCES, MODE_PRIVATE);
        editor = preferences.edit();

        //If app has been installed for the first time download the articles and their data
        //and then change shared preferences key "FIRST_TIME_INSTALL"
        if (preferences.getBoolean("FIRST_TIME_INSTALL", true)) {
            Intent startFirstTimeDownloader = new Intent(this, DownloadForFirstTimeActivity.class);
            startActivity(startFirstTimeDownloader);
            // finish();
        }
        editor.putBoolean("AppStarted", true);
        editor.apply();

        View activityHomeView = findViewById(R.id.tabs);
        toolbarObject = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbarObject);

        //reference fab button
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //setup pagecolors
        // pageColors = new int[NUMBEROFPAGES];
        pageColors[0] = ContextCompat.getColor(HomeActivity.this, R.color.colorPrimary);
        pageColors[1] = ContextCompat.getColor(HomeActivity.this, R.color.green500);
        pageColors[2] = ContextCompat.getColor(HomeActivity.this, R.color.quora_primary);
        pageColors[3] = ContextCompat.getColor(HomeActivity.this, R.color.facebook_primary);

        //setup fab colors
        fabColors = new int[NUMBEROFPAGES];
        fabColors[0] = ContextCompat.getColor(HomeActivity.this, R.color.colorAccent);
        fabColors[1] = ContextCompat.getColor(HomeActivity.this, R.color.lightblue500);
        fabColors[2] = ContextCompat.getColor(HomeActivity.this, R.color.orangeA400);
        fabColors[3] = ContextCompat.getColor(HomeActivity.this, R.color.yellowA400);


        //setup fab icons
        fabIcons = new Drawable[NUMBEROFPAGES];
        fabIcons[0] = ContextCompat.getDrawable(HomeActivity.this, R.drawable.ic_refresh_white_24dp);
        fabIcons[1] = ContextCompat.getDrawable(HomeActivity.this, R.drawable
                .ic_picture_in_picture_white_24dp);
        fabIcons[2] = ContextCompat.getDrawable(HomeActivity.this, R.drawable
                .ic_local_convenience_store_white_24dp);
        fabIcons[3] = ContextCompat.getDrawable(HomeActivity.this, R.drawable
                .ic_edit_white_24dp);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);


        // finally change the color

        //If on starting the app, no internet connection is available, error Snackbar is
        // shown.
        // If on clicking fab button, no internet is there, Custom SimpleAlertDialog is generated
        if (!isOnline()) {
            Snackbar.make(activityHomeView, "No Internet. Can't check for updates.",
                    Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }


        viewPager = (ViewPager) findViewById(R.id.viewpager);
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
                PAGENUMBER = tab.getPosition();
                toolbarObject.setBackgroundColor(pageColors[PAGENUMBER]);
                tabLayout.setBackgroundColor(pageColors[PAGENUMBER]);
                if (Build.VERSION.SDK_INT >= 21) {
                    window.setStatusBarColor(pageColors[PAGENUMBER]);
                    window.setNavigationBarColor(pageColors[PAGENUMBER]);
                }
                fab.setBackgroundTintList(ColorStateList.valueOf(fabColors[PAGENUMBER]));
                fab.setImageDrawable(fabIcons[PAGENUMBER]);
                viewPager.setCurrentItem(PAGENUMBER);
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
                if (position < NUMBEROFPAGES - 1)
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
                if (position == NUMBEROFPAGES - 1) {

                    toolbarObject.setBackgroundColor(pageColors[3]);
                    tabLayout.setBackgroundColor(pageColors[3]);
                    if (Build.VERSION.SDK_INT >= 21) {
                        window.setStatusBarColor(pageColors[3]);
                        window.setNavigationBarColor(pageColors[3]);
                    }
                }


                if (position <= NUMBEROFPAGES - 2) {
                    // Retrieve the current and next ColorFragment
                    final int from = pageColors[position];
                    final int to = pageColors[position + 1];
                    // Blend the colors and adjust the ActionBar
                    final int blended = blendColors(to, from, positionOffset);

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

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_chrome_reader_mode_white_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_picture_in_picture_white_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_local_convenience_store_white_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_event_note_white_24dp);
    }

    //Setting up the View Pager with the fragments
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settings = new Intent(HomeActivity.this, Settings.class);
            settings.putExtra("pageColor", pageColors[PAGENUMBER]);
            startActivity(settings);

        } else if (id == R.id.action_about_us) {
            SimpleAlertDialog sad = new SimpleAlertDialog();
            sad.showDialog(this, "About", "This app has been created for DoJMA by the Mobile App " +
                            "Club(MAC). If you find any issues with the app please report in the " +
                            "Suggestions Page\n" +
                            " Check " +
                            "out our " +
                            "Google Play page for more apps.", "OK", "", true,
                    false);
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
            Intent suggestFeature = new Intent(HomeActivity.this, SuggestFeature.class);
            startActivity(suggestFeature);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * isOnline - Check if there is a NetworkConnection
     * @return boolean
     */
    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

}
