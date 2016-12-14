package com.csatimes.dojma;

import android.os.Bundle;
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
 * Created by yash on 18/7/16.
 */

public class OpenCategoryListView extends AppCompatActivity {


    private MaterialSearchView searchView;
    private Realm database;
    private RealmResults realmResults;
    private RealmList realmList;
    private RecyclerView catHeraldRV;
    private HeraldRV heraldRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_open_list_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.offline_category_toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        searchView = (MaterialSearchView) findViewById(R.id.category_material_search_view);
        catHeraldRV = (RecyclerView) findViewById(R.id.category_list_view_herald_rv);

        searchView.setHint("Search post");

        database = Realm.getDefaultInstance();
        realmResults = database.where(HeraldNewsItemFormat.class).equalTo("categoryTitle", getIntent().getStringExtra("myCategoryTag")).findAll();
        realmList = new RealmList();
        realmList.addAll(realmResults);
        getSupportActionBar().setTitle(getIntent().getStringExtra("myCategoryTag"));
        heraldRVAdapter = new HeraldRV(realmList);
        catHeraldRV.setHasFixedSize(true);
        catHeraldRV.setLayoutManager(new LinearLayoutManager(this));
        catHeraldRV.setItemAnimator(new DefaultItemAnimator());

        catHeraldRV.setAdapter(heraldRVAdapter);

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                realmResults = database
                        .where(HeraldNewsItemFormat.class)
                        .beginGroup()
                        .equalTo("categoryTitle", getIntent().getStringExtra("myCategoryTag"))
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
                        .equalTo("categoryTitle", getIntent().getStringExtra("myCategoryTag"))
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
    protected void onDestroy() {
        super.onDestroy();
        database.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.open_category_menu, menu);

        MenuItem item = menu.findItem(R.id.category_menu_action_search);
        searchView.setMenuItem(item);

        return true;
    }
}
