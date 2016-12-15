package com.csatimes.dojma;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.adapters.EventsRV;
import com.csatimes.dojma.models.EventItem;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;

public class Events extends Fragment implements View.OnClickListener {

    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");
    private EventsRV adapter;
    private TextView errorText;
    private RealmResults<EventItem> eventItems;
    private Realm database;
    private RecyclerView eventsRV;
    private ValueEventListener eventListener;

    public Events() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        eventsRV = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        errorText = (TextView) view.findViewById(R.id.error_text_view);

        eventsRV.setHasFixedSize(true);
        eventsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        errorText.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!isOnline()) {
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
        }
        //As mentioned in Realm Docs, the realm should be instantiated in the onStart method
        //Because of the late initialising of the realm, adapter is placed after it so that adapter.notifyDataSetChanged() works
        database = Realm.getDefaultInstance();

        eventItems = database.where(EventItem.class).findAll();

        adapter = new EventsRV(getContext(), eventItems, Calendar.getInstance().getTime());
        eventsRV.setAdapter(adapter);

        eventListener = returnListener();
        eventsRef.addValueEventListener(eventListener);

    }

    private ValueEventListener returnListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                errorText.setVisibility(View.GONE);

                database.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(EventItem.class);
                    }
                });
                for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                    try {

                        final EventItem foo = childDataSnapshot.getValue(EventItem.class);
                        database.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                EventItem bar = realm.createObject(EventItem.class);
                                bar.setDesc(foo.getDesc());
                                bar.setEndDate(foo.getEndDate());
                                bar.setEndTime(foo.getEndTime());
                                bar.setLocation(foo.getLocation());
                                bar.setStartDate(foo.getStartDate());
                                bar.setStartTime(foo.getStartTime());
                                bar.setTitle(foo.getTitle());
                            }
                        });
                    } catch (Exception ignore) {
                    }
                }

                eventItems = database.where(EventItem.class).findAll();
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        eventsRef.removeEventListener(eventListener);
        database.close();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == errorText.getId()) {
            errorText.setVisibility(View.GONE);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

}
