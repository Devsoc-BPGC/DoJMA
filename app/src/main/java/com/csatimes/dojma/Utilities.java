package com.csatimes.dojma;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.adapters.UtilitiesRV;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Utilities extends Fragment {
    ValueEventListener miscEventListener;
    private UtilitiesRV adapter;
    private String message = "";
    private DatabaseReference miscReference = FirebaseDatabase.getInstance().getReference().child("miscCard");
    private SharedPreferences.Editor editor;

    public Utilities() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilities, container, false);


        RecyclerView utilitiesRecyclerView = (RecyclerView) view.findViewById(R.id.utilities_rv);

        SharedPreferences sp = getContext().getSharedPreferences(DHC.USER_PREFERENCES, Context.MODE_PRIVATE);
        editor = sp.edit();

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        sglm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        utilitiesRecyclerView.setLayoutManager(sglm);
        utilitiesRecyclerView.setHasFixedSize(true);

        message = sp.getString("miscMessage", "");
        adapter = new UtilitiesRV(message);
        utilitiesRecyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        miscEventListener = returnValueListener();

        miscReference.addValueEventListener(miscEventListener);

    }

    private ValueEventListener returnValueListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                message = dataSnapshot.getValue(String.class);
                editor.putString("miscMessage", message);
                editor.apply();
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        miscReference.removeEventListener(miscEventListener);
    }
}
