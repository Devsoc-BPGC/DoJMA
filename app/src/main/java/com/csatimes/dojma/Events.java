package com.csatimes.dojma;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;
import io.realm.RealmResults;

public class Events extends Fragment implements View.OnClickListener {

    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");
    private EventsRV adapter;
    private TextView errorText;
    private RealmResults<EventItem> eventItems;
    private Realm database;
    private RecyclerView eventsRV;

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
        //As mentioned in Realm Docs, the realm should be instantiated in the onStart method
        //Because of the late initialising of the realm, adapter is placed after it so that adapter.notifyDataSetChanged() works
        database = Realm.getDefaultInstance();

        eventItems = database.where(EventItem.class).findAll();

        adapter = new EventsRV(getContext(), eventItems, Calendar.getInstance().getTime());
        eventsRV.setAdapter(adapter);

        eventsRef.addValueEventListener(new ValueEventListener() {
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
                sortThisShit();
                adapter.notifyDataSetChanged();
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isOnline()) {
            errorText.setText(R.string.message_events_stay_connected);
            errorText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
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

    private void sortThisShit() {
        Collections.sort(eventItems, new Comparator<EventItem>() {

            @Override
            public int compare(EventItem e1, EventItem e2) {
                try {
                    Date one = new SimpleDateFormat("ddMMyyyy", Locale.UK).parse(e1.getStartDate());
                    Date two = new SimpleDateFormat("ddMMyyyy", Locale.UK).parse(e2.getStartDate());
                    if (one.getTime() - two.getTime() < 0) {
                        return -1;
                    } else if (one.getTime() == two.getTime()) {
                        if (!e1.getStartTime().equalsIgnoreCase("-") && e2.getStartTime()
                                .equalsIgnoreCase("-")) {
                            Log.e("TAG", "same day e1 -1 " + e1.getStartTime());
                            return -1;
                        } else if (e1.getStartTime().equalsIgnoreCase("-") && !e2.getStartTime()
                                .equalsIgnoreCase("-")) {
                            Log.e("TAG", "same day e2 1 " + e2.getStartTime());
                            return 1;
                        } else if (e1.getStartTime().equalsIgnoreCase("-") && e2.getStartTime()
                                .equalsIgnoreCase("-")) {
                            // can further diff between location maybe?
                            return 0;
                        } else {
                            Date onee = new SimpleDateFormat("HHmm", Locale.UK).parse(e1
                                    .getStartTime());
                            Date twoo = new SimpleDateFormat("HHmm", Locale.UK).parse(e2
                                    .getStartTime());

                            if (onee.getTime() - twoo.getTime() < 0) {
                                return -1;
                            } else if (onee.getTime() == twoo.getTime()) {
                                return 0;
                                //further diff here
                            } else {
                                return 1;
                            }
                        }
                    } else {
                        return 1;
                    }
                } catch (Exception ignore) {
                    Log.e("TAG", "Events compare exception ");
                }

                return 0;
            }
        });
    }

}
