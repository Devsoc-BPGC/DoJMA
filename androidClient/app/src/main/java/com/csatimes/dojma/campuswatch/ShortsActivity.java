package com.csatimes.dojma.campuswatch;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageButton;

import com.csatimes.dojma.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;


public class ShortsActivity extends AppCompatActivity {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorts);

        final Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.black));
        window.setNavigationBarColor(ContextCompat.getColor(this, R.color.black));
        ImageButton back_but = findViewById(R.id.img_back);
        back_but.setOnClickListener(view -> onBackPressed());

        ViewPager viewPager = findViewById(R.id.vp_shorts);
        viewPager.setAdapter(new ShortsAdapter());
    }
}
