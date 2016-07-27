package com.csatimes.dojma;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Calendar;

public class Events extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView eventsRV;
    private EventsRV adapter;
    private String response = null;
    private TextView errorText;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private SwipeRefreshLayout swipeRefreshLayout;

    public Events() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        setOldValues();
        if (!isOnline()) {
            errorText.setText("Stay connected for latest updates on events");
            errorText.setVisibility(View.VISIBLE);
        } else {
            errorText.setVisibility(View.GONE);
            new DownloadList().execute();
        }
    }

    private void setOldValues() {
        int events = preferences.getInt("EVENTS_number", 0);
        if (events != 0) {
            errorText.setVisibility(View.VISIBLE);
            errorText.setText("Stay connected for latest updates");
            EventItem[] eventlist = new EventItem[events];
            for (int i = 0; i < events; i++) {
                String title = preferences.getString("EVENTS_number_" + i + "_title", "");
                String date = preferences.getString("EVENTS_number_" + i + "_date", "");
                String time = preferences.getString("EVENTS_number_" + i + "_time", "");
                String endtime = preferences.getString("EVENTS_number_" + i + "_endtime", "");
                String location = preferences.getString("EVENTS_number_" + i + "_location", "");
                String desc = preferences.getString("EVENTS_number_" + i + "_desc", "");

                eventlist[i] = new EventItem(title, date, time, endtime, location, desc);
            }
            adapter = new EventsRV(getContext(), eventlist, Calendar.getInstance().getTime());
            eventsRV.setAdapter(adapter);
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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.events_refresh);

        swipeRefreshLayout.setColorSchemeResources(R.color.amber500, R.color.blue500, R.color
                .brown500, R.color.cyan500, R.color.deeporange500, R.color.deepPurple500, R.color.green500, R
                .color.grey500, R.color.indigo500, R.color.lightblue500, R.color.lime500, R.color
                .orange500, R.color.pink500, R.color.red500, R.color.teal500, R.color.violet500, R
                .color.yellow500);

        swipeRefreshLayout.setOnRefreshListener(this);


        eventsRV.setHasFixedSize(true);
        eventsRV.setItemAnimator(new DefaultItemAnimator());
        eventsRV.setLayoutManager(new LinearLayoutManager(getContext()));

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

    @Override
    public void onRefresh() {
        if (isOnline()) {
            new DownloadList().execute();
            errorText.setVisibility(View.GONE);
        } else {
            Snackbar.make(swipeRefreshLayout, R.string.no_internet_msg, Snackbar.LENGTH_LONG).show();
            setOldValues();
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private class DownloadList extends AsyncTask<Void, Void, Void> {


        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL(DHC.eventsAddress);
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                StringBuilder sb = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                    sb.append("\n");
                }
                response = sb.toString();
                in.close();
            } catch (Exception e) {
                response = null;
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            errorText.setText("Could not check for updates");
            errorText.setVisibility(View.VISIBLE);
            swipeRefreshLayout.setRefreshing(false);
            setOldValues();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            swipeRefreshLayout.setRefreshing(false);
            if (response != null) {
                try {
                    InputStream stream = new ByteArrayInputStream(response.getBytes("US-ASCII"));
                    InputStreamReader reader = new InputStreamReader(stream);
                    BufferedReader scanner = new BufferedReader(reader);
                    int noOfEvents = Integer.parseInt(scanner.readLine());
                    EventItem[] events = new EventItem[noOfEvents];
                    for (int i = 0; i < noOfEvents; i++) {
                        events[i] = new EventItem(scanner.readLine(), scanner.readLine(), scanner.readLine(), scanner.readLine(), scanner.readLine(), scanner.readLine());
                    }
                    adapter = new EventsRV(getContext(), events, Calendar.getInstance().getTime());
                    eventsRV.setAdapter(adapter);

                    scanner.close();
                    reader.close();
                    stream.close();

                    for (int i = 0; i < noOfEvents; i++) {
                        editor.putString("EVENTS_number_" + i + "_title", events[i].getTitle());
                        editor.putString("EVENTS_number_" + i + "_date", events[i].getDate());
                        editor.putString("EVENTS_number_" + i + "_time", events[i].getTime());
                        editor.putString("EVENTS_number_" + i + "_endtime", events[i].getEndTime());
                        editor.putString("EVENTS_number_" + i + "_location", events[i].getLocation());
                        editor.putString("EVENTS_number_" + i + "_desc", events[i].getDesc());
                    }
                    editor.putInt("EVENTS_number", noOfEvents);
                    editor.apply();
                } catch (Exception e) {
                    errorText.setText("Error! Try again later");
                    errorText.setVisibility(View.VISIBLE);
                    setOldValues();
                }

            } else {
                errorText.setText("Could not check for latest updates");
                errorText.setVisibility(View.VISIBLE);
                setOldValues();
            }
        }
    }
}
