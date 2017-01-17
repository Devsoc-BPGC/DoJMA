package com.csatimes.dojma.activities;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.MessAdapter;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by vikramaditya on 15/12/16.
 */

public class UtilitiesMenuActivity extends AppCompatActivity {


    private RecyclerView mMessRecyclerView;
    private DatabaseReference mMessRef = FirebaseDatabase.getInstance().getReference().child("mess");
    private ValueEventListener mMessEventListener;
    private Realm mDatabase;
    private MessAdapter mRVAdapter;
    private RealmList<MessItem> mMessItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_mess_menus_toolbar);
        mMessRecyclerView = (RecyclerView) findViewById(R.id.activity_mess_menu_rv);

        setSupportActionBar(toolbar);

        mMessRecyclerView.setLayoutManager(new GridLayoutManager(this, span()));
        mMessRecyclerView.setHasFixedSize(true);


    }

    @Override
    protected void onStart() {
        super.onStart();

        mDatabase = Realm.getDefaultInstance();
        mMessItems = new RealmList<>();
        mMessItems.addAll(mDatabase.where(MessItem.class).findAll());
        mRVAdapter = new MessAdapter(mMessItems);

        mMessRecyclerView.setAdapter(mRVAdapter);

        mMessEventListener = getListener();
        mMessRef.addValueEventListener(mMessEventListener);
    }

    private ValueEventListener getListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(MessItem.class);
                    }
                });
                mMessItems.clear();
                DHC.log("Child count " + dataSnapshot.getChildrenCount());
                for (DataSnapshot childShot : dataSnapshot.getChildren()) {
                    try {
                        final MessItem messItem = childShot.getValue(MessItem.class);

                        mDatabase.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                MessItem mi = realm.createObject(MessItem.class);
                                mi.setTitle(messItem.getTitle());
                                mi.setImageUrl(messItem.getImageUrl());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        DHC.log("Parse exception in MessItem");
                    }
                }
                mMessItems.addAll(mDatabase.where(MessItem.class).findAll());
                mRVAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log("database error " + databaseError.getMessage());
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMessRef.removeEventListener(mMessEventListener);
        mDatabase.close();
    }

    private int span() {

        //Setup columns according to device screen
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up grid
        int num = 180;
        float t = dpWidth / num;
        float r = dpWidth % num;
        int cols;
        if (r < 0.1 * num)
            cols = (int) Math.ceil(dpWidth / num);
        else
            cols = (int) t;

        return cols;
    }

    private void setTheme() {
        boolean mode = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getString(R.string.PREFERENCE_general_night_mode), false);
        if (mode)
            setTheme(R.style.AppThemeDark);
        else {
            setTheme(R.style.AppTheme);
        }
    }

}
