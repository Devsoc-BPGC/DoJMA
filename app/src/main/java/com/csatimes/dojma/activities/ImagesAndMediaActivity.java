package com.csatimes.dojma.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ImageGalleryAdapter;
import com.csatimes.dojma.adapters.PhotoPagerAdapter;
import com.csatimes.dojma.models.PosterItem;
import com.csatimes.dojma.utilities.GestureSettingsMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.realm.Realm;
import io.realm.RealmList;

public class ImagesAndMediaActivity extends AppCompatActivity implements ImageGalleryAdapter.OnPhotoListener {

    boolean mIsViewPagerVisible = false;

    ImageGalleryAdapter mGridAdapter;
    PhotoPagerAdapter mPagerAdapter;
    GestureSettingsMenu mGestureSettingsMenu;
    ViewHolder views;
    RealmList<PosterItem> mPosterItems;
    Realm mDatabase;
    ValueEventListener mPosterValueEventListener;

    DatabaseReference mPostersReference = FirebaseDatabase.getInstance().getReference().child("posters");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_and_media);

        views = new ViewHolder(this);

        setSupportActionBar(views.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //image settings setup
        mGestureSettingsMenu = new GestureSettingsMenu();
        mGestureSettingsMenu.onRestoreInstanceState(savedInstanceState);

        mDatabase = Realm.getDefaultInstance();
        mPosterItems = new RealmList<>();
        mPosterItems.addAll(mDatabase.where(PosterItem.class).findAll());

        initGrid();
        initPager();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mPosterValueEventListener = returnPostersListener();
        mPostersReference.addValueEventListener(mPosterValueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPostersReference.removeEventListener(mPosterValueEventListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

    private void initGrid() {

        //Setup columns according to device screen
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up images grid
        float t = dpWidth / 150;
        float r = dpWidth % 150;
        int cols;
        if (r > 50f)
            cols = (int) Math.ceil(dpWidth / 150);
        else
            cols = (int) t;

        views.grid.setLayoutManager(new GridLayoutManager(this, cols));

        mGridAdapter = new ImageGalleryAdapter(this, mPosterItems, this);

        views.grid.setAdapter(mGridAdapter);
    }

    private void initPager() {
        // Setting up pager views
        mPagerAdapter = new PhotoPagerAdapter(views.pager, mPosterItems);
        mPagerAdapter.setSetupListener(mGestureSettingsMenu);

        ViewPager.OnPageChangeListener pagerListener = new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                onPhotoInPagerSelected(position);
            }
        };

        views.pager.setAdapter(mPagerAdapter);
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

    private ValueEventListener returnPostersListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Delete old data
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(PosterItem.class);
                    }
                });
                mPosterItems.clear();
                for (DataSnapshot childShot : dataSnapshot.getChildren()) {
                    try {
                        mPosterItems.add(childShot.getValue(PosterItem.class));
                        mDatabase.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                PosterItem pi = realm.createObject(PosterItem.class);
                                pi.setTitle(mPosterItems.get(mPosterItems.size() - 1).getTitle());
                                pi.setUrl(mPosterItems.get(mPosterItems.size() - 1).getUrl());
                            }
                        });
                    } catch (Exception e) {
                        Log.e("TAG", "Poster parse exception");
                    }
                }
                if (mPosterItems.size() == 0) {
                    views.noPostersText.setVisibility(View.VISIBLE);
                } else {
                    views.noPostersText.setVisibility(View.GONE);
                }
                mGridAdapter.notifyDataSetChanged();
                mPagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    private void onPhotoInPagerSelected(int position) {

    }

    @Override
    public void onPhotoClick(int position) {
        mPagerAdapter.setActivated(true);
        mIsViewPagerVisible = true;
        views.pager.setVisibility(View.INVISIBLE);
        views.pager.setCurrentItem(position, false);
        views.toolbarBack.setVisibility(View.VISIBLE);
        views.pagerToolbar.setVisibility(View.VISIBLE);
        views.appBarLayout.setVisibility(View.INVISIBLE);
        views.pagerBackground.setVisibility(View.VISIBLE);
        views.pager.setVisibility(View.VISIBLE);
        views.noPostersText.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mGestureSettingsMenu.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (mIsViewPagerVisible) {
            mPagerAdapter.setActivated(false);
            mIsViewPagerVisible = true;
            views.pager.setVisibility(View.GONE);
            views.appBarLayout.setVisibility(View.VISIBLE);
            views.toolbarBack.setVisibility(View.GONE);
            views.pagerToolbar.setVisibility(View.GONE);
            views.pagerBackground.setVisibility(View.GONE);
            views.pager.setVisibility(View.GONE);
            mIsViewPagerVisible = false;
        } else {
            super.onBackPressed();
        }
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
        final TextView noPostersText;

        ViewHolder(Activity activity) {
            toolbar = Views.find(activity, R.id.images_toolbar);
            toolbarBack = Views.find(activity, R.id.advanced_toolbar_back);
            grid = Views.find(activity, R.id.images_recyclerView_grid);
            appBarLayout = Views.find(activity, R.id.images_appbar);
            pager = Views.find(activity, R.id.advanced_view_pager);
            pagerToolbar = Views.find(activity, R.id.advanced_full_toolbar);
            // pagerTitle = Views.find(activity, R.id.advanced_full_title);
            noPostersText = Views.find(activity, R.id.noPostersText);
            pagerBackground = Views.find(activity, R.id.advanced_full_background);
        }
    }

}
