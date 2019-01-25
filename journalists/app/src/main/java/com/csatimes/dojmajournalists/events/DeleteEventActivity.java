package com.csatimes.dojmajournalists.events;

import android.os.Bundle;

import com.csatimes.dojmajournalists.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojmajournalists.login.LoginActivity.checkLogin;
import static com.csatimes.dojmajournalists.utils.FirebaseKeys.NODE_EVENTS;
import static com.csatimes.dojmajournalists.utils.Jhc.getFirebaseRef;

public class DeleteEventActivity extends AppCompatActivity {
    private List<EventModel> eventList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_event);

        RecyclerView eventRv = findViewById(R.id.rv_event);
        DatabaseReference databaseReference = getFirebaseRef().child(NODE_EVENTS);
        databaseReference.keepSynced(true);
        eventRv.setHasFixedSize(true);
        eventList = new ArrayList<>();
        DeleteEventAdapter adapter = new DeleteEventAdapter(eventList, this);
        eventRv.setItemAnimator(new DefaultItemAnimator());
        eventRv.setAdapter(adapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EventModel eventModel = snapshot.getValue(EventModel.class);
                    eventList.add(eventModel);
                    adapter.notifyDataSetChanged();
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
        checkLogin(this);
    }
}
