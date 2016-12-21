package com.csatimes.dojma;

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

import com.csatimes.dojma.adapters.SearchRV;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.models.HeraldNewsItemFormat;
import com.csatimes.dojma.utilities.DHC;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class Searchable extends AppCompatActivity {

    public static final int SEARCHABLE_ARTICLES = 0;
    public static final int SEARCHABLE_GAZETTES = 1;
    public static final int SEARCHABLE_EVENTS = 2;

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
        setSupportActionBar(toolbar);

        emptyQuery = (TextView) findViewById(R.id.content_searchable_empty_text);
        recyclerView = (RecyclerView) findViewById(R.id.content_searchable_rv);

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //    window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            //     window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
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

        //add herald articles
        List<Object> articles = new ArrayList<>();
        articles.addAll(database
                .where(HeraldNewsItemFormat.class)
                .contains("title", query)
                .findAll());
        results.put(SEARCHABLE_ARTICLES, articles);

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

        DHC.log("articles " + results.get(SEARCHABLE_ARTICLES).size() + " \ngaz " + results.get(SEARCHABLE_GAZETTES).size() + " \neves " + results.get(SEARCHABLE_EVENTS).size());

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
                Searchable.this.finish();
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
