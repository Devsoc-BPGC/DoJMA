package com.csatimes.dojma.activities;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.SearchRV;
import com.csatimes.dojma.models.ContactItem;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldItem;
import com.csatimes.dojma.models.LinkItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class SearchableActivity extends AppCompatActivity {

    public static final int SEARCHABLE_FAVOURITES = 0;
    public static final int SEARCHABLE_HERALD = 1;
    public static final int SEARCHABLE_GAZETTES = 2;
    public static final int SEARCHABLE_EVENTS = 3;
    public static final int SEARCHABLE_CONTACTS = 4;
    public static final int SEARCHABLE_LINKS = 5;
    public static final int SEARCHABLE_MESS = 6;
    public static final int SEARCHABLE_POSTER = 7;


    private TextView emptyQuery;
    private SparseArray<List<Object>> results = new SparseArray<>();
    private RecyclerView recyclerView;
    private Realm database;
    private SearchRV adapter;

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode) {
            setTheme(R.style.AppThemeDark);
        } else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_search_toolbar);
        emptyQuery = (TextView) findViewById(R.id.content_searchable_empty_text);
        recyclerView = (RecyclerView) findViewById(R.id.content_searchable_rv);

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

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        database = Realm.getDefaultInstance();

        handleIntent(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (query.length() == 0) {
                emptyQuery.setVisibility(View.VISIBLE);
                if (adapter != null) {
                    adapter = null;
                    recyclerView.setAdapter(null);
                }
            } else {
                emptyQuery.setVisibility(View.GONE);
                generateResults(query);
                if (adapter == null) {
                    adapter = new SearchRV(results);
                    recyclerView.setAdapter(adapter);
                    adapter.updateResult();
                } else {
                    adapter.notifyDataSetChanged();
                    adapter.updateResult();
                }
            }
        }
    }

    private void generateResults(String query) {

        results.clear();

        //add herald fav articles
        List<Object> favourites = new ArrayList<>();
        favourites.addAll(database
                .where(HeraldItem.class)
                .contains("title", query)
                .findAll());
        results.put(SEARCHABLE_FAVOURITES, favourites);

        //add herald articles
        List<Object> articles = new ArrayList<>();
        articles.addAll(database
                .where(HeraldItem.class)
                .contains("title", query)
                .findAll());
        results.put(SEARCHABLE_HERALD, articles);

        //add gazettes
        List<Object> gazettes = new ArrayList<>();
        gazettes.addAll(database
                .where(GazetteItem.class)
                .contains("title", query)
                .or().contains("date", query)
                .findAll());
        results.put(SEARCHABLE_GAZETTES, gazettes);

        //add events
        List<Object> events = new ArrayList<>();
        events.addAll(database
                .where(EventItem.class)
                .contains("title", query)
                .or().contains("location", query)
                .or().contains("desc", query)
                .findAll());
        results.put(SEARCHABLE_EVENTS, events);

        //add contacts
        List<Object> contacts = new ArrayList<>();
        contacts.addAll(database
                .where(ContactItem.class)
                .contains("name", query)
                .or().contains("number", query)
                .or().contains("email", query)
                .or().contains("type", query)
                .findAll());
        results.put(SEARCHABLE_CONTACTS, contacts);

        //add links
        List<Object> links = new ArrayList<>();
        contacts.addAll(database
                .where(LinkItem.class)
                .contains("title", query)
                .or().contains("url", query)
                .findAll());
        results.put(SEARCHABLE_LINKS, contacts);


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
                SearchableActivity.this.finish();
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
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
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setAction(Intent.ACTION_SEARCH);
                intent.putExtra(SearchManager.QUERY, newText);
                onNewIntent(intent);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

}
