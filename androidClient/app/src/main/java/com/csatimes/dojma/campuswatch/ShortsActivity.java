package com.csatimes.dojma.campuswatch;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toolbar;

import com.csatimes.dojma.R;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class ShortsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);
        final Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        AppBarLayout appBarLayout = findViewById(R.id.shorts_appbar);
        appBarLayout.setExpanded(true);

        Toolbar toolbar = findViewById(R.id.shorts_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle("Campus Watch");
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        toolbar.inflateMenu(R.menu.shorts_menu);
        ViewPager viewPager = findViewById(R.id.vp_shorts);
        viewPager.setAdapter(new ShortsAdapter());
        viewPager.setOnClickListener(view -> {
            appBarLayout.setExpanded(false);
        });

//        findViewById(R.id.fab_done).setOnClickListener(view -> {
//            final Realm db = Realm.getDefaultInstance();
//            db.executeTransaction(realm -> {
//                for (final ShortsItem item : realm.where(ShortsItem.class).findAll()) {
//                    final ShortsItem updatedData = realm.copyFromRealm(item);
//                    updatedData.isRead = true;
//                    realm.insertOrUpdate(updatedData);
//                }
//            });
//            db.close();
//            onBackPressed();
//            finish();
//        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shorts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            //Perform refresh of data here.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
