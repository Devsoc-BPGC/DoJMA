package com.csatimes.dojma.herald;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;
import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;


public class HeraldFragment extends Fragment {

    private static final String TAG = TAG_PREFIX + HeraldFragment.class.getSimpleName();
    private RecyclerView heraldTv;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_herald, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        heraldTv = view.findViewById(R.id.fragment_herald_rv);
        final Context context = getContext();
        heraldTv.setLayoutManager(new GridLayoutManager(context, span()));
        if (context != null) {
            heraldTv.addItemDecoration(new DividerItemDecoration(context, VERTICAL));
        }
    }

    private int span() {
        //Setup columns according to device screen
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        // Setting up images grid
        final float val = 300.0f;
        final float t = dpWidth / val;
        final float r = dpWidth % val;
        return r < val / 2 ? (int) Math.floor(t) : (int) Math.ceil(t);
    }


    @Override
    public void onStart() {
        super.onStart();
        final Activity activity = getActivity();
        if (activity != null) {
            final HeraldAdapter mAdapter = new HeraldAdapter(activity);
            heraldTv.setAdapter(mAdapter);
        } else {
            Log.e(TAG, "getActivity() returned null in onStart()");
        }
    }
}
