package com.csatimes.dojma;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.widget.TextView;

import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.csatimes.dojma.adapters.ImageGalleryAdapter;
import com.csatimes.dojma.models.PosterItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Vector;

public class ImagesAndMedia extends AppCompatActivity implements ImageGalleryAdapter.OnPhotoListener {
    private static final int NO_POSITION = -1;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child
            ("posters");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference().child
            ("images/posters");
    private ImageGalleryAdapter gridAdapter;
    private PhotoPagerAdapter pagerAdapter;
    private ViewPager.OnPageChangeListener pagerListener;
    private GestureSettingsMenu settingsMenu;
    private ViewHolder views;
    private Vector<PosterItem> posterItems = new Vector<>(5, 5);
    private boolean isViewPagerVisible = false;
    private String sprefPostersNumber = "POSTERS_number";
    private String sprefPreFix = "POSTERS_number_";
    private String sprefTitlePostFix = "_title";
    private String sprefUrlPostFix = "_url";

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_and_media);

        sharedPreferences = getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        views = new ViewHolder(this);

        setSupportActionBar(views.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //image settings setup
        settingsMenu = new GestureSettingsMenu();
        settingsMenu.onRestoreInstanceState(savedInstanceState);

        initGrid();
        initPager();

        setOldValues();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                posterItems.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    posterItems.add(child.getValue(PosterItem.class));
                }
                if (posterItems.size() == 0) {
                    views.noPostersText.setVisibility(View.VISIBLE);
                } else {
                    views.noPostersText.setVisibility(View.GONE);
                }
                for (int i = 0; i < posterItems.size(); i++) {
                    editor.putString(sprefPreFix + i + sprefTitlePostFix, posterItems.get(i).getTitle());
                    editor.putString(sprefPreFix + i + sprefUrlPostFix, posterItems.get(i).getUrl());
                }
                editor.putInt(sprefPostersNumber, posterItems.size());
                editor.apply();
                gridAdapter.notifyDataSetChanged();
                pagerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                setOldValues();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isOnline()) {
            setOldValues();
        }
    }

    private void setOldValues() {
        int posters = sharedPreferences.getInt(sprefPostersNumber, 0);
        if (posters != 0) {
            posterItems.clear();
            for (int i = 0; i < posters; i++) {
                posterItems.add(new PosterItem(sharedPreferences.getString
                        (sprefPreFix + i + sprefTitlePostFix, ""), sharedPreferences.getString
                        (sprefPreFix + i + sprefUrlPostFix, "")));
            }
            pagerAdapter.notifyDataSetChanged();
            gridAdapter.notifyDataSetChanged();
        } else {
            views.noPostersText.setVisibility(View.VISIBLE);
        }
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

        gridAdapter = new ImageGalleryAdapter(this, posterItems, this);

        views.grid.setAdapter(gridAdapter);
    }

    private void initPager() {
        // Setting up pager views
        pagerAdapter = new PhotoPagerAdapter(views.pager, posterItems);
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
        views.noPostersText.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        settingsMenu.onSaveInstanceState(outState);
        super.onSaveInstanceState(outState);
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

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
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
