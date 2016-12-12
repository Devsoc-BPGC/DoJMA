package com.csatimes.dojma;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.adapters.GazettesRV;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.realm.Realm;
import io.realm.RealmResults;


public class Gazette extends Fragment {

    //Random number to address write permission
    public static final int REQUEST_WRITE_STORAGE = 112;
    GazettesRV adapter;
    private RecyclerView gazetteRecyclerView;
    private RealmResults<GazetteItem> gazetteList;
    private boolean hasPermission = false;
    private TextView emptyList;
    private DatabaseReference gazettes = FirebaseDatabase.getInstance().getReference().child("gazettes");
    private Realm database;

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

        emptyList = (TextView) view.findViewById(R.id.gazette_empty_text);
        gazetteRecyclerView = (RecyclerView) view.findViewById(R.id.gazette_listview);

        gazetteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gazetteRecyclerView.setHasFixedSize(true);


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        database = Realm.getDefaultInstance();

        gazetteList = database.where(GazetteItem.class).findAll();
        adapter = new GazettesRV(getContext(), gazetteList);
        gazetteRecyclerView.setAdapter(adapter);

        if (adapter.getItemCount() != 0) {
            emptyList.setVisibility(View.GONE);
        }

        gazettes.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Delete old values in the database
                database.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        database.delete(GazetteItem.class);
                    }
                });

                for (DataSnapshot shot : dataSnapshot.getChildren()) {

                    try {
                        final GazetteItem foo = shot.getValue(GazetteItem.class);
                        database.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                GazetteItem bar = realm.createObject(GazetteItem.class);
                                bar.setTitle(foo.getTitle());
                                bar.setDate(foo.getDate());
                                bar.setUrl(foo.getUrl());
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        DHC.log("child shot get value parse error in gazettes. check firebase");
                    }

                }

                //TODO: sort gazettes later
                gazetteList = database.where(GazetteItem.class).findAll();
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() != 0) {
                    emptyList.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log("gazettes firebase database error " + databaseError.getMessage());
            }
        });
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

        if (adapter.getItemCount() != 0) {
            emptyList.setVisibility(View.GONE);
        }

    }

    @Override
    public void onStop() {
        super.onStop();
        database.close();
    }

}

