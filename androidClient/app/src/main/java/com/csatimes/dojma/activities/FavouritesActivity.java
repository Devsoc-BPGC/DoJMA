package com.csatimes.dojma.activities;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.HeraldAdapter;
import com.csatimes.dojma.callbacks.SimpleItemTouchCallback;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.services.CopyLinkBroadcastReceiver;
import com.csatimes.dojma.utilities.CustomTabActivityHelper;
import com.csatimes.dojma.utilities.DHC;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.Sort;

public class FavouritesActivity extends BaseActivity
        implements
        HeraldAdapter.OnLikeClickedListener,
        HeraldAdapter.OnShareClickedListener,
        HeraldAdapter.OnItemClickedListener,
        HeraldAdapter.OnScrollUpdateListener {

    private Realm mDatabase;
    private RecyclerView mFavHeraldRecyclerView;
    private TextView mEmptyListTextView;
    private HeraldAdapter mHeraldAdapter;
    private String category;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        final Toolbar toolbar = findViewById(R.id.activity_favourites_toolbar);
        setSupportActionBar(toolbar);

        category = getIntent().getStringExtra("category");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (category != null) getSupportActionBar().setTitle(category);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onBackPressed();
            }
        });

        mEmptyListTextView = (TextView) findViewById(R.id.favourites_empty);
        mFavHeraldRecyclerView = (RecyclerView) findViewById(R.id.favourites_herald_rv);

        mDatabase = Realm.getDefaultInstance();
        final RealmList<HeraldItem> favouritesList = new RealmList<>();
        RealmQuery<HeraldItem> query = mDatabase.where(HeraldItem.class);
        query = category == null
                ? query.equalTo("fav", true)
                : query.equalTo("category", category);
        favouritesList.addAll(query.sort(EventItem.FIELD_ORIGINAL_DATE, Sort.ASCENDING).findAll());
        mHeraldAdapter = new HeraldAdapter(favouritesList);

        mFavHeraldRecyclerView.setHasFixedSize(true);
        mFavHeraldRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mFavHeraldRecyclerView.addItemDecoration(new androidx.recyclerview.widget.DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mHeraldAdapter.setHasStableIds(true);
        mHeraldAdapter.setOnLikeClickedListener(this);
        mHeraldAdapter.setOnShareClickedListener(this);
        mHeraldAdapter.setOnItemClickedListener(this);
        mHeraldAdapter.setOnScrollUpdateListener(this);
        mFavHeraldRecyclerView.setAdapter(mHeraldAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (category == null) {
            ItemTouchHelper.Callback callback = new SimpleItemTouchCallback(mHeraldAdapter);
            ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
            touchHelper.attachToRecyclerView(mFavHeraldRecyclerView);
        }
        if (mHeraldAdapter.getItemCount() == 0)
            mEmptyListTextView.setVisibility(View.VISIBLE);
        else {
            mEmptyListTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //Returning true displays the menu
        getMenuInflater().inflate(R.menu.favourites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favourites_menu_action_search) {
            Intent intent = new Intent(this, SearchableActivity.class);
            startActivity(intent);
            return true;
        } else
            return super.onOptionsItemSelected(item);
    }

    //When an article is liked mDatabase has to be updated using the postID
    @Override
    public void onLiked(final String postID) {
        mDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                HeraldItem foo = realm.where(HeraldItem.class).equalTo("postID", postID).findFirst();
                foo.setFav(true);

            }
        });
    }

    //When an article is disliked mDatabase has to be updated using the postID
    @Override
    public void onDisLiked(final String postID) {
        mDatabase.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                HeraldItem foo = realm.where(HeraldItem.class).equalTo("postID", postID).findFirst();
                foo.setFav(false);

            }
        });
    }

    //When an article is shared sharing intent is started
    @Override
    public void onShare(String postID) {

        HeraldItem foo = mDatabase.where(HeraldItem.class).equalTo("postID", postID).findFirst();

        Intent intent = new Intent((Intent.ACTION_SEND));
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, foo.getTitle_plain() + " at " + foo.getUrl());
        startActivity(Intent.createChooser(intent, "Share via"));

    }

    //Article was clicked, accordingly open the webpage
    @Override
    public void onClick(String postID) {

        HeraldItem foo = mDatabase.where(HeraldItem.class).equalTo("postID", postID).findFirst();

        if (DHC.isOnline(this)) {
            try {
                Intent intent = new Intent((Intent.ACTION_SEND));
                intent.putExtra(android.content.Intent.EXTRA_TEXT, foo.getUrl());

                Intent copy_intent = new Intent(this, CopyLinkBroadcastReceiver.class);
                PendingIntent copy_pendingIntent = PendingIntent.getBroadcast(this, 0, copy_intent, PendingIntent.FLAG_UPDATE_CURRENT);
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
                        Uri.parse(foo.getUrl()),
                        new CustomTabActivityHelper.CustomTabFallback() {
                            @Override
                            public void openUri(Activity activity, Uri uri) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                                    intent.putExtra(Intent.EXTRA_REFERRER,
                                            Uri.parse(Intent.URI_ANDROID_APP_SCHEME + "//" + getPackageName()));
                                }

                                startActivity(intent);
                            }
                        });
            } catch (Exception e) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(foo.getUrl()));
                startActivity(intent);
            }

        } else {
            Intent intent = new Intent(this, OfflineSimpleViewerActivity.class);
            intent.putExtra("POSTID", postID);
            startActivity(intent);
        }
    }

    @Override
    public void onUpdate(int pos) {
        mFavHeraldRecyclerView.scrollToPosition(pos);
    }


    private int getChromeCustomTabColorFromTheme() {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.colorPrimary, typedValue, true);
        return typedValue.data;
    }
}

