package com.csatimes.dojma.favorites;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.herald.HeraldAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.recyclerview.widget.DividerItemDecoration.VERTICAL;
import static com.csatimes.dojma.utilities.DHC.TAG_PREFIX;

public class FavouritesFragment extends Fragment {

    private static final String TAG = TAG_PREFIX + FavouritesFragment.class.getSimpleName();
    private RecyclerView favRv;

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourites, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favRv = view.findViewById(R.id.rv_favorites_fragment);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final Activity activity = getActivity();
        if (activity == null) {
            Log.e(TAG, "getActivity() in onActivityCreated() returned null");
            return;
        }
        final HeraldAdapter mHeraldAdapter = new HeraldAdapter(activity);
        mHeraldAdapter.onlyFavorites();
        favRv.setLayoutManager(new LinearLayoutManager(activity));
        favRv.addItemDecoration(new DividerItemDecoration(activity, VERTICAL));
        favRv.setAdapter(mHeraldAdapter);
    }
}
