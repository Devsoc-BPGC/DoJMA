package com.csatimes.dojma;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;

import com.alexvasilkov.android.commons.state.InstanceState;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.commons.DepthPageTransformer;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class ImagesAndMedia extends AppCompatActivity implements ImageGalleryAdapter.OnPhotoListener {
    private static final int NO_POSITION = -1;
    RealmList<HeraldNewsItemFormat> realmList;
    RealmResults<HeraldNewsItemFormat> realmResults;
    Realm database;
    RealmConfiguration realmConfiguration;
    private ImageGalleryAdapter gridAdapter;
    private PhotoPagerAdapter pagerAdapter;
    private ViewPager.OnPageChangeListener pagerListener;
    private GestureSettingsMenu settingsMenu;
    private ViewHolder views;
    @InstanceState
    private int savedPagerPosition = NO_POSITION;
    @InstanceState
    private int savedGridPosition = NO_POSITION;
    @InstanceState
    private int savedGridPositionFromTop;
    @InstanceState
    private int savedPhotoCount;
    private boolean isViewPagerVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_and_media);

        views = new ViewHolder(this);

        setSupportActionBar(views.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //image settings setup
        settingsMenu = new GestureSettingsMenu();
        settingsMenu.onRestoreInstanceState(savedInstanceState);


        //realm setup
        realmConfiguration = new RealmConfiguration.Builder(this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();

        realmResults = database.where(HeraldNewsItemFormat.class).notEqualTo("url", "false")
                .notEqualTo("bigImageUrl", "false")
                .findAllSorted("originalDate", Sort.DESCENDING);
        realmList = new RealmList<>();
        realmList.addAll(realmResults);


        initGrid();
        initPager();

    }

    private void initGrid() {

        //Setup columns according to device screen
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up images grid
        float t = dpWidth / 150;
        float r = dpWidth % 150;
        int cols = 0;
        if (r > 50f)
            cols = (int) Math.ceil(dpWidth / 150);
        else
            cols = (int) t;


        views.grid.setLayoutManager(new GridLayoutManager(this, cols));
        views.grid.setItemAnimator(new DefaultItemAnimator());

        gridAdapter = new ImageGalleryAdapter(this, realmList, this);

        views.grid.setAdapter(gridAdapter);
    }

    private void initPager() {
        // Setting up pager views
        pagerAdapter = new PhotoPagerAdapter(this, views.pager, realmList);
        pagerAdapter.setSetupListener(settingsMenu);

        pagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                onPhotoInPagerSelected(position);
            }
        };

        views.pager.setAdapter(pagerAdapter);
        views.pager.addOnPageChangeListener(pagerListener);
        views.pager.setPageTransformer(true, new DepthPageTransformer());

        views.pagerToolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        views.pagerToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                onBackPressed();
            }
        });

    }

    private void onPhotoInPagerSelected(int position) {

    }

    @Override
    public void onPhotoClick(int position) {
        pagerAdapter.setActivated(true);
        isViewPagerVisible = true;
        views.pager.setVisibility(View.INVISIBLE);
        views.pager.setCurrentItem(position, false);
        views.toolbarBack.setVisibility(View.VISIBLE);
        views.pagerToolbar.setVisibility(View.VISIBLE);
        views.appBarLayout.setVisibility(View.INVISIBLE);
        views.pagerBackground.setVisibility(View.VISIBLE);
        views.pager.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        settingsMenu.onSaveInstanceState(outState);
        saveScreenState();
        super.onSaveInstanceState(outState);
        clearScreenState(); // We don't want to restore state if activity instance is not destroyed
    }

    @Override
    public void onBackPressed() {
        if (isViewPagerVisible) {
            pagerAdapter.setActivated(false);
            isViewPagerVisible = true;
            views.pager.setVisibility(View.GONE);
            views.appBarLayout.setVisibility(View.VISIBLE);
            views.toolbarBack.setVisibility(View.GONE);
            views.pagerToolbar.setVisibility(View.GONE);
            views.pagerBackground.setVisibility(View.GONE);
            views.pager.setVisibility(View.GONE);
            isViewPagerVisible = false;
        } else {
            super.onBackPressed();
        }
    }

    private void saveScreenState() {
        clearScreenState();

        savedPhotoCount = gridAdapter.getItemCount();

        savedPagerPosition = pagerAdapter.getCount() == 0
                ? NO_POSITION : views.pager.getCurrentItem();

        if (views.grid.getChildCount() > 0) {
            View child = views.grid.getChildAt(0);
            savedGridPosition = views.grid.getChildAdapterPosition(child);
            savedGridPositionFromTop = child.getTop()
                    - Views.getMarginParams(child).topMargin
                    - views.grid.getPaddingTop();
        }
    }

    private void clearScreenState() {
        savedPhotoCount = 0;
        savedPagerPosition = NO_POSITION;
        savedGridPosition = NO_POSITION;
        savedGridPositionFromTop = 0;
    }


    private class ViewHolder {
        final Toolbar toolbar;
        final View toolbarBack;
        final RecyclerView grid;
        final AppBarLayout appBarLayout;
        final ViewPager pager;
        final Toolbar pagerToolbar;
        // final TextView pagerTitle;
        final View pagerBackground;

        ViewHolder(Activity activity) {
            toolbar = Views.find(activity, R.id.images_toolbar);
            toolbarBack = Views.find(activity, R.id.advanced_toolbar_back);
            grid = Views.find(activity, R.id.images_recyclerView_grid);
            appBarLayout = Views.find(activity, R.id.images_appbar);
            pager = Views.find(activity, R.id.advanced_view_pager);
            pagerToolbar = Views.find(activity, R.id.advanced_full_toolbar);
            // pagerTitle = Views.find(activity, R.id.advanced_full_title);
            pagerBackground = Views.find(activity, R.id.advanced_full_background);
        }
    }
}
