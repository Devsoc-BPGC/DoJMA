package com.csatimes.dojma;

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

import com.csatimes.dojma.adapters.GazettesRV;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.utilities.SimpleAlertDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

import static com.csatimes.dojma.adapters.GazettesRV.SINGLE_CLICK;


public class Gazette extends Fragment implements GazettesRV.onGazetteItemClickedListener {

    //Random number to address write permission
    public static final int REQUEST_WRITE_STORAGE = 112;

    private GazettesRV adapter;
    private RealmResults<GazetteItem> gazetteResults;
    private TextView emptyList;
    private DatabaseReference gazettes = FirebaseDatabase.getInstance().getReference().child("gazettes2");
    private Realm database;
    private ValueEventListener gazetteListener;
    private RecyclerView gazetteRecyclerView;

    public Gazette() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gazette, container, false);

        emptyList = (TextView) view.findViewById(R.id.gazette_empty_text);
        gazetteRecyclerView = (RecyclerView) view.findViewById(R.id.gazette_listview);

        gazetteRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), span()));
        gazetteRecyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();


        database = Realm.getDefaultInstance();
        gazetteResults = database.where(GazetteItem.class).findAllSorted("time", Sort.DESCENDING);

        adapter = new GazettesRV(gazetteResults);
        if (adapter.getItemCount() != 0) {
            emptyList.setVisibility(View.GONE);
        }
        gazetteRecyclerView.setAdapter(adapter);

        adapter.setOnGazetteItemClickedListener(this);

        gazetteListener = returnEventListener();

        gazettes.addValueEventListener(gazetteListener);
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

    private ValueEventListener returnEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                emptyList.setVisibility(View.GONE);

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
                                bar.setImageUrl(foo.getImageUrl());
                                bar.setTime(bar.getTime());
                            }
                        });
                    } catch (Exception e) {
                        DHC.log("child shot get value parse error in gazettes. check firebase");
                    }

                }

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
    public void onClicked(GazetteItem gi, int clickType) {
        if (clickType == SINGLE_CLICK) {
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
        } else {
            //Detected long click, show fragment
            //TODO Handle gazette long click more appropriately
            SimpleAlertDialog sad = new SimpleAlertDialog();
            sad.showDialog(getContext(), "hello", "", "OK", "", true, true);
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

