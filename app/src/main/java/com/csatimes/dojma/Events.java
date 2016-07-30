package com.csatimes.dojma;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Vector;

public class Events extends Fragment implements View.OnClickListener {
    DatabaseReference eventsRef = FirebaseDatabase.getInstance().getReference().child("events");
    private RecyclerView eventsRV;
    private EventsRV adapter;
    private TextView errorText;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Vector<EventItem> eventItems = new Vector<>(5, 5);
    private String sprefPreFix = "EVENTS_number_";
    private String sprefTitlePostFix = "_title";
    private String sprefDescPostFix = "_desc";
    private String sprefLocationPostFix = "_location";
    private String sprefSTPostFix = "_start_time";
    private String sprefSDPostFix = "_start_date";
    private String sprefETPostFix = "_end_time";
    private String sprefEDPostFix = "_end_date";
    private String sprefEventNumber = "EVENTS_number";

    public Events() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isOnline()) {
            errorText.setText("Stay connected for latest updates on events");
            errorText.setVisibility(View.VISIBLE);
            setOldValues();
            adapter.notifyDataSetChanged();
        } else {
            eventsRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    errorText.setVisibility(View.GONE);
                    int i = 0;
                    eventItems.clear();
                    for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                        try {
                            eventItems.add(i, childDataSnapshot.getValue(EventItem.class));
                            i++;
                        } catch (Exception e) {
                        }
                    }

                    for (i = 0; i < eventItems.size(); i++) {
                        editor.putString(sprefPreFix + i + sprefTitlePostFix, eventItems.get(i).getTitle());
                        editor.putString(sprefPreFix + i + sprefSDPostFix, eventItems.get(i).getStartDate());
                        editor.putString(sprefPreFix + i + sprefSTPostFix, eventItems.get(i).getStartTime());
                        editor.putString(sprefPreFix + i + sprefETPostFix, eventItems.get(i).getEndTime());
                        editor.putString(sprefPreFix + i + sprefLocationPostFix, eventItems.get(i).getLocation());
                        editor.putString(sprefPreFix + i + sprefEDPostFix, eventItems.get(i).getEndDate());
                        editor.putString(sprefPreFix + i + sprefDescPostFix, eventItems.get(i).getDesc());
                    }
                    editor.putInt(sprefEventNumber, eventItems.size());
                    editor.apply();
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    setOldValues();
                    adapter.notifyDataSetChanged();
                }
            });
        }
    }

    private void setOldValues() {
        int events = preferences.getInt("EVENTS_number", 0);
        eventItems.clear();
        if (events != 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Stay connected for latest updates");
            eventItems.clear();
            for (int i = 0; i < events; i++) {
                String title = preferences.getString(sprefPreFix + i + sprefTitlePostFix, "");
                String date = preferences.getString(sprefPreFix + i + sprefSDPostFix, "");
                String endDate = preferences.getString(sprefPreFix + i + sprefEDPostFix, "");
                String time = preferences.getString(sprefPreFix + i + sprefSTPostFix, "");
                String endtime = preferences.getString(sprefPreFix + i + sprefETPostFix, "");
                String location = preferences.getString(sprefPreFix + i + sprefLocationPostFix, "");
                String desc = preferences.getString(sprefPreFix + i + sprefDescPostFix, "");

                eventItems.add(i, new EventItem(title, date, time, endtime, location, desc, endDate));
            }
        } else {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("No events are available");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_events, container, false);

        preferences = getContext().getSharedPreferences(DHC.USER_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();

        eventsRV = (RecyclerView) view.findViewById(R.id.events_recycler_view);

        errorText = (TextView) view.findViewById(R.id.error_text_view);

        eventsRV.setHasFixedSize(true);
        eventsRV.setItemAnimator(new DefaultItemAnimator());
        eventsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        setOldValues();
        adapter = new EventsRV(getContext(), eventItems, Calendar.getInstance().getTime());
        eventsRV.setAdapter(adapter);

        errorText.setOnClickListener(this);
        return view;
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
