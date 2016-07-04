package com.csatimes.dojma;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmResults;
import io.realm.Sort;

public class ImagesAndMedia extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageGalleryRV adapter;
    RealmList<HeraldNewsItemFormat> realmList;
    RealmResults<HeraldNewsItemFormat> realmResults;
    Realm database;
    RealmConfiguration realmConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_and_media);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.images_recyclerView);

        realmConfiguration = new RealmConfiguration.Builder(this)
                .name(DHC.REALM_DOJMA_DATABASE).deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfiguration);
        database = Realm.getDefaultInstance();

        realmResults = database.where(HeraldNewsItemFormat.class).notEqualTo("link", "-1")
                .findAllSorted("originalDate", Sort.DESCENDING);
        realmList = new RealmList<>();
        realmList.addAll(realmResults);

        adapter = new ImageGalleryRV(this, realmList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

}
