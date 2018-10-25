package com.csatimes.dojmajournalists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteEvent extends AppCompatActivity {
    private List<Event> eventList = new ArrayList<>();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);

        mAuth = FirebaseAuth.getInstance();
        RecyclerView recyclerView = findViewById(R.id.recycler_event);
        FirebaseDatabase fData = Utils.getDatabase();
        DatabaseReference databaseReference = fData.getReference("events2");
        databaseReference.keepSynced(true);
        recyclerView.setHasFixedSize(true);
        eventList = new ArrayList<>();
        DeleteEventAdapter mAdapter = new DeleteEventAdapter(eventList, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventsPuller dataPuller = snapshot.getValue(EventsPuller.class);
                    Event listItem = new Event(
                            dataPuller.getTitle()
                    );
                    eventList.add(listItem);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Intent i = new Intent(DeleteEvent.this, LoginActivity.class);
            startActivity(i);
        }
    }
}
