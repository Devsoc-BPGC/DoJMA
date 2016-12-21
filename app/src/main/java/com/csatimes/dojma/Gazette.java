package com.csatimes.dojma;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.adapters.GazettesRV;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.utilities.DHC;
import com.csatimes.dojma.utilities.SimpleAlertDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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

        gazetteRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), span()));
        gazetteRecyclerView.setHasFixedSize(true);

        database = Realm.getDefaultInstance();

        gazetteResults = database.where(GazetteItem.class).findAllSorted("time", Sort.DESCENDING);

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
            //TODO Download file to destination uri and check if file exists
            DownloadManager downloadManager = (DownloadManager) getContext().getSystemService(Context.DOWNLOAD_SERVICE);
            downloadManager.enqueue(new DownloadManager.Request(Uri.parse(gi.getUrl())).setTitle(gi.getReleaseDateFormatted()).setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED).setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE).setMimeType("text/pdf"));
        } else {
            //Detected long click, show fragment
            //TODO Handle gazette long click more appropriately
            SimpleAlertDialog sad = new SimpleAlertDialog();
            sad.showDialog(getContext(), "hello", "", "OK", "", true, true);
        }
    }
}

