package com.csatimes.dojma.campuswatch;

import android.os.Bundle;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.ShortsItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import io.realm.Realm;

public class ShortsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);
        ((ViewPager) findViewById(R.id.vp_shorts)).setAdapter(new ShortsAdapter());
        findViewById(R.id.fab_back).setOnClickListener(view -> onBackPressed());
        findViewById(R.id.fab_done).setOnClickListener(view -> {
            final Realm db = Realm.getDefaultInstance();
            db.executeTransaction(realm -> {
                for (final ShortsItem item : realm.where(ShortsItem.class).findAll()) {
                    final ShortsItem updatedData = realm.copyFromRealm(item);
                    updatedData.isRead = true;
                    realm.insertOrUpdate(updatedData);
                }
            });
            db.close();
            onBackPressed();
            finish();
        });
    }
}
