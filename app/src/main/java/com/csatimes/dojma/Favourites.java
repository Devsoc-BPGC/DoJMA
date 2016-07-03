package com.csatimes.dojma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.miguelcatalan.materialsearchview.MaterialSearchView;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;

public class Favourites extends AppCompatActivity {

    private MaterialSearchView searchView;
    private Realm database;
    private RealmConfiguration realmConfiguration;
    private RealmResults realmResults;
    private RealmList realmList;
    private RecyclerView favHeraldRV;
    private HeraldRV heraldRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView = (MaterialSearchView) findViewById(R.id.favourites_material_search_view);
        favHeraldRV = (RecyclerView) findViewById(R.id.favourites_herald_rv);

        searchView.setHint("Search favourites");

        realmConfiguration = new RealmConfiguration.Builder(this).name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();

        realmResults = database.where(HeraldNewsItemFormat.class).equalTo("fav", true).findAll();
        realmList = new RealmList();
        realmList.addAll(realmResults);

        heraldRVAdapter = new HeraldRV(this, realmList, database, Favourites.this);
        heraldRVAdapter.setGoogleChromeInstalled(getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE)
                .getBoolean(getString(R.string.SP_chrome_install_status), false));

        favHeraldRV.setHasFixedSize(true);
        favHeraldRV.setLayoutManager(new LinearLayoutManager(this));
        favHeraldRV.setItemAnimator(new DefaultItemAnimator());


        favHeraldRV.setAdapter(heraldRVAdapter);


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                realmResults = database.where(HeraldNewsItemFormat.class).beginGroup().equalTo
                        ("fav", true).endGroup().contains
                        ("title", query)
                        .findAll();
                realmList.clear();
                realmList.addAll(realmResults);
                heraldRVAdapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                realmResults = database.where(HeraldNewsItemFormat.class).beginGroup().equalTo
                        ("fav", true).endGroup().contains("title", newText).findAll();
                realmList.clear();
                realmList.addAll(realmResults);
                heraldRVAdapter.notifyDataSetChanged();

                return true;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                //Do some magic
            }

            @Override
            public void onSearchViewClosed() {
                //Do some magic
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourites_menu, menu);

        MenuItem item = menu.findItem(R.id.favourites_menu_action_search);
        searchView.setMenuItem(item);

        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            database.close();
            finish();
            super.onBackPressed();

        }

    }
}
