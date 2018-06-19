package com.csatimes.dojma.issues;


import android.os.Bundle;
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

public class CategoryFragment extends Fragment {
    private RecyclerView favRv;
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {



        return inflater.inflate(R.layout.fragment_category, container, false);
    }
    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favRv = view.findViewById(R.id.rv_categories_fragment);
        final HeraldAdapter mHeraldAdapter = new HeraldAdapter(getActivity().getParent());
        Bundle b = getArguments();
        String category = b.getString("category");
        mHeraldAdapter.setCategory(category);
        favRv.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        favRv.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), VERTICAL));
        favRv.setAdapter(mHeraldAdapter);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
