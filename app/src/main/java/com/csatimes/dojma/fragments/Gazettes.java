package com.csatimes.dojma.fragments;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.csatimes.dojma.BuildConfig;
import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.GazettesAdapter;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;


public class Gazettes extends Fragment implements GazettesAdapter.onGazetteItemClickedListener {

    private GazettesAdapter mGazettesAdapter;
    private TextView mEmptyListTextView;
    private DatabaseReference mGazettesReference = FirebaseDatabase.getInstance().getReference().child("gazettes2");
    private Realm mDatabase;
    private ChildEventListener mGazettesReferenceListener;
    private RecyclerView mGazetteRecyclerView;
    private RealmList<GazetteItem> mDataSet;

    public Gazettes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gazette, container, false);

        mEmptyListTextView = (TextView) view.findViewById(R.id.gazette_empty_text);
        mGazetteRecyclerView = (RecyclerView) view.findViewById(R.id.gazette_listview);

        mGazetteRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), span()));
        mGazetteRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabase = Realm.getDefaultInstance();
        mDataSet = new RealmList<>();
        mDataSet.addAll(mDatabase.where(GazetteItem.class).findAllSorted("time", Sort.DESCENDING));

        mGazettesAdapter = new GazettesAdapter(mDataSet);

        if (mGazettesAdapter.getItemCount() != 0) {
            mEmptyListTextView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyListTextView.setVisibility(View.VISIBLE);
        }

        mGazetteRecyclerView.setAdapter(mGazettesAdapter);

        mGazettesAdapter.setOnGazetteItemClickedListener(this);
        mGazettesReferenceListener = returnChildEventListener();
        mGazettesReference.addChildEventListener(mGazettesReferenceListener);
    }

    private int span() {

        //Setup columns according to device screen
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up grid
        int num = 180;
        float t = dpWidth / num;
        float r = dpWidth % num;
        int cols;
        if (r < 0.1 * num)
            cols = (int) Math.ceil(dpWidth / num);
        else
            cols = (int) t;

        return cols;
    }


    private ChildEventListener returnChildEventListener() {
        return new ChildEventListener() {
            int position;

            @Override
            public void onChildAdded(final DataSnapshot dataSnapshot, String s) {
                DHC.log("onChildAdded");
                try {
                    if (mDatabase.where(GazetteItem.class).equalTo("key", dataSnapshot.getKey()).findFirst() == null) {
                        final GazetteItem foo = dataSnapshot.getValue(GazetteItem.class);
                        mDatabase.executeTransactionAsync(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                GazetteItem bar = realm.createObject(GazetteItem.class, dataSnapshot.getKey());
                                bar.setTitle(foo.getTitle());
                                bar.setDate(foo.getDate());
                                bar.setUrl(foo.getUrl());
                                bar.setImageUrl(foo.getImageUrl());
                                bar.setTime(bar.getTime());
                            }
                        }, new Realm.Transaction.OnSuccess() {
                            @Override
                            public void onSuccess() {
                                mDataSet.clear();
                                mDataSet.addAll(mDatabase.where(GazetteItem.class).findAllSorted("time",Sort.DESCENDING));
                                GazetteItem foo = mDatabase.where(GazetteItem.class).equalTo("key",dataSnapshot.getKey()).findFirst();
                                int position = mDataSet.indexOf(foo);
                                mGazettesAdapter.notifyItemInserted(position);
                                mGazetteRecyclerView.scrollToPosition(position);
                                mEmptyListTextView.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                } catch (Exception e) {
                    DHC.log("Parsing exception for Gazette " + e.getMessage());
                }
            }

            @Override
            public void onChildChanged(final DataSnapshot dataSnapshot, String s) {
                onChildMoved(dataSnapshot,s);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                DHC.log("onChildRemoved");
                //Child deleted. Delete the entry from Gazettes database
                final String key = dataSnapshot.getKey();
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        GazetteItem foo = realm.where(GazetteItem.class).equalTo("key", key).findFirst();
                        int position = mDataSet.indexOf(foo);
                        mGazettesAdapter.notifyItemRemoved(position);
                        mDataSet.remove(position);
                        foo.deleteFromRealm();
                    }
                });
                if (mGazettesAdapter.getItemCount() == 0)
                    mEmptyListTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                DHC.log("onChildMoved");
                onChildRemoved(dataSnapshot);
                onChildAdded(dataSnapshot, null);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.log("Database Error in Gazettes " + databaseError.getDetails());
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        mGazettesReference.removeEventListener(mGazettesReferenceListener);
        mDatabase.close();
    }

    @Override
    public void onClicked(GazetteItem gi) {
        File pdf = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), gi.getTitle() + " " + gi.getReleaseDateFormatted() + ".pdf");
        if (pdf.exists()) {
            Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", pdf);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(uri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (intent.resolveActivity(getContext().getPackageManager()) != null)
                startActivity(intent);
            else {
                Toast.makeText(getContext(), "Could not load from local storage, Downloading again", Toast.LENGTH_SHORT).show();
                downloadPDF(gi);
            }
        } else {
            downloadPDF(gi);
        }
    }

    private void downloadPDF(GazetteItem gi) {
        DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
        downloadManager.enqueue(
                new DownloadManager.Request(Uri.parse(gi.getUrl()))
                        .setTitle(gi.getReleaseDateFormatted())
                        .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, gi.getTitle() + " " + gi.getReleaseDateFormatted() + ".pdf")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE)
                        .setMimeType("application/pdf"));
        Toast.makeText(getContext(), "Check notifications for download progress", Toast.LENGTH_SHORT).show();

    }
}

