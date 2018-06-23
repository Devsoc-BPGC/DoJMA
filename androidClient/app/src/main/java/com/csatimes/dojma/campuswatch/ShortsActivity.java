package com.csatimes.dojma.campuswatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Toolbar;

import com.csatimes.dojma.R;
import com.google.android.material.appbar.AppBarLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


public class ShortsActivity extends AppCompatActivity {
    private AppBarLayout appBarLayout;
    private boolean isCardClicked = true;
    private GestureDetector gestureDetector;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);

        final Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));

        gestureDetector = new GestureDetector(this, new SingleTapConfirm());
        appBarLayout = findViewById(R.id.shorts_appbar);
        appBarLayout.setExpanded(isCardClicked);

        Toolbar toolbar = findViewById(R.id.shorts_toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setTitle(R.string.campus_watch);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
        ViewPager viewPager = findViewById(R.id.vp_shorts);
        viewPager.setAdapter(new ShortsAdapter());
        viewPager.setOnTouchListener((View arg0, MotionEvent arg1) -> {

            if (gestureDetector.onTouchEvent(arg1)) {
               onShortClicked();
                return true;
            }
            return false;
        });
    }

    public void onShortClicked() {
        isCardClicked = !isCardClicked;
        appBarLayout.setExpanded(isCardClicked);
    }

    private class SingleTapConfirm extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapUp(MotionEvent event) {
            return true;
        }
    }
}
