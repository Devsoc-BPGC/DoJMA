package com.csatimes.dojma.activities;

import android.os.Bundle;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ShortsVerticalViewPagerAdapter;
import com.csatimes.dojma.models.ShortsItem;
import com.csatimes.dojma.viewholders.ShortsVerticalViewPager;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class ShortsActivity extends AppCompatActivity {

    /*private String ref = "campusWatch";
    private DatabaseReference mDatabaseReference;

    private ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
            ShortsItem shortsItem = dataSnapshot.getValue(ShortsItem.class);
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);

        //mDatabaseReference = FirebaseDatabase.getInstance().getReference(ref);

        initSwipePager();
    }

    private void initSwipePager() {
        ShortsVerticalViewPager shortsVerticalViewPager = (ShortsVerticalViewPager) findViewById(R.id.viewpager_shorts);
        shortsVerticalViewPager.setAdapter(new ShortsVerticalViewPagerAdapter(this));
    }
}
