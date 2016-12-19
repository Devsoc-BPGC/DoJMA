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
import android.widget.Toast;

import com.csatimes.dojma.adapters.EventsRV;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

public class Events extends Fragment implements EventsRV.OnAlarmSetListener {

    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events2");
    private EventsRV adapter;
    private TextView errorText;
    private RealmResults<EventItem> eventItems;
    private Realm database;
    private RecyclerView eventsRV;
    private ChildEventListener eventChildListener;

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
        eventsRV.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        //As mentioned in Realm Docs, the realm should be instantiated in the onStart method.
        //and because of the late initialising of the realm, adapter is placed after it so that adapter.notifyDataSetChanged() works
        database = Realm.getDefaultInstance();

        eventItems = database.where(EventItem.class).
                findAllSorted(new String[]{"time", "title", "location", "desc"},
                        new Sort[]{Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING});

        adapter = new EventsRV(getContext(), eventItems, Calendar.getInstance().getTime());
        eventsRV.setAdapter(adapter);

        adapter.setOnAlarmSetListener(this);
        eventChildListener = returnChildrenListener();
        eventsRef.addChildEventListener(eventChildListener);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (!isOnline()) {
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
        }

    }

    private ChildEventListener returnChildrenListener() {
        return new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    final String key = dataSnapshot.getKey();
                    final EventItem foo = dataSnapshot.getValue(EventItem.class);
                    database.executeTransaction(
                            new Realm.Transaction() {
                                @Override
                                public void execute(Realm realm) {
                                    EventItem bar = realm.where(EventItem.class).equalTo("key", key).findFirst();
                                    if (bar == null) {
                                        {
                                            bar = realm.createObject(EventItem.class, key);
                                            bar.setAlarm(false);
                                        }
                                    }
                                    bar.setTitle(foo.getTitle());
                                    bar.setLocation(foo.getLocation());
                                    bar.setDesc(foo.getDesc());
                                    bar.setStartDate(foo.getStartDate());
                                    bar.setStartTime(foo.getStartTime());
                                    bar.setEndDate(foo.getEndDate());
                                    bar.setEndTime(foo.getEndTime());
                                    bar.setTime(bar.getTime());
                                }
                            }
                    );
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    DHC.log("parse error of event in Events");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                final String key = dataSnapshot.getKey();
                EventItem baz = database.where(EventItem.class).equalTo("key", key).findFirst();
                if (baz == null)
                    onChildAdded(dataSnapshot, s);
                else
                    //Parse error could occur
                    try {
                        final EventItem foo = dataSnapshot.getValue(EventItem.class);
                        final int pos = eventItems.indexOf(baz);
                        database.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                EventItem bar = realm.where(EventItem.class).equalTo("key", key).findFirst();
                                bar.setDesc(foo.getDesc());
                                bar.setEndDate(foo.getEndDate());
                                bar.setEndTime(foo.getEndTime());
                                bar.setLocation(foo.getLocation());
                                bar.setStartDate(foo.getStartDate());
                                bar.setStartTime(foo.getStartTime());
                                bar.setTitle(foo.getTitle());
                                bar.setTime(bar.getTime());

                            }
                        });
                        adapter.notifyItemChanged(pos);
                        adapter.notifyItemChanged(0);
                        adapter.notifyItemChanged(adapter.getItemCount() - 1);

                    } catch (Exception e) {
                        DHC.log("parse error while trying to update event data at key " + s);
                    }
            }

            @Override
            public void onChildRemoved(final DataSnapshot dataSnapshot) {

                EventItem foo = database.where(EventItem.class).equalTo("key", dataSnapshot.getKey()).findFirst();

                DHC.log("data key " + dataSnapshot.getKey());

                if (foo != null) {
                    int position = eventItems.indexOf(foo);
                    database.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            realm.where(EventItem.class).equalTo("key", dataSnapshot.getKey()).findFirst().deleteFromRealm();
                        }
                    });
                    adapter.notifyItemRemoved(position);
                } else DHC.log("Deleted item was not in database ");
                try {
                    adapter.notifyItemChanged(0);
                    adapter.notifyItemChanged(adapter.getItemCount() - 1);
                } catch (Exception e) {
                    DHC.log("expected error in deleting event");
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                DHC.log(dataSnapshot.getKey() + " " + s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    public void onPause() {
        super.onPause();
        eventsRef.removeEventListener(eventChildListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        database.close();
    }

    @Override
    public void onItemClicked(final String key) {
        database.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                EventItem foo = realm.where(EventItem.class).equalTo("key", key).findFirst();
                if (foo != null) {
                    if (foo.isAlarmSet()) {
                        foo.setAlarm(false);
                        Toast.makeText(getContext(), "Alarm removed", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        foo.setAlarm(true);
                        Toast.makeText(getContext(), "Alarm set", Toast.LENGTH_SHORT).show();
                        //TODO Set alarm
                    }
                }
            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }


}
