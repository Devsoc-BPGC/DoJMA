package com.csatimes.dojma.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.models.TypeItem;
import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;

import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_CONTACT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_EVENT;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_GAZETTE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_LINK;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_MESS;
import static com.csatimes.dojma.utilities.DHC.SEARCH_ITEM_TYPE_TITLE;

public class SearchableActivity extends BaseActivity implements SearchAdapter.OnImageClicked {

    private TextView mEmptyQuery;
    private List<TypeItem> results = new ArrayList<>();
    private RecyclerView mSearchRV;
    private Realm mDatabase;
    private SearchAdapter mSearchAdapter;
    private GestureFrameLayout gestureFrameLayout;
    private SimpleDraweeView simpleDraweeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_search_toolbar);
        mEmptyQuery = (TextView) findViewById(R.id.content_searchable_empty_text);
        mSearchRV = (RecyclerView) findViewById(R.id.content_searchable_rv);
        gestureFrameLayout = (GestureFrameLayout) findViewById(R.id.gesture_frame_search);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.imageView_content_search_large);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        gestureFrameLayout.getController().getSettings().setOverzoomFactor(10);
        setSupportActionBar(toolbar);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        mSearchRV.setHasFixedSize(true);

        mDatabase = Realm.getDefaultInstance();

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (imageShown) {
            onClicked(null);
        }
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
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
                    mSearchAdapter = new SearchAdapter(this, results, Calendar.getInstance().getTime());
                    mSearchRV.setAdapter(mSearchAdapter);
                    final int span = span();
                    GridLayoutManager mLayoutManager = new GridLayoutManager(this, span);
                    mLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                        @Override
                        public int getSpanSize(int position) {
                            if (mSearchAdapter.getItemViewType(position) == DHC.SEARCH_ITEM_TYPE_MESS)
                                return 1;
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

    private void generateResults(String query) {

        results.clear();

        //add herald fav articles
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Favourites"));
            int lastHeaderPosition = results.size() - 1;
            RealmList<HeraldItem> searchHeraldFavourites = new RealmList<>();
            searchHeraldFavourites.addAll(mDatabase
                    .where(HeraldItem.class)
                    .contains("title", query, Case.INSENSITIVE)
                    .equalTo("fav", true)
                    .findAll());
            if (searchHeraldFavourites.size() != 0)
                for (int i = 0; i < searchHeraldFavourites.size(); i++) {
                    results.add(new TypeItem(SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE, searchHeraldFavourites.get(i)));
                }
            else {
                results.remove(lastHeaderPosition);
            }
        }
        //add herald articles
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Herald"));
            int lastHeaderPosition = results.size() - 1;
            RealmList<HeraldItem> searchHerald = new RealmList<>();
            searchHerald.addAll(mDatabase
                    .where(HeraldItem.class)
                    .contains("title", query, Case.INSENSITIVE)
                    .equalTo("fav", false)
                    .findAll());

            if (searchHerald.size() != 0) for (int i = 0; i < searchHerald.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_HERALD_ARTICLES_FAVOURITE, searchHerald.get(i)));
            }
            else {
                results.remove(lastHeaderPosition);
            }
        }
        //add gazettes
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Gazettes"));
            int lastHeaderPosition = results.size() - 1;
            RealmList<GazetteItem> searchGazettes = new RealmList<>();
            searchGazettes.addAll(mDatabase
                    .where(GazetteItem.class)
                    .contains("title", query, Case.INSENSITIVE)
                    .or().contains("date", query, Case.INSENSITIVE)
                    .findAll());
            if (searchGazettes.size() != 0) for (int i = 0; i < searchGazettes.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_GAZETTE, searchGazettes.get(i)));
            }
            else {
                results.remove(lastHeaderPosition);
            }
        }
        //add events
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Events"));
            int lastHeaderPosition = results.size() - 1;
            RealmList<EventItem> searchEvents = new RealmList<>();
            searchEvents.addAll(mDatabase
                    .where(EventItem.class)
                    .contains("title", query, Case.INSENSITIVE)
                    .or().contains("location", query, Case.INSENSITIVE)
                    .or().contains("desc", query, Case.INSENSITIVE)
                    .findAll());
            if (searchEvents.size() != 0) for (int i = 0; i < searchEvents.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_EVENT, searchEvents.get(i)));
            }
            else {
                results.remove(lastHeaderPosition);
            }
        }
        //add contacts
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Contacts"));
            int lastHeaderPosition = results.size() - 1;
            RealmList<ContactItem> searchContacts = new RealmList<>();
            searchContacts.addAll(mDatabase
                    .where(ContactItem.class)
                    .contains("name", query, Case.INSENSITIVE)
                    .or().contains("number", query)
                    .or().contains("email", query, Case.INSENSITIVE)
                    .or().contains("type", query, Case.INSENSITIVE)
                    .or().contains("sub1", query, Case.INSENSITIVE)
                    .or().contains("sub2", query, Case.INSENSITIVE)
                    .findAll());
            if (searchContacts.size() != 0)
                for (int i = 0; i < searchContacts.size(); i++) {
                    results.add(new TypeItem(SEARCH_ITEM_TYPE_CONTACT, searchContacts.get(i)));
                }
            else {
                results.remove(lastHeaderPosition);
            }
        }
        //add links
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Links"));
            int lastHeaderPosition = results.size() - 1;
            RealmList<LinkItem> searchLinks = new RealmList<>();
            searchLinks.addAll(mDatabase
                    .where(LinkItem.class)
                    .contains("title", query, Case.INSENSITIVE)
                    .or().contains("url", query, Case.INSENSITIVE)
                    .findAll());
            if (searchLinks.size() != 0) for (int i = 0; i < searchLinks.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_LINK, searchLinks.get(i)));
            }
            else {
                results.remove(lastHeaderPosition);
            }
        }
        //add mess images
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE, "Mess"));
            int lastHeaderPosition = results.size() - 1;
            RealmList<MessItem> searchMess = new RealmList<>();
            searchMess.addAll(mDatabase
                    .where(MessItem.class)
                    .contains("title", query, Case.INSENSITIVE)
                    .findAll());
            if (searchMess.size() != 0) for (int i = 0; i < searchMess.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_MESS, searchMess.get(i)));
            }
            else {
                results.remove(lastHeaderPosition);
            }
        }


        //add posters. Currently disabled due to it's complexity
        /*
        {
            results.add(new TypeItem(SEARCH_ITEM_TYPE_TITLE,"Posters"));
            int lastHeaderPosition = results.size()-1;
            RealmList<PosterItem> searchPosters = new RealmList<>();
            searchPosters.addAll(mDatabase
                    .where(PosterItem.class)
                    .contains("title", query,Case.INSENSITIVE)
                    .findAll());
            for (int i = 0; i < searchPosters.size(); i++) {
                results.add(new TypeItem(SEARCH_ITEM_TYPE_POSTER, searchPosters.get(i)));
            }
        }
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.menu_search_search);

        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setQueryHint("Search DoJMA app..");
        searchItem.expandActionView();

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                if (imageShown) {
                    onClicked(null);
                }
                SearchableActivity.this.finish();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                if (imageShown) {
                    onClicked(null);
                }
                //handle search
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY, query);
                onNewIntent(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //handle search
                if (imageShown) {
                    onClicked(null);
                }
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY, newText);
                onNewIntent(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

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

    boolean imageShown = false;

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
    public void onClicked(String uri) {
        if (imageShown) {
            gestureFrameLayout.setVisibility(View.GONE);
            imageShown = false;
        } else {
            imageShown = true;
            gestureFrameLayout.setVisibility(View.VISIBLE);
            simpleDraweeView.setImageURI(Uri.parse(uri));
            View view = this.getCurrentFocus();
            if (view != null) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}
