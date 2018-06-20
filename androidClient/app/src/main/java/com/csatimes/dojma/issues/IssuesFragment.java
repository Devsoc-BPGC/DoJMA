package com.csatimes.dojma.issues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import static com.csatimes.dojma.issues.CategoryFragment.KEY_CATEGORY;

public class IssuesFragment extends Fragment {


    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container,
                             final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_issues, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ViewPager viewPager = view.findViewById(R.id.viewpager);
        final SmartTabLayout viewPagerTab = view.findViewById(R.id.viewpagertab);
        final FragmentPagerItems.Creator creator = FragmentPagerItems.with(getContext());
        creator.add("Issues", IssuePagerFragment.class);
        for (final String category : Categories.getCATEGORIES()) {
            final Bundle args = new Bundle();
            args.putString(KEY_CATEGORY, category);
            creator.add(category, CategoryFragment.class, args);
        }
        final FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getChildFragmentManager(), creator.create()
        );

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
