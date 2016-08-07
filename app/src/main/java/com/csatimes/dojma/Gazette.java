package com.csatimes.dojma;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;


public class Gazette extends Fragment {
    private static final int REQUEST_WRITE_STORAGE = 112;
    GazettesRV adapter;
    private RecyclerView gazetteRecyclerView;
    private Vector<GazetteItem> gazetteList = new Vector<>(5, 1);

    private boolean hasPermission = false;
    private TextView emptyList;


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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        } catch (Exception ignore) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gazette, container, false);

        emptyList = (TextView) view.findViewById(R.id.gazette_empty_text);
        gazetteRecyclerView = (RecyclerView) view.findViewById(R.id.gazette_listview);

        gazetteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gazetteRecyclerView.setHasFixedSize(true);
        gazetteRecyclerView.setItemAnimator(new DefaultItemAnimator());


        emptyList.setVisibility(View.VISIBLE);


        final DatabaseReference gazettes;

        gazettes = FirebaseDatabase.getInstance().getReference().child("gazettes");
        gazettes.keepSynced(true);
        gazettes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() != 0) {
                    emptyList.setVisibility(View.GONE);
                    gazetteList.clear();
                    for (DataSnapshot shot : dataSnapshot.getChildren()) {
                        try {
                            gazetteList.add(shot.getValue(GazetteItem.class));
                        } catch (Exception ignore) {
                        }
                    }

                    Collections.sort(gazetteList, new Comparator<GazetteItem>() {
                        @Override
                        public int compare(GazetteItem o1, GazetteItem o2) {
                            try {
                                Date one = new SimpleDateFormat("ddMMyyyy", Locale.UK).parse(o1.getDate());
                                Date two = new SimpleDateFormat("ddMMyyyy", Locale.UK).parse(o2.getDate());
                                if (one.getTime() - two.getTime() < 0) {
                                    return 1;
                                } else if (one.getTime() == two.getTime()) {
                                    return 0;
                                } else {
                                    return -1;
                                }
                            } catch (ParseException ignore) {
                            }
                            return 0;
                        }
                    });
                    adapter.notifyDataSetChanged();
                } else {
                    emptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        adapter = new GazettesRV(getContext(), gazetteList);
        gazetteRecyclerView.setAdapter(adapter);
        return view;
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


    }


}

