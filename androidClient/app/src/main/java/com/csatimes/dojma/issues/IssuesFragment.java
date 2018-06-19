package com.csatimes.dojma.issues;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csatimes.dojma.R;
import com.csatimes.dojma.models.Category;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        SmartTabLayout viewPagerTab = (SmartTabLayout) view.findViewById(R.id.viewpagertab);
        Bundle args = new Bundle();
        args.putString("category","Campus");
        Bundle args1 = new Bundle();
        args1.putString("category","National");
        Bundle args2 = new Bundle();
        args2.putString("category","World");
        Bundle args3 = new Bundle();
        args3.putString("category","Technology");
        Bundle args4 = new Bundle();
        args4.putString("category","Sports");
        Bundle args5 = new Bundle();
        args5.putString("category","Interviews");
        Bundle args6 = new Bundle();
        args6.putString("category","DoJMA Recommends");
        Bundle args7 = new Bundle();
        args7.putString("category","Bitzfeed");
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getActivity().getSupportFragmentManager(), FragmentPagerItems.with(getContext())
                .add("Issues", IssuePagerFragment.class)
                .add("Campus", CategoryFragment.class,args)
                .add("National", CategoryFragment.class,args1)
                .add("World", CategoryFragment.class,args2)
                .add("Technology", CategoryFragment.class,args3)
                .add("Sports", CategoryFragment.class,args4)
                .add("Interviews", CategoryFragment.class,args5)
                .add("Recommends", CategoryFragment.class,args6)
                .add("BITZFEED", CategoryFragment.class,args7)
                .create());

        viewPager.setAdapter(adapter);
        viewPagerTab.setViewPager(viewPager);
    }

    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}
