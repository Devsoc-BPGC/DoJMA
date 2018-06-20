package com.csatimes.dojma.issues;


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

public class CategoryFragment extends Fragment {

    @SuppressWarnings("WeakerAccess")
    public static final String KEY_CATEGORY = "category";
    public static final String TAG = TAG_PREFIX + CategoryFragment.class.getSimpleName();

    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView favRv = view.findViewById(R.id.rv_categories_fragment);
        final HeraldAdapter mHeraldAdapter = new HeraldAdapter(getActivity().getParent());
        final Bundle b = getArguments();
        if (b != null && b.containsKey(KEY_CATEGORY)) {
            final String category = b.getString(KEY_CATEGORY);
            mHeraldAdapter.setCategory(category);
        } else {
            Log.e(TAG, "Got no category", new Throwable().fillInStackTrace());
        }
        favRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        favRv.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), VERTICAL));
        favRv.setAdapter(mHeraldAdapter);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
