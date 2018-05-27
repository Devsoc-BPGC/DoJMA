package com.csatimes.dojma.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by vikramaditya on 14/12/16.
 */

public class ViewPagerAdapter extends androidx.fragment.app.FragmentPagerAdapter {
    private SparseArray<androidx.fragment.app.Fragment> fragmentMap       = new SparseArray<>();
    private SparseArray<String>                         fragmentTitlesMap = new SparseArray<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public androidx.fragment.app.Fragment getItem(int position) {
        return fragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return fragmentMap.size();
    }

    public void addFragment(androidx.fragment.app.Fragment fragment, String title, int position) {
        fragmentMap.put(position, fragment);
        fragmentTitlesMap.put(position, title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitlesMap.get(position);
    }
}