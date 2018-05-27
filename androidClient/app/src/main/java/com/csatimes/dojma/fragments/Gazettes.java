package com.csatimes.dojma.fragments;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.GazettesAdapter;
import com.csatimes.dojma.models.GazetteItem;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.Sort;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;


public class Gazettes extends Fragment implements GazettesAdapter.onGazetteItemClickedListener {

    public static final String TAG = "fragments.Gazettes";
    private GazettesAdapter mGazettesAdapter;
    private TextView mEmptyListTextView;
    private DatabaseReference mGazettesReference = FirebaseDatabase.getInstance().getReference().child("gazettes2");
    private Realm mDatabase;
    private ValueEventListener mGazettesReferenceListener;
    private androidx.recyclerview.widget.RecyclerView mGazetteRecyclerView;
    private RealmList<GazetteItem> mDataSet;

    public static Fragment newInstance() {
        Gazettes gazettes = new Gazettes();
        return gazettes;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gazette, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mEmptyListTextView = (TextView) view.findViewById(R.id.gazette_empty_text);
        mGazetteRecyclerView = (RecyclerView) view.findViewById(R.id.gazette_listview);

        mGazetteRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), span()));
        mGazetteRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onStart() {
        super.onStart();

        mDatabase = Realm.getDefaultInstance();
        mDataSet = new RealmList<>();
        mDataSet.addAll(mDatabase.where(GazetteItem.class).sort("time", Sort.DESCENDING).findAll());

        mGazettesAdapter = new GazettesAdapter(mDataSet);

        updateEmptyText();

        mGazetteRecyclerView.setAdapter(mGazettesAdapter);

        mGazettesAdapter.setOnGazetteItemClickedListener(this);
        mGazettesReferenceListener = returnChildEventListener();
        mGazettesReference.addListenerForSingleValueEvent(mGazettesReferenceListener);
    }

    public void updateEmptyText() {
        if (mGazettesAdapter == null || mGazettesAdapter.getItemCount() == 0) {
            mEmptyListTextView.setVisibility(VISIBLE);
        } else {
            mEmptyListTextView.setVisibility(INVISIBLE);
        }
    }

    private ValueEventListener returnChildEventListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Delete old database
                mDatabase.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        realm.delete(GazetteItem.class);
                    }
                });

                for (final DataSnapshot childShot : dataSnapshot.getChildren()) {
                    try {
                        final GazetteItem foo = childShot.getValue(GazetteItem.class);
                        mDatabase.executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {
                                GazetteItem bar = realm.createObject(GazetteItem.class, childShot.getKey());
                                bar.setTitle(foo.getTitle());
                                bar.setDate(foo.getDate());
                                bar.setUrl(foo.getUrl());
                                bar.setImageUrl(foo.getImageUrl());
                                bar.setTime(bar.getTime());
                            }
                        });
                    } catch (Exception e) {
                        DHC.e(TAG, "Exception in parsing value at " + dataSnapshot.getKey());
                    }
                }
                mDataSet.clear();
                mDataSet.addAll(mDatabase.where(GazetteItem.class).sort("time", Sort.DESCENDING).findAll());
                mGazettesAdapter.notifyDataSetChanged();
                updateEmptyText();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                DHC.e(TAG, "Database error " + databaseError.getMessage() + " " + databaseError.getDetails());
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        mGazettesReference.removeEventListener(mGazettesReferenceListener);
        mDatabase.close();
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
        if (r < 0.1 * num) {
            cols = (int) Math.ceil(dpWidth / num);
        } else {
            cols = (int) t;
        }

        return cols;
    }

    @Override
    public void onClicked(GazetteItem gi) {
        DHC.getGazette(getActivity(), gi);
    }
}

