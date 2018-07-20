package com.csatimes.dojma.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;
import static com.csatimes.dojma.utilities.DHC.USER_PREFERENCES_MISC_CARD_MESSAGE;
import static com.csatimes.dojma.utilities.DHC.getStaggeredGridSpan;

public class Utilities extends Fragment {
    private final DatabaseReference mMiscReference = FirebaseDatabase.getInstance().getReference().child("miscCard");
    private ValueEventListener mMiscEventListener;
    private String mMessage = "";
    private SharedPreferences.Editor mEditor;
    private static final String TAG = TAG_PREFIX + Utilities.class.getSimpleName();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_utilities, container, false);
    }

    @Override
    public void onViewCreated(final View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView utilitiesRecyclerView = view.findViewById(R.id.utilities_rv);

        final SharedPreferences sp = getContext().getSharedPreferences(DHC.USER_PREFERENCES, Context.MODE_PRIVATE);
        mEditor = sp.edit();

        final StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(getStaggeredGridSpan(), StaggeredGridLayoutManager.VERTICAL);
        sglm.setGapStrategy(androidx.recyclerview.widget.StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        utilitiesRecyclerView.setLayoutManager(sglm);
        utilitiesRecyclerView.setHasFixedSize(true);

        mMessage = sp.getString(USER_PREFERENCES_MISC_CARD_MESSAGE, getString(R.string.UTILITIES_MISC_subtitle));
        final UtilitiesAdapter mUtilitiesAdapter = new UtilitiesAdapter(getContext(), mMessage);
        utilitiesRecyclerView.setAdapter(mUtilitiesAdapter);

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
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                mMessage = dataSnapshot.getValue(String.class);
                mEditor.putString(USER_PREFERENCES_MISC_CARD_MESSAGE, mMessage);
                mEditor.apply();
            }

            @Override
            public void onCancelled(@NonNull final DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage(), databaseError.toException());
            }
        };
    }

    @Override
    public void onStop() {
        super.onStop();
        mMiscReference.removeEventListener(mMiscEventListener);
    }
}
