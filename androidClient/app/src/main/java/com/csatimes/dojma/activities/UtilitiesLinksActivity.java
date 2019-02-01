package com.csatimes.dojma.activities;

import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.LinkRv;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;

/**
 * Links Activity under the Utilities Section
 */

public class UtilitiesLinksActivity extends BaseActivity {

    public final String TAG = "activities." + UtilitiesLinksActivity.class.getSimpleName();

    private LinkRv adapter;
    private Realm mDatabase;
    private DatabaseReference mLinksReference = FirebaseDatabase.getInstance().getReference().child
            ("links");
    private TextView emptyList;
    private ValueEventListener linksListener;
    private RealmList<LinkItem> linkItems;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);

        Toolbar toolbar = findViewById(R.id.activity_links_toolbar);
        emptyList = findViewById(R.id.content_links_empty_text);
        RecyclerView linkRecyclerView = findViewById(R.id.content_links_rv);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        }

        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linkRecyclerView.setHasFixedSize(true);
        linkRecyclerView.addItemDecoration(new DividerItemDecoration(this, VERTICAL));

        mDatabase = Realm.getDefaultInstance();
        linkItems = new RealmList<>();
        linkItems.addAll(mDatabase.where(LinkItem.class).findAll());
        if (linkItems.size() > 0) emptyList.setVisibility(GONE);

        adapter = new LinkRv(linkItems, this);
        linkRecyclerView.setAdapter(adapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        linksListener = returnListener();
        mLinksReference.addValueEventListener(linksListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mLinksReference.removeEventListener(linksListener);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }

    private ValueEventListener returnListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.executeTransaction(realm -> realm.delete(LinkItem.class));

                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    final LinkItem foo = shot.getValue(LinkItem.class);
                    if (foo == null) {
                        continue;
                    }
                    mDatabase.executeTransaction(realm -> {
                        LinkItem bar = realm.createObject(LinkItem.class);
                        bar.setTitle(foo.getTitle());
                        bar.setUrl(foo.getUrl());
                    });
                }

                linkItems.clear();
                linkItems.addAll(mDatabase.where(LinkItem.class).findAll());
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() == 0) {
                    emptyList.setVisibility(VISIBLE);
                } else {
                    emptyList.setVisibility(GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.e(TAG, "database error " + databaseError.getMessage() + " " + databaseError.getDetails());
            }
        };
    }

}
