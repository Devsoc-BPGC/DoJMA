package com.csatimes.dojma;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.csatimes.dojma.adapters.LinkRv;
import com.csatimes.dojma.models.LinkItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.realm.Realm;
import io.realm.RealmList;

import static android.content.Intent.ACTION_VIEW;

/**
 * Created by yash on 21/7/16.
 */

public class UtilitiesLinksActivity extends AppCompatActivity implements LinkRv.OnLinkClickedListener {


    private LinkRv adapter;
    private Realm database;
    private DatabaseReference linksReference = FirebaseDatabase.getInstance().getReference().child
            ("links");
    private TextView emptyList;
    private ValueEventListener linksListener;
    private RealmList<LinkItem> linkItems;

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_links);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_links_toolbar);
        emptyList = (TextView) findViewById(R.id.content_links_empty_text);
        RecyclerView linkRecyclerView = (RecyclerView) findViewById(R.id.content_links_rv);

        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Useful Links");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //These flags are for system bar on top
        //Don't bother yourself with this code
        Window window = this.getWindow();
        // clear FLAG_TRANSLUCENT_STATUS flag:
        if (Build.VERSION.SDK_INT >= 19) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        if (Build.VERSION.SDK_INT >= 21) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
         }

        linkRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        linkRecyclerView.setHasFixedSize(true);
        linkRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        database = Realm.getDefaultInstance();

        linkItems = new RealmList<>();
        linkItems.addAll(database.where(LinkItem.class).findAll());

        adapter = new LinkRv(linkItems);
        linkRecyclerView.setAdapter(adapter);

        adapter.setOnLinkClickedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        linksListener = returnListener();
        linksReference.addValueEventListener(linksListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        linksReference.removeEventListener(linksListener);
        database.close();
    }

    private ValueEventListener returnListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emptyList.setVisibility(View.GONE);
                database.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(LinkItem.class);
                    }
                });

                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    try {
                        final LinkItem foo = shot.getValue(LinkItem.class);
                        database.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                LinkItem bar = realm.createObject(LinkItem.class);
                                bar.setTitle(foo.getTitle());
                                bar.setUrl(foo.getUrl());
                            }
                        });
                    } catch (Exception ignore) {
                        DHC.log("parse error in link item");
                    }
                }

                linkItems.clear();
                linkItems.addAll(database.where(LinkItem.class).findAll());
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    @Override
    public void onClick(String url) {
        Intent intent = new Intent(ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (!database.isClosed())
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                Snackbar.make(findViewById(R.id.activity_links_cl), "FTP viewer not found", Snackbar.LENGTH_SHORT).show();
            } catch (Exception e) {
                FirebaseCrash.report(new Exception("Links error occured for url " + url + " \n" + e.getMessage()));
            }
    }
}
