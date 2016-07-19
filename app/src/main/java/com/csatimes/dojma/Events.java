package com.csatimes.dojma;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

public class Events extends Fragment implements View.OnClickListener {
    private RecyclerView eventsRV;
    private EventsRV adapter;
    private String response = null;
    private TextView errorText;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    public Events() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isOnline())
            new DownloadList().execute();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_events, container, false);
        eventsRV = (RecyclerView) view.findViewById(R.id.events_recycler_view);
        errorText = (TextView) view.findViewById(R.id.error_text_view);

        if (!isOnline()) {
            errorText.setText("Stay connected for latest updates on events");
            errorText.setVisibility(View.VISIBLE);
            eventsRV.setVisibility(View.GONE);
        } else {
            errorText.setVisibility(View.GONE);
        }
        eventsRV.setHasFixedSize(true);
        eventsRV.setItemAnimator(new DefaultItemAnimator());
        eventsRV.setLayoutManager(new LinearLayoutManager(getContext()));

        errorText.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == errorText.getId()) {
            if (errorText.getVisibility() == View.VISIBLE) errorText.setVisibility(View.GONE);
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (response != null) {
                try {
                    InputStream stream = new ByteArrayInputStream(response.getBytes("US-ASCII"));
                    InputStreamReader reader = new InputStreamReader(stream);
                    BufferedReader scanner = new BufferedReader(reader);
                    //errorText.setText(response);
                    int noOfEvents = Integer.parseInt(scanner.readLine());
                    EventItem[] events = new EventItem[noOfEvents];
                    for (int i = 0; i < noOfEvents; i++) {
                        events[i] = new EventItem(scanner.readLine(), scanner.readLine(), scanner.readLine(), scanner.readLine(), scanner.readLine());
                    }
                    if (adapter == null) {
                        adapter = new EventsRV(getContext(), events);
                        eventsRV.setAdapter(adapter);
                    } else
                        adapter.notifyDataSetChanged();

                    scanner.close();
                    reader.close();
                    stream.close();

                } catch (Exception e) {
                    errorText.setText("Could not check for updates");
                    errorText.setVisibility(View.VISIBLE);
                }

            } else {
                errorText.setText("Could not check for updates");
                errorText.setVisibility(View.VISIBLE);
            }
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}
