package com.csatimes.dojma;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.csatimes.dojma.adapters.HeraldRV;
import com.csatimes.dojma.models.HeraldNewsItemFormat;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by yash on 19/7/16.
 */

public class OpenArchiveListView extends AppCompatActivity {


    private MaterialSearchView searchView;
    private Realm database;
    private RealmResults realmResults;
    private RealmList realmList;
    private RecyclerView arcHeraldRV;
    private HeraldRV heraldRVAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_archive_open_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.offline_archive_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView = (MaterialSearchView) findViewById(R.id.archive_material_search_view);
        arcHeraldRV = (RecyclerView) findViewById(R.id.archive_list_view_herald_rv);

        searchView.setHint("Search post");

         database = Realm.getDefaultInstance();
        realmResults = database.where(HeraldNewsItemFormat.class).equalTo("originalMonthYear",getIntent().getStringExtra("myOriginalMonthYear")).findAll();
        realmList = new RealmList();
        realmList.addAll(realmResults);

        heraldRVAdapter = new HeraldRV(this, realmList, database, OpenArchiveListView.this);
        heraldRVAdapter.setGoogleChromeInstalled(getSharedPreferences(DHC.USER_PREFERENCES, MODE_PRIVATE)
                .getBoolean(getString(R.string.SP_chrome_install_status), false));

        arcHeraldRV.setHasFixedSize(true);
        arcHeraldRV.setLayoutManager(new LinearLayoutManager(this));
        arcHeraldRV.setItemAnimator(new DefaultItemAnimator());

        arcHeraldRV.setAdapter(heraldRVAdapter);


        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String query) {
                realmResults = database
                        .where(HeraldNewsItemFormat.class)
                        .beginGroup()
                        .equalTo("originalMonthYear",getIntent().getStringExtra("myOriginalMonthYear"))
                        .endGroup()
                        .contains
                                ("title", query, Case.INSENSITIVE)
                        .findAll();
                realmList.clear();
                realmList.addAll(realmResults);
                heraldRVAdapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {


                realmResults = database
                        .where(HeraldNewsItemFormat.class)
                        .beginGroup()
                        .equalTo("originalMonthYear",getIntent().getStringExtra("myOriginalMonthYear"))
                        .endGroup()
                        .contains("title", newText, Case.INSENSITIVE)
                        .findAll();
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
        getMenuInflater().inflate(R.menu.open_category_menu, menu);

        MenuItem item = menu.findItem(R.id.category_menu_action_search);
        searchView.setMenuItem(item);

        return true;
    }
}
