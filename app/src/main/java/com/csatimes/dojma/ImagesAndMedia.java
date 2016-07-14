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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.alexvasilkov.android.commons.state.InstanceState;
import com.alexvasilkov.android.commons.utils.Views;
import com.alexvasilkov.gestures.animation.ViewPositionAnimator;
import com.alexvasilkov.gestures.commons.DepthPageTransformer;
import com.alexvasilkov.gestures.commons.RecyclePagerAdapter;
import com.alexvasilkov.gestures.transition.SimpleViewsTracker;
import com.alexvasilkov.gestures.transition.ViewsCoordinator;
import com.alexvasilkov.gestures.transition.ViewsTransitionAnimator;
import com.alexvasilkov.gestures.transition.ViewsTransitionBuilder;
import com.facebook.drawee.backends.pipeline.Fresco;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class ImagesAndMedia extends AppCompatActivity implements ViewPositionAnimator
        .PositionUpdateListener, ImageGalleryAdapter.OnPhotoListener {
    private static final int PAGE_SIZE = 30;
    private static final int NO_POSITION = -1;
    RealmList<HeraldNewsItemFormat> realmList;
    RealmResults<HeraldNewsItemFormat> realmResults;
    Realm database;
    RealmConfiguration realmConfiguration;
    private ViewsTransitionAnimator<Integer> animator;
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

        realmResults = database.where(HeraldNewsItemFormat.class).notEqualTo("url", "")
                .findAllSorted("originalDate", Sort.DESCENDING);
        realmList = new RealmList<>();
        realmList.addAll(realmResults);

        initDecorMargins();
        initGrid();
        initPager();
        initAnimator();


        if (savedPagerPosition != NO_POSITION) {
            // Photo was show in pager, we should switch to pager mode instantly
            onPositionUpdate(1f, false);
        }

    }

    private void initDecorMargins() {

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

        onCreateOptionsMenuFullMode(views.pagerToolbar.getMenu());

        views.pagerToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelectedFullMode(item);
            }
        });
    }

    private void initAnimator() {

        animator = new ViewsTransitionBuilder<Integer>()
                .fromRecyclerView(views.grid, new SimpleViewsTracker() {
                    @Override
                    public View getViewForPosition(int position) {
                        RecyclerView.ViewHolder holder =
                                views.grid.findViewHolderForLayoutPosition(position);
                        return holder == null ? null : ImageGalleryAdapter.getImage(holder);
                    }
                })
                .intoViewPager(views.pager, new SimpleViewsTracker() {
                    @Override
                    public View getViewForPosition(int position) {
                        RecyclePagerAdapter.ViewHolder holder = pagerAdapter.getViewHolder(position);
                        return holder == null ? null : PhotoPagerAdapter.getImage(holder);
                    }
                })
                .build();
        animator.addPositionUpdateListener(this);
        animator.setReadyListener(new ViewsCoordinator.OnViewsReadyListener<Integer>() {
            @Override
            public void onViewsReady(@NonNull Integer id) {
                // Setting image drawable from 'from' view to 'to' to prevent flickering
                ImageView from = (ImageView) animator.getFromView();
                ImageView to = (ImageView) animator.getToView();
                if (to.getDrawable() == null) {
                    to.setImageDrawable(from.getDrawable());
                }
            }
        });
    }

    private void onPhotoInPagerSelected(int position) {
       /* Photo photo = pagerAdapter.getPhoto(position);
        if (photo == null) {
            views.pagerTitle.setText(null);
        } else {
            SpannableBuilder title = new SpannableBuilder(Ex6AdvancedDemoActivity.this);
            title.append(photo.getTitle()).append("\n")
                    .createStyle().setColorResId(R.color.text_secondary_light).apply()
                    .append(R.string.photo_by).append(" ")
                    .append(photo.getOwner().getUsername());
            //views.pagerTitle.setText(title.build());
        }*/
    }

    @Override
    public void onPhotoClick(int position) {
        pagerAdapter.setActivated(true);
        animator.enter(position, true);
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
        if (!animator.isLeaving()) {
            animator.exit(true);
        } else {
            super.onBackPressed();
        }
    }

    private void saveScreenState() {
        clearScreenState();

        savedPhotoCount = gridAdapter.getItemCount();

        savedPagerPosition = animator.isLeaving() || pagerAdapter.getCount() == 0
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return settingsMenu.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (settingsMenu.onOptionsItemSelected(item)) {
            invalidateOptionsMenu();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void onCreateOptionsMenuFullMode(Menu menu) {
    }

    private boolean onOptionsItemSelectedFullMode(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return false;
        }
    }

    @Override
    public void onPositionUpdate(float state, boolean isLeaving) {
        views.pagerBackground.setVisibility(state == 0f ? View.INVISIBLE : View.VISIBLE);
        views.pagerBackground.getBackground().setAlpha((int) (255 * state));
//
//        views.toolbar.setVisibility(state == 1f ? View.INVISIBLE : View.VISIBLE);
//        views.toolbar.setAlpha((float) Math.sqrt(1d - state)); // Slow down toolbar animation

        views.appBarLayout.setVisibility(state == 1f ? View.INVISIBLE : View.VISIBLE);
        views.appBarLayout.setAlpha((float) Math.sqrt(1d - state)); // Slow down toolbar animation

        views.pagerToolbar.setVisibility(state == 0f ? View.INVISIBLE : View.VISIBLE);
        views.pagerToolbar.setAlpha(state);

        // views.pagerTitle.setVisibility(state == 1f ? View.VISIBLE : View.INVISIBLE);

        if (isLeaving && state == 0f) {
            pagerAdapter.setActivated(false);
        }
    }

    @Override
    protected void onDestroy() {
        Fresco.shutDown();
        super.onDestroy();
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
