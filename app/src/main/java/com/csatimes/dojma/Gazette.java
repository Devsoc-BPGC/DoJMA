package com.csatimes.dojma;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import io.realm.RealmList;


public class Gazette extends Fragment implements GazettesRV.onGazetteItemClickedListener {

    //Random number to address write permission
    public static final int REQUEST_WRITE_STORAGE = 112;

    private GazettesRV adapter;
    private RealmList<GazetteItem> gazetteResults;
    private TextView emptyList;
    private DatabaseReference gazettes = FirebaseDatabase.getInstance().getReference().child("gazettes");
    private Realm database;
    private ValueEventListener gazetteListener;

    public Gazette() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gazette, container, false);

        emptyList = (TextView) view.findViewById(R.id.gazette_empty_text);
        RecyclerView gazetteRecyclerView = (RecyclerView) view.findViewById(R.id.gazette_listview);

        gazetteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        gazetteRecyclerView.setHasFixedSize(true);

        database = Realm.getDefaultInstance();

        gazetteResults = new RealmList<>();
        gazetteResults.addAll(database.where(GazetteItem.class).findAll());

        adapter = new GazettesRV(gazetteResults);
        gazetteRecyclerView.setAdapter(adapter);

        adapter.setOnGazetteItemClickedListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (adapter.getItemCount() != 0) {
            emptyList.setVisibility(View.GONE);
        }

        gazetteListener = returnEventListener();

        gazettes.addValueEventListener(gazetteListener);
    }

    private ValueEventListener returnEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //Delete old values in the database
                database.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(GazetteItem.class);
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
                        DHC.log("child shot get value parse error in gazettes. check firebase");
                    }

                }

                //TODO: sort gazettes later
                gazetteResults.clear();
                gazetteResults.addAll(database.where(GazetteItem.class).findAll());
                adapter.notifyDataSetChanged();
                if (adapter.getItemCount() != 0) {
                    emptyList.setVisibility(View.GONE);
                } else {
                    emptyList.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log("gazettes firebase database error " + databaseError.getMessage());
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        gazettes.removeEventListener(gazetteListener);
        database.close();
    }

    @Override
    public void onClicked(String url, String title) {
        //TODO Download file to destination uri and check if file exists
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(new DownloadManager.Request(Uri.parse(url)).setTitle(title).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).setMimeType("text/pdf"));
    }
}

