package com.csatimes.dojma.activities;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import androidx.annotation.NonNull;
import com.google.android.material.appbar.AppBarLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;

import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ImageGalleryAdapter;
import com.csatimes.dojma.adapters.PhotoPagerAdapter;
import com.csatimes.dojma.models.PosterItem;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.utilities.GestureSettingsMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.realm.Realm;
import io.realm.RealmList;

public class ImagesAndMediaActivity extends BaseActivity implements ImageGalleryAdapter.OnPhotoListener {

    public static final String TAG = "activities.ImagesAndMedia";
    boolean mIsViewPagerVisible = false;
    private ImageGalleryAdapter mGridAdapter;
    private PhotoPagerAdapter mPagerAdapter;
    private GestureSettingsMenu mGestureSettingsMenu;
    private ViewHolder views;
    private RealmList<PosterItem> mPosterItems;
    private Realm mDatabase;
    private ValueEventListener mPosterValueEventListener;
    private DatabaseReference mPostersReference = FirebaseDatabase.getInstance().getReference().child("posters");

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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

    private void initGrid() {
        views.grid.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(this, span()));
        mGridAdapter = new ImageGalleryAdapter(this, mPosterItems, this);
        views.grid.setAdapter(mGridAdapter);
    }

    private int span() {

        //Setup columns according to device screen
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up grid
        int num = 180;
        float t = dpWidth / num;
        float r = dpWidth % num;
        int cols;
        if (r < 0.1 * num)
            cols = (int) Math.ceil(dpWidth / num);
        else
            cols = (int) t;

        return cols;
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

    @Override
    protected void onStart() {
        super.onStart();
        mPosterValueEventListener = returnPostersListener();
        mPostersReference.addValueEventListener(mPosterValueEventListener);
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
                        DHC.e(TAG,"Added poster");
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
                       DHC.e(TAG, "Poster parse exception");
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
                DHC.e(TAG,"onDatabaseError " + databaseError.getMessage());
            }
        };
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

    private void onPhotoInPagerSelected(int position) {

    }

    @Override
    public void onPhotoClick(int position) {
        mPagerAdapter.setActivated(true);
        mIsViewPagerVisible = true;
        views.pager.setVisibility(View.GONE);
        views.pager.setCurrentItem(position, false);
        views.toolbarBack.setVisibility(View.VISIBLE);
        views.pagerToolbar.setVisibility(View.VISIBLE);
        views.appBarLayout.setVisibility(View.GONE);
        views.pagerBackground.setVisibility(View.VISIBLE);
        views.pager.setVisibility(View.VISIBLE);
        views.noPostersText.setVisibility(View.GONE);
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
        final Toolbar                                   toolbar;
        final View                                      toolbarBack;
        final androidx.recyclerview.widget.RecyclerView grid;
        final AppBarLayout                              appBarLayout;
        final ViewPager                                 pager;
        final Toolbar                                   pagerToolbar;
        // final TextView pagerTitle;
        final View                                      pagerBackground;
        final TextView                                  noPostersText;

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
