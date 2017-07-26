package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private ValueEventListener mEventListener;
    private OnTitleUpdateListener onTitleUpdateListener;

    public Events() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEventsRecyclerView = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        mErrorText = (TextView) view.findViewById(R.id.error_text_view);

        mEventsRecyclerView.setHasFixedSize(true);
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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

        mEventListener = returnValueListener();
        eventsRef.addValueEventListener(mEventListener);
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

    private ValueEventListener returnValueListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(EventItem.class);
                    }
                });
                for (final DataSnapshot child : dataSnapshot.getChildren()) {
                    try {
                        final String key = child.getKey();
                        final EventItem foo = child.getValue(EventItem.class);
                        mDatabase.executeTransaction(
                                new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        EventItem bar = realm.createObject(EventItem.class, key);
                                        bar.setTitle(foo.getTitle());
                                        bar.setLocation(foo.getLocation());
                                        bar.setDesc(foo.getDesc());
                                        bar.setDateTime(foo.getStartDate() + foo.getStartTime());
                                    }
                                }
                        );
                    } catch (Exception e) {
                        DHC.e(TAG, "parse error of event in Events");
                    }
                }
                if (onTitleUpdateListener != null)
                    onTitleUpdateListener.onTitleUpdate("Events(" + getCount() + ")", DHC.MAIN_ACTIVITY_EVENTS_POS);
                mEventItems.clear();
                mEventItems.addAll(mDatabase.where(EventItem.class).findAllSorted("time", Sort.ASCENDING));
                mEventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.e(TAG, "database Error " + databaseError.getMessage());
            }
        };
    }

    public int getCount() {
        return mEventsAdapter != null ? mEventsAdapter.getItemCount() : 0;
    }

    @Override
    public void onStop() {
        super.onStop();
        eventsRef.removeEventListener(mEventListener);
        mDatabase.close();
    }

    public void setOnTitleUpdateListener(OnTitleUpdateListener onTitleUpdateListener) {
        this.onTitleUpdateListener = onTitleUpdateListener;
    }
}
