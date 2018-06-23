package com.csatimes.dojma.campuswatch;

import android.os.Bundle;
import android.view.Window;
import android.widget.Toolbar;

import com.csatimes.dojma.R;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class ShortsActivity extends AppCompatActivity implements OnShortClicked {
    private AppBarLayout appBarLayout;
    private boolean clicked;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);
        final Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        appBarLayout = findViewById(R.id.shorts_appbar);
        clicked = true;
        appBarLayout.setExpanded(clicked);
        Toolbar toolbar = findViewById(R.id.shorts_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.campus_watch);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        ViewPager viewPager = findViewById(R.id.vp_shorts);
        ShortsAdapter shortsAdapter = new ShortsAdapter();
        shortsAdapter.setOnShortClicked(this);
        viewPager.setAdapter(shortsAdapter);
    }


    @Override
    public void onClick() {
        clicked = !clicked;
        appBarLayout.setExpanded(clicked);
    }
}
