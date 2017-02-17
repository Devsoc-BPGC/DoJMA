package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.EventsRV;
import com.csatimes.dojma.interfaces.OnTitleUpdateListener;
import com.csatimes.dojma.models.EventItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

public class Events extends Fragment {

    public static final String TAG = "fragments.Events";
    private DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events2");
    private EventsRV mEventsAdapter;
    private TextView mErrorText;
    private RealmList<EventItem> mEventItems;
    private Realm mDatabase;
    private RecyclerView mEventsRecyclerView;
    private ChildEventListener mEventChildListener;
    private OnTitleUpdateListener onTitleUpdateListener;

    public Events() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        mEventsRecyclerView = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        mErrorText = (TextView) view.findViewById(R.id.error_text_view);

        mEventsRecyclerView.setHasFixedSize(true);
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

        //As mentioned in Realm Docs, the realm should be instantiated in the onStart method.
        //and because of the late initialising of the realm, mEventsAdapter is placed after it so that mEventsAdapter.notifyDataSetChanged() works
        mDatabase = Realm.getDefaultInstance();

        mEventItems = new RealmList<>();
        mEventItems.addAll(mDatabase.where(EventItem.class).
                findAllSorted(new String[]{"time", "title", "location", "desc", "key"},
                        new Sort[]{Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING}));

        mEventsAdapter = new EventsRV(getContext(), mEventItems, Calendar.getInstance().getTime());
        mEventsRecyclerView.setAdapter(mEventsAdapter);

        mEventChildListener = returnChildrenListener();
        eventsRef.addChildEventListener(mEventChildListener);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!DHC.isOnline(getContext())) {
            mErrorText.setVisibility(View.VISIBLE);
        } else {
            mErrorText.setVisibility(View.GONE);
        }
        if (onTitleUpdateListener != null)
            onTitleUpdateListener.onTitleUpdate("Events(" + getCount() + ")", DHC.MAIN_ACTIVITY_EVENTS_POS);
    }

    private ChildEventListener returnChildrenListener() {
        return new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    final String key = dataSnapshot.getKey();
                    final EventItem foo = dataSnapshot.getValue(EventItem.class);
                    EventItem bar = mDatabase.where(EventItem.class).equalTo("key", key).findFirst();
                    if (bar == null) {
                        mDatabase.executeTransaction(
                                new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        EventItem bar = realm.createObject(EventItem.class, key);
                                        bar.setTitle(foo.getTitle());
                                        bar.setLocation(foo.getLocation());
                                        bar.setDesc(foo.getDesc());
                                        bar.setStartDate(foo.getStartDate());
                                        bar.setStartTime(foo.getStartTime());
                                        bar.setTime(bar.getTime());
                                    }
                                }
                        );
                        mEventItems.add(foo);
                        mEventsAdapter.notifyDataSetChanged();
                        if (onTitleUpdateListener != null)
                            onTitleUpdateListener.onTitleUpdate("Events(" + getCount() + ")", DHC.MAIN_ACTIVITY_EVENTS_POS);
                    } else {
                        onChildChanged(dataSnapshot, s);
                    }
                } catch (Exception e) {
                    DHC.log(TAG,"parse error of event in Events");
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                final String key = dataSnapshot.getKey();
                EventItem baz = mDatabase.where(EventItem.class).equalTo("key", key).findFirst();
                if (baz == null)
                    onChildAdded(dataSnapshot, s);
                else
                    //Parse error could occur
                    try {
                        final EventItem foo = dataSnapshot.getValue(EventItem.class);
                        mDatabase.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                EventItem bar = realm.where(EventItem.class).equalTo("key", key).findFirst();
                                bar.setDesc(foo.getDesc());
                                bar.setLocation(foo.getLocation());
                                bar.setStartDate(foo.getStartDate());
                                bar.setStartTime(foo.getStartTime());
                                bar.setTitle(foo.getTitle());
                                bar.setTime(bar.getTime());
                            }
                        });
                        mEventsAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        DHC.log(TAG,"parse error while trying to update event data at key " + dataSnapshot.getKey() + "\n" + e.getMessage());
                        e.printStackTrace();
                    }
            }

            @Override
            public void onChildRemoved(final DataSnapshot dataSnapshot) {

                EventItem foo = mDatabase.where(EventItem.class).equalTo("key", dataSnapshot.getKey()).findFirst();
                if (foo != null) {
                    int position = mEventItems.indexOf(foo);
                    if (position > -1) {
                        mEventItems.remove(position);
                        mEventsAdapter.notifyItemRemoved(position);
                        mDatabase.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                realm.where(EventItem.class).equalTo("key", dataSnapshot.getKey()).findFirst().deleteFromRealm();
                            }
                        });
                        if (onTitleUpdateListener != null)
                            onTitleUpdateListener.onTitleUpdate("Events(" + getCount() + ")", DHC.MAIN_ACTIVITY_EVENTS_POS);

                    } else DHC.log(TAG,"position " + position);
                } else DHC.log(TAG,"Deleted item was not in database ");
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                DHC.log(TAG,dataSnapshot.getKey() + " has been moved" + s);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log(TAG,"database Error " + databaseError.getMessage());
            }
        };
    }

    public int getCount() {
        return mEventsAdapter.getItemCount();
    }

    @Override
    public void onPause() {
        super.onPause();
        eventsRef.removeEventListener(mEventChildListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        mDatabase.close();
    }

    public void setOnTitleUpdateListener(OnTitleUpdateListener onTitleUpdateListener) {
        this.onTitleUpdateListener = onTitleUpdateListener;
    }
}
