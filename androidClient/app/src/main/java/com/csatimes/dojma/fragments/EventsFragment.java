package com.csatimes.dojma.fragments;

import android.os.Bundle;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

public class EventsFragment extends Fragment {

    public static final String TAG = "fragments.EventsFragment";
    private final DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference()
            .child(DHC.FIREBASE_DATABASE_REFERENCE_EVENTS);
    private EventsRV mEventsAdapter;
    private TextView mErrorText;
    private RealmList<EventItem> mEventItems;
    private Realm mDatabase;
    private RecyclerView mEventsRecyclerView;
    private ValueEventListener mEventListener;
    private OnTitleUpdateListener onTitleUpdateListener;

    public static Fragment newInstance() {
        EventsFragment eventsFragment=new EventsFragment();
        return eventsFragment;
    }

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEventsRecyclerView = view.findViewById(R.id.events_recycler_view);
        mErrorText = view.findViewById(R.id.error_text_view);

        mEventsRecyclerView.setHasFixedSize(true);
        mEventsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onStart() {
        super.onStart();

        // As mentioned in Realm Docs, the realm should be instantiated in the
        // onStart method. and because of the late initialising of the realm,
        // mEventsAdapter is placed after it so that
        // mEventsAdapter.notifyDataSetChanged() works
        mDatabase = Realm.getDefaultInstance();

        mEventItems = new RealmList<>();
        //noinspection DuplicateStringLiteralInspection
        final String fields[] = {"time", "title", "location", "desc", "key"};
        final Sort sortOrders[] = {Sort.ASCENDING, Sort.ASCENDING,
                Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING};
        mEventItems.addAll(mDatabase.where(EventItem.class)
                .sort(fields, sortOrders)
                .findAll());

        mEventsAdapter = new EventsRV(getContext(), mEventItems, Calendar.getInstance().getTime());
        mEventsRecyclerView.setAdapter(mEventsAdapter);

        mEventListener = returnValueListener();
        eventsRef.addValueEventListener(mEventListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        assert getContext() != null;
        if (DHC.isOnline(getContext())) {
            mErrorText.setVisibility(View.GONE);
        } else {
            mErrorText.setVisibility(View.VISIBLE);
        }
        if (onTitleUpdateListener != null) {        //noinspection DuplicateStringLiteralInspection

            onTitleUpdateListener.onTitleUpdate("Events(" + getCount() + ")",
                    DHC.MAIN_ACTIVITY_EVENTS_POS);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        eventsRef.removeEventListener(mEventListener);
        mDatabase.close();
    }

    @SuppressWarnings("FeatureEnvy")
    private ValueEventListener returnValueListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(@NonNull final Realm realm) {
                        realm.delete(EventItem.class);
                        for (final DataSnapshot child :
                                dataSnapshot.getChildren()) {
                            try {
                                final String key = child.getKey();
                                final EventItem item = child.getValue(EventItem.class);
                                if (item == null) continue;
                                item.setKey(key);
                                item.setDateTime(item.getStartDate() + item.getStartTime());
                                realm.insertOrUpdate(item);
//                                EventItem       bar  = realm.createObject(EventItem.class, key);
//                                bar.setTitle(item.getTitle());
//                                bar.setLocation(item.getLocation());
//                                bar.setDesc(item.getDesc());
//                                bar.setDateTime(item.getStartDate() + item.getStartTime());
                            } catch (final Exception e) {
                                DHC.e(TAG, "parse error of event in Events");
                            }
                        }
                    }
                });
                /*for (final DataSnapshot child : dataSnapshot.getChildren()) {
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
                    } catch (final Exception e) {
                        DHC.e(TAG, "parse error of event in Events");
                    }
                }*/
                if (onTitleUpdateListener != null) {
                    //noinspection DuplicateStringLiteralInspection
                    onTitleUpdateListener.onTitleUpdate("Events(" + getCount() + ")",
                            DHC.MAIN_ACTIVITY_EVENTS_POS);
                }
                mEventItems.clear();
                mEventItems.addAll(
                        mDatabase.where(EventItem.class)
                                .sort("time", Sort.ASCENDING)
                                .findAll());
                mEventsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(final DatabaseError error) {
                DHC.e(TAG, "database Error " + error.getMessage());
            }
        };
    }

    private int getCount() {
        return mEventsAdapter != null ? mEventsAdapter.getItemCount() : 0;
    }

    public void setOnTitleUpdateListener(final OnTitleUpdateListener listener) {
        this.onTitleUpdateListener = listener;
    }
}
