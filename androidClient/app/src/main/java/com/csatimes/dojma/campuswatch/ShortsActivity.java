package com.csatimes.dojma.campuswatch;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toolbar;

import com.csatimes.dojma.R;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

public class ShortsActivity extends AppCompatActivity implements OnShortClicked {
    AppBarLayout appBarLayout;
    boolean clicked;

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
        toolbar.inflateMenu(R.menu.shorts_menu);
        ViewPager viewPager = findViewById(R.id.vp_shorts);
        ShortsAdapter shortsAdapter = new ShortsAdapter();
        shortsAdapter.setOnShortClicked(this);
        viewPager.setAdapter(shortsAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.shorts_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            //Perform refresh of data here.
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick() {
        clicked = !clicked;
        appBarLayout.setExpanded(clicked);
    }
}
