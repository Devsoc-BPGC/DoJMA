package com.csatimes.dojma.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.UtilitiesAdapter;
import com.csatimes.dojma.utilities.DHC;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_MISC_CARD_MESSAGE;

public class Utilities extends Fragment {
    ValueEventListener mMiscEventListener;
    private String mMessage = "";
    private DatabaseReference mMiscReference = FirebaseDatabase.getInstance().getReference().child("miscCard");
    private SharedPreferences.Editor mEditor;

    public Utilities() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_utilities, container, false);


        RecyclerView utilitiesRecyclerView = (RecyclerView) view.findViewById(R.id.utilities_rv);

        SharedPreferences sp = getContext().getSharedPreferences(DHC.USER_PREFERENCES, Context.MODE_PRIVATE);
        mEditor = sp.edit();

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(span(), StaggeredGridLayoutManager.VERTICAL);
        sglm.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        utilitiesRecyclerView.setLayoutManager(sglm);
        utilitiesRecyclerView.setHasFixedSize(true);

        mMessage = sp.getString(USER_PREFERENCES_MISC_CARD_MESSAGE, getString(R.string.UTILITIES_MISC_subtitle));
        UtilitiesAdapter mUtilitiesAdapter = new UtilitiesAdapter(getContext(), mMessage);
        utilitiesRecyclerView.setAdapter(mUtilitiesAdapter);

        return view;
    }

    private int span() {

        //Setup columns according to device screen
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up grid
        int num = 180;
        float t = dpWidth / num;
        float r = dpWidth % num;
        if (r < 0.1 * num)
            return (int) Math.ceil(dpWidth / num);
        else
            return (int) t;
    }

    @Override
    public void onStart() {
        super.onStart();
        mMiscEventListener = returnValueListener();
        mMiscReference.addValueEventListener(mMiscEventListener);
    }

    private ValueEventListener returnValueListener() {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mMessage = dataSnapshot.getValue(String.class);
                mEditor.putString(USER_PREFERENCES_MISC_CARD_MESSAGE, mMessage);
                mEditor.apply();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        mMiscReference.removeEventListener(mMiscEventListener);
    }
}
