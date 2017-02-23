package com.csatimes.dojma.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;

/**
 * Created by vikramaditya on 14/12/16.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private SparseArray<Fragment> fragmentMap = new SparseArray<>();
    private SparseArray<String> fragmentTitlesMap = new SparseArray<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentMap.get(position);
    }

    @Override
    public int getCount() {
        return fragmentMap.size();
    }

    public void addFragment(Fragment fragment, String title, int position) {
        fragmentMap.put(position, fragment);
        fragmentTitlesMap.put(position, title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitlesMap.get(position);
    }
}