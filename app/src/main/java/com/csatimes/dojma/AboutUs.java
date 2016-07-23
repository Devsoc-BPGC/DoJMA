package com.csatimes.dojma;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class AboutUs extends AppCompatActivity {
    Window window;
    ImageView vik;
    ImageView yash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.about_us_toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("About DoJMA Android App");
        getSupportActionBar().setSubtitle("Mobile Applications Club");

        //These flags are for system bar on top
        //Don't bother yourself with this code
        window = this.getWindow();

        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        if (Build.VERSION.SDK_INT >= 21) {  // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
            window.setNavigationBarColor(ContextCompat.getColor(this, R.color.colorPrimary));
        }

        vik = (ImageView) findViewById(R.id.about_us_vik);
        yash = (ImageView) findViewById(R.id.about_us_yash);

        Picasso.with(this).load(R.drawable.vikramaditya_300x400).transform(new CircleCropTransformation()).into(vik);
        Picasso.with(this).load(R.drawable.yash).transform(new CircleCropTransformation()).into(yash);

    }

}
