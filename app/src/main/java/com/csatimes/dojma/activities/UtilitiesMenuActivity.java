package com.csatimes.dojma.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.MessAdapter;
import com.csatimes.dojma.adapters.SearchAdapter;
import com.csatimes.dojma.models.MessItem;
import com.csatimes.dojma.utilities.DHC;
import com.facebook.drawee.view.SimpleDraweeView;
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

public class UtilitiesMenuActivity extends BaseActivity implements SearchAdapter.OnImageClicked {


    private DatabaseReference mMessRef = FirebaseDatabase.getInstance().getReference().child("mess");
    private ValueEventListener mMessEventListener;
    private Realm mDatabase;
    private MessAdapter mRVAdapter;
    private RealmList<MessItem> mMessItems;
    private TextView emptyTextView;
    private SimpleDraweeView simpleDraweeView;
    private GestureFrameLayout gestureFrameLayout;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menus);

        Toolbar toolbar = (Toolbar) findViewById(R.id.activity_mess_menus_toolbar);
        simpleDraweeView = (SimpleDraweeView) findViewById(R.id.imageView_content_mess_large);
        emptyTextView = (TextView) findViewById(R.id.textView_content_mess_empty);
        RecyclerView mMessRecyclerView = (RecyclerView) findViewById(R.id.activity_mess_menu_rv);
        gestureFrameLayout = (GestureFrameLayout) findViewById(R.id.gesture_frame_mess);

        gestureFrameLayout.getController().getSettings().setOverzoomFactor(10);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mMessRecyclerView.setLayoutManager(new GridLayoutManager(this, span()));
        mMessRecyclerView.setHasFixedSize(true);
        mDatabase = Realm.getDefaultInstance();
        mMessItems = new RealmList<>();
        mMessItems.addAll(mDatabase.where(MessItem.class).findAll());
        mRVAdapter = new MessAdapter(mMessItems, this);

        mMessRecyclerView.setAdapter(mRVAdapter);
        if (mMessItems.isEmpty()) emptyTextView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMessEventListener = getListener();
        mMessRef.addValueEventListener(mMessEventListener);
        if (mMessItems.isEmpty()) emptyTextView.setVisibility(View.VISIBLE);
        else emptyTextView.setVisibility(View.GONE);
    }

    private ValueEventListener getListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
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
                        DHC.log("Parse exception in MessItem " + e.getMessage());
                    }
                }
                mMessItems.addAll(mDatabase.where(MessItem.class).findAll());
                if (mMessItems.isEmpty()) emptyTextView.setVisibility(View.VISIBLE);
                else emptyTextView.setVisibility(View.GONE);
                mRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                DHC.log("database error " + databaseError.getMessage());
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMessRef.removeEventListener(mMessEventListener);
        if (mMessItems.isEmpty()) emptyTextView.setVisibility(View.VISIBLE);
        else emptyTextView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    boolean imageShown = false;

    @Override
    public void onBackPressed() {
        if (imageShown) {
            Log.e("TAG", "on back pressed 1");
            onClicked(null);
            imageShown = false;
        } else {
            Log.e("TAG", "on back pressed 2");
            super.onBackPressed();
        }
    }

    @Override
    public void onClicked(String uri) {

        if (imageShown) {
            gestureFrameLayout.setVisibility(View.GONE);
            imageShown = false;
        } else {
            imageShown = true;
            gestureFrameLayout.setVisibility(View.VISIBLE);
            simpleDraweeView.setImageURI(Uri.parse(uri));
        }
    }
}
