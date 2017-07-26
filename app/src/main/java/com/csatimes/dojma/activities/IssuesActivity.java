package com.csatimes.dojma.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.HeraldItem;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

public class IssuesActivity extends BaseActivity {

    Realm mDatabase;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_issues_toolbar);
        ListView issuesListView = (ListView) findViewById(R.id.content_issues_lv);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = Realm.getDefaultInstance();
        RealmList<HeraldItem> issuesList = new RealmList<>();
        issuesList.addAll(mDatabase.where(HeraldItem.class).distinct("category").sort("category", Sort.ASCENDING));
        final List<String> titlesList = new ArrayList<>();
        for (int i = 0; i < issuesList.size(); i++) {
            titlesList.add(issuesList.get(i).getCategory());
        }
        issuesListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, titlesList));
        issuesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(IssuesActivity.this, FavouritesActivity.class);
                intent.putExtra("category", titlesList.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
