package com.csatimes.dojma.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.SearchAdapter;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.models.TypeItem;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;

import static android.os.Build.VERSION_CODES.LOLLIPOP;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_EVENT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_LINK;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_MESS;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_TITLE;

public class SearchableActivity extends BaseActivity implements SearchAdapter.OnImageClicked {

    private final List<TypeItem> results = new ArrayList<>(0);
    boolean imageShown = false;
    private TextView mEmptyQuery;
    private RecyclerView mSearchRV;
    private Realm mDatabase;
    @Nullable
    private SearchAdapter mSearchAdapter;
    private GestureFrameLayout gestureFrameLayout;
    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        final Toolbar toolbar = findViewById(R.id.activity_search_toolbar);
        setSupportActionBar(toolbar);
        mEmptyQuery = findViewById(R.id.content_searchable_empty_text);
        mSearchRV = findViewById(R.id.content_searchable_rv);
        gestureFrameLayout = findViewById(R.id.gesture_frame_search);
        simpleDraweeView = findViewById(R.id.imageView_content_search_large);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        gestureFrameLayout.getController().getSettings().setOverzoomFactor(10);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        final Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        mSearchRV.setHasFixedSize(true);

        mDatabase = Realm.getDefaultInstance();

        handleIntent(getIntent());

    }

    private void handleIntent(final Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            final String query = intent.getStringExtra(SearchManager.QUERY);
            if (query.length() == 0) {
                mEmptyQuery.setVisibility(View.VISIBLE);
                if (mSearchAdapter != null) {
                    mSearchAdapter = null;
                    mSearchRV.setAdapter(null);
                }
            } else {
                mEmptyQuery.setVisibility(View.GONE);
                generateResults(query);
                if (mSearchAdapter == null) {
                    mSearchAdapter = new SearchAdapter(this,
                            results, Calendar.getInstance().getTime());
                    mSearchRV.setAdapter(mSearchAdapter);
                    final int span = span();
                    final androidx.recyclerview.widget.GridLayoutManager mLayoutManager =
                            new GridLayoutManager(this, span);
                    mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(final int position) {
                            if (mSearchAdapter.getItemViewType(position) == SEARCH_ITEM_TYPE_MESS) {
                                return 1;
                            }
                            return span;
                        }
                    });
                    mSearchRV.setLayoutManager(mLayoutManager);
                } else {
                    mSearchAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void generateResults(final String query) {

        results.clear();

        //add herald fav articles

        results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Favourites"));
        int lastHeaderPosition = results.size() - 1;
        final List<HeraldItem> favoriteMatches = new RealmList<>();
        favoriteMatches.addAll(mDatabase
                .where(HeraldItem.class)
                .contains("title", query, Case.INSENSITIVE)
                .equalTo("fav", true)
                .findAll());
        if (favoriteMatches.size() == 0) {
            results.remove(lastHeaderPosition);
        } else {
            for (int i = 0; i < favoriteMatches.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE,
                        favoriteMatches.get(i)));
            }
        }

        //add herald articles
        results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Herald"));
        lastHeaderPosition = results.size() - 1;
        final List<HeraldItem> searchHerald = new RealmList<>();
        searchHerald.addAll(mDatabase
                .where(HeraldItem.class)
                .contains("title", query, Case.INSENSITIVE)
                .equalTo("fav", false)
                .findAll());

        if (searchHerald.size() == 0) {
            results.remove(lastHeaderPosition);
        } else {
            for (int i = 0; i < searchHerald.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE,
                        searchHerald.get(i)));
            }
        }

        //add events

        results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Events"));
        lastHeaderPosition = results.size() - 1;
        final List<EventItem> eventsResults = new RealmList<>();
        eventsResults.addAll(mDatabase
                .where(EventItem.class)
                .contains(EventItem.FIELD_TITLE, query, Case.INSENSITIVE)
                .or().contains(EventItem.FIELD_LOCATION, query, Case.INSENSITIVE)
                .or().contains(EventItem.FIELD_DESC, query, Case.INSENSITIVE)
                .findAll());
        if (eventsResults.size() == 0) {
            results.remove(lastHeaderPosition);
        } else {
            for (int i = 0; i < eventsResults.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_EVENT, eventsResults.get(i)));
            }
        }

        //add contacts

        results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Contacts"));
        lastHeaderPosition = results.size() - 1;
        final List<ContactItem> contactResults = new RealmList<>();
        contactResults.addAll(mDatabase
                .where(ContactItem.class)
                .contains(ContactItem.FIELD_NAME, query, Case.INSENSITIVE)
                .or().contains(ContactItem.FIELD_NUMBER, query)
                .or().contains(ContactItem.FIELD_EMAIL, query, Case.INSENSITIVE)
                .or().contains(ContactItem.FIELD_TYPE, query, Case.INSENSITIVE)
                .or().contains(ContactItem.FIELD_SUB1, query, Case.INSENSITIVE)
                .or().contains(ContactItem.FIELD_SUB2, query, Case.INSENSITIVE)
                .findAll());
        if (contactResults.size() == 0) {
            results.remove(lastHeaderPosition);
        } else {
            for (int i = 0; i < contactResults.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_CONTACT, contactResults.get(i)));
            }
        }

        //add links

        results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Links"));
        lastHeaderPosition = results.size() - 1;
        final List<LinkItem> linkResults = new RealmList<>();
        linkResults.addAll(mDatabase
                .where(LinkItem.class)
                .contains(LinkItem.FIELD_TITLE, query, Case.INSENSITIVE)
                .or().contains("url", query, Case.INSENSITIVE)
                .findAll());
        if (linkResults.size() == 0) {
            results.remove(lastHeaderPosition);
        } else {
            for (int i = 0; i < linkResults.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_LINK, linkResults.get(i)));
            }
        }

        //add mess images

        results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Mess"));
        lastHeaderPosition = results.size() - 1;
        final List<MessItem> messResults = new RealmList<>();
        messResults.addAll(mDatabase
                .where(MessItem.class)
                .contains(MessItem.FIELD_TITLE, query, Case.INSENSITIVE)
                .findAll());
        if (messResults.size() == 0) {
            results.remove(lastHeaderPosition);
        } else {
            for (int i = 0; i < messResults.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_MESS, messResults.get(i)));
            }
        }
    }

    private int span() {
        //Setup columns according to device screen
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up grid
        final float num = 180.0f;
        final float t = dpWidth / num;
        final float r = dpWidth % num;
        final double cols;
        cols = r < num / 10
                ? Math.ceil(dpWidth / num)
                : t;

        return (int) cols;
    }

    @Override
    public void onBackPressed() {
        if (imageShown) {
            onClicked(null);
            imageShown = false;
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        if (imageShown) {
            onClicked(null);
        }
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onClicked(final String uri) {
        if (imageShown) {
            gestureFrameLayout.setVisibility(View.GONE);
            imageShown = false;
        } else {
            imageShown = true;
            gestureFrameLayout.setVisibility(View.VISIBLE);
            simpleDraweeView.setImageURI(Uri.parse(uri));
            final View view = this.getCurrentFocus();
            if (view != null) {
                final InputMethodManager imm =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        final MenuItem searchItem = menu.findItem(R.id.menu_search_search);

        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Search DoJMA app..");
        searchItem.expandActionView();

        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(final MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(final MenuItem menuItem) {
                if (imageShown) {
                    onClicked(null);
                }
                SearchableActivity.this.finish();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                searchView.clearFocus();
                if (imageShown) {
                    onClicked(null);
                }
                //handle search
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY, query);
                onNewIntent(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
                //handle search
                if (imageShown) {
                    onClicked(null);
                }
                final Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY, newText);
                onNewIntent(intent);
                return true;
            }
        });
        return true;
    }
}
