package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.GazettesAdapter;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

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
        DHC.getGazette(getActivity(),gi);
    }
}

