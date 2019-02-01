package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.EventsRV;
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
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

public class EventsFragment extends Fragment {

    public static final String TAG = "fragments.EventsFragment";
    private final DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference()
            .child(DHC.FIREBASE_DATABASE_REFERENCE_EVENTS);
    private final Realm realm = Realm.getDefaultInstance();
    private final RealmList<EventItem> eventItems = new RealmList<>();
    private EventsRV eventsRV;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_events);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (recyclerView.getAdapter() == null) {
            //noinspection DuplicateStringLiteralInspection
            final String fields[] = {"time", "title", "location", "desc", "key"};
            final Sort sortOrders[] = {Sort.ASCENDING, Sort.ASCENDING,
                    Sort.ASCENDING, Sort.ASCENDING, Sort.ASCENDING};
            eventItems.addAll(realm.where(EventItem.class)
                    .sort(fields, sortOrders)
                    .findAll());

            eventsRV = new EventsRV(getContext(), eventItems, Calendar.getInstance().getTime());
            recyclerView.setAdapter(eventsRV);

            final ValueEventListener listener = returnValueListener();
            eventsRef.addValueEventListener(listener);
        }
    }

    @SuppressWarnings("FeatureEnvy")
    private ValueEventListener returnValueListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(final DataSnapshot dataSnapshot) {
                realm.executeTransaction(realm -> {
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
                        } catch (final Exception e) {
                            DHC.e(TAG, "parse error of event in Events");
                        }
                    }
                });
                eventItems.clear();
                eventItems.addAll(
                        realm.where(EventItem.class)
                                .sort("time", Sort.ASCENDING)
                                .findAll());
                eventsRV.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(final DatabaseError error) {
                DHC.e(TAG, "database Error " + error.getMessage());
            }
        };
    }
}
