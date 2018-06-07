package com.csatimes.dojma.activities;

import android.os.Bundle;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.ShortsVerticalViewPagerAdapter;
import com.csatimes.dojma.viewholders.ShortsVerticalViewPager;

import androidx.appcompat.app.AppCompatActivity;

public class ShortsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);
        initSwipePager();
    }

    private void initSwipePager() {
        ShortsVerticalViewPager shortsVerticalViewPager = (ShortsVerticalViewPager) findViewById(R.id.viewpager_shorts);
        shortsVerticalViewPager.setAdapter(new ShortsVerticalViewPagerAdapter(this));
    }
}
