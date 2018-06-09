package com.csatimes.dojma.issues;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.csatimes.dojma.R;
import com.csatimes.dojma.activities.BaseActivity;
import com.csatimes.dojma.activities.SearchableActivity;
import com.csatimes.dojma.herald.HeraldAdapter;
import com.csatimes.dojma.models.HeraldItem;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

public class IssuesActivity extends BaseActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        final Toolbar toolbar = findViewById(R.id.toolbar_issues_activity);
        setSupportActionBar(toolbar);

        final String category = getIntent().getStringExtra(HeraldItem.CATEGORY);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (category != null) getSupportActionBar().setTitle(category);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        final RecyclerView favRv = findViewById(R.id.rv_issues_activity);

        final HeraldAdapter mHeraldAdapter = new HeraldAdapter(this);
        mHeraldAdapter.setCategory(category);
        favRv.setLayoutManager(new LinearLayoutManager(this));
        favRv.addItemDecoration(new DividerItemDecoration(this, VERTICAL));

        favRv.setAdapter(mHeraldAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.favourites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();
        if (id == R.id.favourites_menu_action_search) {
            final Intent intent = new Intent(this, SearchableActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}

