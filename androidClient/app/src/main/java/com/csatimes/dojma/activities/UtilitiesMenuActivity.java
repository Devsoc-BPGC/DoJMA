package com.csatimes.dojma.activities;

import android.content.Intent;
import android.os.Bundle;

import com.csatimes.dojma.R;
import com.csatimes.dojma.adapters.SlidingImageAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

/**
 * Created by aryanagarwal on 19/08/18.
 */

public class UtilitiesMenuActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menus);
        Intent intent = getIntent();
        String outlet= intent.getStringExtra("outlet");
        ArrayList<Integer> menus = new ArrayList<>();
        menus.add(R.drawable.ic_arrow_back);
        menus.add(R.drawable.ic_call);
        ViewPager viewPager = findViewById(R.id.view_pager);
        SlidingImageAdapter adapter = new SlidingImageAdapter(this,menus);
        viewPager.setAdapter(adapter);
    }
}
