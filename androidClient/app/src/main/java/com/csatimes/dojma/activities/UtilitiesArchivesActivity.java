package com.csatimes.dojma.activities;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ArchiveAdapter;
import com.csatimes.dojma.models.Archive;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;

public class UtilitiesArchivesActivity extends BaseActivity {

    public static final String TAG = UtilitiesArchivesActivity.class.getSimpleName();
    private Realm mDatabase;
    private ArchiveAdapter mRVAdapter;
    private TextView emptyTextView;
    private List<Archive> mArchiveItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archives);

        final Toolbar toolbar = findViewById(R.id.activity_archives_toolbar);
        final RecyclerView mArchiveRecyclerView = findViewById(R.id.activity_archive_rv);
        emptyTextView = findViewById(R.id.textView_content_archive_empty);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDatabase = Realm.getDefaultInstance();
        mArchiveItems = new ArrayList<>(0);
        mArchiveItems.addAll(mDatabase.where(Archive.class).findAll());
        mDatabase.close();
        if (mArchiveItems.isEmpty()) emptyTextView.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference()
                .child("archive").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mArchiveItems.clear();
                        for (final DataSnapshot child : dataSnapshot.getChildren()) {
                            Realm mDatabase = Realm.getDefaultInstance();
                            final Archive archive = child.getValue(Archive.class);
                            mDatabase.executeTransaction(realm -> realm.insertOrUpdate(archive));
                            mArchiveItems.add(archive);
                        }
                        if (mArchiveItems.isEmpty()) {
                            emptyTextView.setVisibility(View.VISIBLE);
                        } else {
                            emptyTextView.setVisibility(View.GONE);
                        }
                        mRVAdapter.notifyDataSetChanged();
                        mDatabase.close();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e(TAG, databaseError.getMessage(), databaseError.toException());
                    }
                });

        mRVAdapter = new ArchiveAdapter(mArchiveItems);
        mArchiveRecyclerView.setAdapter(mRVAdapter);
        mArchiveRecyclerView.setLayoutManager(new GridLayoutManager(this, span()));
        mArchiveRecyclerView.setHasFixedSize(true);
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


}
