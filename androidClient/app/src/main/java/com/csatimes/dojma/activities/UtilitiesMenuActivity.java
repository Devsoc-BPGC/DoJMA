package com.csatimes.dojma.activities;

import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alexvasilkov.gestures.views.GestureFrameLayout;
import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.MessAdapter;
import com.csatimes.dojma.adapters.SearchAdapter;
import com.csatimes.dojma.models.MessItem;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;

/**
 * Created by vikramaditya on 15/12/16.
 */

public class

UtilitiesMenuActivity extends BaseActivity implements SearchAdapter.OnImageClicked {

    private static final String TAG = UtilitiesMenuActivity.class.getSimpleName();
    private final DatabaseReference mMessRef = FirebaseDatabase.getInstance().getReference()
            .child("mess");
    boolean imageShown = false;
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

        final Toolbar toolbar = findViewById(R.id.activity_mess_menus_toolbar);
        simpleDraweeView = findViewById(R.id.imageView_content_mess_large);
        emptyTextView = findViewById(R.id.textView_content_mess_empty);
        final RecyclerView mMessRecyclerView = findViewById(R.id.activity_mess_menu_rv);
        gestureFrameLayout = findViewById(R.id.gesture_frame_mess);

        gestureFrameLayout.getController().getSettings().setOverzoomFactor(10);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
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

    private int span() {
        //Setup columns according to device screen
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up grid
        final int num = 180;
        final float t = dpWidth / num;
        final float r = dpWidth % num;
        final int cols;
        cols = r < 0.1 * num
                ? (int) Math.ceil(dpWidth / num)
                : (int) t;

        return cols;
    }

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
    public void onClicked(final String uri) {

        if (imageShown) {
            gestureFrameLayout.setVisibility(View.GONE);
            imageShown = false;
        } else {
            imageShown = true;
            gestureFrameLayout.setVisibility(View.VISIBLE);
            simpleDraweeView.setImageURI(Uri.parse(uri));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mMessEventListener = getListener();
        mMessRef.addValueEventListener(mMessEventListener);
        if (mMessItems.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
    }

    private ValueEventListener getListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mDatabase.executeTransaction(realm -> realm.delete(MessItem.class));
                mMessItems.clear();
                for (final DataSnapshot childShot : dataSnapshot.getChildren()) {
                    final MessItem messItem = childShot.getValue(MessItem.class);
                    if (messItem == null) {
                        continue;
                    }
                    mDatabase.executeTransaction(realm -> realm.insert(messItem));
                }
                mMessItems.addAll(mDatabase.where(MessItem.class).findAll());
                if (mMessItems.isEmpty()) {
                    emptyTextView.setVisibility(View.VISIBLE);
                } else {
                    emptyTextView.setVisibility(View.GONE);
                }
                mRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(final DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage(), databaseError.toException());
            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        mMessRef.removeEventListener(mMessEventListener);
        if (mMessItems.isEmpty()) {
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDatabase.close();
    }
}
