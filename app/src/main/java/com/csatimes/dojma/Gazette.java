package com.csatimes.dojma;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Vector;


public class Gazette extends Fragment {
    private static final int REQUEST_WRITE_STORAGE = 112;
    GazettesRV adapter;
    private RecyclerView gazetteRecyclerView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Vector<GazetteItem> gazetteList = new Vector<>(5, 1);
    private boolean hasPermission = false;
    private TextView emptyList;
    private DatabaseReference gazettes = FirebaseDatabase.getInstance().getReference().child
            ("gazettes");
    private String sprefGazetteNumber = "GAZETTE_number";
    private String sprefPreFix = "GAZETTE_number_";
    private String sprefTitlePostFix = "_title";
    private String sprefUrlPostFix = "_url";

    public Gazette() {
        // Required empty public constructor
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_WRITE_STORAGE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasPermission = true;
                    if (adapter != null) adapter.setHasWritePermission(hasPermission);
                } else {
                    Snackbar.make(gazetteRecyclerView, "Write permission denied, pdfs will be handled by browser", Snackbar.LENGTH_LONG).show();
                    hasPermission = false;
                    if (adapter != null) adapter.setHasWritePermission(hasPermission);
                }
            }
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gazette, container, false);

        sharedPreferences = getContext().getSharedPreferences(DHC.USER_PREFERENCES, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        emptyList = (TextView) view.findViewById(R.id.gazette_empty_text);
        gazetteRecyclerView = (RecyclerView) view.findViewById(R.id.gazette_listview);

        gazetteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gazetteRecyclerView.setHasFixedSize(true);
        gazetteRecyclerView.setItemAnimator(new DefaultItemAnimator());
        setOldValues();
        adapter = new GazettesRV(getContext(), gazetteList);
        gazetteRecyclerView.setAdapter(adapter);


        return view;
    }


    private boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context
                .CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }

    @Override
    public void onResume() {
        super.onResume();

        hasPermission = (ContextCompat.checkSelfPermission(getContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
        if (!hasPermission) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }


        if (isOnline()) {
            gazettes.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    emptyList.setVisibility(View.GONE);
                    gazetteRecyclerView.setVisibility(View.VISIBLE);
                    gazetteList.clear();
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        gazetteList.add(shot.getValue(GazetteItem.class));
                    }
                    for (int i = 0; i < gazetteList.size(); i++) {
                        editor.putString(sprefPreFix + i + sprefTitlePostFix, gazetteList.get(i).getTitle());
                        Log.e("TAG", gazetteList.get(i).getTitle());
                        editor.putString(sprefPreFix + i + sprefUrlPostFix, gazetteList.get(i).getUrl());
                    }
                    editor.putInt(sprefGazetteNumber, gazetteList.size());
                    editor.apply();
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    setOldValues();
                    adapter.notifyDataSetChanged();
                }
            });
        } else {
            setOldValues();
            adapter.notifyDataSetChanged();
        }
    }

    private void setOldValues() {

        int pdfs = sharedPreferences.getInt(sprefGazetteNumber, 0);
        if (pdfs != 0) {

            emptyList.setVisibility(View.GONE);
            gazetteRecyclerView.setVisibility(View.VISIBLE);
            gazetteList.clear();
            for (int i = 0; i < pdfs; i++) {
                gazetteList.add(new GazetteItem(sharedPreferences.getString(sprefPreFix + i +
                        sprefTitlePostFix, "-"), sharedPreferences.getString(sprefPreFix + i +
                        sprefUrlPostFix, "-")));
            }

        } else {
            //Since number of gazettes can't go down :P , it will be always >=0
            //if it is zero then it means that the app has never been connected to
            // the internet before while checking
            //Just set everything as empty
            emptyList.setVisibility(View.VISIBLE);
            gazetteRecyclerView.setVisibility(View.GONE);

        }
    }
}

