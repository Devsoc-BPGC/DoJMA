package com.csatimes.dojma.herald;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;
import static com.csatimes.dojma.utilities.DHC.getGridSpan;


public class HeraldFragment extends Fragment {

    private static final String TAG = TAG_PREFIX + HeraldFragment.class.getSimpleName();
    private RecyclerView heraldRv;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_herald, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        heraldRv = view.findViewById(R.id.fragment_herald_rv);
        final Context context = getContext();
        heraldRv.setLayoutManager(new GridLayoutManager(context, getGridSpan()));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (heraldRv.getAdapter() == null) {
            final HeraldAdapter mAdapter = new HeraldAdapter();
            heraldRv.setAdapter(mAdapter);
        }
    }
}
